package rehabilitationzoo.db.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Drug implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4834976860627984605L;

	private Integer id; //No need to be in constructor
	private String name;
	private Integer treatmentDuration;
	private Integer periodBetweenDosis;
	private Integer drugType_id;
	//private float dosis;
	
	private List<Animal> animals; 
	private List<Illness> illnesses; 
	
	public void addAnimal(Animal animal) {
		this.animals.add(animal);
	}
	
	public void addIllness(Illness illness) {
		this.illnesses.add(illness);
	}
		

	public Drug(String name, Integer treatmentDuration, Integer periodBetweenDosis, Integer drugType_id,  List<Animal> animals,  List<Illness> illness) {
		super();
		this.name = name;
		this.treatmentDuration = treatmentDuration;
		this.periodBetweenDosis = periodBetweenDosis;
		this.drugType_id = drugType_id;
		
		this.animals = new ArrayList<Animal>();
		this.illnesses = new ArrayList<Illness>();
		
	}
	
	public Drug(String name, Integer treatmentDuration, Integer periodBetweenDosis, Integer drugType_id) {
		super();
		this.name = name;
		this.treatmentDuration = treatmentDuration;
		this.periodBetweenDosis = periodBetweenDosis;
		this.drugType_id = drugType_id;
	}
	

	public Drug(Integer id, String name, Integer treatmentDuration, Integer periodBetweenDosis, Integer drugType_id) {
		super();
		this.id = id;
		this.name = name;
		this.treatmentDuration = treatmentDuration;
		this.periodBetweenDosis = periodBetweenDosis;
		this.drugType_id = drugType_id;
	}
	
	public Drug(String name, Integer treatmentDuration, Integer periodBetweenDosis/*, List<Animal> animals, List<Illness> illness*/) {
		super();
		this.name = name;
		this.treatmentDuration = treatmentDuration;
		this.periodBetweenDosis = periodBetweenDosis;
		
		//this.animals = new ArrayList<Animal>();
		//this.illnesses = new ArrayList<Illness>();
		
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
		Drug other = (Drug) obj;
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


	public Integer getTreatmentDuration() {
		return treatmentDuration;
	}


	public void setTreatmentDuration(Integer treatmentDuration) {
		this.treatmentDuration = treatmentDuration;
	}


	public Integer getPeriodBetweenDosis() {
		return periodBetweenDosis;
	}


	public void setPeriodBetweenDosis(Integer periodBetweenDosis) {
		this.periodBetweenDosis = periodBetweenDosis;
	}


	public Integer getType() {
		return drugType_id;
	}


	public void setType(Integer drugType_id) {
		this.drugType_id = drugType_id;
	}


	public List<Animal> getAnimals() {
		return animals;
	}


	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}


	public List<Illness> getIllnesses() {
		return illnesses;
	}


	public void setIllnesses(List<Illness> illnesses) {
		this.illnesses = illnesses;
	}


	
	
}
