package pl.project.library.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="BORROWS")
@Table(name="BORROWS")
@XmlRootElement
public class Borrow implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int borrowId;
	
	@Column(length=10, nullable=false, unique=false) // dd-mm-yyyy
	private String dateOfBorrow;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="USERS_userId")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="BOOKCOPIES_bookCopyId")
	private BookCopy bookCopy;
	
	public int getBorrowId() {
		return this.borrowId;
	}
	
	public void setBorrowId(int id) {
		this.borrowId = id;
	}
	
	public String getDateOfBorrow() {
		return this.dateOfBorrow;
	}
	
	public void setDateOfBorrow(String dateOfBorrow) {
		this.dateOfBorrow = dateOfBorrow;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public BookCopy getBookCopy(){
		return this.bookCopy;
	}
	
	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}
}
