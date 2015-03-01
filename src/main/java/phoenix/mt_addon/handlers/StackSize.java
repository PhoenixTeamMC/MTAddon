package phoenix.mt_addon.handlers;

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

    private static class SetAction implements IUndoableAction {
        private Item item;
        private int newLimit;
        private int oldLimit;

        public SetAction(Item item, int limit) {
            this.item = item;
            this.newLimit = limit;
            this.oldLimit = item.getItemStackLimit(new ItemStack(item));
        }

        @Override
        public void apply() {
            this.item.setMaxStackSize(this.newLimit);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            this.item.setMaxStackSize(this.oldLimit);
        }

        @Override
        public String describe() {
            return "Setting stacksize of "+Item.itemRegistry.getNameForObject(this.item)+" to "+this.newLimit;
        }

        @Override
        public String describeUndo() {
            return "Resetting stacksize of "+Item.itemRegistry.getNameForObject(this.item)+" to "+this.oldLimit;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
