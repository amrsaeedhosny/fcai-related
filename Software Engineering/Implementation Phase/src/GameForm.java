import java.util.ArrayList;
import java.util.Scanner;

public class GameForm 
{
	Scanner in = new Scanner(System.in);
	String name;
	String type;
	ArrayList <Question> questions = new ArrayList <Question> ();
	String help;
	
	GameForm ()
	{
		showFormHeader();
		insertName();
		insertType();
		insertQuestions();
		insertHelp();
	}
	
	void showFormHeader ()
	{
		System.out.println(" ----------------------------------------- ");
		System.out.println("                GAME FORM                  ");
		System.out.println(" ----------------------------------------- ");
	}
	
	void insertName ()
	{	
		System.out.print(" > Game Name:    ");
		name = in.nextLine();
	}
	
	void insertType ()
	{
		System.out.print(" > Game Type (MCQ/TF):    ");
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
			
			String hintChoice;
			
			System.out.println("------------------------");
			System.out.println(" Add some hint ? (Y/N): ");
			System.out.println("------------------------");
			System.out.print(" > Choice:    ");
			hintChoice = in.nextLine();
			
			if ( hintChoice.equals("Y") )
			{
				System.out.print(" > Hint statement:    ");
				question.setHint(in.nextLine());
			}
			
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
	
	void insertHelp ()
	{
		System.out.print(" > Help statement:    ");
		help = in.nextLine();
	}
	
	void gameNameExistsMessage ()
	{
		System.out.println(" --------------------------- ");
		System.out.println("  Game name already exists!  ");
		System.out.println(" --------------------------- ");
	}
	
	void gameCreatedSuccessfullyMessage ()
	{
		System.out.println(" -------------------------------------------- ");
		System.out.println("   Your game has been created successfully!   ");
		System.out.println(" -------------------------------------------- ");
	}

}