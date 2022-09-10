package pl.project.library.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookCopies {
	private List<BookCopy> bookCopies = new ArrayList<BookCopy>();

	public BookCopies(List<BookCopy> bookCopies) {
		super();
		this.bookCopies = bookCopies;
	}

	public BookCopies() {}
	
	public List<BookCopy> getBookCopies() {
		return bookCopies;
	}

	public void setBookCopies(List<BookCopy> bookCopies) {
		this.bookCopies = bookCopies;
	}
}
