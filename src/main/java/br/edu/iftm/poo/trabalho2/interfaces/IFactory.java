package br.edu.iftm.poo.trabalho2.interfaces;

public interface IFactory {

	/**
	 * Acesso à API.
	 * @return Instância para acesso à API.
	 */
	public IApi getAPI();
	
	/**
	 * Acesso à janela.
	 * @return Instância da janela.
	 */
	public IWindow getWindow();
	
}
