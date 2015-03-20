package phoenix.mt_addon.eventhandlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import phoenix.mt_addon.handlers.oredict.OreDict;

import java.util.ArrayList;

/**
 * Created by Elec332 on 19-2-2015.
 */
public class OreDictNet {

    public OreDictNet(String s){
        this.name = s;
    }

    String name;

    @SubscribeEvent
    public void catchEntry(OreDictionary.OreRegisterEvent event){
        if (event.Name.equals(name)){
            ArrayList<ItemStack> list = OreDict.removedEntries.get(name);
            list.add(event.Ore);
            OreDict.removedEntries.put(name, list);
            event.setCanceled(true);
        }
    }
}
