package rehabilitationzoo.db.jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import rehabilitationzoo.db.ifaces.VetManager;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.FeedingType;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Illness;
import rehabilitationzoo.db.pojos.LightType;

public class VetSQL implements VetManager{
	
	
////////////DIAGNOSIS
	
	@Override
	public List<String> getAnimalTypesInZoo() {
		List<String> animalTypes = new ArrayList<String>();
		
		try {
		String sql = "SELECT type DISTINCT FROM animals"; 				//TODO DISTINCT esta bien??
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	//TODO esta bien??
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { //like hasNext
			String type = rs.getString("type");
			
			System.out.println(type);//esto por favor me lo quitas despues ;)
			animalTypes.add(type);
		}
		
			prep.close();
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	return animalTypes;
	
	}
	
	@Override
	public List<Animal> getAnimalsGivenType(String animalType) {
		List<Animal> animals = new ArrayList<Animal>();
			
			try {
			String sql = "SELECT * FROM animals WHERE type = ?"; 				//TODO esta bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			prep.setString(1, "%" + animalType + "%");							//TODO esta bien??
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
				int id = rs.getInt("id");
				Date enterDate = rs.getDate("enterDate");
				Integer habitat_id = rs.getInt("habitat_id");
				FeedingType feedingType = FeedingType.valueOf(rs.getString("feedingType"));
				Date lastBath = rs.getDate("lastBath");
				Date lastFed = rs.getDate("lastFed");
				Date deathDate = rs.getDate("deathDate");
				Date freedomDate = rs.getDate("freedomDate");
				String type = rs.getString("type");
				String name = rs.getString("name");
				
				System.out.println(name);//esto por favor me lo quitas despues ;)
				Animal animal = new Animal (id, enterDate, habitat_id, feedingType, lastBath, lastFed, deathDate, freedomDate, type, name);
				
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
	public List<Animal> getAnimalByNameAndType (String nameToSearch, String typeToSearch) {
		List<Animal> animals = new ArrayList<Animal>();
		
		try {
			String sql = "SELECT * FROM animals WHERE name LIKE ? AND WHERE type LIKE ?";
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + nameToSearch + "%");
			prep.setString(2, "%" + typeToSearch + "%");
			ResultSet rs = prep.executeQuery();

			while (rs.next()) { //like hasNext
				int id = rs.getInt("id");
				Date enterDate = rs.getDate("enterDate");
				Integer habitat_id = rs.getInt("habitat_id");
				FeedingType feedingType = FeedingType.valueOf(rs.getString("feedingType"));
				Date lastBath = rs.getDate("lastBath");
				Date lastFed = rs.getDate("lastFed");
				Date deathDate = rs.getDate("deathDate");
				Date freedomDate = rs.getDate("freedomDate");
				String type = rs.getString("type");
				String name = rs.getString("name");
				
				System.out.println(name);//esto por favor me lo quitas despues ;)
				Animal animal = new Animal (id, enterDate, habitat_id, feedingType, lastBath, lastFed, deathDate, freedomDate, type, name);
				
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
	public void prothesisInstallation(Boolean bol, Illness illness, Animal animal) {
		
		try {
			String sql = "UPDATE illnesses SET prothesis=?, WHERE id=?";
			PreparedStatement s = JDBCManager.c.prepareStatement(sql);
			s.setString(1, "%" + bol + "%");
			s.setString(1, "%" + getIllnessIdByName(illness) + "%");
			s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if (getNumberOfIllnessesAnAnimalHas() == 0) {
			
		LocalDate localToday = LocalDate.now(); //only way to add dates
		LocalDate localHealingDay = localToday.plusDays(30);
		String stringHealingDay = localHealingDay.toString();
		Date newDate = Date.valueOf(stringHealingDay);
		///TODO EEEPAA que solo se libere al bicho si no tiene alguna otra enfermedad -> MEJOR ESTO
		//o bien que si tiene otra enfermedad que se cambie la fecha de salida a indefinida
		
		//TODO YA: ahora me falta que cuando anhada una enfermedad tras la protesis se ponga la fecha de salida a null
			
		try {
			String sql = "UPDATE animals SET freedomDate=?, WHERE id=?";
			PreparedStatement s = JDBCManager.c.prepareStatement(sql);
			s.setString(1, "%" + newDate + "%");
			s.setString(1, "%" + getAnimalId(animal) + "%");
			s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		}
		
	}
	
	@Override
	public Integer getIllnessIdByName(Illness illness) throws SQLException{
		Integer id = null;
		
		try {
			String sql = "SELECT id FROM illnesses WHERE name LIKE ?"; 		//TODO esta bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			prep.setString(1, "%" + illness.getName() + "%");				//TODO esta bien??
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
				id = rs.getInt("id");
			}
			
		prep.close();
		rs.close();
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return id;
	}
	
	@Override
	public Integer getAnimalId (Animal animal) {
		Integer id = null;
			
			try {
				String sql = "SELECT id FROM animals WHERE name LIKE ?"; 		//TODO esta bien??
				PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
				prep.setString(1, "%" + animal.getName() + "%");				//TODO esta bien??
				ResultSet rs = prep.executeQuery();
				
				while (rs.next()) { //like hasNext
					id = rs.getInt("id");
				}
				
			prep.close();
			rs.close();
			
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		
		return id;
	}
	
	
	
	//MOVE public Integer getNumberOfIllnessesAnAnimalHas(); here when done
	
	@Override	
	public void addIllness(Illness illness) { //do we need a prepared Statement better to avoid injection? I think so bc it is insert
		try {
			//Id is chosen by the database
			Statement stmt = JDBCManager.c.createStatement(); 
			String sql = "INSERT INTO illnesses (name, quarantine, prothesis, drug_id )";
			sql+= "VALUES ('" + illness.getName() + "','" + illness.getQuarantine() + "','" 
			+ illness.getProthesis() + "','" + illness.getDrug() + ")";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void illnessQuarantine(Boolean bol, Illness illness) {
		
		try {
			String sql = "UPDATE illnesses SET quarantine=?, WHERE id=?";
			PreparedStatement s = JDBCManager.c.prepareStatement(sql);
			s.setString(1, "%" + bol + "%");
			s.setString(1, "%" + getIllnessIdByName(illness) + "%");
			s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Integer getTypeOfDrugId(String type) {
		Integer id = null;
			
			try {
				String sql = "SELECT id FROM drugTypes WHERE type LIKE ?"; 	//TODO esta bien??
				PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
				prep.setString(1, "%" + type + "%");						//TODO esta bien??
				ResultSet rs = prep.executeQuery();
				
				while (rs.next()) { //like hasNext
					id = rs.getInt("id");
				}
				
			prep.close();
			rs.close();
			
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
		return id;
	}
	
	
	
	
////////////CHECK
	
	@Override
	public List<String> getAllHabitatsNames(){
		List<String> habitatNames = new ArrayList<String>();
		
		try {
		String sql = "SELECT * DISTINCT FROM habitats"; 			    //TODO DISTINCT esta bien??
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	//TODO esta bien??
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { //like hasNext
			String name = rs.getString("name");
			habitatNames.add(name);
		}
		
			prep.close();
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	return habitatNames;
	
	}
	
	
	
	@Override
	public void reportAnimalState(Integer option, Animal animal) {
		LocalDate localToday = LocalDate.now(); //only way to add dates
		LocalDate localHealingDay = localToday.plusDays(30);
		String stringHealingDay = localHealingDay.toString();
		Date newDate = Date.valueOf(stringHealingDay);
		
		switch (option) {
		case 1: //release
			
			try {
				String sql = "UPDATE animal SET freedomDate=?, WHERE id=?";
				PreparedStatement s = JDBCManager.c.prepareStatement(sql);
				s.setString(1, "%" + newDate + "%");
				s.setString(1, "%" + animal.getId() + "%");
				s.executeUpdate();
				s.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case 2: //death
			
			try {
				String sql = "UPDATE animal SET deathDate=?, WHERE id=?";
				PreparedStatement s = JDBCManager.c.prepareStatement(sql);
				s.setString(1, "%" + newDate + "%");
				s.setString(1, "%" + animal.getId() + "%");
				s.executeUpdate();
				s.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		default:
			//Exception?
			break;		
		}
		
	}

	@Override
	public List<String> getAnimalTypesInAHabitat(Habitat habitat){
		List<String> types = new ArrayList<String>();
		
		try {
		String sql = "SELECT type FROM animals WHERE habitat_id = ?"; 			
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
		prep.setString(1, "%" + habitat.getId() + "%");
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { //like hasNext
			String type = rs.getString("type");
			
			types.add(type);
		}
		
			prep.close();
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	return types;
	
		
	}
	
	@Override
	public List<Animal> getAnimalsGivenHabitatAndType(String habitatName, String animalType) {
		List<Animal> animals = new ArrayList<Animal>();
			
			try {
			String sql = "SELECT * FROM animals WHERE type = ? AND habitat_id = ?"; 			
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			prep.setString(1, "%" + animalType + "%");	
			prep.setString(2, "%" + getHabitatIdByName(habitatName) + "%");
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
				int id = rs.getInt("id");
				Date enterDate = rs.getDate("enterDate");
				Integer habitat_id = rs.getInt("habitat_id");
				FeedingType feedingType = FeedingType.valueOf(rs.getString("feedingType"));
				Date lastBath = rs.getDate("lastBath");
				Date lastFed = rs.getDate("lastFed");
				Date deathDate = rs.getDate("deathDate");
				Date freedomDate = rs.getDate("freedomDate");
				String type = rs.getString("type");
				String name = rs.getString("name");
				
				System.out.println(name);//esto por favor me lo quitas despues ;)
				Animal animal = new Animal (id, enterDate, habitat_id, feedingType, lastBath, lastFed, deathDate, freedomDate, type, name);
				
				animals.add(animal);
			}
			
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
		return animals;
		
	}
	
	
	
	
/////////////////////////////////////////////////
/////////////////////////////////////////////////

	//Metodos que incluir en el codigo que aun no estan:

	
	@Override
	public Integer getHabitatIdByName(String habitatName) throws SQLException{
		Integer id = null;
		
		try {
			String sql = "SELECT id FROM habitats WHERE name LIKE ?"; 		//TODO esta bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			prep.setString(1, "%" + habitatName + "%");						//TODO esta bien??
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
				id = rs.getInt("id");
			}
			
		prep.close();
		rs.close();
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return id;
	}
	
	@Override
	public List<Animal> getAnimalsInHabitat(String habitatNameToSearch) throws SQLException {
		List<Animal> animals = new ArrayList<Animal>();
		
		try {
		String sql = "SELECT * FROM animals WHERE habitat_id = ?"; 				//TODO esta bien??
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
		prep.setString(1, "%" + getHabitatIdByName(habitatNameToSearch) + "%");	//TODO esta bien??
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { //like hasNext
			int id = rs.getInt("id");
			Date enterDate = rs.getDate("enterDate");
			Integer habitat_id = rs.getInt("habitat_id");
			FeedingType feedingType = FeedingType.valueOf(rs.getString("feedingType"));
			Date lastBath = rs.getDate("lastBath");
			Date lastFed = rs.getDate("lastFed");
			Date deathDate = rs.getDate("deathDate");
			Date freedomDate = rs.getDate("freedomDate");
			String type = rs.getString("type");
			String name = rs.getString("name");
			
			System.out.println(name);//esto por favor me lo quitas despues ;)
			Animal animal = new Animal (id, enterDate, habitat_id, feedingType, lastBath, lastFed, deathDate, freedomDate, type, name);
			
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
	public List<String> getDrugTypes() {
		List<String> drugTypes = new ArrayList<String>();
			
			try {
			String sql = "SELECT type DISTINCT FROM drugTypes"; 			//TODO DISTINCT esta bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	//TODO esta bien??
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
				String type = rs.getString("type");
				
				System.out.println(type);//esto por favor me lo quitas despues ;)
				drugTypes.add(type);
			}
			
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
		return drugTypes;
		
	}

	@Override
	public List<Drug> getDrugByNameAndType(String nameOfDrug, String typeOfDrug) {
		List<Drug> drugs = new ArrayList<Drug>();
			
			try {
				String sql = "SELECT * FROM drugs WHERE name LIKE ? AND WHERE type LIKE ?";
				PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
				prep.setString(1, "%" + nameOfDrug + "%");
				prep.setString(2, "%" + getTypeOfDrugId(typeOfDrug) + "%");
				ResultSet rs = prep.executeQuery();
	
				while (rs.next()) { //like hasNext
					int id = rs.getInt("id");
					String name = rs.getString("name");
					Integer treatmentDuration = rs.getInt("treatmentDuration");
					Integer periodBetweenDosis = rs.getInt("periodBetweenDosis");
					Integer drugType_id = rs.getInt("drugType_id");
					Integer dosis = rs.getInt("dosis");					
					
					System.out.println(name);//esto por favor me lo quitas despues ;)
					Drug drug = new Drug(id, name, treatmentDuration, periodBetweenDosis, drugType_id, dosis);
					
					drugs.add(drug);
				}
				
				prep.close();
				rs.close();
			
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
			return drugs;
	}


	
	//TODO al ser una n-n no se muy bien como hacer estos metodos:
	@Override 
	public List<Illness> getAnimalIllnesses() { 
	  List<Illness> illnesses = new ArrayList<Illness>();
	  
	  try { //TODO no se hacer para coger todas las enfermedades de un animal: Java directamente?
	  String sql = "SELECT * FROM illnesses"; 
	  PreparedStatement prep = JDBCManager.c.prepareStatement(sql); ResultSet rs = prep.executeQuery();
	  
	  while(rs.next()) { 
		  int drugId = rs.getInt("id"); String name = rs.getString("name"); 
		  Boolean quarantine = rs.getBoolean("quaratine");
		  Boolean prothesis = rs.getBoolean("prothesis");
	  
		  Illness illness = new Illness(drugId, name, quarantine, prothesis);
		  illnesses.add(illness); } prep.close(); rs.close();
	  
	  }catch(Exception ex) { 
		  ex.printStackTrace(); 
	  }
	  
	  return illnesses;
	  
	  }
	
	//TODO este tampoco
	@Override
	public Integer getNumberOfIllnessesAnAnimalHas() {
		Integer numberOfIllnesses = null;
		
		try {
			String sql = "SELECT type COUNT FROM illnesses WHERE ????"; 	//TODO COUNT esta bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	//TODO esta bien??
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) { //like hasNext
				numberOfIllnesses = Integer.parseInt(rs.getString("xxx")); 
			}
			
				prep.close();
				rs.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		
		return numberOfIllnesses;
		
	}

	//TODO este tampoco
	@Override
	public Illness setIllnessOnAnimal(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//TODO rt
	@Override
	public void drugPrescription(Drug drug) {
		// TODO Auto-generated method stub
		
	}


}
