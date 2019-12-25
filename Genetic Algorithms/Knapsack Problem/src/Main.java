import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	static Scanner input = new Scanner(System.in);
	static int numOfTestCases, numOfItems, knapsackSize;
	static int popSize, chromSize, numOfGenerations;
	static double propOfCrossover, propOfMutation;
	static List<Item> items;
	static List<Chromosome> population;
	static List<Chromosome> nextGeneration;
			
	public static void main(String[] args) {
		popSize = 100;
		numOfGenerations = 100;
		propOfCrossover = 0.6;
		propOfMutation = 0.01;
		
		numOfTestCases = input.nextInt();
		
		for(int t = 0; t < numOfTestCases; t++) {
		
			numOfItems = input.nextInt();
			chromSize = numOfItems;
			knapsackSize = input.nextInt();
			items = new ArrayList<Item>();
			
			for(int i = 0; i < numOfItems; i++) {
				items.add(new Item(input.nextInt(), input.nextInt()));
			}

			generatePopulation();
			
			for(int i = 0; i < numOfGenerations; i++) {
				 nextGeneration = new ArrayList<Chromosome>();
				 for(int j = 0; j < popSize; j += 2) {
					 Chromosome firstParent = selectParent();
					 Chromosome secondParent = selectParent();
					 Chromosome firstOffspring = new Chromosome(chromSize);
					 Chromosome secondOffspring = new Chromosome(chromSize);
					 
					 performCrossover(firstParent, secondParent, firstOffspring, secondOffspring);
					 performMutation(firstOffspring);
					 performMutation(secondOffspring);
					 
					 nextGeneration.add(firstOffspring);
					 nextGeneration.add(secondOffspring);
				 }
				 
				 for(int j = 0; j < nextGeneration.size(); j++) {
					 calculateFitness(nextGeneration.get(j));
				 }
				 
				 population = nextGeneration;
			}
			
			Chromosome bestSolution = selectFittest();
			
			int itemsInSolution = 0;
			int benefitInSolution = 0;
			for(int i = 0; i < bestSolution.genes.size(); i++) {
				if(bestSolution.genes.get(i) == 1) {
					itemsInSolution++;
					benefitInSolution += items.get(i).benefit;
				}
			}
			
			System.out.println("Case " + (t+1) + ": " + benefitInSolution);
			System.out.println(itemsInSolution);
			for(int i = 0; i < bestSolution.genes.size(); i++){
				if(bestSolution.genes.get(i) == 1) {
					System.out.println(items.get(i).weight + " " + items.get(i).benefit);
				}
			}
		}
	}
	
	
	static void generatePopulation() {
		population = new ArrayList<Chromosome>();
		
		while(population.size() != popSize) {
			Chromosome chromosome = new Chromosome(chromSize);
			for(int i = 0; i < chromSize; i++) {
				double randomNumber = Math.random();
				if(randomNumber < 0.5) {
					chromosome.genes.set(i, 1);
				}
			}
			
			if(validSize(chromosome) == true) {
				calculateFitness(chromosome);
				population.add(chromosome);
			}			
		}		
	}
	
	static void calculateFitness(Chromosome chromosome) {
		for(int i = 0; i < chromSize; i++) {
			chromosome.fitness += chromosome.genes.get(i) * items.get(i).benefit;
		}
	}
	
	static boolean validSize(Chromosome chromosome) {
		int weight = 0;
		
		for(int i = 0; i < chromSize; i++) {
			weight += chromosome.genes.get(i) * items.get(i).weight;
		}
		
		return weight <= knapsackSize;
	}
	
	static Chromosome selectParent() {
		ArrayList<Integer> rouletteWheel = new ArrayList<Integer>();
		int cumulativeSum = 0;
		for(int i = 0; i < popSize; i++) {
			cumulativeSum += population.get(i).fitness;
			rouletteWheel.add(cumulativeSum);
		}

		Random random = new Random();

		int randomNumber = random.nextInt(rouletteWheel.get(popSize - 1));
		
		int parentIndex = 0;
		for(int i = 0; i < popSize; i++) {
			if(randomNumber < rouletteWheel.get(i)) {
				parentIndex = i;
				break;
			}
		}
		
		return population.get(parentIndex);
	}
	
	static void performCrossover(Chromosome firstParent, Chromosome secondParent, Chromosome firstOffspring, Chromosome secondOffspring) {
		Random object = new Random();
		boolean child1 = false, child2 = false;
		while (!child1 || !child2) {
			double rand = object.nextDouble();
			if (rand < propOfCrossover) {
				int r = object.nextInt(chromSize - 1);
				for (int i = 0; i < r; i++) {
					firstOffspring.genes.set(i, firstParent.genes.get(i));
					secondOffspring.genes.set(i, secondParent.genes.get(i));
				}
				for (int i = r; i < chromSize; i++) {
					firstOffspring.genes.set(i, secondParent.genes.get(i));
					secondOffspring.genes.set(i, firstParent.genes.get(i));
				}
			} else {
				firstOffspring.genes = (ArrayList<Integer>) firstParent.genes.clone();
				secondOffspring.genes = (ArrayList<Integer>) secondParent.genes.clone();

			}
			child1 = validSize(firstOffspring);
			child2 = validSize(secondOffspring);
		}
	}
	
	static void performMutation(Chromosome chromosome) {
		ArrayList<Integer> gen = new ArrayList<Integer>();
		for(int i = 0; i < chromSize; i++) {
			gen.add(0);
		}
		
		for (int j = 0; j < chromSize; j++) {
			gen.set(j, chromosome.genes.get(j));
		}

		while (true) {
			for (int i = 0; i < chromSize; i++) {
				double random = Math.random();

				if (random < propOfMutation) {
					if (chromosome.genes.get(i) == 0) {
						chromosome.genes.set(i, 1);
					} 
					else {
						chromosome.genes.set(i, 0);
					}
				}
				else{
					continue;
				}
			}
			
			if (validSize(chromosome) == false) {
				for (int j = 0; j < chromSize; j++) {
					chromosome.genes.set(j, gen.get(j));
				}
			}
			else {
				break;
			}

		}
	}
	
	static Chromosome selectFittest() {
		Chromosome chromosome = null;
		int max = 0;
		int maxIndex = 0;

		for (int i = 0; i < population.size(); i++) {
			chromosome = population.get(i);
			if (chromosome.fitness > max) {
				max = chromosome.fitness;
				maxIndex = i;
			}
		}
		return population.get(maxIndex);
	}
}
