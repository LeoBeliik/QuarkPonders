package com.leobeliik.quark_ponders.ponder.scenes.building;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;

public class RopeScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos rope = util.grid().at(2, 3, 2);
        BlockPos dispenser = rope.south();
        BlockPos lamp = dispenser.above();
        BlockPos button = lamp.west();


        //start screen
        PonderAux.setAgentScene(scene, util, "quark_rope");
        scene.idle(20);

        //explain rope
        scene.overlay().showText(60)
                .text("quark_rope.text_1")
                .independent(20)
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(80);

        scene.overlay().showControls(rope.getCenter(), Pointing.UP, 20).withItem(PonderAux.getBlock("rope").asItem().getDefaultInstance());
        scene.idle(15);
        scene.world().setBlock(rope, PonderAux.getBlock("rope").defaultBlockState(), false);
        scene.idle(40);
        PonderAux.setSmoke(scene, rope);
        scene.world().setBlock(rope, Blocks.AIR.defaultBlockState(), false);
        scene.idle(20);
        PonderAux.clickLampButton(scene, util, button, lamp, true, true);
        scene.idle(5);
        scene.world().setBlock(rope, PonderAux.getBlock("rope").defaultBlockState(), false);
        scene.idle(20);
        PonderAux.clickLampButton(scene, util, button, lamp, false, true);
        scene.idle(40);

        //extend
        scene.overlay().showText(60)
                .text("quark_rope.text_2")
                .independent(20)
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(90);

        scene.overlay().showControls(rope.getCenter(), Pointing.DOWN, 20).withItem(PonderAux.getBlock("rope").asItem().getDefaultInstance());
        scene.idle(15);
        scene.world().setBlock(rope.below(), PonderAux.getBlock("rope").defaultBlockState(), false);
        scene.idle(40);
        PonderAux.clickLampButton(scene, util, button, lamp, true, true);
        scene.idle(5);
        scene.world().setBlock(rope.below().below(), PonderAux.getBlock("rope").defaultBlockState(), false);
        scene.idle(20);
        PonderAux.clickLampButton(scene, util, button, lamp, false, true);
        scene.idle(40);

        //pull up
        scene.overlay().showText(60)
                .text("quark_rope.text_3")
                .independent(20)
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(80);
        BlockPos below = rope.below().below().below(); //I should be sue for using so many below()
        PonderAux.setSmoke(scene, below);
        scene.world().setBlock(below, Blocks.BARREL.defaultBlockState(), false);
        scene.idle(20);
        scene.overlay().showControls(rope.getCenter(), Pointing.DOWN, 20).rightClick();
        scene.idle(15);
        scene.world().setBlock(below, Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(below.above(), Blocks.BARREL.defaultBlockState(), false);
        scene.overlay().showText(60)
                .text("quark_rope.text_4")
                .independent(20)
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(80);

        //block pull and push
        scene.overlay().showText(80)
                .text("quark_rope.text_5")
                .pointAt(below.above().getCenter())
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(100);
        scene.overlay().showControls(rope.getCenter(), Pointing.DOWN, 20).withItem(PonderAux.getBlock("rope").asItem().getDefaultInstance());
        scene.idle(15);
        scene.world().setBlock(below.above(), PonderAux.getBlock("rope").defaultBlockState(), false);
        scene.world().setBlock(below, Blocks.BARREL.defaultBlockState(), false);
        scene.idle(20);

        //ladders!
        scene.overlay().showText(60)
                .text("quark_rope.text_6")
                .independent(25)
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(80);
        scene.markAsFinished();
    }
}
