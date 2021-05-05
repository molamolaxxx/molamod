package com.mola.molamod.items;

import com.mola.molamod.utils.ModelLoaderUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author : molamola
 * @Project: molamod
 * @Description:
 * @date : 2020-11-03 18:24
 **/
public class MyFirstItem extends Item implements IModelRender{

    private static final String name = "mola_item";

    public MyFirstItem() {
        // 设置该物品在Forge中的注册名（必选，通常和unlocalizedName一致）
        this.setRegistryName(name);
        // 设置该物品的非本地化名称
        this.setUnlocalizedName(name);
        // 设置该物品所在的创造模式物品栏列表
        this.setCreativeTab(CreativeTabs.MATERIALS);
        // 设置伤害
        this.setMaxDamage(10000);
    }

    @Override
    public void onItemRender() {
        // 设置模型地址
        ModelLoaderUtil.setCustomModelResourceLocation( this, 0, "inventory");
    }


    /**
     * 设置物品的名字
     * @param stack
     * @return
     */
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        Item item = stack.getItem();
        // 设置物品攻击
        stack.setItemDamage(10000);
        return "item.mola." + item.getRegistryName().toString().toLowerCase();
    }
}
