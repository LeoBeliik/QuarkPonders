package com.leobeliik.quark_ponders.ponder.scenes.automation;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;

public class MetalButtonsScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos goldButton = util.grid().at(1, 2, 3);
        BlockPos ironButton = util.grid().at(3, 2, 3);
        BlockPos woodButton = util.grid().at(3, 2, 1);
        BlockPos stoneButton = util.grid().at(1, 2, 1);

        PonderAux.setAgentScene(scene, util, "quark_metal_buttons");

        //explain the buttons
        scene.overlay().showText(60)
                .text("quark_metal_buttons.text_1")
                .independent(5)
                .placeNearTarget()
                .attachKeyFrame();
        scene.idle(80);

        PonderAux.clickLampButtonTop(scene, util, goldButton, true, true);
        PonderAux.clickLampButtonTop(scene, util, ironButton, true, true);
        PonderAux.clickLampButtonTop(scene, util, woodButton, true, true);
        PonderAux.clickLampButtonTop(scene, util, stoneButton, true, true);
        scene.idle(4);
        scene.overlay().showText(60)
                .text("quark_metal_buttons.text_2")
                .pointAt(goldButton.getCenter())
                .placeNearTarget()
                .attachKeyFrame();
        PonderAux.clickLampButtonTop(scene, util, goldButton, false, true);
        scene.idle(16);
        PonderAux.clickLampButtonTop(scene, util, stoneButton, false, true);
        scene.idle(10);
        PonderAux.clickLampButtonTop(scene, util, woodButton, false, true);
        scene.idle(40);
        scene.overlay().showText(60)
                .text("quark_metal_buttons.text_3")
                .pointAt(ironButton.getCenter())
                .placeNearTarget()
                .attachKeyFrame();
        scene.idle(30);
        PonderAux.clickLampButtonTop(scene, util, ironButton, false, true);
        scene.idle(20);
        scene.markAsFinished();
    }
}
