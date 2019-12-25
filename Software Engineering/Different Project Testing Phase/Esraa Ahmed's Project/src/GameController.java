
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class GameController {
	Game game;
	ArrayList <Category> Categories = new ArrayList <Category>();
	Scanner in = new Scanner (System.in);
	
	public void AddGame()
	{
		Game mygame = new Game();
		
//		Account a = new Account();
//		AccountController Ac = new AccountController();
//		Ac.Login(a);
		Category Categoryy = new Category ();
		String choice;
		int numOfChoices;
		ArrayList <String> Choices = new ArrayList<String>();
		Scanner inInt = new Scanner (System.in);
		System.out.println("Please Enter your name !");
		mygame.TeacherName= in.nextLine();
		System.out.println("Please Enter game name !");
		mygame.Name = in.nextLine();
		for(int i =0 ; i< Categories.size() ; i++)
		{
			System.out.println(Categories.get(i).CatName);
		}
		System.out.println("Please choose the category of the game ! ");
		Categoryy.CatName = in.nextLine();
		System.out.println("Please Choose game type ! ");
		System.out.println("You have to choices : 1.True and False  2. Multiple Choices");
		mygame.GameType = in.nextLine();
		System.out.println("Please enter number of questions !");
		mygame.NumOfQuestions = Integer.valueOf(in.nextLine());
		
		if (mygame.GameType.equals("1"))
		{
		
			for(int j=0 ; j<mygame.NumOfQuestions ; j++){
				
				myQuestion p = new myQuestion();
					System.out.println("Enter the question !");
					p.QuestionNumber = j;
					p.QuestionNumber=j;
					p.Question = in.nextLine();
					System.out.println("Please enter the answer of the question !");
					p.Answer = in.nextLine();
					mygame.Questions.add(p);
				}}
		else if(mygame.GameType.equals("2")){
			String mychoice;
			for(int j=0 ; j < mygame.NumOfQuestions ; j++){
				myQuestion q = new myQuestion();
					System.out.println("Enter the question !");
					q.QuestionNumber = j;
					mychoice = in.nextLine();
					q.Question = mychoice;
					System.out.println("Please enter the number of choices !");
					numOfChoices = Integer.valueOf(in.nextLine());
					for(int i=1 ; i <= numOfChoices ; i++)
					{
						System.out.println("Enter Choices number " + i + " !");
						choice = in.nextLine();
						Choices.add(choice);
					}
					System.out.println("Please enter the right answer !");
					q.Answer = in.nextLine();
					mygame.Questions.add(q);
			}
		
		}
		SaveGame(mygame, Categoryy);
	
		//return SelectedCategory;
			//return Choices;
			
		
		
	}
	
	public  Category SaveGame(Game mygame, Category Categoo)
	{
		game = mygame;
		Category Catee = new Category();
//		game.Name = mygame.getName();
//		game.GameType = mygame.getGameType();
//		game.NumOfQuestions = mygame.getNumOfQuestions();
//		game.HighScore = mygame.getHighScore();
//		game.Questions = mygame.getQuestions();
//		game.Rates = mygame.getRates();
//		game.Scores = mygame.getScores();
//		game.TeacherName = mygame.getTeacherName();
		for(int i = 0 ;i <Categories.size();i++)
		{
			if(Categoo.CatName.equals(Categories.get(i).CatName))
			{
				Catee = Categories.get(i);
				Categories.get(i).ListOfGames.add(game);
			}
			
		}
		System.out.println("Game Saved !");
		return Catee;
		
	}
	
public void PlayGame() throws FileNotFoundException
{
	Game mygame = new Game();
	Account a = new Account();
	AccountController Ac = new AccountController();
	// already tested
	//Ac.Login(a);
	Student s = new Student ();
//	if (a.Type.equals( "Student"))
//	{
//		
//		s.Name = a.getName();
//		s.Age = a.getAge();
//		s.Email = a.getEmail();
//		s.Password = a.getPassword();
//		s.Gender = a.getGender();
//		s.Type = a.getType();
//		s.Gender = a.getGender();
//	}
	int Score=0;
	boolean ans=false;
	String userAnswer;
	Category cat= new Category();
	System.out.println("Please choose one of these categories !");
	GameController GC = new GameController();
	//ArrayList<String> choices = GC.AddGame();
	for(int i =0 ; i < Categories.size() ; i++)
	{
		System.out.println(Categories.get(i).CatName);
	}
	cat.CatName = in.nextLine();
	System.out.println("Please Choose a game !");
	for(int i =0 ; i < Categories.size() ; i++)
	{
		if(Categories.get(i).CatName.equals(cat.CatName))
			for(int j=0 ; j< Categories.get(i).ListOfGames.size() ; j++){
			System.out.println(Categories.get(i).ListOfGames.get(j).Name);}
	}

	
	String choice;
	choice = in.nextLine();
	for( int i = 0 ; i < Categories.size() ; i++)
	{
		if(Categories.get(i).CatName.equals(cat.CatName))
			for(int j=0 ; j < Categories.get(i).ListOfGames.size() ; j++){
				if(choice.equals(Categories.get(i).ListOfGames.get(j).Name))
					mygame = Categories.get(i).ListOfGames.get(j);
				}
	}

	if(mygame.GameType.equals("1"))
	{
		for(int i = 0;i<mygame.NumOfQuestions ;i++){
			System.out.println(mygame.Questions.get(i).Question);
			userAnswer = in.nextLine();
			if(userAnswer.equals(mygame.Questions.get(i).Answer)){
				ans = true;
				Score++;
				System.out.println("Correct Answer !");}
			else
			{
				System.out.println("Wrong answer !");
			}
				
	}}
	if(mygame.GameType.equals("2"))
	{
		for(int i = 0;i<mygame.NumOfQuestions ;i++){
			System.out.println(mygame.Questions.get(i).Question);
			//System.out.println(choices);
			//na2s ezay n return choices mn el add game
			userAnswer = in.nextLine();
			if(userAnswer.equals(mygame.Questions.get(i).Answer)){
				ans = true;
				System.out.println("Correct Answer !");
				Score++;
		}
			else
			{
				System.out.println("Wrong answer !");
			}
			
				
	}
	}
	SaveScore(Score);
	System.out.println("Your new score is : " + Score + "!");
	RateGame(mygame);
	String comm;
	System.out.println("Would you like to add a comment for this game ?");
	comm = in.nextLine();
	if(comm == "Yes")
	{
		String comment = s.WriteComment();
		mygame.Comments.add(comment);
	}
	//return Score;
	
}
public void EditGame()
{
	Account a = new Account();
	AccountController Ac = new AccountController();
	//Ac.Login(a);
	Scanner in = new Scanner (System.in);
	int Answer;
	String choice;
	Category cat= new Category();
	Game mygame = new Game();
	System.out.println("Please Choose Category of the game !");
	for(int i =0 ; i < Categories.size() ; i++)
	{
		System.out.println(Categories.get(i).CatName);
	}
	
	choice = in.next();
	cat.CatName = choice;
	System.out.println("Please Choose a game !");
	for(int i =0 ; i < Categories.size() ; i++)
	{
		if(Categories.get(i).CatName.equals(cat.CatName))
			for(int j=0 ; j< Categories.get(i).ListOfGames.size() ; j++){
			System.out.println(Categories.get(i).ListOfGames.get(j).Name);}
	}
	String selectedGame;
	int QN = 0;
	selectedGame = in.next();
	for( int i = 0 ; i < Categories.size() ; i++)
	{
		if(Categories.get(i).CatName.equals(cat.CatName))
			for(int j=0 ; j < Categories.get(i).ListOfGames.size() ; j++){
				if(choice.equals(Categories.get(i).ListOfGames.get(j).Name))
					mygame = Categories.get(i).ListOfGames.get(j);
				}
	}
	for(int i=0 ; i <mygame.NumOfQuestions ; i++)
	{
		
		System.out.println(mygame.Questions.get(i).QuestionNumber+"."+game.Questions.get(i).Question);
		System.out.println(mygame.Questions.get(i).Answer);
	}
	
	System.out.println("Please choose one of these options !");
	System.out.println("1.Change Name");
	System.out.println("2.Change Question");
	System.out.println("3.Change Answer");
	Answer = in.nextInt();
	myQuestion q = new myQuestion();
	switch (Answer)
	{
		
	case 1 :
		mygame.Name = in.next();
		System.out.println("Name Changed Successfully !");
		break;
	case 2 :
		for(int i=0;i<game.Questions.size();i++){
			System.out.println(" Please enter question number ");
			QN = in.nextInt();
			if(game.Questions.get(i).QuestionNumber==QN)
		{
				System.out.println("Enter the edited question ");
				q.Question = in.next();
				System.out.println("Question Changed Successfully !");}}
		break;
	case 3 :
		for(int i=0;i<game.Questions.size();i++){
			if(game.Questions.get(i).QuestionNumber==QN)
		{
				System.out.println("Enter the edited Answer ");
				q.Answer = in.next();
				System.out.println("Answer Changed Successfully !");}
	
}
		break;
		}

	}
	


public void RateGame(Game g)
{
	Scanner in = new Scanner (System.in);
	//Game g= new Game();
	String rate;
	System.out.println("Choose one of these rates ,Please!");
	System.out.println("1.Interesting 2.Normal 3.Boring");
	rate = in.nextLine();
	g.Rates.add(rate);
	for(int i = 0 ; i <g.Rates.size() ; i++)
	{
		System.out.println(g.Rates.get(i));
	}
}

public void SaveScore(int score)
{
	game.Scores.add(score);
}
public void RemoveGame(Game g)
{
	Category categ = new Category();
	categ.ListOfGames.remove(g);
	
}
	


}
