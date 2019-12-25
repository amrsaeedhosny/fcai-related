
public class Graph {
	int numberOfVertices;
	int numberOfEdges;
	Edge[] edges;
	
	Graph(int numberOfVertices, int numberOfEdges) {
		
		this.numberOfVertices = numberOfVertices;
		this.numberOfEdges = numberOfEdges;
		edges = new Edge[numberOfEdges];
		for (int i = 0; i < numberOfEdges; i++ ) {
			edges[i] = new Edge();
		}
		
	}

}
