import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AccountControllerTest {
	AccountController accountController = new AccountController();
	AccountController accountController1 = new AccountController();
	
	@BeforeTest
	public void prepareSignUpInputSource () throws FileNotFoundException {
		
		// Reinitialize accountController's Scanner to Accept Input from Accounts.txt
		accountController1.in = new Scanner(new File("Accounts.txt"));
	}

	@BeforeTest
	public void prepareLoginInputSource () throws FileNotFoundException {
		
		// Reinitialize accountController's Scanner to Accept Input from Login Input.txt
		accountController.in = new Scanner(new File("Login Input.txt"));
	}
	
	// InvocationCount Equals to the Number of Test Cases in Login Input.txt File
	@Test(invocationCount = 11)
	public void Signup() {
		Account NewMember = accountController1.Registration();
        String type = accountController.Login(NewMember);
		Assert.assertEquals(NewMember.Type, type);
	}
	
	@Test(invocationCount = 36)
	public void Login() throws FileNotFoundException {
		Account user = new Account();
		accountController = new AccountController();
		accountController.in = new Scanner(new File("Login Input.txt"));
		Assert.assertEquals(user.Type, accountController.Login(user));
	}
}