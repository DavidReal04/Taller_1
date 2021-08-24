package co.edu.unbosque.view;

import java.util.Scanner;

public class View {

	private Scanner read;
	
	public View() {
		read = new Scanner(System.in);
	}
	
	public int readNum() {
		int aux=0;
		try {
			aux = Integer.parseInt(read.nextLine());
		} catch(NumberFormatException e) {}
		return aux;
	}
	
	public String readLine() {
		return read.nextLine();
	}
	
	public void write(String message) {
		System.out.println(message);
	}
	
	public void loadCSV() {
		System.out.println("Ciudadanos de 4 Patas\n\nCargue el archivo con los registros");
	}
	public void menu() {
		System.out.println("\nCiudadanos de 4 Patas\n\n Escriba el n�mero seg�n la opci�n que desee"
				+"\n1. Asigar ID a todos los animales"
				+"\n2. Buscar por n�mero de microhip"
				+"\n3. Contar n�mero de animales por especie (Si desea puede especificar la especie)"
				+"\n4. Contar n�mero de animales por localidad (Si desea puede especificar la localidad)"
				+"\n5. B�squeda personalizada"
				+"\n6. Salir");
	}
}
