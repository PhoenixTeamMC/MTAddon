package phoenix.mt_addon.handlers.stacksize;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**Provides functionality to change the stacksizes of items*/
@ZenClass("phoenixTweaks.StackSize")
public class StackSize {
    @ZenMethod
    public static void set(IItemStack stack, int size) {
        ItemStack stackToChange = MineTweakerMC.getItemStack(stack);
        if(size>0 && size<=64) {
            MineTweakerAPI.apply(new SetAction(stackToChange.getItem(), size));
        }
        else {
            MineTweakerAPI.logError("StackSize should be between 0 and 64");
        }
    }
}
