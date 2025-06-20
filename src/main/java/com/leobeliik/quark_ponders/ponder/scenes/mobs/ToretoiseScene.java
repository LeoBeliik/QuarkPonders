package com.leobeliik.quark_ponders.ponder.scenes.mobs;

import com.leobeliik.quark_ponders.ponder.PonderAux;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.enums.PonderGuiTextures;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.TripWireHookBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.Vec3;
import org.violetmoon.quark.content.automation.block.FeedingTroughBlock;
import org.violetmoon.quark.content.automation.block.IronRodBlock;
import org.violetmoon.quark.content.mobs.entity.Toretoise;
import org.violetmoon.quark.content.mobs.module.ToretoiseModule;

import java.util.ArrayList;
import java.util.List;

public class ToretoiseScene {

    public static void Working(SceneBuilder builder, SceneBuildingUtil util) {
        PonderSceneBuilder scene = new PonderSceneBuilder(builder.getScene());
        BlockPos toretoise = util.grid().at(2, 1, 2);

        var tot = scene.world().createEntity(level -> createToretoise(level, toretoise, 2));

        PonderAux.setScene(scene, util, "quark_toretoise");
        scene.idle(10);

        //explain module
        scene.overlay().showText(60)
                .text("quark_toretoise.text_1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(toretoise.getCenter());
        scene.idle(90);

        scene.overlay().showText(80)
                .text("quark_toretoise.text_2")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(toretoise.getCenter());
        scene.idle(100);

        scene.overlay().showText(40)
                .text("quark_toretoise.text_3")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(toretoise.getCenter());
        scene.idle(60);

        scene.overlay().showControls(toretoise.getCenter(), Pointing.UP, 20).withItem(Items.WOODEN_PICKAXE.getDefaultInstance());
        scene.idle(5);
        scene.world().modifyEntity(tot, Entity::discard);
        tot = scene.world().createEntity(level -> createToretoise(level, toretoise, 0));
        var item = scene.world().createItemEntity(toretoise.getCenter(), new Vec3(0, 0.5, 0), Items.RAW_IRON.getDefaultInstance());
        scene.idle(40);
        scene.world().modifyEntity(item, Entity::discard);

        //aoe attack
        scene.overlay().showText(60)
                .text("quark_toretoise.text_4")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(toretoise.getCenter());
        scene.idle(80);
        scene.overlay().showControls(toretoise.getCenter(), Pointing.DOWN, 20).withItem(Items.WOODEN_SWORD.getDefaultInstance());
        scene.idle(5);
        scene.world().modifyEntity(tot, entity -> {
            ((Toretoise) entity).angeryTicks = 20;
            ((Toretoise) entity).hurtTime = 5;
        });
        scene.idle(20);
        for (int x = 0; x < 4; x++) {
            for (int z = 0; z < 4; z++) {
                scene.effects().emitParticles(util.vector().of(x, 1, z),
                        scene.effects().particleEmitterWithinBlockSpace(ParticleTypes.CLOUD, Vec3.ZERO), 3, 1);
            }
        }
        scene.idle(10);
        scene.overlay().showText(60)
                .text("quark_toretoise.text_5")
                .colored(PonderPalette.BLUE)
                .placeNearTarget()
                .pointAt(toretoise.getCenter());
        scene.idle(100);

        //transport
        scene.overlay().showText(60)
                .text("quark_toretoise.text_6")
                .colored(PonderPalette.GREEN)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(toretoise.getCenter());
        scene.idle(80);

        scene.overlay().showText(40)
                .text("quark_toretoise.text_7")
                .placeNearTarget()
                .pointAt(toretoise.getCenter());
        scene.idle(60);

        scene.overlay().showControls(toretoise.getCenter(), Pointing.DOWN, 20).withItem(Items.LEAD.getDefaultInstance()).showing(PonderGuiTextures.ICON_DISABLE);
        scene.idle(40);
        var boat = scene.world().createEntity(level -> {
            Entity mob = EntityType.BOAT.create(level);
            mob.setPos(toretoise.north().getCenter().add(0, -0.25, 0));
            mob.setYRot(90);
            return mob;
        });
        scene.idle(5);
        scene.overlay().showControls(toretoise.north().getCenter(), Pointing.UP, 20).showing(PonderGuiTextures.ICON_DISABLE);
        scene.idle(40);
        scene.world().modifyEntity(boat, Entity::discard);
        scene.idle(20);
        scene.overlay().showText(40)
                .text("quark_toretoise.text_8")
                .placeNearTarget()
                .pointAt(toretoise.getCenter());
        scene.idle(60);

        List<ElementLink<WorldSectionElement>> sections = new ArrayList<>();
        for (int x = 5; x >= 0; x--) {
            sections.add(scene.world().showIndependentSection(util.select().position(x, 1, 2), Direction.DOWN));
            scene.world().setBlock(util.grid().at(x, 1, 2), Blocks.RAIL.defaultBlockState().setValue(RailBlock.SHAPE, RailShape.EAST_WEST), false);
            scene.idle(5);
        }
        final Minecart[] a = new Minecart[1];
        var cart = scene.world().createEntity(level -> {
            Minecart minecart = EntityType.MINECART.create(level);
            Vec3 pos = util.grid().at(4, 1, 2).getCenter();
            int rotation = 90;
            minecart.setPos(pos);
            minecart.xo = pos.x;
            minecart.yo = pos.y;
            minecart.zo = pos.z;
            minecart.yRotO = rotation;
            minecart.setYRot(rotation);
            a[0] = minecart;
            return minecart;
        });
        scene.idle(20);
        PonderAux.entityMove(cart, scene, 2, Direction.EAST);
        scene.world().modifyEntity(tot, entity -> entity.startRiding(a[0]));
        scene.idle(40);
        for (int i = 0; i < 15; i++) {
            scene.idle(1);
            scene.world().modifyEntity(cart, entity -> entity.move(MoverType.SELF, new Vec3(-0.2, 0, 0)));
            scene.world().modifyEntity(tot, entity -> entity.move(MoverType.SELF, new Vec3(-0.2, 0, 0)));
            if (i >= 14)
                scene.world().modifyEntity(cart, Entity::discard);
        }
        scene.world().modifyEntity(tot, Entity::discard);

        //feed
        for (ElementLink<WorldSectionElement> section : sections) {
            scene.world().hideIndependentSection(section, Direction.UP);
        }
        scene.idle(40);
        tot = scene.world().createEntity(level -> createToretoise(level, toretoise, 0));
        scene.idle(20);
        scene.overlay().showText(60)
                .text("quark_toretoise.text_9")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(toretoise.getCenter());
        scene.idle(80);
        for (int i = 0; i < 3; i++) {
            scene.overlay().showControls(toretoise.west().getCenter(), Pointing.DOWN, 20).withItem(Items.GLOW_BERRIES.getDefaultInstance());
            scene.idle(30);
        }

        scene.world().modifyEntity(tot, Entity::discard);
        var tot2 = scene.world().createEntity(level -> createToretoise(level, toretoise, 1));

        scene.overlay().showText(40)
                .text("quark_toretoise.text_10")
                .colored(PonderPalette.BLUE)
                .placeNearTarget()
                .independent(40);
        scene.idle(60);

        var section = scene.world().showIndependentSection(util.select().position(0, 1, 2), Direction.DOWN);
        scene.world().setBlock(util.grid().at(0, 1, 2), PonderAux.getBlock("feeding_trough").defaultBlockState().setValue(FeedingTroughBlock.FULL, true), false);
        scene.overlay().showText(60)
                .text("quark_toretoise.text_11")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.grid().at(0, 1, 2).getCenter());
        scene.idle(70);
        scene.world().setBlock(util.grid().at(0, 1, 2), Blocks.AIR.defaultBlockState(), true);
        scene.overlay().showText(60)
                .text("quark_toretoise.text_12")
                .colored(PonderPalette.BLUE)
                .placeNearTarget()
                .independent(40);
        scene.idle(80);

        //tripwire
        scene.world().modifyEntity(tot2, Entity::discard);
        tot = scene.world().createEntity(level -> createToretoise(level, toretoise, 0));
        section = scene.world().showIndependentSection(util.select().fromTo(0, 2, 0, 5, 5, 5), Direction.DOWN);
        scene.world().setBlock(util.grid().at(2, 2, 0), Blocks.REDSTONE_LAMP.defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(2, 2, 4), Blocks.REDSTONE_LAMP.defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(2, 2, 1), Blocks.TRIPWIRE_HOOK.defaultBlockState()
                .setValue(TripWireHookBlock.ATTACHED, true).setValue(TripWireHookBlock.FACING, Direction.SOUTH), false);
        scene.world().setBlock(util.grid().at(2, 2, 3), Blocks.TRIPWIRE_HOOK.defaultBlockState()
                .setValue(TripWireHookBlock.ATTACHED, true).setValue(TripWireHookBlock.FACING, Direction.NORTH), false);
        scene.world().setBlock(util.grid().at(2, 2, 2), Blocks.TRIPWIRE.defaultBlockState(), false);
        scene.idle(50);

        scene.overlay().showText(60)
                .text("quark_toretoise.text_13")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(toretoise.above().getCenter().add(0, -0.25, 0));
        scene.idle(80);

        scene.overlay().showControls(toretoise.west().getCenter(), Pointing.UP, 20).withItem(Items.GLOW_BERRIES.getDefaultInstance());
        scene.idle(10);
        scene.world().modifyEntity(tot, Entity::discard);
        tot2 = scene.world().createEntity(level -> createToretoise(level, toretoise, 3));
        scene.idle(5);
        scene.world().toggleRedstonePower(util.select().fromTo(2, 2, 0, 2, 2, 4));
        scene.idle(40);

        //iron rod
        scene.world().hideIndependentSection(section, Direction.UP);
        scene.idle(20);
        scene.world().setBlocks(util.select().fromTo(0, 2, 0, 5, 5, 5), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(toretoise.above(3), Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.DOWN), false);
        scene.world().setBlock(toretoise.above(2), PonderAux.getBlock("iron_rod").defaultBlockState().setValue(IronRodBlock.FACING, Direction.DOWN), false);
        scene.world().showIndependentSection(util.select().fromTo(0, 2, 0, 5, 5, 5), Direction.DOWN);
        scene.idle(40);
        scene.overlay().showText(60)
                .text("quark_toretoise.text_14")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(toretoise.above(2).getCenter());
        scene.idle(80);

        scene.world().setBlock(toretoise.above(3), Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.DOWN)
                .setValue(PistonBaseBlock.EXTENDED, true), false);
        scene.world().setBlock(toretoise.above(2), Blocks.PISTON_HEAD.defaultBlockState()
                .setValue(PistonHeadBlock.FACING, Direction.DOWN).setValue(PistonHeadBlock.TYPE, PistonType.STICKY), false);
        scene.world().setBlock(toretoise.above(), PonderAux.getBlock("iron_rod").defaultBlockState().setValue(IronRodBlock.FACING, Direction.DOWN), false);
        scene.idle(5);
        scene.world().createItemEntity(toretoise.getCenter(), new Vec3(0, 1, 0), Items.REDSTONE.getDefaultInstance());
        scene.world().createEntity(level -> createToretoise(level, toretoise, 0));
        scene.world().modifyEntity(tot2, Entity::discard);
        scene.idle(10);
        scene.world().setBlock(toretoise.above(3), Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.DOWN), false);
        scene.world().setBlock(toretoise.above(2), PonderAux.getBlock("iron_rod").defaultBlockState().setValue(IronRodBlock.FACING, Direction.DOWN), false);
        scene.world().setBlock(toretoise.above(), Blocks.AIR.defaultBlockState(), false);
        scene.markAsFinished();
    }

    private static Toretoise createToretoise(Level level, BlockPos toretoise, int ore) {
        //PonderAux#newEntity doesn't place the toretoise in the correct place for some reason?
        Toretoise t = new Toretoise(ToretoiseModule.toretoiseType, level);
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("oreType", ore);
        t.load(nbt);
        t.setPos(toretoise.getCenter().add(0, -0.5, 0));
        t.setYRot(90);
        t.yRotO = 90;
        return t;
    }
}
