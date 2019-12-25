public class Account 
{
	String firstName;
	String lastName;
	String username;
	String email;
	String birthday;
	String password;
	String gender;
	int score;
	
	void setFirstName ( String firstName )
	{
		this.firstName = firstName;
	}
	
	void setLastName ( String lastName )
	{
		this.lastName = lastName;
	}
	
	void username ( String username )
	{
		this.username = username;
	}
	
	void setEmail ( String email )
	{
		this.email = email;
	}
	
	void setBirthday ( String birthday )
	{
		this.birthday = birthday;
	}
	
	void setPassword ( String password )
	{
		this.password = password;
	}
	
	void setGender ( String gender )
	{
		this.gender = gender;
	}

	String getUsername ()
	{
		return username;
	}
	
	String getPassword() 
	{
		return password;
	}
	
	String getEmail ()
	{
		return email;
	}
	
	void addScore( int score )
	{
		this.score += score;
	}
}