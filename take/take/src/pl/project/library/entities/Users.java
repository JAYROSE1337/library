package pl.project.library.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Users {
	private List<User> users = new ArrayList<User>();

	public Users(List<User> users) {
		super();
		this.users = users;
	}

	public Users() {}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
