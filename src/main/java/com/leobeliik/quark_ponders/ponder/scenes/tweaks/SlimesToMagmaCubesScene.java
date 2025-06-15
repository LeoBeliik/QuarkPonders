package com.leobeliik.quark_ponders.ponder.scenes.tweaks;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import java.util.ArrayList;
import java.util.List;

public class SlimesToMagmaCubesScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());

        PonderAux.setAgentScene(scene, util, "quark_slimes_to_magma_cubes");
        scene.idle(10);

        //explain module
        scene.overlay().showText(60)
                .text("quark_slimes_to_magma_cubes.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(3);
        scene.idle(80);

        var entity = scene.world().createEntity(level -> {
            Slime slime = (Slime) PonderAux.newEntity(EntityType.SLIME, level, util.vector().topOf(1, 1, 1), 90);
            slime.setSize(3, false);
            return slime;
        });

        scene.idle(20);
        for (int i = 0; i < 6; i++) {
            scene.world().modifyEntity(entity, e ->  e.animateHurt(1));
            scene.idle(20);
        }
        PonderAux.setSmoke(scene, util.grid().at(2,1,2));
        scene.world().modifyEntity(entity, Entity::discard);
        scene.idle(5);
        List<ElementLink<EntityElement>> entities = new ArrayList<>();
        entities.add(scene.world().createEntity(level -> {
            MagmaCube magma = (MagmaCube) PonderAux.newEntity(EntityType.MAGMA_CUBE, level, util.vector().topOf(1, 0, 1), 90);
            magma.setSize(2, false);
            return magma;
        }));
        entities.add(scene.world().createEntity(level -> {
            MagmaCube magma = (MagmaCube) PonderAux.newEntity(EntityType.MAGMA_CUBE, level, util.vector().topOf(3, 0, 3), 90);
            magma.setSize(2, false);
            return magma;
        }));
        scene.idle(40);
        PonderAux.setSmoke(scene, util.grid().at(1,1,1));
        PonderAux.setSmoke(scene, util.grid().at(3,1,3));
        for (ElementLink<EntityElement> e : entities) {
            scene.world().modifyEntity(e, Entity::discard);
        }
        scene.idle(20);
        scene.markAsFinished();
    }
}
