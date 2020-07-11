package br.edu.iftm.poo.trabalho2.interfaces;

public interface IFactory {

	/**
	 * Acesso � API.
	 * @return Inst�ncia para acesso � API.
	 */
	public IApi getAPI();
	
	/**
	 * Acesso � janela.
	 * @return Inst�ncia da janela.
	 */
	public IWindow getWindow();
	
}
