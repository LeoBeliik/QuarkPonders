package com.leobeliik.quark_ponders.registry;

import com.leobeliik.quark_ponders.Config;
import com.leobeliik.quark_ponders.ponder.scenes.automation.*;
import com.leobeliik.quark_ponders.ponder.scenes.building.GlassItemFrameScene;
import com.leobeliik.quark_ponders.ponder.scenes.building.IronGrateScene;
import com.leobeliik.quark_ponders.ponder.scenes.building.RopeScene;
import com.leobeliik.quark_ponders.ponder.scenes.building.StoolScene;
import com.leobeliik.quark_ponders.ponder.scenes.mobs.FoxHoundScene;
import com.leobeliik.quark_ponders.ponder.scenes.mobs.ToretoiseScene;
import com.leobeliik.quark_ponders.ponder.scenes.oddities.MagnetScene;
import com.leobeliik.quark_ponders.ponder.scenes.oddities.PipeScene;
import com.leobeliik.quark_ponders.ponder.scenes.tools.SkullPikeScenes;
import com.leobeliik.quark_ponders.ponder.scenes.tweaks.EnhancedLadderScene;
import com.leobeliik.quark_ponders.ponder.scenes.tweaks.ScaffoldingSubstitutionScene;
import com.leobeliik.quark_ponders.ponder.scenes.tweaks.SlimesToMagmaCubesScene;
import net.createmod.ponder.api.scene.PonderStoryBoard;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.violetmoon.quark.addons.oddities.module.MagnetsModule;
import org.violetmoon.quark.addons.oddities.module.PipesModule;
import org.violetmoon.quark.content.automation.module.*;
import org.violetmoon.quark.content.building.module.GlassItemFrameModule;
import org.violetmoon.quark.content.building.module.GrateModule;
import org.violetmoon.quark.content.building.module.RopeModule;
import org.violetmoon.quark.content.building.module.StoolsModule;
import org.violetmoon.quark.content.mobs.module.FoxhoundModule;
import org.violetmoon.quark.content.mobs.module.ToretoiseModule;
import org.violetmoon.quark.content.tools.module.SkullPikesModule;
import org.violetmoon.quark.content.tweaks.module.EnhancedLaddersModule;
import org.violetmoon.quark.content.tweaks.module.ReplaceScaffoldingModule;
import org.violetmoon.quark.content.tweaks.module.SlimesToMagmaCubesModule;
import org.violetmoon.zeta.module.ZetaModule;

import java.util.List;

interface ModuleRegistry {
    record ModuleSceneInfo(Class<? extends ZetaModule> module, Object rl, String sbPath, PonderStoryBoard scene) {}

    static List toRL(ModConfigSpec.ConfigValue<List<? extends String>> value) {
        return value.get().stream().map(s -> {
            String[] sp = s.split(":", 2);
            return ResourceLocation.fromNamespaceAndPath(sp[0], sp[1]);
        }).toList();
    }

    static List<RegisterScenes.ModuleSceneInfo> automationModules = List.of(
            new RegisterScenes.ModuleSceneInfo(ChainsConnectBlocksModule.class, toRL(Config.chainConnectBlocks_module), "automation/chain", ChainScene::Working),
            new RegisterScenes.ModuleSceneInfo(ChuteModule.class, toRL(Config.chute_module), "automation/chute", ChuteScene::Working),
            new RegisterScenes.ModuleSceneInfo(DispensersPlaceBlocksModule.class, toRL(Config.dispenser_module), "automation/dispenser", DispenserScene::Working),
            new RegisterScenes.ModuleSceneInfo(EnderWatcherModule.class, toRL(Config.enderWatcher_module), "automation/ender_watcher", EnderWatcherScene::Working),
            new RegisterScenes.ModuleSceneInfo(FeedingTroughModule.class, toRL(Config.feedingTrough_module), "automation/feeding_trough", FeedingTroughScene::Working),
            new RegisterScenes.ModuleSceneInfo(GravisandModule.class, toRL(Config.gravisand_module), "automation/gravisand", GravisandScene::Working),
            new RegisterScenes.ModuleSceneInfo(IronRodModule.class, toRL(Config.ironRod_module), "automation/iron_rod", IronRodScene::Working),
            new RegisterScenes.ModuleSceneInfo(MetalButtonsModule.class, toRL(Config.metalButtons_module), "automation/metal_buttons", MetalButtonsScene::Working),
            new RegisterScenes.ModuleSceneInfo(ObsidianPlateModule.class, toRL(Config.obsidianPressurePlate_module), "automation/obsidian_pressure_plate", ObsidianPressurePlateScene::Working),
            new RegisterScenes.ModuleSceneInfo(PistonsMoveTileEntitiesModule.class, toRL(Config.pistonsMoveTE_module), "automation/piston_move_te", PistonMoveTEScene::Working),
            new RegisterScenes.ModuleSceneInfo(RedstoneRandomizerModule.class, toRL(Config.redstoneRandomizer_module), "automation/redstone_randomizer", RedstoneRandomizerScene::Working)
    );

    static List<RegisterScenes.ModuleSceneInfo> buildingModules = List.of(
            new RegisterScenes.ModuleSceneInfo(GlassItemFrameModule.class, toRL(Config.glassItemFrames_module), "building/glass_item_frame", GlassItemFrameScene::Working),
            new RegisterScenes.ModuleSceneInfo(GrateModule.class, toRL(Config.grate_module), "building/grate", IronGrateScene::Working),
            new RegisterScenes.ModuleSceneInfo(RopeModule.class, toRL(Config.rope_module), "building/rope", RopeScene::Working),
            new RegisterScenes.ModuleSceneInfo(StoolsModule.class, toRL(Config.stools_module), "building/stool", StoolScene::Working)
    );

    static List<RegisterScenes.ModuleSceneInfo> mobsModules = List.of(
            new RegisterScenes.ModuleSceneInfo(ToretoiseModule.class, toRL(Config.oretoise_module), "mobs/toretoise", ToretoiseScene::Working),
            new RegisterScenes.ModuleSceneInfo(FoxhoundModule.class, toRL(Config.foxhound_module), "mobs/foxhound", FoxHoundScene::Working)
    );

    static List<RegisterScenes.ModuleSceneInfo> toolsModules = List.of(
            new ModuleSceneInfo(SkullPikesModule.class, toRL(Config.skullPikes_module), "tools/skull_pike", SkullPikeScenes::Working)
    );

    static List<RegisterScenes.ModuleSceneInfo> tweaksModules = List.of(
            new RegisterScenes.ModuleSceneInfo(EnhancedLaddersModule.class, toRL(Config.enhancedLadders_module), "tweaks/enhanced_ladders", EnhancedLadderScene::Working),
            new RegisterScenes.ModuleSceneInfo(ReplaceScaffoldingModule.class, toRL(Config.replaceScaffolding_module), "tweaks/scaffolding_substitution", ScaffoldingSubstitutionScene::Working),
            new RegisterScenes.ModuleSceneInfo(SlimesToMagmaCubesModule.class, toRL(Config.slimeToMagma_module), "tweaks/slimes_to_magma_cubes", SlimesToMagmaCubesScene::Working)
    );

    static List<RegisterScenes.ModuleSceneInfo> odditiesModules = List.of(
            new RegisterScenes.ModuleSceneInfo(PipesModule.class, toRL(Config.pipes_module), "oddities/pipe", PipeScene::Working),
            new RegisterScenes.ModuleSceneInfo(MagnetsModule.class, toRL(Config.magnet_module), "oddities/magnet", MagnetScene::Working)
    );

}
