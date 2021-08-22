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
				pet.setSize(partes[3]);
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

	public Pet findByMicrochip(long microchip) {
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
			return null;
		}
		return pet;
	}
}
