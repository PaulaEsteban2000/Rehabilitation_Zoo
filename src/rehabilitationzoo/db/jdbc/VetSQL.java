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
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Illness;
import rehabilitationzoo.db.pojos.LightType;

public class VetSQL implements VetManager {
	
	
	//0. SQL HELPER METHODS
	
	@Override
	public Integer getIllnessIdByName(Illness illness) throws SQLException {
		Integer id = null;

		try {
			String sql = "SELECT id FROM illnesses WHERE name LIKE ?"; // esta bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + illness.getName() + "%"); // esta bien??
			ResultSet rs = prep.executeQuery();

			while (rs.next()) { // like hasNext
				id = rs.getInt("id");
			}

			prep.close();
			rs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return id;
	}

	@Override
	public Integer getAnimalId(Animal animal) {
		Integer id = null;

		try {
			String sql = "SELECT id FROM animals WHERE name LIKE ?"; // esta bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + animal.getName() + "%"); // esta bien??
			ResultSet rs = prep.executeQuery();

			while (rs.next()) { // like hasNext
				id = rs.getInt("id");
			}

			prep.close();
			rs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return id;
	}

	@Override
	public Integer getHabitatIdByName(String habitatName) throws SQLException {
		Integer id = null;

		try {
			String sql = "SELECT id FROM habitats WHERE name LIKE ?"; 
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + habitatName + "%"); 
			ResultSet rs = prep.executeQuery();

			while (rs.next()) { // like hasNext
				id = rs.getInt("id");
			}

			prep.close();
			rs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return id;
	}

	
	//1. ANIMAL DIAGNOSIS - only on arrival
		
		//a. checkIfAnimalsInHabitat
	
			@Override
			public List<Animal> getAnimalsInHabitat(String habitatNameToSearch) throws SQLException { // Tambien lo utiliza zoo
																										// keeper
				List<Animal> animals = new ArrayList<Animal>();

				try {
					String sql = "SELECT * FROM animals WHERE habitat_id = ?"; // esta bien??
					PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
					prep.setInt(1,getHabitatIdByName(habitatNameToSearch)); // esta bien??
					ResultSet rs = prep.executeQuery();

					while (rs.next()) { // like hasNext
						int id = rs.getInt(1);
						Date enterDate = Date.valueOf(rs.getString("enterDate"));
						Integer habitat_id = rs.getInt("habitat_id");
						Date lastBath = Date.valueOf(rs.getString("lastBath"));
						Date lastFed = Date.valueOf(rs.getString("lastFed"));
						Date lastDrug = Date.valueOf(rs.getString("lastDrug"));
						
						Date deathDate = null;
						if (rs.getString("deathDate") == null) {
						} else {
							String deathDateString = rs.getString("deathDate");
							deathDate = Date.valueOf(deathDateString);
						}
						
						Date freedomDate = null;
						if (rs.getString("freedomDate") == null) {
						} else {
							String freedomDateString = rs.getString("freedomDate");
							freedomDate = Date.valueOf(freedomDateString);
						}

						int type_id = rs.getInt("type_id");
						String name = rs.getString(10);

						Animal animal = new Animal(id, enterDate, habitat_id, lastBath, lastFed, lastDrug, deathDate,
						freedomDate, type_id, name);
						animals.add(animal);
					}

					prep.close();
					rs.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				return animals;
			}

		//b. askForAnimalForDiagnosis
			
			@Override
			public List<Animal> getAnimalByNameAndType(String nameToSearch, String typeToSearch) {
				List<Animal> animals = new ArrayList<Animal>();

				try {
					String sql = "SELECT * FROM animals AS a JOIN animals_characteristics AS ac ON ac.id = a.type_id "
							+ "WHERE a.name LIKE ? AND ac.type LIKE ?";
					PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
					prep.setString(1, "%" + nameToSearch + "%");
					prep.setString(2, "%" + typeToSearch + "%");
					ResultSet rs = prep.executeQuery();

					while (rs.next()) { // like hasNext
						int id = rs.getInt(1);
						Date enterDate = Date.valueOf(rs.getString("enterDate"));
						Integer habitat_id = rs.getInt("habitat_id");
						Date lastBath = Date.valueOf(rs.getString("lastBath"));
						Date lastFed = Date.valueOf(rs.getString("lastFed"));
						Date lastDrug = Date.valueOf(rs.getString("lastDrug"));

						Date deathDate = null;
						if (rs.getString("deathDate") == null) {
						} else {
							String deathDateString = rs.getString("deathDate");
							deathDate = Date.valueOf(deathDateString);
						}
						
						Date freedomDate = null;
						if (rs.getString("freedomDate") == null) {
						} else {
							String freedomDateString = rs.getString("freedomDate");
							freedomDate = Date.valueOf(freedomDateString);
						}
						
						int type_id = rs.getInt("type_id");
						String name = rs.getString(10);

						Animal animal = new Animal(id, enterDate, habitat_id, lastBath, lastFed, lastDrug, deathDate,
						freedomDate, type_id, name);

						animals.add(animal);
					}

					prep.close();
					rs.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				return animals;
			}
			
		//c. firstDiagnosisSubMenu
			
			//I. PROTHESIS
			
				@Override
				public List<String> getAllProthesisExistent() {
					List<String> prothesisNames = new ArrayList<String>();

					try {
						String sql = "SELECT * FROM illnesses WHERE name LIKE ?";
						PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
						prep.setString(1, "%Prothesis%");
						ResultSet rs = prep.executeQuery();

						while (rs.next()) { // like hasNext
							int id = rs.getInt("id");
							String name = rs.getString("name");
							Boolean quarantine = rs.getBoolean("quarantine");
							Boolean prothesis = rs.getBoolean("prothesis");
							int drug_id = rs.getInt("drug_id");

							Illness proth = new Illness(id, name, quarantine, prothesis, drug_id);
							prothesisNames.add(proth.getName());
						}

						prep.close();
						rs.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return prothesisNames;
				}
				
				@Override
				public Illness addIllness(Illness illness) {
					Illness illness2 = null;
					
					try {
						// Id is chosen by the database
						Statement stmt = JDBCManager.c.createStatement();
						String sql = "INSERT INTO illnesses (name, quarantine, prothesis, drug_id)";
						sql += "VALUES ('" + illness.getName() + "','" + illness.getQuarantine() + "','" + illness.getProthesis() + "'," + illness.getDrugId() + ")";
						stmt.executeUpdate(sql);
						
						stmt.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					try {
						String sql = "SELECT * FROM illnesses WHERE name LIKE ?"; 
						PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
						prep.setString(1, "%" + illness.getName() + "%"); 
						ResultSet rs = prep.executeQuery();

						while (rs.next()) { 
							int id = rs.getInt("id");
							String name = rs.getString("name");
							Boolean quarantine = rs.getBoolean("quarantine");
							Boolean prothesis = rs.getBoolean("prothesis");
							int drug_id = rs.getInt("drug_id");

							illness2 = new Illness(id, name, quarantine, prothesis, drug_id);
						}

						prep.close();
						rs.close();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					return illness2;
				}
				
				@Override
				public Illness getIllnessByName(String illnessName) {
					Illness illness = null;
					
					try {
						String sql = "SELECT * FROM illnesses WHERE name LIKE ?";
						PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
						prep.setString(1, "%" + illnessName + "%");
						ResultSet rs = prep.executeQuery();

						while (rs.next()) { // like hasNext
							int id = rs.getInt("id");
							String name = rs.getString("name");
							Boolean quarantine = rs.getBoolean("quarantine");
							Boolean prothesis = rs.getBoolean("prothesis");
							int drug_id = rs.getInt("drug_id");

							illness = new Illness(id, name, quarantine, prothesis, drug_id);
						}

						prep.close();
						rs.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return illness;
					
				}
				
				@Override
				public List<Illness> getAnimalIllnesses(Animal animal) {
					List<Illness> illnesses = new ArrayList<Illness>();

					try {
						String sql = "SELECT * FROM illnesses AS i JOIN animal_illness AS ai ON i.id = ai.illness_id "
								+ "WHERE ai.animal_id LIKE ?";
						
						PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
						prep.setInt(1, animal.getId());
						ResultSet rs = prep.executeQuery();

						while (rs.next()) {
							int id = rs.getInt(1);
							String name = rs.getString("name");
							Boolean quarantine = rs.getBoolean("quarantine");
							Boolean prothesis = rs.getBoolean("prothesis");
							int drugId = rs.getInt("drug_id");

							Illness illness = new Illness(id, name, quarantine, prothesis, drugId);
							illnesses.add(illness);
						}
						prep.close();
						rs.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return illnesses;

				}
				
				@Override
				public void setIllnessOnAnimal(Illness illness, Animal animal) {

					try {
						String sql = "INSERT INTO animal_illness VALUES(?,?)";
						PreparedStatement s = JDBCManager.c.prepareStatement(sql);
						s.setInt(1, animal.getId());
						s.setInt(2, illness.getId());
						s.executeUpdate();
						s.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				@Override
				public void prothesisInstallation(Illness illness, Animal animal) {

					try {
						String sql = "UPDATE illnesses SET prothesis=? WHERE id=?";
						PreparedStatement s = JDBCManager.c.prepareStatement(sql);
						s.setBoolean(1, true);
						s.setInt(2, getIllnessIdByName(illness));
						s.executeUpdate();
						s.close();

					} catch (Exception e) {
						e.printStackTrace();
					}

					if (getNumberOfIllnessesAnAnimalHas(animal) == 1) {

						LocalDate localToday = LocalDate.now(); // only way to add dates
						LocalDate localHealingDay = localToday.plusDays(30);
						Date newDate = Date.valueOf(localHealingDay);

						// TODO YA: ahora me falta que cuando anhada una enfermedad tras la protesis se
						// ponga la fecha de salida a null

						if (this.getAnimalIllnesses(animal).size() == 1) {
							
								try {
								String sql = "UPDATE animals SET freedomDate=? WHERE id=?";
								PreparedStatement s = JDBCManager.c.prepareStatement(sql);
								s.setString(1, "" + newDate + "");
								s.setInt(2, getAnimalId(animal));
								s.executeUpdate();
								s.close();
				
							} catch (Exception e) {
								e.printStackTrace();
							}
								
						}
						
						
					}

				}
			
			//II. OTHER - illnessesInputSubMenu
				
				@Override
				public Integer getNumberOfIllnessesAnAnimalHas(Animal animal) {
					List<Integer> illnesses = new ArrayList<Integer>();

					try {
						String sql = "SELECT illness_id FROM animal_illness WHERE animal_id LIKE ?"; // COUNT

						PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
						prep.setInt(1, animal.getId());
						ResultSet rs = prep.executeQuery();

						while (rs.next()) { // like hasNext
							Integer id = rs.getInt("illness_id");

							illnesses.add(id);
						}

						prep.close();
						rs.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return illnesses.size();

				}
				
				@Override
				public List<String> getDrugTypes() {
					List<String> drugTypes = new ArrayList<String>();

					try {
						String sql = "SELECT DISTINCT type FROM drugTypes"; // DISTINCT esta bien??
						PreparedStatement prep = JDBCManager.c.prepareStatement(sql); // esta bien??
						ResultSet rs = prep.executeQuery();

						while (rs.next()) { // like hasNext
							String type = rs.getString("type");

							System.out.println(type);// esto por favor me lo quitas despues ;)
							drugTypes.add(type);
						}

						prep.close();
						rs.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return drugTypes;

				}
				
				@Override
				public List<Drug> getDrugsGivenType(String drugName) {
					List<Drug> drugs = new ArrayList<Drug>();

					try {
						String sql = "SELECT * FROM drugs AS d JOIN drugTypes AS dt ON dt.id = d.drugType_id WHERE dt.type LIKE ?";
						PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
						prep.setString(1, "%" + drugName + "%");
						ResultSet rs = prep.executeQuery();

						while (rs.next()) { // like hasNext
							int id = rs.getInt(1);
							String name = rs.getString("name");
							Integer treatmentDuration = rs.getInt("treatmentDuration");
							Integer periodBetweenDosis = rs.getInt("periodBetweenDosis");
							Integer drugType_id = rs.getInt("drugType_id");

							Drug drug = new Drug(id, name, treatmentDuration, periodBetweenDosis, drugType_id);
							drugs.add(drug);
						}

						prep.close();
						rs.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return drugs;
				}
				
				@Override
				public List<Drug> getDrugByNameAndType(String nameOfDrug, String typeOfDrug) {
					List<Drug> drugs = new ArrayList<Drug>();

					try {
						String sql = "SELECT * FROM drugs AS d JOIN drugTypes AS dt ON d.drugType_id = dt.id "
								+ "WHERE d.name LIKE ? AND dt.type LIKE ?";
						PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
						prep.setString(1, "%" + nameOfDrug + "%");
						prep.setString(2, "%" + typeOfDrug+ "%");
						ResultSet rs = prep.executeQuery();

						while (rs.next()) { // like hasNext
							int id = rs.getInt(1);
							String name = rs.getString("name");
							Integer treatmentDuration = rs.getInt("treatmentDuration");
							Integer periodBetweenDosis = rs.getInt("periodBetweenDosis");
							Integer drugType_id = rs.getInt("drugType_id");

							Drug drug = new Drug(id, name, treatmentDuration, periodBetweenDosis, drugType_id);

							drugs.add(drug);
						}

						prep.close();
						rs.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return drugs;
				}

				@Override
				public Illness getIllness(Illness illness) {
					Illness illness1 = null;
					
					try {
						String sql = "SELECT * FROM illnesses WHERE name LIKE ? AND quarantine LIKE ? AND prothesis LIKE ? AND drug_id LIKE ?";
						PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
						prep.setString(1, "%" + illness.getName() + "%");
						prep.setBoolean(2, illness.getQuarantine());
						prep.setBoolean(3, illness.getProthesis());
						
						if (illness.getDrugId() == null) {
							prep.setString(4, null);
						} else {
							prep.setInt(4, illness.getDrugId());
						}
						
						ResultSet rs = prep.executeQuery();

						while (rs.next()) { // like hasNext
							int id = rs.getInt("id");
							String name = rs.getString("name");
							Boolean quarantine = rs.getBoolean("quarantine");
							Boolean prothesis = rs.getBoolean("prothesis");
							int drug_id = rs.getInt("drug_id");

							illness1 = new Illness(id, name, quarantine, prothesis, drug_id);
						}

						prep.close();
						rs.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return illness1;
				}
				
				//public Illness addIllness(Illness illness); again
				
				//public void setIllnessOnAnimal(Illness illness, Animal animal); again
				
				@Override
				public void drugPrescription(Animal animal) {
					// Cuando se le receta una medicina a un animal, conectar una cosa con la otra

					try {
						PreparedStatement s = null;
						for (int i = 0; i < animal.getDrugs().size(); i++) {
							Drug d = animal.getDrugs().get(i);
							String sql = "INSERT INTO animal_drug VALUES(?,?)";
							s = JDBCManager.c.prepareStatement(sql);
							s.setInt(1, d.getId());
							s.setInt(2, animal.getId());
							s.executeUpdate();
						}
						s.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	
			//II. BACK
				
				@Override
				public List<String> getAllHabitatsNames() {
					List<String> habitatNames = new ArrayList<String>();

					try {
						String sql = "SELECT DISTINCT * FROM habitats"; // DISTINCT esta bien, comprobado
						PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
						ResultSet rs = prep.executeQuery();

						while (rs.next()) { // like hasNext
							String name = rs.getString("name");
							habitatNames.add(name);
						}

						prep.close();
						rs.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return habitatNames;

				}
				
				
				@Override
				public void changeHabitat(String habitatName, Animal animal) {
					try {
						String sql = "UPDATE animals SET habitat_id = ? WHERE id = ?";
						
						PreparedStatement s = JDBCManager.c.prepareStatement(sql);
						s.setInt(1, this.getHabitatIdByName(habitatName));
						s.setInt(2, animal.getId());
						s.executeUpdate();
						s.close();

					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			
				
	
	//2. ANIMAL CHECK - daily
			
		//a. askForHabitatToCheckItsAnimals
				
			//public List<String> getAllHabitatsNames(); again
				
			@Override
			public List<Habitat> getHabitatByName(String name) {

					List<Habitat> habitats = new ArrayList<Habitat>();

					try {
						String sql = "SELECT * FROM habitats WHERE name LIKE ?";
						PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
						prep.setString(1, "%" + name + "%");
						ResultSet rs = prep.executeQuery();

						while (rs.next()) { // like hasNext
							int id = rs.getInt("id");
							String hName = rs.getString("name");
							Date lastCleaned = Date.valueOf(rs.getString("lastCleaned"));
							Date waterTank = Date.valueOf(rs.getString("waterTank"));
							Integer temperature = rs.getInt("temperature");
							LightType light = LightType.valueOf(rs.getString("light"));

							Habitat habitat = new Habitat(id, hName, lastCleaned, waterTank, temperature, light);
							habitats.add(habitat);
						}

						prep.close();
						rs.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return habitats;
				}
		
		//b. checkIfAnimalsInHabitat
			
			//just like on 1.a (with getAnimalsInHabitat)
			
		//c. animalCheckSubMenu
			
			//I. askForAnimalFromHabitat: in the future this will only need an id read by a barcorde reader
				
				@Override
				public List<String> getAnimalTypesInAHabitat(Habitat habitat) {
					List<String> types = new ArrayList<String>();

					try {
						String sql = "SELECT ac.type FROM animals_characteristics AS ac " // TODO ver que va bien
								+ "	JOIN animals AS a ON a.type_id = ac.id "
								+ "	JOIN habitats AS h ON a.habitat_id = h.id " 
								+ "	WHERE h.name LIKE ?";
						PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
						prep.setString(1, "%" + habitat.getName() + "%");
						ResultSet rs = prep.executeQuery();

						while (rs.next()) { // like hasNext
							String type = rs.getString("type");

							types.add(type);
						}

						prep.close();
						rs.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return types;

				}
				
				@Override
				public List<Animal> getAnimalsGivenHabitatAndType(String habitatName, String animalType) {
					List<Animal> animals = new ArrayList<Animal>();

					try {
						String sql = "SELECT * FROM animals AS a "
								+ "	JOIN habitats AS h ON a.habitat_id=h.id "
								+ "	JOIN animals_characteristics AS ac ON ac.id = a.type_id "
								+ "	WHERE ac.type LIKE ? AND h.id LIKE ?";

						PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
						prep.setString(1, "%" + animalType + "%");
						prep.setInt(2, getHabitatIdByName(habitatName));
						ResultSet rs = prep.executeQuery();

						while (rs.next()) { // like hasNext
							int id = rs.getInt(1);
							Date enterDate = Date.valueOf(rs.getString("enterDate"));
							Integer habitat_id = rs.getInt("habitat_id");
							Date lastBath = Date.valueOf(rs.getString("lastBath"));
							Date lastFed = Date.valueOf(rs.getString("lastFed"));
							Date lastDrug = Date.valueOf(rs.getString("lastDrug"));
							
							Date deathDate = null;
							if (rs.getString("deathDate") == null) {
							} else {
								String deathDateString = rs.getString("deathDate");
								deathDate = Date.valueOf(deathDateString);
							}
							
							Date freedomDate = null;
							if (rs.getString("freedomDate") == null) {
							} else {
								String freedomDateString = rs.getString("freedomDate");
								freedomDate = Date.valueOf(freedomDateString);
							}

							int type_id = rs.getInt("type_id");
							String name = rs.getString(10);

							Animal animal = new Animal(id, enterDate, habitat_id, lastBath, lastFed, lastDrug, deathDate,
							freedomDate, type_id, name);

							animals.add(animal);
						}

						prep.close();
						rs.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return animals;

				}
				
				//public List<Animal> getAnimalByNameAndType (String nameToSearch, String typeToSearch); again
				
			//II. [rest]
				
				@Override
				public void reportAnimalState(Integer option, Animal animal) {
					
					LocalDate localToday = LocalDate.now(); 
					Date newDate = Date.valueOf(localToday);

					switch (option) {
					case 1: // release

						try {
							String sql1 = "UPDATE animals SET freedomDate=? WHERE id=?";
							PreparedStatement s1 = JDBCManager.c.prepareStatement(sql1);
							s1.setString(1, "" + newDate + "");
							s1.setInt(2, animal.getId());
							s1.executeUpdate();
							s1.close();
							
							String sql2 = "UPDATE animals SET habitat_id = ? WHERE id=?";
							PreparedStatement s2 = JDBCManager.c.prepareStatement(sql2);
							s2.setString(1, null);
							s2.setInt(2, animal.getId());
							s2.executeUpdate();
							s2.close();
							
							String sql3 = "UPDATE animals SET deathDate=? WHERE id=?";
							PreparedStatement s3 = JDBCManager.c.prepareStatement(sql3);
							s3.setString(1, null);
							s3.setInt(2, animal.getId());
							s3.executeUpdate();
							s3.close();

						} catch (Exception e) {
							e.printStackTrace();
						}

						break;
					case 2: // death

						try {
							String sql1 = "UPDATE animals SET deathDate=? WHERE id=?";
							PreparedStatement s1 = JDBCManager.c.prepareStatement(sql1);
							s1.setString(1, "" + newDate + "");
							s1.setInt(2, animal.getId());
							s1.executeUpdate();
							s1.close();

							String sql2 = "UPDATE animals SET habitat_id = ? WHERE id=?";
							PreparedStatement s2 = JDBCManager.c.prepareStatement(sql2);
							s2.setString(1, null);
							s2.setInt(2, animal.getId());
							s2.executeUpdate();
							s2.close();
							
							String sql3 = "UPDATE animals SET freedomDate=? WHERE id=?";
							PreparedStatement s3 = JDBCManager.c.prepareStatement(sql3);
							s3.setString(1, null);
							s3.setInt(2, animal.getId());
							s3.executeUpdate();
							s3.close();

						} catch (Exception e) {
							e.printStackTrace();
						}

						break;
					default:
						// Exception?
						break;
					}

				}

				
}
