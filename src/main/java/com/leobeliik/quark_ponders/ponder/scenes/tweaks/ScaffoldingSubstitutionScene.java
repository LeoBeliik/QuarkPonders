package com.leobeliik.quark_ponders.ponder.scenes.tweaks;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class ScaffoldingSubstitutionScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos centerScaff = util.grid().at(2, 1, 3);

        PonderAux.setAgentScene(scene, util, "quark_scaffolding_substitution");
        scene.idle(20);

        //explain module
        scene.overlay().showText(80)
                .text("quark_scaffolding_substitution.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(3);
        scene.idle(100);

        scene.overlay().showControls(centerScaff.getCenter(), Pointing.DOWN, 20).withItem(Items.OAK_PLANKS.getDefaultInstance());
        scene.idle(5);
        scene.world().setBlock(util.grid().at(4, 4, 3), Blocks.OAK_PLANKS.defaultBlockState(), false);
        scene.idle(30);
        scene.overlay().showControls(centerScaff.getCenter(), Pointing.DOWN, 20).withItem(Items.OAK_PLANKS.getDefaultInstance());
        scene.idle(5);
        scene.world().setBlock(util.grid().at(3, 4, 3), Blocks.OAK_PLANKS.defaultBlockState(), false);
        scene.idle(40);

        scene.overlay().showText(80)
                .text("quark_scaffolding_substitution.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(3);
        scene.idle(100);

        scene.overlay().showControls(centerScaff.getCenter(), Pointing.DOWN, 20).withItem(Items.OAK_PLANKS.getDefaultInstance());
        scene.idle(5);
        scene.world().setBlock(util.grid().at(0, 4, 3), Blocks.OAK_PLANKS.defaultBlockState(), false);
        scene.idle(30);
        scene.overlay().showControls(centerScaff.getCenter(), Pointing.DOWN, 20).withItem(Items.OAK_PLANKS.getDefaultInstance());
        scene.idle(5);
        scene.world().setBlock(util.grid().at(1, 4, 3), Blocks.OAK_PLANKS.defaultBlockState(), false);
        scene.idle(40);

        scene.overlay().showText(80)
                .text("quark_scaffolding_substitution.text_3")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(3);
        scene.idle(100);
        scene.overlay().showControls(centerScaff.getCenter(), Pointing.DOWN, 20).whileSneaking().withItem(Items.DARK_OAK_PLANKS.getDefaultInstance());
        scene.idle(5);
        scene.world().setBlock(util.grid().at(2, 1, 2), Blocks.DARK_OAK_PLANKS.defaultBlockState(), false);
        scene.markAsFinished();
    }
}
