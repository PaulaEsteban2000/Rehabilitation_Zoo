package rehabilitationzoo.db.jpa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import rehabilitationzoo.db.ifaces.UserManager;
import rehabilitationzoo.db.pojos.users.Role;
import rehabilitationzoo.db.pojos.users.User;

public class JPAUserManager implements UserManager{

	private EntityManager em;
	
	@Override
	public void Connect() {
		em = Persistence.createEntityManagerFactory("user-provider").createEntityManager(); //create em
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		
		//TODO aqui se haria lo de los roles, lo de tenerlos creados ya al abrir la db
		//Seria acceder a getRoles y si no tiene el tamanho deseado, entonces acceder a newRoles tantas veces como fuera necesario
		
		List<Role> existingRoles = this.getRoles();
		if (existingRoles.size() < 2) { //no se el numero de roles que necesitamos: creo que 3 por vet, admin, zookeeper
			this.newRole(new Role ("vet"));
			this.newRole(new Role ("zookeeper"));
			this.newRole(new Role ("admin"));
		}
		
	}

	@Override
	public void Disconnect() {
		em.close();		
	}

	@Override
	public void newUser(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}

	@Override
	public void newRole(Role role) {
		em.getTransaction().begin();
		em.persist(role);
		em.getTransaction().commit();
	}

	@Override
	public Role getRole(int id) {
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE id = ?", Role.class);
		q.setParameter(1, id);
		return (Role) q.getSingleResult();
	}

	@Override
	public List<Role> getRoles() {
		Query q = em.createNativeQuery("SELECT * FROM roles", Role.class);
		return (List<Role>) q.getResultList();	
	}

	@Override
	public User checkPassword(String email, String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); //for changing the password into a different language which is MD5
			md.update(password.getBytes());
			byte[] hash = md.digest(); //the hash that is the one that translates
			
			Query q = em.createNativeQuery("SELECT * FROM users WHERE email = ? AND password = ?", User.class);
			q.setParameter(1, email);
			q.setParameter(2, hash);
			
			return (User)q.getSingleResult();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoResultException e) {
			return null;
		}
		return null;
	}

}
