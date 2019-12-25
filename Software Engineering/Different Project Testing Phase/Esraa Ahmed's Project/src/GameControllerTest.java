
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GameControllerTest {

	GameController gameController1 = new GameController();
	GameController gameController2 = new GameController();
	

	@BeforeTest
	public void prepareAddGameRequiredData() throws FileNotFoundException {
		gameController1.in = new Scanner(new File("AddGame Input.txt"));
		Category math = new Category(); 
		math.CatName = "Math";
		Category science = new Category();
		science.CatName = "Science";
		gameController1.Categories.add(math);
		gameController1.Categories.add(science);		
	}
	
	@BeforeTest
	public void preprarePlayGameRequiredData() throws FileNotFoundException {
		gameController2.in = new Scanner(new File("PlayGame Input.txt"));
		gameController2.Categories = gameController1.Categories;
	}

	@Test(invocationCount = 4)
	public void AddGame() {
		gameController1.AddGame();
		for ( int i = 0; i < gameController1.Categories.size(); i++ ) {
			System.out.println("Category: " + gameController1.Categories.get(i).CatName);
			for ( int j = 0 ; j < gameController1.Categories.get(i).ListOfGames.size() ; j++) {
				System.out.println("Game:" + gameController1.Categories.get(i).ListOfGames.get(j).Name);
			}
		}
	}

	@Test(dependsOnMethods={"AddGame"}, invocationCount = 2)
	public void PlayGame() throws FileNotFoundException {
		gameController2.PlayGame();
	}
}
