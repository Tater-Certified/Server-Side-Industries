package com.github.tatercertified.server_side_industries.groups;

import com.github.tatercertified.server_side_industries.items.SSI_Items;
import eu.pb4.polymer.api.item.PolymerItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.github.tatercertified.server_side_industries.Main.modid;

public class SSI_Group {
    public static final PolymerItemGroup SSI_GROUP = PolymerItemGroup.create(new Identifier(modid, "ssi_group"), Text.literal("Server Side Industries Items")).setIcon(() -> new ItemStack(SSI_Items.WRENCH));
}
