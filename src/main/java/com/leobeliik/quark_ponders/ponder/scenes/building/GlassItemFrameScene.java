package com.leobeliik.quark_ponders.ponder.scenes.building;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import org.violetmoon.quark.content.building.entity.GlassItemFrame;

import java.util.ArrayList;
import java.util.List;

public class GlassItemFrameScene {
    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos frame = util.grid().at(2, 1, 2);
        var frameEntity = scene.world().createEntity(level -> new GlassItemFrame(level, frame, Direction.UP));
        ItemStack onShowItem = Items.STONE.getDefaultInstance();

        //start screen
        PonderAux.setAgentScene(scene, util, "quark_glass_item_frame");

        //explain the frame
        scene.overlay().showText(60)
                .text("quark_glass_item_frame.text_1")
                .pointAt(frame.getCenter().add(0, -0.5, 0))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(40);
        scene.overlay().showControls(frame.getCenter().add(0, -0.5, 0), Pointing.DOWN, 20).withItem(onShowItem);
        scene.idle(10);
        scene.world().modifyEntity(frameEntity, entity -> ((GlassItemFrame) entity).setItem(onShowItem));
        scene.idle(40);

        //show banner
        scene.rotateCameraY(360);
        scene.idle(20);
        scene.world().modifyEntity(frameEntity, Entity::discard);
        List<ElementLink<EntityElement>> frames = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                int finalI = i;
                int finalJ = j;
                frames.add(scene.world().createEntity(level -> new GlassItemFrame(level, util.grid().at(finalI, 1, finalJ), Direction.UP)));
            }
        }
        scene.overlay().showText(80)
                .text("quark_glass_item_frame.text_2")
                .pointAt(frame.getCenter().add(0, -0.5, 0))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(80);

        ItemStack banner = brickBanner();

        scene.overlay().showControls(frame.getCenter().add(0, -0.5, 0), Pointing.DOWN, 20).withItem(banner);
        scene.idle(10);
        for (ElementLink<EntityElement> element : frames) {
            scene.world().modifyEntity(element, entity -> ((GlassItemFrame) entity).setItem(banner));
        }
        scene.idle(40);

        //sign item frame
        scene.rotateCameraY(360);
        scene.idle(15);
        frames.forEach(entity -> scene.world().modifyEntity(entity, Entity::discard));
        scene.world().setBlock(frame, Blocks.OAK_SIGN.defaultBlockState(), false);
        frameEntity = scene.world().createEntity(level -> new GlassItemFrame(level, frame.north(), Direction.NORTH));
        scene.idle(40);
        scene.overlay().showText(80)
                .text("quark_glass_item_frame.text_3")
                .pointAt(frame.getCenter().add(0, -0.5, 0))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(60);
        scene.overlay().showControls(frame.getCenter().add(0, 0.5, 0), Pointing.DOWN, 20).withItem(onShowItem);
        var item = scene.world().createEntity(level ->
                PonderAux.staticItem(level, util.grid().at(2, 1, 2).getCenter().add(0.3, -0.1, -0.02), 52, Items.STONE.getDefaultInstance()));
        scene.world().modifyEntity(frameEntity, Entity::discard);
        scene.idle(40);
        scene.overlay().showText(60)
                .text("quark_glass_item_frame.text_4")
                .pointAt(frame.getCenter().add(0, 0.15, 0))
                .placeNearTarget();
        scene.idle(80);

        //glass item frames on chests
        scene.rotateCameraY(360);
        scene.idle(15);
        scene.world().modifyEntity(item, Entity::discard);
        scene.world().setBlock(frame, Blocks.CHEST.defaultBlockState(), false);
        frameEntity = scene.world().createEntity(level -> {
            GlassItemFrame f = new GlassItemFrame(level, frame.above(), Direction.UP);
            f.setItem(onShowItem);
            return f;
        });
        scene.idle(40);

        scene.overlay().showText(80)
                .text("quark_glass_item_frame.text_5")
                .pointAt(frame.above().getCenter().add(0, -0.5, 0))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(100);

        scene.overlay().showControls(frame.getCenter().add(0, 0.5, 0), Pointing.DOWN, 20).rightClick();
        scene.idle(5);
        scene.world().modifyBlockEntity(frame, ChestBlockEntity.class, cbe -> cbe.triggerEvent(1,2));
        scene.idle(20);
        scene.world().modifyBlockEntity(frame, ChestBlockEntity.class, cbe -> cbe.triggerEvent(1,0));
        scene.idle(20);

        //maps
        scene.rotateCameraY(360);
        scene.idle(15);
        scene.world().modifyEntity(frameEntity, Entity::discard);
        scene.world().setBlock(frame, Blocks.AIR.defaultBlockState(), false);
        frameEntity = scene.world().createEntity(level -> new GlassItemFrame(level, frame, Direction.UP));
        scene.idle(40);

        scene.overlay().showControls(frame.getCenter().add(0, -0.5, 0), Pointing.DOWN, 20).withItem(Items.MAP.getDefaultInstance());
        scene.idle(10);
        scene.world().modifyEntity(frameEntity, entity -> ((GlassItemFrame) entity).setItem(mapBanner()));

        scene.overlay().showText(80)
                .text("quark_glass_item_frame.text_6")
                .pointAt(frame.getCenter().add(0, -0.5, 0))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(100);
        scene.markAsFinished();
    }

    private static ItemStack brickBanner() {
        ItemStack banner = new ItemStack(Items.GRAY_BANNER);
        ListTag patternTags = new ListTag();
        CompoundTag bri = new CompoundTag();
        bri.putString("Pattern", "bri");
        bri.putInt("Color", DyeColor.RED.getId());
        patternTags.add(bri);
        CompoundTag blockEntityTag = new CompoundTag();
        blockEntityTag.put("Patterns", patternTags);
        banner.addTagElement("BlockEntityTag", blockEntityTag);
        return banner;
    }

    private static ItemStack mapBanner() {
        /*
        * Patterns: [{Pattern: "vh", Color: 7}, {Pattern: "br", Color: 7}, {Pattern: "tr", Color: 7}, {Pattern: "bl", Color: 0}, {Pattern: "tl", Color: 0}*/
        ItemStack banner = new ItemStack(Items.LIGHT_BLUE_BANNER);
        ListTag patternTags = new ListTag();

        CompoundTag glb = new CompoundTag();
        glb.putString("Pattern", "glb");
        glb.putInt("Color", DyeColor.GREEN.getId());
        patternTags.add(glb);

        CompoundTag blockEntityTag = new CompoundTag();
        blockEntityTag.put("Patterns", patternTags);
        banner.addTagElement("BlockEntityTag", blockEntityTag);
        return banner;
    }

}
