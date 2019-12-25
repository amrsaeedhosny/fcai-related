
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	String Name;
	String TeacherName;
	ArrayList <Integer> Scores = new ArrayList <Integer>();
	int HighScore;
	String GameType;
	int NumOfQuestions;
	ArrayList <myQuestion> Questions= new ArrayList <myQuestion>();
	ArrayList <String> Rates= new ArrayList <String>();; 
	Category GameCat = new Category();
	ArrayList <String> Comments = new ArrayList <String> ();
	public ArrayList<Integer> getScores() {
		return Scores;
	}
	public void setScores(ArrayList<Integer> scores) {
		Scores = scores;
	}
	public String getGameType() {
		return GameType;
	}
	public void setGameType(String gameType) {
		GameType = gameType;
	}
	public int getNumOfQuestions() {
		return NumOfQuestions;
	}
	public void setNumOfQuestions(int numOfQuestions) {
		NumOfQuestions = numOfQuestions;
	}
	public ArrayList<myQuestion> getQuestions() {
		return Questions;
	}
	public void setQuestions(ArrayList<myQuestion> questions) {
		Questions = questions;
	}
	public ArrayList<String> getRates() {
		return Rates;
	}
	public void setRates(ArrayList<String> rates) {
		Rates = rates;
	}
	
	public Category getGameCat() {
		return GameCat;
	}
	public void setGameCat(Category gameCat) {
		GameCat = gameCat;
	}
	public String getName() {
		
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getTeacherName() {
		return TeacherName;
	}
	public void setTeacherName(String teacherName) {
		TeacherName = teacherName;
	}
	public int getHighScore() {
		return HighScore;
	}
	public void setHighScore(int highScore) {
		HighScore = highScore;
	}
	
	

}
