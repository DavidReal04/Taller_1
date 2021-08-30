package co.edu.unbosque.controller;

import co.edu.unbosque.model.Model;
import co.edu.unbosque.view.View;
/**
 * Clase que relaciona las clases del paquete model con las del paqute view y controla las interacciones entre ellas 
 * @author David Real
 *
 */
public class Controller {
	/**
	 * Objeto de View que permite acceder a sus métodos y atrbutos
	 */
	private View view;
	/**
	 * Objeto de Model que permite acceder a sus métodos y atrbutos
	 */
	private Model model;
	/**
	 * Mëtodo constructor de la clase Controller
	 */
	public Controller() {
		model = new Model();
		view = new View();
		function();
	}
	/**
	 * Método que da orden a la ejecución de las partes del programa 
	 */
	public void function() {
		int option = 0;
		String aux;
		view.write("Ciudadanos de 4 Patas\n\nCargue el archivo con los registros");
		view.write(model.getManager().uploadData());
		do {
			view.menu();
			option=view.readNum();
			//Menú Switch-Case que da a elegir qué funcionaidad desea ejecutar
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
				view.write("Número de registros a mostrar (Por defecto se muestran lo primeros 15 registros)");
				int n = 0;
				n = view.readNum();
				view.write("Posición (TOP||LAST)");
				String position= view.readLine().toUpperCase();
				view.write("Especie (Canino||Felino)");
				String species= view.readLine().toUpperCase();
				view.write("Sexo (Hembra||Macho)");
				String sex= view.readLine().toUpperCase();
				view.write("Tamaño\n||Miniatura-Pequeño-Mediano-Grande||");
				String size= view.readLine().toUpperCase();
				view.write("Potencialmente peligroso (Si||No)");
				String potentDangerous = view.readLine().toUpperCase().replace("SI", "True").replace("NO", "False");
				view.write("Barrio\n||Usaquen, Chapinero, Santa Fe, San Cristobal, Usme, Tunjuelito, Bosa"
						+ ", Kennedy, Fontibon, Engativa, Suba, B. Unidos, Teusaquillo, Los Martires, A. Narino"
						+ ", P. Aranda, La Candelaria, R. Uribe, C. Bolivar, Sumapaz, Municipios Aledaños Bogota D.C.||");
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
