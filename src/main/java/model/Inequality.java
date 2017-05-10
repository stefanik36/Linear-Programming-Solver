package model;

import java.util.List;

public class Inequality {

	private int inequalityNumber;
	private InequalityType inequalityType;
	private List<Double> parameters;
	private List<Integer> powers;

	private double intercept;

	public Inequality(int inequalityNumber, InequalityType inequalityType, List<Double> parameters,
			List<Integer> powers, double intercept) {
		this.inequalityNumber = inequalityNumber;
		this.inequalityType = inequalityType;
		this.parameters = parameters;
		this.powers = powers;
		this.intercept = intercept;
	}

	public InequalityType getInequalityType() {
		return inequalityType;
	}

	public void setInequalityType(InequalityType inequalityType) {
		this.inequalityType = inequalityType;
	}

	public List<Double> getParameters() {
		return parameters;
	}

	public void setParameters(List<Double> parameters) {
		this.parameters = parameters;
	}

	public double getIntercept() {
		return intercept;
	}

	public void setIntercept(double intercept) {
		this.intercept = intercept;
	}

	public List<Integer> getPowers() {
		return powers;
	}

	public void setPowers(List<Integer> powers) {
		this.powers = powers;
	}

	public int getNumberOfVariables() {
		return parameters.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Inequality id=" + String.format("%3d", inequalityNumber) + " [");
		sb.append(parameters.get(0) + "*x1^" + powers.get(0));
		for (int i = 1; i < parameters.size(); i++) {
			sb.append(" + ");
			sb.append(parameters.get(i) + "*x" + (i + 1) + "^" + powers.get(i));
		}
		sb.append(" " + inequalityType + " " + intercept + "]");
		return sb.toString();

	}

}
