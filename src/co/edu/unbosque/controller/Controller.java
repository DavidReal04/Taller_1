package co.edu.unbosque.controller;

import co.edu.unbosque.model.Model;
import co.edu.unbosque.view.View;

public class Controller {

	private View view;
	private Model model;
	
	public Controller() {
		model = new Model();
		view = new View();
		funcionar();
	}
	
	public void funcionar() {
		int option = 0;
		String aux;
		view.loadCSV();
		view.write(model.getManager().uploadData());
		do {
			view.menu();
			option=view.readNum();
			switch(option) {
			case 1:
				view.write(model.getManager().assignID());
				break;
			case 2:
				view.write("Ingrese el número del microchip");
				try {
					view.write(model.getManager().findByMicrochip(Long.parseLong(view.readLine())));	
				}catch(NumberFormatException e) {
					view.write("Ingrese un número de microchip");
				}
				break;
			case 3:
				view.write("Si desea ingrese la especie (Canino, Felino), si desea ver ambas deje en blanco");
				aux=view.readLine();
				if(aux.equals("")) {
					view.write(model.getManager().countBySpecies());
				}else{
					view.write(model.getManager().countBySpecies(aux));
				}
				break;
			case 4: 
				view.write("Si desea ingrese la localidad\n||Usaquen, Chapinero, Santa Fe, San Cristobal, Usme, Tunjuelito, Bosa"
						+ ", Kennedy, Fontibon, Engativa, Suba, B. Unidos, Teusaquillo, Los Martires, A. Narino"
						+ ", P. Aranda, La Candelaria, R. Uribe, C. Bolivar, Sumapaz, Municipios Aledaños Bogota D.C.||");
				aux=view.readLine();
				if(aux.equals("")) {
					view.write(model.getManager().countByNeighborhood());
				} else {
					view.write(model.getManager().countByNeighborhood(aux));
				}
				break;
			case 5: 
				view.write(model.getManager().findByMultipleFields(0, "position", "species", "sex", "size", false, "neighborhood").toString());
				break;
			case 6:
				view.write("Hasta Pronto");
				break;
			default:
				view.write("Ingrese un número válido");
				break;
			}
		} while(option!=6);
		
	}
}
