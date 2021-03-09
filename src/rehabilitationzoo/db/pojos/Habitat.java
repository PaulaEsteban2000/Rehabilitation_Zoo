package rehabilitationzoo.db.pojos;

import java.io.Serializable;
import java.sql.Date;

public class Habitat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1947022899735841747L;

	private Integer id; //No need to be in constructor
	private String name;
	private Date lastCleaned;
	private Float waterLevel;
	private GroundType ground;
	private Integer temperature;
	private LightType light;
	
	
	public Habitat(String name, Date lastCleaned, Float waterLevel, GroundType ground, Integer temperature,
			LightType light) {
		super();
		this.name = name;
		this.lastCleaned = lastCleaned;
		this.waterLevel = waterLevel;
		this.ground = ground;
		this.temperature = temperature;
		this.light = light;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Habitat other = (Habitat) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getLastCleaned() {
		return lastCleaned;
	}
	public void setLastCleaned(Date lastCleaned) {
		this.lastCleaned = lastCleaned;
	}
	public Float getWaterLevel() {
		return waterLevel;
	}
	public void setWaterLevel(Float waterLevel) {
		this.waterLevel = waterLevel;
	}
	public GroundType getGround() {
		return ground;
	}
	public void setGround(GroundType ground) {
		this.ground = ground;
	}
	public Integer getTemperature() {
		return temperature;
	}
	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}
	public LightType getLight() {
		return light;
	}
	public void setLight(LightType light) {
		this.light = light;
	}
	
}
