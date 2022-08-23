package com.github.tatercertified.server_side_industries.blocks;

import eu.pb4.polymer.api.block.PolymerBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static com.github.tatercertified.server_side_industries.blocks.SSI_Blocks.SLOPED_CONVEYORS;

public class Slope_Conveyor extends StairsBlock implements PolymerBlock {

    public Slope_Conveyor(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.DEEPSLATE_TILE_STAIRS;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(HALF, BlockHalf.BOTTOM);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return Blocks.DEEPSLATE_TILE_STAIRS.getDefaultState().with(FACING, state.get(FACING)).with(HALF, BlockHalf.BOTTOM);
    }

    public static void onSteppedUpon(BlockState state, Entity entity, World world) {
        conveyorMove(entity, state, world);
    }

    public static void conveyorMove(Entity entity, BlockState state, World world) {
        BlockPos.Mutable entity_pos_down = entity.getBlockPos().down().mutableCopy();
        BlockPos.Mutable entity_pos_down2 = entity.getBlockPos().down(2).mutableCopy();
        Direction terracotta;
        if (world.getBlockState(entity_pos_down).isOf(Blocks.MAGENTA_GLAZED_TERRACOTTA)) {
            terracotta = world.getBlockState(entity_pos_down).get(GlazedTerracottaBlock.FACING);
        } else if (world.getBlockState(entity_pos_down2).isOf(Blocks.MAGENTA_GLAZED_TERRACOTTA)) {
            terracotta = world.getBlockState(entity_pos_down2).get(GlazedTerracottaBlock.FACING);
        } else return;
        Direction stairs = state.get(FACING);

        //Up
        if (stairs == Direction.NORTH && terracotta == Direction.SOUTH) entity.teleport(entity.getX(), entity.getY() + 0.5, entity.getZ() - 0.5 );
        else if (stairs == Direction.EAST && terracotta == Direction.WEST) entity.teleport(entity.getX() + 0.5, entity.getY() + 0.5, entity.getZ());
        else if (stairs == Direction.SOUTH && terracotta == Direction.NORTH) entity.teleport(entity.getX(), entity.getY() + 0.5, entity.getZ() + 0.5);
        else if (stairs == Direction.WEST && terracotta == Direction.EAST) entity.teleport(entity.getX() - 0.5, entity.getY() + 0.5, entity.getZ());
        //Down
        else if (stairs == Direction.NORTH && terracotta == Direction.NORTH) entity.teleport(entity.getX(), entity.getY() - 0.5, entity.getZ() + 0.5 );
        else if (stairs == Direction.EAST && terracotta == Direction.EAST) entity.teleport(entity.getX() - 0.5, entity.getY() - 0.5, entity.getZ());
        else if (stairs == Direction.SOUTH && terracotta == Direction.SOUTH) entity.teleport(entity.getX(), entity.getY() - 0.5, entity.getZ() - 0.5);
        else if (stairs == Direction.WEST && terracotta == Direction.WEST) entity.teleport(entity.getX() + 0.5, entity.getY() - 0.5, entity.getZ());
        checkLastStep(stairs, entity_pos_down, world, entity);
        entity.velocityDirty = true;
        entity.velocityModified = true;
    }
    public static void checkLastStep(Direction direction, BlockPos.Mutable mutable, World world, Entity entity) {

        if (direction.equals(Direction.NORTH) && !world.getBlockState(mutable.south().down()).isIn(SLOPED_CONVEYORS)) {
            entity.teleport(entity.getX(), entity.getY() + 0.5, entity.getZ());
        }
        else if (direction.equals(Direction.EAST) && !world.getBlockState(mutable.west().down()).isIn(SLOPED_CONVEYORS)) {
            entity.teleport(entity.getX(), entity.getY() + 0.5, entity.getZ());
        }
        else if (direction.equals(Direction.SOUTH) && !world.getBlockState(mutable.north().down()).isIn(SLOPED_CONVEYORS)) {
            entity.teleport(entity.getX(), entity.getY() + 0.5, entity.getZ());
        }
        else if (direction.equals(Direction.WEST) && !world.getBlockState(mutable.east().down()).isIn(SLOPED_CONVEYORS)) {
            entity.teleport(entity.getX(), entity.getY() + 0.5, entity.getZ());
        }
    }
}
