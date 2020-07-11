package br.edu.iftm.poo.trabalho2.models;

import java.util.List;
import java.util.Map;

import br.edu.iftm.poo.trabalho2.interfaces.IApi;

/**
 * @see https://pokeapi.co/docs/v2#ability
 */
public class Ability {

	protected int id;
	protected String name;
	protected boolean isMainSeries;
	protected Map<String, String> names = null;
	protected Map<String, String[]> effectEntries = null;
	protected Map<String, String> flavorTextEntries = null;
	protected NamedResourceList<Pokemon> pokemons = null;
	
	protected List<Pokemon> _pokemons;
	
	public Ability() { }
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
	
	public boolean isMainSeries() {
		return isMainSeries;
	}
	public void setMainSeries(boolean isMainSeries) {
		this.isMainSeries = isMainSeries;
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
	
	public void setPokemons(NamedResourceList<Pokemon> pokemons) {
		this.pokemons = pokemons;
	}
	public List<Pokemon> getPokemons() { return this.getPokemons(null); }
	public List<Pokemon> getPokemons(IApi api) {
		if(api != null && this._pokemons == null) {
			if(this.pokemons == null)
				return null;
			else {
				this._pokemons = this.pokemons.getResources(api);
			}
		}
		return this._pokemons;
	}
	
}
