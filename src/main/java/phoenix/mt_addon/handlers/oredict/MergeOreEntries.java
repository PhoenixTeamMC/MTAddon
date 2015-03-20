package phoenix.mt_addon.handlers.oredict;

import minetweaker.IUndoableAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class MergeOreEntries implements IUndoableAction {

    String name1;
    String name2;
    ArrayList<ItemStack> first;
    ArrayList<ItemStack> second;

    public MergeOreEntries(String one, String two){
        this.name1 = one;
        this.name2 = two;
    }


    @Override
    public void apply() {
        this.first = OreDictionary.getOres(name1);   //local vars in case it will become possible to unregister stuff
        this.second = OreDictionary.getOres(name2);  //local vars in case it will become possible to unregister stuff
        for (ItemStack itemStack : first){
            OreDictionary.registerOre(name2, itemStack);
        }
        for (ItemStack itemStack : second){
            OreDictionary.registerOre(name1, itemStack);
        }
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public void undo() {
        //Cannot undo, MC restart is needed
    }

    @Override
    public String describe() {
        return "Merging oredict entries for "+name1+" and "+name2;
    }

    @Override
    public String describeUndo() {
        return "Impossible";
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}