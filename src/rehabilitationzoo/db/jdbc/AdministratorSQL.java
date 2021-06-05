package rehabilitationzoo.db.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Exceptions.AdminExceptions;
import rehabilitationzoo.db.ifaces.AdministratorManager;
import rehabilitationzoo.db.ifaces.VetManager;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.AnimalType;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.DrugType;
import rehabilitationzoo.db.pojos.FeedingType;
import rehabilitationzoo.db.pojos.GroundType;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Illness;
import rehabilitationzoo.db.pojos.LightType;
import rehabilitationzoo.db.pojos.Worker;
import rehabilitationzoo.db.pojos.WorkerType;
//import sample.db.pojos.Department;
import utils.KeyboardInput;

public class AdministratorSQL implements AdministratorManager{


    List<DrugType> listOfDrugTypes = new ArrayList<DrugType>();
    List<Drug> listOfDrugs = new ArrayList<Drug>();
    
	//ANIMALS METHODS 
		
	@Override
	public void addAnimal(Animal animal) { //do we need a prepared Statement better to avoid injection? I think so bc it is insert
		try {
			VetManager unVet = new VetSQL(); 
			
			String sql = "INSERT INTO animals (enterDate,habitat_id,lastBath,lastFed,lastDrug,deathDate,freedomDate,type_id,name)";
			sql+= " VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = JDBCManager.c.prepareStatement(sql); 
				
			pstmt.setDate(1, animal.getEnterDate());
			pstmt.setInt(2,unVet.getHabitatIdByName("Wait Zone"));
			
			
			if (animal.getLastBath()!= null) {
				pstmt.setString(3, animal.getLastBath().toString());
				
			} else {
				pstmt.setString(3, null);
			}
			

			if (animal.getLastFed()!= null) {
				pstmt.setString(4, animal.getLastFed().toString());;
				
			} else {
				pstmt.setString(4, null);
			}
			
			if (animal.getLastDrug()!= null) {
				pstmt.setString(5, animal.getLastDrug().toString());
				
			} else {
				pstmt.setString(5, null);
			}
			
			if (animal.getDeathDate()!= null) {
				pstmt.setString(6, animal.getDeathDate().toString());
				
			} else {
				pstmt.setString(6, null);
			}
			
			if (animal.getFreedomDate()!= null) {
				pstmt.setString(7, animal.getFreedomDate().toString());
				
			} else {
				pstmt.setString(7, null);
			}
			
			//pstmt.setString(3, animal.getLastBath().toString());
			//pstmt.setString(4, animal.getLastFed().toString());
			//pstmt.setString(5, animal.getLastDrug().toString());
			//pstmt.setString(6, animal.getDeathDate().toString());
			//pstmt.setString(7, animal.getFreedomDate().toString());
			pstmt.setInt(8, animal.getType_id());
			pstmt.setString(9, animal.getName());
		
			pstmt.executeUpdate(sql);
			pstmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void updateAnimal(Animal animal) {
		try {
			String sql = "UPDATE animals ";
			PreparedStatement s = JDBCManager.c.prepareStatement(sql);
			s.setDate(2, animal.getLastBath() );
			s.setDate(3, animal.getLastFed() );
			s.setDate(4,  animal.getLastDrug());
			s.setDate(5,  animal.getDeathDate() );
			s.setDate(6,  animal.getFreedomDate());
			
			//System.out.println(sql);
			s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
			
	} 
	
	
	public void listAnimals () { //we show all the animals in the database
		
		try {	
		String sql = "SELECT * FROM animals "; 			
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { 
			int id = rs.getInt("id");
			Date enterDate = rs.getDate("enterDate");
			Integer habitat_id = rs.getInt("habitat_id");
			Date lastBath = rs.getDate("lastBath");
			Date lastFed = rs.getDate("lastFed");
			Date lastDrug = rs.getDate("lastDrug");
			Date deathDate = rs.getDate("deathDate");
			Date freedomDate = rs.getDate("freedomDate");
			int type_id = rs.getInt("type_id");
			String name = rs.getString("name");
			
		
			//System.out.println(sql);
			prep.close();
			rs.close();
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
			
	}
		
		
	//ANIMAL TYPES
	
	
	@Override
	public void introducingAnimalsTypes (AnimalType animalType) { 
		try {
			String feeding = null;
			if(animalType.getWhatDoYouEat().equals(FeedingType.CARNIVORE)) {
				feeding = "Carnivore";
			}else if(animalType.getWhatDoYouEat().equals(FeedingType.HERVIBORE)) {
				feeding = "Hervibore";
			}else {
				feeding = "Omnivore";
			}
			String sql = "INSERT INTO animals_characteristics(type,feedingType)";
			sql+= "VALUES (?,?)";
			
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			
			prep.setString(1, animalType.getType());
			prep.setString(2, feeding);
		    prep.executeUpdate();
			prep.close();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public List<String> getAnimalTypesByName() {
		List<String> typesOfAnimals = new ArrayList<String>();
		
		try {
			String sql = "SELECT type FROM animals_characteristics"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { 
				String type = rs.getString("type");
				typesOfAnimals.add(type);
			}
			
			
			//System.out.println(sql);
			rs.close();
			prep.close();// Close database connection
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		
	return typesOfAnimals;	
	}	
	
	
	@Override
	public Integer getAnimalTypesById(AnimalType type) {
		//List<Integer> typesOfAnimals = new ArrayList<Integer>();
		Integer id=null;
		
		try {
			String sql = "SELECT id FROM animals_characteristics WHERE type LIKE ? "; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { 
				 id = rs.getInt("id");
				//typesOfAnimals.add(id); 
			}
				//System.out.println(sql);
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		
	return id;	
	}
	
	
	public List<AnimalType> listAnimalTypes() {
		List<AnimalType> typesOfAnimals = new ArrayList<AnimalType>();
		try {
			String sql = "SELECT * FROM animals_characteristics"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { 
				Integer id = rs.getInt("id");
				String type = rs.getString("type");
				FeedingType foodType;
				if (rs.getString("feedingType").equalsIgnoreCase("Carnivore") ){
						foodType = FeedingType.CARNIVORE;
				}else {
					if (rs.getString("feedingType").equalsIgnoreCase("Hervibore")) {
						foodType = FeedingType.HERVIBORE;
					}else {
						foodType =FeedingType.OMNIVORE;
							}
						}	
				AnimalType newAnimalType = new AnimalType (id,type, foodType);	
				typesOfAnimals.add(newAnimalType);
				}
				//System.out.println(sql);
				prep.close();
				rs.close();
			
		}catch(Exception ex) {
				ex.printStackTrace();
			}
		return typesOfAnimals;
		
	}
	
	
	/*AnimalType oneAnimalType =null;
	
	try {
		String sql = "SELECT * FROM animals_characteristics WHERE type LIKE ?"; 			    
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { 
			String type = rs.getString("type");
			
			FeedingType foodType;
			if (rs.getString("feedingType").equalsIgnoreCase("Carnivore") ){
					foodType = FeedingType.CARNIVORE;
			}else {
				if (rs.getString("workerType").equalsIgnoreCase("Hervibore")) {
					foodType = FeedingType.HERVIBORE;
				}else {
					foodType =FeedingType.OMNIVORE;
						}
					}	
				
			oneAnimalType = new AnimalType (type, foodType);
		}*/
		
		
	
	//HABITATS
	
	@Override
	public void addHabitat(Habitat habitat) {
		try {
			String light;
			if(habitat.getLight().equals(LightType.HIGH)) {
				light = "High";
			}else if(habitat.getLight().equals(LightType.MEDIUM)) {
				light = "Medium";
			}else {
				light = "Low";
			}
			String sql = "INSERT INTO habitats (name, lastCleaned, waterTank, temperature, light)" +
					"VALUES (?,?,?,?,?)"; 
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
				
				prep.setString(1,habitat.getName());
				prep.setDate(2,habitat.getLastCleaned( ));
				prep.setDate(3,habitat.getWaterTank() );
				prep.setInt(4,habitat.getTemperature());
				prep.setString(5,light );
					
			    prep.executeUpdate();
				prep.close();
				
			}catch( Exception ex) {
				ex.printStackTrace();
				}
	}
	
	public void addGroundType(GroundType ground) /*throws AdminExceptions*/ {
		try {
			
			String sql = "INSERT INTO groundTypes (habitat_id, type)" +
					"VALUES (?,?)"; 
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
				
				prep.setInt(1,ground.getHabitat());
				prep.setString(2,ground.getType());
					
			    prep.executeUpdate();
				prep.close();
				
			}catch( Exception ex) {
				ex.printStackTrace();
				}
	}
	
	
	public List<String> weListHabitats(){
		List <String> namesHabitats = new ArrayList<String>();
		
		try {
		String sql = "SELECT name FROM habitats "; 			    
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { 
			String name = rs.getString("name");
			namesHabitats.add(name);
		}
		
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return namesHabitats;		
	}
	
	//WORKERS
	@Override
	public void introducingWorkers (Worker aWorker) {//Id is chosen by the database

		try {  			
			String stringWorker;
			
			if(aWorker.getType().equals(WorkerType.ADMINISTRATOR)) {
				stringWorker = "Administrator";
			}else if(aWorker.getType().equals(WorkerType.ZOO_KEEPER)) {
				stringWorker = "Zoo Keeper";
			}else {
				stringWorker = "Veterinary";
			}
			
			String sql = "INSERT INTO workers (name, lastName, hireDate, salary, workerType )";
			sql+= "VALUES (?,?,?,?,?)";
			
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			
			prep.setString(1, aWorker.getName());
			prep.setString(2, aWorker.getLastName() );
			prep.setDate(3, aWorker.getHireDate() );
			prep.setFloat(4, aWorker.getSalary() );
			prep.setString(5, stringWorker); //workerType
			
			prep.executeUpdate();
			prep.close();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<String> getAllWorkersNamesAndLastNames() {
		List<String> workersNamesAndLastNames = new ArrayList<String>();
		
		try {
		String sql = "SELECT name,lastname FROM workers"; 			    
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { 
			String nameAndLastName = rs.getString("name"+" "+"lastName");
			workersNamesAndLastNames.add (nameAndLastName);
		}
		
			prep.close();
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	return workersNamesAndLastNames;
	
	}
	
	public Worker getAWorkerFromNameAndLastname(String name, String lastname) {
		
	Worker oneWorker = new Worker();
		try {
		String sql = "SELECT * FROM workers WHERE name LIKE ? AND lastName LIKE ?"; 			    
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
		prep.setString(1, name);
		prep.setString(2, lastname);
		ResultSet rs = prep.executeQuery();
	
		while (rs.next()) { 
			int id1 = rs.getInt("id");
			//System.out.print(id1+"\n");
			String name1= rs.getString("name");
			String lastname1 = rs.getString("lastName");
			Date hireDate = rs.getDate("hireDate");
			Float salary = rs.getFloat("salary");
			WorkerType job;
			if (rs.getString("workerType").equalsIgnoreCase("Administrator") ){
					job = WorkerType.ADMINISTRATOR;
			}else {
				if (rs.getString("workerType").equalsIgnoreCase("Zoo Keeper")) {
					job = WorkerType.ZOO_KEEPER;
				}else {
					job = WorkerType.VETERINARY;
						}
					}	
				
		oneWorker = new Worker(id1, name1, lastname1, hireDate, salary, job );
		//System.out.print(oneWorker);
		
		
		}
		
			prep.close();
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	return oneWorker;	
	}
	
	
	public Worker getAWorkerFromId(Integer id) {
		
		Worker oneWorker = new Worker();
			try {
			String sql = "SELECT * FROM workers WHERE id LIKE ? "; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
					
			while (rs.next()) { 
				int id1 = rs.getInt("id");
				String name1= rs.getString("name");
				String lastname1 = rs.getString("lastName");
				Date hireDate = rs.getDate("hireDate");
				Float salary = rs.getFloat("salary");
				WorkerType job;
				if (rs.getString("workerType").equalsIgnoreCase("Administrator") ){
						job = WorkerType.ADMINISTRATOR;
				}else {
					if (rs.getString("workerType").equalsIgnoreCase("Zoo Keeper")) {
						job = WorkerType.ZOO_KEEPER;
					}else {
						job = WorkerType.VETERINARY;
							}
						}	
					
			oneWorker = new Worker(id1, name1, lastname1, hireDate, salary, job );
			
			
			}
			
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
		return oneWorker;	
		}
		
	
	@Override
	public List<Worker> getWorkersInfo() {
		
			List <Worker> workersinfo = new ArrayList<Worker>();
			
			try {
				
			String sql = "SELECT * FROM workers "; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { 
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String lastname = rs.getString("lastName");
				Date hireDate = rs.getDate("hiredate");
				Float salary = rs.getFloat("salary");
				WorkerType job;
				if (rs.getString("workerType").equalsIgnoreCase("Administrator") ){
						job = WorkerType.ADMINISTRATOR;
				}else if (rs.getString("workerType").equalsIgnoreCase("Zoo Keeper")) {
					job = WorkerType.ZOO_KEEPER;
				} else {
					job = WorkerType.VETERINARY;
				}
					
			Worker oneWorker = new Worker(id, name, lastname, hireDate, salary, job );
			workersinfo.add(oneWorker);
			
			}
			
			
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
			return workersinfo;		
		}
		
	
	
	@Override
	public void deleteThisWorker(Integer id) {
		
		
		try {
			String sql = "DELETE FROM workers WHERE id = ?"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			
			prep.setInt(1, id);
			
			prep.executeUpdate();
			prep.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	


	@Override
	public void modifyWorker(Integer id , Float salary) {
		try {
				String sql = "UPDATE workers SET salary=? WHERE id =? ";
				PreparedStatement s = JDBCManager.c.prepareStatement(sql);
				s.setFloat(1, salary);
				s.setInt( 2, id ); 
				
				s.executeUpdate();
				s.close();

			} catch (Exception e) {
				e.printStackTrace();
			}	
	}
	
	//DRUG TYPES 
	
	@Override
	public void addNewDrugType (String drugName, float dosis) {
		try {
			DrugType aDrugType = new DrugType(drugName, dosis);
		    String sql= "INSERT INTO drugTypes (type,dosis) VALUES (?,?)"; 
		    PreparedStatement stmt = JDBCManager.c.prepareStatement(sql); 
			
		    listOfDrugTypes.add(aDrugType);
		    stmt.setString(1, aDrugType.getType());
		    stmt.setFloat(2, aDrugType.getDosis());
		    
			stmt.executeUpdate();
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
}
	
public void listDrugTypes(){
		try {	
			
		String sql = "SELECT * FROM drugTypes "; 			
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
		ResultSet rs = prep.executeQuery();
		
		//System.out.println(sql);
		while (rs.next()) { //like hasNext
			int id = rs.getInt("id");
			String type = rs.getString("type");
			Float dosis = rs.getFloat("dosis");
			
			//DrugType drugs = new DrugType (id, type, dosis);
			
			
			for(int i=0; i<listOfDrugTypes.size(); i++) {
				System.out.println(listOfDrugTypes.get(i).getId());
				System.out.println(listOfDrugTypes.get(i).getType());
				System.out.println(listOfDrugTypes.get(i).getDosis());
			}
			
			
			prep.close();
			rs.close();
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
			
	}

		@Override
		public List<DrugType> getDrugTypes() {
			
			 List<DrugType> weReturnDrugTypes = new ArrayList<DrugType>();
			 
			 Integer id; 
			 String type;
			 float dosis;
			
			try {
				String sql = "SELECT * FROM drugTypes"; 			    
				PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
				ResultSet rs = prep.executeQuery();
				
				while (rs.next()) { //like hasNext
						id = rs.getInt("id");
						type=rs.getString("type");
						dosis= rs.getInt("dosis");
						
					 DrugType oneDrugType= new DrugType(id, type, dosis);
					 weReturnDrugTypes.add(oneDrugType);
					}
				
					prep.close();
					rs.close();
					
				}catch(Exception ex) {
					ex.printStackTrace();
			}
			return weReturnDrugTypes;
		}

	
	//DRUGS 
	
	@Override
	public void addNewDrug(Drug oneDrug) {
		try {
			
			String sql = "INSERT INTO drugs (name,treatmentDuration,periodBetweenDosis,drugType_id) VALUES (?,?,?,?)" ;
			PreparedStatement stmt = JDBCManager.c.prepareStatement(sql); 
			//listOfDrugs.add(oneDrug);
			stmt.setString(1, oneDrug.getName());
			stmt.setInt(2, oneDrug.getTreatmentDuration());
			stmt.setInt(3, oneDrug.getPeriodBetweenDosis());
			stmt.setInt(4, oneDrug.getType());
			
			
			stmt.executeUpdate();
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	//	public Drug(String name, Integer treatmentDuration, Integer periodBetweenDosis, Integer drugType_id, Float dosis) {

	@Override
	public Drug searchDrugByName(String name){ 
		
		String drugName=null;
		Integer drugPeriodBetweenDosis= null;
		Integer drugTreatmentDuration =null;
		Float drugDosis=null;
		Integer drugTypeId =null;
		Integer drugId =null;
		
		Drug searchedDrug= new Drug(drugName, drugTreatmentDuration, drugPeriodBetweenDosis, drugTypeId);
		
		
		try {
			String sql = "SELECT * FROM drugs WHERE name ?"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
					drugId = rs.getInt("id");
					drugName=rs.getString("name");
					drugPeriodBetweenDosis= rs.getInt("periodBetweenDosis");
					drugTreatmentDuration =rs.getInt("treatmentDuration");
					drugTypeId = rs.getInt("drugType_id");
					
				   searchedDrug= new Drug(drugId,drugName, drugTreatmentDuration, drugPeriodBetweenDosis, drugTypeId );
				}
			
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
		}
		
		return searchedDrug;
	}
	
	
	/*@Override
	public List <Integer> getIdsFromDrugs(String drugName){
	
		List <Integer> theId =new ArrayList<Integer>();
		try {
			String sql = "SELECT id FROM drugs WHERE name LIKE ?"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
				Integer drugId1 = rs.getInt("id");
				theId.add(drugId1);
				
			}
			System.out.println(theId);
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		return theId;
		
	}
	


	/*@Override
	public String getTypeFromDrugs(Integer id){ //NO LO USAMOS CREO
		
		String stringDrug=null;
		try {
			String sql = "SELECT type FROM drugTypes WHERE id LIKE ?"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
				stringDrug = rs.getString("type");
				
			}
			
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		return stringDrug;
		
	}*/
	
	public List <Drug> getDrugs() {
		List <Drug> theDrug = new ArrayList<Drug>();
		try {	
		String sql = "SELECT * FROM drugs "; 			
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { 
			int id = rs.getInt("id");
			String name = rs.getString("name");
			Integer treatmentDuration = rs.getInt("treatmentDuration");
			Integer periodBetweenDosis = rs.getInt("periodBetweenDosis");
			Integer drugType_id = rs.getInt("drugType_id");
			
			Drug specificDrug = new Drug(id, name, treatmentDuration, periodBetweenDosis, drugType_id);
			theDrug.add(specificDrug);
			}
			
			
			prep.close();
			rs.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
			return theDrug;
	}
	

	@Override
	public void deleteDrug(Integer drugId) {
		
		try {
			String sql = "DELETE FROM drugs WHERE id = ?"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			
			prep.setInt(1, drugId);
			
			prep.executeUpdate();
			prep.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}


	@Override
	public List<Worker> searchWorkerByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public Boolean checkForUsers(String userEmail) {
		Boolean bol = false;

		try {
			String sql = "SELECT email FROM users WHERE email LIKE ?"; 
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + userEmail + "%"); 
			ResultSet rs = prep.executeQuery();

			while (rs.next()) { 
				String email = rs.getString("email");
				
				if (email != null) {
					bol = true;
				}
				
			}

			prep.close();
			rs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bol;
	}








}
