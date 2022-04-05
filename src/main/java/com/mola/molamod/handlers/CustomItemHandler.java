package com.mola.molamod.handlers;

import com.google.common.collect.Maps;
import com.mola.molamod.MolaMod;
import com.mola.molamod.items.IModelRender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import org.apache.logging.log4j.core.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : molamola
 * @Project: molamod
 * @Description:
 * @date : 2020-11-03 18:29
 **/
public class CustomItemHandler {

    /**
     * 物品列表
     */
    private Map<Class<? extends Item>, Item> itemMap = Maps.newLinkedHashMap();

    private Map<String, ItemBlock> itemBlockMap = Maps.newLinkedHashMap();

    /**
     * 注册物品
     * @param item
     */
    public void add(Item item) {
        itemMap.putIfAbsent(item.getClass(), item);
        if (item instanceof ItemBlock) {
            itemBlockMap.put(item.getRegistryName().toString(), (ItemBlock)item);
        }
    }

    /**
     * 获取物品
     * @param clazz
     * @return
     */
    public <T> T getItem(Class<T> clazz) {
        return (T) itemMap.get(clazz);
    }

    public ItemBlock getItemBlock(ResourceLocation registryName) {
        Assert.requireNonEmpty(registryName, "registryName is null");
        return itemBlockMap.get(registryName.toString());
    }

    public List<Item> getItemList() {
        return itemMap.entrySet().stream()
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    /**
     * 总线调用，注册物品，
     * @param event
     */
    public void registerItemsAndRendering(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(this.getItemList().toArray(new Item[0]));
        for (Item item : this.getItemList()) {
            // 渲染物品
            if (item instanceof IModelRender && !MolaMod.isServer()) {
                ((IModelRender) item).onItemRender();
            }
        }
    }
}
