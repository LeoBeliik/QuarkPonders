package com.leobeliik.quark_ponders.ponder.scenes.automation;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.phys.Vec3;
import org.violetmoon.quark.content.automation.block.EnderWatcherBlock;

public class EnderWatcherScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos watcher = util.grid().at(2, 2, 2);
        BlockPos lamp = util.grid().at(2, 1, 2);

        PonderAux.setScene(scene, util, "quark_ender_watcher");
        //set the agents
        scene.world().showIndependentSection(util.select().fromTo(0, 1, 0, 5, 5, 5), Direction.DOWN);
        var stand = scene.world().createEntity(level -> PonderAux.spawnPlayer(scene, level, new Vec3(2.5, 1, 0.5), 180));
        scene.idle(25);

        //explain the ender watcher
        scene.overlay().showText(40)
                .text("minecraft_dispenser.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(watcher.getCenter());
        scene.idle(60);

        scene.overlay().showText(40)
                .text("minecraft_dispenser.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(watcher.getCenter());
        scene.idle(70);

        scene.overlay().showText(40)
                .text("minecraft_dispenser.text_3")
                .colored(PonderPalette.BLUE)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().centerOf(2, 2, 0));
        scene.idle(60);

        //show watcher working
        scene.world().modifyEntity(stand, e -> e.setYRot(0));
        scene.idle(5);
        scene.world().modifyBlock(watcher, (s -> s.setValue(EnderWatcherBlock.WATCHED, true)), false);
        scene.world().modifyBlock(watcher.below(), (s -> s.setValue(RedstoneLampBlock.LIT, true)), false);
        scene.idle(30);
        scene.overlay().showText(40)
                .text("minecraft_dispenser.text_4")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().centerOf(watcher));
        scene.idle(50);
        scene.world().modifyEntity(stand, e -> e.setYRot(180));
        scene.idle(5);
        scene.world().modifyBlock(watcher, (s -> s.setValue(EnderWatcherBlock.WATCHED, false)), false);
        scene.world().modifyBlock(watcher.below(), (s -> s.setValue(RedstoneLampBlock.LIT, false)), false);
    }
}
