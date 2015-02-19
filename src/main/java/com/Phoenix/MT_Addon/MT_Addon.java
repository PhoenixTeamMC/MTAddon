package com.Phoenix.MT_Addon;

import com.Phoenix.MT_Addon.handlers.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import minetweaker.MineTweakerAPI;

@Mod(modid = MT_Addon.MOD_ID, name = MT_Addon.MOD_NAME, dependencies = "required-after:MineTweaker3;required-after:ModTweaker")
public class MT_Addon {
    public static final String MOD_ID = "mt_addon";
    public static final String MOD_NAME = "MT-Addon";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MineTweakerAPI.registerClass(MiningLevel.class);
        //MineTweakerAPI.registerClass(OreDict.class);
        MineTweakerAPI.registerClass(StackSize.class);
        MineTweakerAPI.registerClass(Difficulty.class);
    }
}
