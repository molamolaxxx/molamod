package com.mola.molamod.annotation;

import net.minecraftforge.fml.relauncher.Side;

import java.lang.annotation.*;

/**
 * 自定义网络包，ioc模式注入
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomPacket {

    /**
     * handler工作的范围
     * @return
     */
    Side[] side();

    /**
     * 数据包标示
     * @return
     */
    int discriminator();
}
