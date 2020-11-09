package com.mola.molamod.material;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

/**
 * 自定义材质
 */
public class ToolMaterials {

    /**
     * 红石材料
     */
    public static final Item.ToolMaterial REDSTONE =
            EnumHelper.addToolMaterial(
                    "REDSTONE",
                    3,
                    222,
                    13.5F,
                    20.0F,
                    12);
}
