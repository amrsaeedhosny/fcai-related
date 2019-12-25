import java.util.Arrays;
import java.util.Comparator;

public class OptimalBST {
	private MatrixElement[][] chainMatrix;
	private BST optimalBST;
	private int optimalCost;
	
	BST getOptimalBST (MatrixElement[] matrixElements) {
		sortByKey(matrixElements);
		chainMatrix = new MatrixElement[matrixElements.length][matrixElements.length];
		for (int i = 0; i < matrixElements.length; i++) {
			for (int j = 0; j <matrixElements.length; j++) {
				if( i == j ) {
					chainMatrix[i][j] = matrixElements[i];
				}
				else {
					chainMatrix[i][j] = new MatrixElement();
				}
			}
		}
		
		for (int s = 2; s <= matrixElements.length; s++) {
			for (int i = 0; i <= matrixElements.length - s; i++) {
				chainMatrix[i][i + s - 1].cost = Integer.MAX_VALUE;
				for (int j = i; j <= i + s - 1; j++) {
					int sum = 0;
					for (int k = i; k <= i + s - 1; k++) {
						sum += matrixElements[k].cost;
					}

					int leftSubtree = j - 1 < i ? 0 : chainMatrix[i][j - 1].cost;
					int rightSubtree = j + 1 > i + s - 1 ? 0 : chainMatrix[j + 1][i + s - 1].cost;
					
					if (chainMatrix[i][i + s - 1].cost > sum + leftSubtree + rightSubtree ) {
						chainMatrix[i][i + s - 1].cost = sum + leftSubtree + rightSubtree;
						chainMatrix[i][i + s - 1].root = j;
					}
				}
			}
		}
		
		optimalCost = chainMatrix[0][matrixElements.length-1].cost;
		
		optimalBST = new BST();
		
		optimalBST.root = constructOptimalBST(optimalBST.root, 0, matrixElements.length-1, matrixElements);
				
		return optimalBST;
	}
	
	int getOptimalCost () {
		return optimalCost;
	}
	
	private void sortByKey (MatrixElement [] matrixElements ) {
		Comparator <MatrixElement> comparator = new Comparator<MatrixElement>() {
			public int compare( MatrixElement a, MatrixElement b ) {
				return Integer.compare(a.key,b.key);
			}
		};
		Arrays.sort(matrixElements, comparator);
	}
	
	private Node constructOptimalBST(Node root,int start, int end, MatrixElement[] matrixElements) {
		if ( start > end ) {
			return null;
		}
		
		root = new Node();

		if ( start == end ) {
			root.key = matrixElements[start].key;
		}
		else {
			root.key = chainMatrix[start][end].root;
		}
		root.frequency = matrixElements[root.key].cost;
		
		root.left = constructOptimalBST(root.left, start, root.key-1, matrixElements);
		root.right = constructOptimalBST(root.right, root.key+1, end, matrixElements);
		return root;
	}

}
