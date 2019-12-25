import java.util.ArrayList;

public class Tournament 
{
	String name;
	String type;
	ArrayList <Question> questions = new ArrayList <Question> ();
	String date;
	String startTime;
	String finishTime;
	
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getType() 
	{
		return type;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	public ArrayList<Question> getQuestions() 
	{
		return questions;
	}
	public void setQuestions(ArrayList<Question> questions) 
	{
		this.questions = questions;
	}
	public String getDate() 
	{
		return date;
	}
	public void setDate(String date) 
	{
		this.date = date;
	}
	public String getStartTime() 
	{
		return startTime;
	}
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}
	public String getFinishTime() 
	{
		return finishTime;
	}
	public void setFinishTime(String finishTime)
	{
		this.finishTime = finishTime;
	}
}