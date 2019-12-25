import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;

public class KruskalsAlgorithm {
	Graph graph;
	DisjointSet disjointSet;
	
	KruskalsAlgorithm(Graph graph) {
		this.graph = graph;
		disjointSet = new DisjointSet(graph.numberOfVertices);
	}
	
	void MST () {
		
		Edge[] edgesOfMST = new Edge[graph.numberOfVertices-1];
		int indexOfMST = 0;
		BigInteger totalWeight = new BigInteger("0");
		
		sortByWeight();
		
		for (int i = 0; i < graph.numberOfEdges && indexOfMST < edgesOfMST.length ; i++) {
			if (disjointSet.union(graph.edges[i].source, graph.edges[i].destination)) {
				edgesOfMST[indexOfMST] = graph.edges[i];
				totalWeight = totalWeight.add(BigInteger.valueOf(edgesOfMST[indexOfMST].weight));
				indexOfMST++;
			}
		}
		
		for (int i = 0; i < edgesOfMST.length; i++) {
			System.out.println(edgesOfMST[i].source + " " + edgesOfMST[i].destination + " " + edgesOfMST[i].weight);
		}
		
		System.out.println("total weight: " + totalWeight.toString());
				
	}
	
	private void sortByWeight () {
		Arrays.sort(graph.edges, new Comparator <Edge> (){
			public int compare(Edge edge1, Edge edge2) {
				return Integer.compare(edge1.weight, edge2.weight);
			}
		});
	}
	
}
