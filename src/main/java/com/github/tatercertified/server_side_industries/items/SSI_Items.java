package com.github.tatercertified.server_side_industries.items;

import com.github.tatercertified.server_side_industries.groups.SSI_Group;
import com.github.tatercertified.server_side_industries.utils.PolySSIUtils;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class SSI_Items {
    public static Item WRENCH = wrench("wrench");

    public static Item wrench(String path) {
        return PolySSIUtils.ofModelled(path, Items.STICK, SSI_Group.SSI_GROUP,
                (settings, polymerModelData) -> new Wrench(settings.maxCount(1), polymerModelData));
    }
    public static void init() {
    }
    private SSI_Items() {
    }
}
