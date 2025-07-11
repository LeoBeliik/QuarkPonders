package com.leobeliik.quark_ponders.ponder;

import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3f;

import java.util.Objects;

public class PonderAux {

    /**
     * Simple quark block retriever, since quark loves to not have normal registration :P
     *
     * @param block string path for the block.
     * @return The quark block if avaliable, AIR if not.
     */
    public static Block getBlock(String block) {
        Block quarkBlock = ForgeRegistries.BLOCKS.getValue(ResourceLocation.fromNamespaceAndPath("quark", block));
        return quarkBlock == null ? Blocks.AIR : quarkBlock;
    }

    /**
     * Quick scene starter, sets header, base plate, and the agents.
     *
     * @param name name of the item to ponder.
     */
    public static void setScene(PonderSceneBuilder scene, SceneBuildingUtil util, String name) {
        scene.title(name, "header");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.world().showSection(util.select().position(5, 5, 5), Direction.DOWN);
        scene.idle(5);
    }

    /**
     * Same as #setScene but adds the whole plate as agents.
     */
    public static void setAgentScene(PonderSceneBuilder scene, SceneBuildingUtil util, String name) {
        setScene(scene, util, name);
        scene.world().showIndependentSection(util.select().fromTo(0, 1, 0, 5, 5, 5), Direction.DOWN);
        scene.idle(25);
    }

    /**
     * Quick entity generator with position and rotation.
     *
     * @param entity   EntityType for the entity to create.
     * @param pos      Position of the entity in the grid.
     * @param rotation Where entity is looking at.
     * @return Living entity.
     */
    public static Entity newEntity(EntityType entity, Level level, Vec3 pos, int rotation) {
        LivingEntity mob = (LivingEntity) entity.create(level);
        WalkAnimationState animation = Objects.requireNonNull(mob).walkAnimation;
        animation.update(-animation.position(), 1);
        animation.setSpeed(1);
        mob.setPos(pos);
        mob.xo = pos.x;
        mob.yo = pos.y;
        mob.zo = pos.z;
        mob.yRotO = rotation;
        mob.setYRot(rotation);
        mob.yHeadRotO = rotation;
        mob.yHeadRot = rotation;
        return mob;
    }


    /**
     * Creates a static item entity that doesn't rotate or bounce.
     * @param pos Position of the item.
     * @param rot Rotation of the item.
     * @param item The item itself.
     */
    public static Entity staticItem(Level level, Vec3 pos, int rot, ItemStack item) {
        ArmorStand stand = EntityType.ARMOR_STAND.create(level);
        SynchedEntityData dataManager = Objects.requireNonNull(stand).getEntityData();
        dataManager.set(ArmorStand.DATA_CLIENT_FLAGS, (byte)(0x01));
        stand.setPos(pos);
        stand.xo = pos.x;
        stand.yo = pos.y;
        stand.zo = pos.z;
        stand.setYRot(rot);
        stand.yRotO = rot;
        stand.setInvisible(true);
        stand.setItemSlot(EquipmentSlot.MAINHAND, item);
        stand.noPhysics = true;
        return stand;
    }

    /**
     *  Simple move entity in the grid.
     * @param entityElement Entity.
     * @param dist How many blocks the entity will walk
     * @param dir The direction in the grid the entity will move
     */
    public static void entityMove(ElementLink<EntityElement> entityElement, PonderSceneBuilder scene, int dist, Direction dir) {
        double x, y, z;
        if (dir == Direction.NORTH || dir == Direction.SOUTH) {
            x = 0;
            y = 0;
            z = dir == Direction.NORTH ? -0.2 : 0.2;
        } else if (dir == Direction.EAST || dir == Direction.WEST) {
            x = dir == Direction.EAST ? -0.2 : 0.2;
            y = 0;
            z = 0;
        } else {
            z = 0;
            y = dir == Direction.UP ? 0.2 : -0.2;
            x = 0;
        }

        for (int i = 0; i < dist * 5; i++) {
            scene.idle(1);
            scene.world().modifyEntity(entityElement, entity -> entity.move(MoverType.SELF, new Vec3(x, y, z)));
        }
        scene.world().modifyEntity(entityElement, e -> {
            e.setPos(e.position());
            e.xo = e.position().x;
            e.yo = e.position().y;
            e.zo = e.position().z;
        });
    }

