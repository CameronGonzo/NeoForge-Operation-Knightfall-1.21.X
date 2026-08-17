package net.uhhitscam.knightfall.item.custom;

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
    private static final Factory<GrenadeRemoteDetonations> FACTORY = new Factory<>(
            GrenadeRemoteDetonations::new,
            GrenadeRemoteDetonations::load
    );

    private final Set<UUID> activatedLinks = new HashSet<>();
    private final Map<UUID, Integer> deployedCounts = new HashMap<>();

    private GrenadeRemoteDetonations() {
    }

    public static GrenadeRemoteDetonations get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(FACTORY, DATA_NAME);
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

    private static GrenadeRemoteDetonations load(CompoundTag tag, HolderLookup.Provider provider) {
        GrenadeRemoteDetonations data = new GrenadeRemoteDetonations();
        ListTag links = tag.getList(LINKS_TAG, Tag.TAG_COMPOUND);
        for (int index = 0; index < links.size(); index++) {
            CompoundTag linkTag = links.getCompound(index);
            if (linkTag.hasUUID(LINK_ID_TAG)) {
                data.activatedLinks.add(linkTag.getUUID(LINK_ID_TAG));
            }
        }

        ListTag deployedCounts = tag.getList(DEPLOYED_COUNTS_TAG, Tag.TAG_COMPOUND);
        for (int index = 0; index < deployedCounts.size(); index++) {
            CompoundTag countTag = deployedCounts.getCompound(index);
            if (countTag.hasUUID(LINK_ID_TAG)) {
                int count = countTag.getInt(COUNT_TAG);
                if (count > 0) {
                    data.deployedCounts.put(countTag.getUUID(LINK_ID_TAG), count);
                }
            }
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        ListTag links = new ListTag();
        for (UUID linkId : activatedLinks) {
            CompoundTag linkTag = new CompoundTag();
            linkTag.putUUID(LINK_ID_TAG, linkId);
            links.add(linkTag);
        }
        tag.put(LINKS_TAG, links);

        ListTag deployedCounts = new ListTag();
        for (Map.Entry<UUID, Integer> entry : this.deployedCounts.entrySet()) {
            CompoundTag countTag = new CompoundTag();
            countTag.putUUID(LINK_ID_TAG, entry.getKey());
            countTag.putInt(COUNT_TAG, entry.getValue());
            deployedCounts.add(countTag);
        }
        tag.put(DEPLOYED_COUNTS_TAG, deployedCounts);
        return tag;
    }
}
