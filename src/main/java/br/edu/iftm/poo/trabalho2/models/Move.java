package br.edu.iftm.poo.trabalho2.models;

import java.util.Map;

import br.edu.iftm.poo.trabalho2.interfaces.IApi;

/**
 * @see https://pokeapi.co/docs/v2#move
 */
public class Move {

	protected int id;
	protected String name;
	protected int accuracy;
	protected int effectChance;
	protected int pp;
	protected int priority;
	protected int power;
	protected Map<String, String> names = null;
	protected Map<String, String[]> effectEntries = null; 
	protected Map<String, String> flavorTextEntries = null; 
	protected NamedResource<Type> type = null;
	
    protected Type _type = null;
	
    public Move() { }
    
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
	
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	
	public int getEffectChance() {
		return effectChance;
	}
	public void setEffectChance(int effectChance) {
		this.effectChance = effectChance;
	}
	
	public int getPp() {
		return pp;
	}
	public void setPp(int pp) {
		this.pp = pp;
	}
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	
	public Map<String, String> getNames() {
		return names;
	}
	public void setNames(Map<String, String> names) {
		this.names = names;
	}

	public Map<String, String[]> getEffectEntries() {
		return effectEntries;
	}
	public void setEffectEntries(Map<String, String[]> effectEntries) {
		this.effectEntries = effectEntries;
	}

	public Map<String, String> getFlavorTextEntries() {
		return flavorTextEntries;
	}
	public void setFlavorTextEntries(Map<String, String> flavorTextEntries) {
		this.flavorTextEntries = flavorTextEntries;
	}

	public void setType(NamedResource<Type> type) { this.type = type; }
	public Type getType() { return this.getType(null); }
	public Type getType(IApi api) {
		if(api != null && this._type == null) {
			if(this.type == null)
				return null;
			else {
				this._type = this.type.getResource(api);
			}
		}
		return this._type;
	}
	
}