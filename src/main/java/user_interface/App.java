package user_interface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.Controller;
import model.Inequality;
import model.InequalityType;
import model.ObjectiveFunction;

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
		addInqualities();

		addObjectiveFunction();

	}

	private void addObjectiveFunction() {
		System.out.println();
		System.out.println("--- Objective Function ---");
		int numberOfVariables = getNumberOfVariables();
		showFunctionStructure(numberOfVariables);
		System.out.println();
		List<Double> parameters = getParametersList(numberOfVariables);

		ObjectiveFunction of = ctrl.createObjectiveFunction(parameters);

		System.out.println(of);
	}

	private void addInqualities() {
		System.out.print("Number of inequalities: ");
		Double numberOfInenqualities = sc.nextDouble();

		for (int inequalityNumber = 1; inequalityNumber <= numberOfInenqualities; inequalityNumber++) {
			showInequalityLabel(inequalityNumber);

			InequalityType inequalityType = getInequalityType();
			int numberOfVariables = getNumberOfVariables();

			showInequalityStructure(numberOfVariables, inequalityType);

			List<Double> parameters = getParametersList(numberOfVariables);

			double intercept = getInterecept();

			Inequality i = ctrl.createIneqality(inequalityNumber, inequalityType, parameters, intercept);
			System.out.println(i);
		}
	}

	private double getInterecept() {
		System.out.print("b: ");
		return sc.nextDouble();
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
		System.out.print("a" + 1 + "*x" + 1);
		for (int i = 2; i <= numberOfVariables; i++) {
			System.out.print(" + a" + i + "*x" + i);
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
