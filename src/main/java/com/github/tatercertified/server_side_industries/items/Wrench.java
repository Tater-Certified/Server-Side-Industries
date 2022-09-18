package com.github.tatercertified.server_side_industries.items;

import com.github.tatercertified.server_side_industries.utils.ModelledPolymerItem;
import eu.pb4.holograms.api.holograms.WorldHologram;
import eu.pb4.polymer.api.resourcepack.PolymerModelData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.github.tatercertified.server_side_industries.blocks.Conveyor.ConveyorFacing;
import static com.github.tatercertified.server_side_industries.blocks.SSI_Blocks.CONVEYORS;

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

        if (context.getHand() == Hand.MAIN_HAND) {
            populateHologram(wrenchgram, player, context);
        }

        if (context.getHand() == Hand.OFF_HAND) {
            populateHologram(wrenchgram, player, context);
        }
        return super.useOnBlock(context);
    }

    public void populateHologram(WorldHologram hologram, PlayerEntity player, ItemUsageContext context) {
        Vec3d height = new Vec3d(context.getBlockPos().getX(), context.getBlockPos().getY()+3, context.getBlockPos().getZ());
        hologram.setPosition(height);
        hologram.setText(0, Text.of("Configure " + context.getWorld().getBlockState(context.getBlockPos()).getBlock().toString().replace("{", " ").replace("}", "")));
        if (player.getWorld().getBlockState(context.getBlockPos()).isIn(CONVEYORS)) {
            hologram.setText(1, Text.of("Direction: " + ConveyorFacing(context)));
        } else {
            hologram.removeElement(1);
        }
        hologram.addPlayer((ServerPlayerEntity) player);
        hologram.show();
        final ScheduledExecutorService thread = Executors.newScheduledThreadPool(1);
        if (!running) {
            thread.scheduleAtFixedRate(() -> {
                running = true;
                System.out.println(player.getHandItems().toString());
                if (!player.getHandItems().toString().contains("wrench")) {
                   hologram.hide();
                   running = false;
                   thread.shutdownNow();
                }
            }, 0,1, TimeUnit.SECONDS);
        }
    }
}
