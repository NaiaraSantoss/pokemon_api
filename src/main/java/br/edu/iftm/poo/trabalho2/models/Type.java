package br.edu.iftm.poo.trabalho2.models;

import java.util.List;
import java.util.Map;

import br.edu.iftm.poo.trabalho2.interfaces.IApi;

/**
 * @see https://pokeapi.co/docs/v2#types
 */
public class Type {

	protected int id;
	protected String name;
	protected Map<String, String> names;
	protected NamedResourceList<Move> moves = null; 
	protected NamedResourceList<Pokemon> pokemons = null;

    protected List<Move> _moves = null;
	protected List<Pokemon> _pokemons;
	
	public Type() { }
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public Map<String, String> getNames() { return names; }
	public void setNames(Map<String, String> names) { this.names = names; }

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
	
	public void setPokemons(NamedResourceList<Pokemon> pokemons) { this.pokemons = pokemons; }
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