package com.github.tatercertified.server_side_industries.blocks;

import eu.pb4.polymer.api.block.PolymerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;

public class Slope_Conveyor extends StairsBlock implements PolymerBlock {

    public static final DirectionProperty DIRECTION = DirectionProperty.of("direction");
    public static final BooleanProperty REVERSED = BooleanProperty.of("reversed");

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(DIRECTION);
        builder.add(REVERSED);
    }
    public Slope_Conveyor(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
        setDefaultState(getStateManager().getDefaultState().with(FACING, baseBlockState.get(FACING)).with(HALF, BlockHalf.BOTTOM).with(DIRECTION, Direction.NORTH).with(REVERSED, false).with(WATERLOGGED, false));
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.DEEPSLATE_TILE_STAIRS;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(DIRECTION, ctx.getPlayerFacing()).with(HALF, BlockHalf.BOTTOM);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return Blocks.DEEPSLATE_TILE_STAIRS.getDefaultState().with(FACING, state.get(FACING)).with(HALF, BlockHalf.BOTTOM);
    }

    public static void onSteppedUpon(Entity entity, BlockState state) {
        conveyorMove(entity, state);
    }

    public static void conveyorMove(Entity entity, BlockState current) {
        Direction dir;
            dir = current.get(DIRECTION);
            //Down
            if (current.get(REVERSED)) {
                if (dir == Direction.NORTH) {
                    entity.teleport(entity.getX(), entity.getY() - 0.5, entity.getZ() + 0.5);
                } else if (dir == Direction.EAST) {
                    entity.teleport(entity.getX() - 0.5, entity.getY() - 0.5, entity.getZ());
                } else if (dir == Direction.SOUTH) {
                    entity.teleport(entity.getX(), entity.getY() - 0.5, entity.getZ() - 0.5);
                } else {
                    entity.teleport(entity.getX() + 0.5, entity.getY() - 0.5, entity.getZ());
                }
            } else {
                //Up
                if (dir == Direction.NORTH) {
                    entity.teleport(entity.getX(), entity.getY() + 0.5, entity.getZ() - 0.5);
                } else if (dir == Direction.EAST) {
                    entity.teleport(entity.getX() + 0.5, entity.getY() + 0.5, entity.getZ());
                } else if (dir == Direction.SOUTH) {
                    entity.teleport(entity.getX(), entity.getY() + 0.5, entity.getZ() + 0.5);
                } else {
                    entity.teleport(entity.getX() - 0.5, entity.getY() + 0.5, entity.getZ());
                }
            }
        entity.velocityDirty = true;
        entity.velocityModified = true;
    }
}
