public class Block
{
	private int [][] block;
	private int blockHeight;
	private int blockWidth;
	
	Block ( int blockHeight, int blockWidth )
	{
		this.setBlockHeight(blockHeight);
		this.setBlockWidth(blockWidth);
		block = new int[blockHeight][blockWidth];
	}
	
	int get( int row , int column )
	{
		return block[row][column];
	}
	
	void set( int row , int column , int value )
	{
		block[row][column] = value;
	}

	int getBlockHeight() 
	{
		return blockHeight;
	}

	void setBlockHeight(int blockHeight) 
	{
		this.blockHeight = blockHeight;
	}

	int getBlockWidth() 
	{
		return blockWidth;
	}

	void setBlockWidth(int blockWidth) 
	{
		this.blockWidth = blockWidth;
	}
}
