package br.edu.iftm.poo.trabalho2.models;

import java.util.Map;

/**
 * @see https://pokeapi.co/docs/v2#pokemon-species
 */
public class Species {

	protected int id;
	protected String name;
	protected int order;
	protected int genderRate;
	protected int captureRate;
	protected int baseHappiness;
	protected boolean isBaby;
	protected int hatchCounter;
	protected boolean hasGenderDifferences;
	protected String color;
	protected String shape;
	protected Map<String, String> names;
	protected Map<String, String> flavorTextEntries;
	
	public Species() { }
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}

	public int getGenderRate() {
		return genderRate;
	}
	public void setGenderRate(int genderRate) {
		this.genderRate = genderRate;
	}

	public int getCaptureRate() {
		return captureRate;
	}
	public void setCaptureRate(int captureRate) {
		this.captureRate = captureRate;
	}

	public int getBaseHappiness() {
		return baseHappiness;
	}
	public void setBaseHappiness(int baseHappiness) {
		this.baseHappiness = baseHappiness;
	}

	public boolean isBaby() {
		return isBaby;
	}
	public void setBaby(boolean isBaby) {
		this.isBaby = isBaby;
	}

	public int getHatchCounter() {
		return hatchCounter;
	}
	public void setHatchCounter(int hatchCounter) {
		this.hatchCounter = hatchCounter;
	}

	public boolean isHasGenderDifferences() {
		return hasGenderDifferences;
	}
	public void setHasGenderDifferences(boolean hasGenderDifferences) {
		this.hasGenderDifferences = hasGenderDifferences;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}

	public Map<String, String> getNames() {
		return names;
	}
	public void setNames(Map<String, String> names) {
		this.names = names;
	}

	public Map<String, String> getFlavorTextEntries() {
		return flavorTextEntries;
	}
	public void setFlavorTextEntries(Map<String, String> flavorTextEntries) {
		this.flavorTextEntries = flavorTextEntries;
	}
	
}