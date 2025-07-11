package com.leobeliik.quark_ponders.ponder.scenes.mobs;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.enums.PonderGuiTextures;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.phys.Vec3;
import org.violetmoon.quark.content.mobs.entity.Foxhound;
import org.violetmoon.quark.content.mobs.module.FoxhoundModule;

import java.util.Objects;

public class FoxHoundScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        PonderAux.setAgentScene(scene, util, "quark_foxhound");
        BlockPos fox = util.grid().at(0, 1, 2);

        //explain module
        var hound = scene.world().createEntity(level -> PonderAux.newEntity(FoxhoundModule.foxhoundType, level, fox.getCenter().add(-0.5, 0, -0.5), -90));
        scene.overlay().showText(60)
                .text("quark_foxhound.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(10);
        scene.idle(80);

        var player = scene.world().createEntity(level -> PonderAux.spawnPlayer(scene, level, fox.east(3).getCenter().add(-0.5, 0, -0.5), 90));

        //they bite
        scene.overlay().showText(60)
                .text("quark_foxhound.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(10);
        scene.idle(80);

        PonderAux.entityMove(hound, scene, 1, Direction.WEST);
        jumpFox(hound, scene, Direction.EAST);
        jumpFox(player, scene, Direction.EAST);
        scene.world().modifyEntity(player, entity -> entity.setRemainingFireTicks(5));
        scene.effects().emitParticles(util.grid().at(4, 1, 2).getCenter().add(0, 0.5, 0),
                scene.effects().particleEmitterWithinBlockSpace(ParticleTypes.FLAME.getType(), Vec3.ZERO), 2, 40);

        scene.idle(45);

        scene.rotateCameraY(360);
        scene.world().modifyEntities(Entity.class, Entity::discard);
        hound = scene.world().createEntity(level -> PonderAux.newEntity(FoxhoundModule.foxhoundType, level, fox.east(3).getCenter().add(0, -0.5, 0), 90));
        player = scene.world().createEntity(level -> PonderAux.spawnPlayer(scene, level, fox.getCenter().add(0, -0.5, 0), -90));
        scene.idle(40);

        //tame
        scene.overlay().showText(80)
                .text("quark_foxhound.text_3")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(10);
        scene.idle(90);

        scene.overlay().showControls(fox.east(3).getCenter(), Pointing.DOWN, 20).withItem(Items.COAL.getDefaultInstance()).showing(PonderGuiTextures.ICON_DISABLE);
        PonderAux.setSmoke(scene, fox.east(3));

        scene.overlay().showText(60)
                .text("quark_foxhound.text_4")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(10);
        scene.idle(80);

        var pot = scene.world().createEntity(level -> potion(level, fox.above(2).getCenter()));
        PonderAux.entityMove(pot, scene, 2, Direction.DOWN);
        scene.world().modifyEntity(pot, Entity::discard);
        scene.effects().emitParticles(fox.getCenter(),
                scene.effects().particleEmitterWithinBlockSpace(ParticleTypes.EFFECT.getType(), Vec3.ZERO), 2, 60);
        scene.idle(20);
        scene.overlay().showControls(fox.east(3).getCenter(), Pointing.DOWN, 20).withItem(Items.COAL.getDefaultInstance());
        scene.effects().emitParticles(fox.east(3).getCenter(),
                scene.effects().particleEmitterWithinBlockSpace(ParticleTypes.HEART.getType(), Vec3.ZERO), 5, 3);
        scene.idle(40);

        scene.overlay().showText(40)
                .text("quark_foxhound.text_5")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(10);
        scene.idle(60);

        scene.world().setBlock(util.grid().at(2, 1, 2), Blocks.FURNACE.defaultBlockState().setValue(FurnaceBlock.FACING, Direction.NORTH).setValue(FurnaceBlock.LIT, true), false);
        scene.world().modifyEntity(player, Entity::discard);
        PonderAux.entityMove(hound, scene, 1, Direction.UP);
        jumpFox(hound, scene, Direction.WEST);
        scene.world().modifyEntity(hound, entity -> ((Foxhound) entity).setInSittingPose(true));
        scene.overlay().showText(60)
                .text("quark_foxhound.text_6")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.grid().at(2,2,2).getCenter());
        scene.idle(80);

        if (FoxhoundModule.foxhoundsSpeedUpFurnaces) {
            scene.overlay().showText(80)
                    .text("quark_foxhound.text_7")
                    .attachKeyFrame()
                    .placeNearTarget()
                    .pointAt(util.grid().at(2, 2, 2).getCenter());
            scene.idle(100);
            scene.overlay().showText(80)
                    .text("quark_foxhound.text_8")
                    .placeNearTarget()
                    .pointAt(util.grid().at(2, 2, 2).getCenter());
        }
        scene.markAsFinished();
    }

    private static void jumpFox(ElementLink<EntityElement> entityElement, PonderSceneBuilder scene, Direction dir) {
        double v = dir == Direction.EAST ? 0.2 : -0.2;
        for (int i = 0; i < 6; i++) {
            scene.idle(1);
            int finalI = i;
            scene.world().modifyEntity(entityElement, entity -> entity.move(MoverType.SELF, new Vec3(v, finalI > 2 ? -0.2 : 0.2, 0)));
        }
        scene.world().modifyEntity(entityElement, e -> {
            e.setPos(e.position());
            e.xo = e.position().x;
            e.yo = e.position().y;
            e.zo = e.position().z;
        });
    }

    private static Entity potion(Level level, Vec3 pos) {
        Entity mob = ((EntityType) EntityType.POTION).create(level);
        Objects.requireNonNull(mob).setPos(pos);
        mob.xo = pos.x;
        mob.yo = pos.y;
        mob.zo = pos.z;
        mob.yRotO = 0;
        mob.setYRot(0);
        return mob;
    }
}
