package com.github.tatercertified.server_side_industries.blocks;

import eu.pb4.polymer.api.block.PolymerBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class Conveyor extends SlabBlock implements PolymerBlock {
    public static double conveyor_speed;
    public Conveyor(Settings settings) {
        super(settings);
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.DEEPSLATE_TILE_SLAB;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM);
    }

    @Override
    public BlockState getPolymerBlockState(ServerPlayerEntity player, BlockState state) {
        return Blocks.DEEPSLATE_TILE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM);
    }

    public static void onSteppedUpon(World world, BlockState state, Entity entity) {
        if (state == SSI_Blocks.SLOW_CONVEYOR.getDefaultState()) {
            conveyor_speed = 0.05;
        } else if((state == SSI_Blocks.MEDIUM_CONVEYOR.getDefaultState())) {
            conveyor_speed = 0.2;
        } else if ((state == SSI_Blocks.FAST_CONVEYOR.getDefaultState())) {
            conveyor_speed = 1;
        } else if ((state == SSI_Blocks.INSANE_CONVEYOR.getDefaultState())){
            conveyor_speed = 2;
        }
        conveyorMove(entity, world);
    }

    public static void conveyorMove(Entity entity, World world) {
        BlockPos.Mutable entity_pos = entity.getBlockPos().mutableCopy();
        if (entity.isOnGround() && world.getBlockState(entity_pos.down()).isOf(Blocks.MAGENTA_GLAZED_TERRACOTTA)) {
            Direction terracotta = world.getBlockState(entity_pos.down()).get(GlazedTerracottaBlock.FACING);
            if (terracotta == Direction.NORTH) entity.setVelocity(0, 0, conveyor_speed);
            else if (terracotta == Direction.EAST) entity.setVelocity(conveyor_speed * -1, 0, 0);
            else if (terracotta == Direction.SOUTH) entity.setVelocity(0, 0, conveyor_speed * -1);
            else if (terracotta == Direction.WEST) entity.setVelocity(conveyor_speed, 0, 0);
        }
        if (entity.isOnGround() && world.getBlockState(entity_pos.down(2)).isOf(Blocks.MAGENTA_GLAZED_TERRACOTTA)) {
            Direction terracotta_angle = world.getBlockState(entity_pos.down(2)).get(GlazedTerracottaBlock.FACING);
            if (terracotta_angle == Direction.NORTH) {
                entity.setVelocity(entity.getVelocity().x, 0, conveyor_speed);
            } else if (terracotta_angle == Direction.EAST) {
                entity.setVelocity(conveyor_speed * -1, 0, entity.getVelocity().z);
            } else if (terracotta_angle == Direction.SOUTH) {
                entity.setVelocity(entity.getVelocity().x, 0, conveyor_speed * -1);
            } else if (terracotta_angle == Direction.WEST) {
                entity.setVelocity(conveyor_speed, 0, entity.getVelocity().z);
            }
        } else {
            //center alignment
            if (world.getBlockState(entity_pos.down()).isOf(Blocks.MAGENTA_GLAZED_TERRACOTTA)) {
                Direction terracotta = world.getBlockState(entity_pos.down()).get(GlazedTerracottaBlock.FACING);
                if (!entity.isLiving() && terracotta.getAxis() == Direction.Axis.Z && (entity.getX() - entity_pos.getX() != 0.5)) {
                    double center = entity.getX() - entity_pos.getX() - 0.5;
                    entity.teleport(entity.getX() - center, entity.getY(), entity.getZ());
                } else if (!entity.isLiving() && terracotta.getAxis() == Direction.Axis.X && (entity.getZ() - entity_pos.getZ() != 0.5)) {
                    double center = entity.getZ() - entity_pos.getZ() - 0.5;
                    entity.teleport(entity.getX(), entity.getY(), entity.getZ() - center);
                }
            }
        }
        entity.velocityDirty = true;
        entity.velocityModified = true;
    }
}
