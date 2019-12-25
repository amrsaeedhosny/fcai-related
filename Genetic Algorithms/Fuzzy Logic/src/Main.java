import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static Scanner input = new Scanner(System.in);
	static ArrayList<Variable> variables;
	static int numOfVar;
	static int numOfRules;

	public static void main(String[] args) {
		variables = new ArrayList<Variable>();
		numOfVar = input.nextInt();
		
		for(int i = 0; i < numOfVar + 1; i++) {
			Variable variable = new Variable();			
			variable.name = input.next();
			if(i < numOfVar) {
				variable.value = input.nextDouble();
			}
			int numOfFuzzySets = input.nextInt();
			
			for(int j = 0; j < numOfFuzzySets; j++) {
				FuzzySet fuzzySet = new FuzzySet();
				String shape;
				
				fuzzySet.name = input.next();
				shape = input.next();
				
				if(shape.equals("triangle")) {
					for(int k = 0; k < 3; k++) {
						fuzzySet.points.add(new Point(input.nextDouble(), k%2));
					}
				}
				else {
					for(int k = 0; k < 4; k++) {
						fuzzySet.points.add(new Point(input.nextDouble(), Math.min(k%3, 1)));
					}
				}
				
				variable.fuzzySets.add(fuzzySet);
			}
			
			if(i < numOfVar) {
				variable.computeMembership();
			}
			
			variables.add(variable);
		}
		
		numOfRules = input.nextInt();
		
		for(int i = 0; i < numOfRules; i++) {
			int parts = input.nextInt();
			String rule = input.nextLine();
			
			double ruleValue = getRuleValue(parts, rule);
			
		}	
	}
	
	
	static double getRuleValue(double parts, String rule) {
		
		return 0;
	}
	
}