    /**
     * Simple button press for Scene, with redstone lamp on/off optional.
     *
     * @param button         Position of the button.
     * @param lamp           Position of the lamp.
     * @param lit            If lamp should be lit or not.
     * @param pointFromAbove if right button indicator should be shown from above or below.
     */
    public static void clickLampButton(PonderSceneBuilder scene, SceneBuildingUtil util, BlockPos button, BlockPos lamp, boolean lit, boolean pointFromAbove) {
        if (lit)
            scene.overlay().showControls(button.getCenter().add(pointFromAbove ?
                    util.vector().of(0.75, -0.40, 0.5) : util.vector().of(0.75, -0.65, 0.5)), pointFromAbove ?
                    Pointing.DOWN : Pointing.UP, 25).rightClick();
        scene.world().toggleRedstonePower(util.select().position(button));
        for (int i = 0; i < 10; i++) {
            scene.world().modifyBlock(lamp, (s -> s.setValue(RedstoneLampBlock.LIT, lit)), false);
        }
    }

    /**
     * Same as #clickLampButton but without offset for the right click indicator.
     */
    public static void clickLampButtonTop(PonderSceneBuilder scene, SceneBuildingUtil util, BlockPos button, boolean lit, boolean pointFromAbove) {
        if (lit)
            scene.overlay().showControls(button.getCenter(), pointFromAbove ? Pointing.DOWN : Pointing.UP, 25).rightClick();
        scene.world().toggleRedstonePower(util.select().position(button));
        scene.world().modifyBlock(button.below(), (s -> s.setValue(RedstoneLampBlock.LIT, lit)), false);
    }

    /**
     * Spawns a "player" as armor stand fully clothed and wearing a player head.
     *
     * @param pos Spawn position for the armor stand.
     * @param rot Rotation for the armor stand.
     */
    public static Entity spawnPlayer(PonderSceneBuilder scene, Level level, Vec3 pos, int rot) {
        Entity armorStand = PonderAux.newEntity(EntityType.ARMOR_STAND, level, pos, rot);
        armorStand.setInvisible(true);
        ItemStack head = Items.PLAYER_HEAD.getDefaultInstance();
        CompoundTag tag = head.getOrCreateTag();
        ListTag enchantments = tag.getList("Enchantments", 10); // 10 = CompoundTag type
        CompoundTag bindingCurse = new CompoundTag();
        bindingCurse.putString("id", "minecraft:binding_curse");
        bindingCurse.putShort("lvl", (short) 1);
        enchantments.add(bindingCurse);
        tag.put("Enchantments", enchantments);
        head.setTag(tag);
        armorStand.setItemSlot(EquipmentSlot.HEAD, head);
        armorStand.setItemSlot(EquipmentSlot.CHEST, Items.IRON_CHESTPLATE.getDefaultInstance());
        armorStand.setItemSlot(EquipmentSlot.LEGS, Items.IRON_LEGGINGS.getDefaultInstance());
        armorStand.setItemSlot(EquipmentSlot.FEET, Items.IRON_BOOTS.getDefaultInstance());
        return armorStand;
    }

    /**
     * Set smoke particles at position.
     *
     * @param pos Block position for the particles.
     */
    public static void setSmoke(PonderSceneBuilder scene, BlockPos pos) {
        scene.effects().emitParticles(pos.getCenter(), scene.effects().particleEmitterWithinBlockSpace(new DustParticleOptions(new Vector3f(0), 1), Vec3.ZERO), 150, 1);
    }

}
