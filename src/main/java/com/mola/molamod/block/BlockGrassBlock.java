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
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;
import scala.actors.threadpool.ExecutorService;
import scala.actors.threadpool.Executors;

import java.util.ArrayList;
import java.util.List;

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

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

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
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        if (worldIn.isRemote) {
            UpdatePositionTask updatePositionTask = new UpdatePositionTask(worldIn, pos, 2, 2);
//            executorService.submit(updatePositionTask);
            updatePositionTask.run();
        }
    }

    @Override
    public void onItemRender() {
        logger.info("[item] 草方块开始渲染");
        ItemBlock itemBlock = CustomHandlerManager.getItemHandler().getItemBlock(getRegistryName());
        ModelLoaderUtil.setCustomModelResourceLocation(itemBlock, 0, "inventory");
    }

    public class UpdatePositionTask implements Runnable {

        private World worldIn;
        private BlockPos currentPos;
        private int paddingX;
        private int paddingZ;

        public UpdatePositionTask(World worldIn, BlockPos startPos, int paddingX, int paddingZ) {
            this.worldIn = worldIn;
            this.currentPos = startPos;
            this.paddingX = paddingX;
            this.paddingZ = paddingZ;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1; i++) {
                List<BlockPos> allNeedMoveBlockPosition = getAllNeedMoveBlockPosition();
                for (BlockPos blockPos : allNeedMoveBlockPosition) {
                    IBlockState blockState = worldIn.getBlockState(blockPos);
                    worldIn.setBlockState(blockPos.add(0, 1, 0), blockState, 2);
                    worldIn.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
                }

                this.currentPos = currentPos.add(0, 1, 0);

//                waitOneSecond();

                logger.info("UpdatePositionTask run");
            }
        }

        /**
         * 获取所有需要被移动的方块
         * @return
         */
        private List<BlockPos> getAllNeedMoveBlockPosition() {
            List<BlockPos> blockPosList = new ArrayList<>(paddingX * 2 + paddingZ * 2 + 4);
            for (int i = -paddingX; i <= paddingX; i++) {
                for (int j = -paddingZ; j <= paddingZ; j++) {
                    BlockPos blockPos = currentPos.add(i, 0, j);
                    blockPosList.add(blockPos);
                }
            }
            return blockPosList;
        }

        private void waitOneSecond() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
