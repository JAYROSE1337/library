package pl.project.library.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name=BookCopy.TABLENAME)
public class BookCopy implements Serializable {

	public static final String TABLENAME = "COPIES";

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private UUID copyID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BOOKS_bookID", referencedColumnName = "bookID")
	private Book book;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Lend> lends = new ArrayList<>();
	
	public UUID getBookCopyId(){
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
