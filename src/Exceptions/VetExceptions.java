package Exceptions;

public class VetExceptions extends Exception{

	public enum VetErrors{
		EMPTYLIST
	}
	
	VetErrors anError;

	
	public VetExceptions (VetErrors anError) {
		this.anError= anError;
		
	}
	
	public VetErrors getErrors() {
		return anError;
	}
	
	
	public String toString() {
		switch(getErrors()) {
		
		case EMPTYLIST: 
			return "Error: There are no items here. Talk to an administrator to add some."+"\n";
			
		default:
			return "There was some error"+"\n";
			
		}

	}
	
}
