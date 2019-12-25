import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	
	
	public static void main( String [] args ) throws FileNotFoundException
	{
		Scanner in = new Scanner(new File("dataset/input4.txt"));
		OptimalBST optimalBST = new OptimalBST();
		BST bst = new BST();
		
		int n = Integer.valueOf(in.next());
		
		MatrixElement[] matrixElements = new MatrixElement[n];
		
		for (int i = 0; i < n; i++) {
			matrixElements[i] = new MatrixElement();
			matrixElements[i].key = Integer.valueOf(in.next());
			matrixElements[i].cost = Integer.valueOf(in.next());
		}
		
		bst = optimalBST.getOptimalBST(matrixElements);
		System.out.println("Optimal Cost: " + optimalBST.getOptimalCost());
		System.out.println("Optimal Binary Search Tree: ");
		bst.printBST(bst.root);
	
		in.close();
	    return;
	}
	
	

}