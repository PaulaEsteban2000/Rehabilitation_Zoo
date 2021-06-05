package rehabilitationzoo.db.ifaces;

import java.util.List;

import rehabilitationzoo.db.pojos.users.*;

public interface UserManager {

	public void Connect();
	public void Disconnect();
	
	public void newUser(User user);
	public void newRole(Role role);
	public Role getRole(int id);
	public List<Role> getRoles();
	public User checkPassword(String email, String password);
	void updateEmail(String email, String old);
	
}
