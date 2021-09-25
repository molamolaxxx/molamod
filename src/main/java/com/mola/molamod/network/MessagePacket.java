package com.mola.molamod.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class MessagePacket implements IMessage {

    /**
     * 简单参数传递
     */
    protected NBTTagCompound compound = new NBTTagCompound();
}
