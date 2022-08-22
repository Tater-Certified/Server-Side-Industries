package com.github.tatercertified.server_side_industries.blocks;

import eu.pb4.polymer.api.block.PolymerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Direction;

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
    public BlockState getPolymerBlockState(ServerPlayerEntity player, BlockState state) {
        return Blocks.DEEPSLATE_TILE_STAIRS.getDefaultState().with(FACING, state.get(FACING)).with(HALF, BlockHalf.BOTTOM);
    }

    public static void onSteppedUpon(BlockState state, Entity entity) {
        conveyorMove(entity, state);
    }

    public static void conveyorMove(Entity entity, BlockState state) {
        Direction direction = state.get(FACING);

        if (direction == Direction.NORTH) entity.teleport(entity.getX(), entity.getY() + 0.5, entity.getZ() - 0.5 );
        if (direction == Direction.EAST) entity.teleport(entity.getX() + 0.5, entity.getY() + 0.5, entity.getZ());
        if (direction == Direction.SOUTH) entity.teleport(entity.getX(), entity.getY() + 0.5, entity.getZ() + 0.5);
        if (direction == Direction.WEST) entity.teleport(entity.getX() - 0.5, entity.getY() + 0.5, entity.getZ());
        entity.velocityDirty = true;
        entity.velocityModified = true;
    }

}
