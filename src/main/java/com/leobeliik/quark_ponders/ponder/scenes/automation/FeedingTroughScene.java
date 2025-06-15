package com.leobeliik.quark_ponders.ponder.scenes.automation;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.violetmoon.quark.content.automation.block.FeedingTroughBlock;
import org.violetmoon.quark.content.automation.module.FeedingTroughModule;

import java.util.ArrayList;
import java.util.List;

public class FeedingTroughScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos ft = util.grid().at(2, 1, 2);

        PonderAux.setAgentScene(scene, util, "quark_feeding_trough");

        //explain the feeding trough
        scene.overlay().showText(40)
                .text("quark_feeding_trough.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(ft.getCenter());
        scene.idle(60);

        scene.overlay().showText(60)
                .text("quark_feeding_trough.text_2", FeedingTroughModule.range)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(ft.getCenter());
        scene.idle(80);

        //show the animals breeding
        List<ElementLink<EntityElement>> animals = new ArrayList<>();
        cowGen(animals, scene);

        scene.idle(20);
        scene.overlay().showControls(ft.getCenter(), Pointing.DOWN, 20).withItem(Items.WHEAT.getDefaultInstance());
        scene.world().modifyBlock(ft, (s -> s.setValue(FeedingTroughBlock.FULL, true)), false);
        scene.idle(30);
        moveCows(animals, scene);
        cowBreed(animals, scene, util, ft);
        scene.idle(30);
        scene.rotateCameraY(360);
        scene.setSceneOffsetY(0);
        scene.idle(20);
        scene.effects().emitParticles(ft.east().getCenter(), scene.effects().particleEmitterWithinBlockSpace(new DustParticleOptions(new Vector3f(0), 1), Vec3.ZERO), 150, 1);
        scene.effects().emitParticles(ft.west().getCenter(), scene.effects().particleEmitterWithinBlockSpace(new DustParticleOptions(new Vector3f(0), 1), Vec3.ZERO), 150, 1);
        scene.effects().emitParticles(ft.above().getCenter(), scene.effects().particleEmitterWithinBlockSpace(new DustParticleOptions(new Vector3f(0), 1), Vec3.ZERO), 150, 1);
        scene.world().modifyBlock(ft, (s -> s.setValue(FeedingTroughBlock.FULL, false)), false);
        scene.idle(5);
        for (ElementLink<EntityElement> entity : animals) {
            scene.world().modifyEntity(entity, cow -> {
                cow.setInvisible(true);
                cow.kill();
            });
        }
        animals.clear();
        scene.idle(45);

        cowGen(animals, scene);
        //Mobs eat but not always breed
        scene.overlay().showText(60)
                .text("quark_feeding_trough.text_3")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(ft.getCenter());
        scene.idle(80);
        scene.overlay().showControls(ft.getCenter(), Pointing.DOWN, 20).withItem(Items.WHEAT.getDefaultInstance());
        scene.idle(20);
        scene.world().modifyBlock(ft, (s -> s.setValue(FeedingTroughBlock.FULL, true)), false);
        scene.idle(30);
        moveCows(animals, scene);
        scene.idle(10);
        cowEating(animals, scene, 5);
        scene.world().modifyBlock(ft, (s -> s.setValue(FeedingTroughBlock.FULL, false)), false);
        scene.idle(20);
        scene.overlay().showControls(ft.getCenter(), Pointing.UP, 20).withItem(Items.WHEAT.getDefaultInstance());
        scene.world().modifyBlock(ft, (s -> s.setValue(FeedingTroughBlock.FULL, true)), false);
        scene.idle(20);
        cowBreed(animals, scene, util, ft);

        //Mobs wont breed if there's many
        scene.overlay().showText(80)
                .text("quark_feeding_trough.text_4", FeedingTroughModule.maxAnimals, FeedingTroughModule.range)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(ft.getCenter());
        scene.idle(100);
        scene.markAsFinished();
    }

    private static void cowBreed(List<ElementLink<EntityElement>> animals, PonderSceneBuilder scene, SceneBuildingUtil util, BlockPos pos) {
        cowEating(animals, scene, 1);
        scene.effects().emitParticles(pos.above().getCenter(), scene.effects().simpleParticleEmitter(ParticleTypes.HEART, util.vector().of(0, 0, 0)), 3, 5);
        scene.idle(15);
        animals.add(scene.world().createEntity(l -> {
            Cow cow = (Cow) PonderAux.newEntity(EntityType.COW, l, new Vec3(2.5, 1.75, 2.5), 90);
            cow.setBaby(true);
            return cow;
        }));
    }

    private static void cowEating(List<ElementLink<EntityElement>> animals, PonderSceneBuilder scene, int munch) {
        for (int i = 0; i <= munch; i++) {
            for (ElementLink<EntityElement> animal : animals) {
                int finalI = i;
                scene.world().modifyEntity(animal, cow -> {
                    cow.setXRot(finalI % 2 == 0 ? 45 : 0);
                });
                scene.idle(2);
            }
        }
    }

    private static void moveCows(List<ElementLink<EntityElement>> animals, PonderSceneBuilder scene) {
        for (int i = 0; i < 10; i++) {
            scene.idle(1);
            scene.world().modifyEntity(animals.get(0), cow -> cow.move(MoverType.SELF, new Vec3(0.2, 0, 0)));
            scene.world().modifyEntity(animals.get(1), cow -> cow.move(MoverType.SELF, new Vec3(-0.2, 0, 0)));
        }
        for (ElementLink<EntityElement> entity : animals) {
            scene.world().modifyEntity(entity, e -> {
                e.setPos(e.position());
                e.xo = e.position().x;
            });
        }
    }

    private static void cowGen(List<ElementLink<EntityElement>> animals, PonderSceneBuilder scene) {
        animals.add(scene.world().createEntity(l -> PonderAux.newEntity(EntityType.COW, l, new Vec3(0, 1, 2.5), -90)));
        animals.add(scene.world().createEntity(l -> PonderAux.newEntity(EntityType.COW, l, new Vec3(5, 1, 2.5), 90)));
    }
}
