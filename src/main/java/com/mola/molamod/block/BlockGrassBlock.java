package com.mola.molamod.block;

import com.mola.molamod.MolaMod;
import com.mola.molamod.annotation.CustomBlock;
import com.mola.molamod.factory.CustomHandlerManager;
import com.mola.molamod.items.IModelRender;
import com.mola.molamod.utils.LoggerUtil;
import com.mola.molamod.utils.ModelLoaderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import org.apache.logging.log4j.Logger;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description:
 * @date : 2022-04-04 16:53
 **/
@CustomBlock
public class BlockGrassBlock extends Block implements IModelRender {

    private static final String name = "grass_block";

    private Logger logger = LoggerUtil.getLogger();

    public BlockGrassBlock() {
        super(Material.GROUND);
        this.setUnlocalizedName("mola." + name);
        this.setRegistryName(name);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.PLANT);
        // 设置该物品所在的创造模式物品栏列表
        this.setCreativeTab(MolaMod.MOLA_TAB);
    }


    @Override
    public void onItemRender() {
        logger.info("[item] 草方块开始渲染");
        ItemBlock itemBlock = CustomHandlerManager.getItemHandler().getItemBlock(getRegistryName());
        ModelLoaderUtil.setCustomModelResourceLocation(itemBlock, 0, "inventory");
    }
}
