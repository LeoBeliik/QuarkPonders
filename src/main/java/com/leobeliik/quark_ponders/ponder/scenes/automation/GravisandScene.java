package com.leobeliik.quark_ponders.ponder.scenes.automation;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.Vec3;

public class GravisandScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos buttonDown = util.grid().at(1, 1, 1);
        BlockPos buttonUp = util.grid().at(1, 3, 2);
        BlockPos buttonHigh = util.grid().at(2, 3, 2);
        BlockPos gravisand = util.grid().at(2, 2, 2);
        BlockPos redstoneLamp = util.grid().at(0, 1, 2);
        BlockPos comparator = util.grid().at(1, 1, 2);

        PonderAux.setScene(scene, util, "quark_gravisand");

        //set the agents
        ElementLink<WorldSectionElement> lampBottom = scene.world().showIndependentSection(util.select().fromTo(buttonDown, buttonDown.east()), Direction.DOWN);
        ElementLink<WorldSectionElement> lampTop = scene.world().showIndependentSection(util.select().fromTo(buttonUp, buttonUp.east()), Direction.DOWN);
        ElementLink<WorldSectionElement> gravisandSelection = scene.world().showIndependentSection(util.select().position(gravisand), Direction.DOWN);
        scene.idle(25);

        //explain gravisand
        scene.overlay().showText(60)
                .text("quark_gravisand.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(gravisand.getCenter().add(0.25, -0.5, 0.5));
        scene.idle(80);

        PonderAux.clickLampButton(scene, util, buttonUp, buttonUp.east(), true, true);
        scene.idle(5);
        scene.world().moveSection(gravisandSelection, util.vector().of(0, -1, 0), 10);
        PonderAux.clickLampButton(scene, util, buttonUp, buttonUp.east(), false, true);
        scene.idle(40);

        //it also goes up
        scene.overlay().showText(60)
                .text("quark_gravisand.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(gravisand.below().getCenter().add(0.5, 0, 0.5));
        scene.idle(80);

        PonderAux.clickLampButton(scene, util, buttonDown, buttonDown.east(), true, false);
        scene.idle(5);
        scene.world().moveSection(gravisandSelection, util.vector().of(0, 1, 0), 10);
        PonderAux.clickLampButton(scene, util, buttonDown, buttonDown.east(), false, false);

        scene.idle(40);
        scene.rotateCameraY(360);
        scene.world().hideIndependentSection(lampBottom, Direction.UP);
        scene.world().hideIndependentSection(lampTop, Direction.UP);
        scene.world().hideIndependentSection(gravisandSelection, Direction.UP);
        scene.idle(60);

        //it propagates
        scene.world().setBlock(gravisand, Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(gravisand.below(), PonderAux.getBlock("gravisand").defaultBlockState(), false);
        scene.world().setBlock(gravisand.east(), PonderAux.getBlock("gravisand").defaultBlockState(), false);
        scene.world().setBlock(gravisand.west(), PonderAux.getBlock("gravisand").defaultBlockState(), false);
        scene.world().setBlock(gravisand.north(), PonderAux.getBlock("gravisand").defaultBlockState(), false);
        scene.world().setBlock(gravisand.south(), PonderAux.getBlock("gravisand").defaultBlockState(), false);
        scene.world().setBlock(buttonHigh, Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.FLOOR), false);
        ElementLink<WorldSectionElement> newGravisandSelection = scene.world().showIndependentSection(util.select().position(gravisand.below()), Direction.DOWN);
        scene.world().moveSection(newGravisandSelection, util.vector().of(0, 1, 0), 0);
        ElementLink<WorldSectionElement> gravisandSideSelection = scene.world().showIndependentSection(util.select().fromTo(1, 2, 1, 4, 2, 4), Direction.DOWN);
        scene.world().showIndependentSection(util.select().position(buttonHigh), Direction.DOWN);

        scene.overlay().showText(80)
                .text("quark_gravisand.text_3")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(gravisand.getCenter());
        scene.idle(100);

        scene.overlay().showControls(new Vec3(buttonHigh.getX() + 0.75, buttonHigh.getY() + 0.65, buttonHigh.getZ() + 0.5), Pointing.DOWN, 25).rightClick();
        scene.world().toggleRedstonePower(util.select().fromTo(buttonHigh, buttonHigh.below()));
        scene.idle(5);
        scene.world().moveSection(newGravisandSelection, util.vector().of(0, -1, 0), 10);
        scene.world().setBlock(buttonHigh, Blocks.AIR.defaultBlockState(), true);
        scene.idle(5);
        scene.world().moveSection(gravisandSideSelection, util.vector().of(0, -1, 0), 10);
        scene.idle(60);

        scene.rotateCameraY(360);
        scene.world().hideIndependentSection(newGravisandSelection, Direction.UP);
        scene.world().hideIndependentSection(gravisandSideSelection, Direction.UP);
        scene.idle(60);

        //it emits redstone too

        scene.world().setBlock(gravisand.below(), PonderAux.getBlock("gravisand").defaultBlockState(), false);
        scene.world().setBlock(comparator, Blocks.COMPARATOR.defaultBlockState().setValue(ComparatorBlock.FACING, Direction.EAST), false);
        scene.world().setBlock(redstoneLamp, Blocks.REDSTONE_LAMP.defaultBlockState(), false);
        scene.world().showIndependentSection(util.select().position(gravisand.below()), Direction.DOWN);
        scene.world().showIndependentSection(util.select().position(redstoneLamp), Direction.DOWN);

        scene.overlay().showText(80)
                .text("quark_gravisand.text_4")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(gravisand.below().getCenter());
        scene.idle(100);

        scene.world().showIndependentSection(util.select().position(util.grid().at(1, 1, 2)), Direction.DOWN);
        scene.idle(5);
        scene.world().modifyBlock(comparator, s -> s.setValue(ComparatorBlock.POWERED, true), false);
        scene.idle(10);
        scene.world().modifyBlock(redstoneLamp, (s -> s.setValue(RedstoneLampBlock.LIT, true)), false);
        scene.idle(20);
        scene.markAsFinished();
    }
}
