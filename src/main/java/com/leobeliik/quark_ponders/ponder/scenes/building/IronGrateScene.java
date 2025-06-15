package com.leobeliik.quark_ponders.ponder.scenes.building;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.violetmoon.quark.content.automation.block.FeedingTroughBlock;
import org.violetmoon.quark.content.building.block.GrateBlock;

public class IronGrateScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos grate = util.grid().at(2, 2, 2);

        //start screen
        PonderAux.setAgentScene(scene, util, "quark_grate");

        //explain the grate
        scene.overlay().showText(60)
                .text("quark_grate.text_1")
                .pointAt(grate.getCenter().add(0, 0.5, 0))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(80);

        var item = scene.world().createItemEntity(grate.above().getCenter(), new Vec3(0, -0.075, 0), Items.STONE.getDefaultInstance());
        scene.idle(20);
        scene.world().modifyEntity(item, Entity::discard);
        scene.idle(20);
        BlockPos finalGrate = grate;
        var mob = scene.world().createEntity(level -> {
            var z = PonderAux.newEntity(EntityType.ZOMBIE, level, finalGrate.getCenter().add(0, 0.5, 0), 180);
            z.setDeltaMovement(0, -1, 0);
            return z;
        });
        scene.idle(40);

        //farm mobs won't walk on them
        scene.rotateCameraY(360);
        scene.idle(15);
        scene.world().modifyEntity(mob, Entity::discard);
        scene.world().setBlock(grate, Blocks.AIR.defaultBlockState(), false);
        for (int z = 0; z < 5; z++) {
            scene.world().setBlock(util.grid().at(2, 0, z), PonderAux.getBlock("grate").defaultBlockState(), false);
        }
        scene.idle(40);

        scene.overlay().showText(60)
                .text("quark_grate.text_2")
                .independent()
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(80);

        mob = scene.world().createEntity(level ->
                PonderAux.newEntity(EntityType.COW, level, util.grid().at(4, 1, 2).getCenter().add(0, -0.5, 0), 90));
        scene.idle(20);
        BlockPos ft = util.grid().at(0, 1, 2);
        scene.world().setBlock(ft, PonderAux.getBlock("feeding_trough").defaultBlockState(), false);
        scene.idle(10);
        scene.overlay().showControls(ft.getCenter(), Pointing.UP, 20).withItem(Items.WHEAT.getDefaultInstance());
        scene.world().modifyBlock(ft, state -> state.setValue(FeedingTroughBlock.FULL, true), false);
        scene.idle(20);
        for (int i = 0; i < 10; i++) {
            scene.world().modifyEntity(mob, cow -> cow.move(MoverType.SELF, new Vec3(-0.2, 0, 0)));
            scene.idle(1);
        }
        scene.world().modifyEntity(mob, e -> {
            e.setPos(e.position());
            e.xo = e.position().x;
        });
        scene.idle(20);

        scene.overlay().showText(40)
                .text("quark_grate.text_3")
                .colored(PonderPalette.BLUE)
                .independent()
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(60);

        scene.rotateCameraY(360);
        scene.idle(15);
        for (int z = 0; z < 5; z++) {
            if (z == 2) continue;
            if (z % 2 == 0)
                scene.world().setBlock(util.grid().at(2, 0, z), Blocks.WHITE_CONCRETE.defaultBlockState(), false);
            else
                scene.world().setBlock(util.grid().at(2, 0, z), Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState(), false);
        }
        scene.world().modifyEntity(mob, Entity::discard);
        scene.world().setBlock(ft, Blocks.AIR.defaultBlockState(), false);
        scene.idle(40);

        //water and lava
        grate = util.grid().at(2, 0, 2);

        scene.overlay().showText(60)
                .text("quark_grate.text_4")
                .pointAt(grate.getCenter().add(0, 0.5, 0))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(80);

        scene.overlay().showControls(grate.above().getCenter(), Pointing.DOWN, 20).withItem(Items.WATER_BUCKET.getDefaultInstance());
        scene.idle(15);
        scene.world().modifyBlock(grate, state -> state.setValue(GrateBlock.WATERLOGGED, true), false);
        scene.idle(20);
        item = scene.world().createItemEntity(grate.above().getCenter(), new Vec3(0, -0.075, 0), Items.STONE.getDefaultInstance());
        scene.idle(20);
        scene.world().modifyEntity(item, Entity::discard);
        item = scene.world().createItemEntity(grate.getCenter(), new Vec3(0, 0.075, 0), Items.STONE.getDefaultInstance());
        scene.idle(40);

        PonderAux.setSmoke(scene, grate);
        scene.world().modifyBlock(grate, state -> state.setValue(GrateBlock.WATERLOGGED, false), false);
        scene.world().modifyEntity(item, Entity::discard);
        scene.idle(40);

        scene.overlay().showText(40)
                .text("quark_grate.text_5")
                .pointAt(grate.getCenter().add(0, 0.5, 0))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(60);

        scene.overlay().showControls(grate.above().getCenter(), Pointing.DOWN, 20).withItem(Items.LAVA_BUCKET.getDefaultInstance());
        scene.idle(15);
        scene.world().modifyBlock(grate, state -> state.setValue(GrateBlock.LAVALOGGED, true), false);
        scene.idle(40);
        scene.world().createItemEntity(grate.above().getCenter(), new Vec3(0, -0.075, 0), Items.STONE.getDefaultInstance());
        scene.idle(6);
        PonderAux.setSmoke(scene, grate);
        scene.markAsFinished();
    }
}
