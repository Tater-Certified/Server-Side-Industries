package com.github.tatercertified.server_side_industries.blocks;

import eu.pb4.polymer.api.block.PolymerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.util.BlockRotation;

public class GuidanceBlock extends GlazedTerracottaBlock implements PolymerBlock {
    public GuidanceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.MAGENTA_GLAZED_TERRACOTTA;
    }


    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return super.rotate(state, rotation);
    }
}
