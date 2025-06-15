package com.leobeliik.quark_ponders.ponder.scenes.tweaks;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Rotations;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class EnhancedLadderScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos starterLadder = util.grid().at(2, 4, 2);

        PonderAux.setAgentScene(scene, util, "quark_enhanced_ladders");
        scene.idle(20);

        //explain module
        scene.overlay().showText(80)
                .text("quark_enhanced_ladders.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(starterLadder.getCenter().add(0, 0, 0.5));
        scene.idle(100);
        placeLadders(scene, starterLadder);

        //ladders can hang
        scene.rotateCameraY(360);
        scene.idle(10);
        for (int i = 1; i < 4; i++) {
            BlockPos p = new BlockPos(starterLadder.getX(), starterLadder.getY() - i, starterLadder.getZ());
            scene.world().setBlock(p, Blocks.AIR.defaultBlockState(), false);
            scene.world().setBlock(p.south(), Blocks.AIR.defaultBlockState(), false);
        }
        scene.idle(20);
        scene.overlay().showText(80)
                .text("quark_enhanced_ladders.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(starterLadder.getCenter().add(0, 0, 0.5));
        scene.idle(100);

        placeLadders(scene, starterLadder);

        //slide down
        scene.idle(20);
        scene.overlay().showText(60)
                .text("quark_enhanced_ladders.text_3")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(2);
        scene.idle(80);

        var player = scene.world().createEntity(level -> PonderAux.spawnPlayer(scene, level, starterLadder.getCenter(), 180));
        for (int i = 0; i < 33; i++) {
            scene.idle(1);
            scene.world().modifyEntity(player, entity -> entity.move(MoverType.PLAYER, new Vec3(0, -0.1, 0)));
        }
        scene.world().modifyEntity(player, e -> {
            e.setPos(e.position());
            e.xo = e.position().x;
        });
        scene.idle(20);
        PonderAux.setSmoke(scene, util.grid().at(2, 1, 2));
        PonderAux.setSmoke(scene, util.grid().at(2, 2, 2));
        scene.world().modifyEntity(player, Entity::discard);
        scene.idle(40);

        player = scene.world().createEntity(level -> PonderAux.spawnPlayer(scene, level, starterLadder.getCenter(), 180));
        for (int i = 0; i < 11; i++) {
            scene.idle(1);
            scene.world().modifyEntity(player, entity -> {
                ((ArmorStand) entity).setHeadPose(new Rotations(45, 0, 0));
                entity.move(MoverType.PLAYER, new Vec3(0, -0.32, 0));
            });
        }
        scene.world().modifyEntity(player, e -> {
            e.setPos(e.position());
            e.xo = e.position().x;
        });
        scene.idle(20);

        PonderAux.setSmoke(scene, util.grid().at(2, 1, 2));
        PonderAux.setSmoke(scene, util.grid().at(2, 2, 2));
        scene.world().modifyEntity(player, Entity::discard);
        scene.idle(30);
        scene.markAsFinished();
    }

    private static void placeLadders(PonderSceneBuilder scene, BlockPos ladder) {
        for (int i = 1; i < 4; i++) {
            BlockPos p = new BlockPos(ladder.getX(), ladder.getY() - i, ladder.getZ());
            scene.overlay().showControls(ladder.getCenter().add(0, 0.5, 0.5), Pointing.DOWN, 20).withItem(Items.LADDER.getDefaultInstance());
            scene.world().setBlock(p, Blocks.LADDER.defaultBlockState(), false);
            scene.idle(30);
        }
    }
}
