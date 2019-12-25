package AdaptiveHuffman;

import java.util.ArrayList;

public class HuffmanTree 
{
	private int firstNodeNumber; 
	private Node root, NYT; 
	ArrayList <String> shortCodes = new ArrayList <String> ();
	
	
	HuffmanTree ()
	{		
		firstNodeNumber = 2*256+1; // Getting the first node number by using 2n+1 where n is the number of characters
		root = NYT = new Node();
		initializeShortCodes();
	}
	
	
	// Initialize short code list by using the binary representation of ASCII code for each character 
	
	void initializeShortCodes ()
	{		
		for ( int i = 0 ; i < 256 ; i++ )
		{
			shortCodes.add(Integer.toBinaryString(i));
			
			// Adding leading zeros to every binary representation which has number of bits less than 8 to get fixed size
			
			String leadingZeros = "";
			
			for ( int j = 0 ; j < 8-shortCodes.get(i).length() ; j++ ) leadingZeros += "0";
			
			shortCodes.set( i , leadingZeros + shortCodes.get(i) );	
		}
	}
	
	
	// Check if a symbol exists in the huffmanTree or not and then sends the appropriate code
	
	String getCode ( char symbol )
	{
		String code;
		Node node = search(symbol);
		
		if ( node == null ) // If the symbol is not found send NYT node code and symbol short code
		{
			code = NYT.code + shortCodes.get((int)symbol);
		}
		else // If the symbol is found send symbol node code
		{
			code = node.code;	
		}
		return code;
	}
	
	
	// Compression version of search to search for a symbol in the tree and returns its node
	
	Node search ( char symbol )
	{
		Node node = root;
		
		while ( node.right != null && node.left != null )
		{			
			if ( node.right.symbol == 0 )  // If the node is just a parent and doesn't contain a symbol
			{
				if ( node.left.symbol == symbol )
				{
					node = node.left;
					return node;
				}
				else
				{
					node = node.right;
				}
			}
			else
			{
				if ( node.right.symbol == symbol )
				{
					node = node.right;
					return node;
				}
				else
				{
					node = node.left;
				}
			}
		}
		
		return null;
	}
	
	// Decompression version of search to return the ASCII code of a symbol with specific code in the tree if exists
	
	int search ( String code )
	{
		Node node = root;
		
		if ( NYT.code.equals(code) )
		{
			return -1;
		}
		else
		{
			while (node.right != null && node.left != null) 
			{
				if ( node.right.symbol == 0 ) 
				{
					if ( node.left.code.equals(code) ) 
					{
						return node.left.symbol;
					} 
					else 
					{
						node = node.right;
					}
				} 
				else 
				{
					if ( node.right.code.equals(code) ) 
					{
						return node.right.symbol;
					} 
					else 
					{
						node = node.left;
					}
				}
			}
		}
		
		// If the code that was sent is not in the tree
		
		return -2; 
	}
	
	
	// Updating the tree after adding a symbol
	
	void update ( char symbol )
	{
		Node node = search(symbol);
		
		// If symbol first occurrence
		
		if ( node == null )
		{
			// Splitting NYT Node into new NYT and symbol node
			
			NYT.right = new Node();
			NYT.left = new Node();
			
			// Update symbol node
			
			NYT.right.symbol = symbol;
			NYT.right.count = 1;
			NYT.right.code = NYT.code + "1";
			NYT.right.number = firstNodeNumber--;
			NYT.right.parent = NYT;
			
			// Update new NYT node
			
			NYT.left.code = NYT.code + "0";
			NYT.left.number = firstNodeNumber--;
			NYT.left.parent = NYT;
			
			// Update old NYT node
			
			NYT.count = 1;
			NYT = NYT.left;
		
			node = NYT.parent;
			if ( node.parent == null ) return;
			else node = node.parent;
		}
		else
		{
			Node startNode = root;
			
			// Check if there is a swap condition exist with the most highest symbols
			
			while ( node.parent != startNode )
			{
				if ( startNode.right.symbol == 0 )
				{
					if ( node.count >= startNode.left.count )
					{
						char tempSymbol;
						tempSymbol = node.symbol;
						node.symbol = startNode.left.symbol;
						startNode.left.symbol = tempSymbol;
						startNode.left.count++;
						node = startNode;
						break;
					}
					startNode = startNode.right;
				}
				else
				{
					if ( node.count >= startNode.right.count )
					{
						char tempSymbol;
						tempSymbol = node.symbol;
						node.symbol = startNode.right.symbol;
						startNode.right.symbol = tempSymbol;
						startNode.right.count++;
						node = startNode;
						break;
					}
					startNode = startNode.left;
				}
			}
		}
		
		// Check if there is a swap condition exists with the node with the same level
		
		while ( node.parent != null )
		{
			if ( node == node.parent.left && node.count >= node.parent.right.count )
			{
				// Swap node numbers
				
				int tempNumber;
				tempNumber = node.number;
				node.number = node.parent.right.number;
				node.parent.right.number = tempNumber;
				
				// Swap node codes
				
				String tempCode;
				tempCode = node.code;
				node.code = node.parent.right.code;
				node.parent.right.code = tempCode;
				
				// Swap the entire nodes
				
				Node tempNode;
				tempNode = node.parent.right;
				node.parent.right = node.parent.left;
				node.parent.left = tempNode;
				
				// Edit the codes of the rest of nodes
				
				Node currentNode;
				
				// If node is a parent node, make currentNode equals node and start editing the codes of its children
				if ( node.symbol == 0 ) currentNode = node;
				// If the node is a symbol node, make currentNode equals the other node which is the parent one and start editing the codes of its children 
				else currentNode = node.parent.left; 
				
				while ( currentNode.right != null && currentNode.left != null )
				{
					currentNode.right.code = currentNode.code + "1";
					currentNode.left.code = currentNode.code + "0";
					if ( currentNode.right.symbol == 0 ) currentNode = currentNode.right;
					else currentNode = currentNode.left;
				}
			}
			node.count++;
			node = node.parent;
		}
		
		node.count++;		
	}	
}
