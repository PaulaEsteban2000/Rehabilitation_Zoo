package rehabilitationzoo.db.pojos;

public class GroundType {
	
	//FOREST, OCEAN, FRESHWATER, GRASSLAND, WETLAND, POLAR, DESERT, MOUNTAIN

	private String type; //cambio prepresentacion
	private Integer id;

	public GroundType(String type) {
		super();
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
