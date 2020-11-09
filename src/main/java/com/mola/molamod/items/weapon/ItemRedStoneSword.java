package com.mola.molamod.items.weapon;

import com.mola.molamod.annotation.CustomItem;
import com.mola.molamod.items.IModelRender;
import com.mola.molamod.material.ToolMaterials;
import com.mola.molamod.utils.LoggerUtil;
import com.mola.molamod.utils.ModelLoaderUtil;
import net.minecraft.item.ItemSword;
import org.apache.logging.log4j.Logger;

/**
 * @author : molamola
 * @Project: molamod
 * @Description: 红石剑
 * @date : 2020-11-04 12:16
 **/
@CustomItem
public class ItemRedStoneSword extends ItemSword implements IModelRender {

    private Logger logger = LoggerUtil.getLogger();

    public ItemRedStoneSword() {
        // 设置材质为红石
        super(ToolMaterials.REDSTONE);
        // 属性
        String name = "red_stone_sword";
        this.setUnlocalizedName("mola." + name);
        this.setRegistryName(name);
        this.setMaxStackSize(1);
    }

    @Override
    public void onItemRender() {
        logger.info("[item] 红石剑开始渲染");
        ModelLoaderUtil.setCustomModelResourceLocation(this, 0, "inventory");
    }
}
