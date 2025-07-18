package com.leobeliik.quark_ponders.ponder.scenes.automation;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.RedstoneLampBlock;

public class ObsidianPressurePlateScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos obiPP = util.grid().at(2, 2, 2);
        BlockPos lamp = util.grid().at(2, 1, 2);

        PonderAux.setAgentScene(scene, util, "quark_obsidian_pressure_plate");

        //explain the obiPP
        scene.overlay().showText(80)
                .text("quark_obsidian_pressure_plate.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(obiPP.getCenter().add(0, -0.45, 0));
        scene.idle(100);


        var pig = scene.world().createEntity(l -> PonderAux.newEntity(EntityType.PIG, l, obiPP.getCenter().add(-0.5, 0, -0.5), 135));

        scene.idle(40);
        PonderAux.setSmoke(scene, obiPP);
        scene.idle(5);
        scene.world().modifyEntity(pig, Entity::discard);
        scene.idle(20);
        scene.world().createEntity(level -> PonderAux.spawnPlayer(scene, level, obiPP.getCenter().add(-0.5, 0, -0.5), 135));
        scene.idle(5);
        scene.world().modifyBlock(lamp, (s -> s.setValue(RedstoneLampBlock.LIT, true)), false);
        scene.idle(20);
        scene.markAsFinished();
    }
}
