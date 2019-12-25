import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
	static Scanner input;
	public static void main (String[] args) {
		
		try {
			input = new Scanner(new File("dataset/input1.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("INVALID FILE NAME: file doesn't exist");
			return;
		}
		
		int numberOfVertices = Integer.valueOf(input.next());
		int numberOfEdges = Integer.valueOf(input.next());
		
		Graph graph = new Graph(numberOfVertices,numberOfEdges);
		
		for (int i = 0; i < numberOfEdges; i++) {
			graph.edges[i].source = Integer.valueOf(input.next());
			graph.edges[i].destination = Integer.valueOf(input.next());
			graph.edges[i].weight = Integer.valueOf(input.next());
		}
		
		KruskalsAlgorithm kruskalsAlgorithm = new KruskalsAlgorithm(graph);
		
		kruskalsAlgorithm.MST();
		
	}

}
