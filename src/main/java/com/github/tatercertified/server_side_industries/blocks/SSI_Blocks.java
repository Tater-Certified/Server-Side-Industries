package com.github.tatercertified.server_side_industries.blocks;

import eu.pb4.polymer.api.item.PolymerBlockItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.github.tatercertified.server_side_industries.Main.modid;

public class SSI_Blocks {

    public static Block SLOW_CONVEYOR = new Conveyor(FabricBlockSettings.of(Material.STONE).strength(1.5f, 6.0f));
    public static Block MEDIUM_CONVEYOR = new Conveyor(FabricBlockSettings.of(Material.STONE).strength(1.5f, 6.0f));
    public static Block FAST_CONVEYOR = new Conveyor(FabricBlockSettings.of(Material.STONE).strength(1.5f, 6.0f));
    public static Block INSANE_CONVEYOR = new Conveyor(FabricBlockSettings.of(Material.STONE).strength(1.5f, 6.0f));
    public static Block SLOW_INCLINED_CONVEYOR = new Slope_Conveyor(Blocks.DEEPSLATE_TILE_STAIRS.getDefaultState(), FabricBlockSettings.of(Material.STONE).strength(1.5f, 6.0f));
    public static Block MEDIUM_INCLINED_CONVEYOR = new Slope_Conveyor(Blocks.DEEPSLATE_TILE_STAIRS.getDefaultState(), FabricBlockSettings.of(Material.STONE).strength(1.5f, 6.0f));
    public static Block FAST_INCLINED_CONVEYOR = new Slope_Conveyor(Blocks.DEEPSLATE_TILE_STAIRS.getDefaultState(), FabricBlockSettings.of(Material.STONE).strength(1.5f, 6.0f));
    public static Block INSANE_INCLINED_CONVEYOR = new Slope_Conveyor(Blocks.DEEPSLATE_TILE_STAIRS.getDefaultState(), FabricBlockSettings.of(Material.STONE).strength(1.5f, 6.0f));
    public static Item SLOW_CONVEYOR_I = new PolymerBlockItem(SLOW_CONVEYOR, new FabricItemSettings().group(ItemGroup.REDSTONE), Items.DEEPSLATE_TILE_SLAB);
    public static Item MEDIUM_CONVEYOR_I = new PolymerBlockItem(MEDIUM_CONVEYOR, new FabricItemSettings().group(ItemGroup.REDSTONE), Items.DEEPSLATE_TILE_SLAB);
    public static Item FAST_CONVEYOR_I = new PolymerBlockItem(FAST_CONVEYOR, new FabricItemSettings().group(ItemGroup.REDSTONE), Items.DEEPSLATE_TILE_SLAB);
    public static Item INSANE_CONVEYOR_I = new PolymerBlockItem(INSANE_CONVEYOR, new FabricItemSettings().group(ItemGroup.REDSTONE), Items.DEEPSLATE_TILE_SLAB);
    public static Item SLOW_INCLINED_CONVEYOR_I = new PolymerBlockItem(SLOW_INCLINED_CONVEYOR, new FabricItemSettings().group(ItemGroup.REDSTONE), Items.DEEPSLATE_TILE_STAIRS);
    public static Item MEDIUM_INCLINED_CONVEYOR_I = new PolymerBlockItem(MEDIUM_INCLINED_CONVEYOR, new FabricItemSettings().group(ItemGroup.REDSTONE), Items.DEEPSLATE_TILE_STAIRS);
    public static Item FAST_INCLINED_CONVEYOR_I = new PolymerBlockItem(FAST_INCLINED_CONVEYOR, new FabricItemSettings().group(ItemGroup.REDSTONE), Items.DEEPSLATE_TILE_STAIRS);
    public static Item INSANE_INCLINED_CONVEYOR_I = new PolymerBlockItem(INSANE_INCLINED_CONVEYOR, new FabricItemSettings().group(ItemGroup.REDSTONE), Items.DEEPSLATE_TILE_STAIRS);

    public static final TagKey<Block> CONVEYORS = TagKey.of(Registry.BLOCK_KEY, new Identifier(modid, "conveyors"));
    public static final TagKey<Block> SLOPED_CONVEYORS = TagKey.of(Registry.BLOCK_KEY, new Identifier(modid, "sloped_conveyors"));

    public static void registerBlock(String path, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(modid, path), block);
    }
    public static void registerItem(String path, Item item) {
        Registry.register(Registry.ITEM, new Identifier(modid, path), item);
    }

    public static void initBlocks() {
        registerBlock("slow_conveyor", SLOW_CONVEYOR);
        registerBlock("medium_conveyor", MEDIUM_CONVEYOR);
        registerBlock("fast_conveyor", FAST_CONVEYOR);
        registerBlock("insane_conveyor", INSANE_CONVEYOR);
        registerBlock("slow_sloped_conveyor", SLOW_INCLINED_CONVEYOR);
        registerBlock("medium_sloped_conveyor", MEDIUM_INCLINED_CONVEYOR);
        registerBlock("fast_sloped_conveyor", FAST_INCLINED_CONVEYOR);
        registerBlock("insane_sloped_conveyor", INSANE_INCLINED_CONVEYOR);
        registerItem("slow_conveyor", SLOW_CONVEYOR_I);
        registerItem("medium_conveyor", MEDIUM_CONVEYOR_I);
        registerItem("fast_conveyor", FAST_CONVEYOR_I);
        registerItem("insane_conveyor", INSANE_CONVEYOR_I);
        registerItem("slow_sloped_conveyor", SLOW_INCLINED_CONVEYOR_I);
        registerItem("medium_sloped_conveyor", MEDIUM_INCLINED_CONVEYOR_I);
        registerItem("fast_sloped_conveyor", FAST_INCLINED_CONVEYOR_I);
        registerItem("insane_sloped_conveyor", INSANE_INCLINED_CONVEYOR_I);
    }
}
