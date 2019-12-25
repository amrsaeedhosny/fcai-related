

import java.io.FileNotFoundException;
import java.util.Scanner;


public class test {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		AccountController Ac = new AccountController();
		GameController gc = new GameController();
		Scanner in = new Scanner (System.in);
		Category C1 = new Category();
		C1.SetCatName("Funny");
		gc.Categories.add(C1);
		Category C2 = new Category();
		C2.SetCatName("Scientific");
		gc.Categories.add(C2);
		Category C3 = new Category();
		C3.SetCatName("Mathimatic");
		gc.Categories.add(C3);
		Account User = new Account ();
		User = Ac.Registration();
		String Type = Ac.Login(User);
		if(Type.equals( "Teacher"))
		{
			String choose;
			System.out.println("You have the following options to use , Please Choose one or Enter 'Exit' !");
			System.out.println("1.Add Game");
			System.out.println("2.Try Game");
			System.out.println("3.Edit Game");
			System.out.println("4.Remove Game");
			choose = in.nextLine();
			while(!(choose.equals("Exit"))){
			if(choose.equals( "Add Game"))
			{
				gc.AddGame();
				System.out.println("You have the following options to use , Please Choose one or Enter 'Exit' !");
				System.out.println("1.Add Game");
				System.out.println("2.Try Game");
				System.out.println("3.Edit Game");
				System.out.println("4.Remove Game");
				choose = in.nextLine();
			}
			else if(choose.equals("Try Game"))
			{
				gc.PlayGame();
				System.out.println("You have the following options to use , Please Choose one or Enter 'Exit' !");
				System.out.println("1.Add Game");
				System.out.println("2.Try Game");
				System.out.println("3.Edit Game");
				System.out.println("4.Remove Game");
				choose = in.nextLine();
			}
			else if(choose.equals("Edit Game"))
			{
				gc.EditGame();
				System.out.println("You have the following options to use , Please Choose one or Enter 'Exit' !");
				System.out.println("1.Add Game");
				System.out.println("2.Try Game");
				System.out.println("3.Edit Game");
				System.out.println("4.Remove Game");
				choose = in.nextLine();
			}
			else if (choose.equals("Edit Account"))
			{
				Ac.EditAccout(User);
				System.out.println("You have the following options to use , Please Choose one or Enter 'Exit' !");
				System.out.println("1.Add Game");
				System.out.println("2.Try Game");
				System.out.println("3.Edit Game");
				System.out.println("4.Remove Game");
				choose = in.nextLine();
			}
			
//			else if(choose == "Remove Game")
//			{
//				gc.RemoveGame();
//			}
		}}
		else if(Type.equals("Student"))
		{
			String choose;
			System.out.println("You have the following options to use , Please Choose one or Enter 'Exit' !");
			System.out.println("1.Play Game");
			System.out.println("2.Rate Game");
			choose = in.nextLine();
			while(choose != "Exit"){
			if(choose.equals( "Play Game"))
			{
				gc.PlayGame();
				System.out.println("You have the following options to use , Please Choose one or Enter 'Exit' !");
				System.out.println("1.Play Game");
				System.out.println("2.Rate Game");
				choose = in.nextLine();
			}
//			else if(choose == "Remove Game")
//			{
//				gc.RemoveGame();
//			}
		}}
		//System.out.println("HII");
		//gc.AddGame();
		//gc.EditGame();
		//gc.PlayGame();
		

	}

}
