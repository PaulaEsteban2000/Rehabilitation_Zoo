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
	private String name; //cambiado por String: post prepresentacion
	private Integer quarantineDays;
	private Boolean prothesis;
	private Integer drug_id; //fk to the drug it cures
	
	private List<Animal> animals; 
	
	
	/**public Illness(Integer quarantineDays, Boolean prothesis) {
		super();
		this.quarantineDays = quarantineDays;
		this.prothesis = prothesis;
	}*/
	
	
	public Illness(String name, Integer quarantineDays, Boolean prothesis, Integer drug_id, List<Animal> animals) {
		super();
		this.name = name;
		this.quarantineDays = quarantineDays;
		this.prothesis = prothesis;
		this.drug_id = drug_id;
		this.animals = new ArrayList<Animal>();
	}
	
	public Illness (String name, Integer quarantineDays, Boolean prothesis, Integer drug_id) {
		super();
		this.name = name;
		this.quarantineDays = quarantineDays;
		this.prothesis = prothesis;
		this.drug_id = drug_id;
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

	public String getName() {
		return name;
	}


	public void setName(String name) {
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


	public Integer getDrug() {
		return drug_id;
	}


	public void setDrug(Integer drug_id) {
		this.drug_id = drug_id;
	}


	public List<Animal> getAnimals() {
		return animals;
	}


	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	
}
