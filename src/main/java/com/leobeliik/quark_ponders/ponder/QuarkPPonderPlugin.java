package com.leobeliik.quark_ponders.ponder;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.leobeliik.quark_ponders.QuarkPonders.MODID;

public class QuarkPPonderPlugin implements PonderPlugin {
    @Override
    public @NotNull String getModId() {
        return MODID;
    }

    @Override
    public void registerScenes(@NotNull PonderSceneRegistrationHelper<ResourceLocation> helper) {
        RegisterScenes.register(helper);
    }

}
