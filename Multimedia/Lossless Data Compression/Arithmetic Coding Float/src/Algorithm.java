import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Algorithm
{
	void compress ( String filePath , String savePath ) throws IOException
	{
		Scanner input = new Scanner( new File(filePath) );
		String content = input.useDelimiter("\\Z").next();
		input.close();
		ArrayList <Node> charList = new ArrayList <Node> ();
		
		// Add all characters to charList and calculate the frequency for every character
		
		for ( int i = 0 ; i < content.length() ; i++ )
		{
			boolean found = false;
			for ( int j = 0 ; j < charList.size() ; j++ )
			{
				if ( content.charAt(i) == charList.get(j).character )
				{
					found = true;
					charList.get(j).frequency++;
				}
			}
			
			if ( !found )
			{
				charList.add( new Node ( content.charAt(i) , 1 ) );
			}
		}
		
		// Calculate the probability for every character
		
		for ( int i = 0 ; i < charList.size() ; i++ )
		{
			charList.get(i).probability = (double)charList.get(i).frequency/content.length();
		}
		
		// Sort charList alphabetically and calculate lower and upper for every character
		
		sort(charList);
		
		double prevProbability = 0;
		
		for ( int i = 0 ; i < charList.size() ; i++ )
		{
			charList.get(i).lowRange = prevProbability;
			charList.get(i).highRange = prevProbability + charList.get(i).probability;
			prevProbability = charList.get(i).highRange;
		}
		
		// Get the code word
		
		double lower = 0, upper = 1, range = 1, codeWord = 0;
		
		for ( int i = 0 ; i < content.length() ; i++ )
		{
			for ( int j = 0 ; j < charList.size() ; j++ )
			{
				if ( content.charAt(i) == charList.get(j).character )
				{
					upper = lower + range * ( charList.get(j).highRange);
					lower = lower + range * ( charList.get(j).lowRange );
					range = upper - lower;
					break;
				}
				
			}	
		}
		
		codeWord = (lower+upper)/2;
		
		// Write the code word in addition to each character with its probability in the compressed file
		
		FileOutputStream compressedFile = new FileOutputStream( savePath + "/compressedFile.txt" );
		DataOutputStream output = new DataOutputStream(compressedFile);
		
		output.writeDouble(codeWord);
		System.out.println(codeWord);
		output.writeInt(content.length());
		
		for ( int i = 0 ; i < charList.size() ; i++ )
		{
			output.writeChar(charList.get(i).character);
			output.writeDouble(charList.get(i).probability);
			
		}
		
		output.close();
	}
	
	void decompress( String filePath , String savePath ) throws IOException
	{
		FileInputStream compressedFile = new FileInputStream(filePath);
		DataInputStream input = new DataInputStream (compressedFile);
		ArrayList <Node> charList = new ArrayList <Node>();
		double codeWord = 0;
		int contentLength = 0;
		String content = "";
		
		codeWord = input.readDouble();
		contentLength = input.readInt();
		
		while ( true )
		{
			try 
			{
				charList.add( new Node ( input.readChar() , input.readDouble() ) );
			} 
			catch (IOException e)
			{
				input.close();
				break;
			}
		}
		
		// Prepare the low range and the high range for each character
		
		double prevProbability = 0;
		
		for ( int i = 0 ; i < charList.size() ; i++ )
		{
			charList.get(i).lowRange = prevProbability;
			charList.get(i).highRange = prevProbability + charList.get(i).probability;
			prevProbability = charList.get(i).highRange;
		}
		
		// Use the code word to get the original content
		
		double lower = 0 , upper = 1 , range = 1;
		
		for ( int i = 0 ; i < contentLength ; i++ )
		{
			for ( int j = 0 ; j < charList.size() ; j++ )
			{
				if ( codeWord >= charList.get(j).lowRange && codeWord <= charList.get(j).highRange )
				{
					content += charList.get(j).character;
					lower = charList.get(j).lowRange;
					upper = charList.get(j).highRange;
					range = upper-lower;
					codeWord = (codeWord-lower)/range;
					break;
				}
			}
		}
		
		// Write the content in the file
		
		FileOutputStream decompressedFile = new FileOutputStream ( savePath + "/decompressedFile.txt" );
		DataOutputStream output = new DataOutputStream ( decompressedFile );
		output.writeBytes(content);
		output.close();
	}
	
	private void sort ( ArrayList <Node> charList )
	{
		for ( int i = 0 ; i < charList.size() ; i++ )
		{
			for ( int j = 1 ; j < charList.size() ; j++ )
			{
				if ( charList.get(j-1).character > charList.get(j).character )
				{
					Node node;
					node = charList.get(j-1);
					charList.set(j-1, charList.get(j));
					charList.set(j, node);
				}	
			}
		}
		
	}
}
