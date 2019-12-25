import java.util.ArrayList;

public class AccountModel 
{
	ArrayList <Teacher> teachers = new ArrayList <Teacher> ();
	ArrayList <Student> students = new ArrayList <Student> ();
	
	void insertTournament ( String username, Tournament tournament )
	{
		for ( int i = 0 ; i < teachers.size() ; i++ )
		{
			if ( teachers.get(i).getUsername().equals(username) )
			{
				teachers.get(i).addTournament(tournament);
			}
		}
	}
	
	void insertGame ( String username, Game game )
	{
		for ( int i = 0 ; i < teachers.size() ; i++ )
		{
			if ( teachers.get(i).getUsername().equals(username) )
			{
				teachers.get(i).addGame(game);
			}
		}
	}
	
	void insertScore ( String username, int score )
	{
		// If the player was a teacher
		
		for ( int i = 0 ; i < teachers.size() ; i++ )
		{
			if ( teachers.get(i).getUsername().equals(username) )
			{
				teachers.get(i).addScore(score);
			}
		}
		
		// If the player was a student
		
		for ( int i = 0 ; i < students.size(); i++ )
		{
			if ( students.get(i).getUsername().equals(username) )
			{
				students.get(i).addScore(score);
			}
			
		}
	}
	
	boolean checkUsernameExist ( String username )
	{
		for ( int i = 0 ; i < teachers.size() ; i++ )
		{
			if ( teachers.get(i).getUsername().equals(username) )
			{
				return true;
			}
		}
				
		for ( int i = 0 ; i < students.size(); i++ )
		{
			if ( students.get(i).getUsername().equals(username) )
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	boolean checkEmailExist ( String email )
	{
		for ( int i = 0 ; i < teachers.size() ; i++ )
		{
			if ( teachers.get(i).getEmail().equals(email) )
			{
				return true;
			}
		}
				
		for ( int i = 0 ; i < students.size(); i++ )
		{
			if ( students.get(i).getEmail().equals(email) )
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	boolean checkAccountExist(String email , String password)
	{
		
		for(int i = 0 ; i < teachers.size() ; i++)
		{
			if(email.equals(teachers.get(i).getEmail()) && password.equals(teachers.get(i).getPassword()))
			{
				return true;
			}
		}
	
		for(int i = 0 ; i < students.size() ; i++)
		{
			if(email.equals(students.get(i).getEmail()) && password.equals(students.get(i).getPassword()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	void addTeacher ( Teacher teacher )
	{
		teachers.add(teacher);
	}
	
	void addStudent ( Student student )
	{
		students.add(student);
	}

}