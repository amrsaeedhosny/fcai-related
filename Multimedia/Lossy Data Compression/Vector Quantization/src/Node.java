import java.util.ArrayList;

public class Node
{
	Block associationAverageBlock;
	ArrayList < Block > associationBlocks;
	Block averageBlock;
	int blockHeight;
	int blockWidth;
	Node left;
	Node right;
	
	Node ( int blockHeight , int blockWidth )
	{
		this.blockHeight = blockHeight;
		this.blockWidth = blockWidth;
		associationAverageBlock = new Block ( blockHeight , blockWidth );
		associationBlocks = new ArrayList < Block > ();
		averageBlock = new Block ( blockHeight , blockWidth );
		left = null;
		right = null;
	}
	
	Node ( int blockHeight , int blockWidth , Block associationAverageBlock )
	{
		this.blockHeight = blockHeight;
		this.blockWidth = blockWidth;
		this.associationAverageBlock = associationAverageBlock;
		associationBlocks = new ArrayList < Block > ();
		averageBlock = new Block ( blockHeight , blockWidth );
		left = null;
		right = null;
	}
	
	Block getAverageBlockPlusOne ()
	{
		Block temporaryAverageBlock = new Block( blockHeight , blockWidth );
		for ( int i = 0 ; i < blockHeight ; i++ )
		{
			for ( int j = 0 ; j < blockWidth ; j++ )
			{
				temporaryAverageBlock.set( i , j , averageBlock.get( i , j )+1 );
			}
		}
		
		return temporaryAverageBlock;
	}
	
	Block getAverageBlockMinusOne ()
	{
		Block temporaryAverageBlock = new Block( blockHeight , blockWidth );
		for ( int i = 0 ; i < blockHeight ; i++ )
		{
			for ( int j = 0 ; j < blockWidth ; j++ )
			{
				temporaryAverageBlock.set( i , j , averageBlock.get( i , j )-1 );
			}
		}
		
		return temporaryAverageBlock;
	}
	
}
