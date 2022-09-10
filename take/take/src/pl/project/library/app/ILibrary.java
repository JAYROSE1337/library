package pl.project.library.app;

import pl.project.library.entities.*;

import javax.ws.rs.*;
import java.util.List;
import java.util.UUID;

public interface ILibrary {
	public abstract Author getAuthor(UUID authorID);
	public abstract int addAuthor(Author author);
	public abstract int updateAuthor(Author author);
	public abstract int deleteAuthor(UUID authorID);
	public abstract List<Author> getAllAuthors();

	public abstract Book getBook(UUID bookID);
	public abstract int addBook(Book book);
	public abstract int updateBook(Book book);
	public abstract int deleteBook(UUID bookID);
	public abstract List<Book> getAllBooks();

	public abstract BookCopy getBookCopy(UUID copyID);
	public abstract int addBookCopy(BookCopy copy);
	public abstract int updateBookCopy(BookCopy copy);
	public abstract int deleteBookCopy(UUID copyID);
	public abstract List<BookCopy> getAllBookCopies();

	public abstract Lend getLend(UUID lendID);
	public abstract int addLend(Lend lend);
	public abstract int updateLend(Lend lend);
	public abstract int deleteLend(UUID lendID);
	public abstract List<Lend> getAllLends();

	public abstract Reader getReader(UUID readerID);
	public abstract int addReader(Reader reader) ;
	public abstract int updateReader(Reader reader);
	public abstract int deleteReader(UUID readerID);
	public abstract List<Reader> getAllReaders();
}