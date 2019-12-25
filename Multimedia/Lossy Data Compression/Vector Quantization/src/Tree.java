import java.util.ArrayList;

public class Tree 
{
	private ArrayList <Node> tree;
	private Node root;
	
	Tree ( Node root )
	{
		this.setRoot(root);
		tree = new ArrayList <Node>();
		tree.add(root);
	}
	
	void split ( int nodeIndex )
	{
		int blockHeight = tree.get(nodeIndex).blockHeight;
		int blockWidth = tree.get(nodeIndex).blockWidth;
		
		Node leftNode = new Node( blockHeight , blockWidth , tree.get(nodeIndex).getAverageBlockMinusOne() );
		Node rightNode = new Node( blockHeight , blockWidth , tree.get(nodeIndex).getAverageBlockPlusOne() );
		
		tree.get(nodeIndex).left = leftNode;
		tree.get(nodeIndex).right = rightNode;
		
		tree.add(leftNode);
		tree.add(rightNode);
	}
	
	int size ()
	{
		return tree.size();
	}
	
	Node get( int nodeIndex )
	{
		return tree.get(nodeIndex);
	}

	Node getRoot() 
	{
		return root;
	}
	void setRoot(Node root) 
	{
		this.root = root;
	}
}
