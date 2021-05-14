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
	
	public void addAnimal(Animal animal) {
		this.animals.add(animal);
	}
	
	
	public Illness(String name, Boolean quarantine, Boolean prothesis, Integer drug_id,
			List<Animal> animals) {
		super();
		this.name = name;
		this.quarantine = quarantine;
		this.prothesis = prothesis;
		this.drug_id = drug_id;
		this.animals = new ArrayList<Animal>();
	}

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
	
	public Illness(Integer id, String name, Boolean quarantine, Boolean prothesis, Integer drug_id) {
		super();
		this.id = id;
		this.name = name;
		this.quarantine = quarantine;
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


	public Boolean getQuarantine() {
		return quarantine;
	}


	public void setQuarantine(Boolean quarantine) {
		this.quarantine = quarantine;
	}


	public Boolean getProthesis() {
		return prothesis;
	}


	public void setProthesis(Boolean prothesis) {
		this.prothesis = prothesis;
	}


	public Integer getDrugId() {
		return drug_id;
	}


	public void setDrugId(Integer drug_id) {
		this.drug_id = drug_id;
	}


	public List<Animal> getAnimals() {
		return animals;
	}


	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	@Override
	public String toString() {
		return "Illness [id=" + id + ", name=" + name + ", quarantine=" + quarantine + ", prothesis=" + prothesis
				+ ", drug_id=" + drug_id + "]";
	}


	
	

	
}
