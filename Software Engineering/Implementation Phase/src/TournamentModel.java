import java.util.ArrayList;

public class TournamentModel
{
	ArrayList <Tournament> tournaments = new ArrayList <Tournament> ();
	
	void insertTournament ( Tournament tournament )
	{
		tournaments.add(tournament);
	}
	
	boolean checkTournamentExist ( String TournamentName )
	{
		for(int i = 0 ; i< tournaments.size() ; i++)
		{
			if ( TournamentName.equals( tournaments.get(i).name ) )
			{
				return true;
			}
		}
		  return false;
	}

}