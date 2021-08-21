package co.edu.unbosque.model.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class Manager {

	private Pet pet;
	private ArrayList<Pet> petList;
	private JFileChooser file = new JFileChooser();

	public Manager() {
		pet = new Pet(null, 0, null, null, null, false, null);
		petList = new ArrayList<>();
	}
	
	public String readFile() {
		String linea="";
		String cadena="";
		file.showOpenDialog(file);
		File f = file.getSelectedFile();
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			linea=br.readLine();
			int contador=0;
			while (linea!=null) {
				try {
				String[] partes = linea.split(";");
				pet.setMicrochip(Long.parseLong(partes[0]));
				pet.setSpecies(partes[1]);
				pet.setSex(partes[2]);
				pet.setSize(partes[3]);
				pet.setPotentDangerous(Boolean.parseBoolean(partes[4].replace("NO", "False").replace("SI", "True")));
				pet.setNeighborhood(partes[5]);
				petList.add(pet = new Pet(null, pet.getMicrochip(), pet.getSpecies(), pet.getSex(), pet.getSize(), pet.isPotentDangerous(), pet.getNeighborhood()));
				System.out.println(petList.get(contador)+"\n");
				contador++;
				} catch (NumberFormatException e) {}
				cadena =  petList+"";
				linea=br.readLine();
			}
			fr.close();
		} catch (IOException e) {
			return null;
		}
		return cadena;
	}
	
}
