package com.github.tatercertified.server_side_industries.items;

import com.github.tatercertified.server_side_industries.utils.ModelledPolymerItem;
import eu.pb4.holograms.api.InteractionType;
import eu.pb4.holograms.api.elements.clickable.CubeHitboxHologramElement;
import eu.pb4.holograms.api.holograms.AbstractHologram;
import eu.pb4.holograms.api.holograms.WorldHologram;
import eu.pb4.polymer.api.resourcepack.PolymerModelData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.github.tatercertified.server_side_industries.blocks.Conveyor.DIRECTION;
import static com.github.tatercertified.server_side_industries.blocks.SSI_Blocks.CONVEYORS;
import static com.github.tatercertified.server_side_industries.blocks.SSI_Blocks.SLOPED_CONVEYORS;
import static com.github.tatercertified.server_side_industries.blocks.Slope_Conveyor.REVERSED;
import static net.minecraft.block.StairsBlock.FACING;

public class Wrench extends ModelledPolymerItem {
    public Wrench(Settings settings, PolymerModelData customModelData) {
        super(settings, customModelData);
    }

    public WorldHologram wrenchgram;

    boolean running;

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        assert player != null;

        Vec3d position = new Vec3d(context.getBlockPos().getX(), context.getBlockPos().getY(), context.getBlockPos().getZ());

        if (wrenchgram == null) wrenchgram = new WorldHologram(Objects.requireNonNull(player.getServer()).getWorld(player.world.getRegistryKey()), position);

        populateHologram(wrenchgram, player, context);

        return super.useOnBlock(context);
    }

    public void populateHologram(WorldHologram hologram, PlayerEntity player, ItemUsageContext context) {

        Vec3d height = new Vec3d(context.getBlockPos().getX() + 0.5, context.getBlockPos().getY()+3, context.getBlockPos().getZ() + 0.5);
        hologram.setPosition(height);
        hologram.setText(0, Text.of("Configure " + context.getWorld().getBlockState(context.getBlockPos()).getBlock().toString().replace("{", " ").replace("}", "")), true);
        BlockState current = player.getWorld().getBlockState(context.getBlockPos());

        //Direction

        if (current.isIn(CONVEYORS)) {
            hologram.setText(1, Text.of("Direction: " + current.get(DIRECTION)));
            hologram.addElement(new CubeHitboxHologramElement(1, new Vec3d(0, -0.08, 0)) {
                @Override
                public void onClick(AbstractHologram hologram, ServerPlayerEntity player, InteractionType type, @Nullable Hand hand, @Nullable Vec3d vec, int entityId) {
                    super.onClick(hologram, player, type, hand, vec, entityId);
                    BlockState current = player.getWorld().getBlockState(context.getBlockPos());
                    Direction now = current.get(DIRECTION).rotateYClockwise();
                    player.getWorld().setBlockState(context.getBlockPos(), current.with(DIRECTION, now));
                    if (current.isIn(SLOPED_CONVEYORS)) {
                        player.getWorld().setBlockState(context.getBlockPos(), current.with(FACING, now).with(DIRECTION, now));
                    }
                    hologram.setText(1, Text.of("Direction: " + now));
                }
            });
        } else {
            hologram.removeElement(1);
        }

        //Reversed
        if (current.isIn(SLOPED_CONVEYORS)) {
            hologram.setText(3, Text.of("Reversed: " + current.get(REVERSED)));
        } else {
            hologram.removeElement(3);
        }


        hologram.addPlayer((ServerPlayerEntity) player);
        hologram.show();

        final ScheduledExecutorService thread = Executors.newScheduledThreadPool(1);
        if (!running) {
            thread.scheduleAtFixedRate(() -> {
                running = true;
                if (!player.getHandItems().toString().contains("wrench")) {
                   hologram.hide();
                   running = false;
                   thread.shutdownNow();
                }
            }, 0,1, TimeUnit.SECONDS);
        }
    }
}
