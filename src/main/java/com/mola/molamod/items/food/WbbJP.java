package com.mola.molamod.items.food;

import com.mola.molamod.MolaMod;
import com.mola.molamod.annotation.CustomItem;
import com.mola.molamod.items.IModelRender;
import com.mola.molamod.utils.ModelLoaderUtil;
import net.minecraft.item.ItemFood;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description: 尾巴巴的脚皮
 * @date : 2021-05-07 02:07
 **/
@CustomItem
public class WbbJP extends ItemFood implements IModelRender {

    private static final String name = "wbb_jp";

    public WbbJP() {
        super(10, false);
        // 设置该物品在Forge中的注册名（必选，通常和unlocalizedName一致）
        this.setRegistryName(name);
        // 设置该物品的非本地化名称
        this.setUnlocalizedName("mola." + name);
        // 设置该物品所在的创造模式物品栏列表
        this.setCreativeTab(MolaMod.MOLA_TAB);
    }

    @Override
    public void onItemRender() {
        ModelLoaderUtil.setCustomModelResourceLocation(this, 0, "inventory");
    }
}
