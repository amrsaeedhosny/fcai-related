import java.util.ArrayList;

public class Node
{
	double temporaryAverage;
	ArrayList <Integer> associationValues = new ArrayList <Integer>();
	double average;
	Node left;
	Node right;
	
	Node ()
	{
		temporaryAverage = 0;
		average = 0;
		left = null;
		right = null;
	}
	
	Node ( double temporaryAverage )
	{
		this.temporaryAverage = temporaryAverage;
		average = 0;
		left = null;
		right = null;
	}
	
}
