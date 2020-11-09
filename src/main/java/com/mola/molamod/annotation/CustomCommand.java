package com.mola.molamod.annotation;


import java.lang.annotation.*;


/**
 * 自定义命令，ioc模式注入
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomCommand {
}
