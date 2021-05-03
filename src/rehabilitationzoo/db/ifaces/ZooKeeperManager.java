package rehabilitationzoo.db.ifaces;

import java.sql.SQLException;
import java.util.List;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Habitat;

public interface ZooKeeperManager {
	//PARTE DE MERI
	
	//ZOO-KEEPER
	public void printHabitatsNamesAndId();
	public List<String> getHabitats();
	public List<Habitat> getHabitatById (Integer habitatId);
	
	//1. DRUG ADMINISTRATION
		public void drugAdministrationToAnimal(Animal animal);
	//2. CHOOSE HABITAT
		public Integer getHabitatIdByName(String habitatName) throws SQLException;  //yo tengo hecho ya un metodo asi en VetManager, por si lo quieres ;) - Paula
		public void feedAnimal(); //boolean?
		public void batheAnimal(Habitat habitat); //boolean?
		public void cleanHabitat(Habitat habitat); //boolean?
		public void fillUpWaterTank(Habitat habitat); //boolean?
	
}
