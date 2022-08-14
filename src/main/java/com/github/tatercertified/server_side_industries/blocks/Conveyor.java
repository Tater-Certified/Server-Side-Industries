package com.github.tatercertified.server_side_industries.blocks;

import eu.pb4.polymer.api.block.PolymerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class Conveyor extends Block implements PolymerBlock {
    public static double conveyor_speed;
    public Conveyor(Settings settings) {
        super(settings);
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.BLACK_CONCRETE_POWDER;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (state == SSI_Blocks.SLOW_CONVEYOR.getDefaultState()) {
            conveyor_speed = 0.05;
        } else if((state == SSI_Blocks.MEDIUM_CONVEYOR.getDefaultState())) {
            conveyor_speed = 0.2;
        } else if ((state == SSI_Blocks.FAST_CONVEYOR.getDefaultState())) {
            conveyor_speed = 1;
        } else {
            conveyor_speed = 2;
        }
        conveyorMove(entity, state.getBlock(), world);
        super.onSteppedOn(world, pos, state, entity);
    }

    public static void conveyorMove(Entity entity, Block block, World world) {
        BlockPos.Mutable entity_pos = entity.getBlockPos().mutableCopy();
            if (world.getBlockState(entity_pos.down(2)).isOf(Blocks.MAGENTA_GLAZED_TERRACOTTA)){
            Direction terracotta = world.getBlockState(entity_pos.down(2)).get(GlazedTerracottaBlock.FACING);
            //directions
            BlockPos blockPos = null;
            if (terracotta == Direction.NORTH) blockPos = entity_pos.south();
            if (terracotta == Direction.EAST) blockPos = entity_pos.west();
            if (terracotta == Direction.SOUTH) blockPos = entity_pos.north();
            if (terracotta == Direction.WEST) blockPos = entity_pos.east();


            //center alignment
            if (!entity.isSneaking() && terracotta.getAxis() == Direction.Axis.Z && (entity.getX() - entity_pos.getX() != 0.5)) {
                double center = entity.getX() - entity_pos.getX()-0.5;
                entity.teleport(entity.getX() - center, entity.getY(), entity.getZ());
            } else if (!entity.isSneaking() && terracotta.getAxis() == Direction.Axis.X && (entity.getZ() - entity_pos.getZ() != 0.5)) {
                double center = entity.getZ() - entity_pos.getZ()-0.5;
                entity.teleport(entity.getX(), entity.getY(), entity.getZ() - center);
            }

            if (world.getBlockState(blockPos).isOf(block)) entity.teleport(entity.getX(), entity.getBlockY()+1.3, entity.getZ());
            if(terracotta == Direction.NORTH) entity.setVelocity(0,0, conveyor_speed);
            if (terracotta == Direction.EAST) entity.setVelocity(conveyor_speed*-1,0,0);
            if (terracotta == Direction.SOUTH) entity.setVelocity(0,0,conveyor_speed*-1);
            if (terracotta == Direction.WEST) entity.setVelocity(conveyor_speed,0,0);
        }
        entity.velocityDirty = true;
        entity.velocityModified = true;
    }
}
