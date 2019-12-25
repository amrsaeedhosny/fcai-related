import java.util.Map;
import java.util.Scanner;

public class GameInterface 
{
	Game game;
	
	GameInterface (Game game)
	{
		this.game = game;
	}
	
	void run ()
	{
		Scanner in = new Scanner(System.in);
				
		showGameHeader();
		
		int helpChoice = 0; 
				
		do
		{
			System.out.println(" ------------------------ ");
			help();
			playGame();
			System.out.println(" ------------------------ ");
			System.out.print(" > Choice    ");
			helpChoice = in.nextInt();
			in.nextLine();
			
			if ( helpChoice == 1 )
			{
				System.out.println(" ------------------------------------- ");
				System.out.println("                 HELP                  ");
				System.out.println(" ------------------------------------- ");
				System.out.println(" > " + game.getHelp());
				System.out.println(" ------------------------------------- ");
			}
		} while ( helpChoice != 2 );
		
		
		
		for ( int i = 0 ; i < game.questions.size() ; i++ )
		{
			int questionPoints = 10;
			System.out.println();
			System.out.println( " Q" + (i+1) +") " + game.questions.get(i).header );
			for ( int j = 0 ; j < game.questions.get(i).choices.size() ; j++ )
			{
				System.out.print("  "+(j+1)+ "- ");
				System.out.println(game.questions.get(i).choices.get(j));
			}
			
			if ( !( game.questions.get(i).hint.equals("NO HINT") ) )
			{
				String hintChoice;
				showHint();
				hintChoice = in.nextLine();
				if ( hintChoice.equals("Y") )
				{
					System.out.println(" > Hint statement:    " + game.questions.get(i).hint);
					questionPoints -= (questionPoints/5);
				}
			}
			System.out.print(" > Question Choice    ");
			String solutionChoice;
			solutionChoice = in.nextLine();
			
			if ( solutionChoice.equals(game.questions.get(i).solution) )
			{
				//int temp = game.playerScore;
				game.playerScore += questionPoints;
				//game.playerScore -= temp; 
			}
		}
		
		
	//	in.close();
	}
	
	void showGameHeader ()
	{
		System.out.println(" ------------------------------------------ ");
		System.out.println("               Welcome to " + game.getName() );
		System.out.println(" ------------------------------------------ ");
	}
	
	void help ()
	{
		System.out.println("    1- Help       ");
	}
	
	void playGame()
	{
		System.out.println("    2- Play Game   ");
	}
	
	void showHint()
	{
		System.out.println(" ----------------------------------- ");
		System.out.println("   Do you want to show hint? (Y/N):  ");
		System.out.println(" ----------------------------------- ");
		System.out.print(" > Hint Choice    ");
	}
	
	void showScore ()
	{
		System.out.println(" ------------------------------------- ");
		System.out.println("            Your Score is: " + game.getPlayerScore() + "!"  );
		System.out.println(" ------------------------------------- ");

	}
	
	void showScoreBoard()
	{
		System.out.println(" ------------------------------------- ");
		System.out.println("               Score Board             ");
		System.out.println("     Username       |         Score    ");
		System.out.println("---------------------------------------");
		for (Map.Entry<String, Integer> entry : game.scoreboard.entrySet()) 
		{
		    System.out.println( "    " + entry.getKey() + "                    " + entry.getValue() );
		}
		System.out.println(" ------------------------------------- ");
	}

}