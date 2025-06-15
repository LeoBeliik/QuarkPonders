package com.leobeliik.quark_ponders.ponder;

import com.leobeliik.quark_ponders.ponder.scenes.automation.*;
import com.leobeliik.quark_ponders.ponder.scenes.building.GlassItemFrameScene;
import com.leobeliik.quark_ponders.ponder.scenes.building.IronGrateScene;
import com.leobeliik.quark_ponders.ponder.scenes.building.RopeScene;
import com.leobeliik.quark_ponders.ponder.scenes.oddities.PipeScene;
import com.leobeliik.quark_ponders.ponder.scenes.tools.SkullPikeScenes;
import com.leobeliik.quark_ponders.ponder.scenes.tweaks.EnhancedLadderScene;
import com.leobeliik.quark_ponders.ponder.scenes.tweaks.ScaffoldingSubstitutionScene;
import com.leobeliik.quark_ponders.ponder.scenes.tweaks.SlimesToMagmaCubesScene;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.scene.PonderStoryBoard;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.violetmoon.quark.addons.oddities.module.PipesModule;
import org.violetmoon.quark.base.Quark;
import org.violetmoon.quark.content.automation.module.*;
import org.violetmoon.quark.content.building.module.GlassItemFrameModule;
import org.violetmoon.quark.content.building.module.GrateModule;
import org.violetmoon.quark.content.building.module.RopeModule;
import org.violetmoon.quark.content.tools.module.SkullPikesModule;
import org.violetmoon.quark.content.tweaks.module.EnhancedLaddersModule;
import org.violetmoon.quark.content.tweaks.module.ReplaceScaffoldingModule;
import org.violetmoon.quark.content.tweaks.module.SlimesToMagmaCubesModule;
import org.violetmoon.zeta.module.ZetaModule;

import java.util.List;
import java.util.Map;

import static com.leobeliik.quark_ponders.QuarkPonders.MODID;

public class RegisterScenes implements PonderPlugin {
    private record ModuleSceneInfo(Class<? extends ZetaModule> module, ResourceLocation rl, String sbPath,
                                   PonderStoryBoard scene) {
    }

    @Override
    public @NotNull String getModId() {
        return MODID;
    }

    @Override
    public void registerScenes(@NotNull PonderSceneRegistrationHelper<ResourceLocation> helper) {
        Quark.ZETA.modules.getCategories().stream().filter(category ->
                Quark.ZETA.configManager.isCategoryEnabled(category)).forEach(category ->
                loadScenes(helper, MODULE_MAP.get(category.name)));
    }

    private static ResourceLocation asResource(String name, String path) {
        return ResourceLocation.fromNamespaceAndPath(name, path);
    }

    private static void loadScenes(PonderSceneRegistrationHelper<ResourceLocation> helper, List<ModuleSceneInfo> modules) {
        //TODO TEMP
        if (modules != null)
            modules.stream().filter(info ->
                    Quark.ZETA.modules.isEnabled(info.module())).forEach(info ->
                    helper.forComponents(info.rl()).addStoryBoard(info.sbPath(), info.scene()));
    }

    private static List<ModuleSceneInfo> automationModules = List.of(
            new ModuleSceneInfo(ChainsConnectBlocksModule.class, asResource("minecraft", "chain"), "automation/chain", ChainScene::Working),
            new ModuleSceneInfo(ChuteModule.class, asResource("quark", "chute"), "automation/chute", ChuteScene::Working),
            new ModuleSceneInfo(DispensersPlaceBlocksModule.class, asResource("minecraft", "dispenser"), "automation/dispenser", DispenserScene::Working),
            new ModuleSceneInfo(EnderWatcherModule.class, asResource("quark", "ender_watcher"), "automation/ender_watcher", EnderWatcherScene::Working),
            new ModuleSceneInfo(FeedingTroughModule.class, asResource("quark", "feeding_trough"), "automation/feeding_trough", FeedingTroughScene::Working),
            new ModuleSceneInfo(GravisandModule.class, asResource("quark", "gravisand"), "automation/gravisand", GravisandScene::Working),
            new ModuleSceneInfo(IronRodModule.class, asResource("quark", "iron_rod"), "automation/iron_rod", IronRodScene::Working),
            new ModuleSceneInfo(MetalButtonsModule.class, asResource("quark", "iron_button"), "automation/metal_buttons", MetalButtonsScene::Working),
            new ModuleSceneInfo(MetalButtonsModule.class, asResource("quark", "gold_button"), "automation/metal_buttons", MetalButtonsScene::Working),
            new ModuleSceneInfo(ObsidianPlateModule.class, asResource("quark", "obsidian_pressure_plate"), "automation/obsidian_pressure_plate", ObsidianPressurePlateScene::Working),
            new ModuleSceneInfo(PistonsMoveTileEntitiesModule.class, asResource("minecraft", "piston"), "automation/piston_move_te", PistonMoveTEScene::Working),
            new ModuleSceneInfo(PistonsMoveTileEntitiesModule.class, asResource("minecraft", "sticky_piston"), "automation/piston_move_te", PistonMoveTEScene::Working),
            new ModuleSceneInfo(RedstoneRandomizerModule.class, asResource("quark", "redstone_randomizer"), "automation/redstone_randomizer", RedstoneRandomizerScene::Working)
    );

    private static List<ModuleSceneInfo> buildingModules = List.of(
            new ModuleSceneInfo(GlassItemFrameModule.class, asResource("quark", "glass_item_frame"), "building/glass_item_frame", GlassItemFrameScene::Working),
            new ModuleSceneInfo(GlassItemFrameModule.class, asResource("quark", "glowing_glass_item_frame"), "building/glass_item_frame", GlassItemFrameScene::Working),
            new ModuleSceneInfo(GrateModule.class, asResource("quark", "grate"), "building/grate", IronGrateScene::Working),
            new ModuleSceneInfo(RopeModule.class, asResource("quark", "rope"), "building/rope", RopeScene::Working)
    );

    private static List<ModuleSceneInfo> toolsModules = List.of(
            new ModuleSceneInfo(SkullPikesModule.class, asResource("minecraft", "player_head"), "tools/skull_pike", SkullPikeScenes::Working)
    );

    private static List<ModuleSceneInfo> tweaksModules = List.of(
            new ModuleSceneInfo(EnhancedLaddersModule.class, asResource("minecraft", "ladder"), "tweaks/enhanced_ladders", EnhancedLadderScene::Working),
            new ModuleSceneInfo(ReplaceScaffoldingModule.class, asResource("minecraft", "scaffolding"), "tweaks/scaffolding_substitution", ScaffoldingSubstitutionScene::Working),
            new ModuleSceneInfo(SlimesToMagmaCubesModule.class, asResource("minecraft", "magma_cream"), "tweaks/slimes_to_magma_cubes", SlimesToMagmaCubesScene::Working)
    );

    private static List<ModuleSceneInfo> odditiesModules = List.of(
            new ModuleSceneInfo(PipesModule.class, asResource("quark", "pipe"), "oddities/pipe", PipeScene::Working)
    );

    private static final Map<String, List<ModuleSceneInfo>> MODULE_MAP = Map.of(
            "automation", automationModules,
            "building", buildingModules,
            "tools", toolsModules,
            "tweaks", tweaksModules,
            "oddities", odditiesModules/*
            "management", managementModules,
            "world", worldModules,
            "mobs", mobsModules,
            "client", clientModules,
            "experimental", experimentalModules*/
    );
}