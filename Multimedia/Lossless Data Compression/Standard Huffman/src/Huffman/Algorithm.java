package Huffman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Node
{
	ArrayList <Character> symbols = new ArrayList <Character> ();
	int frequency;
	String code;
	
	Node ()
	{
		frequency = 0;
		code = null;
	}
}

public class Algorithm 
{
	void compress ( String filePath, String savePath ) throws IOException
	{	
		Scanner input = new Scanner( new File(filePath) );
		String content = input.useDelimiter("\\Z").next();
		input.close();
		
		ArrayList < ArrayList <Node> > huffmanTree = new ArrayList < ArrayList <Node> > ();
		ArrayList <Node> leaves = new ArrayList <Node> ();
		Node node;
		
		// Prepare the leaves of the tree
		
		for ( int i = 0 ; i < content.length() ; i++ )
		{
			boolean found = false;
			for ( int j = 0 ; j < leaves.size() ; j++ )
			{
				if ( content.charAt(i) == leaves.get(j).symbols.get(0).charValue() )
				{
					found = true;
					leaves.get(j).frequency++;
				}
			}
			
			if ( !found )
			{
				node = new Node();
				node.symbols.add( new Character( content.charAt(i) ) );
				node.frequency = 1;
				leaves.add(node);
			}
		}
		
		sort(leaves);
		
		huffmanTree.add(leaves);
		
		// Build the rest of the tree
		
		while ( huffmanTree.get(huffmanTree.size()-1).size() > 2 )
		{
			huffmanTree.add( prepareNewList( huffmanTree.get( huffmanTree.size()-1 ) ) );
		}
		
		// Assign a code for every node
		
		huffmanTree.get(huffmanTree.size()-1).get(0).code = "0";

		if ( huffmanTree.get(huffmanTree.size()-1).size() > 1 ) huffmanTree.get(huffmanTree.size()-1).get(1).code = "1";

		for ( int i = huffmanTree.size()-2 ; i >= 0 ; i-- )
		{
			for ( int j = 0 ; j < huffmanTree.get(i+1).size() ; j++ )
			{
				boolean exist = false;
				String code = huffmanTree.get(i+1).get(j).code;
				for ( int k = 0 ; k < huffmanTree.get(i).size() ; k++ )
				{
					if ( code == huffmanTree.get(i).get(k).code )
					{
						exist = true;
						break;
					}
				}
				if ( !exist )
				{
					int counter = 0;
					for ( int k = 0 ; k < huffmanTree.get(i).size() ; k++ )
					{
						if ( huffmanTree.get(i).get(k).code == null )
						{
							if ( counter == 0 ) huffmanTree.get(i).get(k).code = code + "0";
							else if ( counter == 1 ) huffmanTree.get(i).get(k).code = code + "1";
							else break;
							counter++;
						}
					}
				}
			}
		}
		
		
		// Convert the content to the coded version according to the new codes of every symbol
		
		String codedContent = "";
		
		for ( int i = 0 ; i < content.length() ; i++ )
		{
			for ( int j = 0 ; j < huffmanTree.get(0).size() ; j++ )
			{
				if ( content.charAt(i) == huffmanTree.get(0).get(j).symbols.get(0) )
				{
					 codedContent += huffmanTree.get(0).get(j).code;
					 break;
				}
			}
		}
		
		// Write the codedContent and the huffmanTable in the compressdFile
		
		FileOutputStream compressedFile = new FileOutputStream( savePath + "/compressedFile.txt" );
		DataOutputStream output = new DataOutputStream(compressedFile);
		output.writeUTF(codedContent);
		
		
		for ( int i = 0 ; i < huffmanTree.get(0).size() ; i++ )
		{
			output.writeChar(huffmanTree.get(0).get(i).symbols.get(0).charValue());
			output.writeUTF(huffmanTree.get(0).get(i).code);
		}
		
		output.close();	

	}
	
	void decompress ( String filePath , String savePath ) throws IOException
	{		
		FileInputStream compressedFile = new FileInputStream(filePath);
		DataInputStream input = new DataInputStream (compressedFile);
		ArrayList <Node> symbolsList = new ArrayList <Node>();
		Node node;
		
		String codedContent = input.readUTF();
		while ( true )
		{
			try 
			{
				node = new Node();
				node.symbols.add(new Character(input.readChar()));
				node.code = input.readUTF();
				symbolsList.add(node);
			} 
			catch (IOException e)
			{
				input.close();
				break;
			}
		}
		input.close();		

		// Decoding the coded string using symbolsList codes
		
		String code = "" , content = "";
		
		for ( int i = 0 ; i < codedContent.length() ; i++ )
		{
			code += codedContent.charAt(i);
			for ( int j = 0 ; j < symbolsList.size() ; j++ )
			{
				if ( code.equals(symbolsList.get(j).code) )
				{
					code = "";
					content += symbolsList.get(j).symbols.get(0).charValue();
					break;
				}
			}
		}
		
		// Writing the content on the decompressedFile
		
		FileOutputStream decompressedFile = new FileOutputStream ( savePath + "/decompressedFile.txt" );
		DataOutputStream output = new DataOutputStream ( decompressedFile );
		output.writeBytes(content);
		output.close();
	}
	
	private void sort ( ArrayList <Node> list )
	{
		for ( int i = 0 ; i < list.size() ; i++ )
		{
			for ( int j = 1 ; j < list.size() ; j++ )
			{
				if ( list.get(j-1).frequency < list.get(j).frequency )
				{
					Node node;
					node = list.get(j-1);
					list.set(j-1, list.get(j));
					list.set(j, node);
				}	
			}
		}
		
	}
	
	ArrayList <Node> prepareNewList ( ArrayList <Node> list )
	{
		ArrayList <Node> newList = new ArrayList <Node> ();
		
		for ( int i = 0 ; i < list.size()-2 ; i++ )
		{
			newList.add(list.get(i));
		}
		
		Node node = new Node();
		
		for ( int i = 0 ; i < list.get(list.size()-2).symbols.size() ; i++ )
		{
			node.symbols.add( list.get(list.size()-2).symbols.get(i) );
		}
		
		for ( int i = 0 ; i < list.get(list.size()-1).symbols.size() ; i++ )
		{
			node.symbols.add( list.get(list.size()-1).symbols.get(i) );
		}
		
		node.frequency = list.get(list.size()-2).frequency + list.get(list.size()-1).frequency;
		
		newList.add(node);
		
		sort(newList);
		
		return newList;
	}
	
}
