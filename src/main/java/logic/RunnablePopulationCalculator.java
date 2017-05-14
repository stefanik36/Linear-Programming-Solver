package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import model.Entity;
import model.Inequality;
import model.ObjectiveFunction;
import model.Population;

public class RunnablePopulationCalculator extends Thread {
	private Entity entity;
	private ObjectiveFunction objectiveFunction;
	private List<Inequality> inequalities;
	private double accuracy;

	public RunnablePopulationCalculator(Entity entity, ObjectiveFunction objectiveFunction,
			List<Inequality> inequalities, double accuracy) {
		this.entity = entity;
		this.objectiveFunction = objectiveFunction;
		this.inequalities = inequalities;
		this.accuracy = accuracy;
	}

	@Override
	public void run() {
		System.out.println("runnung");

		Population population = new Population();
		double range = objectiveFunction.getBiggestRange();
		// double distance = range / 2;

		Population survivers;
		// Entity oldE = entity;
		Entity newE;
		while (range > accuracy) {

			population = getPopulation(range, entity);
			survivers = population.chooseBestEntities(objectiveFunction.getObjectiveType(),
					MonteCarloLogic.SAMPLES_NUMBER);

			List<RunnablePopulationCalculator> tList = createThreads(survivers);

			newE = getResult(tList);
			// distance = calculateDistance(oldE, newE);
			entity = newE;
			// System.out.println("old:" + oldE + " new: " + newE + " dist: " +
			// distance);

			// range = distance * 1.1;
			range = range / 10;
			System.out.println("s: " + survivers);
		}

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

		for (int i = 0; i < MonteCarloLogic.ENTITIES_NUMBER; i++) {
			Entity entity = getNewEntity(leftL, rightL);
			population.addEntity(entity);
		}
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

	public Entity getEntity() {
		return entity;
	}

}
