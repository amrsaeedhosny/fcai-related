public class BST {
	Node root;
	
	void printBST(Node root) {
		if ( root == null ) return;
		if (root.left != null ) System.out.println(root.key + " " + root.left.key);
		if (root.right != null ) System.out.println(root.key + " " + root.right.key);
		printBST(root.left);
		printBST(root.right);		
	}

}
