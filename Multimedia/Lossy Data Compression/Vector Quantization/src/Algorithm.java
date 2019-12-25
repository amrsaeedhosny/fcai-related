import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Algorithm 
{
	void compress ( String imagePath, String savePath, int levels, int blockHeight, int blockWidth ) throws IOException
	{
		// Read the image and update its size to fit the block size
		
		Image image = new Image();
		
		int[][] pixels = image.readImage(imagePath); 
		
		pixels = image.performPadding(pixels, pixels.length, pixels[0].length, blockHeight, blockWidth);
		
		int imageHeight = pixels.length;
		int imageWidth = pixels[0].length;
		
		// Divide the image into blocks
		
		ArrayList < Block > blocks = image.divideIntoBlocks(pixels, imageHeight, imageWidth, blockHeight, blockWidth);
		
		// Calculate the number of bits using quantization levels
		
		int bits = getBits(levels);
		
		// Prepare the root node by putting all of the blocks in its ArrayList and calculate its average block
		
		Node root = new Node( blockHeight , blockWidth );
		root.associationBlocks = blocks;
		root.averageBlock = getAverage( root.associationBlocks );
				
		Tree tree = new Tree(root);
				
		// Perform splitting and association
		
		int startIndex = 0;
		
		for ( int i = 1 ; i <= bits ; i++ )
		{
			int size = tree.size();
			
			// Split all the nodes of the previous level to create the new level
			
			for ( int j = startIndex ; j < size ; j++ )
			{
				tree.split(j);
			}
			
			// Update the startIndex to the index of the first node in the new level
			
			startIndex = size;
			
			// Associate all of the blocks to the appropriate node
			
			for ( int j = 0 ; j < blocks.size() ; j++ )
			{
				int distance = getDistance(blocks.get(j),tree.get(startIndex).associationAverageBlock)+1;
				
				Node associationNode = null;
				
				for ( int k = startIndex ; k < tree.size() ; k++ )
				{
					if ( getDistance(blocks.get(j),tree.get(k).associationAverageBlock) < distance )
					{
						distance = getDistance( blocks.get(j) , tree.get(k).associationAverageBlock );
						associationNode = tree.get(k);
					}	
				}
							
				associationNode.associationBlocks.add(blocks.get(j));
			}
			
			// Calculate the average block for each node in this level

			for ( int j = startIndex ; j < tree.size() ; j++ )
			{
				tree.get(j).averageBlock = getAverage(tree.get(j).associationBlocks);
			}
		}
				
		
		// Perform the association on the leaves of the tree until the blocks become stable
		
		boolean stable = false;
		
		while ( !stable )
		{
			stable = true;
			
			for ( int i = startIndex ; i < tree.size() ; i++ )
			{
				tree.get(i).associationAverageBlock = tree.get(i).averageBlock;
				tree.get(i).associationBlocks = new ArrayList < Block > ();
			}
			
			for ( int i = 0 ; i < blocks.size() ; i++ )
			{
				int distance = getDistance(blocks.get(i),tree.get(startIndex).associationAverageBlock)+1;
				Node associationNode = null;
				
				for ( int j = startIndex ; j < tree.size() ; j++ )
				{
					if ( getDistance(blocks.get(i), tree.get(j).associationAverageBlock) < distance )
					{
						distance = getDistance(blocks.get(i), tree.get(j).associationAverageBlock);
						associationNode = tree.get(j);
					}	
				}
				
				associationNode.associationBlocks.add(blocks.get(i));
			}
			

			for ( int i = startIndex ; i < tree.size() ; i++ )
			{
				tree.get(i).averageBlock = getAverage(tree.get(i).associationBlocks);
			}
			
			
			for ( int i = startIndex ; i < tree.size() ; i++ )
			{
				for ( int j = 0 ; j < blockHeight ; j++ )
				{
					for ( int k = 0 ; k < blockWidth ; k++ )
					{
						if ( tree.get(i).averageBlock.get( j , k ) != tree.get(i).associationAverageBlock.get( j , k ) )
						{
							stable = false;
							break;
						}
					}
					if ( !stable ) break;
				}
				if ( !stable ) break;
			}
		}
		
		
		DataOutputStream output = new DataOutputStream(new FileOutputStream(savePath+"/compressedImage.txt"));
		
		output.writeInt(imageHeight); output.writeInt(imageWidth);
		
		output.writeInt(blockHeight); output.writeInt(blockWidth);
		
		output.writeInt(levels);
		
		for ( int i = startIndex ; i < tree.size() ; i++ )
		{
			for ( int j = 0 ; j < blockHeight ; j++ )
			{
				for ( int k = 0 ; k < blockWidth ; k++ )
				{
					output.writeInt(tree.get(i).averageBlock.get( j , k ));
				}
			}
		}
				
		for ( int i = 0 ; i < blocks.size() ; i++ )
		{
			boolean found = false;
			for ( int j = startIndex ; j < tree.size() ; j++ )
			{
				for ( int k = 0 ; k < tree.get(j).associationBlocks.size() ; k++)
				{
					if( blocks.get(i) == tree.get(j).associationBlocks.get(k) )
					{
						output.writeInt(j-startIndex);
						found = true;
						break;
					}
				}
				if (found) break;
			}
		}
	
		output.close();
	}
	
	void decompress ( String compressedImagePath, String savePath ) throws IOException
	{
		DataInputStream input = new DataInputStream(new FileInputStream(compressedImagePath));
		int imageHeight = 0, imageWidth = 0, blockHeight = 0, blockWidth = 0, levels = 0;
		ArrayList < Block > codebook = new ArrayList< Block >();
		
		imageHeight = input.readInt(); imageWidth = input.readInt();
		blockHeight = input.readInt(); blockWidth = input.readInt();
		levels = input.readInt();
				
		for ( int i = 0 ; i < levels ; i++ )
		{
			codebook.add( new Block( blockHeight , blockWidth ) );
			for ( int j = 0 ; j < blockHeight ; j++ )
			{
				for ( int k = 0 ; k < blockWidth ; k++ )
				{
					codebook.get(codebook.size()-1).set( j , k , input.readInt() );
				}					
			}
		}
		
		int labelsSize = (imageHeight*imageWidth)/(blockHeight*blockWidth);
		ArrayList <Integer> labels  = new ArrayList <Integer>();
		
		for ( int i = 0 ; i < labelsSize ; i++ )
		{
			labels.add(input.readInt());
		}
		
		int[][] pixels = new int[imageHeight][imageWidth];
		
		int codebookCounter = 0; 
		for ( int i = 0 ; i < imageHeight ; i += blockHeight )
		{
			for ( int j = 0 ; j < imageWidth ; j += blockWidth )
			{
				Block codebookBlock = codebook.get(labels.get(codebookCounter));
				for ( int k = i ; k < i+blockHeight ; k++ )
				{
					for ( int l = j ; l < j+blockWidth ; l++ )
					{
						pixels[k][l] = codebookBlock.get( k-i , l-j );
					}
				}
				codebookCounter++;
			}
		}
		
		Image image = new Image();
		
		image.writeImage( pixels,savePath+"/decompressedImage.jpg", imageWidth, imageHeight);
		
		input.close();
	}
	
	int getBits ( int levels )
	{
		int bits = 0;
		while ( levels != 1 )
		{
			bits++;
			levels /= 2;
		}
		return bits;
	}
	
	Block getAverage ( ArrayList < Block > blocks )
	{
		int blockHeight = blocks.get(0).getBlockHeight();
		int blockWidth = blocks.get(0).getBlockWidth();
		
		Block averageBlock = new Block ( blockHeight , blockWidth );
				
		for ( int i = 0 ; i < blockHeight ; i++ )
		{
			for ( int j = 0 ; j < blockWidth ; j++ )
			{
				double average = 0;
				for ( int k = 0 ; k < blocks.size() ; k++ )
				{
					average += blocks.get(k).get( i , j );
				}
				averageBlock.set( i , j , (int)(average/blocks.size()) );
			}
		}
		
		return averageBlock;
	}
	
	
	int getDistance ( Block block , Block associationAverageBlock )
	{
		int distance = 0;
		
		for ( int i = 0 ; i < block.getBlockHeight() ; i++ )
		{
			for ( int j = 0 ; j < block.getBlockWidth() ; j++ )
			{
				distance += (block.get(i,j)-associationAverageBlock.get(i,j))*(block.get(i,j)-associationAverageBlock.get(i,j));
			}
		}
		
		return distance;
	}
	
}
