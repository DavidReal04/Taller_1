package co.edu.unbosque.controller;

import co.edu.unbosque.model.Model;

public class Controller {

	
	private Model model;
	
	public Controller() {
		model = new Model();
		funcionar();
	}
	
	public void funcionar() {
		System.out.println(model.getManager().uploadData());
		System.out.println(model.getManager().assignID());
	}
}
