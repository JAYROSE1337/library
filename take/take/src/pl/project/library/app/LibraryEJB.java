package pl.project.library.app;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateful;

import pl.project.library.entities.*;

@Stateful
public class LibraryEJB extends BaseDAO {

	// --------- Authors ------------

	public Author getAuthor(UUID readerID) {
		return get(readerID, Author.class);
	}

	public void addAuthor(Author reader) {
		create(reader);
	}

	public void updateAuthor(Author reader) {
		update(reader);
	}

	public void deleteAuthor(UUID readerID) {
		Author reader = getAuthor(readerID);
		delete(reader);
	}

	public List<Author> getAllAuthors() {
		return getAuthors();
	}

	// --------- Books ------------

	public Book getBook(UUID bookID) {
		return get(bookID, Book.class);
	}

	public void addBook(Book book) {
		create(book);
	}

	public void updateBook(Book book) {
		update(book);
	}

	public void deleteBook(UUID bookID) {
		Book book = getBook(bookID);
		delete(book);
	}

	public List<Book> getAllBooks() {
		return getBooks();
	}

	// --------- BookCopies ------------

	public BookCopy getBookCopy(UUID copyID) {
		return get(copyID, BookCopy.class);
	}

	public void addBookCopy(BookCopy copy) {
		create(copy);
	}

	public void updateBookCopy(BookCopy copy) {
		update(copy);
	}

	public void deleteBookCopy(UUID copyID) {
		BookCopy copy = getBookCopy(copyID);
		delete(copy);
	}

	public List<BookCopy> getAllBookCopies() {
		return getBookCopies();
	}

	// --------- Lends ------------

	public Lend getLend(UUID lendID) {
		Lend lend = get(lendID, Lend.class);

		return lend;
	}

	public void addLend(Lend lend) {
		create(lend);
	}

	public void updateLend(Lend lend) {
		update(lend);
	}

	public void deleteLend(UUID lendID) {
		Lend lend = getLend(lendID);
	}

	public List<Lend> getAllLends() {
		return getLends();
	}

	// --------- Readers ------------

	public Reader getReader(UUID readerID) {
		Reader reader = get(readerID, Reader.class);

		return reader;
	}

	public void addReader(Reader reader) {
		create(reader);
	}

	public void updateReader(Reader reader) {
		update(reader);
	}

	public void deleteReader(UUID readerID) {
		Reader reader = getReader(readerID);
	}

	public List<Reader> getAllReaders() {
		return getReaders();
	}

}
