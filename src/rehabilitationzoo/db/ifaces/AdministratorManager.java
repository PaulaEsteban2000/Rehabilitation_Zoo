package rehabilitationzoo.db.ifaces;

import java.sql.SQLException;
import java.util.List;

import Exceptions.AdminExceptions;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.AnimalType;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.GroundType;
import rehabilitationzoo.db.pojos.DrugType;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Worker;

public interface AdministratorManager {
	//PARTE DE NATI
	
	public Boolean checkForUsers(String userEmail); //method for login susbystem
	
	//ADMINISTRATOR
		
		//public List<Drug> searchDrugsByName (String name); //cast??
		
	
		//ANIMALS
		public void addAnimal(Animal animal) ;
		public void listAnimals();
		public void updateAnimal(Animal animal);
		
		
		//ANIMALS TYPES
		public void introducingAnimalsTypes(AnimalType animalType) ;
		public List<String> getAnimalTypesByName();
		public Integer getAnimalTypesById(AnimalType type);
		public List<AnimalType> listAnimalTypes();
		
		//WORKERS
		public List<Worker> searchWorkerByName (String name);
		public void introducingWorkers(Worker aWorker);
		public Worker getAWorkerFromNameAndLastname(String name, String lastname) ;
		public Worker getAWorkerFromId(Integer id) ;
		
		public List<String> getAllWorkersNamesAndLastNames() ;
		public void deleteThisWorker(Integer id);
		public void modifyWorker(Integer id , Float salary);
		public List<Worker> getWorkersInfo() ;
		
	    //DRUGS TYPES
		public void addNewDrugType(String drugType, float dosis);
		public void listDrugTypes()  ;
		public List<DrugType> getDrugTypes() ;
		
		//DRUG
		public void addNewDrug(Drug drug);
		//public List <Integer> getIdsFromDrugs(String drugName);throws AdminExceptions;  
		public void deleteDrug(Integer drugId);
		//public String getTypeFromDrugs(Integer id);
		public List <Drug> getDrugs() ;
		public Drug searchDrugByName(String name);
		// public void modifyDrug(Drug drug); //needed?
		
        //HABITATS
		public List<String> weListHabitats() ;
		public void addHabitat(Habitat habitat);

		
		public void addGroundType(GroundType ground)throws AdminExceptions;

		

		


		
		
		
}
