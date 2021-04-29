package rehabilitationzoo.db.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Illness;
import rehabilitationzoo.db.pojos.LightType;
import rehabilitationzoo.db.pojos.Worker;

public class JDBCManager implements rehabilitationzoo.db.ifaces.DBManager {
	
	//private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	//sysout("Date of Birth: yyyy-MM-dd");
	//String dob = Utils.readLine();
	//LocalDate dobDate = LocalDate.parse(dob, formatter);
	
	private static Connection c;

	
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/management.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			this.createTables();
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect(){
		try {
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void createTables() { //we shouldn't have a main here -> build an interface
		
		try {			
			//Open database connection
			// Create tables: begin	
			
			Statement stmt1 = c.createStatement();
			String sql1 = "CREATE TABLE habitats "
					   + "(id			INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " name			TEXT	NOT NULL	UNIQUE, " 
					   + " lastCleaned	DATE	NOT NULL, "
					   + " waterLevel	FLOAT	NOT NULL, "
					   + " temperature	FLOAT	NOT NULL, "
					   + " light		ENUM	NOT NULL )";
			stmt1.executeUpdate(sql1);
			stmt1.close();
			
			Statement stmt2 = c.createStatement();
			String sql2 = "CREATE TABLE groundTypes "
					   + "(id			INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " habitat_id 	INTEGER	NOT NULL	REFERENCES habitats(id), "
					   + " type			TEXT	NOT NULL )";
			stmt2.executeUpdate(sql2);
			stmt2.close();
			
			Statement stmt3 = c.createStatement();
			String sql3 = "CREATE TABLE animals "
					   + "(id			INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " enterDate	DATE	NOT NULL, "
					   + " habitat_id 	INTEGER NOT NULL 	REFERENCES habitats(id), "
					   + " foodPeriod	INTEGER	NOT NULL, "
					   + " feedingType	ENUM	NOT NULL, "
					   + " lastBath		DATE	NOT NULL, "
					   + " lastFed		DATE	NOT NULL, "
					   + " deathDate	DATE, "
					   + " freedomDate	DATE, "
					   + " type			STRING	NOT NULL, "
					   + " name			STRING	NOT NULL	UNIQUE)";
			stmt3.executeUpdate(sql3);
			stmt3.close();
			
			Statement stmt4 = c.createStatement();
			String sql4 = "CREATE TABLE workers "
					   + "(id			INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " name			TEXT	NOT NULL	UNIQUE, "
					   + " lastName 	TEXT	NOT NULL, "
					   + " hireDate		DATE	NOT NULL, "
					   + " salary		FLOAT	NOT NULL, "
					   + " workerType	ENUM	NOT NULL )";
			stmt4.executeUpdate(sql4);
			stmt4.close();
			
			Statement stmt6 = c.createStatement();
			String sql6 = "CREATE TABLE drugTypes "
					   + "(id	INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " type	TEXT	NOT NULL )"; 
			stmt6.executeUpdate(sql6);
			stmt6.close();
			
			Statement stmt5 = c.createStatement();
			String sql5 = "CREATE TABLE drugs "
					   + "(id					INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " name					TEXT	NOT NULL	UNIQUE,"
					   + " treatmentDuration	INTEGER	NOT NULL, "
					   + " periodBetweenDosis	INTEGER	NOT NULL, "
					   + " drugType_id 			INTEGER NOT NULL	REFERENCES drugTypes(id), "
					   + " dosis 				INTEGER	NOT NULL)";					   
			stmt5.executeUpdate(sql5);
			stmt5.close();
			
			Statement stmt7 = c.createStatement();
			String sql7 = "CREATE TABLE illnesses "
					   + "(id			INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " name			STRING	NOT NULL	UNIQUE, "
					   + " quarantine	BOOLEAN	NOT NULL, "
					   + " prothesis	BOOLEAN	NOT NULL, "
					   + " drug_id 		INTEGER	NOT NULL	REFERENCES Drug(id) )";
					   
			stmt7.executeUpdate(sql7);
			stmt7.close();
			
			
			Statement stmt8 = c.createStatement();
			String sql8 = "CREATE TABLE animal_drug "
					   + "(drug_id	INTEGER	REFERENCES drug(id), "
					   + " animal_id	INTEGER	REFERENCES animal(id), "
					   + " PRIMARY KEY (drug_id, animal_id) )";
			stmt8.executeUpdate(sql8);
			stmt8.close();
			
			Statement stmt9 = c.createStatement();
			String sql9 = "CREATE TABLE worker_animal "
					   + "(worker_id	INTEGER REFERENCES worker(id), "
					   + " animal_id	INTEGER REFERENCES animal(id), "
					   + " PRIMARY KEY (worker_id, animal_id) )";
			stmt9.executeUpdate(sql9);
			stmt9.close();
			
			Statement stmt10 = c.createStatement(); 
			String sql10 = "CREATE TABLE animal_illness "
					   + "(animal_id	INTEGER REFERENCES animal(id), "
					   + " illness_id	INTEGER REFERENCES illness(id), "
					   + " PRIMARY KEY (illness_id, animal_id) )";
			stmt10.executeUpdate(sql10);
			stmt10.close();
			// Create table: end
			
			// - Set initial values for the Primary Keys
			// - Don't try to understand this until JPA is explained
			// This is usually not needed, since the initial values
			// are set when the first row is inserted, but since we
			// are using JPA and JDBC in the same project, and JPA
			// needs an initial value, we do this.
			//Statement stmtSeq = c.createStatement();
			//String sqlSeq = "INSERT INTO sqlite_sequence (name, seq) VALUES ('departments', 1)";
			//stmtSeq.executeUpdate(sqlSeq);
			//sqlSeq = "INSERT INTO sqlite_sequence (name, seq) VALUES ('employees', 1)";
			//stmtSeq.executeUpdate(sqlSeq);
			//sqlSeq = "INSERT INTO sqlite_sequence (name, seq) VALUES ('reports', 1)";
			//stmtSeq.executeUpdate(sqlSeq);
			//stmtSeq.close(); 
			
			// Close database connection
		}  catch (SQLException e) {
	//	if (!e.getMessage().contains("already exists")) {
				e.printStackTrace();
	//		}
		}
		
	}

	
	@Override
	public void addAnimal(Animal animal) { //do we need a prepared Statement better to avoid injection? I think so bc it is insert
		try {
			//Id is chosen by the database
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO animals (enterDate,habitat_id,foodPeriod,feedingType,lastBath,lastFed,deathDate,freedomDate,type,name)";
			sql+= "VALUES ('" + animal.getEnterDate() + "','" + animal.getHabitat() + "','" + animal.getFoodPeriod() + "','" 
			+ animal.getFeedingType() + "','" + animal.getLastBath() + "','" + animal.getLastFed() + "','" + animal.getFreedomDate() + "','"
			+ animal.getType() +  "','" + animal.getName() + ")";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**@Override
	public void addAnimal(Animal animal) throws SQLException { //do we need a prepared Statement better to avoid injection? I think so bc it is an insert
		//Id is chosen by the database
		String sql = "INSERT INTO animals (name) VALUES (?)"; //the ? filters any SQL language
		PreparedStatement prep = c.prepareStatement(sql);
		prep.setObject(0, animal); //Es asi??
		prep.executeUpdate();
		prep.close();
	}*/
	

	@Override
	public Integer getHabitatIdByName(String habitatName) throws SQLException {
		Statement stmt = c.createStatement();
		String sql = "SELECT id FROM habitats WHERE name LIKE '%" + habitatName + "%'";
		//System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql);
		Integer id = null;
		while (rs.next()) { //like hasNext
			id = rs.getInt("id");
		}
		rs.close();
		stmt.close();
		
		return id;
	}
	
	
	@Override
	public void printAnimalsInHabitat(String habitatName) throws SQLException {
		Statement stmt = c.createStatement();
		String sql = "SELECT * FROM animals WHERE habitat_id = '%" + getHabitatId(habitatName) + "%'";
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) { //like hasNext
			int id = rs.getInt("id");
			String name = rs.getString("name");
			
			System.out.println(name);//esto por favor me lo quitas despues ;)
			Date lastCleaned = rs.getDate("lastCleaned");
			Float waterLevel = rs.getFloat("waterLevel");
			Integer temperature = rs.getInt("temperature");
			LightType light = LightType.valueOf(rs.getString("light"));
			new Animal()
			//habitat = new Habitat (id, name, lastCleaned,  waterLevel, temperature, light);
			//this is wrong //System.out.println(habitat.getAnimals());
		}
		rs.close();
		stmt.close();
	}
	

	@Override
	public List<Animal> searchAnimalByName(String name) {
		List<Animal> animals = new ArrayList<Animal>();
		
		try {
			String sql = "SELECT * FROM animals WHERE name LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1,"%"+name+"%");
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				int animalId = rs.getInt("id");
				String animalName = rs.getString("name");
				
				Animal animal = new Animal(animalId, animalName);
				animals.add(animal);
			}
			prep.close();
			rs.close();
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	return animals;
	}
		
		
	@Override
	public void feedAnimal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batheAnimal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanHabitat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillUpWaterTank() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drugAdministrationToAnimal(Animal animal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drugPrescription(Drug drug) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean prothesisInstallation(Boolean bol) {
		// TODO change prhothesis to true and releaseDate to 30 days from today
		return bol;
	}


	@Override
	public void returAnimalToTheWilderness(Animal animal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void markAnimalAsDeceased(Animal animal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hireWorker(Worker worker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireWorker(Worker worker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyWorker(Worker worker, Integer salary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewDrug(Drug drug) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDrug(Drug drug) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public Animal getAnimal(String type, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Illness setIllness(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getTypeOfDrugId(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getDrug(String name, Integer typeOfDrug_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void illnessQuarantine(Boolean bol, Illness illness) {
		// TODO how to know if quarantine is becoming the same value of bol
		// TODO how will the sql know which id it needs
		
		try {
			String sql = "UPDATE quarantine SET quarantine=?, WHERE id=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setBoolean(1, illness.getQuarantine());
			s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void reportAnimalState(Integer option) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Worker> searchWorkerByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Drug> searchDrugByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Worker getWorker(String name, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printAnimalTypes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printAnimalNamesGivenType(String animalType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printDrugTypes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Illness> getIllnesses() {
		List<Illness> illnesses = new ArrayList<Illness>();
		
		try { //TODO no se hacer para coger todas las enfermedades de un animal: Java directamente?
			String sql = "SELECT * FROM illnesses";
			PreparedStatement prep = c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				int drugId = rs.getInt("id");
				String name = rs.getString("name");
				Boolean quarantine = rs.getBoolean("quaratine");
				Boolean prothesis = rs.getBoolean("prothesis");
				
				Illness illness = new Illness(drugId, name, quarantine, prothesis);
				illnesses.add(illness);
			}
			prep.close();
			rs.close();
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	return illnesses;
		
	}

	@Override
	public void quarantineDays() {
		// TODO Auto-generated method stub
		
	}


	
}