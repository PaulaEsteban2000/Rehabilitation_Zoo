package rehabilitationzoo.db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Habitat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1947022899735841747L;

	private Integer id; //No need to be in constructor
	private String name;
	private Date lastCleaned;
	private Date waterTank;
	private Integer temperature;
	private LightType light;
	
	private List<Animal> animals; 
	private List<GroundType> grounds; //cambio post presentacion dado por rodrigo
	
	public Habitat(String name, Date lastCleaned, Date waterTank, Integer temperature,
			LightType light, List<Animal> animals, List<GroundType> grounds) {
		super();
		this.name = name;
		this.lastCleaned = lastCleaned;
		this.waterTank = waterTank;
		this.temperature = temperature;
		this.light = light;
		
		this.animals = new ArrayList<Animal>();
		this.grounds = new ArrayList<GroundType>();
	}
	
	public Habitat(Integer id, String name, Date lastCleaned, Date waterTank, Integer temperature, LightType light) {
		super();
		this.id = id;
		this.name = name;
		this.lastCleaned = lastCleaned;
		this.waterTank = waterTank;
		this.temperature = temperature;
		this.light = light;
	}
	
	
	public Habitat(String name, LocalDate lastCleaned, LocalDate waterTank, Integer temperature, LightType light) {
		super();
		this.name = name;
		this.lastCleaned = lastCleaned;
		this.waterTank = waterTank;
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


	public Date getWaterTank() {
		return waterTank;
	}


	public void setWaterTank(Date waterTank) {
		this.waterTank = waterTank;
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


	public List<Animal> getAnimals() {
		return animals;
	}


	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}


	public List<GroundType> getGrounds() {
		return grounds;
	}


	public void setGrounds(List<GroundType> grounds) {
		this.grounds = grounds;
	}




}
