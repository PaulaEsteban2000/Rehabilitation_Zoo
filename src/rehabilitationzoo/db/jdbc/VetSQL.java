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

public class VetSQL implements VetManager {

////////////DIAGNOSIS

	@Override
	public List<String> getAnimalTypesInZoo() {
		List<String> animalTypes = new ArrayList<String>();

		try {
			String sql = "SELECT type FROM animals_characteristics";
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) { // like hasNext
				String type = rs.getString("type");

				System.out.println(type);// esto por favor me lo quitas despues ;)
				animalTypes.add(type);
			}

			prep.close();
			rs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return animalTypes;

	}

	@Override
	public List<Animal> getAnimalsGivenType(String animalType) {
		List<Animal> animals = new ArrayList<Animal>();

		try {
			String sql = "SELECT * FROM animals	AS a JOIN animals_characteristics AS ac ON ac.id = a.type_id"
					+ " WHERE type = ? FROM animals_characteristics"; // esta bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + animalType + "%"); // esta bien??
			ResultSet rs = prep.executeQuery();

			while (rs.next()) { // like hasNext
				int id = rs.getInt("id");
				Date enterDate = rs.getDate("enterDate");
				Integer habitat_id = rs.getInt("habitat_id");
				// FeedingType feedingType = FeedingType.valueOf(rs.getString("feedingType"));
				Date lastBath = rs.getDate("lastBath");
				Date lastFed = rs.getDate("lastFed");
				Date deathDate = rs.getDate("deathDate");
				Date freedomDate = rs.getDate("freedomDate");
				// String type = rs.getString("type");
				String name = rs.getString("name");

				System.out.println(name);// esto por favor me lo quitas despues ;)
				Animal animal = new Animal(id, enterDate, habitat_id, /* feedingType, */ lastBath, lastFed, deathDate,
						freedomDate, /* type, */ name);

				animals.add(animal);
			}

			prep.close();
			rs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return animals;

	}

	@Override
	public List<Animal> getAnimalByNameAndType(String nameToSearch, String typeToSearch) {
		List<Animal> animals = new ArrayList<Animal>();

		try {
			String sql = "SELECT * FROM animals AS a JOIN animals_characteristics AS ac ON ac.type = a.type_id "
					+ "WHERE a.name LIKE ? AND WHERE ac.type LIKE ?";
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + nameToSearch + "%");
			prep.setString(2, "%" + typeToSearch + "%");
			ResultSet rs = prep.executeQuery();

			while (rs.next()) { // like hasNext
				int id = rs.getInt("id");
				Date enterDate = rs.getDate("enterDate");
				Integer habitat_id = rs.getInt("habitat_id");
				Date lastBath = rs.getDate("lastBath");
				Date lastFed = rs.getDate("lastFed");
				Date lastDrug = rs.getDate("lastDrug");
				Date deathDate = rs.getDate("deathDate");
				Date freedomDate = rs.getDate("freedomDate");
				int typeId = rs.getInt("type_id");
				String name = rs.getString("name");

				Animal animal = new Animal(id, enterDate, habitat_id, lastBath, lastFed, lastDrug, deathDate,
						freedomDate, typeId, name);

				animals.add(animal);
			}

			prep.close();
			rs.close();

		} catch (Exception ex) {
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
			s.setString(2, "%" + getIllnessIdByName(illness) + "%");
			s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (getNumberOfIllnessesAnAnimalHas(animal) == 0) {

			LocalDate localToday = LocalDate.now(); // only way to add dates
			LocalDate localHealingDay = localToday.plusDays(30);
			String stringHealingDay = localHealingDay.toString();
			Date newDate = Date.valueOf(stringHealingDay);
			/// TODO EEEPAA que solo se libere al bicho si no tiene alguna otra enfermedad
			/// -> MEJOR ESTO
			// o bien que si tiene otra enfermedad que se cambie la fecha de salida a
			/// indefinida

			// TODO YA: ahora me falta que cuando anhada una enfermedad tras la protesis se
			// ponga la fecha de salida a null

			try {
				String sql = "UPDATE animals SET freedomDate=?, WHERE id=?";
				PreparedStatement s = JDBCManager.c.prepareStatement(sql);
				s.setString(1, "%" + newDate + "%");
				s.setString(2, "%" + getAnimalId(animal) + "%");
				s.executeUpdate();
				s.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

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

	// MOVE public Integer getNumberOfIllnessesAnAnimalHas(); here when done

	@Override
	public void addIllness(Illness illness) { // do we need a prepared Statement better to avoid injection? I think so
												// bc it is insert
		try {
			// Id is chosen by the database
			Statement stmt = JDBCManager.c.createStatement();
			String sql = "INSERT INTO illnesses (name, quarantine, prothesis, drug_id )";
			sql += "VALUES ('" + illness.getName() + "','" + illness.getQuarantine() + "','" + illness.getProthesis()
					+ "','" + illness.getDrug() + ")";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void illnessQuarantine(Boolean bol, Illness illness) {

		try {
			String sql = "UPDATE illnesses SET quarantine=?, WHERE id=?";
			PreparedStatement s = JDBCManager.c.prepareStatement(sql);
			s.setString(1, "%" + bol + "%");
			s.setString(2, "%" + getIllnessIdByName(illness) + "%");
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
			String sql = "SELECT id FROM drugTypes WHERE type LIKE ?"; // esta bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + type + "%"); // esta bien??
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

////////////CHECK

	@Override
	public List<String> getAllHabitatsNames() {
		List<String> habitatNames = new ArrayList<String>();

		try {
			String sql = "SELECT * DISTINCT FROM habitats"; // DISTINCT esta bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql); // esta bien??
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
	public void reportAnimalState(Integer option, Animal animal) {
		LocalDate localToday = LocalDate.now(); // only way to add dates
		LocalDate localHealingDay = localToday.plusDays(30);
		String stringHealingDay = localHealingDay.toString();
		Date newDate = Date.valueOf(stringHealingDay);

		switch (option) {
		case 1: // release

			try {
				String sql = "UPDATE animal SET freedomDate=?, WHERE id=?";
				PreparedStatement s = JDBCManager.c.prepareStatement(sql);
				s.setString(1, "%" + newDate + "%");
				s.setString(2, "%" + animal.getId() + "%");
				s.executeUpdate();
				s.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case 2: // death

			try {
				String sql = "UPDATE animal SET deathDate=?, WHERE id=?";
				PreparedStatement s = JDBCManager.c.prepareStatement(sql);
				s.setString(1, "%" + newDate + "%");
				s.setString(2, "%" + animal.getId() + "%");
				s.executeUpdate();
				s.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		default:
			// Exception?
			break;
		}

	}

	@Override
	public List<String> getAnimalTypesInAHabitat(Habitat habitat) {
		List<String> types = new ArrayList<String>();

		try {
			String sql = "SELECT ac.name FROM animals_characteristics AS ac \r\n" // TODO ver que va bien
					+ "	JOIN animals AS a ON a.type_id = ac.id \r\n"
					+ "	JOIN habitats AS h ON a.habitat_id = h.id \r\n" + "	WHERE name LIKE ? FROM habitats";
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
			String sql = "\r\n" + "SELECT * FROM animals AS a JOIN habitats AS h ON a.habitat_id=h.id \r\n" // TODO HELP
					+ "	JOIN animals_characteristics AS ac ON ac.id = a.type_id\r\n"
					+ "	WHERE name LIKE ? FROM habitat \r\n" + "	AND type LIKE ? FROM animals_characteristics";

			// WHERE habitat_id = " + getHabitatIdByName(?) + "
			// AND type_id = " + getTypeIdByName(?) + "

			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + animalType + "%");
			prep.setString(2, "%" + getHabitatIdByName(habitatName) + "%");
			ResultSet rs = prep.executeQuery();

			while (rs.next()) { // like hasNext
				int id = rs.getInt("id");
				Date enterDate = rs.getDate("enterDate");
				Integer habitat_id = rs.getInt("habitat_id");
				// FeedingType feedingType = FeedingType.valueOf(rs.getString("feedingType"));
				Date lastBath = rs.getDate("lastBath");
				Date lastFed = rs.getDate("lastFed");
				Date deathDate = rs.getDate("deathDate");
				Date freedomDate = rs.getDate("freedomDate");
				// String type = rs.getString("type");
				String name = rs.getString("name");

				System.out.println(name);// esto por favor me lo quitas despues ;)
				Animal animal = new Animal(id, enterDate, habitat_id, /* feedingType, */ lastBath, lastFed, deathDate,
						freedomDate, /* type, */ name);

				animals.add(animal);
			}

			prep.close();
			rs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return animals;

	}

/////////////////////////////////////////////////
/////////////////////////////////////////////////

	// Metodos que incluir en el codigo que aun no estan:

	@Override
	public Integer getHabitatIdByName(String habitatName) throws SQLException {
		Integer id = null;

		try {
			String sql = "SELECT id FROM habitats WHERE name LIKE ?"; // esta bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + habitatName + "%"); // esta bien??
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
	public List<Animal> getAnimalsInHabitat(String habitatNameToSearch) throws SQLException { // Tambien lo utiliza zoo
																								// keeper
		List<Animal> animals = new ArrayList<Animal>();

		try {
			String sql = "SELECT * FROM animals WHERE habitat_id = ?"; // esta bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + getHabitatIdByName(habitatNameToSearch) + "%"); // esta bien??
			ResultSet rs = prep.executeQuery();

			while (rs.next()) { // like hasNext
				int id = rs.getInt("id");
				Date enterDate = rs.getDate("enterDate");
				Integer habitat_id = rs.getInt("habitat_id");
				// FeedingType feedingType = FeedingType.valueOf(rs.getString("feedingType"));
				Date waterTank = rs.getDate("waterTank");
				Date lastBath = rs.getDate("lastBath");
				Date lastFed = rs.getDate("lastFed");
				Date deathDate = rs.getDate("deathDate");
				Date freedomDate = rs.getDate("freedomDate");
				// String type = rs.getString("type");
				String name = rs.getString("name");

				System.out.println(name);// esto por favor me lo quitas despues ;)
				Animal animal = new Animal(id, enterDate, habitat_id, /* feedingType, */ lastBath, lastFed, deathDate,
						freedomDate, /* type, */ name);

				animals.add(animal);
			}

			prep.close();
			rs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return animals;
	}

	@Override
	public List<String> getDrugTypes() {
		List<String> drugTypes = new ArrayList<String>();

		try {
			String sql = "SELECT type DISTINCT FROM drugTypes"; // DISTINCT esta bien??
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
	public List<Drug> getDrugByNameAndType(String nameOfDrug, String typeOfDrug) {
		List<Drug> drugs = new ArrayList<Drug>();

		try {
			String sql = "SELECT * FROM drugs WHERE name LIKE ? AND WHERE type LIKE ?";
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + nameOfDrug + "%");
			prep.setString(2, "%" + getTypeOfDrugId(typeOfDrug) + "%");
			ResultSet rs = prep.executeQuery();

			while (rs.next()) { // like hasNext
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Integer treatmentDuration = rs.getInt("treatmentDuration");
				Integer periodBetweenDosis = rs.getInt("periodBetweenDosis");
				Integer drugType_id = rs.getInt("drugType_id");
				Float dosis = rs.getFloat("dosis");

				System.out.println(name);// esto por favor me lo quitas despues ;)
				Drug drug = new Drug(id, name, treatmentDuration, periodBetweenDosis, drugType_id, dosis);

				drugs.add(drug);
			}

			prep.close();
			rs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return drugs;
	}

	// TODO al ser una n-n no se muy bien como hacer estos metodos:
	// animal con su lista de illness

	@Override
	public List<Illness> getAnimalIllnesses(Animal animal) {
		List<Illness> illnesses = new ArrayList<Illness>();

		try {
			String sql = "SELECT * FROM illnesses AS i JOIN animals AS a ON i.id=a.id WHERE a.id LIKE ?";
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + animal.getId() + "%");
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				int drugId = rs.getInt("id");
				String name = rs.getString("name");
				Boolean quarantine = rs.getBoolean("quaratine");
				Boolean prothesis = rs.getBoolean("prothesis");

				Illness illness = new Illness(drugId, name, quarantine, prothesis);
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
	public Integer getNumberOfIllnessesAnAnimalHas(Animal animal) {
		Integer numberOfIllnesses = null;

		try {
			String sql = "SELECT id COUNT FROM illnesses AS i JOIN animals AS a ON i.id=a.id WHERE a.id LIKE ?"; // COUNT
																													// esta
																													// bien??
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql); // esta bien??
			ResultSet rs = prep.executeQuery();

			while (rs.next()) { // like hasNext
				numberOfIllnesses = Integer.parseInt(rs.getString("xxx"));
			}

			prep.close();
			rs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return numberOfIllnesses;

	}

	public List<String> getDrugNamesGivenType(String drugName) {
		return null;
	}

	@Override
	public void setIllnessOnAnimal(Illness illness, Animal animal) {
		// Cuando se le diagnostica una enfermedad a un animal, conectar una cosa con la
		// otra

		try {
			String sql = "UPDATE illnesses WH";
			PreparedStatement s = JDBCManager.c.prepareStatement(sql);
			s.setString(1, "%" + illness.getName() + "%");
			s.setString(2, "%" + animal.getId() + "%");
			s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void drugPrescription(Animal animal) {
		// Cuando se le receta una medicina a un animal, conectar una cosa con la otra

		
		try {
			PreparedStatement s = null;
			for (int i = 0; i < animal.getDrugs().size(); i++) {
				Drug d= animal.getDrugs().get(i);
				String sql = "INSERT INTO animal_drug VALUES(?,?)";
				s = JDBCManager.c.prepareStatement(sql);
				s.setString(1, "%" + d.getId() + "%");
				s.setString(2, "%" + animal.getId() + "%");
				s.executeUpdate();				
			}
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
