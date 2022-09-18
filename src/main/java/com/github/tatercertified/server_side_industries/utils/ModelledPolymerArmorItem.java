package com.github.tatercertified.server_side_industries.utils;// Created 2022-15-07T19:26:26

import eu.pb4.polymer.api.item.PolymerItem;
import eu.pb4.polymer.api.resourcepack.PolymerModelData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ampflower
 * @since ${version}
 **/
public class ModelledPolymerArmorItem extends ArmorItem implements PolymerItem {
    private final PolymerModelData customModelData;

    public ModelledPolymerArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings,
                                    PolymerModelData customModelData) {
        super(material, slot, settings);
        this.customModelData = customModelData;
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return customModelData.item();
    }

    @Override
    public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return customModelData.value();
    }
}
