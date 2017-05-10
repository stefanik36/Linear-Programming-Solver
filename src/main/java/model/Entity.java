package model;

import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;

public class Entity implements Comparable {

	private List<Double> vector;
	private double value;

	public Entity(List<Double> vector, ObjectiveFunction objectiveFunction) {
		this.vector = vector;
		this.value = computeEntityValue(objectiveFunction);
	}

	private double computeEntityValue(ObjectiveFunction objectiveFunction) {
		double value = 0;
		for (int i = 0; i < objectiveFunction.getNumberOfVariables(); i++) {
			value += objectiveFunction.getParameters().get(i) * vector.get(i);
		}
		return value;
	}

	public boolean checkMembership(List<Inequality> inequalities) {
		for (Inequality i : inequalities) {

			if (i.getNumberOfVariables() != vector.size()) {
				return false;// Exception
			}
			double sum = getSum(i);

			if (i.getInequalityType().equals(InequalityType.SMALLER)) {
				if (!(sum <= i.getIntercept())) {
					return false;
				}
			} else if (i.getInequalityType().equals(InequalityType.EQUAL)) {
				if (sum != i.getIntercept()) {// TODO epsilon
					return false;
				}
			} else if (i.getInequalityType().equals(InequalityType.BIGGER)) {
				if (!(sum >= i.getIntercept())) {
					return false;
				}
			} else {
				return false;// Exception
			}

		}
		return true;
	}

	private double getSum(Inequality i) {
		double sum = 0;
		for (int it = 0; it < vector.size(); it++) {
			// System.out.println("v: "+vector.get(it)+" i:
			// "+i.getParameters().get(it));
			sum += vector.get(it) * i.getParameters().get(it);
			// System.out.println("sum: "+sum);
		}
		// System.out.println("------------------------");
		return sum;
	}

	public List<Double> getVector() {
		return vector;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Entity [");
		sb.append("v: " + String.format("%.2f", value) + " | ");
		vector.stream().limit(vector.size() - 1).forEach(d -> sb.append(String.format("%.2f", d) + ", "));
		sb.append(String.format("%.2f", vector.get(vector.size() - 1)));
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Entity) {
			Entity e = (Entity) o;
			if (this.value > e.value) {
				return 1;
			} else if (this.value == e.value) {
				return 0;
			} else {
				return -1;
			}
		}
		return -1;// TODO Exception
	}

}
