package com.leobeliik.quark_ponders.ponder.scenes.automation;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.phys.Vec3;
import org.violetmoon.quark.content.automation.block.IronRodBlock;

public class IronRodScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos ironRod = util.grid().at(2, 1, 2);
        BlockPos piston = ironRod.east();
        BlockPos lamp = piston.above();
        BlockPos button = lamp.west();

        PonderAux.setScene(scene, util, "quark_iron_rod");

        //set the agents
        scene.world().showIndependentSection(util.select().fromTo(2, 1, 2, 3, 3, 3), Direction.DOWN);
        scene.idle(25);

        //explain iron rod
        scene.overlay().showText(60)
                .text("quark_iron_rod.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(ironRod.getCenter().add(0.25, -0.5, 0.5));
        scene.idle(80);

        //show the rod working
        scene.world().setBlock(ironRod.west(), Blocks.STONE.defaultBlockState(), false);
        var stoneSelection = scene.world().showIndependentSection(util.select().position(ironRod.west()), Direction.DOWN);
        scene.idle(40);
        PonderAux.clickLampButton(scene, util, button, button.east(), true, true);
        scene.idle(5);
        scene.world().modifyBlock(piston, state -> state.setValue(PistonBaseBlock.EXTENDED, true), false);
        scene.world().setBlock(piston.west(), Blocks.PISTON_HEAD.defaultBlockState().setValue(PistonHeadBlock.FACING, Direction.WEST), false);
        scene.world().setBlock(ironRod.west(), PonderAux.getBlock("iron_rod").defaultBlockState().setValue(IronRodBlock.FACING, Direction.WEST), true);
        var stone = scene.world().createItemEntity(ironRod.west().getCenter(), new Vec3(0, -0.2, 0), Items.COBBLESTONE.getDefaultInstance());
        scene.idle(20);
        PonderAux.clickLampButton(scene, util, button, button.east(), false, true);
        scene.idle(5);
        scene.world().modifyBlock(piston, state -> state.setValue(PistonBaseBlock.EXTENDED, false), false);
        scene.world().setBlock(ironRod, PonderAux.getBlock("iron_rod").defaultBlockState().setValue(IronRodBlock.FACING, Direction.WEST), false);
        scene.world().setBlock(ironRod.west(), Blocks.AIR.defaultBlockState(), false);

        scene.overlay().showText(60)
                .text("quark_iron_rod.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(ironRod.west().getCenter().add(0.25, -0.5, 0.5));
        scene.idle(80);

        scene.world().modifyEntity(stone, Entity::discard);
        scene.world().hideIndependentSection(stoneSelection, Direction.UP);
        scene.idle(40);

        //they only break what they can push
        scene.world().setBlock(ironRod.west(), Blocks.OBSIDIAN.defaultBlockState(), false);
        scene.world().showIndependentSection(util.select().position(ironRod.west()), Direction.DOWN);
        scene.overlay().showText(60)
                .text("quark_iron_rod.text_3")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(ironRod.west().getCenter().add(0.25, -0.5, 0.5));
        scene.idle(80);

        PonderAux.clickLampButton(scene, util, button, button.east(), true, true);
        scene.idle(25);
        PonderAux.clickLampButton(scene, util, button, button.east(), false, true);
        scene.idle(20);
        scene.markAsFinished();
    }
}
