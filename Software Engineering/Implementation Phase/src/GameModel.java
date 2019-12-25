import java.util.ArrayList;

public class GameModel 
{
	ArrayList <Game> games = new ArrayList <Game>();
	
	void insertGame ( Game game )
	{
		games.add(game);
	}
	
	Game retrieveGame ( String gameName )
	{
		Game game = null;
		for ( int i = 0 ; i < games.size() ; i++ )
		{
			if ( games.get(i).getName().equals(gameName) )
			{
				game = games.get(i);
			}
		}
		return game;
	}
	
	boolean checkExist ( String gameName )
	{
		for(int i = 0 ; i< games.size() ; i++)
		{
			if( gameName.equals( games.get(i).getName() ) )
			{
				return true;
			}
		}
		return false;
		
	}
	

}