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
	private Boolean quarantine;
	private Boolean prothesis;
	private Integer drug_id; //fk to the drug it cures
	
	private List<Animal> animals; 
	
	
	/**public Illness(Integer quarantineDays, Boolean prothesis) {
		super();
		this.quarantineDays = quarantineDays;
		this.prothesis = prothesis;
	}*/
	
	
	public Illness(String name, Boolean quarantine, Boolean prothesis, Integer drug_id) {
		super();
		this.name = name;
		this.quarantine = quarantine;
		this.prothesis = prothesis;
		this.drug_id = drug_id;
	}
	
	public Illness (Integer id, String name, Boolean quarantine, Boolean prothesis) {
		super();
		this.id = id;
		this.name = name;
		this.quarantine = quarantine;
		this.prothesis = prothesis;
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


	public boolean getQuarantine() {
		return quarantine;
	}


	public void setQuarantine(boolean quarantine) {
		this.quarantine = quarantine;
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
