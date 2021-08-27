package co.edu.unbosque.model.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;


public class Manager {

	private Pet pet;
	private ArrayList<Pet> petList = new ArrayList<>();
	private JFileChooser file = new JFileChooser();
	
	public Manager() {
		pet = new Pet(null, 0, null, null, null, false, null);
	}
	
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
			cadena = "Registros Existosos: "+ exitosos +" || Registros Fallidos: " + fallidos;
			fr.close();
		} catch (IOException e) {
			return null;
		}
		return cadena + "\nEl proceso de carga del archivo ha finalizado";
	}
	
	public String assignID() {
		String id="";
		int exceptions=0;
		for(int i=0;i<petList.size();i++) {
			int digits=2;
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
			id = microchip+"-"+species+sex+size+potentDangerous;
			boolean flag = false;
			while(!flag) {
				try {
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

	public String findByMicrochip(long microchip) {
		boolean flag = false;
		try {
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

	public String countBySpecies() {
		int canino=0;
		int felino=0;
		for (int i = 0; i < petList.size(); i++) {
			if(petList.get(i).getSpecies().equals("CANINO")) {
				canino++;
			} else if(petList.get(i).getSpecies().equals("FELINO")) {
				felino++;
			}
		}
		return "Canino: "+canino+"\nFelino: "+felino;
	}
	
	public String countBySpecies(String species) {
		int contador=0;
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

	public String countByNeighborhood(String neigborhood) {
		int contador=0;
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

	public String findByMultipleFields(int n, String position, String species,String sex, String size,boolean potentDangerous, String neighborhood) {
		String list="";
		if((position.equals("CANINO")||position.equals("FELINO")) && (sex.equals("MACHO")||sex.equals("HEMBRA"))) {
			for (int i = 0; i < n; i++) {
				Pet pet= petList.get(i);
				if(pet.getSpecies().equals(species) && pet.getSex().equals(sex)) {
					list += (petList.get(i))+"/n";
				}
		}
		
		}
		return list;
	}
	
	public String findByMultipleFields(int n, String position, String species,String sex, String size, String neighborhood) {
		String list="";
		if(position.equals("CANINO")||position.equals("FELINO")) {
			
		}
		for (int i = 0; i < n; i++) {
			if(petList.get(i).getSpecies().equals(species)) {
				list += (petList.get(i))+"/n";
			}
			
		}
		return list;
	}
}
