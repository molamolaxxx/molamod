package com.mola.molamod.handlers;

import com.google.common.collect.Maps;
import com.mola.molamod.MolaMod;
import com.mola.molamod.items.IModelRender;
import net.minecraft.block.Block;
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
public class CustomBlockHandler {

    /**
     * 方块列表
     */
    private Map<Class<? extends Block>, Block> blockMap = Maps.newLinkedHashMap();

    /**
     * 注册方块
     * @param block
     */
    public void add(Block block) {
        blockMap.putIfAbsent(block.getClass(), block);
    }

    /**
     * 获取物品
     * @param clazz
     * @return
     */
    public <T> T getItem(Class<T> clazz) {
        return (T) blockMap.get(clazz);
    }

    public List<Block> getBlockList() {
        return blockMap.entrySet().stream()
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    /**
     * 总线调用，注册物品，
     * @param event
     */
    public void registerBlocksAndRendering(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(this.getBlockList().toArray(new Block[0]));
        for (Block block : this.getBlockList()) {
            // 渲染物品
            if (block instanceof IModelRender && !MolaMod.isServer()) {
                ((IModelRender) block).onItemRender();
            }
        }
    }
}
