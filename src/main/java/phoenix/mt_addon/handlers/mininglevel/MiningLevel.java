package phoenix.mt_addon.handlers.mininglevel;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**Provides functionality to change the harvest level of blocks*/
@ZenClass("phoenixTweaks.MiningLevel")
public class MiningLevel {

    @ZenMethod
    public static void set(IItemStack stack, int level, String tool) {
        ItemStack stackToChange = MineTweakerMC.getItemStack(stack);
        if(stackToChange.getItem() instanceof ItemBlock) {
            MineTweakerAPI.apply(new SetAction(((ItemBlock) stackToChange.getItem()).field_150939_a, stackToChange.getItemDamage(), level, tool));
        }
        else {
            MineTweakerAPI.logError("Can only change harvest level of blocks");
        }
    }
}
