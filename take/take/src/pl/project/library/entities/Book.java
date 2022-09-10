package pl.project.library.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name="BOOKS")
@Table(name="BOOKS")
@XmlRootElement
public class Book implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int bookId;
	
	@Column(length=128, nullable=false, unique=false)
	private String title;
	
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<BookCopy> bookCopies = new ArrayList<BookCopy>();
	
	public int getBookId() {
		return this.bookId;
	}
	
	public void setBookId(int id) {
		this.bookId = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<BookCopy> getBookCopies() {
		return this.bookCopies;
	}
	
	public void setBookCopies(List<BookCopy> bookCopies) {
		this.bookCopies = bookCopies;
	}
}
