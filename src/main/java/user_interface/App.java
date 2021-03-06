package user_interface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.Controller;
import exceptions.ProblemNotDefiniedCompletlyException;
import model.Entity;
import model.Inequality;
import model.InequalityType;
import model.ObjectiveFunction;
import model.ObjectiveType;

public class App {

	private Scanner sc;
	private Controller ctrl;

	public App() {
		sc = new Scanner(System.in);
		ctrl = new Controller();
	}

	public static void main(String[] args) {
		App app = new App();
		app.start();
	}

	private void start() {

		int numberOfVariables = addObjectiveFunction();

		addInqualities(numberOfVariables);

		System.out.print("set epsilon: ");
		ctrl.createAccuracy(sc.nextDouble());

		try {
			Entity result = ctrl.compute();

			System.out.println("--- RESULT ---");
			for (int i = 0; i < result.getVector().size(); i++) {
				System.out.println("x" + i + " = " + String.format("%.8f", result.getVector().get(i)));
			}
			System.out.println("objective function value = " + String.format("%.8f", result.getValue()));
		} catch (ProblemNotDefiniedCompletlyException e) {
			e.printStackTrace();
		}

	}

	private int addObjectiveFunction() {
		System.out.println();
		System.out.println("--- Objective Function ---");
		int numberOfVariables = getNumberOfVariables();
		showFunctionStructure(numberOfVariables);
		System.out.println();

		ObjectiveType objectiveType = getObjectiveType();
		List<Double> parameters = getParametersList(numberOfVariables);
		List<Integer> powers = getPowersList(numberOfVariables);

		System.out.println("add restriction:");
		List<Double> restrictionLeft = new ArrayList<>();
		List<Double> restrictionRight = new ArrayList<>();
		for (int i = 1; i <= numberOfVariables; i++) {
			System.out.print("x" + i + " > ");
			restrictionLeft.add(sc.nextDouble());
			System.out.print("x" + i + " < ");
			restrictionRight.add(sc.nextDouble());
		}

		ObjectiveFunction of = ctrl.createObjectiveFunction(objectiveType, parameters, powers, restrictionLeft,
				restrictionRight);

		System.out.println(of);
		return numberOfVariables;
	}

	private ObjectiveType getObjectiveType() {
		ObjectiveType objectiveType;

		System.out.println("maximalize (max) or minimalize (min): ");
		String command = sc.next();
		
		if (command.contains("max")) {
			objectiveType = ObjectiveType.MAXIMALIZE;
		} else if (command.contains("min")) {
			objectiveType = ObjectiveType.MINIMALIZE;
		} else {
			System.out.println("Unrecognized objective type.");
			objectiveType = getObjectiveType();
		}
		return objectiveType;
	}

	private void addInqualities(int numberOfVariables) {
		System.out.print("Number of inequalities: ");
		Double numberOfInenqualities = sc.nextDouble();

		for (int inequalityNumber = 1; inequalityNumber <= numberOfInenqualities; inequalityNumber++) {
			showInequalityLabel(inequalityNumber);

			InequalityType inequalityType = getInequalityType();
			// int numberOfVariables = getNumberOfVariables();

			showInequalityStructure(numberOfVariables, inequalityType);

			List<Double> parameters = getParametersList(numberOfVariables);

			List<Integer> powers = getPowersList(numberOfVariables);

			double intercept = getInterecept();

			Inequality i = ctrl.createIneqality(inequalityNumber, inequalityType, parameters, powers, intercept);
			System.out.println(i);
		}
	}

	private double getInterecept() {
		System.out.print("b: ");
		return sc.nextDouble();
	}

	private List<Integer> getPowersList(int numberOfVariables) {
		List<Integer> list = new ArrayList<>();

		for (int j = 1; j <= numberOfVariables; j++) {
			System.out.print("n" + j + ": ");
			list.add(sc.nextInt());
		}

		return list;
	}

	private List<Double> getParametersList(int numberOfVariables) {
		List<Double> parametersList = new ArrayList<>();

		for (int j = 1; j <= numberOfVariables; j++) {
			System.out.print("a" + j + ": ");
			parametersList.add(sc.nextDouble());
		}

		return parametersList;
	}

	private int getNumberOfVariables() {
		System.out.print("Number of variables: ");
		int numberOfVariables;
		numberOfVariables = sc.nextInt();
		if (numberOfVariables <= 0) {
			System.out.println("Wrong number of variables.");
			numberOfVariables = getNumberOfVariables();
		}
		return numberOfVariables;

	}

	private void showInequalityStructure(int numberOfVariables, InequalityType inequalityType) {
		showFunctionStructure(numberOfVariables);
		System.out.print(" " + inequalityType + " b");
		System.out.println();

	}

	private void showFunctionStructure(int numberOfVariables) {
		System.out.print("a" + 1 + "*x" + 1 + "^n" + 1);
		for (int i = 2; i <= numberOfVariables; i++) {
			System.out.print(" + a" + i + "*x" + i + "^n" + i);
		}
	}

	private InequalityType getInequalityType() {
		System.out.print("Inequality type ( < = > ): ");
		char inequalityTypeChar = sc.next().charAt(0);
		InequalityType inequalityType;

		if (inequalityTypeChar == '<') {
			inequalityType = InequalityType.SMALLER;
		} else if (inequalityTypeChar == '=') {
			inequalityType = InequalityType.EQUAL;
		} else if (inequalityTypeChar == '>') {
			inequalityType = InequalityType.BIGGER;
		} else {
			System.out.println("Unrecognized inequality type.");
			inequalityType = getInequalityType();
		}
		return inequalityType;
	}

	private void showInequalityLabel(int i) {
		System.out.println();
		System.out.println("--- Inequality " + i + " ---");
	}
}
