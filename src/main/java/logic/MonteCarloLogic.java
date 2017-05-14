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
	public static int SAMPLES_NUMBER = 100;// TODO
	public static int ENTITIES_NUMBER = 20000;

	public MonteCarloLogic(List<Inequality> inequalities, ObjectiveFunction objectiveFunction, double accuracy) {
		this.inequalities = inequalities;
		this.objectiveFunction = objectiveFunction;
		this.accuracy = accuracy;
	}

	public Entity start() {

		Population initPopulation = getInitialPopulation();

		Population survivers = initPopulation.chooseBestEntities(objectiveFunction.getObjectiveType(), SAMPLES_NUMBER);
		System.out.println("survivors: " + survivers);

		List<RunnablePopulationCalculator> tList = createThreads(survivers);

		Entity result = getResult(tList);

		// double range = objectiveFunction.getBiggestRange();
		// Population population;
		// Entity oldE = survivers.getEntityList().get(0);
		// Entity newE;
		// double distance = range / 2;
		// while (range > accuracy) {
		//
		// population = getPopulation(range, oldE);
		// survivers =
		// population.chooseBestEntities(objectiveFunction.getObjectiveType(),
		// SAMPLES_NUMBER);
		// newE = survivers.getEntityList().get(0);
		// distance = calculateDistance(oldE, newE);
		// oldE = newE;
		// System.out.println("old:" + oldE + " new: " + newE + " dist: " +
		// distance);
		//
		// range = distance * 1.1;
		// // range = range / 10;
		// System.out.println("s: " + survivers);
		// }

		return result;
	}

	private Entity getResult(List<RunnablePopulationCalculator> tList) {
		Population eGroup = new Population();
		for (RunnablePopulationCalculator rpc : tList) {
			try {
				rpc.join();
				eGroup.addEntity(rpc.getEntity());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("threads Group: "+eGroup);
		return eGroup.chooseBestEntities(objectiveFunction.getObjectiveType(), 1).getEntityList().get(0);
	}

	private List<RunnablePopulationCalculator> createThreads(Population survivers) {
		List<RunnablePopulationCalculator> tList = new ArrayList<>();
		for (int i = 0; i < survivers.getEntityList().size(); i++) {

			RunnablePopulationCalculator rpc = new RunnablePopulationCalculator(survivers.getEntityList().get(i),
					objectiveFunction, inequalities, accuracy);

			rpc.start();
			tList.add(rpc);

		}
		return tList;
	}

	private double calculateDistance(Entity oldE, Entity newE) {
		double distance = 0;
		for (int i = 0; i < oldE.getVector().size(); i++) {
			double length = Math.abs(oldE.getVector().get(i)) - Math.abs(newE.getVector().get(i));
			distance += length * length;
		}
		return Math.sqrt(distance);
	}

	private Population getPopulation(double range, Entity e) {

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
