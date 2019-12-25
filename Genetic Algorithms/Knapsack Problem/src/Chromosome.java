import java.util.ArrayList;

public class Chromosome {
	public ArrayList<Integer> genes;
	public int fitness;
	Chromosome(int chromSize){
		genes = new ArrayList<Integer>();
		for(int i = 0; i < chromSize; i++){
			genes.add(0);
		}
		fitness = 0;
	}
}
