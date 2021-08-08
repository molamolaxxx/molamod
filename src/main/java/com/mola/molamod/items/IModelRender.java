package com.mola.molamod.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author : molamola
 * @Project: molamod
 * @Description:
 * @date : 2020-11-04 10:42
 **/
public interface IModelRender {

    /**
     * 物品渲染
     */
    @SideOnly(Side.CLIENT)
    void onItemRender();
}
