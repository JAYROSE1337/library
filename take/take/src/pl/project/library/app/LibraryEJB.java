package pl.project.library.app;

import java.util.List;

import javax.ejb.Stateful;

import pl.project.library.entities.*;

@Stateful
public class LibraryEJB extends BaseDAO {

	// --------- Authors ------------

	public Author getAuthor(int readerID) {
		return get(readerID, Author.class);
	}

	public void addAuthor(Author reader) {
		create(reader);
	}

	public void updateAuthor(Author reader) {
		update(reader);
	}

	public void deleteAuthor(int readerID) {
		Author reader = getAuthor(readerID);
		delete(reader);
	}

	public List<Author> getAllAuthors() {
		return getAuthors();
	}

	// --------- Books ------------

	public Book getBook(int bookID) {
		return get(bookID, Book.class);
	}

	public void addBook(Book book) {
		create(book);
	}

	public void updateBook(Book book) {
		update(book);
	}

	public void deleteBook(int bookID) {
		Book book = getBook(bookID);
		delete(book);
	}

	public List<Book> getAllBooks() {
		return getBooks();
	}

	// --------- BookCopies ------------

	public BookCopy getBookCopy(int copyID) {
		return get(copyID, BookCopy.class);
	}

	public void addBookCopy(BookCopy copy) {
		create(copy);
	}

	public void updateBookCopy(BookCopy copy) {
		update(copy);
	}

	public void deleteBookCopy(int copyID) {
		BookCopy copy = getBookCopy(copyID);
		delete(copy);
	}

	public List<BookCopy> getAllBookCopies() {
		return getBookCopies();
	}

	// --------- Lends ------------

	public Lend getLend(int lendID) {
		Lend lend = get(lendID, Lend.class);

		return lend;
	}

	public void addLend(Lend lend) {
		create(lend);
	}

	public void updateLend(Lend lend) {
		update(lend);
	}

	public void deleteLend(int lendID) {
		Lend lend = getLend(lendID);
		delete(lend);
	}

	public List<Lend> getAllLends() {
		return getLends();
	}

	// --------- Readers ------------

	public Reader getReader(int readerID) {
		Reader reader = get(readerID, Reader.class);

		return reader;
	}

	public void addReader(Reader reader) {
		create(reader);
	}

	public void updateReader(Reader reader) {
		update(reader);
	}

	public void deleteReader(int readerID) {
		Reader reader = getReader(readerID);
		delete(reader);
	}

	public List<Reader> getAllReaders() {
		return getReaders();
	}

}
