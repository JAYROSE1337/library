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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name="BOOKCOPIES")
@Table(name="BOOKCOPIES")
@XmlRootElement
public class BookCopy implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int bookCopyId;
	
	@Column(length=13, nullable=false, unique=false)
	private String isbn;
	
	@OneToMany(mappedBy = "bookCopy", cascade = CascadeType.ALL)
	private List<Borrow> borrows = new ArrayList<Borrow>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BOOKS_bookId")
	private Book book;
	
	public int getBookCopyId(){
		return this.bookCopyId;
	}
	
	public void setBookCopyId(int id) {
		this.bookCopyId = id;
	}
	
	public String getIsbn(){
		return this.isbn;
	}
	
	public void setIsbn(String isbn){
		this.isbn = isbn;
	}
	
	@XmlTransient
	public List<Borrow> getBorrows(){
		return this.borrows;
	}
	
	public void setBorrows(List<Borrow> borrows){
		this.borrows = borrows;
	}
	
	@XmlTransient
	public Book getBook(){
		return book;
	}
	
	public void setBook(Book book){
		this.book = book;
	}
	
}
