package Exceptions;

public class AdminExceptions  extends Exception{

	
	public enum AdminErrors{
		NULL, NOTAWORKERTYPE, NOTANANIMALTYPE, NOTAFEEDINGTYPE,  NOTADRUGTYPE, NOTADRUG
	}
	
	AdminErrors anError;

	
	public AdminExceptions (AdminErrors anError) {
		this.anError= anError;
		
	}
	
	public AdminErrors getErrors() {
		return anError;
	}
	
	
	public String toString() {
		switch(getErrors()) {
		
		case NOTAWORKERTYPE: 
			return "Error: That is not a worker type available for the zoo"+"\n";
			
		case NULL: 
			return "Error: We don't have any information about this person/animal/drug in the database"+"\n";

		case NOTANANIMALTYPE:
			return "Error: That is not an animal type available for the zoo" + "\n";
			
		case NOTADRUGTYPE:
			return "Error: That is not a drug type available in the zoo"+"\n";
			
		case NOTADRUG:
			return "Error: That is not a drug available in the zoo"+"\n";
			
		case NOTAFEEDINGTYPE:
			return "Error: Please do remember it can only be a CARNIVORE, HERVIBORE or OMNIVORE"+"\n";
			
		}
		return null;

	}
	
}
