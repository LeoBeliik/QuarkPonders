package com.leobeliik.quark_ponders.ponder;

import com.leobeliik.quark_ponders.ponder.scenes.automation.*;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.scene.PonderStoryBoard;
import net.minecraft.resources.ResourceLocation;
import org.violetmoon.quark.base.Quark;
import org.violetmoon.quark.content.automation.module.*;
import org.violetmoon.zeta.module.ZetaModule;

import java.util.List;
import java.util.Map;

class RegisterScenes {
    private record ModuleSceneInfo(Class<? extends ZetaModule> module, ResourceLocation rl, String sbPath, PonderStoryBoard scene) {}

    static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
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

    private static final Map<String, List<ModuleSceneInfo>> MODULE_MAP = Map.of(
            "automation", automationModules/*,
            "building", buildingModules,
            "management", managementModules,
            "tools", toolsModules,
            "tweaks", tweaksModules,
            "world", worldModules,
            "mobs", mobsModules,
            "client", clientModules,
            "experimental", experimentalModules,
            "oddities", odditiesModules*/
    );
}