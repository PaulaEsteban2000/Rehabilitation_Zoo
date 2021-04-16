package rehabilitationzoo.db.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Illness implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3719735548395728811L;

	private Integer id; //No need to be in constructor
	private IllnessName name; //cambiar por String: prepresentacion
	private Integer quarantineDays;
	private Boolean prothesis;
	
	private List<Drug> drugs; 
	private List<Animal> animals; 
	
	
	public Illness(Integer quarantineDays, Boolean prothesis) {
		super();
		this.quarantineDays = quarantineDays;
		this.prothesis = prothesis;
	}
	
	
	public Illness(Integer id, IllnessName name, Integer quarantineDays, Boolean prothesis, List<Drug> drugs, List<Animal> animals) {
		super();
		this.id = id;
		this.name = name;
		this.quarantineDays = quarantineDays;
		this.prothesis = prothesis;
		this.drugs = new ArrayList<Drug>();
		this.animals = new ArrayList<Animal>();
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
		Illness other = (Illness) obj;
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
	public IllnessName getName() {
		return name;
	}
	public void setName(IllnessName name) {
		this.name = name;
	}
	public Integer getQuarantineDays() {
		return quarantineDays;
	}
	public void setQuarantineDays(Integer quarantineDays) {
		this.quarantineDays = quarantineDays;
	}
	public Boolean getProthesis() {
		return prothesis;
	}
	public void setProthesis(Boolean prothesis) {
		this.prothesis = prothesis;
	}


	public List<Drug> getDrugs() {
		return drugs;
	}


	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}


	public List<Animal> getAnimals() {
		return animals;
	}


	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}
	
	
}
