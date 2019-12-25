
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	static Scanner input = new Scanner(System.in);
	static int numOfSets, numOfPoints, polynomialDegree;
	static int popSize, chromSize, numOfGenerations;
	static double propOfCrossover, propOfMutation;
	static List<Point> points;
	static List<Chromosome> population;
	static List<Chromosome> nextGeneration;
			
	public static void main(String[] args) {
		popSize = 100;
		numOfGenerations = 100;
		propOfCrossover = 0.6;
		propOfMutation = 0.01;
		
		
		numOfSets = input.nextInt();
		for(int t = 0; t < numOfSets; t++) {
		
			numOfPoints = input.nextInt();
			polynomialDegree = input.nextInt();
			chromSize = polynomialDegree + 1;
			points = new ArrayList<Point>();
			
			for(int i = 0; i < numOfPoints; i++) {
				points.add(new Point(input.nextDouble(), input.nextDouble()));
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
					 performMutation(firstOffspring, i+1);
					 performMutation(secondOffspring, i+1);
					 
					 nextGeneration.add(firstOffspring);
					 nextGeneration.add(secondOffspring);
				 }
				 
				 for(int j = 0; j < nextGeneration.size(); j++) {
					 calculateFitness(nextGeneration.get(j));
				 }
				 

				 population = nextGeneration;
			}
			Chromosome bestSolution = selectFittest();
			
			System.out.println("Case " + (t+1));
			for(int i = 0; i < bestSolution.genes.size(); i++){
				System.out.print(bestSolution.genes.get(i) + " ");
			}
			System.out.println();
			System.out.println(bestSolution.fitness);

		}
	}
	
	
	static void generatePopulation() {
		population = new ArrayList<Chromosome>();
		double MIN = -10, MAX = 10;
		
		while(population.size() != popSize) {
			Chromosome chromosome = new Chromosome(chromSize);
			for(int i = 0; i < chromSize; i++) {
				Random random = new Random();
				double randomNumber = MIN + (MAX - MIN) * random.nextDouble();
				chromosome.genes.set(i, randomNumber);
			}
			calculateFitness(chromosome);
			population.add(chromosome);
		}		
	}
	
	static void calculateFitness(Chromosome chromosome) {
		double yCalc = 0;
		ArrayList<Double> yCalculation = new ArrayList<Double>();
		for(int i = 0; i < numOfPoints; i++) {
			yCalculation.add(0.0);
		}
		for (int i = 0; i < numOfPoints; i++) {
			for (int j = 0; j < polynomialDegree; j++) {
				yCalc = yCalc + (chromosome.genes.get(j) * Math.pow(points.get(i).x, j));
			}
			yCalculation.set(i, yCalc);
			yCalc = 0;
		}
		double value = 0;
		double MSE;
		for (int i = 0; i < numOfPoints; i++) {
			value = value + Math.pow((yCalculation.get(i) - points.get(i).y), 2);
		}
		MSE = value / numOfPoints;
		chromosome.fitness = MSE;
	}
	
	static Chromosome selectParent() {
		ArrayList<Double> rouletteWheel = new ArrayList<Double>();
		double cumulativeSum = 0.0;
		for(int i = 0; i < popSize; i++) {
			cumulativeSum += (1.0 / population.get(i).fitness);
			rouletteWheel.add(cumulativeSum);
		}

		Random random = new Random();

		double randomNumber = cumulativeSum * random.nextDouble();
		
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
			firstOffspring.genes = (ArrayList<Double>) firstParent.genes.clone();
			secondOffspring.genes = (ArrayList<Double>) secondParent.genes.clone();
		}
	}
	
	static void performMutation(Chromosome chromosome ,int currentGeneration) {
		Random object = new Random();
		double y;
		for (int i = 0; i < chromosome.genes.size(); i++) {
			double rand = object.nextDouble();
			if(rand <= propOfMutation){
				rand = object.nextDouble();
				if (rand <= .5) {
					y = chromosome.genes.get(i) + 10;
				} 
				else {
					y = 10 - chromosome.genes.get(i);
				}
				double delta, r, b;
				r = object.nextDouble();
				b = 0.5;
				delta = y * (1 - (Math.pow(r, Math.pow(1 - (currentGeneration / numOfGenerations), b))));
				if (rand <= 0.5) {
					chromosome.genes.set(i, chromosome.genes.get(i) - delta);
				} 
				else {
					chromosome.genes.set(i, chromosome.genes.get(i) + delta);
				}
			}
		}
	}
	
	static Chromosome selectFittest() {
		Chromosome chromosome = null;
		double min = Double.MAX_VALUE;
		int minIndex = 0;

		for (int i = 0; i < population.size(); i++) {
			chromosome = population.get(i);
			if (chromosome.fitness < min) {
				min = chromosome.fitness;
				minIndex = i;
			}
		}
		return population.get(minIndex);
	}
}
