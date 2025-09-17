package com.leobeliik.quark_ponders;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import java.util.List;

public class Config {

    private static ModConfigSpec CLIENT_CONFIG;
    private static ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    public static ModConfigSpec.ConfigValue<Object> comment;

    //automation
    public static ModConfigSpec.ConfigValue<List<? extends String>> chainConnectBlocks_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> chute_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> dispenser_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> enderWatcher_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> feedingTrough_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> gravisand_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> ironRod_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> metalButtons_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> obsidianPressurePlate_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> pistonsMoveTE_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> redstoneRandomizer_module;

    //building
    public static ModConfigSpec.ConfigValue<List<? extends String>> glassItemFrames_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> grate_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> rope_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> stools_module;

    //Mobs
    public static ModConfigSpec.ConfigValue<List<? extends String>> oretoise_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> foxhound_module;

    //Tools
    public static ModConfigSpec.ConfigValue<List<? extends String>> skullPikes_module;

    //Tweaks
    public static ModConfigSpec.ConfigValue<List<? extends String>> enhancedLadders_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> replaceScaffolding_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> slimeToMagma_module;

    //Oddities
    public static ModConfigSpec.ConfigValue<List<? extends String>> pipes_module;
    public static ModConfigSpec.ConfigValue<List<? extends String>> magnet_module;

    static void init(ModContainer container) {
        buildConfig();
        container.registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }

