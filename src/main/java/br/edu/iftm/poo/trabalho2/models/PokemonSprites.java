package br.edu.iftm.poo.trabalho2.models;

/**
 * @see https://pokeapi.co/docs/v2#pokemonsprites
 */
public class PokemonSprites {
	
	protected String frontDefault;
	protected String frontShiny;
	protected String frontFemale;
	protected String frontShinyFemale;
	protected String backDefault;
	protected String backShiny;
	protected String backFemale;
	protected String backShinyFemale;
	
	public PokemonSprites() { }

	public String getFrontDefault() {
		return frontDefault;
	}
	public void setFrontDefault(String frontDefault) {
		this.frontDefault = frontDefault;
	}

	public String getFrontShiny() {
		return frontShiny;
	}
	public void setFrontShiny(String frontShiny) {
		this.frontShiny = frontShiny;
	}

	public String getFrontFemale() {
		return frontFemale;
	}
	public void setFrontFemale(String frontFemale) {
		this.frontFemale = frontFemale;
	}

	public String getFrontShinyFemale() {
		return frontShinyFemale;
	}
	public void setFrontShinyFemale(String frontShinyFemale) {
		this.frontShinyFemale = frontShinyFemale;
	}

	public String getBackDefault() {
		return backDefault;
	}
	public void setBackDefault(String backDefault) {
		this.backDefault = backDefault;
	}

	public String getBackShiny() {
		return backShiny;
	}
	public void setBackShiny(String backShiny) {
		this.backShiny = backShiny;
	}

	public String getBackFemale() {
		return backFemale;
	}
	public void setBackFemale(String backFemale) {
		this.backFemale = backFemale;
	}

	public String getBackShinyFemale() {
		return backShinyFemale;
	}
	public void setBackShinyFemale(String backShinyFemale) {
		this.backShinyFemale = backShinyFemale;
	}
	
}