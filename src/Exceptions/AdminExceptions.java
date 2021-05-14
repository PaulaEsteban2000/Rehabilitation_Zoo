package Exceptions;

public class AdminExceptions  extends Exception{

	
	public enum AdminErrors{
		NULL, SQLstuff
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
		
		case SQLstuff: 
			return "An SQL Exception has ocurred"+"\n";
			
		
		}
		return null;
	}
	
}
