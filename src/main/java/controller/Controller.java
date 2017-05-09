package controller;

import java.util.ArrayList;
import java.util.List;

import model.Inequality;
import model.InequalityType;
import model.ObjectiveFunction;

public class Controller {

	private List<Inequality> inequalities;
	private ObjectiveFunction objectiveFunction;

	public Controller() {
		inequalities = new ArrayList<>();
	}

	public Inequality createIneqality(int inequalityNumber, InequalityType inequalityType, List<Double> parameters,
			double intercept) {
		Inequality i = new Inequality(inequalityNumber, inequalityType, parameters, intercept);
		inequalities.add(i);
		return i;
	}

	public ObjectiveFunction createObjectiveFunction(List<Double> parameters) {
		objectiveFunction = new ObjectiveFunction(parameters);
		return objectiveFunction;
	}

}
