package controller;

import java.util.ArrayList;
import java.util.List;

import exceptions.ProblemNotDefiniedCompletlyException;
import logic.MonteCarloLogic;
import model.Inequality;
import model.InequalityType;
import model.ObjectiveFunction;
import model.ObjectiveType;
import model.ResultVector;

public class Controller {

	private List<Inequality> inequalities;
	private ObjectiveFunction objectiveFunction;
	private double accuracy;
	private MonteCarloLogic mcl;

	public Controller() {
		inequalities = new ArrayList<>();
	}

	public ResultVector compute() throws ProblemNotDefiniedCompletlyException {
		if ((inequalities.isEmpty()) || (objectiveFunction == null)) {
			throw new ProblemNotDefiniedCompletlyException();
		}
		showLinearProgrammingProblem();
		mcl = new MonteCarloLogic(inequalities, objectiveFunction, accuracy);

		return mcl.start();
	}

	public Inequality createIneqality(int inequalityNumber, InequalityType inequalityType, List<Double> parameters,
			double intercept) {
		Inequality i = new Inequality(inequalityNumber, inequalityType, parameters, intercept);
		inequalities.add(i);
		return i;
	}

	public ObjectiveFunction createObjectiveFunction(ObjectiveType objectiveType, List<Double> parameters,
			List<Double> restrictionLeft, List<Double> restrictionRight) {
		objectiveFunction = new ObjectiveFunction(objectiveType, parameters, restrictionLeft, restrictionRight);
		return objectiveFunction;
	}

	private void showLinearProgrammingProblem() {// TODO view part
		System.out.println();
		System.out.println("--- Linear programing ---");
		System.out.println(objectiveFunction);
		inequalities.stream().forEach(i -> System.out.println(i));
	}

	public void createAccuracy(double accuracy) {
		this.accuracy = accuracy;

	}

}
