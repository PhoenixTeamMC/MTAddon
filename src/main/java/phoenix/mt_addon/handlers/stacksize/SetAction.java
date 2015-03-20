package phoenix.mt_addon.handlers.stacksize;

import minetweaker.IUndoableAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SetAction implements IUndoableAction {

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
