

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Student extends Account {
	int Score;
	

String WriteComment() throws FileNotFoundException{
	String comment;
	Scanner in = new Scanner (System.in);
	comment = in.next();
	return comment;
}
}