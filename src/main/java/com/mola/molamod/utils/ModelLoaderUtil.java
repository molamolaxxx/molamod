package com.mola.molamod.utils;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

/**
 * @author : molamola
 * @Project: molamod
 * @Description:
 * @date : 2020-11-04 11:44
 **/
public class ModelLoaderUtil {

    public static void setCustomModelResourceLocation(Item item, Integer metaData, String id) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), id));
    }
}
