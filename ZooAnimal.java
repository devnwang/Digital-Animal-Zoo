package com.cognixia.jump.intermediatejava.assignments.digitalanimalzoo;

import java.io.Serializable;

public class ZooAnimal implements Serializable, Comparable<ZooAnimal> {

	private static final long serialVersionUID = -8965747996737780716L;
	
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

	@Override
	public String toString() {
		return String.format("\nAnimal Type: %s \nPopulation: %d \nHabitat: %s", type, population, habitat);
	}

	@Override
	public int compareTo(ZooAnimal za) {

		return type.compareTo(za.type);
	}
	
	
	
	
}
