package phoenix.mt_addon;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import minetweaker.MineTweakerAPI;
import phoenix.mt_addon.handlers.Difficulty;
import phoenix.mt_addon.handlers.MiningLevel;
import phoenix.mt_addon.handlers.OreDict;
import phoenix.mt_addon.handlers.StackSize;

@Mod(modid = MT_Addon.MOD_ID, name = MT_Addon.MOD_NAME, dependencies = "required-after:MineTweaker3") //;required-after:ModTweaker") I dont think thats nesseceary
public class MT_Addon {
    public static final String MOD_ID = "mt_addon";
    public static final String MOD_NAME = "MT-Addon";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MineTweakerAPI.registerClass(MiningLevel.class);
        MineTweakerAPI.registerClass(OreDict.class);
        MineTweakerAPI.registerClass(StackSize.class);
        MineTweakerAPI.registerClass(Difficulty.class);
    }
}
