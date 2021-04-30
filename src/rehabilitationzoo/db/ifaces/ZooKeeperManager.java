package rehabilitationzoo.db.ifaces;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Habitat;

public interface ZooKeeperManager {
	//PARTE DE MERI
	
	//ZOO-KEEPER
	public void printHabitatsNamesAndId();
	
	//1. DRUG ADMINISTRATION
		public void drugAdministrationToAnimal(Animal animal);
	//2. CHOOSE HABITAT
		public Habitat getHabitatIdByName(String habitatName); //yo tengo hecho ya un metodo asi en VetManager, por si lo quieres ;) - Paula
		public void feedAnimal(); //boolean?
		public void batheAnimal(); //boolean?
		public void cleanHabitat(); //boolean?
		public void fillUpWaterTank(); //boolean?
	
}
