package com.leobeliik.quark_ponders.registry;

import net.minecraft.resources.ResourceLocation;

interface RegistryUtils {
    static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", path);
    }

    static ResourceLocation asQuarkResource(String path) {
        return ResourceLocation.fromNamespaceAndPath("quark", path);
    }

}
