package com.mola.molamod.annotation;

import java.lang.annotation.*;

/**
 * 自定义物品，ioc模式注入
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomItem {
}
