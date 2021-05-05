package com.mola.molamod;

import com.mola.molamod.factory.CustomHandlerManager;
import com.mola.molamod.items.magic.ExplosionMagicBook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * @author : molamola
 * @Project: molamod
 * @Description:
 * @date : 2020-11-04 10:23
 **/
public class MolaTab extends CreativeTabs {
    public MolaTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(CustomHandlerManager.getItemHandler()
                .getItem(ExplosionMagicBook.class));
    }
}
