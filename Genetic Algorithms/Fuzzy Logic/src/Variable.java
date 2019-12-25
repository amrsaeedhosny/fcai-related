import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Variable {
	String name;
	double value;
	ArrayList<FuzzySet> fuzzySets;
	Map<String, Double> membership;
	
	Variable(){
		fuzzySets = new ArrayList<FuzzySet>();
		membership = new HashMap<String, Double>();
	}
	
	boolean computeMembership() {
		for(int i = 0; i < fuzzySets.size(); i++) {
			int numOfPoints = fuzzySets.get(i).points.size();
			for(int j = 0; j < numOfPoints - 1; j++) {
				Point point1 = fuzzySets.get(i).points.get(j);
				Point point2 = fuzzySets.get(i).points.get(j+1);
				
				if(point1.x == point2.x && value == point1.x) {
					membership.put(fuzzySets.get(i).name, 1.0);
					break;
				}
				else if(value >= point1.x && value <= point2.x) {
					double y = 0.0, m = 0.0;
					m = (point2.y - point1.y) / (point2.x - point1.x);
					y = m * (value - point1.x) + point1.y;
					membership.put(fuzzySets.get(i).name, y);
					break;
				}
				else {
					membership.put(fuzzySets.get(i).name, 0.0);
				}
			}
		}
		return true;
	}
	
}
