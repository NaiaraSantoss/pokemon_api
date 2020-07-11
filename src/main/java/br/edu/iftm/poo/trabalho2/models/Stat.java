package br.edu.iftm.poo.trabalho2.models;

import java.util.Map;

/**
 * @see https://pokeapi.co/docs/v2#stats
 */
public class Stat {

	protected int id;
	protected String name;
	protected int gameIndex;
	protected boolean isBattleOnly;
	protected Map<String, String> names;

	public Stat() { }
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public int getGameIndex() {
		return gameIndex;
	}
	public void setGameIndex(int gameIndex) {
		this.gameIndex = gameIndex;
	}

	public boolean isBattleOnly() {
		return isBattleOnly;
	}
	public void setBattleOnly(boolean isBattleOnly) {
		this.isBattleOnly = isBattleOnly;
	}

	public Map<String, String> getNames() {
		return names;
	}
	public void setNames(Map<String, String> names) {
		this.names = names;
	}
	
}