package phoenix.mt_addon.handlers.oredict;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import phoenix.mt_addon.eventhandlers.OreDictNet;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ZenClass("phoenixTweaks.OreDict")
public class OreDict {

    public static HashMap<String, ArrayList<ItemStack>> removedEntries = new HashMap<String, ArrayList<ItemStack>>();

    @ZenMethod
    public static void registerOreDictNet(String s) {
        MineTweakerAPI.apply(new RemoveAllEntries(s));
    }

    @ZenMethod
    public static void mergeOreDict(String s1, String s2){
        if (OreDictionary.getOres(s1).size() > 0 && OreDictionary.getOres(s2).size() > 0)
            MineTweakerAPI.apply(new MergeOreEntries(s1, s2));
        else
            MineTweakerAPI.logError("Invalid merge: "+s1+" & "+s2);
    }

    public void rem(){
        ItemStack test = new ItemStack(Items.iron_ingot);
        @SuppressWarnings("unchecked")
        List<IRecipe> allRecipes = CraftingManager.getInstance().getRecipeList();
        List<IRecipe> recipesToRemove = new ArrayList<IRecipe>();
        List<IRecipe> recipesToAdd = new ArrayList<IRecipe>();

        // Search vanilla recipes for recipes to replace
        for(Object obj : allRecipes)
        {
            if(obj instanceof ShapedRecipes)
            {
                ShapedRecipes recipe = (ShapedRecipes)obj;
                ItemStack output = recipe.getRecipeOutput();
                if(output == test)
                {
                    recipesToRemove.add(recipe);
                    ItemStack[] t = recipe.recipeItems;
                    recipesToAdd.add(new ShapedOreRecipe(test, t));
                }
            }
        }

        allRecipes.removeAll(recipesToRemove);
        allRecipes.addAll(recipesToAdd);
    }
}
