package com.Phoenix.MT_Addon.handlers;

import com.Phoenix.MT_Addon.eventhandlers.OreDictNet;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.HashMap;

@ZenClass("phoenixTweaks.OreDict")
public class OreDict {

    public static HashMap<String, ArrayList<ItemStack>> removedEntries = new HashMap<String, ArrayList<ItemStack>>();

    @ZenMethod
    public static void registerOredictNet(String s) {
        MineTweakerAPI.apply(new RemoveAllEntries(s));
    }

    private static class RemoveAllEntries implements IUndoableAction {
        private String name;

        public RemoveAllEntries(String s) {
            this.name = s;
            removedEntries.put(name, new ArrayList<ItemStack>());
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
            for (String name : removedEntries.keySet()){
                for (ItemStack itemStack : removedEntries.get(name)){
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
            return "Resetting "+removedEntries.get(name).size()+" oredict entries for "+name;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
