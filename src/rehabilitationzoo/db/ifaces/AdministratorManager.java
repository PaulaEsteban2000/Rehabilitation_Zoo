package rehabilitationzoo.db.ifaces;

import java.sql.SQLException;
import java.util.List;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.AnimalType;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.Worker;

public interface AdministratorManager {
	//PARTE DE NATI
	
	//ADMINISTRATOR
		public List<Worker> searchWorkerByName (String name);
		//public List<Drug> searchDrugsByName (String name); //cast??
		
	
	//1. MANAGE ANIMALS
		public void addAnimal(Animal animal) throws SQLException;
		public void introducingAnimalsTypes(AnimalType animalType) throws SQLException;
		
		
	//2. MANAGE WORKERS
		public void introducingWorkers(Worker aWorker);
		public List<String> getAllWorkersNamesAndLastNames();
		public void deleteThisWorker(String name,String lastname);
		public void modifyWorker(String name, String lastname, Integer salary);
		
		
	//3. MANAGE DRUGS
		public void addNewDrugType(String drugType);
		public void addNewDrug(Drug drug);
		public List <String> getDrugTypes();
		public Integer getIdsFromDrugs(String drugName);
		public Drug searchDrugByName(String name);  
		public void deleteDrug(Drug drug);
		// public void modifyDrug(Drug drug); //needed?
		
		
}
