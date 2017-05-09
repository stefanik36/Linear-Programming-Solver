package model;

import java.util.List;

public class ObjectiveFunction {

	private ObjectiveType objectiveType;
	private List<Double> parameters;

	public ObjectiveFunction(List<Double> parameters) {
		this.parameters = parameters;
		objectiveType = ObjectiveType.MAXIMALIZE; // TODO
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		sb.append(parameters.get(0) + "*x1");
		for (int i = 2; i <= parameters.size(); i++) {
			sb.append(" + ");
			sb.append(parameters.get(0) + "*x" + i);
		}
		sb.append(" -> "+objectiveType+"]");
		return sb.toString();

	}
	
	

}
