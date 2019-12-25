import java.util.ArrayList;

public class Teacher extends Account 
{
	String professionalReference;
	ArrayList <Game> games = new ArrayList <Game>();
	ArrayList <Tournament> tournaments = new ArrayList <Tournament>();
	
	void addGame ( Game game )
	{
		games.add(game);
		
	}
	void addTournament ( Tournament tournament )
	{
		tournaments.add(tournament);
	}

}
