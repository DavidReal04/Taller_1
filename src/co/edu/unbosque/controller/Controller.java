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
				view.write("Escriba al menos un parámetro de búsqueda (Deje en blanco los que no use)");
				view.write("Número de registros a mostrar (Por defecto se muestran 15 registros)");
				int n = 0;
				n = view.readNum();
				view.write("Posición (Primeros||Últimos)");
				String position= view.readLine().toUpperCase();
				view.write("Especie");
				String species= view.readLine().toUpperCase();
				view.write("Sexo");
				String sex= view.readLine().toUpperCase();
				view.write("Tamaño");
				String size= view.readLine().toUpperCase();
				view.write("Potencialmente peligroso (Si||No)");
				String potentDangerous = view.readLine().toUpperCase().replace("SI", "True").replace("NO", "False");
				view.write("Barrio");
				String neighborhood= view.readLine().toUpperCase();
				if(potentDangerous.isEmpty()) {
					view.write(model.getManager().findByMultipleFields(n, position, species, sex, size, neighborhood));
				} else {
					view.write(model.getManager().findByMultipleFields(n, position, species, sex, size, Boolean.parseBoolean(potentDangerous), neighborhood));
				}
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
