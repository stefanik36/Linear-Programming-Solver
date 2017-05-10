package model;

import java.util.ArrayList;
import java.util.List;

public class Population {

	List<List<Double>> population;

	public Population() {
		this.population = new ArrayList<>();
	}

	public void addEntity(List<Double> entity) {
		population.add(entity);
	}

}
