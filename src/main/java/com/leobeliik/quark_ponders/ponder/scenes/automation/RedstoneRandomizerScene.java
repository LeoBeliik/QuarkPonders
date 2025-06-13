package com.leobeliik.quark_ponders.ponder.scenes.automation;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RedstoneLampBlock;
import org.violetmoon.quark.content.automation.base.RandomizerPowerState;
import org.violetmoon.quark.content.automation.block.RedstoneRandomizerBlock;

public class RedstoneRandomizerScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos randomizer = util.grid().at(2, 1, 2);
        BlockPos mainLamp = randomizer.south();
        BlockPos button = mainLamp.above();
        BlockPos eastLamp = randomizer.east();
        BlockPos westLamp = randomizer.west();
        scene.rotateCameraY(25);

        PonderAux.setAgentScene(scene, util, "quark_redstone_randomizer");

        scene.overlay().showText(60)
                .text("quark_redstone_randomizer.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(randomizer.getCenter().add(0.25, -0.5, 0.5));
        scene.idle(80);

        for (int i = 0; i < 5; i++) {
            boolean random = (int) (Math.random() * 100) % 2 == 0;
            RandomizerPowerState side = random ? RandomizerPowerState.LEFT : RandomizerPowerState.RIGHT;
            PonderAux.clickLampButtonTop(scene, util, button, true, true);
            scene.idle(5);
            scene.world().modifyBlock(randomizer, state -> state.setValue(RedstoneRandomizerBlock.POWERED, side), false);
            if (random)
                scene.world().modifyBlock(randomizer.west(), state -> state.setValue(RedstoneLampBlock.LIT, true), false);
            else
                scene.world().modifyBlock(randomizer.east(), state -> state.setValue(RedstoneLampBlock.LIT, true), false);
            scene.idle(20);
            PonderAux.clickLampButtonTop(scene, util, button, false, true);
            scene.world().modifyBlock(randomizer, state -> state.setValue(RedstoneRandomizerBlock.POWERED, RandomizerPowerState.OFF), false);
            scene.world().modifyBlock(randomizer.east(), state -> state.setValue(RedstoneLampBlock.LIT, false), false);
            scene.world().modifyBlock(randomizer.west(), state -> state.setValue(RedstoneLampBlock.LIT, false), false);
            scene.idle(25);
        }
    }
}
