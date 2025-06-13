package com.leobeliik.quark_ponders.ponder;

import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
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
        scene.world().modifyBlock(lamp, (s -> s.setValue(RedstoneLampBlock.LIT, lit)), false);
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
        //TODO look how quark render player models on armor stands
        Entity armorStand = PonderAux.newEntity(EntityType.ARMOR_STAND, level, pos, rot);
        armorStand.setInvisible(true);
        armorStand.setItemSlot(EquipmentSlot.HEAD, Items.PLAYER_HEAD.getDefaultInstance());
        armorStand.setItemSlot(EquipmentSlot.CHEST, Items.IRON_CHESTPLATE.getDefaultInstance());
        armorStand.setItemSlot(EquipmentSlot.LEGS, Items.IRON_LEGGINGS.getDefaultInstance());
        armorStand.setItemSlot(EquipmentSlot.FEET, Items.IRON_BOOTS.getDefaultInstance());
        return armorStand;
    }

}
