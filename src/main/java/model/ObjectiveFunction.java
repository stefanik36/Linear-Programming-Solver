package model;

import java.util.List;

public class ObjectiveFunction {

	private ObjectiveType objectiveType;
	private List<Double> parameters;
	private List<Double> restrictionLeft;
	private List<Double> restrictionRight;

	public ObjectiveFunction(ObjectiveType objectiveType, List<Double> parameters, List<Double> restrictionLeft,
			List<Double> restrictionRight) {
		this.objectiveType = objectiveType;
		this.parameters = parameters;
		this.restrictionLeft = restrictionLeft;
		this.restrictionRight = restrictionRight;
	}

	public double getBiggestRange() {
		double range = 0;
		for (int i = 0; i < restrictionLeft.size(); i++) {
			double length = Math.abs(restrictionLeft.get(i)) + Math.abs(restrictionRight.get(i));
			range += length * length;
		}
		return Math.sqrt(range)/2;
	}

	public List<Double> getRestrictionLeft() {
		return restrictionLeft;
	}

	public void setRestrictionLeft(List<Double> restrictionLeft) {
		this.restrictionLeft = restrictionLeft;
	}

	public List<Double> getRestrictionRight() {
		return restrictionRight;
	}

	public void setRestrictionRight(List<Double> restrictionRight) {
		this.restrictionRight = restrictionRight;
	}

	public int getNumberOfVariables() {
		return parameters.size();
	}

	public List<Double> getParameters() {
		return parameters;
	}

	public void setParameters(List<Double> parameters) {
		this.parameters = parameters;
	}

	public ObjectiveType getObjectiveType() {
		return objectiveType;
	}

	public void setObjectiveType(ObjectiveType objectiveType) {
		this.objectiveType = objectiveType;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		sb.append(parameters.get(0) + "*x1");
		for (int i = 2; i <= parameters.size(); i++) {
			sb.append(" + ");
			sb.append(parameters.get(0) + "*x" + i);
		}
		sb.append(" -> " + objectiveType + "]");
		return sb.toString();
	}

}
