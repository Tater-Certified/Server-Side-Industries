package com.github.tatercertified.server_side_industries.blocks;

import eu.pb4.polymer.api.block.PolymerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Conveyor extends SlabBlock implements PolymerBlock {
    public static final DirectionProperty DIRECTION = DirectionProperty.of("direction");
    public static final DirectionProperty OFFSET = DirectionProperty.of("offset");

    public static double conveyor_speed;

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(DIRECTION);
        builder.add(OFFSET);
    }

    public Conveyor(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM).with(WATERLOGGED, false));
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.DEEPSLATE_TILE_SLAB;
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM).with(DIRECTION, ctx.getPlayerFacing()).with(OFFSET, Direction.UP).with(WATERLOGGED, false);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return Blocks.DEEPSLATE_TILE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM);
    }

    public static void onSteppedUpon(BlockState state, Entity entity) {
        if (state.getBlock() == SSI_Blocks.SLOW_CONVEYOR) {
            conveyor_speed = 0.05;
        } else if(state.getBlock() == SSI_Blocks.MEDIUM_CONVEYOR) {
            conveyor_speed = 0.2;
        } else if (state.getBlock() == SSI_Blocks.FAST_CONVEYOR) {
            conveyor_speed = 1;
        } else if (state.getBlock() == SSI_Blocks.INSANE_CONVEYOR){
            conveyor_speed = 2;
        }
        conveyorMove(entity, state);
    }
    public static void conveyorMove(Entity entity, BlockState blockState) {
        Direction dir = blockState.get(DIRECTION);
        Vec3d velo = entity.getVelocity();

            if (dir == Direction.NORTH) {
                entity.setVelocity(0, velo.y, conveyor_speed*-1);

            } else if (dir == Direction.EAST) {
                entity.setVelocity(conveyor_speed, velo.y, 0);

            } else if (dir == Direction.SOUTH) {
                entity.setVelocity(0, velo.y, conveyor_speed);

            } else {
                entity.setVelocity(conveyor_speed*-1, velo.y, 0);

            }

            //Offsets
            Direction offset = blockState.get(OFFSET);
            if (offset != Direction.UP) {
                if (offset == Direction.NORTH && dir.getAxis() == Direction.Axis.X) {
                    entity.setVelocity(0, velo.y, conveyor_speed * -1);
                } else if (offset == Direction.SOUTH && dir.getAxis() == Direction.Axis.X) {
                    entity.setVelocity(0, velo.y, conveyor_speed);
                } else if (offset == Direction.EAST && dir.getAxis() == Direction.Axis.Z) {
                    entity.setVelocity(conveyor_speed, velo.y, 0);
                } else {
                    entity.setVelocity(conveyor_speed * -1, velo.y, 0);
                }
            }

            //Centering
            if (!entity.isLiving()) {
                if (dir == Direction.NORTH || dir == Direction.SOUTH) {
                    entity.teleport(entity.getBlockX() + 0.5, entity.getY(), entity.getZ());
                } else {
                    entity.teleport(entity.getX(), entity.getY(), entity.getBlockZ() + 0.5);
                }
            }
            entity.velocityDirty = true;
            entity.velocityModified = true;
    }
}
