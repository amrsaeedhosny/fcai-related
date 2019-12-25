import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TournamentManager 
{
	TournamentModel tournamentModel;
	
	TournamentManager ( TournamentModel tournamentModel )
	{
		this.tournamentModel = tournamentModel;
	}
	
	void createTournamentForm ( AccountManager accountManager, String username )
	{
		TournamentForm tournamentForm = new TournamentForm();
		validateFormContent(tournamentForm);
		
		Tournament tournament = new Tournament();
		tournament.name = tournamentForm.name;
		tournament.type = tournamentForm.type;
		tournament.questions = tournamentForm.questions;
		tournament.date = tournamentForm.date;
		tournament.startTime = tournamentForm.startTime;
		tournament.finishTime = tournamentForm.finishTime;
		
		accountManager.addTournamentToAccount(username, tournament);
		tournamentModel.insertTournament(tournament);
		tournamentForm.tournamentCreatedSuccessfullyMessage();
	}
	
	void validateFormContent ( TournamentForm tournamentForm )
	{
		while ( tournamentModel.checkTournamentExist(tournamentForm.name) )
		{
				tournamentForm.gameNameExistsMessage();
				tournamentForm.insertName();
		}
		
		while(checkDate(tournamentForm.date))
		{
				tournamentForm.incorrectDateMessage();
				tournamentForm.insertDate();
		}
		
	}
	
	boolean checkDate(String date)
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Calendar cal = Calendar.getInstance();
		
		String CurrentDate = (dateFormat.format(cal.getTime()).toString());
		String[] currentdate = CurrentDate.split("/");
		String[] tournamentdate = date.split("/");
		
		if(currentdate[2].compareTo(tournamentdate[2]) == 1)
			return true;
		
		else if (currentdate[1].compareTo(tournamentdate[1]) == 1 && currentdate[2].equals(tournamentdate[2]) )
		 return true;
		
		else if (currentdate[0].compareTo(tournamentdate[0]) == 1 && currentdate[1].equals(tournamentdate[1]) && currentdate[2].equals(tournamentdate[2]) )
			 return true;
		
	    
		return false;
	}

}