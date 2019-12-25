import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Algorithm 
{
	
	void compress ( String imagePath, String savePath, int quantizationLevels ) throws IOException
	{
		int [][] pixels = readImage(imagePath);
		ArrayList <Integer> imageValues = new ArrayList <Integer> ();
		
		// Complete Binary Tree
		
		ArrayList <Node> tree = new ArrayList <Node> ();
		
		// Add all pixel values to the array list imageValues
		
		for ( int i = 0 ; i < pixels.length ; i++  )
		{
			for ( int j = 0 ; j < pixels[i].length ; j++)
			{
				imageValues.add(pixels[i][j]);
			}
		}
		
		// Calculate the number of bits  from the number of quantization levels
		
		int bits = getBits(quantizationLevels);
		
		// Prepare the root node by putting all values in its array and calculate its average
		
		Node root = new Node();
		root.associationValues = imageValues;
		root.average = getAverage(root.associationValues);
		tree.add(root);
		
		// Perform splitting and association
		
		int startIndex = 0;
		
		for ( int i = 1 ; i <= bits ; i++ )
		{
			int size = tree.size();
			
			// Split all the nodes of the previous level to create the new level
			
			for ( int j = startIndex ; j < size ; j++ )
			{
				tree.get(j).left = new Node( tree.get(j).average - 1 );
				tree.get(j).right = new Node( tree.get(j).average + 1 );
				tree.add( tree.get(j).left );
				tree.add( tree.get(j).right );
			}
			
			// Update the startIndex to the index of the first node in the new level
			
			startIndex = size;
			
			// Associate all of the image values to the appropriate node
			
			for ( int j = 0 ; j < imageValues.size() ; j++ )
			{
				double distance = 1000; // Large value
				Node associationNode = null;
				
				for ( int k = startIndex ; k < tree.size() ; k++ )
				{
					if ( abs(imageValues.get(j) - tree.get(k).temporaryAverage) < distance )
					{
						distance = abs(imageValues.get(j) - tree.get(k).temporaryAverage);
						associationNode = tree.get(k);
					}	
				}
				
				associationNode.associationValues.add(imageValues.get(j));
			}
			
			// Calculate the average for each node in this level
			
			for ( int j = startIndex ; j < tree.size() ; j++ )
			{
				tree.get(j).average = getAverage(tree.get(j).associationValues);
			}
		}
		
		// Perform the association on the leaves of the tree until the values become stable
		
		boolean stable = false;
		
		while ( !stable )
		{
			stable = true;
			
			for ( int i = startIndex ; i < tree.size() ; i++ )
			{
				tree.get(i).temporaryAverage = tree.get(i).average;
				tree.get(i).associationValues = new ArrayList <Integer>();
			}
			
			for ( int i = 0 ; i < imageValues.size() ; i++ )
			{
				double distance = 1000;
				Node associationNode = null;
				
				for ( int j = startIndex ; j < tree.size() ; j++ )
				{
					if ( abs(imageValues.get(i) - tree.get(j).temporaryAverage) < distance )
					{
						distance = abs(imageValues.get(i) - tree.get(j).temporaryAverage);
						associationNode = tree.get(j);
					}	
				}
				
				associationNode.associationValues.add(imageValues.get(i));
			}
			

			for ( int i = startIndex ; i < tree.size() ; i++ )
			{
				tree.get(i).average = getAverage(tree.get(i).associationValues);
			}
			
			
			for ( int i = startIndex ; i < tree.size() ; i++ )
			{
				if ( tree.get(i).average != tree.get(i).temporaryAverage )
				{
					stable = false;
				}
			}
		}
		
		// Prepare the ranges to assign each pixel value to the appropriate codeword
		
		ArrayList <Range> ranges = new ArrayList <Range>();
		
		for ( int i = 0 ; i < quantizationLevels ; i++ )
		{
			ranges.add(new Range());
		}
		
		for ( int i = startIndex+1 ; i < tree.size() ; i++ )
		{
			ranges.get(i-startIndex-1 ).upper = (tree.get(i-1).average + tree.get(i).average)/2;
			ranges.get(i-startIndex).lower = ranges.get(i-startIndex-1).upper;
		}
		
		// Edit the pixel values of the image to the code word
		
		for ( int i = 0 ; i < pixels.length ; i++ )
		{
			for ( int j = 0 ; j < pixels[i].length ; j++ )
			{
				for ( int k = 0 ; k < ranges.size() ; k++ )
				{
					if ( pixels[i][j] >= ranges.get(k).lower && pixels[i][j] < ranges.get(k).upper )
					{
						pixels[i][j] = k;
						break;
					}
				}
			}
		}
		
		// Write size of averages, array list of averages, height, width, and image content in a file
		
		DataOutputStream output = new DataOutputStream(new FileOutputStream(savePath+"/compressedImage.txt"));
		
		output.writeInt(quantizationLevels);
		for ( int i = startIndex ; i < tree.size() ; i++ )
		{
			output.writeDouble(tree.get(i).average);
		}
		
		output.writeInt(pixels[0].length);
		output.writeInt(pixels.length);
		
		for ( int i = 0 ; i < pixels.length ; i++ )
		{
			for ( int j = 0 ; j < pixels[i].length ; j++ )
			{
				output.writeInt(pixels[i][j]);
			}
		}
		
		output.close();
	}
	
	void decompress ( String compressedImagePath, String savePath ) throws IOException
	{
		DataInputStream input = new DataInputStream(new FileInputStream(compressedImagePath));
		
		// Read the content of the compressed file
		
		int averagesSize = input.readInt();
		
		ArrayList <Double> averages = new ArrayList <Double> ();
		
		for ( int i = 0 ; i < averagesSize ; i++ )
		{
			averages.add(input.readDouble());
		}
		
		int width = input.readInt(), height = input.readInt();
		
		int [][] pixels = new int [height][width];
		
		for ( int i = 0 ; i < height ; i++ )
		{
			for ( int j = 0 ; j < width ; j++ )
			{
				pixels[i][j] = input.readInt();
			}
		}
		
		// Change the codeword values to the average values
		
		for ( int i = 0 ; i < height ; i++ )
		{
			for ( int j = 0 ; j < width ; j++ )
			{
				pixels[i][j] = averages.get(pixels[i][j]).intValue();
			}
		}
		
		
		
		// Write the decompressed image in the save path
		
		writeImage( pixels,savePath+"/decompressedImage.jpg", width, height);
		
		input.close();
	}
	
	class Range
	{
		double lower = 0;
		double upper = 256;
	}
	
	
	int getBits ( int quantizationLevels )
	{
		int bits = 0;
		while ( quantizationLevels != 1 )
		{
			bits++;
			quantizationLevels /= 2;
		}
		return bits;
	}
	
	double getAverage ( ArrayList <Integer> associationValues )
	{
		double average = 0;
		
		for ( int i = 0 ; i < associationValues.size() ; i++ )
		{
			average += associationValues.get(i);
		}
		
		average /= associationValues.size();
		
		return average;
	}
	
	double abs ( double number )
	{
		if ( number < 0 ) number *= -1;
		
		return number;
	}
	
	public static int[][] readImage(String filePath)
	{
	    int width=0;
		int height=0;
	    File file=new File(filePath);
	    BufferedImage image=null;
	    try
	    {
	        image=ImageIO.read(file);
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }

	    width=image.getWidth();
	    height=image.getHeight();
	    
	    int[][] pixels=new int[height][width];

	    for(int x=0;x<width;x++)
	    {
	        for(int y=0;y<height;y++)
	        {
	            int rgb=image.getRGB(x, y);
	            int alpha = (rgb >> 24) & 0xff;
	            int r = (rgb >> 16) & 0xff;
	            int g = (rgb >> 8) & 0xff;
	            int b = (rgb >> 0) & 0xff;

	            pixels[y][x]=r;
	        }
	    }

	    return pixels;
	}
	
	public static void writeImage(int[][] pixels,String outputFilePath,int width,int height)
    {
        File fileout=new File(outputFilePath);
        
        try 
        {
			fileout.createNewFile();
		} 
        catch (IOException e1) 
        {
			e1.printStackTrace();
		}
        
        BufferedImage image2=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB );

        for(int x=0;x<width ;x++)
        {
            for(int y=0;y<height;y++)
            {
                image2.setRGB(x,y,(pixels[y][x]<<16)|(pixels[y][x]<<8)|(pixels[y][x]));
            }
        }
        try
        {
            ImageIO.write(image2, "jpg", fileout);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}

