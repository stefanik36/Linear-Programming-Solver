package model;

import java.util.List;

public class Entity {

	private List<Double> vector;

	public Entity(List<Double> vector) {
		this.vector = vector;
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
			System.out.println("v: "+vector.get(it)+" i: "+i.getParameters().get(it));
			sum += vector.get(it) * i.getParameters().get(it);
			System.out.println("sum: "+sum);
		}
		System.out.println("------------------------");
		return sum;
	}

	public List<Double> getVector() {
		return vector;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Entity [");
		vector.stream().limit(vector.size() - 1).forEach(d -> sb.append(d + ", "));
		sb.append(vector.get(vector.size() - 1));
		sb.append("]");
		return sb.toString();
	}

}
