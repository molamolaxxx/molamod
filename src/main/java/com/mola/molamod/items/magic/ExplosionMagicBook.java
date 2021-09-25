package com.mola.molamod.items.magic;

import com.google.common.collect.Maps;
import com.mola.molamod.MolaMod;
import com.mola.molamod.annotation.CustomItem;
import com.mola.molamod.constants.PositionEventCodeConstant;
import com.mola.molamod.items.IModelRender;
import com.mola.molamod.network.PositionSendPacket;
import com.mola.molamod.utils.LoggerUtil;
import com.mola.molamod.utils.ModelLoaderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description: 魔法书，可以制造爆炸效果
 * todo 1、鼠标指向传送到服务器 2、设置损耗
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
    private float explosionDamage = 3.5f;
    private float explosionDistance = 2.2f;

    private boolean useFire = false;
    private boolean brokingBlock = false;

    public ExplosionMagicBook() {
        if (this instanceof ExplosionMagicBookMax) {
            return;
        }
        // 设置该物品在Forge中的注册名（必选，通常和unlocalizedName一致）
        this.setRegistryName(name);
        // 设置该物品的非本地化名称
        this.setUnlocalizedName("mola." + name);
        // 设置该物品所在的创造模式物品栏列表
        this.setCreativeTab(MolaMod.MOLA_TAB);
        // 单个物品栈最多堆叠1个
        this.maxStackSize = 1;
        // 最多释放100次
        setMaxDamage(100);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        if (isInCoolingStatus.get()) {
            return new ActionResult(EnumActionResult.PASS, player.getHeldItem(handIn));
        }
        ItemStack stack = player.getHeldItem(handIn);

        // 1、客户端执行逻辑
        if (player instanceof EntityPlayerSP) {
            Vec3d hitVec = Minecraft.getMinecraft().objectMouseOver.hitVec;
            Vec3d playerVec = player.getPositionVector();
            Vec3d finalVec = getFinalExplosionPos(hitVec, playerVec);
            displayExplosion(world, player, finalVec);
        }
        // 损耗+1
        setDamage(stack, getDamage(stack) - 1);

        // 挥手
        player.swingArm(EnumHand.MAIN_HAND);
        return new ActionResult(EnumActionResult.PASS, stack);
    }

    @Override
    public void onItemRender() {
        logger.info("[item] 爆炸魔法书开始渲染");
        ModelLoaderUtil.setCustomModelResourceLocation(this, 0, "inventory");
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
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
     * @param finalVec
     */
    private void displayExplosion(World world, EntityPlayer player, Vec3d finalVec) {
        Map<String,String> params = Maps.newHashMap();
        params.put("className", this.getClass().getName());
        // 发送爆炸位置到服务器
        MolaMod.getNetwork().sendToServer(new PositionSendPacket(finalVec, PositionEventCodeConstant.MAGIC_BOOK_EXPLOSION, params));

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
