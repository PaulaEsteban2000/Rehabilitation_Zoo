package rehabilitationzoo.db.pojos;

public class GroundType {
	
	//FOREST, OCEAN, FRESHWATER, GRASSLAND, WETLAND, POLAR, DESERT, MOUNTAIN

	private Integer id;
	private String type; 
	private Integer habitat_id; //fk to habitat id where the groundType is 
	//n ground tyes 1 habitat

	public GroundType(String type, Integer habitat) {
		super();
		this.type = type;
		this.habitat_id = habitat;
	}
	
	public GroundType(String type) {
		super();
		this.type = type;
	}
	
	public Integer getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getHabitat() {
		return habitat_id;
	}

	public void setHabitat(Integer habitat) {
		this.habitat_id = habitat;
	}
	
	
}
