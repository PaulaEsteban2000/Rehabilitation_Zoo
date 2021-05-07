package rehabilitationzoo.db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Worker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1409279678686881422L;

	


	private  Integer id; //No need to be in constructor
	private  String name;
	private  String lastname;
	private  Date hireDate;
	private  Float salary;
	private WorkerType type;
	private String whichHabitatDoYouWorkOn;
	
	public static List<Animal> animals; //as there's its brother List on Animal class, this conforms a many-to-many relationship
	
	
	public Worker(String name,String lastname, Date hireDate, Float salary, WorkerType type, List<Animal> animals) {
		super();
		this.name = name;
		this.lastname= lastname;
		this.hireDate = hireDate;
		this.salary = salary;
		this.type = type; 
		this.animals = new ArrayList<Animal>(); //better than having an empty list //do this even on empty constructors
												//cannot have a List, but an ArrayList (or any other class that implements list)
	}
	
	public Worker (String name, String lastname, Date hireDate, Float salary, WorkerType type) { //VET O ADMINISTRATOR
		super();
		this.name = name;
		this.lastname= lastname;
		this.hireDate = hireDate;
		this.salary = salary;
		this.type = type; 
		
	}
	
	public Worker (String name, String lastname, Date hireDate, Float salary, WorkerType type,String whichHabitatDoYouWorkOn) { //ESTE ES PARA EL ZOO KEEPER
		super();
		this.name = name;
		this.lastname= lastname;
		this.hireDate = hireDate;
		this.salary = salary;
		this.type = type; 
		this.whichHabitatDoYouWorkOn =whichHabitatDoYouWorkOn;
		
	}
	
	/**public Worker(Integer id, String name, Date hireDate) {
		super();
		this.id = id;
		this.name = name;
		this.hireDate = hireDate;
		//cannot have a List, but an ArrayList (or any other class that implements list)
	}*/
	
	
	
	/**public Worker(Integer id, String name, String lastName, Date hireDate, Integer salary, WorkerType type,
			List<Animal> animals) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.hireDate = hireDate;
		this.salary = salary;
		this.type = type;
		this.animals = animals;
	}*/


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
		Worker other = (Worker) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public void addAnimal (Animal animal) {
		if (!animals.contains(animal)) {
			animals.add(animal);
		}
		
	}

	public void removeAnimal (Animal animal) {
		if (animals.contains(animal)) {
			animals.remove(animal);
		}
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


	public String getLastName() {
		return lastname;
	}


	public void setLastName(String lastname) {
		this.lastname = lastname;
	}


	public Date getHireDate() {
		return hireDate;
	}


	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}


	public Float getSalary() {
		return salary;
	}


	public void setSalary(Float salary) {
		this.salary = salary;
	}


	 	public WorkerType getType() {
		return type;
	}


	public void setType(WorkerType type) {
		this.type = type;
	}


	public String getwhichHabitatDoYouWorkOn(){
		return whichHabitatDoYouWorkOn;
	}


	public void setwhichHabitatDoYouWorkOn(String whichHabitatDoYouWorkOn) {
		this.whichHabitatDoYouWorkOn = whichHabitatDoYouWorkOn;
	}
	
	
	public List<Animal> getAnimals() {
		return animals;
	}


	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	

	@Override
	public String toString() {
		return "Worker [id=" + id + ", name=" + name + ", hireDate=" + hireDate + ", salary=" + salary + ", type="
				+ type + ", animals=" + animals + "]";
	}
	
}
