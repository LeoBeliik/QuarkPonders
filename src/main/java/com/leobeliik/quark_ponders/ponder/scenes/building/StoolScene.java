package com.leobeliik.quark_ponders.ponder.scenes.building;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.phys.Vec3;
import org.violetmoon.quark.content.building.block.StoolBlock;

public class StoolScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos stool = util.grid().at(2, 1, 2);
        BlockPos b1 = util.grid().at(3, 3, 2);

        //start screen
        PonderAux.setAgentScene(scene, util, "quark_stool");

        //explain bouncing stool
        scene.overlay().showText(60)
                .text("quark_stool.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(stool.getCenter());
        scene.idle(80);

        var player = scene.world().createEntity(level ->
                PonderAux.spawnPlayer(scene, level, new Vec3(stool.getX() + 0.5, stool.getY() + 3, stool.getZ() + 0.5), 90));
        PonderAux.entityMove(player, scene, 3, Direction.DOWN);
        PonderAux.entityMove(player, scene, 1, Direction.UP);
        PonderAux.entityMove(player, scene, 1, Direction.DOWN);
        scene.idle(20);

        //sitting
        scene.overlay().showText(40)
                .text("quark_stool.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(stool.getCenter());
        scene.idle(60);

        scene.overlay().showControls(stool.getCenter(), Pointing.UP, 20).rightClick();
        scene.idle(15);

        scene.world().modifyEntity(player, entity -> {
            entity.noPhysics = true;
            ((ArmorStand) entity).setLeftLegPose(new Rotations(-90.0F, -20.0F, 0.0F));
            ((ArmorStand) entity).setRightLegPose(new Rotations(-90.0F, 20.0F, 0.0F));
            entity.move(MoverType.SELF, new Vec3(0, -0.5, 0));
            entity.setPos(entity.position());
            entity.yo = entity.position().y;
        });
        scene.idle(20);

        //piston
        scene.overlay().showText(60)
                .text("quark_stool.text_3")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(stool.getCenter());
        scene.idle(80);

        PonderAux.clickLampButtonTop(scene, util, b1, true, true);
        scene.idle(10);
        scene.world().modifyBlock(stool.east(), (s -> s.setValue(PistonBaseBlock.EXTENDED, true)), false);
        scene.world().setBlock(stool.west(), PonderAux.getBlock("black_stool").defaultBlockState(), false);
        scene.world().modifyEntity(player, entity -> {
            entity.move(MoverType.PISTON, new Vec3(-1, 0, 0));
            entity.setPos(entity.position());
            entity.yo = entity.position().y;
        });
        scene.world().setBlock(stool, Blocks.PISTON_HEAD.defaultBlockState().setValue(PistonHeadBlock.FACING, Direction.WEST), false);
        scene.idle(10);
        PonderAux.clickLampButtonTop(scene, util, b1, false, true);
        scene.world().modifyBlock(stool.east(), (s -> s.setValue(PistonBaseBlock.EXTENDED, false)), false);
        scene.world().setBlock(stool, Blocks.AIR.defaultBlockState(), false);

        scene.idle(20);
        PonderAux.setSmoke(scene, stool.west().above());
        scene.world().modifyEntity(player, Entity::discard);

        //big stool
        scene.overlay().showText(60)
                .text("quark_stool.text_4")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(stool.west().getCenter());
        scene.idle(80);

        scene.overlay().showControls(stool.west().getCenter(), Pointing.UP, 20).withItem(Items.LANTERN.getDefaultInstance());
        scene.idle(30);
        scene.world().setBlock(stool.west().above(), Blocks.LANTERN.defaultBlockState(), false);
        scene.world().modifyBlock(stool.west(), s -> s.setValue(StoolBlock.BIG, true), false);
        scene.markAsFinished();
    }
}
