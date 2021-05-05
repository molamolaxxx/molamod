package com.mola.molamod.handlers;

import com.google.common.collect.Maps;
import com.mola.molamod.factory.CustomHandlerManager;
import com.mola.molamod.items.IModelRender;
import com.mola.molamod.items.magic.ExplosionMagicBook;
import com.mola.molamod.items.weapon.ItemRedStoneSword;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
    private Map<Class<? extends Item>, Item> itemMap = Maps.newConcurrentMap();

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
    public Item getItem(Class<? extends Item> clazz) {
        return itemMap.get(clazz);
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

    public static class ItemStackHolder{

        /**
         * 魔法书与其配方
         */
        public static ItemStack EXPLOSION_MAGIC_BOOK_STACK = getItemStack(ExplosionMagicBook.class);
        public static ItemStack BOOK_STACK_1 = new ItemStack(Item.getItemById(340),1);
        public static ItemStack STICK_STACK_1 = new ItemStack(Item.getItemById(280),1);

        public static ItemStack RED_STONE_SWORD_STACK = getItemStack(ItemRedStoneSword.class);

        public static ItemStack getItemStack(Class clazz) {
            return new ItemStack(CustomHandlerManager.getItemHandler().getItem(clazz));
        }

        public static ItemStack getItemStack(Class clazz, int amount) {
            return new ItemStack(CustomHandlerManager.getItemHandler().getItem(clazz), amount);
        }
    }

}
