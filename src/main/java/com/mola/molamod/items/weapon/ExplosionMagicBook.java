package com.mola.molamod.items.weapon;

import com.mola.molamod.annotation.CustomItem;
import com.mola.molamod.items.IModelRender;
import com.mola.molamod.utils.CommandUtil;
import com.mola.molamod.utils.LoggerUtil;
import com.mola.molamod.utils.ModelLoaderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
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
 * @Description: 魔法书，可以爆炸
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
     * 冷却时间，默认3s
     */
    private long coolingDuring = 3000L;
    private final Timer coolingTimer = new Timer();

    /**
     * 属性
     */
    // 伤害爆炸威力/范围，普通苦力怕为3.0，TNT为4.0或者更大一点，末影水晶为6.0
    private float explosionDamage = 2.5f;
    private float explosionDistance = 2.5f;

    private boolean useFire = true;
    private boolean brokingBlock = false;

    public ExplosionMagicBook() {
        // 设置该物品在Forge中的注册名（必选，通常和unlocalizedName一致）
        this.setRegistryName(name);
        // 设置该物品的非本地化名称
        this.setUnlocalizedName("mola." + name);
        // 设置该物品所在的创造模式物品栏列表
        this.setCreativeTab(CreativeTabs.COMBAT);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
//        CommandUtil.sendMessage(playerIn, "world = " + world.toString());
        if (isInCoolingStatus.get()) {
            CommandUtil.sendMessage(player, "技能正在冷却");
            return new ActionResult(EnumActionResult.PASS, player.getHeldItem(handIn));
        }

        // 1、只用WorldServer才能实现爆炸效果， 否则用不了
        if (null != world && world instanceof WorldServer) {
            // 爆炸逻辑
            CommandUtil.sendMessage(player, "魔法书启动【爆炸】");
            RayTraceResult lookingAt = Minecraft.getMinecraft().objectMouseOver;
            if (lookingAt != null) {
//                BlockPos pos = lookingAt.getBlockPos();
                Vec3d hitVec = lookingAt.hitVec;
                Vec3d playerVec = player.getPositionVector();
                CommandUtil.sendMessage(player, "hit = " + hitVec.toString());
//                CommandUtil.sendMessage(player, "block = " + pos.toString());
                CommandUtil.sendMessage(player, "pos = " + playerVec.toString());
                // 爆炸距离可调节
                Vec3d finalVec = getFinalExplosionPos(hitVec, playerVec);
                // 爆炸
                displayExplosion(world, player, finalVec.x, hitVec.y, finalVec.z);
                return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
            } else {
                CommandUtil.sendMessage(player, "光线追踪为空");
            }
        }
        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(handIn));
    }

    @Override
    public void onItemRender() {
        logger.info("[item] 爆炸魔法书开始渲染");
        ModelLoaderUtil.setCustomModelResourceLocation(this, 0, "inventory");
    }

    private void coolingRecoveryTimer() {
        coolingTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        isInCoolingStatus.compareAndSet(true, false);
                    }
                }, coolingDuring);
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
        coolingRecoveryTimer();
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
