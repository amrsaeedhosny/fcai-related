import java.util.ArrayList;
import java.util.Scanner;

public class TournamentForm
{
	Scanner in = new Scanner(System.in);
	String name;
	String type;
	ArrayList <Question> questions = new ArrayList <Question> ();
	String date;
	String startTime;
	String finishTime;
	
	TournamentForm ()
	{
		showFormHeader();
		insertName();
		insertType();
		insertQuestions();
		insertDate();
		insertStartTime();
		insertFinishTime();
	}
	
	void showFormHeader ()
	{
		System.out.println(" ----------------------------------------- ");
		System.out.println("              TOURNAMENT FORM              ");
		System.out.println(" ----------------------------------------- ");
	}
	
	void insertName ()
	{		
		System.out.print(" > Tournament Name:    ");
		name = in.nextLine();
	}
	
	void insertType ()
	{
		System.out.print(" > Tournament Type (MCQ/TF):    ");
		type = in.nextLine();
	}
	
	void insertQuestions ()
	{
		int choice = 0;
		
		System.out.println(" ------------------------ ");
		System.out.println("     1- Add question      ");
		System.out.println("     2- Finish            ");
		System.out.println(" ------------------------ ");
		System.out.print(" > Choice:     ");
		choice = in.nextInt();
		in.nextLine();
		
		while ( choice != 2 )
		{
			Question question = new Question(type);
			System.out.print(" > Question header:    ");
			question.setHeader(in.nextLine());
			
			if ( type.equals("MCQ") )
			{
				ArrayList <String> choices = new ArrayList <String>();
				for ( int i = 0 ; i < 4 ; i++ )
				{
					System.out.print(" > Choice #" + (i+1) + ":    " );
					choices.add(in.nextLine());
				}
				question.setChoices(choices);
			}
			
			System.out.print(" > Correct answer:    ");
			question.setSolution(in.nextLine());
			
			questions.add(question);
				
			System.out.println(" ------------------------ ");
			System.out.println("     1- Add question      ");
			System.out.println("     2- Finish            ");
			System.out.println(" ------------------------ ");
			System.out.print(" > Choice:     ");
			choice = in.nextInt();
			in.nextLine();
		}
		
	}
	
	void insertDate ()
	{
		System.out.print(" > Date:    ");
		date = in.nextLine();
	}

	void insertStartTime ()
	{
		System.out.print(" > Start time:    ");
		startTime = in.nextLine();
	}
	
	void insertFinishTime()
	{
		System.out.print(" > Finish time:    ");
		finishTime = in.nextLine();
	}
	
	void gameNameExistsMessage ()
	{
		System.out.println(" --------------------------------- ");
		System.out.println("  Tournament name already exists!  ");
		System.out.println(" --------------------------------- ");
	}
	
	void incorrectDateMessage ()
	{
		System.out.println(" --------------------------------- ");
		System.out.println("          Incorrect date!          ");
		System.out.println(" --------------------------------- ");
	}
	
	void tournamentCreatedSuccessfullyMessage ()
	{
		System.out.println(" -------------------------------------------------- ");
		System.out.println("   Your tournament has been created successfully!   ");
		System.out.println(" -------------------------------------------------- ");
	}
}