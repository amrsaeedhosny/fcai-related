import java.util.ArrayList;

public class Question 
{
	String type;
	String header;
	ArrayList <String> choices = new ArrayList <String> ();
	String solution;
	String hint;
	
	Question ( String type )
	{
		this.type = type;
		if ( type.equals("MCQ") )
		{
			for ( int i = 0 ; i < 4 ; i++ ) choices.add(null);
		}
		else
		{
			choices.add("True");
			choices.add("False");
		}
		hint = "NO HINT";
	}
	
	void setHeader ( String header )
	{
		this.header = header;
	}
	
	// Set choices in case of MCQ type
	
	void setChoices ( ArrayList<String> choices )
	{
		for ( int i = 0 ; i < 4 ; i++ )
		{
			this.choices.set(i, choices.get(i));
		}
	}
	
	void setSolution ( String solution )
	{
		this.solution = solution;
	}
	
	void setHint ( String hint )
	{
		this.hint = hint;
	}
	
}