package rehabilitationzoo.db.xml;

import java.io.File;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.GroundType;
import rehabilitationzoo.db.pojos.Habitat;

public class Xml2JavaHabitat {

	private static final String PERSISTENCE_PROVIDER = "user-provider";
	private static EntityManagerFactory factory;

	public static void main(String[] args) throws JAXBException {

		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Habitat.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshal the XML document from a file
		File file = new File("./xmls/Habitat.xml");
		Habitat habitat = (Habitat) unmarshaller.unmarshal(file); // Tiene los employees

		
		// Print the habitat
		System.out.println("Habitat:");
		System.out.println("Name: " + habitat.getName());
		System.out.println("Last Cleaned: " + habitat.getLastCleaned());
		System.out.println("Water Tank: " + habitat.getWaterTank());
		List<Animal> animals = habitat.getAnimals();
		for (Animal animal : animals) {
			System.out.println("Animal Type: " + animal.getType_id());
			System.out.println("Animal Name: " + animal.getName());
		}
		
		List<GroundType> grounds = habitat.getGrounds();
		for (GroundType ground : grounds) {
			System.out.println("Ground Type: " + ground.getType());
			System.out.println("Ground Habitat: " + ground.getHabitat());
		}

		// Store the report in the database
		// Create entity manager
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_PROVIDER);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();

		// Create a transaction
		EntityTransaction tx1 = em.getTransaction();

		// Start transaction
		tx1.begin();

		// Persist
		// We assume the authors are not already in the database
		// In a real world, we should check if they already exist
		// and update them instead of inserting as new
		for (Animal animal : animals) {
			em.persist(animal);
		}
		for (GroundType ground : grounds) {
			em.persist(ground);
		}
		em.persist(habitat);
		
		// End transaction
		tx1.commit();
	}
}
