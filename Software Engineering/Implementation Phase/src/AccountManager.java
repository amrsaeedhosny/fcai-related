public class AccountManager 
{
	AccountModel accountModel;
	
	AccountManager ( AccountModel accountModel )
	{
		this.accountModel = accountModel;
	}
	
	void createRegistrationForm ()
	{
		RegistrationForm registrationForm = new RegistrationForm();
		
		validateFormContent ( registrationForm );
		
		if ( registrationForm.type.equals("Teacher"))
		{
			Teacher teacher = new Teacher ();
			teacher.firstName = registrationForm.firstName;
			teacher.lastName = registrationForm.lastName;
			teacher.username = registrationForm.username;
			teacher.email = registrationForm.email;
			teacher.birthday = registrationForm.birthday;
			teacher.password = registrationForm.password;
			teacher.gender = registrationForm.gender;
			teacher.professionalReference = registrationForm.professionalReference;
			accountModel.addTeacher(teacher);
		}
		else
		{
			Student student = new Student ();
			student.firstName = registrationForm.firstName;
			student.lastName = registrationForm.lastName;
			student.username = registrationForm.username;
			student.email = registrationForm.email;
			student.birthday = registrationForm.birthday;
			student.password = registrationForm.password;
			student.gender = registrationForm.gender;
			student.educationalStage = registrationForm.educationalStage;
			accountModel.addStudent(student);
		}
		
		registrationForm.createdSuccessfullyMessage();
	}
	
	void validateFormContent ( RegistrationForm registrationForm )
	{
		while ( accountModel.checkUsernameExist(registrationForm.username) )
		{
			registrationForm.usernameExistMessage();
			registrationForm.insertUsername();
		}
		
		while ( accountModel.checkEmailExist(registrationForm.email) )
		{
			registrationForm.emailExistMessage();
			registrationForm.insertEmail();
		}
	}
	
	void addTournamentToAccount ( String username, Tournament tournament )
	{
		accountModel.insertTournament(username, tournament);
	}
	
	void addGameToAccount ( String username, Game game )
	{
		accountModel.insertGame(username, game);
	}
	
	void addScoreToAccount ( String username, int score )
	{
		accountModel.insertScore(username, score);
		
	}
	
	boolean checkIfTeacher(String  email , String password)
	{
		for(int i = 0 ; i < accountModel.teachers.size() ; i++)
		{
			if(email.equals(accountModel.teachers.get(i).getEmail()) && password.equals(accountModel.teachers.get(i).getPassword()))
				{
				  return true;
				}
		}
		
		return false;
	}
	
	
}