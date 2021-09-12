package com.mola.molamod.items.tool;

import com.mola.molamod.MolaMod;
import com.mola.molamod.annotation.CustomItem;
import com.mola.molamod.items.IModelRender;
import com.mola.molamod.utils.CommandUtil;
import com.mola.molamod.utils.LoggerUtil;
import com.mola.molamod.utils.ModelLoaderUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description: 右键瞬移
 * @date : 2021-05-06 15:16
 **/
@CustomItem
public class MolaItem extends Item implements IModelRender {

    private static final String name = "mola_item";

    private final Timer waitingTimer = new Timer();

    private boolean waitingFlag = false;

    public MolaItem() {
        // 设置该物品在Forge中的注册名（必选，通常和unlocalizedName一致）
        this.setRegistryName(name);
        // 设置该物品的非本地化名称
        this.setUnlocalizedName("mola." + name);
        // 设置该物品所在的创造模式物品栏列表
        this.setCreativeTab(MolaMod.MOLA_TAB);
        // 单个物品栈最多堆叠12个
        this.maxStackSize = 12;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (worldIn instanceof WorldServer) {
            if (!waitingFlag) {
                BlockPos bedLocation = playerIn.bedLocation;
                if (null == bedLocation) {
                    CommandUtil.sendMessage(playerIn, "你好像没有床...");
                    return new ActionResult(EnumActionResult.PASS, stack);
                }
                // 特效
                spawnParticle(worldIn, playerIn);
                CommandUtil.sendMessage(playerIn, "准备回家...需要蓄力3s");
                waitingTimer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                try {

                                    MinecraftServer server = worldIn.getMinecraftServer();
                                    EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(playerIn.getName());
                                    NetHandlerPlayServer connection = player.connection;
                                    connection.setPlayerLocation(bedLocation.getX(), bedLocation.getY(), bedLocation.getZ(), playerIn.rotationYaw, playerIn.rotationPitch);
                                    CommandUtil.sendMessage(playerIn, "到家了...");
                                    stack.shrink(1);
                                } catch (Exception e) {
                                    LoggerUtil.getLogger().error("onItemRightClick error", e);
                                } finally {
                                    waitingFlag = false;
                                }
                            }
                        }, 3000L);
                waitingFlag = true;
            }
        }
        // 挥手
        playerIn.swingArm(EnumHand.MAIN_HAND);
        return new ActionResult(EnumActionResult.PASS, stack);
    }

    /**
     * EnumParticleTypes particleType,
     * boolean longDistance, double xCoord, double yCoord, double zCoord,
     * int numberOfParticles,
     * double xOffset, double yOffset, double zOffset,
     * double particleSpeed, int... particleArguments
     * @param world
     * @param player
     */
    public void spawnParticle(World world, EntityPlayer player) {
        Vec3d playerVec = player.getPositionVector();
        WorldServer worldServer = (WorldServer) world;
        worldServer.spawnParticle(
                EnumParticleTypes.SPIT, // 雪花粒子
                true,
                playerVec.x,
                playerVec.y + (double) player.eyeHeight,
                playerVec.z,
                50, // 粒子个数
                4.5,
                2,
                4.5,
                1.2,
                new int[0]
        );
    }

    @Override
    public void onItemRender() {
        ModelLoaderUtil.setCustomModelResourceLocation(this, 0, "inventory");
    }
}
