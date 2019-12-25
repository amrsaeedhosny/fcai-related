package AdaptiveHuffman;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class HuffmanOperations 
{	
	void compress ( String filePath , String savePath ) throws IOException
	{
		
		// Load the content of the file in the string content
		
		HuffmanTree huffmanTree = new HuffmanTree();
		Scanner input = new Scanner( new File(filePath) );
		String content = "" , codedContent = "";
		if ( input.hasNext() ) content = input.useDelimiter("\\Z").next(); // Check if the file is empty or not
		input.close();

		// Perform the getCode and update functions to get the coded version of the content
		
		for ( int i = 0 ; i < content.length() ; i++ )
		{
			codedContent += huffmanTree.getCode(content.charAt(i));
			huffmanTree.update(content.charAt(i));
		}
		
		// Write the coded version in the compressedFile
		
		FileOutputStream compressedFile = new FileOutputStream( savePath + "/compressedFile.txt" );
		DataOutputStream output = new DataOutputStream(compressedFile);
		output.writeBytes(codedContent);
		output.close();
	}
	
	void decompress ( String filePath , String savePath ) throws IOException
	{
		// Load the coded content of the file in the string codedContent

		HuffmanTree huffmanTree = new HuffmanTree();
		Scanner input = new Scanner( new File(filePath) );
		String codedContent = "" , content = "";
		if ( input.hasNext() ) codedContent = input.useDelimiter("\\Z").next(); // Check if the file is empty or not
		input.close();
		
		// Take the first short code and then convert it and add the resulted character to the huffmanTree and content
		
		String code = "";
		
		if ( codedContent.length() >= 8 )
		{
			for ( int i = 0 ; i < 8 ; i++ ) code += codedContent.charAt(i);
			char firstCharacter = (char) Integer.parseInt(code,2);
			content += firstCharacter;
			huffmanTree.update(firstCharacter);
		}
		
		code = "";
		
		// Decoding the rest of the codedContent
		
		for ( int i = 8 ; i < codedContent.length() ; i++ )
		{
			code += codedContent.charAt(i);
			int result = huffmanTree.search(code);
			
			if ( result >= 0 ) // If the code exists in the huffmanTree and for a specific symbol
			{
				content += (char)result;
				huffmanTree.update((char)result);
				code = "";
			}
			else if ( result == -1 ) // If the code exists in the huffmanTree and for NYT node
			{
				code = "";
				for ( int j = i+1 ; j <= i+8 ; j++ ) code += codedContent.charAt(j);
				char newCharacter = (char) Integer.parseInt(code,2);
				content += newCharacter;
				huffmanTree.update(newCharacter);
				code = "";
				i += 8;
			}
		}
		
		// Write the content in the decompressedFile
		
		FileOutputStream decompressedFile = new FileOutputStream ( savePath + "/decompressedFile.txt" );
		DataOutputStream output = new DataOutputStream ( decompressedFile );
		output.writeBytes(content);
		output.close();
	}
	
}
