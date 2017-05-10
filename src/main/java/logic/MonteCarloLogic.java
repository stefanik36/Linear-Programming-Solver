package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import model.Entity;
import model.Inequality;
import model.ObjectiveFunction;
import model.Population;
import model.ResultVector;

public class MonteCarloLogic {

	private List<Inequality> inequalities;
	private ObjectiveFunction objectiveFunction;
	private double accuracy;
	private ResultVector result;
	private int SAMPLES_NUMBER = 1;// TODO
	private int ENTITIES_NUMBER = 20000;

	public MonteCarloLogic(List<Inequality> inequalities, ObjectiveFunction objectiveFunction, double accuracy) {
		this.inequalities = inequalities;
		this.objectiveFunction = objectiveFunction;
		this.accuracy = accuracy;
	}

	public ResultVector start() {
		double range = objectiveFunction.getBiggestRange() / 2;
		Population initPopulation = getInitialPopulation();

		Population survivers = initPopulation.chooseBestEntities(objectiveFunction.getObjectiveType(), SAMPLES_NUMBER);
		System.out.println("survivors: " + survivers);

		Population population;
		while (range > accuracy) {
			population = getPopulation(range, survivers);
			System.out.println("nop: "+population.numberOfEntities());
			survivers = population.chooseBestEntities(objectiveFunction.getObjectiveType(), SAMPLES_NUMBER);
			System.out.println("s: " + survivers);
			range = range / 100;
		}

		// for (int i = 0; i < survivers.numberOfEntities(); i++) {//TODO
		// Entity e = survivers.getEntityList().get(i);
		//
		// }

		return result;
	}

	private Population getPopulation(double range, Population survivers) {
		Entity e = survivers.getEntityList().get(0);

		Population population = new Population();

		List<Double> leftL = new ArrayList<>();
		List<Double> rightL = new ArrayList<>();
		for (int j = 0; j < e.getVector().size(); j++) {
			double v = e.getVector().get(j);
			double rl = objectiveFunction.getRestrictionLeft().get(j);
			double rr = objectiveFunction.getRestrictionRight().get(j);
			double left = rl > (v - range) ? rl : (v - range);
			double right = rr < (v + range) ? rr : (v + range);
			System.out.println("l: " + left + " r: " + right + " range: " + range);
			leftL.add(left);
			rightL.add(right);
		}

		for (int i = 0; i < ENTITIES_NUMBER; i++) {
			Entity entity = getNewEntity(leftL, rightL);
			population.addEntity(entity);
		}
		return population;
	}

	private Population getInitialPopulation() {
		Population population = new Population();
		for (int i = 0; i < ENTITIES_NUMBER; i++) {
			Entity entity = getNewEntity(objectiveFunction.getRestrictionLeft(),
					objectiveFunction.getRestrictionRight());
			population.addEntity(entity);
		}
		// System.out.println(population);
		return population;
	}

	private Entity getNewEntity(List<Double> leftRestriction, List<Double> rightRestriction) {
		Entity entity;
		do {
			List<Double> randVector = new ArrayList<>();
			for (int i = 0; i < objectiveFunction.getNumberOfVariables(); i++) {
				randVector.add(ThreadLocalRandom.current().nextDouble(leftRestriction.get(i), rightRestriction.get(i)));
			}
			entity = new Entity(randVector, objectiveFunction);
			// System.out.println(entity);
		} while (!entity.checkMembership(inequalities));
		return entity;
	}

}
