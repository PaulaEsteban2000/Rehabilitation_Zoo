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
	private DrugType type;
	private int dosis;
	
	private List<Animal> animals; 
	
	public Drug(String name, Integer treatmentDuration, Integer periodBetweenDosis, DrugType type,  List<Animal> animals, int dosis, Illness illness) {
		super();
		this.name = name;
		this.treatmentDuration = treatmentDuration;
		this.periodBetweenDosis = periodBetweenDosis;
		this.type = type;
		this.animals = new ArrayList<Animal>();
		this.setDosis(dosis);
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
	public void setId(Integer id) {
		this.id = id;
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
	public DrugType getType() {
		return type;
	}
	public void setType(DrugType type) {
		this.type = type;
	}


	public List<Animal> getAnimals() {
		return animals;
	}


	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}


	public int getDosis() {
		return dosis;
	}
	public void setDosis(int dosis) {
		this.dosis = dosis;
	}
	
}
