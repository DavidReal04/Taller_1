package co.edu.unbosque.model;

import co.edu.unbosque.model.persistence.Manager;
/**
 * Clase que conecta la persistencia con el Controller
 * @author David Real
 */
public class Model {
	/**
	 * Objeto de la clase Manager que permite acceder a sus m�todos y atributos
	 */
	private Manager manager;
	/**
	 * M�todo constructor de la clase Model
	 */
	public Model(){
		manager = new Manager();
	}
	
	//Getters-Setters
	
	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
}
