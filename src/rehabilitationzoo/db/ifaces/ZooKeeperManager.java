package rehabilitationzoo.db.ifaces;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Habitat;

public interface ZooKeeperManager {
	//PARTE DE MERI
	
	//ZOO-KEEPER
	public List<String> getHabitats();
	public List<Habitat> getHabitatById (Integer habitatId);
	
	//1.  ANIMALS
		public Date drugAdministrationToAnimals(Habitat habitat);
		public Date feedAnimals(Habitat habitat);
		public Date batheAnimals(Habitat habitat); 
	//2.  HABITAT
		public Integer getHabitatIdByName(String habitatName) throws SQLException;  //yo tengo hecho ya un metodo asi en VetManager, por si lo quieres ;) - Paula
		public void cleanHabitat(Habitat habitat); //boolean?
		public void fillUpWaterTank(Habitat habitat); //boolean?
	
}
