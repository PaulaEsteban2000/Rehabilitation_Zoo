package rehabilitationzoo.db.ifaces;

import java.sql.SQLException;
import java.util.List;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.Worker;

public interface AdministratorManager {
	//PARTE DE NATI
	
	//ADMINISTRATOR
	public List<Worker> searchWorkerByName (String name);
	public List<Drug> searchDrugByName (String name);
	//1. MANAGE ANIMALS
		public void addAnimal(Animal animal) throws SQLException;
		public void returAnimalToTheWilderness(Animal animal);
		//changes releaseDate
		public void markAnimalAsDeceased(Animal animal);
		//changes deathDate
	//2. MANAGE WORKERS
		public void hireWorker(Worker worker); 
		public Worker getWorker(String name, String lastName);
		public void fireWorker(Worker worker);
		public void modifyWorker(Worker worker, Integer salary);
	//3. MANAGE DRUGS
		public void addNewDrug(Drug drug);
		public void deleteDrug(Drug drug);
		// public void modifyDrug(Drug drug); //needed?
		/**@Override
		public void addAnimal(Animal animal) throws SQLException { //do we need a prepared Statement better to avoid injection? I think so bc it is an insert
			//Id is chosen by the database
			String sql = "INSERT INTO animals (name) VALUES (?)"; //the ? filters any SQL language
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setObject(0, animal); //Es asi??
			prep.executeUpdate();
			prep.close();
		}*/
	
}
