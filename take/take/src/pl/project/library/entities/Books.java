package pl.project.library.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Books {
	private List<Book> books = new ArrayList<Book>();

	public Books(List<Book> books) {
		super();
		this.books = books;
	}

	public Books() {}
	
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}