package pl.project.library.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

@Entity
@Table(name=Book.TABLENAME)
public class Book implements Serializable{
	static final String TABLENAME = "BOOKS";
	private static final long serialVersionUID = -2449297703711368024L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private UUID bookID;
	
	@Column(nullable=false)
	private String title;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUTHORS_authorID", referencedColumnName = "authorID")
	private Author author;
	
	@OneToMany(mappedBy="book", fetch = FetchType.EAGER)
	private List<BookCopy> copies = new ArrayList<BookCopy>();
	
	public UUID getBookId() {
		return this.bookID;
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
	
	public void setBookCopies(List<BookCopy> copies) {
		this.copies = copies;
	}
}
