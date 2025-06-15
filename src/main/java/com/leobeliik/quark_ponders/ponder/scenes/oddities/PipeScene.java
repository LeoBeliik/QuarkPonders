package com.leobeliik.quark_ponders.ponder.scenes.oddities;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.theme.Color;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class PipeScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos hopper = util.grid().at(2, 1, 0);
        BlockPos intersection = util.grid().at(2, 1, 4);
        ItemStack stone = Items.STONE.getDefaultInstance();

        PonderAux.setAgentScene(scene, util, "quark_pipe");
        scene.idle(10);

        //explain module
        scene.overlay().showText(60)
                .text("quark_pipe.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(3);
        scene.idle(80);

        scene.overlay().showText(80)
                .text("quark_pipe.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(hopper.getCenter());
        scene.idle(100);
        var item = scene.world().createItemEntity(hopper.getCenter().add(0, 3, 0), Vec3.ZERO, stone);
        scene.idle(12);
        scene.world().modifyEntity(item, Entity::discard);
        scene.idle(5);
        item = scene.world().createEntity(level ->
                PonderAux.staticItem(level, hopper.getCenter().add(0.4, -0.75, 0.4), 90, stone));
        scene.idle(5);
        PonderAux.entityMove(item, scene, 2, Direction.SOUTH);

        //intersection
        scene.overlay().showText(40)
                .text("quark_pipe.text_3")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(hopper.getCenter().add(0, 0, 2.25));
        scene.idle(60);
        PonderAux.entityMove(item, scene, 2, Direction.SOUTH);
        scene.world().modifyEntity(item, entity -> {
            entity.move(MoverType.SELF, new Vec3(0, 0, 0.25));
            entity.setPos(entity.position());
            entity.zo = entity.position().z;
        });

        scene.overlay().showText(40)
                .text("quark_pipe.text_4")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(intersection.getCenter().add(0, 0, 0.25));
        scene.idle(60);

        PonderAux.entityMove(item, scene, 2, new Random().nextInt() % 2 == 0 ? Direction.WEST : Direction.EAST);
        scene.idle(40);
        scene.world().modifyEntity(item, Entity::discard);

        //redstone
        scene.overlay().showText(60)
                .text("quark_pipe.text_5")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(40);
        scene.idle(80);

        scene.world().setBlock(util.grid().at(1, 0, 4), Blocks.REDSTONE_BLOCK.defaultBlockState(), true);
        scene.effects().emitParticles(util.grid().at(1, 1, 4).getCenter(), scene.effects()
                .particleEmitterWithinBlockSpace(new DustParticleOptions(new Color(0xFF0000).asVectorF(), 1), Vec3.ZERO), 0.5f, 190);
        scene.world().setBlock(util.grid().at(3, 0, 4), Blocks.REDSTONE_BLOCK.defaultBlockState(), true);
        scene.effects().emitParticles(util.grid().at(3, 1, 4).getCenter(), scene.effects()
                .particleEmitterWithinBlockSpace(new DustParticleOptions(new Color(0xFF0000).asVectorF(), 1), Vec3.ZERO), 0.5f, 190);

        //last place

        item = scene.world().createItemEntity(hopper.getCenter().add(0, 3, 0), Vec3.ZERO, stone);
        scene.idle(12);
        scene.world().modifyEntity(item, Entity::discard);
        scene.idle(5);
        item = scene.world().createEntity(level ->
                PonderAux.staticItem(level, hopper.getCenter().add(0.4, -0.75, 0.4), 90, stone));
        scene.idle(5);
        PonderAux.entityMove(item, scene, 4, Direction.SOUTH);

        scene.overlay().showText(40)
                .text("quark_pipe.text_6")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(intersection.getCenter());
        scene.idle(60);

        PonderAux.entityMove(item, scene, 2, Direction.UP);
        scene.world().modifyEntity(item, Entity::discard);

        scene.overlay().showText(40)
                .text("quark_pipe.text_7")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(5);
        scene.idle(60);

        //in and out
        scene.rotateCameraY(360);
        scene.idle(15);
        scene.world().setBlock(hopper.south(), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(intersection, Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(intersection.north(), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(intersection.west(), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(intersection.above(), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(intersection.above().above(), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(intersection.west(), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(intersection.west().west(), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(intersection.east(), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(intersection.east().east(), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(1, 1, 2), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(3, 1, 2), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(1, 0, 4), Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(3, 0, 4), Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState(), false);
        scene.world().modifyBlock(util.grid().at(2, 1, 2), state ->
                state.setValue(PipeBlock.EAST, false).setValue(PipeBlock.WEST, false).setValue(PipeBlock.UP, true).setValue(PipeBlock.SOUTH, false), false);

        scene.world().setBlock(hopper.south(), PonderAux.getBlock("pipe").defaultBlockState().setValue(PipeBlock.SOUTH, true).setValue(PipeBlock.NORTH, true), false);
        scene.world().setBlock(hopper, PonderAux.getBlock("pipe").defaultBlockState().setValue(PipeBlock.UP, true).setValue(PipeBlock.SOUTH, true), false);
        scene.world().setBlock(hopper.above(), PonderAux.getBlock("pipe").defaultBlockState().setValue(PipeBlock.DOWN, true), false);
        scene.world().setBlock(util.grid().at(2, 2, 2), PonderAux.getBlock("pipe").defaultBlockState().setValue(PipeBlock.DOWN, true), false);
        scene.idle(40);

        scene.overlay().showText(40)
                .text("quark_pipe.text_8")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(5);
        scene.idle(60);

        item = scene.world().createEntity(level ->
                PonderAux.staticItem(level, util.grid().at(2, 3, 0).getCenter().add(0.25, 0.55, 0.25), 90, stone));
        scene.idle(2);
        PonderAux.entityMove(item, scene, 3, Direction.DOWN);
        PonderAux.entityMove(item, scene, 2, Direction.SOUTH);
        PonderAux.entityMove(item, scene, 3, Direction.UP);
        scene.idle(2);
        PonderAux.entityMove(item, scene, 3, Direction.DOWN);
        PonderAux.entityMove(item, scene, 2, Direction.NORTH);
        PonderAux.entityMove(item, scene, 3, Direction.UP);
        scene.world().modifyEntity(item, Entity::discard);

        //comparator
        scene.idle(40);
        scene.world().showSection(util.select().position(0, 1, 2), Direction.DOWN);
        scene.idle(10);
        scene.world().setBlock(util.grid().at(1, 1, 2), Blocks.COMPARATOR.defaultBlockState().setValue(ComparatorBlock.FACING, Direction.EAST), true);
        scene.world().setBlock(util.grid().at(0, 1, 2), Blocks.REDSTONE_LAMP.defaultBlockState(), true);
        scene.idle(20);

        item = scene.world().createEntity(level ->
                PonderAux.staticItem(level, util.grid().at(2, 3, 0).getCenter().add(0.25, 0.55, 0.25), 90, stone));
        PonderAux.entityMove(item, scene, 3, Direction.DOWN);
        PonderAux.entityMove(item, scene, 2, Direction.SOUTH);
        scene.world().modifyBlock(util.grid().at(1, 1, 2), state -> state.setValue(ComparatorBlock.POWERED, true), false);
        scene.world().modifyBlock(util.grid().at(0, 1, 2), state -> state.setValue(RedstoneLampBlock.LIT, true), false);
        scene.overlay().showText(80)
                .text("quark_pipe.text_9")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.grid().at(1, 1, 2).getCenter());
        scene.idle(80);
        scene.world().modifyBlock(util.grid().at(1, 1, 2), state -> state.setValue(ComparatorBlock.POWERED, false), false);
        scene.world().modifyBlock(util.grid().at(0, 1, 2), state -> state.setValue(RedstoneLampBlock.LIT, false), false);
        PonderAux.entityMove(item, scene, 3, Direction.UP);
        scene.world().modifyEntity(item, Entity::discard);

        //TMI
        scene.idle(20);
        scene.overlay().showText(80)
                .text("quark_pipe.text_10")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(5);
        scene.idle(100);
        scene.markAsFinished();
    }

}
