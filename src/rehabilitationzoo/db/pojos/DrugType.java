package rehabilitationzoo.db.pojos;

import java.util.List;

public class DrugType {
	
	//OINTMENT, ANALGESIC, ANTIBIOTIC, ANTI_INFLAMATORY, BANDAGE, ANTIBODIES_SERUM

	private Integer id; 
	private String type;
	//private Drug drug; //fk that references to the drug whose type this is
	
	private List<Drug> drugs; 
	
	public DrugType(String type) {
		super();
		this.type = type;
		//this.drug = drug;
	}

	public Integer getId() {
			return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Drug> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}

	/**public Drug getDrug() {
		return drug;
	}

	public void setDrug(Drug drug) {
		this.drug = drug;
	}*/
	
	
}
