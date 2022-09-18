package com.github.tatercertified.server_side_industries;

import com.github.tatercertified.server_side_industries.items.SSI_Items;
import eu.pb4.polymer.api.resourcepack.PolymerRPUtils;
import net.fabricmc.api.ModInitializer;

import static com.github.tatercertified.server_side_industries.blocks.SSI_Blocks.initBlocks;

public class Main implements ModInitializer {
    public static String modid = "server-side-industries" ;

    @Override
    public void onInitialize() {
        initBlocks();
        PolymerRPUtils.addAssetSource(modid);
        SSI_Items.init();
    }
}