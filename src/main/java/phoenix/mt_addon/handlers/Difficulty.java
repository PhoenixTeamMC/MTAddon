package phoenix.mt_addon.handlers;

/**
 * Created by Elec332 on 19-2-2015.
 */

import cpw.mods.fml.common.FMLCommonHandler;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import phoenix.mt_addon.eventhandlers.DifficultyForcer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**Provides functionality to change the gametype of client and servers*/
@ZenClass("phoenixTweaks.Difficulty")
public class Difficulty {

    static MinecraftServer theServer = MinecraftServer.getServer();

    @ZenMethod
    public static void ForceDifficulty(String type){
        EnumDifficulty difficulty = getDifficulty(type);
        if (difficulty != null)
            MineTweakerAPI.apply(new ForceDifficulty(difficulty));
    }

    private static EnumDifficulty getDifficulty(String s){
        if (s.equalsIgnoreCase("peaceful"))
            return EnumDifficulty.PEACEFUL;
        if (s.equalsIgnoreCase("easy"))
            return EnumDifficulty.EASY;
        if (s.equalsIgnoreCase("normal"))
            return EnumDifficulty.NORMAL;
        if (s.equalsIgnoreCase("hard"))
           return EnumDifficulty.HARD;
        MineTweakerAPI.logError("\""+s+"\""+" is not an valid difficulty" );
        return null;
    }

    private static class ForceDifficulty implements IUndoableAction {
        public ForceDifficulty(EnumDifficulty difficulty){
            this.newType = difficulty;
            this.oldType = theServer.func_147135_j();
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
            if (theServer.isServerRunning())
                for(World world: theServer.worldServers)
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







/* Meh

    @ZenMethod
    public static void setGameType(String type) {
        MineTweakerAPI.apply(new SetGameTypeSoft(getTypeFromString(type)));
    }

    @ZenMethod
    public static void forceGameType(String type){
        MineTweakerAPI.apply(new ForceGameType(getTypeFromString(type)));
    }

    private static WorldSettings.GameType getTypeFromString(String s){
        if (s.equalsIgnoreCase("survival"))
            return WorldSettings.GameType.SURVIVAL;
        if (s.equalsIgnoreCase("adventure"))
            return WorldSettings.GameType.ADVENTURE;
        if (s.equalsIgnoreCase("creative"))
            return WorldSettings.GameType.CREATIVE;
        MineTweakerAPI.logError("\"" + s + "\""+ " is an invalid gametype, using default set one.");
        return theServer.getGameType();
    }

    private static class ForceGameType extends GameTypeBase{

        public ForceGameType(WorldSettings.GameType gameType) {
            this.oldType = theServer.getGameType();
            this.newType = gameType;
        }

        @Override
        public void apply() {
            theServer.setGameType(newType);
        }

        @Override
        public void undo() {
            theServer.setGameType(oldType);
        }
    }

    private static class SetGameTypeSoft extends GameTypeBase{

        public SetGameTypeSoft(WorldSettings.GameType gameType) {
            this.oldType = theServer.getGameType();
            this.newType = gameType;
        }

        @Override
        public void apply() {
            theServer.setGameType(newType);
        }

        @Override
        public void undo() {
            theServer.setGameType(oldType);
        }
    }

    private abstract static class GameTypeBase implements IUndoableAction {

        protected WorldSettings.GameType oldType;
        protected WorldSettings.GameType newType;

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Setting the gametype from " + this.oldType.getName() + " to " + this.newType.getName();
        }

        @Override
        public String describeUndo() {
            return "Resetting the gametype from " + this.newType.getName() + " to " + this.oldType.getName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }*/
}

