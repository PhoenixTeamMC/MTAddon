package phoenix.mt_addon.handlers.mininglevel;

import minetweaker.IUndoableAction;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

public class SetAction implements IUndoableAction {
    private static boolean initialized = false;

    private Block block;
    private int meta;
    private int newLvl;
    private int oldLvl;
    private String newTool;
    private String oldTool;

    public SetAction(Block block, int meta, int level, String tool) {
        if(!initialized) {
            new ForgeHooks();
            initialized = true;
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
