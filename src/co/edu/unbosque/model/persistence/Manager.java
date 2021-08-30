package co.edu.unbosque.model.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

/**
 * Clase DAO del proyecto
 * @author David Real
 */
public class Manager {
	/**
	 * Objeto de Pet que permite acceder a sus atributos y métodos
	 */
	private Pet pet;
	/**
	 * Objeto de tipo ArrayList que almacena objetos Pet
	 */
	private ArrayList<Pet> petList = new ArrayList<>();
	/**
	 * Objeto de JFileChooser que permite seleccionar el archivo
	 */
	private JFileChooser file = new JFileChooser();
	
	/**
	 * Método constructor de la clase Manager
	 */
	public Manager() {
		pet = new Pet(null, 0, null, null, null, false, null);
	}
	/**
	 * Método que realiza la carga del archivo, crea objetos de Pet y los añade a la lista
	 * 
	 * @return String que muestra los registros exitosos y fallidos, e indica la finalización de la carga
	 */
	public String uploadData() {
		String linea="";
		String cadena="";
		file.showOpenDialog(file);
		File f = file.getSelectedFile();
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			linea=br.readLine();
			int exitosos=0;
			int fallidos=0;
			while (linea!=null) {
				try {
				String[] partes = linea.split(";");
				pet.setMicrochip(Long.parseLong(partes[0]));
				if(partes[1].equals("NO IDENTIFICADO")) {
					throw new UnknownSpeciesException(); 
				}
				pet.setSpecies(partes[1]);
				pet.setSex(partes[2]);
				pet.setSize(partes[3].replace("Muy Grande", "Grande"));
				pet.setPotentDangerous(Boolean.parseBoolean(partes[4].replace("SI", "True")));
				pet.setNeighborhood(partes[5]);
				petList.add(pet = new Pet("NO ASIGNADO", pet.getMicrochip(), pet.getSpecies(), pet.getSex(), pet.getSize(), pet.isPotentDangerous(), pet.getNeighborhood()));
				exitosos++;
				} catch (NumberFormatException e) {
					fallidos++;
				} catch (ArrayIndexOutOfBoundsException e) {
					fallidos++;
				} catch (UnknownSpeciesException e) {
					fallidos++;
				}
				linea=br.readLine();
			}
			cadena = "Registros Exitosos: "+ exitosos +" || Registros Fallidos: " + fallidos;
			fr.close();
		} catch (IOException e) {
			return null;
		}
		return cadena + "\nEl proceso de carga del archivo ha finalizado";
	}
	/**
	 * Método que asigna los ID de acuerdo con los criterios requeridos
	 * 
	 * @return String que dice cuántas veces hubo IDs repetidos, e indica que se terminó la asignación
	 */
	public String assignID() {
		String id="";
		int exceptions=0;
		//Bucle que recorre todo el Arraylist de Pet uno a uno 
		for(int i=0;i<petList.size();i++) {
			int digits=2;
			//Guarda cada campo del objeto Pet y les asigna el valor correspondiente
			String microchip = String.valueOf(petList.get(i).getMicrochip());
			microchip = microchip.substring(microchip.length()-digits);
			String species = petList.get(i).getSpecies();
			species = species.substring(0, 1);
			String sex = petList.get(i).getSex();
			sex = sex.substring(0,1);
			String size = petList.get(i).getSize();
			if(size.equals("MINIATURA")) {
				size = size.substring(0, 2);
			}else {
				size = size.substring(0, 1);
			}
			String potentDangerous = String.valueOf(petList.get(i).isPotentDangerous()).substring(0, 1).toUpperCase();
			//Arma el ID usando los valores que guardó antes
			id = microchip+"-"+species+sex+size+potentDangerous;
			boolean flag = false;
			//Bucle del que se sale sólamente cuando se haya asignado el Id
			while(!flag) {
				try {
					//Bucle para verificar que el ID no existe y asignarlo
					for(int j=i;j>0;j--) {
						if(id.equals(petList.get(j).getId())) {
							throw new IdentifierExistsException();
						}
					}
					flag=true;
					petList.get(i).setId(id);
					if(digits>2) {
						exceptions++;
					}
				}catch(IdentifierExistsException e) {
					digits++;
					microchip = String.valueOf(petList.get(i).getMicrochip());
					microchip = microchip.substring(microchip.length()-digits);
					id = microchip+"-"+species+sex+size+potentDangerous;
				}
			}
		}
		return "Se ha repetido el ID en "+exceptions+" ocasiones" + "\nEl proceso de asignación de ids ha finalizado";
	}
	/**
	 * Método que recibe por parámetro un long que corresponde al microchip que se quiere encontrar 
	 * @param microchip
	 * @return String con el objeto Pet correspondiente al microchip
	 */
	public String findByMicrochip(long microchip) {
		boolean flag = false;
		try {
			//Bucle que compara cada atributo Microchip de los objetos Pet en la lista con el long pasado por parámetro
			for (int i = 0; i < petList.size(); i++) {
				if(petList.get(i).getMicrochip()==microchip) {
					pet=petList.get(i);
					i=petList.size();
					flag=true;
				}
			}
			if(!flag) {
				throw new InvalidMicrochipException();
			}
		} catch (InvalidMicrochipException e) {
			return "El Microchip ingresado no existe";
		}
		return pet.toString();
	}
	/**
	 * Método que cuenta la cantidad de animales por especie
	 * @return String con la cantidad de Caninos y Felinos respectivamente
	 */
	public String countBySpecies() {
		int canino=0;
		int felino=0;
		//Bucle con dos condicionales que recorre toda la lista petList 
		for (int i = 0; i < petList.size(); i++) {
			if(petList.get(i).getSpecies().equals("CANINO")) {
				canino++;
			} else if(petList.get(i).getSpecies().equals("FELINO")) {
				felino++;
			}
		}
		return "Canino: "+canino+"\nFelino: "+felino;
	}
	/**
	 * Método que cuenta cantidad de animales de la especie pasada por parámetro
	 * @param species
	 * @return String con la cantidad animales de la especie pasada por parámetro
	 */
	public String countBySpecies(String species) {
		int contador=0;
		//Bucle con un condicional que recorre toda la lista petList
		for (int i = 0; i < petList.size(); i++) {
			if(petList.get(i).getSpecies().equals(species.toUpperCase())) {
				contador++;
			}
		}
		if(contador==0) {
			return "Ingrese una especie válida";
		} else {
			return species+": "+contador;
		} 
	}
	/**
	 * Método que cuenta la cantidad de animales por barrio
	 * @return String con la cantidad de animales de cada barrio
	 */
	public String countByNeighborhood() {
		int usaquen =0;
		int chapinero=0;
		int santaFe=0;
		int sanCristobal=0;
		int usme=0;
		int tunjuelito=0;
		int bosa=0;
		int kennedy=0;
		int fontibon=0;
		int engativa=0;
		int suba=0;
		int bUnidos=0;
		int teusaquillo=0;
		int losMartires=0;
		int aNariño=0;
		int pAranda=0;
		int candelaria=0;
		int rUribe=0;
		int cBolivar=0;
		int sumapaz=0;
		int municipios=0;
		int sinIdentificar=0;
		//Bucle con múltiples condicionales que compara el atributo neighborhood con un texto y suma uno a la variable correspondiente
		for (int i = 0; i < petList.size(); i++) {
			if(petList.get(i).getNeighborhood().equals("USAQUEN")) {
				usaquen++;
			} else if(petList.get(i).getNeighborhood().equals("CHAPINERO")) {
				chapinero++;
			} else if(petList.get(i).getNeighborhood().equals("SANTA FE")) {
				santaFe++;
			} else if(petList.get(i).getNeighborhood().equals("SAN CRISTOBAL")) {
				sanCristobal++;
			} else if(petList.get(i).getNeighborhood().equals("USME")) {
				usme++;
			} else if(petList.get(i).getNeighborhood().equals("TUNJUELITO")) {
				tunjuelito++;
			}else if(petList.get(i).getNeighborhood().equals("BOSA")) {
				bosa++;
			}else if(petList.get(i).getNeighborhood().equals("KENNEDY")) {
				kennedy++;
			}else if(petList.get(i).getNeighborhood().equals("FONTIBON")) {
				fontibon++;
			}else if(petList.get(i).getNeighborhood().equals("ENGATIVA")) {
				engativa++;
			} else if(petList.get(i).getNeighborhood().equals("SUBA")) {
				suba++;
			} else if(petList.get(i).getNeighborhood().equals("B. UNIDOS")) {
				bUnidos++;
			} else if(petList.get(i).getNeighborhood().equals("TEUSAQUILLO")) {
				teusaquillo++;
			} else if(petList.get(i).getNeighborhood().equals("LOS MARTIRES")) {
				losMartires++;
			}else if(petList.get(i).getNeighborhood().equals("A. NARINO")) {
				aNariño++;
			} else if(petList.get(i).getNeighborhood().equals("P. ARANDA")) {
				pAranda++;
			} else if(petList.get(i).getNeighborhood().equals("LA CANDELARIA")) {
				candelaria++;
			} else if(petList.get(i).getNeighborhood().equals("R. URIBE")) {
				rUribe++;
			} else if(petList.get(i).getNeighborhood().equals("C. BOLIVAR")) {
				cBolivar++;
			} else if(petList.get(i).getNeighborhood().equals("SUMAPAZ")) {
				sumapaz++; 
			} else if(petList.get(i).getNeighborhood().equals("MUNICIPIOS ALEDAÑOS BOGOTA D.C.")) {
				municipios++;
			} else if(petList.get(i).getNeighborhood().equals("SIN IDENTIFICAR")) {
				sinIdentificar++;
			}
			
		}
		return "Usaquén: "+usaquen+"\nChapinero: "+chapinero+"\nSanta Fé: "+santaFe+"\nSan Cristóbal: "+sanCristobal
				+"\nUsme: "+usme+"\nTunjuelito: "+tunjuelito+"\nBosa: "+bosa+"\nKennedy: "+kennedy+"\nFontibón: "+fontibon
				+"\nEngativa: "+engativa+"\nSuba: "+suba+"\nBarrios Unidos: "+bUnidos+"\nTeusaquillo: "+teusaquillo
				+"\nLos Mártires: "+losMartires+"\nAntonio Nariño: "+aNariño+"\nPuente Aranda: "+pAranda+"\nLa Candelaria: "+candelaria
				+"\nRafael Uribe Uribe: "+rUribe+"\nCiudad Bolívar: "+cBolivar+"\nSumapaz: "+sumapaz+"\nMunicipios Aledaños: "+municipios
				+"\nSin Identificar:"+sinIdentificar;
	}
	/**
	 * Método que cuenta la cantidad de animales del barrio pasado por parámetro
	 * @param neigborhood
	 * @return String con la cantidad de animales del barrio pasado por parámetro
	 */
	public String countByNeighborhood(String neigborhood) {
		int contador=0;
		//Bucle con un condicional que compara el atributo neighborhood del Pet en la lista, con el pasado por parámetro y suma uno a la variable
		for (int i = 0; i < petList.size(); i++) {
			if(petList.get(i).getNeighborhood().equals(neigborhood.toUpperCase())) {
				contador++;
			}
		}
		if(contador==0) {
			return "Ingrese un barrio válido";
		} else {
			return neigborhood+": "+contador;
		} 
	}
	/**
	 * Método para búsqueda por diferentes parámetros
	 * @param n
	 * @param position
	 * @param species
	 * @param sex
	 * @param size
	 * @param potentDangerous
	 * @param neighborhood
	 * @return String con la lista de los objetos de Pet que coinciden con la búsqueda
	 */
	public String findByMultipleFields(int n, String position, String species,String sex, String size, boolean potentDangerous, String neighborhood) {
		String list="";
		int contador=0;
		if (n==0) {n=15;} //Registros mostrados por defecto
		//Si el usuario quiere los primeros (por defecto)
		if(position.equals("TOP") || position.isEmpty()) {
			//Todos los campos ingresados
			if(!species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Sólamente potentDangerous
			}else if(species.isEmpty() && sex.isEmpty() && size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.isPotentDangerous()==potentDangerous) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Todos los campos menos species
			}else if(species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()){
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSex().equals(sex) && pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Todos los campos menos sex
			}else if(!species.isEmpty() && sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Todos los campos menos size
			}else if(!species.isEmpty() && !sex.isEmpty() && size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Todos los campos menos neigborhood
			} else if(!species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			// Species y sex
			} else if(!species.isEmpty() && !sex.isEmpty() && size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.isPotentDangerous()==potentDangerous) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			// Species y size
			} else if(!species.isEmpty() && sex.isEmpty() && !size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Species y neigborhood
			} else if(!species.isEmpty() && sex.isEmpty() && size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Sex y size
			} else if(species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSex().equals(sex) && pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Sex y neigborhood
			} else if(species.isEmpty() && !sex.isEmpty() && size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSex().equals(sex) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Size y neigborhood
			} else if(species.isEmpty() && sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			}
		//Si el usuario quiere los úlimos
		}else if(position.equals("LAST")) {
			
			//Todos los campos ingresados
			if(!species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Sólamente potentDangerous
			}else if(species.isEmpty() && sex.isEmpty() && size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.isPotentDangerous()==potentDangerous) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Todos los campos menos species
			}else if(species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()){
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSex().equals(sex) && pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Todos los campos menos sex
			}else if(!species.isEmpty() && sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Todos los campos menos size
			}else if(!species.isEmpty() && !sex.isEmpty() && size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Todos los campos menos neigborhood
			} else if(!species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			// Species y sex
			} else if(!species.isEmpty() && !sex.isEmpty() && size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.isPotentDangerous()==potentDangerous) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			// Species y size
			} else if(!species.isEmpty() && sex.isEmpty() && !size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Species y neigborhood
			} else if(!species.isEmpty() && sex.isEmpty() && size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Sex y size
			} else if(species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSex().equals(sex) && pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Sex y neigborhood
			} else if(species.isEmpty() && !sex.isEmpty() && size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSex().equals(sex) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Size y neigborhood
			} else if(species.isEmpty() && sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSize().equals(size) && pet.isPotentDangerous()==potentDangerous && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			}
		}
		return list;
	}
	/**
	 * Método para búsqueda por diferentes parámetros
	 * @param n
	 * @param position
	 * @param species
	 * @param sex
	 * @param size
	 * @param neighborhood
	 * @return String con la lista de los objetos de Pet que coinciden con la búsqueda
	 */
	public String findByMultipleFields(int n, String position, String species,String sex, String size, String neighborhood) {
		String list="";
		int contador=0;
		if (n==0) {n=15;} //Registros mostrados por defecto
		//Si el usuario quiere los primeros (por defecto)
		if(position.equals("TOP") || position.isEmpty()) {
			//Todos los campos ingresados
			if(!species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.getSize().equals(size)&& pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Ningún campo
			} else if(species.isEmpty() && sex.isEmpty() && size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					list += (pet)+"\n";
					contador++;
					if(contador==n){
						i=petList.size();
					}
				}
			//Todos los campos menos species
			}else if(species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()){
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSex().equals(sex) && pet.getSize().equals(size) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Todos los campos menos sex
			}else if(!species.isEmpty() && sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSize().equals(size) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Todos los campos menos size
			}else if(!species.isEmpty() && !sex.isEmpty() && size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Todos los campos menos neigborhood
			} else if(!species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.getSize().equals(size)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			// Species y sex
			} else if(!species.isEmpty() && !sex.isEmpty() && size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			// Species y size
			} else if(!species.isEmpty() && sex.isEmpty() && !size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSize().equals(size)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Species y neigborhood
			} else if(!species.isEmpty() && sex.isEmpty() && size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Sex y size
			} else if(species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSex().equals(sex) && pet.getSize().equals(size)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Sex y neigborhood
			} else if(species.isEmpty() && !sex.isEmpty() && size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSex().equals(sex) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			//Size y neigborhood
			} else if(species.isEmpty() && sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = 0; i < petList.size(); i++) {
					Pet pet = petList.get(i);
					if(pet.getSize().equals(size) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=petList.size();
						}
					}
				}
			}
		//Si el usuario quiere los úlimos
		}else if(position.equals("LAST")) {
			//Todos los campos ingresados
			if(!species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.getSize().equals(size) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Ningún campo
			}else if(species.isEmpty() && sex.isEmpty() && size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					list += (pet)+"\n";
					contador++;
					if(contador==n){
						i=-1;
					}
				}
			//Todos los campos menos Species
			}else if(species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSex().equals(sex) && pet.getSize().equals(size) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Todos los campos menos sex
			}else if(!species.isEmpty() && sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSize().equals(size) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Todos los campos menos size
			}else if(!species.isEmpty() && !sex.isEmpty() && size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Todos los campos menos neigborhood
			} else if(!species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex) && pet.getSize().equals(size)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			// Species y sex
			} else if(!species.isEmpty() && !sex.isEmpty() && size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSex().equals(sex)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			// Species y size
			} else if(!species.isEmpty() && sex.isEmpty() && !size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getSize().equals(size)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Species y neigborhood
			} else if(!species.isEmpty() && sex.isEmpty() && size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSpecies().equals(species) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Sex y size
			} else if(species.isEmpty() && !sex.isEmpty() && !size.isEmpty() && neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSex().equals(sex) && pet.getSize().equals(size)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Sex y neigborhood
			} else if(species.isEmpty() && !sex.isEmpty() && size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSex().equals(sex) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			//Size y neigborhood
			} else if(species.isEmpty() && sex.isEmpty() && !size.isEmpty() && !neighborhood.isEmpty()) {
				for (int i = petList.size()-1; i > -1; i--) {
					Pet pet = petList.get(i);
					if(pet.getSize().equals(size) && pet.getNeighborhood().equals(neighborhood)) {
						list += (pet)+"\n";
						contador++;
						if(contador==n){
							i=-1;
						}
					}
				}
			}
		}
		return list;
	}
}
