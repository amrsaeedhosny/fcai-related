import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game 
{
	String name;
	String type;
	ArrayList <Question> questions = new ArrayList <Question> ();
	Map < String , Integer > scoreboard = new HashMap < String , Integer > ();
	int playerScore;
	String help;
	
	
	public Game() {
		
	}

	public Game(String name, String type, ArrayList<Question> questions, 
			Map<String, Integer> scoreboard, int playerScore, String help) 
	{
		
		this.name = name;
		this.type = type;
		this.questions = questions;
		this.scoreboard = scoreboard;
		this.playerScore = playerScore;
		this.help = help;
	}

	String getName ()
	{
		return name;
	}
	
	String getHelp ()
	{
		return help;
	}
	
	int getPlayerScore ()
	{
		return playerScore;
	}
	
	Map < String , Integer > getScoreboard ()
	{
		return scoreboard;
	}
	
	void updateScoreboard (String username)
	{
		scoreboard.put(username, playerScore);
	}
	
}