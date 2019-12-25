package LZWPackage;
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
	private ArrayList <String> dictionary;
	
	private void initializeDictionary ()
	{
		dictionary = new ArrayList <String> ();
		
		for ( int i = 0 ; i < 256 ; i++ )
		{
			dictionary.add(String.valueOf((char)i));
		}
	}
	
	private int inDictionary ( String codedString )
	{
		for ( int i = 0 ; i < dictionary.size() ; i++ )
		{
			if ( dictionary.get(i).equals(codedString) )
			{
				return i;
			}
		}
		return -1;
	}
	
	void compress ( String filePath , String savePath ) throws IOException
	{
		initializeDictionary();
		ArrayList <Short> indexes = new ArrayList <Short> ();
		Scanner input = new Scanner( new File(filePath) );
		String content = input.useDelimiter("\\Z").next() , codedString = "";
		short index = -1;

		for ( int i = 0 ; i < content.length() ; i++ ) 
		{
			if ( inDictionary( codedString + content.charAt(i) ) == -1 )
			{	
				indexes.add( new Short(index) );
				dictionary.add( codedString + content.charAt(i) );
				codedString = String.valueOf(content.charAt(i));
				index = (short) inDictionary( String.valueOf( content.charAt(i) ) );
			}
			else
			{
				index = (short) inDictionary( codedString + content.charAt(i) );
				codedString += content.charAt(i);
			}
			
			// Restart the dictionary
			
			if ( dictionary.size() == 32768 )
			{
				initializeDictionary();
				codedString = "";
				index = -1;
				i--;
			}
		}
		
		indexes.add( new Short(index) );
		
		
		FileOutputStream compressedFile = new FileOutputStream( savePath + "/compressedFile.txt" );
		DataOutputStream output = new DataOutputStream(compressedFile);
		
		for ( int i = 0 ; i < indexes.size() ; i++ ) 
		{
			output.writeShort(indexes.get(i).shortValue());
		}
		
		output.close();	
	}
	
	void decompress ( String filePath , String savePath ) throws IOException
	{
		initializeDictionary();
		ArrayList <Short> indexes = new ArrayList <Short> ();
		FileInputStream compressedFile = new FileInputStream(filePath);
		DataInputStream input = new DataInputStream (compressedFile);
		String content = "" , prevCodedString = "";
		
		
		while ( true )
		{
			try 
			{
				indexes.add(new Short(input.readShort()));
			} 
			catch (IOException e)
			{
				break;
			}
		}
				
		if ( !indexes.isEmpty() )
		{
			prevCodedString = content = dictionary.get(indexes.get(0));
		}
		
		for ( int i = 1 ; i < indexes.size() ; i++ )
		{	

			if ( indexes.get(i).shortValue() < dictionary.size() )
			{
				dictionary.add( prevCodedString + dictionary.get(indexes.get(i)).charAt(0) );
			}
			else
			{
				dictionary.add( prevCodedString + prevCodedString.charAt(0) );
			}
			content += dictionary.get(indexes.get(i));
			prevCodedString = dictionary.get(indexes.get(i));
			
			// Restart the dictionary
			
			if ( dictionary.size() == 32768 )
			{
				initializeDictionary();
				prevCodedString = dictionary.get(indexes.get(i));
			}
		}
				
		FileOutputStream decompressedFile = new FileOutputStream ( savePath + "/decompressedFile.txt" );
		DataOutputStream output = new DataOutputStream ( decompressedFile );
		output.writeBytes(content);
		output.close();
	}

}
