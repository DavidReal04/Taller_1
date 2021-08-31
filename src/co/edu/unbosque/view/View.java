package co.edu.unbosque.view;

import java.util.Scanner;
/**
 * Clase encargada mostrar y capturar datos ingresados por consolas
 * @author David Real
 *
 */
public class View {
	/**
	 * Objeto de Scanner que permite acceder a sus m�todos
	 */
	private Scanner read;
	/**
	 * M�todo constructor de la clase View
	 */
	public View() {
		read = new Scanner(System.in);
	}
	/**
	 * M�todo que lee un n�mero ingresado por consola
	 * @return int con el n�mero le�do
	 */
	public int readNum() {
		int aux=0;
		try {
			aux = Integer.parseInt(read.nextLine());
		} catch(NumberFormatException e) {}
		return aux;
	}
	/**
	 * M�todo que lee el texto ingresado por consola
	 * @return String con el texto le�do
	 */
	public String readLine() {
		return read.nextLine();
	}
	/**
	 * M�todo que escribe por consola lo pasado por par�metro
	 * @param message
	 */
	public void write(String message) {
		System.out.println(message);
	}
	/**
	 * M�todo que imprime en consola el texto del men� del programa
	 */
	public void menu() {
		System.out.println("\nCiudadanos de 4 Patas\n\n Escriba el n�mero seg�n la opci�n que desee"
				+"\n1. Asigar ID a todos los animales"
				+"\n2. Buscar por n�mero de microchip"
				+"\n3. Contar n�mero de animales por especie (Si desea puede especificar la especie)"
				+"\n4. Contar n�mero de animales por localidad (Si desea puede especificar la localidad)"
				+"\n5. B�squeda personalizada"
				+"\n6. Salir");
	}
}
