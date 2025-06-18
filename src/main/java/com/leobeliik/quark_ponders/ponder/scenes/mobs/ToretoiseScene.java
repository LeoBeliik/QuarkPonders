package com.leobeliik.quark_ponders.ponder.scenes.mobs;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;

public class ToretoiseScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos magnet = util.grid().at(2, 1, 2);
        BlockPos lamp = magnet.above();
        BlockPos lever = lamp.above();

        PonderAux.setAgentScene(scene, util, "quark_toretoise");
        scene.idle(10);

        //explain module
        scene.overlay().showText(60)
                .text("quark_toretoise.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(magnet.getCenter());
        scene.idle(80);
    }
}
