package com.mola.molamod.items.magic;

import com.mola.molamod.MolaMod;
import com.mola.molamod.annotation.CustomItem;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description: 魔法书Max，可以制造爆炸效果
 * @date : 2021-09-25 20:50
 **/
@CustomItem
public class ExplosionMagicBookMax extends ExplosionMagicBook{

    private static final String name = "explosion_magic_book_max";

    public ExplosionMagicBookMax() {
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

        // 设置可破坏属性
        this.setUseFire(true);
    }
}
