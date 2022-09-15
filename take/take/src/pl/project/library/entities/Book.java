package pl.project.library.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name=Book.TABLENAME)
@Table(name=Book.TABLENAME)
@XmlRootElement
public class Book implements Serializable{
	private static final long serialVersionUID = 7756240746240050241L;
	public static final String TABLENAME = "BOOKS";
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int bookID;
	
	@Column(nullable=false)
	private String title;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUTHORS_authorID", referencedColumnName = "authorID")
	private Author author;
	
	@OneToMany(mappedBy="book", fetch = FetchType.EAGER)
	private List<BookCopy> copies = new ArrayList<BookCopy>();
	
	public int getBookID() {
		return this.bookID;
	}
	
	public void setBookID(int id) {
		this.bookID = id;
	}

	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<BookCopy> getCopies() {
		return this.copies;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public void setBookCopies(List<BookCopy> copies) {
		this.copies = copies;
	}
}
