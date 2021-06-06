package rehabilitationzoo.db.pojos;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

	//JAXB
	@XmlAccessorType(XmlAccessType.FIELD) // Obligatorio, nos dice que vamos a poner las anotaciones en los atributos
	@XmlRootElement(name = "Ground Type") // Es opcional pero Indica que el objeto Report puede ser el root element del xml doc
	@XmlType(propOrder = { "type", "habitat_id" }) // orden en el q todos los atributos y elementos del xml aparecen
	//El orden importa

public class GroundType {
	
	@XmlTransient
	private Integer id;
	@XmlElement
	private String type; 
	@XmlElement
	private Integer habitat_id; //fk to habitat id where the groundType is //cambio tras presentacion
		
	
	public GroundType() {
		super();
	}

	public GroundType(Integer habitat,String type) {
		super();
		this.habitat_id = habitat;
		this.type = type;
		
	}
	
	public GroundType(String type) {
		super();
		this.type = type;
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

	public Integer getHabitat() {
		return habitat_id;
	}

	public void setHabitat(Integer habitat) {
		this.habitat_id = habitat;
	}
	
	
}
