package rehabilitationzoo.db.jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import rehabilitationzoo.db.ifaces.ZooKeeperManager;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.FeedingType;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.LightType;

public class ZooKeeperSQL implements ZooKeeperManager{

	@Override
	public void printHabitatsNamesAndId() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drugAdministrationToAnimal(Animal animal) { //TODO
			LocalDate localToday = LocalDate.now(); 
			LocalDate localDrugDay = localToday.plusDays();
			String stringDrugDay = localDrugDay.toString();
			Date newDate = Date.valueOf(stringDrugDay);
				
			try {
				String sql = "UPDATE animal SET lastDrug=?, WHERE id=?";
				PreparedStatement s = JDBCManager.c.prepareStatement(sql);
				s.setString(1, "%" + newDate + "%");
				s.setString(1, "%" + animal.getId() + "%");
				s.executeUpdate();
				s.close();

				} catch (Exception e) {
					e.printStackTrace();
				}	
			
	}

	
	@Override
	public Integer getHabitatIdByName(String habitatName) throws SQLException{  //TODO PREGUNTAR ERROR
		Integer id = null;
		
		try {
			String sql = "SELECT id FROM habitats WHERE name LIKE ?"; 		
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			prep.setString(1, "%" + habitatName + "%");						
			ResultSet rs = prep.executeQuery();
			
	//		while (rs.next()) { //like hasNext
				id = rs.getInt("id");
	//		}
			
		prep.close();
		rs.close();
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return id;
	}
	
	
	@Override
	public List<String> getHabitats(){
		
		List<String> habitatsNames = new ArrayList<String>();
		
		try {
		String sql = "SELECT name DISTINCT FROM habitat"; 				//TODO DISTINCT esta bien??
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	//TODO esta bien??
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { //like hasNext
			String name = rs.getString("name");
			
			System.out.println(name);
			habitatsNames.add(name);
		}
		
			prep.close();
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	return habitatsNames;
			
	}
	
	
	public List<Habitat> getHabitatById (Integer habitatId){
		List<Habitat> habitats = new ArrayList<Habitat>();
		
		try {
			String sql = "SELECT * FROM habitat WHERE id LIKE ? ";
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);
			prep.setString(1, "%" + habitatId + "%");
			ResultSet rs = prep.executeQuery();
		
			while (rs.next()) { //like hasNext
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Date lastCleaned = rs.getDate("lastCleaned");
				Date waterTank = rs.getDate("waterTank");
				Integer temperature = rs.getInt("temperature");
				LightType light = LightType.valueOf(rs.getString("light"));
				
				
				System.out.println(name);//esto por favor me lo quitas despues ;)
				Habitat habitat = new Habitat (id, name, lastCleaned, waterTank, temperature, light);
				
				habitats.add(habitat);
			}
			
			prep.close();
			rs.close();
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return habitats;		
		
	}
	
	
	

	@Override
	public void feedAnimal() { //TODO
		// TODO Auto-generated method stub
	}

	@Override
	public void batheAnimal(Habitat habitat) {
		LocalDate localToday = LocalDate.now(); 
		LocalDate localBathingDay = localToday.plusDays(7);
		String stringBathingDay = localBathingDay.toString();
		Date newDate = Date.valueOf(stringBathingDay);
		
		try {
			String sql = "UPDATE habitat SET lastCleaned=?, WHERE id=?";
			PreparedStatement s = JDBCManager.c.prepareStatement(sql);
			s.setString(1, "%" + newDate + "%");
			s.setString(1, "%" + habitat.getId() + "%");
			s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void cleanHabitat(Habitat habitat) {
		LocalDate localToday = LocalDate.now(); 
		LocalDate localCleaningDay = localToday.plusDays(1);
		String stringCleaningDay = localCleaningDay.toString();
		Date newDate = Date.valueOf(stringCleaningDay);
		
		try {
			String sql = "UPDATE habitat SET lastCleaned=?, WHERE id=?";
			PreparedStatement s = JDBCManager.c.prepareStatement(sql);
			s.setString(1, "%" + newDate + "%");
			s.setString(1, "%" + habitat.getId() + "%");
			s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public void fillUpWaterTank(Habitat habitat) {
		LocalDate localToday = LocalDate.now(); 
		LocalDate localFillingDay = localToday.plusDays(1);
		String stringFillingDay = localFillingDay.toString();
		Date newDate = Date.valueOf(stringFillingDay);
		
		try {
			String sql = "UPDATE habitat SET waterTank=?, WHERE id=?";
			PreparedStatement s = JDBCManager.c.prepareStatement(sql);
			s.setString(1, "%" + newDate + "%");
			s.setString(1, "%" + habitat.getId() + "%");
			s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

}
	
}
