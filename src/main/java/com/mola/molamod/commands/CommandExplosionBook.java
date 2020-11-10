package com.mola.molamod.commands;

import com.mola.molamod.annotation.CustomCommand;
import com.mola.molamod.factory.CustomHandlerManager;
import com.mola.molamod.items.weapon.ExplosionMagicBook;
import com.mola.molamod.utils.CommandUtil;
import com.mola.molamod.utils.LoggerUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Logger;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description:
 * @date : 2020-11-09 18:19
 **/
@CustomCommand
public class CommandExplosionBook extends CommandBase {

    private Logger logger = LoggerUtil.getLogger();

    @Override
    public String getName() {
        return "book";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.book.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1 && "--help".equals(args[0])) {
            CommandUtil.sendMessage(sender,
                    "【1】book damage 2.5【设置魔法伤害值】\n" +
                    "【2】book distance 2.5【设置魔法释放距离】\n" +
                    "【3】book fire 0/1【是否释放火焰】\n" +
                    "【4】book break 0/1【是否破坏方块】\n" +
                    "【5】book attr【查看属性值】");
            return;
        }
        ExplosionMagicBook book = (ExplosionMagicBook) CustomHandlerManager.getItemHandler().getItem(ExplosionMagicBook.class);
        if (args.length == 1 && "attr".equals(args[0])) {
            CommandUtil.sendMessage(sender, book.toString());
            return;
        }


        if (args.length != 2) {
            CommandUtil.reportError(sender, "执行出错，命令参数为2，第一个为属性，第二个为值，book --help查看使用详情");
            return;
        }
        String attr = args[0];
        String value = args[1];

        if (null == book) {
            CommandUtil.reportError(sender, "执行出错，注册表未找到相应物品");
            return;
        }
        try {
            switch (attr) {
                case "damage": {
                    handleChangeDamage(book, value);
                    break;
                }
                case "distance": {
                    handleChangeDistance(book, value);
                    break;
                }
                case "fire": {
                    handleChangeFire(book, value);
                    break;
                }
                case "break": {
                    handleChangeBreak(book, value);
                    break;
                }
                default: {
                    CommandUtil.reportError(sender, "未知属性 : " + attr);
                }
            }
        } catch (RuntimeException rte) {
//            rte.printStackTrace();
            CommandUtil.reportError(sender, "填充属性值错误 : " + rte.getMessage());
        }
        CommandUtil.sendMessage(sender, book.toString());
    }

    private void handleChangeDamage(ExplosionMagicBook book, String value) {
        float damage = Float.valueOf(value);
        if (damage <= 0) {
            throw new RuntimeException("伤害不能小于0");
        }
        book.setExplosionDamage(damage);
    }

    private void handleChangeDistance(ExplosionMagicBook book, String value) {
        float distance = Float.valueOf(value);
        if (distance <= 0) {
            throw new RuntimeException("距离不能小于0");
        }
        book.setExplosionDistance(distance);
    }

    private void handleChangeFire(ExplosionMagicBook book, String value) {
        if ("1".equals(value)) {
            book.setUseFire(true);
        } else if ("0".equals(value)) {
            book.setUseFire(false);
        } else {
            throw new RuntimeException("值不在考虑范围内");
        }
    }

    private void handleChangeBreak(ExplosionMagicBook book, String value) {
        if ("1".equals(value)) {
            book.setBrokingBlock(true);
        } else if ("0".equals(value)) {
            book.setBrokingBlock(false);
        } else {
            throw new RuntimeException("值不在考虑范围内");
        }
    }
}
