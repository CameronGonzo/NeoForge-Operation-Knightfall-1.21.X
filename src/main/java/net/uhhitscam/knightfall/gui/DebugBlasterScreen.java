package net.uhhitscam.knightfall.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.uhhitscam.knightfall.item.custom.projectile.DebugBlasterSettings;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.network.SSDebugBlasterEditorPacket;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class DebugBlasterScreen extends Screen {
    private static final int FIELD_TOP = 59;
    private static final int FIELD_HEIGHT = 20;
    private static final int ROW_SPACING = 22;
    private static final int PAGE_BUTTON_GAP = 6;
    private static int lastGroup;
    private static int lastPage;
    private record Row(DebugBlasterSettings.Setting setting, int y) {}
    private final int sessionId;
    private final boolean mainHand;
    private final Map<String, String> draft = new LinkedHashMap<>();
    private final List<String> groups = DebugBlasterSettings.SETTINGS.stream().map(DebugBlasterSettings.Setting::group).distinct().toList();
    private final List<Row> rows = new ArrayList<>();
    private int group;
    private int page;
    private String message = "";
    private boolean waiting;
    private boolean choosingGroup;
    private Button apply;

    public DebugBlasterScreen(int sessionId, boolean mainHand, DebugBlasterSettings settings) {
        super(Component.literal("Debug Blaster Editor"));
        this.sessionId = sessionId;
        this.mainHand = mainHand;
        group = lastGroup;
        page = lastPage;
        draft.putAll(settings.values());
    }

    public int sessionId() { return sessionId; }

    @Override
    protected void init() {
        rows.clear();
        int panelWidth = Math.min(560, width - 24);
        int left = (width - panelWidth) / 2;
        int editorWidth = Math.min(185, panelWidth / 2);
        int editorX = left + panelWidth - editorWidth;
        List<DebugBlasterSettings.Setting> fields = DebugBlasterSettings.SETTINGS.stream()
                .filter(setting -> setting.group().equals(groups.get(group)))
                .filter(setting -> !setting.choices().isEmpty() || setting.min() != setting.max()).toList();
        int pageY = height - 72;
        // Fit whole field rows above the page buttons, including a clear gap.
        int pageSize = Math.max(1,
                (pageY - PAGE_BUTTON_GAP - FIELD_TOP + ROW_SPACING - FIELD_HEIGHT) / ROW_SPACING);
        int pages = Math.max(1, (fields.size() + pageSize - 1) / pageSize);
        page = Math.min(page, pages - 1);
        lastGroup = group;
        lastPage = page;
        addRenderableWidget(Button.builder(Component.literal("<"), button -> changeGroup(-1)).bounds(left, 34, 25, 20).build());
        addRenderableWidget(Button.builder(Component.literal(groupLabel(groups.get(group))), button -> {
                    choosingGroup = !choosingGroup;
                    rebuildWidgets();
                })
                .bounds(left + 29, 34, panelWidth - 58, 20)
                .tooltip(Tooltip.create(Component.literal("Choose weapon settings or a firing mode"))).build());
        addRenderableWidget(Button.builder(Component.literal(">"), button -> changeGroup(1)).bounds(left + panelWidth - 25, 34, 25, 20).build());
        if (choosingGroup) {
            int choiceWidth = (panelWidth - 8) / 3;
            for (int index = 0; index < groups.size(); index++) {
                final int selection = index;
                addRenderableWidget(Button.builder(Component.literal(groupLabel(groups.get(index))), button -> {
                    group = selection;
                    page = 0;
                    choosingGroup = false;
                    rebuildWidgets();
                }).bounds(left + (index % 3) * (choiceWidth + 4), FIELD_TOP + (index / 3) * ROW_SPACING, choiceWidth, FIELD_HEIGHT).build());
            }
        }
        for (int index = page * pageSize; !choosingGroup && index < Math.min(fields.size(), (page + 1) * pageSize); index++) {
            var setting = fields.get(index);
            int y = FIELD_TOP + (index - page * pageSize) * ROW_SPACING;
            rows.add(new Row(setting, y));
            String help = setting.label() + (setting.choices().isEmpty()
                    ? "\nRange: " + setting.min() + " to " + setting.max() : "\nClick to cycle; Shift-click to go backwards");
            if (setting.choices().isEmpty()) {
                EditBox field = new EditBox(font, editorX, y, editorWidth, FIELD_HEIGHT, Component.literal(setting.label()));
                field.setMaxLength(32);
                field.setValue(draft.get(setting.key()));
                field.setTooltip(Tooltip.create(Component.literal(help)));
                field.setResponder(value -> {
                    draft.put(setting.key(), value);
                    message = "";
                    try {
                        setting.validate(value);
                        field.setTextColor(0xFFE0E0E0);
                    } catch (IllegalArgumentException exception) {
                        field.setTextColor(0xFFFF7777);
                    }
                });
                addRenderableWidget(field);
            } else {
                addRenderableWidget(Button.builder(Component.literal(draft.get(setting.key())), button -> {
                    var choices = setting.choices();
                    int next = Math.floorMod(choices.indexOf(draft.get(setting.key())) + (minecraft.hasShiftDown() ? -1 : 1), choices.size());
                    draft.put(setting.key(), choices.get(next));
                    button.setMessage(Component.literal(choices.get(next)));
                    message = "";
                }).bounds(editorX, y, editorWidth, FIELD_HEIGHT).tooltip(Tooltip.create(Component.literal(help))).build());
            }
        }
        addRenderableWidget(Button.builder(Component.literal("< Page"), button -> {
            page = Math.floorMod(page - 1, pages);
            rebuildWidgets();
        }).bounds(left, pageY, 70, 20).build()).active = pages > 1 && !choosingGroup;
        addRenderableWidget(Button.builder(Component.literal("Page >"), button -> {
            page = (page + 1) % pages;
            rebuildWidgets();
        }).bounds(left + panelWidth - 70, pageY, 70, 20).build()).active = pages > 1 && !choosingGroup;
        addRenderableOnly((graphics, mouseX, mouseY, partialTick) -> graphics.centeredText(font,
                choosingGroup ? "Choose a section" : (page + 1) + " / " + pages, width / 2, pageY + 6, 0xFFFFFFFF));
        int buttonWidth = (panelWidth - 12) / 4;
        apply = addRenderableWidget(Button.builder(Component.literal("Apply & Test"), button -> save())
                .bounds(left, height - 27, buttonWidth, 20)
                .tooltip(Tooltip.create(Component.literal("Save to this blaster, refill ammo, clear heat and cooldowns, and return to testing"))).build());
        apply.active = !waiting;
        addRenderableWidget(Button.builder(Component.literal("Refill & Cool"), button -> send(SSDebugBlasterEditorPacket.REFILL, DebugBlasterSettings.DEFAULT))
                .bounds(left + buttonWidth + 4, height - 27, buttonWidth, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Defaults"), button -> {
            draft.clear();
            draft.putAll(DebugBlasterSettings.DEFAULT.values());
            message = "Defaults loaded. Apply & Test to save them.";
            rebuildWidgets();
        }).bounds(left + (buttonWidth + 4) * 2, height - 27, buttonWidth, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Cancel"), button -> onClose())
                .bounds(left + (buttonWidth + 4) * 3, height - 27, buttonWidth, 20).build());
    }

    private void changeGroup(int direction) {
        group = Math.floorMod(group + direction, groups.size());
        page = 0;
        choosingGroup = false;
        rebuildWidgets();
    }

    private static String groupLabel(String group) {
        return switch (group) {
            case "SEMI_AUTO" -> "Semi Auto";
            case "FULL_AUTO" -> "Full Auto";
            case "CHARGENSHOOT" -> "Charge & Fire";
            case "CHARGENSHOOTONRELEASE" -> "Charge & Release";
            default -> group.charAt(0) + group.substring(1).toLowerCase(java.util.Locale.ROOT);
        };
    }

    private void save() {
        try {
            var settings = new DebugBlasterSettings(draft);
            waiting = true;
            apply.active = false;
            message = "Applying...";
            send(SSDebugBlasterEditorPacket.APPLY, settings);
        } catch (IllegalArgumentException | IllegalStateException exception) {
            message = exception.getMessage();
        }
    }

    private void send(int action, DebugBlasterSettings settings) {
        PayloadRegister.sendToServer(new SSDebugBlasterEditorPacket(action, mainHand, sessionId, settings));
    }

    public void receive(String message, boolean close) {
        waiting = false;
        if (close) {
            onClose();
        } else {
            this.message = message;
            apply.active = true;
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        int panelWidth = Math.min(560, width - 24);
        int left = (width - panelWidth) / 2;
        graphics.centeredText(font, title, width / 2, 7, 0xFFFFFFFF);
        graphics.centeredText(font, (mainHand ? "Main hand" : "Off hand") + " | 20 ticks = 1 second | Edits save on this item", width / 2, 20, 0xFFAAAAAA);
        int labelWidth = panelWidth - Math.min(185, panelWidth / 2) - 10;
        for (Row row : rows) {
            graphics.text(font, font.plainSubstrByWidth(row.setting().label(), labelWidth), left, row.y() + 6, 0xFFFFFFFF);
        }
        var lines = font.split(Component.literal(message), panelWidth);
        for (int index = 0; index < Math.min(2, lines.size()); index++) {
            graphics.text(font, lines.get(index), left, height - 47 + index * 10, 0xFFFFCC77);
        }
    }

    @Override public boolean isPauseScreen() { return false; }
}
