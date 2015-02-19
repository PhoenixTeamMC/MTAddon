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

    @ZenMethod
    public static void MergeOredict(String s1, String s2){
        if (OreDictionary.getOres(s1).size() > 0 && OreDictionary.getOres(s2).size() > 0)
            MineTweakerAPI.apply(new mergeOreEntries(s1, s2));
        else
            MineTweakerAPI.logError("Invalid merge: "+s1+" & "+s2);
    }

    private static class mergeOreEntries implements IUndoableAction{

        String name1;
        String name2;
        ArrayList<ItemStack> first;
        ArrayList<ItemStack> second;

        public mergeOreEntries(String one, String two){
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
