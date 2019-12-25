import java.util.ArrayList;

public class CategoryManager 
{
	CategoryModel categoryModel;
	
	CategoryManager ( CategoryModel categoryModel )
	{
		this.categoryModel = categoryModel;
	}
	
	ArrayList <Game> getCategoryGames ( String categoryName )
	{
		return categoryModel.retrieveCategoryGames(categoryName);
	}
	
	void addGameToCategory ( String categoryName, Game game )
	{
		categoryModel.insertGame(categoryName, game);
	}
}