    private static void buildConfig() {
        Automation();

        Building();

        Mobs();

        Tools();

        Tweaks();

        Oddities();

        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void Oddities() {
        CLIENT_BUILDER.comment("Oddities module settings").push("Oddities");

        pipes_module = CLIENT_BUILDER.comment("List of items for the Copper Pipes ponder scene, sepparated by ','.")
                .defineList("pipes_module", List.of("quark:pipe", "quark:encased_pipe"), () -> "", o -> o instanceof String);

        magnet_module = CLIENT_BUILDER.comment("List of items for the Magnet ponder scene, sepparated by ','.")
                .defineList("magnet_module", List.of("quark:magnet"), () -> "", o -> o instanceof String);

        CLIENT_BUILDER.pop();
    }

    private static void Tweaks() {
        CLIENT_BUILDER.comment("Tweaks module settings").push("Tweaks");

        enhancedLadders_module = CLIENT_BUILDER.comment("List of items for the Enhanced Ladders ponder scene, sepparated by ','.")
                .defineList("enhancedLadders_module", List.of("minecraft:ladder", "quark:spruce_ladder", "quark:birch_ladder", "quark:jungle_ladder",
                        "quark:acacia_ladder", "quark:dark_oak_ladder", "quark:crimson_ladder", "quark:warped_ladder", "quark:mangrove_ladder",
                        "quark:bamboo_ladder", "quark:cherry_ladder", "quark:iron_ladder", "quark:azalea_ladder", "quark:ancient_ladder"), () -> "",
                        o -> o instanceof String);

        replaceScaffolding_module = CLIENT_BUILDER.comment("List of items for the Replace scaffoldings ponder scene, sepparated by ','.")
                .defineList("replaceScaffolding_module", List.of("minecraft:scaffolding"), () -> "", o -> o instanceof String);

        slimeToMagma_module = CLIENT_BUILDER.comment("List of items for the Slime to magma cubes ponder scene, sepparated by ','.")
                .defineList("slimeToMagma_module", List.of("minecraft:magma_cream"), () -> "", o -> o instanceof String);

        CLIENT_BUILDER.pop();
    }

    private static void Tools() {
        CLIENT_BUILDER.comment("Tools module settings").push("Tools");

        skullPikes_module = CLIENT_BUILDER.comment("List of items for the Pikes ponder scene, sepparated by ','.")
                .defineList("skullPikes_module", List.of("minecraft:creeper_head", "minecraft:zombie_head", "minecraft:skeleton_skull"), () -> "",
                        o -> o instanceof String);

        CLIENT_BUILDER.pop();
    }

    private static void Mobs() {
        CLIENT_BUILDER.comment("Mobs module settings").push("Mobs");

        oretoise_module = CLIENT_BUILDER.comment("List of items for the Oretoise ponder scene, sepparated by ','.")
                .defineList("oretoise_module", List.of("minecraft:coal", "minecraft:raw_iron", "minecraft:raw_copper", "minecraft:redstone",
                        "minecraft:lapis_lazuli"), () -> "", o -> o instanceof String);

        foxhound_module = CLIENT_BUILDER.comment("List of items for the FoxHound ponder scene, sepparated by ','.")
                .defineList("foxhound_module", List.of("minecraft:furnace", "minecraft:blast_furnace", "minecraft:smoker", "quark:blackstone_furnace",
                        "quark:deepslate_furnace"), () -> "", o -> o instanceof String);

        CLIENT_BUILDER.pop();
    }

    private static void Building() {
        CLIENT_BUILDER.comment("Building module settings").push("Building");

        glassItemFrames_module = CLIENT_BUILDER.comment("List of items for the Glass Items Frames ponder scene, sepparated by ','.")
                .defineList("glassItemFrames_module", List.of("quark:glass_item_frame", "quark:glowing_glass_item_frame"), () -> "", o -> o instanceof String);

        grate_module = CLIENT_BUILDER.comment("List of items for the Grate ponder scene, sepparated by ','.")
                .defineList("grate_module", List.of("quark:grate"), () -> "", o -> o instanceof String);

        rope_module = CLIENT_BUILDER.comment("List of items for the Rope ponder scene, sepparated by ','.")
                .defineList("rope_module", List.of("quark:rope"), () -> "", o -> o instanceof String);

        stools_module = CLIENT_BUILDER.comment("List of items for the Stools ponder scene, sepparated by ','.")
                .defineList("stools_module", List.of("quark:black_stool", "quark:blue_stool", "quark:brown_stool", "quark:cyan_stool", "quark:gray_stool",
                        "quark:green_stool", "quark:light_blue_stool", "quark:light_gray_stool", "quark:lime_stool", "quark:magenta_stool", "quark:orange_stool",
                        "quark:pink_stool", "quark:purple_stool", "quark:red_stool", "quark:white_stool", "quark:yellow_stool"), () -> "", o -> o instanceof String);

        CLIENT_BUILDER.pop();
    }

    private static void Automation() {
        //TODO see if there's a good way to do this.
        CLIENT_BUILDER.comment("You can set what items are used to ponder each scene in the mod.",
                "Noted that disabled Modules in Quark will also disable the corresponding Ponder scene");
        CLIENT_BUILDER.comment("Automation module settings").push("Automation");

        chainConnectBlocks_module = CLIENT_BUILDER.comment("List of items for the Chains connect blocks ponder scene, sepparated by ','.")
                .defineList("chainConnectBlocks_module", List.of("minecraft:chain"), () -> "", o -> o instanceof String);

        chute_module = CLIENT_BUILDER.comment("List of items for the Chute ponder scene, sepparated by ','.")
                .defineList("chute_module", List.of("quark:chute"), () -> "", o -> o instanceof String);

        dispenser_module = CLIENT_BUILDER.comment("List of items for the Dispenser places blocks ponder scene, sepparated by ','.")
                .defineList("dispenser_module", List.of("minecraft:dispenser"), () -> "", o -> o instanceof String);

        enderWatcher_module = CLIENT_BUILDER.comment("List of items for the Ender Watcher ponder scene, sepparated by ','.")
                .defineList("enderWatcher_module", List.of("quark:ender_watcher"), () -> "", o -> o instanceof String);

        feedingTrough_module = CLIENT_BUILDER.comment("List of items for the Feeding Trough ponder scene, sepparated by ','.")
                .defineList("feedingTrough_module", List.of("quark:feeding_trough"), () -> "", o -> o instanceof String);

        gravisand_module = CLIENT_BUILDER.comment("List of items for the Gravisand ponder scene, sepparated by ','.")
                .defineList("gravisand_module", List.of("quark:gravisand"), () -> "", o -> o instanceof String);

        ironRod_module = CLIENT_BUILDER.comment("List of items for the Iron Rod ponder scene, sepparated by ','.")
                .defineList("ironRod_module", List.of("quark:iron_rod"), () -> "", o -> o instanceof String);

        metalButtons_module = CLIENT_BUILDER.comment("List of items for the Metal Buttons ponder scene, sepparated by ','.")
                .defineList("metalButtons_module", List.of("quark:iron_button", "quark:gold_button"), () -> "", o -> o instanceof String);

        obsidianPressurePlate_module = CLIENT_BUILDER.comment("List of items for the Obsidian pressure plate ponder scene, sepparated by ','.")
                .defineList("obsidianPressurePlate_module", List.of("quark:obsidian_pressure_plate"), () -> "", o -> o instanceof String);

        pistonsMoveTE_module = CLIENT_BUILDER.comment("List of items for the Pistons move Tile Entities ponder scene, sepparated by ','.")
                .defineList("pistonsMoveTE_module", List.of("minecraft:piston", "minecraft:sticky_piston"), () -> "", o -> o instanceof String);

        redstoneRandomizer_module = CLIENT_BUILDER.comment("List of items for the Redstone Randomizer ponder scene, sepparated by ','.")
                .defineList("redstoneRandomizer_module", List.of("quark:redstone_randomizer"), () -> "", o -> o instanceof String);
        CLIENT_BUILDER.pop();
    }
}