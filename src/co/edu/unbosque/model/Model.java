package co.edu.unbosque.model;

import co.edu.unbosque.model.persistence.Manager;

public class Model {
	
	private Manager manager;
	
	public Model(){
		manager = new Manager();
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
}
