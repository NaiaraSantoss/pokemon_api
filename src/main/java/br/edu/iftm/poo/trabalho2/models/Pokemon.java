package br.edu.iftm.poo.trabalho2.models;

import java.util.List;

import br.edu.iftm.poo.trabalho2.interfaces.IApi;

/**
 * @see https://pokeapi.co/docs/v2#pokemon
 */
public class Pokemon {
	
	protected int id;
	protected String name;
	protected int baseExperience;
	protected int height;
	protected boolean isDefault;
	protected int order;
	protected int weight;
	
	protected NamedResourceList<Ability> abilities = null;
	protected NamedResourceList<Move> moves = null; 
    protected PokemonSprites sprites = null;
	protected NamedResource<Species> species = null;
	protected PokemonStat[] stats = null;
	protected NamedResourceList<Type> types = null;
    
    protected List<Ability> _abilities = null;
    protected List<Move> _moves = null;
    protected Species _species = null;
    protected List<Type> _types = null;
	
	public Pokemon() { }
	
	public Pokemon loadAll(IApi api) {
		this.getAbilities(api);
		this.getMoves(api);
		this.getSpecies(api);
		this.getTypes(api);
		
		if(this.stats != null)
			for(PokemonStat stat : this.stats)
				stat.getStat(api);
		
		return this;
	}
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public int getBaseExperience() { return this.baseExperience; }
	public void setBaseExperience(int baseExperience) { this.baseExperience = baseExperience; }

	public int getHeight() { return this.height; }
	public void setHeight(int height) { this.height = height; }

	public boolean isDefault() { return this.isDefault; }
	public void setDefault(boolean isDefault) { this.isDefault = isDefault; }

	public int getOrder() { return this.order; }
	public void setOrder(int order) { this.order = order; }

	public int getWeight() { return this.weight; }
	public void setWeight(int weight) { this.weight = weight; }

	public void setAbilities(NamedResourceList<Ability> abilities) { this.abilities = abilities; }
	public List<Ability> getAbilities() { return this.getAbilities(null); }
	public List<Ability> getAbilities(IApi api) {
		if(api != null && this._abilities == null) {
			if(this.abilities == null)
				return null;
			else {
				this._abilities = this.abilities.getResources(api);
			}
		}
		return this._abilities;
	}

	public void setMoves(NamedResourceList<Move> moves) { this.moves = moves; }
	public List<Move> getMoves() { return this.getMoves(null); }
	public List<Move> getMoves(IApi api) {
		if(api != null && this._moves == null) {
			if(this.moves == null)
				return null;
			else {
				this._moves = this.moves.getResources(api);
			}
		}
		return this._moves;
	}
	
	public void setSprites(PokemonSprites sprites) { this.sprites = sprites; }
	public PokemonSprites getSprites() { return this.sprites; }

	public void setSpecies(NamedResource<Species> species) { this.species = species; }
	public Species getSpecies() { return this.getSpecies(null); }
	public Species getSpecies(IApi api) {
		if(api != null && this._species == null) {
			if(this.species == null)
				return null;
			else {
				this._species = this.species.getResource(api);
			}
		}
		return this._species;
	}
	
	public PokemonStat[] getStats() { return this.stats; }
	public void setStats(PokemonStat[] stats) { this.stats = stats;	}

	public void setTypes(NamedResourceList<Type> types) { this.types = types; }
	public List<Type> getTypes() { return this.getTypes(null); }
	public List<Type> getTypes(IApi api) {
		if(api != null && this._types == null) {
			if(this.types == null)
				return null;
			else {
				this._types = this.types.getResources(api);
			}
		}
		return this._types;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}
