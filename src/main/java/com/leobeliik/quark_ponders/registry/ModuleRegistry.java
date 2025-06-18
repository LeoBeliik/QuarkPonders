package com.leobeliik.quark_ponders.registry;

import com.leobeliik.quark_ponders.ponder.scenes.automation.*;
import com.leobeliik.quark_ponders.ponder.scenes.building.GlassItemFrameScene;
import com.leobeliik.quark_ponders.ponder.scenes.building.IronGrateScene;
import com.leobeliik.quark_ponders.ponder.scenes.building.RopeScene;
import com.leobeliik.quark_ponders.ponder.scenes.mobs.ToretoiseScene;
import com.leobeliik.quark_ponders.ponder.scenes.oddities.MagnetScene;
import com.leobeliik.quark_ponders.ponder.scenes.oddities.PipeScene;
import com.leobeliik.quark_ponders.ponder.scenes.tools.SkullPikeScenes;
import com.leobeliik.quark_ponders.ponder.scenes.tweaks.EnhancedLadderScene;
import com.leobeliik.quark_ponders.ponder.scenes.tweaks.ScaffoldingSubstitutionScene;
import com.leobeliik.quark_ponders.ponder.scenes.tweaks.SlimesToMagmaCubesScene;
import net.createmod.ponder.api.scene.PonderStoryBoard;
import org.violetmoon.quark.addons.oddities.module.MagnetsModule;
import org.violetmoon.quark.addons.oddities.module.PipesModule;
import org.violetmoon.quark.content.automation.module.*;
import org.violetmoon.quark.content.building.module.GlassItemFrameModule;
import org.violetmoon.quark.content.building.module.GrateModule;
import org.violetmoon.quark.content.building.module.RopeModule;
import org.violetmoon.quark.content.mobs.module.ToretoiseModule;
import org.violetmoon.quark.content.tools.module.SkullPikesModule;
import org.violetmoon.quark.content.tweaks.module.EnhancedLaddersModule;
import org.violetmoon.quark.content.tweaks.module.ReplaceScaffoldingModule;
import org.violetmoon.quark.content.tweaks.module.SlimesToMagmaCubesModule;
import org.violetmoon.zeta.module.ZetaModule;
import java.util.List;
import static com.leobeliik.quark_ponders.registry.RegistryUtils.asQuarkResource;
import static com.leobeliik.quark_ponders.registry.RegistryUtils.asResource;

interface ModuleRegistry extends ModuleTags {
    record ModuleSceneInfo(Class<? extends ZetaModule> module, Object rl, String sbPath, PonderStoryBoard scene) {}

    static List<RegisterScenes.ModuleSceneInfo> automationModules = List.of(
            new RegisterScenes.ModuleSceneInfo(ChainsConnectBlocksModule.class, asResource("chain"), "automation/chain", ChainScene::Working),
            new RegisterScenes.ModuleSceneInfo(ChuteModule.class, asQuarkResource("chute"), "automation/chute", ChuteScene::Working),
            new RegisterScenes.ModuleSceneInfo(DispensersPlaceBlocksModule.class, asResource("dispenser"), "automation/dispenser", DispenserScene::Working),
            new RegisterScenes.ModuleSceneInfo(EnderWatcherModule.class, asQuarkResource("ender_watcher"), "automation/ender_watcher", EnderWatcherScene::Working),
            new RegisterScenes.ModuleSceneInfo(FeedingTroughModule.class, asQuarkResource("feeding_trough"), "automation/feeding_trough", FeedingTroughScene::Working),
            new RegisterScenes.ModuleSceneInfo(GravisandModule.class, asQuarkResource("gravisand"), "automation/gravisand", GravisandScene::Working),
            new RegisterScenes.ModuleSceneInfo(IronRodModule.class, asQuarkResource("iron_rod"), "automation/iron_rod", IronRodScene::Working),
            new RegisterScenes.ModuleSceneInfo(MetalButtonsModule.class, metalButtons, "automation/metal_buttons", MetalButtonsScene::Working),
            new RegisterScenes.ModuleSceneInfo(ObsidianPlateModule.class, asQuarkResource("obsidian_pressure_plate"), "automation/obsidian_pressure_plate", ObsidianPressurePlateScene::Working),
            new RegisterScenes.ModuleSceneInfo(PistonsMoveTileEntitiesModule.class, pistons, "automation/piston_move_te", PistonMoveTEScene::Working),
            new RegisterScenes.ModuleSceneInfo(RedstoneRandomizerModule.class, asQuarkResource("redstone_randomizer"), "automation/redstone_randomizer", RedstoneRandomizerScene::Working)
    );

    static List<RegisterScenes.ModuleSceneInfo> buildingModules = List.of(
            new RegisterScenes.ModuleSceneInfo(GlassItemFrameModule.class, glassFrames, "building/glass_item_frame", GlassItemFrameScene::Working),
            new RegisterScenes.ModuleSceneInfo(GrateModule.class, asQuarkResource("grate"), "building/grate", IronGrateScene::Working),
            new RegisterScenes.ModuleSceneInfo(RopeModule.class, asQuarkResource("rope"), "building/rope", RopeScene::Working)
    );

    static List<RegisterScenes.ModuleSceneInfo> mobsModules = List.of(
            new RegisterScenes.ModuleSceneInfo(ToretoiseModule.class, ores, "mobs/toretoise", ToretoiseScene::Working)
    );

    static List<RegisterScenes.ModuleSceneInfo> toolsModules = List.of(
            new ModuleSceneInfo(SkullPikesModule.class, heads, "tools/skull_pike", SkullPikeScenes::Working)
    );

    static List<RegisterScenes.ModuleSceneInfo> tweaksModules = List.of(
            new RegisterScenes.ModuleSceneInfo(EnhancedLaddersModule.class, ladders, "tweaks/enhanced_ladders", EnhancedLadderScene::Working),
            new RegisterScenes.ModuleSceneInfo(ReplaceScaffoldingModule.class, asResource("scaffolding"), "tweaks/scaffolding_substitution", ScaffoldingSubstitutionScene::Working),
            new RegisterScenes.ModuleSceneInfo(SlimesToMagmaCubesModule.class, asResource("magma_cream"), "tweaks/slimes_to_magma_cubes", SlimesToMagmaCubesScene::Working)
    );

    static List<RegisterScenes.ModuleSceneInfo> odditiesModules = List.of(
            new RegisterScenes.ModuleSceneInfo(PipesModule.class, asQuarkResource("pipe"), "oddities/pipe", PipeScene::Working),
            new RegisterScenes.ModuleSceneInfo(MagnetsModule.class, asQuarkResource("magnet"), "oddities/magnet", MagnetScene::Working)
    );
}
