package com.mola.molamod.commands;

import com.mola.molamod.annotation.CustomCommand;
import com.mola.molamod.utils.CommandUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description:
 * @date : 2020-11-08 11:16
 **/
@CustomCommand
public class CommandExplosionTest extends CommandBase {
    @Override
    public String getName() {
        return "mola_explosion";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.mola_explosion.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        CommandUtil.sendMessage(sender, "world = " + sender.getEntityWorld().toString());
        // 获得世界
        World world = sender.getEntityWorld();
        Vec3d posVec = sender.getPositionVector();
        /*
         * 制造爆炸
         *  entityIn 爆炸源，可以为 null，不参与爆炸伤害判定
            x,y,z double，坐标
            strength float，爆炸威力/范围，普通苦力怕为3.0，TNT为4.0或者更大一点，末影水晶为6.0，所以说。。
            isSmoking 是否破坏地形和造成伤害
         */
        if (null != world) {
            world.createExplosion(null, posVec.x, posVec.y, posVec.z, 2.5f, true);
        } else {
            CommandUtil.reportError(sender, "获取不到世界");
        }

        CommandUtil.sendMessage(sender, "爆炸即是艺术");
    }
}
