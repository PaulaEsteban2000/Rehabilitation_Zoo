package rehabilitationzoo.db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class WorkerType implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public enum Type {
		
		ZOO_KEEPER, VETERINARY ,ADMINISTRATOR 
		}
	/*
	private String name;
	private String lastname;
	private WorkerType type;;
	
	
	public WorkerType( String name, String lastname,WorkerType type ) {
		
		this.name = name;
	    this.lastname = lastname;
	    this.type = type;
	
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
	public void setLastName(String lastName) {
		this.lastname = lastName;
	}
		
	
	public WorkerType getWorkerType() {
		return type;
	}
	
	public void setWorkerType(WorkerType workerType) {
		this.type = type;
	}*/

}