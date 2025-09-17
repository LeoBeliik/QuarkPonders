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
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.phys.Vec3;


public class ChainScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos leverPos = util.grid().at(0, 1, 3);
        BlockPos piston = leverPos.east();

        //start screen
        PonderAux.setScene(scene, util, "minecraft_chain");

        //place the agents
        scene.world().showIndependentSection(util.select().fromTo(0, 1, 3, 3, 1, 3), Direction.DOWN);
        ElementLink<WorldSectionElement> chains = scene.world().showIndependentSection(util.select().fromTo(2, 1, 0, 2, 1, 2), Direction.DOWN);
        scene.idle(25);

        //show chain connection description
        scene.overlay().showText(50)
                .text("minecraft_chain.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(2, 1, 2));
        scene.idle(80);
        //click lever
        Vec3 falseSelection = util.vector().blockSurface(leverPos, Direction.DOWN);
        scene.overlay().showControls(falseSelection, Pointing.UP, 25).rightClick();
        scene.world().toggleRedstonePower(util.select().fromTo(leverPos, leverPos.east()));
        scene.idle(5);
        //move scene and "trigger" piston
        scene.world().modifyBlock(piston, (s -> s.setValue(PistonBaseBlock.EXTENDED, true)), false);
        scene.world().moveSection(chains, util.vector().of(1, 0, 0), 1);
        scene.world().setBlock(piston.east(), Blocks.PISTON_HEAD.defaultBlockState().setValue(PistonHeadBlock.FACING, Direction.EAST), false);
        scene.world().setBlock(piston.east().east(), Blocks.OAK_WOOD.defaultBlockState(), false);
        scene.overlay().showText(75)
                .pointAt(util.vector().topOf(piston))
                .placeNearTarget()
                .attachKeyFrame()
                .text("minecraft_chain.text_2")
                .pointAt(util.vector().topOf(3, 1, 3));
        scene.idle(90);

        //chains are directional description
        scene.world().setBlock(util.grid().at(3, 2, 2), Blocks.OAK_WOOD.defaultBlockState(), false);
        scene.world().showIndependentSection(util.select().fromTo(0, 2, 0, 5, 2, 5), Direction.DOWN);
        scene.overlay().showText(60)
                .pointAt(util.vector().topOf(piston))
                .placeNearTarget()
                .attachKeyFrame()
                .text("minecraft_chain.text_3")
                .pointAt(util.vector().topOf(3, 2, 2));
        scene.idle(80);
        //click lever again
        scene.overlay().showControls(falseSelection, Pointing.UP, 25).rightClick();
        scene.world().toggleRedstonePower(util.select().fromTo(leverPos, leverPos.east()));
        scene.idle(5);
        //reset scene and leave unchained block away
        scene.world().modifyBlock(piston, (s -> s.setValue(PistonBaseBlock.EXTENDED, false)), false);
        scene.world().moveSection(chains, util.vector().of(-1, 0, 0), 1);
        scene.world().setBlock(piston.east(), Blocks.OAK_WOOD.defaultBlockState(), false);
        scene.world().setBlock(piston.east().east(), Blocks.AIR.defaultBlockState(), false);
        scene.markAsFinished();
    }
}
