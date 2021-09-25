package com.mola.molamod.network.handler.impl;

import com.mola.molamod.factory.CustomHandlerManager;
import com.mola.molamod.items.magic.ExplosionMagicBook;
import com.mola.molamod.items.magic.ExplosionMagicBookMax;
import com.mola.molamod.network.handler.PosEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.commons.lang3.StringUtils;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description:
 * @date : 2021-09-25 19:42
 **/
public class MagicBookExplosionHandler implements PosEventHandler {

    @Override
    public boolean handlePosEvent(Vec3d vec3d, MessageContext ctx, NBTTagCompound compound) {
        String className = compound.getString("className");
        ExplosionMagicBook magicBook = null;
        if (StringUtils.containsIgnoreCase(className, "max")) {
            magicBook = CustomHandlerManager.getItemHandler().getItem(ExplosionMagicBookMax.class);
        } else {
            magicBook = CustomHandlerManager.getItemHandler().getItem(ExplosionMagicBook.class);
        }

        World world = ctx.getServerHandler().player.world;
        world.newExplosion(null, vec3d.x, vec3d.y, vec3d.z,
                magicBook.getExplosionDamage(), magicBook.isUseFire(), magicBook.isBrokingBlock());
        // 绘制火焰例子
        spawnParticle(world, ctx.getServerHandler().player);
        return true;
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
                80, // 粒子个数
                4.5,
                2,
                4.5,
                1.2,
                new int[0]
        );
    }
}
