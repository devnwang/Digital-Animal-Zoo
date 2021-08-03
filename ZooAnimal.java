package com.cognixia.jump.intermediatejava.assignments.digitalanimalzoo;

public class ZooAnimal {
	private String type;
	private int population;
	private String habitat;
	
	public ZooAnimal(String type, int population, String habitat) {
		this.type = type;
		this.population = population;
		this.habitat = habitat;
	}

	// Have all data members be read-only
	public String getType() {
		return type;
	}

	public int getPopulation() {
		return population;
	}

	public String getHabitat() {
		return habitat;
	}
	
	
}
