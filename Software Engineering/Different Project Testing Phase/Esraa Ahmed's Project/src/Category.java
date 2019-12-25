
import java.util.ArrayList;


public class Category {

		String CatName;
		ArrayList <Game> ListOfGames= new ArrayList <Game>();;
	public void SetCatName(String Name)
	{
		CatName = Name;
	}
	public void SaveGame(Game g)
	{
		ListOfGames.add(g);
	}

}
