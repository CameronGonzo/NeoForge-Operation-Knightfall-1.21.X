package net.uhhitscam.knightfall.item.custom.grenade;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;
import net.uhhitscam.knightfall.component.GrenadeRemoteLink;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class GrenadeRemoteDetonations extends SavedData {
    private static final String DATA_NAME = "knightfall_remote_grenade_detonations";
    private static final String LINKS_TAG = "ActivatedLinks";
    private static final String DEPLOYED_COUNTS_TAG = "DeployedCounts";
    private static final String LINK_ID_TAG = "Id";
    private static final String COUNT_TAG = "Count";
    private static final net.minecraft.world.level.saveddata.SavedDataType<GrenadeRemoteDetonations> TYPE =
            new net.minecraft.world.level.saveddata.SavedDataType<>(
                    net.minecraft.resources.Identifier.fromNamespaceAndPath("knightfall", "remote_grenade_detonations"),
                    GrenadeRemoteDetonations::new,
                    CompoundTag.CODEC.xmap(GrenadeRemoteDetonations::load, data -> data.save(new CompoundTag())));

    private final Set<UUID> activatedLinks = new HashSet<>();
    private final Map<UUID, Integer> deployedCounts = new HashMap<>();

    private GrenadeRemoteDetonations() {
    }

    public static GrenadeRemoteDetonations get(MinecraftServer server) {
        var storage = server.overworld().getDataStorage();
        var existing = storage.get(TYPE);
        if (existing != null) return existing;
        var dataDirectory = server.getWorldPath(net.minecraft.world.level.storage.LevelResource.DATA);
        // Older worlds used a flat file name. Preserve linked charges when first opened in 26.2.
        for (var path : List.of(dataDirectory.resolve(DATA_NAME + ".dat"),
                dataDirectory.resolve("minecraft").resolve(DATA_NAME + ".dat"))) {
            if (!java.nio.file.Files.isRegularFile(path)) continue;
            try {
                var tag = net.minecraft.nbt.NbtIo.readCompressed(path, net.minecraft.nbt.NbtAccounter.unlimitedHeap());
                var migrated = load(tag.getCompoundOrEmpty("data"));
                storage.set(TYPE, migrated);
                migrated.setDirty();
                return migrated;
            } catch (java.io.IOException exception) {
                throw new IllegalStateException("Cannot migrate remote grenade links from " + path, exception);
            }
        }
        return storage.computeIfAbsent(TYPE);
    }

    public boolean isActivated(GrenadeRemoteLink link) {
        return activatedLinks.contains(link.id());
    }

    public static boolean isUnusedDetonator(MinecraftServer server, ItemStack stack) {
        GrenadeRemoteLink link = stack.get(ModDataComponentTypes.GRENADE_REMOTE_LINK.get());
        return link != null
                && isUnusedDetonator(stack, link)
                && get(server).isActivated(link);
    }

    public void registerDeployedCharge(GrenadeRemoteLink link) {
        deployedCounts.merge(link.id(), 1, Integer::sum);
        setDirty();
    }

    public void unregisterDeployedCharge(MinecraftServer server, GrenadeRemoteLink link) {
        int remainingCharges = deployedCounts.getOrDefault(link.id(), 1) - 1;
        if (remainingCharges > 0) {
            deployedCounts.put(link.id(), remainingCharges);
        } else {
            deployedCounts.remove(link.id());
            activatedLinks.add(link.id());
            unlinkStoredCharges(server, link);
            removeUnusedDetonators(server, link);
        }
        setDirty();
    }

    public static int activateAndDetonate(MinecraftServer server, GrenadeRemoteLink link) {
        GrenadeRemoteDetonations data = get(server);
        if (data.activatedLinks.add(link.id())) {
            data.setDirty();
        }

        List<GrenadeEntity> linkedGrenades = new ArrayList<>();
        for (ServerLevel level : server.getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof GrenadeEntity grenade
                        && link.equals(grenade.getItem().get(ModDataComponentTypes.GRENADE_REMOTE_LINK.get()))) {
                    linkedGrenades.add(grenade);
                }
            }
        }

        linkedGrenades.forEach(GrenadeEntity::activateRemoteDetonation);
        unlinkStoredCharges(server, link);
        return linkedGrenades.size();
    }

    private static void unlinkStoredCharges(MinecraftServer server, GrenadeRemoteLink link) {
        for (ServerLevel level : server.getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof Player player) {
                    unlinkChargesFromContainer(player.getInventory(), link);
                    unlinkChargesFromContainer(player.getEnderChestInventory(), link);
                } else if (entity instanceof ItemEntity itemEntity) {
                    ItemStack droppedStack = itemEntity.getItem();
                    if (unlinkCharge(droppedStack, link)) {
                        itemEntity.setItem(droppedStack.copy());
                    }
                } else if (entity instanceof ItemFrame itemFrame) {
                    ItemStack framedStack = itemFrame.getItem();
                    if (unlinkCharge(framedStack, link)) {
                        itemFrame.setItem(framedStack);
                    }
                } else if (entity instanceof Container container) {
                    unlinkChargesFromContainer(container, link);
                }
            }
        }
    }

    private static void unlinkChargesFromContainer(Container container, GrenadeRemoteLink link) {
        boolean changed = false;
        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);
            if (unlinkCharge(stack, link)) {
                container.setItem(slot, stack);
                changed = true;
            }
        }
        if (changed) {
            container.setChanged();
        }
    }

    private static boolean unlinkCharge(ItemStack stack, GrenadeRemoteLink link) {
        if (!(stack.getItem() instanceof GrenadeItem)
                || !link.equals(stack.get(ModDataComponentTypes.GRENADE_REMOTE_LINK.get()))) {
            return false;
        }

        stack.remove(ModDataComponentTypes.GRENADE_REMOTE_LINK.get());
        return true;
    }

    private static void removeUnusedDetonators(MinecraftServer server, GrenadeRemoteLink link) {
        List<ItemEntity> droppedDetonators = new ArrayList<>();
        for (ServerLevel level : server.getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof Player player) {
                    removeFromContainer(player.getInventory(), link);
                    removeFromContainer(player.getEnderChestInventory(), link);
                } else if (entity instanceof ItemEntity itemEntity
                        && isUnusedDetonator(itemEntity.getItem(), link)) {
                    droppedDetonators.add(itemEntity);
                } else if (entity instanceof ItemFrame itemFrame
                        && isUnusedDetonator(itemFrame.getItem(), link)) {
                    itemFrame.setItem(ItemStack.EMPTY);
                } else if (entity instanceof Container container) {
                    removeFromContainer(container, link);
                }
            }
        }
        droppedDetonators.forEach(ItemEntity::discard);
    }

    private static void removeFromContainer(Container container, GrenadeRemoteLink link) {
        boolean changed = false;
        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);
            if (isUnusedDetonator(stack, link)) {
                container.setItem(slot, ItemStack.EMPTY);
                changed = true;
            }
        }
        if (changed) {
            container.setChanged();
        }
    }

    private static boolean isUnusedDetonator(ItemStack stack, GrenadeRemoteLink link) {
        return stack.getItem() instanceof GrenadeDetonatorItem
                && link.equals(stack.get(ModDataComponentTypes.GRENADE_REMOTE_LINK.get()))
                && !stack.has(ModDataComponentTypes.REMOTE_DETONATOR_STATE.get());
    }

    private static GrenadeRemoteDetonations load(CompoundTag tag) {
        GrenadeRemoteDetonations data = new GrenadeRemoteDetonations();
        ListTag links = tag.getListOrEmpty(LINKS_TAG);
        for (int index = 0; index < links.size(); index++) {
            CompoundTag linkTag = links.getCompoundOrEmpty(index);
            if (linkTag.read(LINK_ID_TAG, net.minecraft.core.UUIDUtil.CODEC).isPresent()) {
                data.activatedLinks.add(linkTag.read(LINK_ID_TAG, net.minecraft.core.UUIDUtil.CODEC).orElseThrow());
            }
        }

        ListTag deployedCounts = tag.getListOrEmpty(DEPLOYED_COUNTS_TAG);
        for (int index = 0; index < deployedCounts.size(); index++) {
            CompoundTag countTag = deployedCounts.getCompoundOrEmpty(index);
            if (countTag.read(LINK_ID_TAG, net.minecraft.core.UUIDUtil.CODEC).isPresent()) {
                int count = countTag.getIntOr(COUNT_TAG, 0);
                if (count > 0) {
                    data.deployedCounts.put(countTag.read(LINK_ID_TAG, net.minecraft.core.UUIDUtil.CODEC).orElseThrow(), count);
                }
            }
        }
        return data;
    }

    private CompoundTag save(CompoundTag tag) {
        ListTag links = new ListTag();
        for (UUID linkId : activatedLinks) {
            CompoundTag linkTag = new CompoundTag();
            linkTag.store(LINK_ID_TAG, net.minecraft.core.UUIDUtil.CODEC, linkId);
            links.add(linkTag);
        }
        tag.put(LINKS_TAG, links);

        ListTag deployedCounts = new ListTag();
        for (Map.Entry<UUID, Integer> entry : this.deployedCounts.entrySet()) {
            CompoundTag countTag = new CompoundTag();
            countTag.store(LINK_ID_TAG, net.minecraft.core.UUIDUtil.CODEC, entry.getKey());
            countTag.putInt(COUNT_TAG, entry.getValue());
            deployedCounts.add(countTag);
        }
        tag.put(DEPLOYED_COUNTS_TAG, deployedCounts);
        return tag;
    }
}
