package com.Phoenix.MT_Addon.handlers;

/**
 * Created by Elec332 on 19-2-2015.
 */

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldSettings;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**Provides functionality to change the gametype of client and servers*/
@ZenClass("phoenixTweaks.Difficulty")
public class Difficulty {

    static MinecraftServer theServer = MinecraftServer.getServer();

    @ZenMethod
    public static void setGameType(String type) {
        MineTweakerAPI.apply(new SetAction(getTypeFromString(type)));
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

    private static class SetAction implements IUndoableAction {

        private WorldSettings.GameType oldType;
        private WorldSettings.GameType newType;

        public SetAction(WorldSettings.GameType gameType) {
            this.oldType = MinecraftServer.getServer().getGameType();
            this.newType = gameType;
        }

        @Override
        public void apply() {
            theServer.setGameType(newType);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            theServer.setGameType(oldType);
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
    }
}

