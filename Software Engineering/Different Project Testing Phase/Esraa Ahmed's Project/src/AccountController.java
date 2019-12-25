
import java.util.ArrayList;
import java.util.Scanner;


public class AccountController {
	Account A = new Account();
	Scanner in = new Scanner (System.in);
	ArrayList <Account> Acc = new ArrayList <Account>();
public Account Registration()
{
	System.out.println("Please Enter your name !");
	A.Name = in.next();
	System.out.println("Please Enter your Age !");
	A.Age = in.nextInt();
	System.out.println("Please Enter your Gender !");
	A.Gender = in.next();
	System.out.println("Please Enter your Email !");
	A.Email = in.next();
	System.out.println("Please Enter your Password !");
	A.Password = in.next();
	System.out.println("Please Enter Accout Type (Teacher or Student) !");
	A.Type = in.next();
	SaveInfo(A);
	 return A;
}
public String Login (Account User)
{
	
	System.out.println("Please Enter User Name : ");
	User.Name= in.next();
	System.out.println("Please Enter your Password ! : ");
	User.Password = in.next();
	for(int i = 0 ; i <Acc.size() ; i++){
		if(User.Name.equals(A.Name)&&User.Password.equals(A.Password))
		{
			User = A;
			System.out.println("Logged in !");
		}
		else
		{
			System.out.println("not valid !");
		}
	}
	return (User.Type);
}
public void Logout()
{

}
public void EditAccout(Account Accou)
{
	int answer = 0;
	System.out.println(Accou.Name);
	System.out.println(Accou.Age);
	System.out.println(Accou.Email);
	System.out.println(Accou.Gender);
	
	System.out.println("Please choose one of these options !");
	System.out.println("1.Change Name");
	System.out.println("2.Change Email");
	System.out.println("3.Change Age");
	System.out.println("4.Change Gender");
	System.out.println("5.Change Password");
	
	switch (answer)
	{
	case 1 :
		System.out.println("Please Enter new Name !");
		Accou.Name = in.next();
		System.out.println("Name Changed Successfully !");
	case 2 :
		System.out.println("Please Enter new Email !");
		Accou.Email = in.next();
		System.out.println("Email Changed Successfully !");
	case 3 :
		System.out.println("Please Enter new Age !");
		Accou.Age = in.nextInt();
		System.out.println("Age Changed Successfully !");
	case 4 :
		System.out.println("Please Enter new Gender !");
		Accou.Gender = in.next();
		System.out.println("Gender Changed Successfully !");
	case 5 :
		System.out.println("Please Enter new Password !");
		Accou.Password = in.next();
		System.out.println("Password Changed Successfully !");
	}
	
	
}
void SaveInfo (Account acc){
	  Acc.add(acc);
}
}
