package com.mola.molamod.factory;

import com.mola.molamod.annotation.CustomBlock;
import com.mola.molamod.annotation.CustomCommand;
import com.mola.molamod.annotation.CustomItem;
import com.mola.molamod.annotation.CustomPacket;
import com.mola.molamod.handlers.CustomBlockHandler;
import com.mola.molamod.handlers.CustomCommandHandler;
import com.mola.molamod.handlers.CustomItemHandler;
import com.mola.molamod.handlers.CustomPacketHandler;
import com.mola.molamod.items.block.MolaItemBlock;
import com.mola.molamod.utils.LoggerUtil;
import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.http.util.Asserts;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author : molamola
 * @Project: Evolution-Of-Knowledge
 * @Description:
 * @date : 2020-11-04 17:43
 **/
public class CustomHandlerRegistry {

    private static String packageName  = "com.mola.molamod";

    private static Logger logger = LoggerUtil.getLogger();

    /**
     * 扫描包下的注解
     */
    public static void scanningInPackage() {
        try {
            //获取路径
            Enumeration<URL> dirs = Thread.currentThread()
                    .getContextClassLoader().getResources(packageName.replace('.','/'));
            while (dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                if ("jar".equals(url.getProtocol())) {
                    // 转换为JarURLConnection
                    JarURLConnection connection = (JarURLConnection) url.openConnection();
                    if (connection != null) {
                        JarFile jarFile = connection.getJarFile();
                        if (jarFile != null) {
                            // 得到该jar文件下面的类实体
                            Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                            while (jarEntryEnumeration.hasMoreElements()) {
                                JarEntry entry = jarEntryEnumeration.nextElement();
                                String jarEntryName = entry.getName();
                                // 这里我们需要过滤不是class文件和不在basePack包名下的类
                                if (jarEntryName.contains(".class") && jarEntryName.replaceAll("/", ".").startsWith(packageName)) {
                                    String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");
                                    try {
                                        Class<?> clazz = Class.forName(className);
                                        // 获取方块
                                        Annotation customBlockAnnotation = clazz.getAnnotation(CustomBlock.class);
                                        if (null != customBlockAnnotation) {
                                            handleRegisterBlock(clazz);
                                            continue;
                                        }

                                        // 获取物品
                                        Annotation customItemAnnotation = clazz.getAnnotation(CustomItem.class);
                                        if (null != customItemAnnotation) {
                                            handleRegisterItem(clazz);
                                            continue;
                                        }

                                        // 获取命令
                                        Annotation customCommandAnnotation = clazz.getAnnotation(CustomCommand.class);
                                        if (null != customCommandAnnotation) {
                                            handleRegisterCommand(clazz);
                                            continue;
                                        }

                                        // 注册数据包
                                        CustomPacket customPacketAnnotation = clazz.getAnnotation(CustomPacket.class);
                                        if (null != customPacketAnnotation) {
                                            handleRegisterPacket(clazz, customPacketAnnotation);
                                            continue;
                                        }
                                    } catch (ClassNotFoundException e) {
                                        logger.error("[registry] 类加载异常", e);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    logger.error("[registry] 不支持扫描注入");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRegisterItem(Class clazz) {
        CustomItemHandler itemHandler = CustomHandlerManager.getItemHandler();
        Asserts.notNull(clazz, "[registry] class不能为空");
        Object target = null;
        try {
            target = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (null != target && target instanceof Item) {
            itemHandler.add((Item) target);
        } else {
            logger.error("[registry] handleRegisterItem 出现错误, class = {}",clazz.toString() );
        }
    }

    private static void handleRegisterBlock(Class clazz) {
        CustomBlockHandler blockHandler = CustomHandlerManager.getBlockHandler();
        Asserts.notNull(clazz, "[registry] class不能为空");
        Block target = null;
        try {
            target = (Block) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != target) {
            blockHandler.add(target);
        } else {
            logger.error("[registry] handleRegisterBlock 出现错误, class = {}",clazz.toString() );
        }
        // 注册方块对应物品
        CustomItemHandler itemHandler = CustomHandlerManager.getItemHandler();
        itemHandler.add(new MolaItemBlock(target).setRegistryName(target.getRegistryName()));
    }

    private static void handleRegisterCommand(Class clazz) {
        CustomCommandHandler commandHandler = CustomHandlerManager.getCommandHandler();
        Asserts.notNull(clazz, "[registry] class不能为空");
        Object target = null;
        try {
            target = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (null != target && target instanceof ICommand) {
            commandHandler.add((ICommand) target);
        } else {
            logger.error("[registry] handleRegisterCommand 出现错误, class = {}",clazz.toString() );
        }
    }

    private static void handleRegisterPacket(Class clazz, CustomPacket customPacketAnnotation) {
        CustomPacketHandler packetHandler = CustomHandlerManager.getPacketHandler();
        for (Side side : customPacketAnnotation.side()) {
            packetHandler.registerPacket(clazz, customPacketAnnotation.discriminator(), side);
        }
    }
}
