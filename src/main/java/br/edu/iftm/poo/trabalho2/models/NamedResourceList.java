package br.edu.iftm.poo.trabalho2.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.edu.iftm.poo.trabalho2.interfaces.IApi;
import br.edu.iftm.poo.trabalho2.interfaces.IApiResourceCall;

/**
 * @see https://pokeapi.co/docs/v2#named
 */
public class NamedResourceList<T> {
	
	private Class<T> type;
	
	private List<NamedResourceItem> items = new ArrayList<NamedResourceItem>();
	
	public NamedResourceList(Class<T> type) {
		this.type = type;
	}
	
	public void add(String name, String url) {
		NamedResourceItem item = new NamedResourceItem();
		item.name = name;
		item.url = url;
		
		this.items.add(item);
	}
	
	public void add(NamedResource<T> resource) {
		this.add(resource.getName(), resource.getUrl());
	}
	
	public void clear() {
		this.items.clear();
	}
	
	public NamedResource<T> get(int index) {
		NamedResource<T> result = new NamedResource<T>(this.type);
		NamedResourceItem item = this.items.get(index);
		
		result.setName(item.name);
		result.setUrl(item.url);
		
		return result;
	}
	
	public void remove(int index) {
		this.items.remove(index);
	}
	
	public int size() {
		return this.items.size();
	}
	
	/**
	 * Pede os recursos para a API.
	 * @param api Referência da API.
	 * @return Os recursos completos.
	 */
	public List<T> getResources(IApi api) {
		Map<Class<?>, IApiResourceCall<?>> typeTable = api.getTypeTable();
		
		if(!typeTable.containsKey(type))
			return null;
		else {
			List<T> items = new ArrayList<T>(this.size());			
			for(int i = 0; i < this.size(); i++)
				items.add(this.get(i).getResource(api));
			return items;
		}
	}
	
	private class NamedResourceItem {
		public String name;
		public String url;
	}
	
}
