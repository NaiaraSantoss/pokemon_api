package br.edu.iftm.poo.trabalho2.models;

import java.util.Map;

import br.edu.iftm.poo.trabalho2.interfaces.IApi;
import br.edu.iftm.poo.trabalho2.interfaces.IApiResourceCall;

/**
 * @see https://pokeapi.co/docs/v2#named
 */
public class NamedResource<T> {
	
	private Class<T> type;
	
	private String name;
	private String url;
	
	public NamedResource(Class<T> type) {
		this.type = type;
	}
	
	/**
	 * Pega o ID a partir da URL.
	 * @return ID.
	 */
	public int getId() {
		String url = this.url.substring(0, this.url.length() - 1);
			   url = url.substring(url.lastIndexOf('/') + 1, url.length());
		return Integer.parseInt(url);
	}
	
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
	
	public String getUrl() { return this.url; }
	public void setUrl(String url) { this.url = url; }
	
	/**
	 * Pede o recurso para a API.
	 * @param api Referência da API.
	 * @return O recurso completo.
	 */
	@SuppressWarnings("unchecked")
	public T getResource(IApi api) {
		Map<Class<?>, IApiResourceCall<?>> typeTable = api.getTypeTable();
		
		if(!typeTable.containsKey(type))
			return null;
		else 
			return (T)typeTable.get(type).call(this.getId());
	}
	
}
