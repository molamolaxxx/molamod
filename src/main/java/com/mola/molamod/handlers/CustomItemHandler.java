package com.mola.molamod.handlers;

import com.google.common.collect.Maps;
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
    private Map<String, Item> itemMap = Maps.newConcurrentMap();

    /**
     * 注册物品
     * @param item
     */
    public void add(Item item) {
        itemMap.putIfAbsent(item.getRegistryName().toString(), item);
    }

    /**
     * 获取物品
     * @param name
     * @return
     */
    public Item getItem(String name) {
        return itemMap.get(name);
    }

    public List<Item> getItemList() {
        return itemMap.entrySet().stream()
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    /**
     * 总线调用，注册物品
     * @param event
     */
    public void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : this.getItemList()) {
            // 渲染物品
            if (item instanceof IModelRender) {
                ((IModelRender) item).onItemRender();
            }
            event.getRegistry().register(item);
        }
    }

}
