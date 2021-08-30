package co.edu.unbosque.model.persistence;

/**
 * Clase DTO del proyecto
 * @author David Real
 */
public class Pet {
	/**
	 * Objeto de tipo String para el ID
	 */
	private String id;
	/**
	 * Objeto de tipo Long para el microchip
	 */
	private long microchip;
	/**
	 * Objeto de tipo String para la especie
	 */
	private String species;
	/**
	 * Objeto de tipo String para el sexo
	 */
	private String sex;
	/**
	 * Objeto de tipo String para el tamaño
	 */
	private String size;
	/**
	 * Objeto de tipo Booleano para potencialmente peligroso
	 */
	private boolean potentDangerous;
	/**
	 * Objeto de tipo String para el barrio
	 */
	private String neighborhood;
	/**
	 * Método constructor de la clase Pet
	 * @param id
	 * @param microchip
	 * @param species
	 * @param sex
	 * @param size
	 * @param potentDangerous
	 * @param neighborhood
	 */
	public Pet(String id, long microchip, String species, String sex, String size, boolean potentDangerous, String neighborhood) {
		this.id = id;
		this.microchip = microchip;
		this.species = species;
		this.sex = sex;
		this.size = size;
		this.potentDangerous = potentDangerous;
		this.neighborhood = neighborhood;
	}
	/**
	 * Sobreescritura del método toString() de Object
	 */
	public String toString() {
		return "id: "+this.id+" || Microchip: "+this.microchip+" || Species: "+this.species+" || Size: "+this.size+" || PotentDangerous: "+this.potentDangerous+" || neighborhood: "+this.neighborhood;
	}
	
	//Getters-Setters
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getMicrochip() {
		return microchip;
	}

	public void setMicrochip(long microchip) {
		this.microchip = microchip;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public boolean isPotentDangerous() {
		return potentDangerous;
	}

	public void setPotentDangerous(boolean potentDangerous) {
		this.potentDangerous = potentDangerous;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

}
