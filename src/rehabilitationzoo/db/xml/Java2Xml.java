package rehabilitationzoo.db.xml;

import rehabilitationzoo.*;
import rehabilitationzoo.db.pojos.Habitat;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;



public class Java2Xml {

	// Put entity manager and buffered reader here so it can be used
	// in several methods
	private static EntityManager em;
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
	private static void printReports() {
		Query q1 = em.createNativeQuery("SELECT * FROM habitats", Habitat.class);
		List<Habitat> reps = (List<Habitat>) q1.getResultList();
		// Print the reports
		for (Habitat rep : reps) {
			System.out.println(rep);
		}
	}
	
	public static void main(String[] args) throws Exception {
		// Get the entity manager
		// Note that we are using the class' entity manager
		em = Persistence.createEntityManagerFactory("company-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		
		
// -- IMPORTANTE --		
		//Convertimos un objeto de java en XML
		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Habitat.class); //Root class del XML
		//JAXBContext es como el entity manager del XML
		
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();
		
// --  --			

		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE); // Pa poner el xml bonito
		
		// Choose the report to turn into an XML
		// Choose his new department
		printReports();
		System.out.print("Choose a report to turn into an XML file:");
		int rep_id = Integer.parseInt(reader.readLine());
		Query q2 = em.createNativeQuery("SELECT * FROM habitats WHERE id = ?", Habitat.class);
		q2.setParameter(1, rep_id);
		Habitat report = (Habitat) q2.getSingleResult(); // esto es lo q vamos a marshallear
		
		// Use the Marshaller to marshal the Java object to a file
		File file = new File("./xmls/Sample-Zoo.xml");
		marshaller.marshal(report, file);
		// Printout
		marshaller.marshal(report, System.out);

	}
}
