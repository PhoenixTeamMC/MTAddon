package com.Phoenix.MT_Addon.handlers;

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

    private static class SetAction implements IUndoableAction{
        private static boolean initialised = false;

        private Block block;
        private int meta;
        private int newLvl;
        private int oldLvl;
        private String newTool;
        private String oldTool;

        public SetAction(Block block, int meta, int level, String tool) {
            if(!initialised) {
                new ForgeHooks();
                initialised = true;
            }
            this.block = block;
            this.meta = meta;
            this.newLvl = level;
            this.oldLvl = block.getHarvestLevel(meta);
            this.newTool = tool;
            this.oldTool = block.getHarvestTool(meta);
        }

        @Override
        public void apply() {
            this.block.setHarvestLevel(newTool, newLvl, meta);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            this.block.setHarvestLevel(oldTool, oldLvl, meta);
        }

        @Override
        public String describe() {
            return "Setting harvest level of "+(new ItemStack(block, 1, meta)).getDisplayName()+ " to "+newLvl+ " with tool "+newTool;
        }

        @Override
        public String describeUndo() {
            return "Resetting harvest level of "+(new ItemStack(block, 1, meta)).getDisplayName()+ " to "+oldLvl+ " with tool "+oldTool;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
