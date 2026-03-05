package com.leobeliik.quark_ponders.ponder.scenes.oddities;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.Vec3;
import org.violetmoon.quark.addons.oddities.block.MagnetBlock;
import org.violetmoon.quark.addons.oddities.module.MagnetsModule;
import org.violetmoon.quark.base.Quark;
import org.violetmoon.quark.content.automation.block.IronRodBlock;
import org.violetmoon.quark.content.automation.module.IronRodModule;

public class MagnetScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos magnet = util.grid().at(2, 1, 2);
        BlockPos lamp = magnet.above();
        BlockPos lever = lamp.above();

        PonderAux.setAgentScene(scene, util, "quark_magnet");
        scene.idle(10);

        //explain module
        scene.overlay().showText(80)
                .text("quark_magnet.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(magnet.getCenter());
        scene.idle(100);

        //pull and push
        PonderAux.clickLampButtonTop(scene, util, lever, true, true);
        scene.idle(5);
        scene.world().modifyBlock(magnet, state -> state.setValue(MagnetBlock.POWERED, true), false);
        scene.world().setBlock(util.grid().at(2, 1, 0), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(2, 1, 1), Blocks.IRON_BLOCK.defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(2, 1, 3), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(2, 1, 4), Blocks.COPPER_BLOCK.defaultBlockState(), false);
        PonderAux.clickLampButtonTop(scene, util, lever, false, true);
        scene.idle(5);
        scene.world().modifyBlock(magnet, state -> state.setValue(MagnetBlock.POWERED, false), false);
        scene.idle(20);

        //and items
        scene.world().setBlock(util.grid().at(2, 1, 1), Blocks.AIR.defaultBlockState(), true);
        scene.world().setBlock(util.grid().at(2, 1, 4), Blocks.AIR.defaultBlockState(), true);

        scene.overlay().showText(80)
                .text("quark_magnet.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(magnet.getCenter());
        scene.idle(100);

        scene.world().createItemEntity(magnet.north().north().getCenter(), Vec3.ZERO, Items.COPPER_GRATE.getDefaultInstance());
        scene.world().createItemEntity(magnet.south().getCenter(), Vec3.ZERO, Items.HOPPER.getDefaultInstance());
        scene.idle(40);

        PonderAux.clickLampButtonTop(scene, util, lever, true, true);
        scene.idle(3);
        scene.world().modifyBlock(magnet, state -> state.setValue(MagnetBlock.POWERED, true), false);
        scene.world().modifyEntities(ItemEntity.class, itemEntity -> itemEntity.addDeltaMovement(new Vec3(0, 0, 0.45)));
        scene.idle(20);
        PonderAux.clickLampButtonTop(scene, util, lever, false, true);
        scene.idle(3);
        scene.world().modifyBlock(magnet, state -> state.setValue(MagnetBlock.POWERED, false), false);
        scene.idle(40);
        scene.world().modifyEntities(ItemEntity.class, Entity::discard);

        //red push, blue pulls
        scene.idle(10);
        scene.overlay().showText(60)
                .text("quark_magnet.text_3")
                .colored(PonderPalette.RED)
                .placeNearTarget()
                .independent(50);
        scene.overlay().showText(60)
                .text("quark_magnet.text_4")
                .colored(PonderPalette.MEDIUM) //Medium is bluer than Blue
                .placeNearTarget()
                .independent(70);
        scene.idle(80);

        //magnet force
        PonderAux.transition(scene);
        scene.world().setBlock(magnet, Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(lamp, Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(lever, Blocks.AIR.defaultBlockState(), false);
        BlockPos m1 = util.grid().at(0, 1, 2);
        BlockPos m2 = util.grid().at(4, 1, 2);
        scene.world().setBlock(m1, PonderAux.getBlock("magnet").defaultBlockState().setValue(MagnetBlock.FACING, Direction.EAST), false);
        scene.world().setBlock(m2, PonderAux.getBlock("magnet").defaultBlockState().setValue(MagnetBlock.FACING, Direction.WEST), false);
        scene.world().setBlock(m1.above(), Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.FLOOR), false);
        scene.world().setBlock(m2.above(), Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.FLOOR), false);
        BlockPos l1 = util.grid().at(1, 1, 4);
        BlockPos l2 = util.grid().at(3, 1, 0);
        for (int x = 0; x < 5; x++) {
            if (x == 0) {
                scene.world().setBlock(util.grid().at(x, 1, 0), Blocks.REDSTONE_WIRE.defaultBlockState()
                        .setValue(RedStoneWireBlock.EAST, RedstoneSide.SIDE)
                        .setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE), false);
                scene.world().setBlock(util.grid().at(x, 1, 4), Blocks.REDSTONE_WIRE.defaultBlockState()
                        .setValue(RedStoneWireBlock.EAST, RedstoneSide.SIDE)
                        .setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE), false);
                continue;
            }
            if (x == 4) {
                scene.world().setBlock(util.grid().at(x, 1, 0), Blocks.REDSTONE_WIRE.defaultBlockState()
                        .setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE)
                        .setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE), false);
                scene.world().setBlock(util.grid().at(x, 1, 4), Blocks.REDSTONE_WIRE.defaultBlockState()
                        .setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE)
                        .setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE), false);
                continue;
            }

            scene.world().setBlock(util.grid().at(x, 1, 0), Blocks.REDSTONE_WIRE.defaultBlockState()
                    .setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE)
                    .setValue(RedStoneWireBlock.EAST, RedstoneSide.SIDE), false);
            scene.world().setBlock(util.grid().at(x, 1, 4), Blocks.REDSTONE_WIRE.defaultBlockState()
                    .setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE)
                    .setValue(RedStoneWireBlock.EAST, RedstoneSide.SIDE), false);
        }

        scene.world().setBlock(l1, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.FLOOR), false);
        scene.world().setBlock(l2, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.FLOOR), false);
        scene.world().setBlock(util.grid().at(0, 1, 1), Blocks.REDSTONE_WIRE.defaultBlockState()
                .setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE)
                .setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE), false);
        scene.world().setBlock(util.grid().at(0, 1, 3), Blocks.REDSTONE_WIRE.defaultBlockState()
                .setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE)
                .setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE), false);
        scene.world().setBlock(util.grid().at(4, 1, 1), Blocks.REDSTONE_WIRE.defaultBlockState()
                .setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE)
                .setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE), false);
        scene.world().setBlock(util.grid().at(4, 1, 3), Blocks.REDSTONE_WIRE.defaultBlockState()
                .setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE)
                .setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE), false);
        scene.world().setBlock(magnet, Blocks.COPPER_BLOCK.defaultBlockState(), false);
        scene.idle(40);

        scene.overlay().showText(80)
                .text("quark_magnet.text_5")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(70);
        scene.idle(100);

        scene.overlay().showControls(m1.above().getCenter(), Pointing.DOWN, 20).rightClick();
        scene.overlay().showControls(m2.above().getCenter(), Pointing.DOWN, 20).rightClick();
        scene.idle(10);
        scene.world().toggleRedstonePower(util.select().fromTo(m1, m1.above()));
        scene.world().toggleRedstonePower(util.select().fromTo(m2, m2.above()));
        scene.idle(40);
        scene.overlay().showControls(m1.above().getCenter(), Pointing.DOWN, 20).rightClick();
        scene.overlay().showControls(m2.above().getCenter(), Pointing.DOWN, 20).rightClick();
        scene.idle(10);
        scene.world().toggleRedstonePower(util.select().fromTo(m1, m1.above()));
        scene.world().toggleRedstonePower(util.select().fromTo(m2, m2.above()));
        scene.idle(40);
        scene.overlay().showText(60)
                .text("quark_magnet.text_6")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(70);
        scene.idle(80);

        scene.overlay().showControls(l1.getCenter(), Pointing.DOWN, 20).rightClick();
        scene.idle(5);
        scene.world().toggleRedstonePower(util.select().fromTo(0, 1, 2, 5, 1, 4));
        for (int i = 0; i < 10; i++) {
            scene.idle(5);
            if (i % 2 == 0) {
                scene.world().setBlock(m2.west().west(), Blocks.AIR.defaultBlockState(), false);
                scene.world().setBlock(m2.west(), Blocks.COPPER_BLOCK.defaultBlockState(), false);
            } else {
                scene.world().setBlock(m2.west(), Blocks.AIR.defaultBlockState(), false);
                scene.world().setBlock(m2.west().west(), Blocks.COPPER_BLOCK.defaultBlockState(), false);
            }
        }
        scene.overlay().showControls(l1.getCenter(), Pointing.DOWN, 20).rightClick();
        scene.idle(5);
        scene.world().toggleRedstonePower(util.select().fromTo(0, 1, 2, 5, 1, 4));

        scene.idle(40);
        scene.overlay().showControls(l2.getCenter(), Pointing.DOWN, 20).rightClick();
        scene.idle(5);
        scene.world().toggleRedstonePower(util.select().fromTo(0, 1, 0, 5, 1, 2));
        for (int i = 0; i < 10; i++) {
            scene.idle(5);
            if (i % 2 == 0) {
                scene.world().setBlock(m1.east().east(), Blocks.AIR.defaultBlockState(), false);
                scene.world().setBlock(m1.east(), Blocks.COPPER_BLOCK.defaultBlockState(), false);
            } else {
                scene.world().setBlock(m1.east(), Blocks.AIR.defaultBlockState(), false);
                scene.world().setBlock(m1.east().east(), Blocks.COPPER_BLOCK.defaultBlockState(), false);
            }
        }
        scene.overlay().showControls(l2.getCenter(), Pointing.DOWN, 20).rightClick();
        scene.idle(5);
        scene.world().toggleRedstonePower(util.select().fromTo(0, 1, 0, 5, 1, 2));
        scene.idle(40);

        PonderAux.transition(scene);
        scene.world().setBlocks(util.select().fromTo(0, 1, 0, 5, 2, 5), Blocks.AIR.defaultBlockState(), false);
        magnet = util.grid().at(2, 0, 2);
        lamp = util.grid().at(1, 0, 2);
        lever = lamp.above();
        scene.world().setBlock(magnet, PonderAux.getBlock("magnet").defaultBlockState().setValue(MagnetBlock.FACING, Direction.UP), false);
        scene.world().setBlock(lamp, Blocks.REDSTONE_LAMP.defaultBlockState(), false);
        scene.world().setBlock(lever, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.FLOOR), false);


        var golem = scene.world().createEntity(l -> PonderAux.newEntity(EntityType.IRON_GOLEM, l, new Vec3(2.5, 1, 2.5), 90));

        scene.idle(40);
        scene.overlay().showText(60)
                .text("quark_magnet.text_7")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(magnet.above(2).getCenter());
        scene.idle(80);

        scene.overlay().showControls(lever.getCenter(), Pointing.DOWN, 20).rightClick();
        scene.world().toggleRedstonePower(util.select().fromTo(magnet, lever));
        scene.idle(5);
        bounceEntity(scene, util, magnet, lever, golem);

        scene.idle(30);

        for (int i = 0; i < 3; i++) {
            PonderAux.setSmoke(scene, magnet.above(i));
        }

        scene.world().modifyEntity(golem, Entity::discard);
        golem = scene.world().createEntity(l -> PonderAux.spawnPlayer(scene, l, new Vec3(2.5, 1, 2.5), 90));
        scene.idle(20);

        scene.overlay().showControls(lever.getCenter(), Pointing.DOWN, 20).rightClick();
        scene.world().toggleRedstonePower(util.select().fromTo(magnet, lever));
        scene.idle(5);
        scene.idle(30);

        var chestplate = Items.IRON_CHESTPLATE.getDefaultInstance();
        scene.overlay().showControls(magnet.above(3).getCenter(), Pointing.DOWN, 20).rightClick().withItem(chestplate);
        scene.idle(15);
        scene.world().modifyEntity(golem, e -> ((ArmorStand) e).setItemSlot(EquipmentSlot.CHEST, chestplate));
        bounceEntity(scene, util, magnet, lever, golem);

        scene.idle(20);

        //hoppers
        PonderAux.transition(scene);
        scene.world().modifyEntity(golem, Entity::discard);
        scene.world().setBlocks(util.select().fromTo(0, 1, 0, 5, 2, 5), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(magnet, Blocks.WHITE_CONCRETE.defaultBlockState(), false);
        scene.world().setBlock(lamp, Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState(), false);
        magnet = util.grid().at(4, 1, 0);
        lamp = magnet.above();
        lever = lamp.above();

        BlockPos hopper = util.grid().at(4, 1, 1);
        BlockPos farm = util.grid().at(3, 0, 3);
        BlockState hopperBlock = Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, Direction.WEST);
        scene.world().setBlock(magnet, PonderAux.getBlock("magnet").defaultBlockState().setValue(MagnetBlock.FACING, Direction.SOUTH), false);
        scene.world().setBlock(lamp, Blocks.REDSTONE_LAMP.defaultBlockState(), false);
        scene.world().setBlock(lever, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.FLOOR), false);
        scene.world().setBlock(hopper, hopperBlock, false);
        scene.world().setBlock(farm, Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE, 7), false);
        scene.idle(40);

        scene.overlay().showText(80)
                .text("quark_magnet.text_7")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(hopper.getCenter());
        scene.idle(100);

        scene.overlay().showControls(lever.getCenter(), Pointing.DOWN, 20).rightClick();
        scene.world().toggleRedstonePower(util.select().fromTo(magnet, lever));

        for (int i = 1; i < 4; i++) {
            scene.idle(5);
            scene.world().setBlock(magnet.south(i), Blocks.AIR.defaultBlockState(), false);
            scene.world().setBlock(magnet.south(i + 1), hopperBlock, false);
            if (i != 2)
                scene.world().createItemEntity(magnet.south(i + 1).west().getCenter(), Vec3.ZERO, Items.WHEAT_SEEDS.getDefaultInstance());
            else {
                scene.world().setBlock(magnet.south(i + 1).west(), Blocks.WHEAT.defaultBlockState(), false);

                scene.overlay().showText(80)
                        .text("quark_magnet.text_8")
                        .placeNearTarget()
                        .pointAt(magnet.south(i + 1).west().getCenter());
                scene.idle(60);
            }
        }
        scene.overlay().showControls(lever.getCenter(), Pointing.DOWN, 20).rightClick();
        scene.world().toggleRedstonePower(util.select().fromTo(magnet, lever));
        scene.idle(40);

        //stonecutter
        PonderAux.transition(scene);
        BlockState stoneCutter = Blocks.STONECUTTER.defaultBlockState().setValue(StonecutterBlock.FACING, Direction.WEST);
        scene.world().setBlock(hopper, stoneCutter, false);
        scene.world().setBlock(farm, Blocks.WHITE_CONCRETE.defaultBlockState(), false);
        scene.world().setBlock(farm.above(), Blocks.AIR.defaultBlockState(), false);
        scene.world().modifyEntities(ItemEntity.class, Entity::discard);
        scene.world().setBlock(util.grid().at(4, 1, 4), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(4, 2, 2), Blocks.STONE.defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(4, 2, 3), Blocks.OBSIDIAN.defaultBlockState(), false);
        scene.idle(40);

        boolean doesSilk = MagnetsModule.stoneCutterSilkTouch;

        scene.overlay().showText(80)
                .text("quark_magnet.text_9", doesSilk ? Component.translatable("quark_ponders.ponder.quark_magnet.silk").getString() : ".")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(hopper.getCenter());
        scene.idle(100);

        scene.overlay().showControls(lever.getCenter(), Pointing.DOWN, 20).rightClick();
        scene.world().toggleRedstonePower(util.select().fromTo(magnet, lever));

        for (int i = 1; i < 4; i++) {
            scene.idle(5);
            scene.world().setBlock(magnet.south(i), Blocks.AIR.defaultBlockState(), false);
            scene.world().setBlock(magnet.south(i + 1), stoneCutter, false);
            if (i == 1) {
                scene.world().setBlock(magnet.south(i + 1).above(), Blocks.AIR.defaultBlockState(), false);
                scene.world().createItemEntity(magnet.south(i + 1).above().getCenter(), Vec3.ZERO, doesSilk ? Items.STONE.getDefaultInstance() : Items.COBBLESTONE.getDefaultInstance());
            }
            if (i == 2) {
                scene.overlay().showText(80)
                        .text("quark_magnet.text_10")
                        .placeNearTarget()
                        .pointAt(magnet.south(i + 1).above().getCenter());
                scene.idle(60);
            }
        }
        scene.overlay().showControls(lever.getCenter(), Pointing.DOWN, 20).rightClick();
        scene.world().toggleRedstonePower(util.select().fromTo(magnet, lever));


        scene.idle(20);

        //Iron rod
        if (Quark.ZETA.modules.isEnabled(IronRodModule.class)) {
            PonderAux.transition(scene);
            scene.world().setBlock(util.grid().at(4, 2, 3), Blocks.AIR.defaultBlockState(), false);
            BlockState ironRod = IronRodModule.iron_rod.defaultBlockState().setValue(IronRodBlock.FACING, Direction.SOUTH);
            scene.world().setBlock(hopper, ironRod, false);
            scene.world().setBlock(farm, Blocks.WHITE_CONCRETE.defaultBlockState(), false);
            scene.world().setBlock(farm.above(), Blocks.AIR.defaultBlockState(), false);
            scene.world().modifyEntities(ItemEntity.class, Entity::discard);
            scene.world().setBlock(util.grid().at(4, 1, 3), Blocks.STONE.defaultBlockState(), false);
            scene.world().setBlock(util.grid().at(4, 1, 4), Blocks.OBSIDIAN.defaultBlockState(), false);
            scene.idle(40);


            scene.overlay().showText(80)
                    .text("quark_magnet.text_11")
                    .attachKeyFrame()
                    .placeNearTarget()
                    .pointAt(hopper.getCenter());
            scene.idle(100);

            scene.overlay().showControls(lever.getCenter(), Pointing.DOWN, 20).rightClick();
            scene.world().toggleRedstonePower(util.select().fromTo(magnet, lever));

            for (int i = 1; i < 3; i++) {
                scene.idle(5);
                scene.world().setBlock(magnet.south(i), Blocks.AIR.defaultBlockState(), false);
                scene.world().setBlock(magnet.south(i + 1), ironRod, false);
                if (i == 1) {
                    scene.world().setBlock(magnet.south(i + 1).above(), Blocks.AIR.defaultBlockState(), false);
                    scene.world().createItemEntity(magnet.south(i + 1).above().getCenter(), Vec3.ZERO, Items.COBBLESTONE.getDefaultInstance());
                }
                if (i == 2) {
                    scene.overlay().showText(80)
                            .text("quark_magnet.text_12")
                            .placeNearTarget()
                            .pointAt(magnet.south(i + 2).getCenter());
                    scene.idle(60);
                }
            }
            scene.overlay().showControls(lever.getCenter(), Pointing.DOWN, 20).rightClick();
            scene.world().toggleRedstonePower(util.select().fromTo(magnet, lever));
        }
        scene.markAsFinished();
    }

    private static void bounceEntity(PonderSceneBuilder scene, SceneBuildingUtil util, BlockPos magnet, BlockPos lever, ElementLink<EntityElement> golem) {
        PonderAux.entityMove(golem, scene, 1, Direction.UP);

        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0)
                scene.world().modifyEntity(golem, entity -> entity.move(MoverType.SELF, new Vec3(0, 0.25, 0)));
            else
                scene.world().modifyEntity(golem, entity -> entity.move(MoverType.SELF, new Vec3(0, -0.25, 0)));
            scene.idle(6);
        }

        scene.overlay().showControls(lever.getCenter(), Pointing.DOWN, 20).rightClick();
        scene.world().toggleRedstonePower(util.select().fromTo(magnet, lever));
        PonderAux.entityMove(golem, scene, 2, Direction.DOWN);
    }
}
