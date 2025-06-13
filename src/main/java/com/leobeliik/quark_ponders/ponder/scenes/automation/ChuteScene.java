package com.leobeliik.quark_ponders.ponder.scenes.automation;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneLampBlock;

import java.util.ArrayList;
import java.util.List;

public class ChuteScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos lever = util.grid().at(3, 2, 0);
        BlockPos button = util.grid().at(0, 2, 3);
        BlockPos chute = util.grid().at(2, 2, 2);

        //set base scene
        PonderAux.setAgentScene(scene, util, "quark_chute");

        //Explain chute
        scene.overlay().showText(60)
                .text("quark_chute.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(chute.getCenter().add(0.25, 0, 0.5));
        scene.idle(80);

        //show chute working with dropper and hopper
        scene.overlay().showText(60)
                .text("quark_chute.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(chute.getCenter().add(0.25, 0, 0.5));
        scene.idle(80);

        toggleLever(scene, util, lever, false);

        List<ElementLink<EntityElement>> drops = new ArrayList<>();
        scene.overlay().showText(80)
                .text("quark_chute.text_3")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(chute.below().getCenter());

        for (int i = 0; i < 5; i++) {
            drops.add(scene.world().createItemEntity(chute.getCenter().add(0, -0.75, 0), util.vector().of(0, .5f, 0), new ItemStack(Items.OAK_PLANKS)));
            scene.idle(10);
        }

        toggleLever(scene, util, lever, true);
        scene.idle(20);
        burnDrops(scene, chute, drops);
        scene.idle(20);

        PonderAux.clickLampButton(scene, util, button, button.east(), true, true);
        drops.add(scene.world().createItemEntity(chute.getCenter().add(0, -0.75, 0), util.vector().of(0, .5f, 0), new ItemStack(Items.OAK_PLANKS)));
        scene.idle(10);
        PonderAux.clickLampButton(scene, util, button, button.east(), false, true);
        scene.idle(20);
        burnDrops(scene, chute, drops);
        scene.idle(20);

        //lock with redstone and solid block
        scene.world().setBlock(util.grid().at(2, 3, 2), Blocks.REDSTONE_BLOCK.defaultBlockState(), true);
        scene.overlay().showText(40)
                .placeNearTarget()
                .attachKeyFrame()
                .text("quark_chute.text_4")
                .pointAt(util.vector().topOf(chute));
        scene.idle(60);
        toggleLever(scene, util, lever, false);
        scene.idle(30);
        toggleLever(scene, util, lever, true);
        scene.idle(20);
        PonderAux.clickLampButton(scene, util, button, button.east(), true, true);
        scene.idle(20);
        PonderAux.clickLampButton(scene, util, button, button.east(), false, true);
        scene.idle(20);
        scene.world().setBlock(util.grid().at(2, 3, 2), Blocks.AIR.defaultBlockState(), true);
        scene.idle(20
        );
        scene.overlay().showText(40)
                .text("quark_chute.text_5")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(chute.below().getCenter());
        scene.world().setBlock(util.grid().at(2, 1, 2), Blocks.DARK_OAK_PLANKS.defaultBlockState(), true);
        scene.idle(40);
        scene.world().setBlock(util.grid().at(2, 1, 2), Blocks.AIR.defaultBlockState(), true);
        scene.idle(20);

        //indicate it works with hollow blocks
        scene.overlay().showText(40)
                .text("quark_chute.text_6")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(chute.below().getCenter());
        scene.world().setBlock(util.grid().at(2, 1, 2), PonderAux.getBlock("hollow_oak_log").defaultBlockState(), true);
        scene.idle(10);
        PonderAux.clickLampButton(scene, util, button, button.east(), true, true);
        drops.add(scene.world().createItemEntity(chute.getCenter().add(0, -0.75, 0), util.vector().of(0, .5f, 0), new ItemStack(Items.OAK_PLANKS)));
        scene.idle(10);
        PonderAux.clickLampButton(scene, util, button, button.east(), false, true);
        scene.idle(10);
        scene.world().setBlock(util.grid().at(2, 1, 2), Blocks.AIR.defaultBlockState(), true);
        scene.markAsFinished();
    }

    private static void toggleLever(PonderSceneBuilder scene, SceneBuildingUtil util, BlockPos lever, boolean lit) {
        scene.overlay().showControls(util.vector().topOf(lever), Pointing.DOWN, 25).rightClick();
        scene.world().toggleRedstonePower(util.select().fromTo(lever, lever.east()));
        scene.world().modifyBlock(lever.south(), (s -> s.setValue(RedstoneLampBlock.LIT, lit)), false);
    }

    private static void burnDrops(PonderSceneBuilder scene, BlockPos chute, List<ElementLink<EntityElement>> drops) {
        scene.world().setBlock(chute.below(), Blocks.FIRE.defaultBlockState(), true);
        scene.idle(5);
        drops.forEach(d -> scene.world().modifyEntity(d, Entity::discard));
        scene.world().setBlock(chute.below(), Blocks.AIR.defaultBlockState(), true);
        scene.world().setBlock(chute.below(), Blocks.AIR.defaultBlockState(), true);
    }
}
