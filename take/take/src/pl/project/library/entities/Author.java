package pl.project.library.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name= Author.TABLENAME)
public class Author implements Serializable {
	static final String TABLENAME = "AUTHORS";
	private static final long serialVersionUID = -2449297703711368024L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID authorID;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	
	@OneToMany(mappedBy="author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Book> books = new ArrayList<Book>();

	public UUID getAuthorID() {return this.authorID;}
	
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
