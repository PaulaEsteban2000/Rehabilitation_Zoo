package rehabilitationzoo.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rehabilitationzoo.db.ifaces.AdministratorManager;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.AnimalType;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.DrugType;
import rehabilitationzoo.db.pojos.FeedingType;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Illness;
import rehabilitationzoo.db.pojos.LightType;
import rehabilitationzoo.db.pojos.Worker;
//import sample.db.pojos.Department;

public class AdministratorSQL implements AdministratorManager{

	
	@Override
	public void addAnimal(Animal animal) { //do we need a prepared Statement better to avoid injection? I think so bc it is insert
		try {
			//Ids are chosen by the database
			Statement stmt = JDBCManager.c.createStatement(); //JDBCManager.c porque asi tenemos una sola conexion abierta en la clase que se encarga de la DB - Paula
			String sql = "INSERT INTO animals (enterDate,lastBath,lastFed,deathDate,freedomDate,name)";
			sql+= "VALUES ('" + animal.getEnterDate() + "','"  + animal.getLastBath() + "','" 
							  + animal.getFreedomDate() + "','" + animal.getName() + ")";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addHabitat(Habitat habitat) { //do we need a prepared Statement better to avoid injection? I think so bc it is insert
		try {
			//Ids are chosen by the database
			Statement stmt = JDBCManager.c.createStatement(); //JDBCManager.c porque asi tenemos una sola conexion abierta en la clase que se encarga de la DB - Paula
			String sql1 = "INSERT INTO habitats (name, lastCleaned, waterTank, temperature, light)";
			sql1+= "VALUES ('" + habitat.getName() + ", " + habitat.getLastCleaned() + ", " + habitat.getWaterTank() + "','" 
							+ ",  "+ habitat.getTemperature() + ",  "+ habitat.getLight() +")";
		
			
			System.out.println(sql1);
			stmt.executeUpdate(sql1);
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void introducingAnimalsTypes (AnimalType animalType) { 
		try {
			//Ids are chosen by the database
			Statement stmt = JDBCManager.c.createStatement(); 
			String sql = "INSERT INTO animals_characteristics(type,feedingType)";
			sql+= "VALUES ('" + animalType.getType() + "','" + animalType.getWhatDoYouEat() + ")";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public List<String> getAnimalTypesByName() {
		List<String> typesOfAnimals = new ArrayList<String>();
		
		
		try {
			Statement stmt = JDBCManager.c.createStatement(); 
			String sql = "SELECT type FROM animals_characteristics"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
				String type = rs.getString("type");
				typesOfAnimals.add(type);
				//FeedingType whatDoYouEat =  FeedingType.valueOf(rs.getString("feedingType")) ;
				//AnimalType newType = new AnimalType(type, whatDoYouEat);
				
				//numberOfIllnesses = Integer.parseInt(rs.getString("xxx")); 
			
				System.out.print("In SQL: Se ha guardado"+"\n");
			}
			
			System.out.println(sql);
			prep.executeUpdate(sql);

				rs.close();
				stmt.close();
				System.out.println("Search finished.");// Retrieve data: end
				
				prep.close();// Close database connection
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		
	return typesOfAnimals;	
	}	
	
	@Override
	public List<String> getAnimalTypesById(String name) {
		List<String> typesOfAnimals = new ArrayList<String>();
		
		
		try {
			String sql = "SELECT id FROM animals_characteristics WHERE type LIKE ? "; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
				String type = rs.getString("type");
				typesOfAnimals.add(type);
			}
			
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		
	return typesOfAnimals;	
	}
	

	@Override
	public void introducingWorkers (Worker aWorker) {//Id is chosen by the database

		try {  			
			
			Statement stmt = JDBCManager.c.createStatement(); 
			String sql = "INSERT INTO workers (name, lastname, hireDate, salary, workerType, whichHabitatDoYouWorkOn )";
			sql+= "VALUES ('" + aWorker.getName() + "','" + aWorker.getLastName() + "','" +aWorker.getHireDate()+ "','" +aWorker.getSalary()
			+ aWorker.getType()+ "','" + aWorker.getwhichHabitatDoYouWorkOn() + ")";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<String> getAllWorkersNamesAndLastNames(){
		List<String> workersNamesAndLastNames = new ArrayList<String>();
		
		try {
		String sql = "SELECT names,lastnames FROM workers"; 			    
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { //like hasNext
			String nameAndLastName = rs.getString("name"+" "+"lastname");
			workersNamesAndLastNames.add (nameAndLastName);
		}
		
			prep.close();
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	return workersNamesAndLastNames;
	
	}
	
	
	@Override
	public void deleteThisWorker(String name,String lastname) {
		
		try {
			String sql = "DELETE * FROM workers WHERE name LIKE ? AND WHERE lastname LIKE ?"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	

	

	@Override
	public void modifyWorker(String name, String lastname, Integer salary) {
		try {
				String sql = "UPDATE workers WHERE name LIKE ? AND WHERE lastname LIKE ?";
				PreparedStatement s = JDBCManager.c.prepareStatement(sql);
				s.setString( 1,"%" + salary + "%"); //ES ASI?
				s.executeUpdate();
				s.close();

			} catch (Exception e) {
				e.printStackTrace();
			}	
	}
	
	@Override
	public void addNewDrugType (String drugName) {
		try {
			//Ids are chosen by the database
			Statement stmt = JDBCManager.c.createStatement(); 
			String sql = "INSERT INTO drugTypes type ";
			DrugType aDrugType = new DrugType(drugName);
			sql+= "VALUES ('" + aDrugType.getType() +")"; //que tiene que ser todo static dice
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	
	}
	
	@Override
	public void addNewDrug(Drug oneDrug) {
		try {
			//Ids are chosen by the database
			Statement stmt = JDBCManager.c.createStatement(); 
			String sql = "INSERT INTO drugs ";
			sql+= "VALUES ('" + oneDrug.getName() +"','" + oneDrug.getTreatmentDuration() + "','" +oneDrug.getPeriodBetweenDosis()+ "','" 
			+ oneDrug.getType()+ "','" + oneDrug.getDosis() + ")"; //que tiene que ser static dice
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public List getDrugTypes() {
		List<String> drugTypes = new ArrayList<String>();
		
		try {
		String sql = "SELECT type FROM drugTypes"; 			    
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { //like hasNext
			String weGetTheTypes = rs.getString("type");
			drugTypes.add (weGetTheTypes );
		}
			prep.close();
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
	}	
	return drugTypes;
}
	
	//	public Drug(String name, Integer treatmentDuration, Integer periodBetweenDosis, Integer drugType_id, Float dosis) {

	@Override
	public Drug searchDrugByName(String name) { //cast??
		
		String drugName=null;
		Integer drugPeriodBetweenDosis= null;
		Integer drugTreatmentDuration =null;
		Float drugDosis=null;
		Integer drugTypeId =null;;
		
		Drug searchedDrug= new Drug(drugName, drugTreatmentDuration, drugPeriodBetweenDosis, drugTypeId, drugDosis);
		
		
		try {
			String sql = "SELECT * FROM drugs WHERE name ?"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
					drugName=rs.getString("name");
					drugPeriodBetweenDosis= rs.getInt("periodBetweenDosis");
					drugTreatmentDuration =rs.getInt("treatmentDuration");
					drugDosis=rs.getFloat("dosis");
					drugTypeId = rs.getInt("drugType_id");
					
				   searchedDrug= new Drug(drugName, drugTreatmentDuration, drugPeriodBetweenDosis, drugTypeId, drugDosis );
				}
			
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
		}
		
		return searchedDrug;
	}
	
	
	@Override
	public Integer getIdsFromDrugs(String drugName){
	
		Integer drugId=null;
		try {
			String sql = "SELECT id FROM drugTypes WHERE type LIKE ?"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
				String stringDrug = rs.getString("id");
				drugId = Integer.parseInt(stringDrug);
			}
			
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		return drugId;
		
	}
	



	@Override
	public void deleteDrug(Drug drug) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Worker> searchWorkerByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
