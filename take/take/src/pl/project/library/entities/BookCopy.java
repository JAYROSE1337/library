package pl.project.library.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name= BookCopy.TABLENAME)
@Table(name= BookCopy.TABLENAME)
@XmlRootElement
public class BookCopy implements Serializable {

	private static final long serialVersionUID = 4139797928723198344L;

	public static final String TABLENAME = "COPIES";

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int copyID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BOOKS_bookID", referencedColumnName = "bookID")
	private Book book;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Lend> lends = new ArrayList<>();
	
	public void setBookCopyID(int id) {
		this.copyID = id;
	}
	public int getBookCopyID(){
		return this.copyID;
	}

	public Book getBook(){
		return book;
	}
	public void setBook(Book book){
		this.book = book;
	}

	public List<Lend> getLends() { return this.lends; }
	
}
