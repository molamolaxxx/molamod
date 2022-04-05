package com.mola.molamod.handlers;

import com.google.common.collect.Maps;
import com.mola.molamod.MolaMod;
import com.mola.molamod.items.IModelRender;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

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

    /**
     * 注册物品
     * @param item
     */
    public void add(Item item) {
        itemMap.putIfAbsent(item.getClass(), item);
    }

    /**
     * 获取物品
     * @param clazz
     * @return
     */
    public <T> T getItem(Class<T> clazz) {
        return (T) itemMap.get(clazz);
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
        for (Item item : this.getItemList()) {
            event.getRegistry().register(item);
            // 渲染物品
            if (item instanceof IModelRender && !MolaMod.isServer()) {
                ((IModelRender) item).onItemRender();
            }
        }
    }
}
