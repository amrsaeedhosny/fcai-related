
public class Node 
{
	char character;
	int frequency;
	double probability;
	double lowRange;
	double highRange;
	
	Node ()
	{
		character = 0;
		frequency = 0;
		probability = 0;
		lowRange = 0;
		highRange = 0;
	}
	
	Node ( char character, int frequency )
	{
		this.character = character;
		this.frequency = frequency;	
	}
	
	Node ( char character, double probability )
	{
		this.character = character;
		this.probability = probability;
	}
	
	void getInfo ()
	{
		System.out.println( "Character: " + character );
		System.out.println( "Frequency: " + frequency );
		System.out.println( "Probability: " + probability );
		System.out.println( "Lower: " + lowRange );
		System.out.println( "Upper: " + highRange );
	}

}
