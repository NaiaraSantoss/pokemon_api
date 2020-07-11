package br.edu.iftm.poo.trabalho2;

import br.edu.iftm.poo.trabalho2.interfaces.IApi;
import br.edu.iftm.poo.trabalho2.interfaces.IFactory;
import br.edu.iftm.poo.trabalho2.interfaces.IWindow;

public class Factory implements IFactory {

	private IApi APISingleton = new Api(this);
	private IWindow WindowSingleton = new Window(this);
	
	public Factory() { }

	@Override
	public IApi getAPI() {
		return this.APISingleton;
	}
	
	@Override
	public IWindow getWindow() { 
		return this.WindowSingleton;
	}
	
}
