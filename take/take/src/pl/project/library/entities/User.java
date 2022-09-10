package pl.project.library.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="USERS")
@Table(name="USERS")
@XmlRootElement
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	//tutaj do zmiany imie nazwisko pesel 
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int userId;
	
	@Column(length=128, nullable=false, unique=false)
	private String firstName;
	
	@Column(length=128, nullable=false, unique=false)
	private String surname;
	
	@Column(length=128, nullable=false, unique=true)
	private String email;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Borrow> borrows = new ArrayList<Borrow>();
	
	public int getUserId() {
		return this.userId;
	}
	
	public void setUserId(int id) {
		this.userId = id;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Borrow> getBorrows() {
		return this.borrows;
	}
}
