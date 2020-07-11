package br.edu.iftm.poo.trabalho2;

import br.edu.iftm.poo.trabalho2.interfaces.IFactory;

public class Main {

	public static IFactory Factory = new Factory();
	
	public static void main(String[] args) {
		Factory.getWindow().setVisible(true);
	}

}

