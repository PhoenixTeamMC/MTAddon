package phoenix.mt_addon.handlers.oredict;

import minetweaker.IUndoableAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import phoenix.mt_addon.eventhandlers.OreDictNet;

import java.util.ArrayList;

public class RemoveAllEntries implements IUndoableAction {

    private String name;

    public RemoveAllEntries(String s) {
        this.name = s;
        OreDict.removedEntries.put(name, new ArrayList<ItemStack>());
    }

    @Override
    public void apply() {
        MinecraftForge.EVENT_BUS.register(new OreDictNet(name));
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        MinecraftForge.EVENT_BUS.unregister(new OreDictNet(name));
        for (String name : OreDict.removedEntries.keySet()){
            for (ItemStack itemStack : OreDict.removedEntries.get(name)){
                OreDictionary.registerOre(name, itemStack);
            }
        }
    }

    @Override
    public String describe() {
        return "Removed oredict net for "+name;
    }

    @Override
    public String describeUndo() {
        return "Resetting " + OreDict.removedEntries.get(name).size() + " oredict entries for "+name;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}
