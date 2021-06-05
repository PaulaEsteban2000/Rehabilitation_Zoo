package Exceptions;

import java.util.List;

import rehabilitationzoo.db.ifaces.AdministratorManager;
import rehabilitationzoo.db.jdbc.AdministratorSQL;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.DrugType;
import rehabilitationzoo.db.pojos.FeedingType;
import rehabilitationzoo.db.pojos.Worker;
import utils.KeyboardInput;

public class ExceptionMethods {

	public static AdministratorManager adminMan = new AdministratorSQL();
	
	public static void checkForExistingFeedingType(String foodType) throws AdminExceptions {
		if (foodType.equalsIgnoreCase("Carnivore") || foodType.equalsIgnoreCase("Hervibore") || foodType.equalsIgnoreCase("Omnivore")) {
		
		}else {
			throw new AdminExceptions (AdminExceptions.AdminErrors.NOTAFEEDINGTYPE);
		}
	
	}
	
	public static void checkForExistingWorkerType(String workerType) throws AdminExceptions {
		if (workerType.equalsIgnoreCase("Zoo_keeper") || workerType.equalsIgnoreCase("Vet")|| workerType.equalsIgnoreCase("Veterinary") || workerType.equalsIgnoreCase("Administrator")) {
		
		}else {
			throw new AdminExceptions (AdminExceptions.AdminErrors.NOTAWORKERTYPE);
		}
	
	}
	
	public static void checkForExistingAnimal(String unAnimal) throws AdminExceptions {
		boolean realAnimal= false;
		List <String> typesOfAnimalsInTheZoo=adminMan.getAnimalTypesByName();
		
		for(int i=0; i<typesOfAnimalsInTheZoo.size(); i++) {
			if(unAnimal.equalsIgnoreCase(typesOfAnimalsInTheZoo.get(i))) {
				realAnimal=true;
			}
		 }
		
		if (realAnimal == false){
			throw new AdminExceptions (AdminExceptions.AdminErrors.NOTANANIMALTYPE);
		}
		
	}//Comprobamos que el animal que nos han dicho es realmente un animal existente en el zoo y 
	// ya que estamos, marcamos el tipo de animal que es
	
	public static void checkForExistingWorker(List<Worker> workersInformation, Worker oneWorker) throws AdminExceptions {
		boolean workerExist = false;
		
		for(int i = 0; i<workersInformation.size(); i++){
			if(oneWorker.equals(workersInformation)) {
				System.out.println(oneWorker);
				System.out.println(workersInformation);
				workerExist = true;
			} else {
				workerExist=false;
			}
		}
		
		if(workerExist == false) {
			throw new AdminExceptions(AdminExceptions.AdminErrors.NULL);
		}
		
	}
	
	public static void checkForDrug(int drugId, List<Drug> differentDrugs) throws AdminExceptions {
		Boolean bol = false;
		
		for(int i =0; i< differentDrugs.size(); i++) {
			if (drugId == differentDrugs.get(i).getId()) {
				bol = true;
			} else {
				
			}
		}
		
		if (bol == false) {
			throw new AdminExceptions(AdminExceptions.AdminErrors.NOTADRUG);
		}
		
	}
	
	public static void checkForDrugType(int drugTypeId, List<DrugType> drugTypesList) throws AdminExceptions {
		Boolean realDrug = false;
		
		for(int i = 0; i<drugTypesList.size(); i++){
			if(drugTypeId == drugTypesList.get(i).getId() ) {
				realDrug = true;
				break; 
			}
		}
		
		if(realDrug == false) {
			throw new AdminExceptions(AdminExceptions.AdminErrors.NOTADRUGTYPE);
		}
		
	}

}
