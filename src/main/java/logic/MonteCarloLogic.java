package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import model.Entity;
import model.Inequality;
import model.ObjectiveFunction;
import model.ResultVector;

public class MonteCarloLogic {

	private List<Inequality> inequalities;
	private ObjectiveFunction objectiveFunction;
	private ResultVector result;
	private double range;

	public MonteCarloLogic(List<Inequality> inequalities, ObjectiveFunction objectiveFunction) {
		this.inequalities = inequalities;
		this.objectiveFunction = objectiveFunction;
		this.range = objectiveFunction.getBiggestRange();
		System.out.println("range: " + range);
	}

	public ResultVector start() {
		// Entity initEntity = createInitialEntity();
		Entity entity = getNewEntity(objectiveFunction.getRestrictionLeft(), objectiveFunction.getRestrictionRight());
		System.out.println("valid: " + entity);
		// this.result = new ResultVector(randVector);

		return result;
	}

	private Entity getNewEntity(List<Double> leftRestriction, List<Double> rightRestriction) {
		Entity entity;
		do {
			List<Double> randVector = new ArrayList<>();
			for (int i = 0; i < objectiveFunction.getNumberOfVariables(); i++) {
				randVector.add(ThreadLocalRandom.current().nextDouble(leftRestriction.get(i), rightRestriction.get(i)));
			}
			entity = new Entity(randVector);
			System.out.println(entity);
		} while (!entity.checkMembership(inequalities));
		System.out.println("valid: " + entity);
		return entity;
	}

}
