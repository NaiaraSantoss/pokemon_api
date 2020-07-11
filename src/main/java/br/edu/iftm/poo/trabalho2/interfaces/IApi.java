package br.edu.iftm.poo.trabalho2.interfaces;

import java.util.Map;

import br.edu.iftm.poo.trabalho2.models.*;

public interface IApi {
	
	/**
	 * Tabela contendo os métodos para instanciação dos recursos. 
	 * Classe -> Factory.
	 * @return A tabela de tipos.
	 */
	public Map<Class<?>, IApiResourceCall<?>> getTypeTable();
	
	/**
	 * Enumera todos os Pokemons da API.
	 * @return NamedResource<Pokemon>
	 */
	public NamedResourceList<Pokemon> enumeratePokemons();
	
	public Pokemon getPokemon(int id);

	public Ability getAbility(int id);
	
	public Move getMove(int id);
	
	public Species getSpecies(int id);
	
	public Stat getStat(int id);
	
	public Type getType(int id);
	
}
