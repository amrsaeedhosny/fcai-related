import java.util.ArrayList;

public class Chromosome {
	public ArrayList<Double> genes;
	public double fitness;
	Chromosome(int chromSize){
		genes = new ArrayList<Double>();
		for(int i = 0; i < chromSize; i++){
			genes.add(0.0);
		}
		fitness = 0.0;
	}
}
