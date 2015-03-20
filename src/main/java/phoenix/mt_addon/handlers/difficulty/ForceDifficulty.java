package phoenix.mt_addon.handlers.difficulty;

import cpw.mods.fml.common.FMLCommonHandler;
import minetweaker.IUndoableAction;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import phoenix.mt_addon.eventhandlers.DifficultyForcer;

public class ForceDifficulty implements IUndoableAction {

    public ForceDifficulty(EnumDifficulty difficulty){
        this.newType = difficulty;
        this.oldType = Difficulty.theServer.func_147135_j();
        this.DiffForcerInstance = new DifficultyForcer(newType);
    }

    protected EnumDifficulty oldType;
    protected EnumDifficulty newType;
    private DifficultyForcer DiffForcerInstance;

    @Override
    public void apply() {
        FMLCommonHandler.instance().bus().register(DiffForcerInstance);
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        FMLCommonHandler.instance().bus().unregister(DiffForcerInstance);
        if (Difficulty.theServer.isServerRunning())
            for(World world: Difficulty.theServer.worldServers)
                world.difficultySetting = oldType;
    }

    @Override
    public String describe() {
        return "Setting the difficulty from " + this.oldType.getDifficultyResourceKey() + " to " + this.newType.getDifficultyResourceKey();
    }

    @Override
    public String describeUndo() {
        return "Resetting the difficulty from " + this.newType.getDifficultyResourceKey() + " to " + this.oldType.getDifficultyResourceKey();
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}
