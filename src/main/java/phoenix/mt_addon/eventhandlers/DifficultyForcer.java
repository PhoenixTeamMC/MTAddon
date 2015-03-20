package phoenix.mt_addon.eventhandlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.EnumDifficulty;

/**
 * Created by Elec332 on 1-3-2015.
 */
public class DifficultyForcer {
    public DifficultyForcer(EnumDifficulty d){
        this.difficulty = d;
    }

    EnumDifficulty difficulty;

    @SubscribeEvent
    public void forceDifficulty(TickEvent.WorldTickEvent event) {
        if (event.side.isServer())
            event.world.difficultySetting = difficulty;
    }
}
