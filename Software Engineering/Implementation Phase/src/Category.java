import java.util.ArrayList;

public class Category 
{
	String name;
	ArrayList <Game> games = new ArrayList <Game> ();
	
	void setName(String name) 
	{
		this.name = name;
	}

	void setGames(ArrayList<Game> games) 
	{
		this.games = games;
	}
	
	String getName ()
	{
		return name;
	}
	
	
	ArrayList <Game> getGames ()
	{
		return games;
	}

	void addGame ( Game game )
	{
		games.add(game);
	}
	
}
