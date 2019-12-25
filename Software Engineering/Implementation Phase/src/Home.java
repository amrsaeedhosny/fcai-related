import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Home {
	
	static AccountModel accountModel = new AccountModel();
	static AccountManager accountManager = new AccountManager(accountModel);
	static CategoryModel categoryModel = new CategoryModel();
	static CategoryManager categoryManager = new CategoryManager(categoryModel);
	static TournamentModel tournamentModel = new TournamentModel();
	static TournamentManager tournamentManager = new TournamentManager(tournamentModel);
	static GameModel gameModel = new GameModel();
	static GameManager gameManager = new GameManager(gameModel);
	static String mail = null;	
	static String password = null;
	static String username = null;
	static String categoryName = null;
	static String gameName = null;
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException 
	{	
		ArrayList<Category> categories = new ArrayList<Category>();
		setCategoriesName(categories);
		categoryModel.setCategories(categories);
		
		//int choose = 0;
		
		while(true){	
	    int choose = 0;
		System.out.println("1- New user ? Sign up");
		System.out.println("2- Already have account ? login ");
		
		if(scan.hasNext())
		choose = scan.nextInt();
		
		
		if(choose == 1){
			accountManager.createRegistrationForm();
		}
		validation();
		username = getusername(mail);
		clearScreen();
		view();
		
			
		if(accountManager.checkIfTeacher(mail , password))
		{
			System.out.println("1- Create Tournament  2-else");
			choose = scan.nextInt();
			
			if(choose == 1)
			{
				//clearScreen();
				createTournament();
			}
			
			System.out.println("Choose Category ");
		    categoryName = scan.next();
		    showCategoryGames();
		    System.out.println("1- play   2- Create Game");
		    int choice = scan.nextInt();
			if(choice == 2)
			{
				clearScreen();
				createGame();
			}
		if(choice == 1)
			{
				System.out.println("Enter Game's Name: ");
				gameName = scan.next();
				//clearScreen();
				showGame(gameName);
			}     	
		}
		
		else
		{
			System.out.println("Choose Category ");
		    categoryName = scan.next();
			showCategoryGames();
			System.out.println("Enter Game's Name: ");
			gameName = scan.next();
			//clearScreen();
			showGame(gameName);
		}
		clearScreen();
		view();
		publishTournament();
	  }
	}
	
	
	
	 // Our Buttons that calls a specific functions
	static void showCategoryGames ()
	{
		ArrayList<Game> categoriesGames = new ArrayList<Game>();
		categoriesGames = categoryManager.categoryModel.retrieveCategoryGames(categoryName);
		
		for(int i = 0 ; i < categoriesGames.size() ; i++)
			System.out.print(categoriesGames.get(i).getName() + " ");
		System.out.println();
	}
	
	static void showGame( String gameName )
	{
		gameManager.runGameInterface(gameName, accountManager, username);
	}
	
	static void createGame ()
	{
		//System.out.println("Choose Category ");
		 //categoryName = scan.next();
		 //showCategoryGames();
		gameManager.createGameForm(accountManager, username, categoryManager, categoryName);
	}
	
	static void createTournament ()
	{
		tournamentManager.createTournamentForm(accountManager, username);	
	}
	
	static void publishTournament()
	{
		for(int i  = 0 ; i < tournamentManager.tournamentModel.tournaments.size() ; i++)
		{
			
			System.out.println(tournamentManager.tournamentModel.tournaments.get(i).getName() + "   Date: " 
		    +tournamentManager.tournamentModel.tournaments.get(i).getDate() + "  at Time: " +
		    tournamentManager.tournamentModel.tournaments.get(i).getStartTime() + " to: " +
		    tournamentManager.tournamentModel.tournaments.get(i).getFinishTime());
			System.out.println();
		}
	}
	
	static void validation()
	{
		boolean warn = true;
		do
		{
			System.out.println("Email: ");
			if(scan.hasNext())
			mail = scan.next();
			System.out.println("Password: ");
			if(scan.hasNext())
			password = scan.next();
			if (!warn)
			 System.out.println("Incorrect Email or Password please try again");
		    warn = false;
		}
		while(!accountManager.accountModel.checkAccountExist(mail , password));
	}
	
	static void view()
	{
		for(int i = 0 ; i < categoryManager.categoryModel.categories.size() ; i++)
		{
			System.out.print(categoryManager.categoryModel.categories.get(i).getName()+ " ");
		}
		System.out.println();
	}
	
	static String getusername(String mail)
	{
		for(int i = 0 ; i < accountManager.accountModel.teachers.size(); i++)
		{
			if(mail.equals(accountManager.accountModel.teachers.get(i).getEmail()))
            {
	          return accountManager.accountModel.teachers.get(i).getUsername();
            }
			
		}
		
		for(int i = 0 ; i < accountManager.accountModel.students.size(); i++){
		if(mail.equals(accountManager.accountModel.students.get(i).getEmail()))
            {
	          return accountManager.accountModel.students.get(i).getUsername();
            }
		}
		return null;

	}
	
	static void setCategoriesName(ArrayList<Category>categories)
	{
		for(int i = 0 ; i < 4 ; i++)
		{
			Category temp = new Category();
			categories.add(temp);
	    }
	    categories.get(0).setName("Math");
	    categories.get(1).setName("Science");
	    categories.get(2).setName("Programming");
	    categories.get(3).setName("Languages");
	}
	
	public static void clearScreen() 
	{  
		for(int i=0;i<10;++i)System.out.println();
	}
	

}
