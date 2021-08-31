package co.edu.unbosque.view;

import java.util.Scanner;
/**
 * Clase encargada mostrar y capturar datos ingresados por consolas
 * @author David Real
 *
 */
public class View {
	/**
	 * Objeto de Scanner que permite acceder a sus métodos
	 */
	private Scanner read;
	/**
	 * Método constructor de la clase View
	 */
	public View() {
		read = new Scanner(System.in);
	}
	/**
	 * Método que lee un número ingresado por consola
	 * @return int con el número leído
	 */
	public int readNum() {
		int aux=0;
		try {
			aux = Integer.parseInt(read.nextLine());
		} catch(NumberFormatException e) {}
		return aux;
	}
	/**
	 * Método que lee el texto ingresado por consola
	 * @return String con el texto leído
	 */
	public String readLine() {
		return read.nextLine();
	}
	/**
	 * Método que escribe por consola lo pasado por parámetro
	 * @param message
	 */
	public void write(String message) {
		System.out.println(message);
	}
	/**
	 * Método que imprime en consola el texto del menú del programa
	 */
	public void menu() {
		System.out.println("\nCiudadanos de 4 Patas\n\n Escriba el número según la opción que desee"
				+"\n1. Asigar ID a todos los animales"
				+"\n2. Buscar por número de microchip"
				+"\n3. Contar número de animales por especie (Si desea puede especificar la especie)"
				+"\n4. Contar número de animales por localidad (Si desea puede especificar la localidad)"
				+"\n5. Búsqueda personalizada"
				+"\n6. Salir");
	}
}
