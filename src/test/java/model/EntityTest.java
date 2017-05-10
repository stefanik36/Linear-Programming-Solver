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
		assertEquals(true, e.checkMembership(inequalities));
	}

	private List<Inequality> createInequalities() {
		Inequality i1 = new Inequality(1, InequalityType.SMALLER, Arrays.asList(6., 6.), Arrays.asList(1, 1), 36000);
		Inequality i2 = new Inequality(2, InequalityType.SMALLER, Arrays.asList(10., 5.), Arrays.asList(1, 1), 50000);
		List<Inequality> inequalities = new ArrayList<>(Arrays.asList(new Inequality[] { i1, i2 }));
		return inequalities;
	}

	private ObjectiveFunction createObjectiveFunction() {
		List<Double> obParameters = Arrays.asList(new Double[] { 2., 1. });
		ObjectiveFunction objectiveFunction = new ObjectiveFunction(ObjectiveType.MAXIMALIZE, obParameters,
				Arrays.asList(1, 1), Arrays.asList(0., 0.), Arrays.asList(10000., 4000.));
		return objectiveFunction;
	}

}
