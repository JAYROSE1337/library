package pl.project.library.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = Author.TABLENAME)
@Table(name= Author.TABLENAME)
@XmlRootElement
public class Author implements Serializable {
	public static final String TABLENAME = "AUTHORS";
	private static final long serialVersionUID = -2449297703711368024L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int authorID;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@OneToMany(mappedBy="author", fetch = FetchType.EAGER)
	private List<Book> books = new ArrayList<Book>();
	
	public void setAuthorID(int id) {
		this.authorID = id;
	}

	public int getAuthorID() {return this.authorID;}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public List<Book> getBooks() {
		return this.books;
	}
	
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
}
