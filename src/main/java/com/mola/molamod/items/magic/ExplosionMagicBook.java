package com.mola.molamod.items.magic;

import com.mola.molamod.MolaMod;
import com.mola.molamod.annotation.CustomItem;
import com.mola.molamod.items.IModelRender;
import com.mola.molamod.utils.LoggerUtil;
import com.mola.molamod.utils.ModelLoaderUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description: 魔法书，可以制造爆炸效果
 * @date : 2020-11-08 11:57
 **/
@CustomItem
public class ExplosionMagicBook extends Item implements IModelRender {

    private Logger logger = LoggerUtil.getLogger();

    private static final String name = "explosion_magic_book";

    /**
     * 是否在冷却状态
     */
    private AtomicBoolean isInCoolingStatus = new AtomicBoolean(false);

    /**
     * 冷却时间，默认1s和等级相关
     */
    private long coolingDuring = 1000L;
    private final Timer coolingTimer = new Timer();

    /**
     * 属性
     */
    // 伤害爆炸威力/范围，普通苦力怕为3.0，TNT为4.0或者更大一点，末影水晶为6.0
    private float explosionDamage = 2.8f;
    private float explosionDistance = 2.15f;

    private boolean useFire = true;
    private boolean brokingBlock = false;

    public ExplosionMagicBook() {
        // 设置该物品在Forge中的注册名（必选，通常和unlocalizedName一致）
        this.setRegistryName(name);
        // 设置该物品的非本地化名称
        this.setUnlocalizedName("mola." + name);
        // 设置该物品所在的创造模式物品栏列表
        this.setCreativeTab(MolaMod.MOLA_TAB);
        // 单个物品栈最多堆叠1个
        this.maxStackSize = 1;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        if (isInCoolingStatus.get()) {
            return new ActionResult(EnumActionResult.PASS, player.getHeldItem(handIn));
        }
        // 1、服务端执行逻辑，只用WorldServer才能实现爆炸效果， 否则用不了
        if (null != world && world instanceof WorldServer) {

            // 爆炸逻辑
            Vec3d hitVec = rayTraceHand(player, 30d).hitVec;
            Vec3d playerVec = player.getPositionVector();
            // 绘制火焰粒子效果
            spawnParticle(world, player);
            // 爆炸距离可调节
            Vec3d finalVec = getFinalExplosionPos(hitVec, playerVec);
            // 爆炸
            displayExplosion(world, player, finalVec.x, hitVec.y, finalVec.z);
            return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
        }
        // 挥手
        player.swingArm(EnumHand.MAIN_HAND);
        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(handIn));
    }

    @Override
    public void onItemRender() {
        logger.info("[item] 爆炸魔法书开始渲染");
        ModelLoaderUtil.setCustomModelResourceLocation(this, 0, "inventory");
    }

    private void coolingRecoveryTimer(int level) {
        float mul  = 1f;
        if (level != 0) {
            mul = 10 / level;
            if (mul > 1) mul = 1;
        }
        coolingTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        isInCoolingStatus.compareAndSet(true, false);
                    }
                }, (long) (coolingDuring*mul));
    }

    /**
     * 爆炸
     * @param world
     * @param player
     * @param x
     * @param y
     * @param z
     */
    private void displayExplosion(World world, EntityPlayer player, double x, double y, double z) {
        world.newExplosion(null, x, y, z, explosionDamage, useFire, brokingBlock);
        // 技能冷却计时
        isInCoolingStatus.compareAndSet(false, true);
        coolingRecoveryTimer(player.experienceLevel);
    }

    /**
     * 根据爆炸距离，算出爆炸发生的真正位置
     * @param hitVec
     * @param playerVec
     * @return
     */
    public Vec3d getFinalExplosionPos(Vec3d hitVec, Vec3d playerVec) {
        float distance = explosionDistance;
        if (distance < 1.0f) {
            distance = 1.0f;
        }
        double nextX = playerVec.x + (hitVec.x - playerVec.x) * distance;
        double nextY = playerVec.y + (hitVec.y - playerVec.y) * distance;
        double nextZ = playerVec.z + (hitVec.z - playerVec.z) * distance;
        return new Vec3d(nextX, nextY, nextZ);
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
                EnumParticleTypes.FLAME, // 火焰粒子
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

    public static RayTraceResult rayTraceHand(EntityLivingBase entity, double length)
    {
        Vec3d startPos = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight()/1.38, entity.posZ);
        Vec3d endPos = startPos.add(new Vec3d(entity.getLookVec().x * length, entity.getLookVec().y * length, entity.getLookVec().z * length));
        return entity.world.rayTraceBlocks(startPos, endPos);
    }

    public float getExplosionDamage() {
        return explosionDamage;
    }

    public float getExplosionDistance() {
        return explosionDistance;
    }

    public void setExplosionDistance(float explosionDistance) {
        this.explosionDistance = explosionDistance;
    }

    public boolean isUseFire() {
        return useFire;
    }

    public void setUseFire(boolean useFire) {
        this.useFire = useFire;
    }

    public boolean isBrokingBlock() {
        return brokingBlock;
    }

    public void setBrokingBlock(boolean brokingBlock) {
        this.brokingBlock = brokingBlock;
    }

    public void setExplosionDamage(float explosionDamage) {
        this.explosionDamage = explosionDamage;
    }

    @Override
    public String toString() {
        return "ExplosionMagicBook{" +
                "explosionDamage=" + explosionDamage +
                ", explosionDistance=" + explosionDistance +
                ", useFire=" + useFire +
                ", brokingBlock=" + brokingBlock +
                '}';
    }
}
