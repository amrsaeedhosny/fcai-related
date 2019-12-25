import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Tag
{
	byte position;
	byte length;
	char nextSymbol;
	
	Tag ()
	{
		position = 0;
		length = 0;
	}
}

public class Algorithm {
	
	void compress ( String fileName ) throws IOException
	{
		Scanner sc =  new Scanner(new File(fileName));
		String content = sc.useDelimiter("\\Z").next();
		sc.close();
		ArrayList <Tag> tags = new ArrayList <Tag>();
		String matchedStr = "";
		boolean longestMatch = false;
		Tag tag = null;
		int bar;
		
		for ( int i = 0 ; i < content.length() ; i++ )
		{
			bar = i;
			longestMatch = false;
			matchedStr = "";
			tag = new Tag();
			tag.nextSymbol = content.charAt(i);
			for ( int j = i-1 ; j >= java.lang.Math.max(0,i-127) ; j-- )
			{
				if ( content.charAt(j) == tag.nextSymbol )
				{
					longestMatch = true;
					if ( matchedStr.length() <= j )
					{
						for ( int k = 0 ; k < matchedStr.length() ; k++ )
						{
							if ( matchedStr.charAt(matchedStr.length()-k-1) != content.charAt(j-k-1) )
							{
								longestMatch = false;
								break;
							}
						}
						if ( longestMatch )
						{
							tag.position = (byte) (bar - ( j- matchedStr.length() ));
							matchedStr += tag.nextSymbol;
							tag.length = (byte) matchedStr.length();
							if ( i+1 < content.length() )
							{
								i++;
								tag.nextSymbol = content.charAt(i);
								longestMatch = false;
								j = i;
							}
							else
							{
								tag.nextSymbol = 0;
								tags.add(tag);
								j = i;
								break;
							}
						}
					}
					else
					{
						tags.add(tag);
						break;
					}
				}
				
			}
			if ( !longestMatch ) tags.add(tag);
		}
		
		for ( int i = 0 ; i < tags.size(); i++ )
		{
			System.out.println(tags.get(i).position + " " + tags.get(i).length + tags.get(i).nextSymbol);
		}
		
		DataOutputStream compressedFile = new DataOutputStream(new FileOutputStream("compressedFile.txt"));
		for ( int i = 0 ; i < tags.size() ; i++ )
		{
			compressedFile.writeByte(tags.get(i).position);
			compressedFile.writeByte(tags.get(i).length);
			compressedFile.writeChar(tags.get(i).nextSymbol);
		}
		compressedFile.close();
	}
	
	
	void decompress ( String fileName ) throws IOException
	{
		ArrayList <Tag> tags = new ArrayList <Tag> ();
		Tag tag = null;
		DataInputStream compressedFile = new DataInputStream(new FileInputStream(fileName));
		String content = "";
		try {
		    while (true) {
		        tag = new Tag();
				tag.position = compressedFile.readByte();
				tag.length = compressedFile.readByte();
				tag.nextSymbol = compressedFile.readChar();
				tags.add(tag);
		    }
		} 
		catch (EOFException ignored) 
		{
		}
		compressedFile.close();
		
		for ( int i = 0 ; i < tags.size() ; i++ )
		{
			for ( int j = 0 ; j < tags.get(i).length ; j++ )
			{
				content += content.charAt(content.length()-tags.get(i).position);	
			}
			if ( tags.get(i).nextSymbol != 0 ) content += tags.get(i).nextSymbol;
		}
		
		DataOutputStream decompressedFile = new DataOutputStream(new FileOutputStream("decompressedFile.txt"));
		decompressedFile.writeBytes(content);
		decompressedFile.close();
	}	
}
