package com.leobeliik.quark_ponders.ponder.scenes.tools;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.phys.Vec3;
import org.violetmoon.quark.content.tools.module.SkullPikesModule;

public class SkullPikeScenes {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos spike = util.grid().at(4, 1, 2);
        scene.effects().emitParticles(spike.above().getCenter(), scene.effects()
                .simpleParticleEmitter(Math.random() < 0.05 ? ParticleTypes.WARPED_SPORE : ParticleTypes.ASH, new Vec3(0.25, 0.25, 0.25)), 1, 300);

        //start screen
        PonderAux.setAgentScene(scene, util, "quark_skull_spike");
        scene.idle(20);

        //explain spikes
        scene.overlay().showText(60)
                .text("quark_skull_spike.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(spike.getCenter().add(0, 0.25, 0));
        scene.idle(80);

        scene.overlay().showText(60)
                .text("quark_skull_spike.text_2", SkullPikesModule.pikeRange)
                .attachKeyFrame()
                .placeNearTarget()
                .independent(2);
        scene.idle(80);

        var mob = scene.world().createEntity(level -> PonderAux.newEntity(EntityType.CREEPER, level, new Vec3(2.5, 1, 2.5), 270));
        scene.idle(30);
        scene.world().modifyEntity(mob, entity -> {
            int pos = 90;
            entity.yRotO = pos;
            entity.setYRot(pos);
            ((Creeper) entity).yHeadRotO = pos;
            ((Creeper) entity).yHeadRot = pos;
        });

        scene.idle(5);
        PonderAux.entityMove(mob, scene, 3, Direction.EAST);
        scene.world().modifyEntity(mob, Entity::discard);
        scene.idle(40);

        //wither and dragon don't work
        mob = scene.world().createEntity(level -> PonderAux.newEntity(EntityType.CREEPER, level, new Vec3(2.5, 1, 2.5), 270));
        scene.world().setBlock(spike.above(), Blocks.WITHER_SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 12), true);

        scene.overlay().showText(60)
                .text("quark_skull_spike.text_3")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(spike.getCenter().add(0, 0.25, 0));
        scene.idle(80);
        scene.world().setBlock(spike.above(), Blocks.DRAGON_HEAD.defaultBlockState().setValue(SkullBlock.ROTATION, 12), true);
        scene.idle(40);
        scene.world().setBlock(spike.above(), Blocks.ZOMBIE_HEAD.defaultBlockState().setValue(SkullBlock.ROTATION, 12), true);
        scene.effects().emitParticles(spike.above().getCenter(), scene.effects()
                .simpleParticleEmitter(Math.random() < 0.05 ? ParticleTypes.WARPED_SPORE : ParticleTypes.ASH, new Vec3(0.25, 0.25, 0.25)), 1, 1000);
        scene.idle(5);
        scene.world().modifyEntity(mob, entity -> {
            int pos = 90;
            entity.yRotO = pos;
            entity.setYRot(pos);
            ((Creeper) entity).yHeadRotO = pos;
            ((Creeper) entity).yHeadRot = pos;
        });
        scene.idle(5);
        PonderAux.entityMove(mob, scene, 3, Direction.EAST);
        scene.world().modifyEntity(mob, Entity::discard);
        scene.idle(40);

        //no boss, no raid only mobs
        scene.overlay().showText(60)
                .text("quark_skull_spike.text_4")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(2);
        scene.idle(80);

        mob = scene.world().createEntity(level -> PonderAux.newEntity(EntityType.PILLAGER, level, new Vec3(2.5, 1, 2.5), 270));
        scene.idle(40);
        scene.world().modifyEntity(mob, Entity::discard);
        mob = scene.world().createEntity(level -> PonderAux.newEntity(EntityType.WITHER, level, new Vec3(2.5, 1, 2.5), 270));
        scene.idle(40);
        scene.world().modifyEntity(mob, Entity::discard);
        scene.idle(40);

        //mobs focus on player
        scene.overlay().showText(60)
                .text("quark_skull_spike.text_5")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(2);
        scene.idle(80);

        mob = scene.world().createEntity(level -> PonderAux.spawnPlayer(scene, level, spike.west().getCenter().add(0, -0.5, 0), 90));
        scene.idle(10);
        var husk = scene.world().createEntity(level -> PonderAux.newEntity(EntityType.HUSK, level, new Vec3(0.5, 1, 2.5), 270));
        scene.idle(10);
        PonderAux.entityMove(husk, scene, 2, Direction.WEST);
        scene.world().modifyEntity(mob, Entity::discard);
        scene.idle(10);
        scene.world().modifyEntity(husk, entity -> {
            int pos = 90;
            entity.yRotO = pos;
            entity.setYRot(pos);
            ((Husk) entity).yHeadRotO = pos;
            ((Husk) entity).yHeadRot = pos;
        });
        scene.idle(5);
        PonderAux.entityMove(husk, scene, 3, Direction.EAST);
        scene.world().modifyEntity(husk, Entity::discard);
        scene.markAsFinished();
    }
}
