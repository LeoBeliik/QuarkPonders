package com.leobeliik.quark_ponders.ponder.scenes.automation;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class PistonMoveTEScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos piston = util.grid().at(3, 1, 2);
        BlockPos chest = piston.west();
        BlockPos lamp = piston.above();
        BlockPos button = chest.above();

        PonderAux.setAgentScene(scene, util, "quark_piston_move_te");

        //explain the module
        scene.overlay().showText(80)
                .text("quark_piston_move_te.text_1")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(piston.getCenter());
        scene.idle(100);

        //show
        PonderAux.clickLampButton(scene, util, button, lamp, true, true);
        scene.idle(5);
        scene.world().modifyBlock(piston, state -> state.setValue(PistonBaseBlock.EXTENDED, true), false);
        scene.world().setBlock(piston.west(), Blocks.PISTON_HEAD.defaultBlockState().setValue(PistonHeadBlock.FACING, Direction.WEST), false);
        scene.world().setBlock(chest.west(), Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST), true);
        scene.idle(20);
        PonderAux.clickLampButton(scene, util, button, lamp, false, true);
        scene.world().modifyBlock(piston, state -> state.setValue(PistonBaseBlock.EXTENDED, false), false);
        scene.world().setBlock(piston.west(), Blocks.AIR.defaultBlockState(), false);
        scene.idle(40);
        scene.effects().emitParticles(piston.getCenter(), scene.effects().particleEmitterWithinBlockSpace(new DustParticleOptions(new Vector3f(0), 1), Vec3.ZERO), 150, 1);
        scene.idle(5);
        scene.world().setBlock(piston, Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.WEST), false);
        scene.idle(10);
        PonderAux.clickLampButton(scene, util, button, lamp, true, true);
        scene.idle(5);
        scene.world().modifyBlock(piston, state -> state.setValue(PistonBaseBlock.EXTENDED, true), false);
        scene.world().setBlock(piston.west(), Blocks.PISTON_HEAD.defaultBlockState()
                .setValue(PistonHeadBlock.FACING, Direction.WEST).setValue(PistonHeadBlock.TYPE, PistonType.STICKY), false);
        scene.idle(10);
        PonderAux.clickLampButton(scene, util, button, lamp, false, true);
        scene.world().modifyBlock(piston, state -> state.setValue(PistonBaseBlock.EXTENDED, false), false);
        scene.world().setBlock(chest, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST), false);
        scene.world().setBlock(chest.west(), Blocks.AIR.defaultBlockState(), false);
        scene.idle(40);

        //some BE can break
        scene.rotateCameraY(360);
        scene.idle(5);
        scene.world().setBlock(piston, Blocks.PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.WEST), false);
        scene.world().setBlock(piston.west().south(), Blocks.STONE.defaultBlockState(), false);
        scene.world().setBlock(piston.west(), Blocks.OAK_WALL_SIGN.defaultBlockState().setValue(WallSignBlock.FACING, Direction.NORTH), false);
        scene.idle(60);

        scene.overlay().showText(60)
                .text("quark_piston_move_te.text_2")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(piston.getCenter());
        scene.idle(80);
        PonderAux.clickLampButton(scene, util, button, lamp, true, true);
        scene.idle(5);
        scene.world().modifyBlock(piston, state -> state.setValue(PistonBaseBlock.EXTENDED, true), false);
        scene.world().setBlock(piston.west(), Blocks.PISTON_HEAD.defaultBlockState().setValue(PistonHeadBlock.FACING, Direction.WEST), false);
        var sign = scene.world().createItemEntity(piston.west().west().getCenter(), new Vec3(0, -0.2, 0), Items.OAK_SIGN.getDefaultInstance());
        scene.idle(20);
        PonderAux.clickLampButton(scene, util, button, lamp, false, true);
        scene.world().modifyBlock(piston, state -> state.setValue(PistonBaseBlock.EXTENDED, false), false);
        scene.world().setBlock(piston.west(), Blocks.AIR.defaultBlockState(), false);
        scene.idle(40);

        //what can't be moved
        scene.rotateCameraY(360);
        scene.idle(5);
        scene.world().modifyEntity(sign, Entity::discard);
        scene.world().setBlock(piston.west().south(), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(piston.west(), PonderAux.getBlock("sturdy_stone").defaultBlockState(), false);
        scene.idle(20);

        scene.overlay().showText(80)
                .text("quark_piston_move_te.text_3")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(piston.west().getCenter());
        scene.idle(100);
        PonderAux.clickLampButton(scene, util, button, lamp, true, true);
        scene.idle(25);
        PonderAux.clickLampButton(scene, util, button, lamp, false, true);
        scene.idle(40);
        PonderAux.setSmoke(scene, piston.west());
        scene.idle(5);
        scene.world().setBlock(piston.west(), Blocks.SPAWNER.defaultBlockState(), false);
        scene.overlay().showText(80)
                .text("quark_piston_move_te.text_4")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(piston.west().getCenter());
        scene.idle(100);
        PonderAux.clickLampButton(scene, util, button, lamp, true, true);
        scene.idle(25);
        PonderAux.clickLampButton(scene, util, button, lamp, false, true);
        scene.markAsFinished();
    }
}
