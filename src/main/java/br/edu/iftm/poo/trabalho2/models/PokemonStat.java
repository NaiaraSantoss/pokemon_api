package br.edu.iftm.poo.trabalho2.models;

import br.edu.iftm.poo.trabalho2.interfaces.IApi;

public class PokemonStat {

	protected int base;
	protected int effort;
	protected NamedResource<Stat> stat = null;

    protected Stat _stat = null;
	
    public PokemonStat() { }

    public int getBase() { return this.base; }
    public void setBase(int base) { this.base = base; }
    
    public int getEffort() { return this.effort; }
    public void setEffort(int effort) { this.effort = effort; }
    
	public void setStat(NamedResource<Stat> stat) { this.stat = stat; }
	public Stat getStat() { return this.getStat(null); }
	public Stat getStat(IApi api) {
		if(api != null && this._stat == null) {
			if(this.stat == null)
				return null;
			else {
				this._stat = this.stat.getResource(api);
			}
		}
		return this._stat;
	}   
	
}
