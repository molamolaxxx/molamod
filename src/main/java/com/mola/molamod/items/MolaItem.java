package com.mola.molamod.items;

import com.mola.molamod.MolaMod;
import com.mola.molamod.annotation.CustomItem;
import com.mola.molamod.utils.ModelLoaderUtil;
import net.minecraft.item.Item;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description: 右键瞬移
 * @date : 2021-05-06 15:16
 **/
@CustomItem
public class MolaItem extends Item implements IModelRender {

    private static final String name = "mola_item";

    public MolaItem() {
        // 设置该物品在Forge中的注册名（必选，通常和unlocalizedName一致）
        this.setRegistryName(name);
        // 设置该物品的非本地化名称
        this.setUnlocalizedName("mola." + name);
        // 设置该物品所在的创造模式物品栏列表
        this.setCreativeTab(MolaMod.MOLA_TAB);
        // 单个物品栈最多堆叠1个
        this.maxStackSize = 1;
    }


    @Override
    public void onItemRender() {
        ModelLoaderUtil.setCustomModelResourceLocation(this, 0, "inventory");
    }
}
