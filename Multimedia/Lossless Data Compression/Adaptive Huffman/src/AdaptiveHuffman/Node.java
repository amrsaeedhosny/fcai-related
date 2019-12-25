package AdaptiveHuffman;

public class Node 
{
	char symbol; 
	int count;
	String code;
	int number;
	Node parent;
	Node right;
	Node left;
	
	Node ()
	{
		symbol = 0;
		count = 0;
		code = "";
		number = 0;
		parent = null;
		right = null;
		left = null;
	}
}
