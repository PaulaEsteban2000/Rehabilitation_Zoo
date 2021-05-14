package rehabilitationzoo.db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Animal implements Serializable { //Serializable is used to have things exist outside memory (in the computer)

	/**
	 * 
	 */
	private static final long serialVersionUID = 723080679524606900L; //"signature" for this class, all objects in the class will have the same one
																		// automatically turning "Animal" into a file (when calling a specific method)
	
	private Integer id; //No need to be in constructor
	private Date enterDate;
	private Integer habitat_id; //fk to habitat the animal lives in
	private Date lastBath;	
	private Date lastFed;
	private Date lastDrug;
	private Date deathDate;
	private Date freedomDate;
	private Integer type_id; //fk that points to elephant, giraffe...
	private String name; //for easy access when still do not have a "barcode reader"

	
	private List<Worker> workers ; //as there's its brother List on Worker class, this conforms a many-to-many relationship
	private List<Illness> illnesses; 
	private List<Drug> drugs; 
    
	
	public void addDrug(Drug drug) {
		
		if (this.drugs == null) {
			this.drugs = new ArrayList<Drug>();
		}
			this.drugs.add(drug);
	}
	
	
	public void addIllness(Illness illness) {
		this.illnesses.add(illness);
	}
	
	
    public Animal(Integer id, Date enterDate, Integer habitat_id, Date lastBath, Date lastFed, Date lastDrug,
			Date deathDate, Date freedomDate, Integer type_id, String name, List<Worker> workers,
			List<Illness> illnesses, List<Drug> drugs) {
		super();
		this.id = id;
		this.enterDate = enterDate;
		this.habitat_id = habitat_id;
		this.lastBath = lastBath;
		this.lastFed = lastFed;
		this.lastDrug = lastDrug;
		this.deathDate = deathDate;
		this.freedomDate = freedomDate;
		this.type_id = type_id;
		this.name = name;
		
		this.workers = new ArrayList<Worker>();
		this.illnesses = new ArrayList<Illness>();
		this.drugs = new ArrayList<Drug>();
	}

	public Animal(Integer id, Date enterDate, Integer habitat_id, Date lastBath, Date lastFed, Date lastDrug,
			Date deathDate, Date freedomDate, Integer type_id, String name) {
		super();
		this.id = id;
		this.enterDate = enterDate;
		this.habitat_id = habitat_id;
		this.lastBath = lastBath;
		this.lastFed = lastFed;
		this.lastDrug = lastDrug;
		this.deathDate = deathDate;
		this.freedomDate = freedomDate;
		this.type_id = type_id;
		this.name = name;
	}


	public Animal(String name, Date enterDate) {
    	this.name= name;
		this.enterDate = enterDate;
    }
    

	public Animal(Integer id, Date enterDate, Integer habitat_id, Date lastBath, Date lastFed,
			Date deathDate, Date freedomDate, String name) {
		super();
		this.id = id;
		this.enterDate = enterDate;
		this.habitat_id = habitat_id;
		this.lastBath = lastBath;
		this.lastFed = lastFed;
		this.deathDate = deathDate;
		this.freedomDate = freedomDate;
		this.name = name;
	}

	
	public Animal(Date enterDate, Date lastBath, Date lastFed,
			Date deathDate, Date freedomDate, String name) {
		super();
		this.enterDate = enterDate;
		this.lastBath = lastBath;
		this.lastFed = lastFed;
		this.deathDate = deathDate;
		this.freedomDate = freedomDate;
		this.name = name;
	}
	
	


	@Override //To compare ids
	public int hashCode() { //if two objects have the same id, they'll have the same hashCode
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode()); //compact if
		return result;
	}


	@Override //To compare objects (in memory) 
	public boolean equals(Object obj) { //cannot use ==, not the same thing
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) //checking if objects are from same class
			return false;
		Animal other = (Animal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) //compares ids
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

	public Date getEnterDate() {
		return enterDate;
	}


	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}


	public Integer getHabitat_id() {
		return habitat_id;
	}


	public void setHabitat_id(Integer habitat_id) {
		this.habitat_id = habitat_id;
	}

	public Date getLastBath() {
		return lastBath;
	}


	public void setLastBath(Date lastBath) {
		this.lastBath = lastBath;
	}


	public Date getLastFed() {
		return lastFed;
	}


	public void setLastFed(Date lastFed) {
		this.lastFed = lastFed;
	}


	public Date getDeathDate() {
		return deathDate;
	}


	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}


	public Date getFreedomDate() {
		return freedomDate;
	}


	public void setFreedomDate(Date freedomDate) {
		this.freedomDate = freedomDate;
	}


	public List<Worker> getWorkers() {
		return workers;
	}


	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}


	public List<Illness> getIllnesses() {
		return illnesses;
	}


	public void setIllnesses(List<Illness> illnesses) {
		this.illnesses = illnesses;
	}


	public List<Drug> getDrugs() {
		return drugs;
	}


	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}


	@Override
	public String toString() {
		return "Animal [id=" + id + ", enterDate=" + enterDate + ", habitat_id=" + habitat_id + ", lastBath=" + lastBath
				+ ", lastFed=" + lastFed + ", lastDrug=" + lastDrug + ", deathDate=" + deathDate + ", freedomDate="
				+ freedomDate + ", type_id=" + type_id + ", name=" + name + ", illnesses=" + illnesses + ", drugs="
				+ drugs + "]"; //No workers print so as to avoid stackOverflow Error (loop)
	}

	public Date getLastDrug() {
		return lastDrug;
	}


	public void setLastDrug(Date lastDrug) {
		this.lastDrug = lastDrug;
	}


	public Integer getType_id() {
		return type_id;
	}


	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}

	
}
