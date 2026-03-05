package com.leobeliik.quark_ponders.ponder.scenes.oddities;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.enums.PonderGuiTextures;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.violetmoon.quark.addons.oddities.module.MatrixEnchantingModule;
import org.violetmoon.quark.addons.oddities.util.InfluenceLocations;

public class InfluenceScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        InfluenceLocations ench = MatrixEnchantingModule.candleInfluences.get(DyeColor.LIME);
        BlockPos matrix = util.grid().at(2, 1, 2);
        BlockPos candle1 = util.grid().at(0, 2, 2);
        BlockPos candle2 = util.grid().at(4, 2, 2);


        PonderAux.setAgentScene(scene, util, "quark_influence");
        litFam(scene, candle1, candle2);
        scene.idle(10);

        //explain module
        scene.overlay().showText(100)
                .text("quark_influence.text_1", (MatrixEnchantingModule.influenceMax * MatrixEnchantingModule.influencePower) * 100)
                .attachKeyFrame()
                .placeNearTarget()
                .independent(3);
        int comp = FastColor.ARGB32.opaque(160);
        for (int j = 0; j < 4; j++) {
            for (double i = 0; i < 15; i++) {
                scene.effects().emitParticles(new Vec3(candle1.getCenter().x + i / 6, candle1.getCenter().y - i / 12, candle1.getCenter().z), scene.effects().simpleParticleEmitter(
                        new DustParticleOptions(new Vector3f((comp >> 16) / 255f, (comp >> 8) / 255f, comp / 255f), 0.75F), Vec3.ZERO), 5, 7);
                scene.effects().emitParticles(new Vec3(candle2.getCenter().x - i / 6, candle2.getCenter().y - i / 12, candle2.getCenter().z), scene.effects().simpleParticleEmitter(
                        new DustParticleOptions(new Vector3f((comp >> 16) / 255f, (comp >> 8) / 255f, comp / 255f), 0.75F), Vec3.ZERO), 5, 7);
                scene.idle(1);
            }
            scene.idle(20);
        }
        scene.idle(5);

        //look in JEI bro
        scene.overlay().showText(100)
                .text("quark_influence.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .independent(3);
        scene.overlay().showControls(candle1.getCenter(), Pointing.DOWN, 80).showing(PonderGuiTextures.ICON_CONFIRM);
        scene.overlay().showControls(candle2.getCenter(), Pointing.DOWN, 80).showing(PonderGuiTextures.ICON_CONFIRM);
        scene.idle(110);

        //example of candle doing something
        scene.overlay().showText(100)
                .text("quark_influence.text_3", Component.translatable("enchantment." + ench.boost().get(0).toLanguageKey()).getString(),
                        ench.boost().size() > 1 ? Component.translatable("quark_ponders.ponder.quark_influence.amongus").getString() : ".")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(candle1.getCenter());
        scene.idle(130);


        //can be converted to do the opposite!
        scene.overlay().showText(80)
                .text("quark_influence.text_4")
                .attachKeyFrame()
                .placeNearTarget()
                .independent();
        scene.idle(100);

        scene.world().setBlock(candle1.below(), Blocks.SOUL_SAND.defaultBlockState(), true);
        scene.world().setBlock(candle2.below(), Blocks.SOUL_SOIL.defaultBlockState(), true);
        scene.idle(5);
        scene.effects().emitParticles(candle1.getCenter(), scene.effects().simpleParticleEmitter(ParticleTypes.SOUL_FIRE_FLAME, Vec3.ZERO), 0.25f, 300);
        scene.effects().emitParticles(candle2.getCenter(), scene.effects().simpleParticleEmitter(ParticleTypes.SOUL_FIRE_FLAME, Vec3.ZERO), 0.25f, 300);

        scene.idle(25);

        scene.overlay().showText(110)
                .text("quark_influence.text_5")
                .attachKeyFrame()
                .placeNearTarget()
                .independent();
        scene.idle(120);

        scene.world().setBlock(candle1.below(), PonderAux.getBlock("dark_oak_bookshelf").defaultBlockState(), true);
        scene.world().setBlock(candle2.below(), Blocks.GLASS.defaultBlockState(), true);
        scene.world().setBlock(candle1.below(2), Blocks.SOUL_SAND.defaultBlockState(), true);
        scene.world().setBlock(candle2.below(2), Blocks.SOUL_SOIL.defaultBlockState(), true);

        scene.overlay().showText(100)
                .text("quark_influence.text_6")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(candle1.below(2).south().getBottomCenter());
        scene.idle(150);

        scene.world().setBlock(candle2.below(), PonderAux.getBlock("ancient_bookshelf").defaultBlockState(), true);
        scene.world().setBlock(candle1.below(2), Blocks.WHITE_CONCRETE.defaultBlockState(), true);
        scene.world().setBlock(candle2.below(2), Blocks.WHITE_CONCRETE.defaultBlockState(), true);
        scene.idle(20);

        //they are also bookshelves* (* kinda)
        scene.overlay().showText(80)
                .text("quark_influence.text_7")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(candle2.getCenter());
        scene.idle(100);

        scene.world().destroyBlock(util.grid().at(1, 1, 0));
        scene.world().destroyBlock(util.grid().at(3, 1, 0));
        scene.idle(30);

        scene.overlay().showText(80)
                .text("quark_influence.text_8")
                .attachKeyFrame()
                .placeNearTarget()
                .independent();
        scene.idle(130);

        scene.idle(20);
        scene.markAsFinished();
    }

    private static void litFam(PonderSceneBuilder scene, BlockPos candle1, BlockPos candle2) {
        scene.effects().emitParticles(candle1.getCenter(), scene.effects().simpleParticleEmitter(ParticleTypes.SMOKE, Vec3.ZERO), 0.5f, 2000);
        scene.effects().emitParticles(candle1.getCenter(), scene.effects().simpleParticleEmitter(ParticleTypes.SMALL_FLAME, Vec3.ZERO), 0.15f, 2000);
        scene.effects().emitParticles(candle2.getCenter(), scene.effects().simpleParticleEmitter(ParticleTypes.SMOKE, Vec3.ZERO), 0.5f, 2000);
        scene.effects().emitParticles(candle2.getCenter(), scene.effects().simpleParticleEmitter(ParticleTypes.SMALL_FLAME, Vec3.ZERO), 0.15f, 2000);
    }
}
