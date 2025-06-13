package com.leobeliik.quark_ponders.ponder.scenes.automation;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.PotatoBlock;
import net.minecraft.world.level.block.StairBlock;

public class DispenserScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos button = util.grid().at(2, 2, 2);
        BlockPos dispenser = util.grid().at(2, 1, 2);

        //set base scene
        PonderAux.setAgentScene(scene, util, "minecraft_dispenser");;

        //Explain dispenser
        scene.overlay().showText(40)
                .text("quark_ender_watcher.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(dispenser.getCenter());
        scene.idle(60);

        //use the dispenser to place block
        scene.overlay().showControls(util.vector().topOf(button), Pointing.DOWN, 20).rightClick();
        scene.idle(5);
        scene.world().toggleRedstonePower(util.select().fromTo(button, button.below()));
        scene.world().setBlock(dispenser.west(), Blocks.OAK_PLANKS.defaultBlockState(), true);
        scene.idle(20);
        scene.world().toggleRedstonePower(util.select().fromTo(button, button.below()));
        scene.overlay().showText(60)
                .text("minecraft_dispenser.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(dispenser.west().getCenter());
        scene.idle(80);
        scene.world().setBlock(dispenser.west(), Blocks.AIR.defaultBlockState(), true);
        scene.idle(30);

        //use the dispenser to place faced block
        scene.overlay().showControls(util.vector().topOf(button), Pointing.DOWN, 20).rightClick();
        scene.idle(5);
        scene.world().toggleRedstonePower(util.select().fromTo(button, button.below()));
        scene.world().setBlock(dispenser.west(), Blocks.OAK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST), true);
        scene.idle(20);
        scene.world().toggleRedstonePower(util.select().fromTo(button, button.below()));
        scene.overlay().showText(40)
                .text("minecraft_dispenser.text_3")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(dispenser.west().getCenter());
        scene.idle(60);
        scene.world().setBlock(dispenser.west(), Blocks.AIR.defaultBlockState(), true);
        scene.idle(30);

        //Use the dispenser to plant
        scene.rotateCameraY(360);
        scene.idle(5);
        scene.world().setBlock(dispenser.west().below(), Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE, 5), false);
        scene.overlay().showText(40)
                .text("minecraft_dispenser.text_4")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(dispenser.getCenter());
        scene.idle(60);

        scene.overlay().showControls(util.vector().topOf(button), Pointing.DOWN, 20).rightClick();
        scene.idle(5);
        scene.world().toggleRedstonePower(util.select().fromTo(button, button.below()));
        scene.world().setBlock(dispenser.west(), Blocks.POTATOES.defaultBlockState().setValue(PotatoBlock.AGE, 0), true);
        scene.idle(20);
        scene.world().toggleRedstonePower(util.select().fromTo(button, button.below()));
        scene.markAsFinished();
    }
}
