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
import utils.KeyboardInput;

public class AdministratorSQL implements AdministratorManager{


    List<DrugType> listOfDrugTypes = new ArrayList<DrugType>();
    
	
	 private Connection c;
	    //in all classes that uses a connection 
	    
		public void AdministratorSQLConnection (Connection c) {
			this.c=c;
		}

		
		//ANIMALS METHODS 
		
	@Override
	public void addAnimal(Animal animal) throws SQLException{ //do we need a prepared Statement better to avoid injection? I think so bc it is insert
		try {
			
			String sql = "INSERT INTO animals (enterDate,lastBath,lastFed,deathDate,freedomDate,name)";
			sql+= "VALUES ('" + animal.getEnterDate() + "','"  + animal.getLastBath() + "','" + animal.getLastFed() + "','" 
							  + animal.getDeathDate() + "','"  + animal.getFreedomDate() + "','" + animal.getName() + ")";

			PreparedStatement pstmt = JDBCManager.c.prepareStatement(sql); 
				
			System.out.println(sql);
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
			s.setString(2, "%" + animal.getLastBath() + "%");
			s.setString(3, "%" + animal.getLastFed() + "%");
			s.setString(4, "%" + animal.getLastDrug() + "%");
			s.setString(5, "%" + animal.getDeathDate() + "%");
			s.setString(6, "%" + animal.getFreedomDate() + "%");
			
			System.out.println(sql);
			s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
			
	} //SOLO QUIERO MODIFICAR ESTO, SERIA ASI NO?
	
	
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
			
			Animal animal = new Animal (id, enterDate, habitat_id, lastBath, lastFed,lastDrug, deathDate, freedomDate, type_id , name);
			
			System.out.println(sql);
			prep.close();
			rs.close();
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
			
	}
		
		
	//ANIMAL TYPES
	
	
	@Override
	public void introducingAnimalsTypes (AnimalType animalType) throws SQLException{ 
		//try {
			Statement stmt = JDBCManager.c.createStatement(); 
			String sql = "INSERT INTO animals_characteristics(type,feedingType)";
			sql+= "VALUES ('" + animalType.getType() + "','" + animalType.getWhatDoYouEat() + ")";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			
		//} catch(Exception e) {
		//	e.printStackTrace();
		//}
	}
	
	
	@Override
	public List<String> getAnimalTypesByName() {//PQ NO ME DEJA PONER throws SQLException
		List<String> typesOfAnimals = new ArrayList<String>();
		
		try {
			String sql = "SELECT type FROM animals_characteristics"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { 
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
				System.out.println("Search finished.");// Retrieve data: end
				
				prep.close();// Close database connection
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		
	return typesOfAnimals;	
	}	
	
	/*prep.setString(1, "%" + habitatName + "%");						// esta bien??
	ResultSet rs = prep.executeQuery();
	
	while (rs.next()) { //like hasNext
		id = rs.getInt("id");
	}*/
	
	
	@Override
	public List<String> getAnimalTypesById(String name)  {//REVISAR
		List<String> typesOfAnimals = new ArrayList<String>();
		
		
		try {
			String sql = "SELECT id FROM animals_characteristics WHERE type LIKE ? "; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
				String type = rs.getString("type");
				typesOfAnimals.add(type); //ESTO??
			}
			
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		
	return typesOfAnimals;	
	}
	//HABITATS
	
	@Override
	public void addHabitat(Habitat habitat) /*throws AdminExceptions*/ {
		try {
			String sql = "INSERT INTO habitats (name, lastCleaned, waterTank, temperature, light)"; 
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery(); // SELECT?? no es solo para consultas?
			
			prep.setString(1, "%" + habitat.getName() + "%" );
			prep.setString(2, "%" + habitat.getLastCleaned( )+ "%" );
			prep.setString(3, "%" + habitat.getWaterTank() + "%" );
			prep.setString(4, "%" + habitat.getTemperature() + "%" );
			prep.setString(5, "%" + habitat.getLight() + "%" );
			
			
				/*Date lastCleaned= rs.getDate("lastCleaned");
				Date waterTank= rs.getDate("waterTank");
				Integer temperature = rs.getInt("temperature");
				String light = rs.getString("light");*/
			
			    prep.executeUpdate();
				prep.close();
				rs.close();
				
			}catch( Exception ex) {
				ex.printStackTrace();
				//if(java.sql.SQLException.) {
				//	throw new AdminExceptions(AdminExceptions.AdminErrors.SQLEXCEPTION);
				//}
				//System.out.println("array containing all of the exceptions that were suppressed, typically by the try-with-resources statement, in order to deliver this exception: ");
				//ex.getSuppressed();
				}
		
	}
	
	
	public List<String> weListHabitats()  {
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
	
	//DRUG TYPES 
	
	@Override
	public void addNewDrugType (String drugName, float dosis) {
		try {
			DrugType aDrugType = new DrugType(drugName, dosis);
		    String sql= "INSERT INTO drugTypes (type,dosis) VALUES ('" + aDrugType.getType() +"','" + aDrugType.getDosis() +"')"; 
		    PreparedStatement stmt = JDBCManager.c.prepareStatement(sql); 
			
		    listOfDrugTypes.add(aDrugType);
		    
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
}
	
public void listDrugTypes() {
		try {	
			
		String sql = "SELECT * FROM drugTypes "; 			
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
		
		ResultSet rs = prep.executeQuery();
		System.out.println(sql);
		while (rs.next()) { //like hasNext
			int id = rs.getInt("id");
			String type = rs.getString("type");
			Float dosis = rs.getFloat("dosis");
			
			//DrugType drugs = new DrugType (id, type, dosis);
			
			
			for(int i=0; i<listOfDrugTypes.size(); i++) {
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
	
	
	
	//DRUGS 
	
	@Override
	public void addNewDrug(Drug oneDrug) {
		try {
			//Ids are chosen by the database
			Statement stmt = JDBCManager.c.createStatement(); 
			String sql = "INSERT INTO drugs ";
			sql+= "VALUES ('" + oneDrug.getName() +"','" + oneDrug.getTreatmentDuration() + "','" +oneDrug.getPeriodBetweenDosis()+ "','" 
			+ oneDrug.getType()+ ")"; 
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	//	public Drug(String name, Integer treatmentDuration, Integer periodBetweenDosis, Integer drugType_id, Float dosis) {

	@Override
	public Drug searchDrugByName(String name) { //cast??
		
		String drugName=null;
		Integer drugPeriodBetweenDosis= null;
		Integer drugTreatmentDuration =null;
		Float drugDosis=null;
		Integer drugTypeId =null;;
		
		Drug searchedDrug= new Drug(drugName, drugTreatmentDuration, drugPeriodBetweenDosis, drugTypeId);
		
		
		try {
			String sql = "SELECT * FROM drugs WHERE name ?"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
					drugName=rs.getString("name");
					drugPeriodBetweenDosis= rs.getInt("periodBetweenDosis");
					drugTreatmentDuration =rs.getInt("treatmentDuration");
					drugTypeId = rs.getInt("drugType_id");
					
				   searchedDrug= new Drug(drugName, drugTreatmentDuration, drugPeriodBetweenDosis, drugTypeId );
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



	@Override
	public List<String> getDrugTypes() {
		// TODO Auto-generated method stub
		return null;
	}






}
