import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Image 
{
	public int [][] readImage( String filePath )
	{
	    int width = 0;
		int height = 0;
	    File file = new File ( filePath );
	    BufferedImage image = null;
	    try
	    {
	        image = ImageIO.read( file );
	    }
	    catch ( IOException e )
	    {
	        e.printStackTrace();
	    }

	    width = image.getWidth();
	    height = image.getHeight();
	    
	    int [][] pixels = new int [height][width];

	    for( int x = 0 ; x < width ; x++ )
	    {
	        for( int y = 0 ; y < height ; y++ )
	        {
	            int rgb = image.getRGB( x , y );
	            int alpha = (rgb >> 24) & 0xff;
	            int r = (rgb >> 16) & 0xff;
	            int g = (rgb >> 8) & 0xff;
	            int b = (rgb >> 0) & 0xff;

	            pixels[y][x]=r;
	        }
	    }

	    return pixels;
	}
	
	
	public void writeImage( int[][] pixels , String outputFilePath , int width , int height )
    {
        File fileout = new File( outputFilePath );
        
        try 
        {
			fileout.createNewFile();
		} 
        catch ( IOException e1 ) 
        {
			e1.printStackTrace();
		}
        
        BufferedImage image2 = new BufferedImage( width , height , BufferedImage.TYPE_INT_RGB );

        for( int x = 0 ; x < width ; x++ )
        {
            for( int y = 0 ; y < height ; y++ )
            {
                image2.setRGB( x , y , (pixels[y][x]<<16)|(pixels[y][x]<<8)|(pixels[y][x]) );
            }
        }
        try
        {
            ImageIO.write( image2 , "jpg", fileout );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
	
	// edit 
	
	int [][] performPadding ( int [][] pixels , int imageHeight , int imageWidth , int blockHeight , int blockWidth )
	{
		if ( imageHeight % blockHeight != 0 )
		{
			imageHeight += blockHeight - ( imageHeight % blockHeight );
		}
		if ( imageWidth % blockWidth != 0 )
		{
			imageWidth +=  blockWidth - ( imageWidth % blockWidth );
		}
		
		int [][] updatedPixels = new int[imageHeight][imageWidth];
		
		for ( int i = 0 ; i < pixels.length ; i++ )
		{
			for ( int j = 0 ; j < pixels[i].length ; j++ )
			{
				updatedPixels[i][j] = pixels[i][j];
			}
		}
		
		return updatedPixels;
	}
	
	ArrayList <Block> divideIntoBlocks ( int[][] pixels , int imageHeight , int imageWidth , int blockHeight , int blockWidth )
	{
		ArrayList < Block > blocks = new ArrayList < Block > ();
		
		for ( int i = 0 ; i < imageHeight ; i += blockHeight )
		{
			for ( int j = 0 ; j < imageWidth ; j += blockWidth )
			{
				blocks.add( new Block( blockHeight , blockWidth ) );
				for ( int k = i ; k < i+blockHeight ; k++ )
				{
					for ( int l = j ; l < j+blockWidth ; l++ )
					{
						blocks.get(blocks.size()-1).set( k-i , l-j , pixels[k][l] );
					}
				}
			}
		}
		
		return blocks;
	}
}
