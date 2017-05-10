package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Population {

	List<Entity> entityList;

	public Population() {
		this.entityList = new ArrayList<>();
	}

	public Population(List<Entity> entityList) {
		this.entityList = entityList;
	}

	public void addEntity(Entity entity) {
		entityList.add(entity);
	}

	public int numberOfEntities() {
		return entityList.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("population: \n");
		for (Entity e : entityList) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}

	public Population chooseBestEntities(ObjectiveType objectiveType, int numberOfBestEntities) {

		Collections.sort(entityList);
		if (objectiveType.equals(ObjectiveType.MAXIMALIZE)) {
			return new Population(entityList.subList(entityList.size() - numberOfBestEntities, entityList.size()));
		} else if (objectiveType.equals(ObjectiveType.MINIMALIZE)) {
			return new Population(entityList.subList(0, numberOfBestEntities));
		}
		return null;// TODO Exception
	}

	public List<Entity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}

}
