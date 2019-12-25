
public class DisjointSet {
	int numberOfVertices;
	int[] parents;
	int[] ranks;
	
	DisjointSet(int numberOfVertices) {
		this.numberOfVertices = numberOfVertices;
		parents = new int[numberOfVertices];
		ranks = new int[numberOfVertices];
		
		for (int i = 0; i < numberOfVertices; i++) {
			parents[i] = i;
			ranks[i] = 0;
		}
		
	}
	
	int find(int vertex) {
		
		// Perform Path Compression 
		
		if (parents[vertex] != vertex) {
			parents[vertex] = find(parents[vertex]);
		}
		
		return parents[vertex];
		
	}
	
	boolean union(int vertex1, int vertex2) {
		
		int parent1 = find(vertex1);
		int parent2 = find(vertex2);
				
		if (parent1 == parent2) {
			return false;
		}
		
		// Perform Union by Rank
		
		if ( ranks[parent1] < ranks[parent2] ) {
			parents[parent1] = parent2;
		}
		else if ( ranks[parent1] > ranks[parent2] ) {
			parents[parent2] = parent1;
		}
		else {
			parents[parent2] = parent1;
			ranks[parent1]++;
		}
		
		return true;
	}

}
