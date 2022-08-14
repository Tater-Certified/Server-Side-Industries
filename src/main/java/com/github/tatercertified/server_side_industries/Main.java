package com.github.tatercertified.server_side_industries;

import net.fabricmc.api.ModInitializer;

import static com.github.tatercertified.server_side_industries.blocks.SSI_Blocks.initBlocks;

public class Main implements ModInitializer {
    public static String modid = "server_side_industries" ;

    @Override
    public void onInitialize() {
        initBlocks();
    }
}