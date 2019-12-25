
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Teacher extends Account {
	String RespondToComments () throws FileNotFoundException
	{
		String reply;
		Scanner in = new Scanner (System.in);
		reply = in.next();
		return reply;
		
	}

}