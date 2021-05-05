package com.mola.molamod.handlers;

import com.mola.molamod.MolaMod;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description:
 * @date : 2021-05-04 01:31
 **/
public class CustomCraftHandler {

    /**
     * 无形工艺，不需要特定结构摆放的合成配方
     */
    public static void register() {
        GameRegistry.addShapelessRecipe(new ResourceLocation(MolaMod.MODID,"EXPLOSION_MAGIC_BOOK_RECIPE"),null,
                CustomItemHandler.ItemStackHolder.EXPLOSION_MAGIC_BOOK_STACK,
                Ingredient.fromItem(Items.BOOK),
                Ingredient.fromItem(Items.STICK));
    }
}
