package com.mola.molamod.network.handler;


import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * 对应位置处理器
 */
public interface PosEventHandler {

    /**
     *
     * @param vec3d
     * @return
     */
    boolean handlePosEvent(Vec3d vec3d, MessageContext ctx, NBTTagCompound compound);
}
