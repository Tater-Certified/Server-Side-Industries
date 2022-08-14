package com.github.tatercertified.server_side_industries.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.github.tatercertified.server_side_industries.Main.modid;

public class SSI_Blocks {

    public static Block SLOW_CONVEYOR = conveyor();
    public static Block MEDIUM_CONVEYOR = conveyor();
    public static Block FAST_CONVEYOR = conveyor();
    public static Block INSANE_CONVEYOR = conveyor();

    private static Block conveyor() {
        return new Conveyor(FabricBlockSettings.of(Material.STONE).strength(1.5f, 6.0f));
    }

    public static void register(String path, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(modid, path), block);
        Registry.register(Registry.ITEM, new Identifier(modid, path), new BlockItem(block, new FabricItemSettings()));
    }

    public static void initBlocks() {
        register("slow_conveyor", SLOW_CONVEYOR);
        register("medium_conveyor", MEDIUM_CONVEYOR);
        register("fast_conveyor", FAST_CONVEYOR);
        register("insane_conveyor", INSANE_CONVEYOR);
    }
}
