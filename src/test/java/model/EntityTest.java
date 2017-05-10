package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class EntityTest {

	@Test
	public void checkMembershipTest() {
		List<Inequality> inequalities = createInequalities();
		Entity e = new Entity(Arrays.asList(2000., 4000.), createObjectiveFunction());

		System.out.println(e.checkMembership(inequalities));

	}

	private List<Inequality> createInequalities() {
		Inequality i1 = new Inequality(1, InequalityType.SMALLER, Arrays.asList(6., 6.), 36000);
		Inequality i2 = new Inequality(2, InequalityType.SMALLER, Arrays.asList(10., 5.), 50000);
		Inequality i3 = new Inequality(2, InequalityType.BIGGER, Arrays.asList(1., 0.), 0);
		Inequality i4 = new Inequality(2, InequalityType.BIGGER, Arrays.asList(0., 1.), 0);
		List<Inequality> inequalities = new ArrayList<>(Arrays.asList(new Inequality[] { i1, i2 }));
		return inequalities;
	}

	private ObjectiveFunction createObjectiveFunction() {
		List<Double> obParameters = Arrays.asList(new Double[] { 2., 1. });
		ObjectiveFunction objectiveFunction = new ObjectiveFunction(ObjectiveType.MAXIMALIZE, obParameters,
				Arrays.asList(0., 0.), Arrays.asList(10000., 10000.));
		return objectiveFunction;
	}

}