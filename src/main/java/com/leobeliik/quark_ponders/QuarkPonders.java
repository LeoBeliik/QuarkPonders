package com.leobeliik.quark_ponders;

import com.leobeliik.quark_ponders.registry.RegisterScenes;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(QuarkPonders.MODID)
public class QuarkPonders {
    public static final String MODID = "quark_ponders";

    public QuarkPonders(IEventBus modEventBus, ModContainer container) {
        Config.init(container);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(QuarkPonders::onClientSetup);
    }

    private static void onClientSetup(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new RegisterScenes());
    }
}
