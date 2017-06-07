package com.hk.artm.compat.jei;

import com.hk.artm.Init;
import com.hk.artm.gui.GuiLibrary;
import com.hk.artm.util.recipes.LibraryRecipes;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LibraryCategory extends BlankRecipeCategory<LibraryWrapper>
{
	private final IDrawableStatic libraryGui;
	private int stX = 7, stY = 17;

	private LibraryCategory(IGuiHelper helper)
	{
		libraryGui = helper.createDrawable(GuiLibrary.LIBRARY_GUI, stX, stY, 36, 55);
	}

	public static void register(IModRegistry registry)
	{
		registry.addRecipeCategories(new LibraryCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeHandlers(new RecipeHandler<>(UID, LibraryWrapper.class));
		registry.addRecipeCategoryCraftingItem(new ItemStack(Init.BLOCK_LIBRARY), UID);

		List<LibraryWrapper> list = new ArrayList<>();
		for (LibraryRecipes.LibraryRecipe recipe : LibraryRecipes.INSTANCE.getRecipes())
		{
			list.add(new LibraryWrapper(recipe));
		}
		registry.addRecipes(list);
	}

	@Override
	public String getUid()
	{
		return UID;
	}

	@Override
	public String getTitle()
	{
		return Init.BLOCK_LIBRARY.getLocalizedName();
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, LibraryWrapper recipeWrapper, IIngredients ingredients)
	{
//		recipeLayout.setShapeless();
		IGuiItemStackGroup group = recipeLayout.getItemStacks();
		for (int i = 0; i < 4; i++)
		{
			int x = i % 2;
			int y = i / 2;
			group.init(i, true, 7 + (x * 18) - stX, 17 + (y * 18) - stY);
		}
		group.init(4, true, 7 - stX, 54 - stY);
		group.init(5, false, 25 - stX, 54 - stY);
		recipeWrapper.getIngredients(ingredients);
		group.set(ingredients);
	}

	@Override
	public IDrawable getBackground()
	{
		return libraryGui;
	}

	public static final String UID = "artm.library";
}
