package com.leobeliik.quark_ponders.registry;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.violetmoon.quark.base.Quark;
import java.util.List;
import java.util.Map;
import static com.leobeliik.quark_ponders.QuarkPonders.MODID;

public class RegisterScenes implements PonderPlugin, ModuleRegistry {

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

    private static final Map<String, List<ModuleSceneInfo>> MODULE_MAP = Map.of(
            "automation", automationModules,
            "building", buildingModules,
            "tools", toolsModules,
            "tweaks", tweaksModules,
            "oddities", odditiesModules,
            "mobs", mobsModules/*,
            "management", managementModules,
            "world", worldModules,
            "client", clientModules,
            "experimental", experimentalModules*/
    );

    private static void loadScenes(PonderSceneRegistrationHelper<ResourceLocation> helper, List<ModuleSceneInfo> modules) {
        if (modules != null)
            modules.stream().filter(info ->
                    Quark.ZETA.modules.isEnabled(info.module())).forEach(info -> {
                if (info.rl() instanceof List<?>)
                    helper.forComponents((List<ResourceLocation>) info.rl()).addStoryBoard(info.sbPath(), info.scene());
                else
                    helper.forComponents((ResourceLocation) info.rl()).addStoryBoard(info.sbPath(), info.scene());
            });

    }

}