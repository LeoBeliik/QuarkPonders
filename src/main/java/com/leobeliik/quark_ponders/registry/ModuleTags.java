package com.leobeliik.quark_ponders.registry;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

import static com.leobeliik.quark_ponders.registry.RegistryUtils.asQuarkResource;
import static com.leobeliik.quark_ponders.registry.RegistryUtils.asResource;

interface ModuleTags {
    List<ResourceLocation> heads = List.of(asResource("creeper_head"), asResource("zombie_head"), asResource("skeleton_skull"));
    List<ResourceLocation> ladders = List.of(asQuarkResource("spruce_ladder"), asQuarkResource("birch_ladder"), asQuarkResource("jungle_ladder"),
            asQuarkResource("acacia_ladder"), asQuarkResource("dark_oak_ladder"), asQuarkResource("crimson_ladder"), asQuarkResource("warped_ladder"),
            asQuarkResource("mangrove_ladder"), asQuarkResource("bamboo_ladder"), asQuarkResource("cherry_ladder"), asQuarkResource("iron_ladder"),
            asQuarkResource("azalea_ladder"), asQuarkResource("ancient_ladder"));
    List<ResourceLocation> glassFrames = List.of(asQuarkResource("glass_item_frame"), asQuarkResource("glowing_glass_item_frame"));
    List<ResourceLocation> metalButtons = List.of(asQuarkResource("iron_button"), asQuarkResource("gold_button"));
    List<ResourceLocation> pistons = List.of(asResource("piston"), asResource("sticky_piston"));
    List<ResourceLocation> ores = List.of(asResource("coal"), asResource("raw_iron"), asResource("raw_copper"), asResource("redstone"), asResource("lapis_lazuli"));
    List<ResourceLocation> pipes = List.of(asQuarkResource("pipe"), asQuarkResource("encased_pipe"));
}
