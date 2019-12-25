
import java.security.KeyStore.Builder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
public class Account {

	String Name;
	int Age;
	String Email;
	String Password;
	String Gender;
	String Type;
	
	public void setPassword(String password) {
		Password = password;
	}
	public void setType(String type) {
		Type = type;
	}
	void setName(String name){
	 Name = name;
 }
 	void setAge(int age){
	 	Age = age;
 	}
 	void setEmail(String email)
 	{
	 	Email = email;
 	}
 	void SetPW(String pw)
 	{
	 	Password = pw;
 	}
 	void setGender (String g)
 	{
	 	Gender = g;
 	}
 	public String getPassword() {
		return Password;
	}
	public String getName() {
		return Name;
	}
	public int getAge() {
		return Age;
	}
	public String getEmail() {
		return Email;
	}
	public String getGender() {
		return Gender;
	
	
	
// public boolean checkValid (String email,String Passw)
// {
//	 boolean ch=false;
//	 for(int i=0 ; i < Acc.size() ; i++)
//	 {
//		 if(Acc.get(i).Email.equals(email)&&Acc.get(i).Password.equals(Passw)){
//			 ch =  true;
//		 
//	 }
		 
// }
//	return ch;
// 
// }
 
	}
	public String getType() {
		return Type;
	}}
