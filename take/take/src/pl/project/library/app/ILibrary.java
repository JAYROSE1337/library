package pl.project.library.app;

import java.util.List;

import pl.project.library.entities.Author;
import pl.project.library.entities.Book;
import pl.project.library.entities.BookCopy;
import pl.project.library.entities.Lend;
import pl.project.library.entities.Reader;

public interface ILibrary {
	public abstract Author getAuthor(int authorID);
	public abstract void addAuthor(Author author);
	public abstract void updateAuthor(Author author);
	public abstract void deleteAuthor(int authorID);
	public abstract List<Author> getAllAuthors();

	public abstract Book getBook(int bookID);
	public abstract void addBook(int authorID, Book book);
	public abstract void updateBook(Book book);
	public abstract void deleteBook(int bookID);
	public abstract List<Book> getAllBooks();

	public abstract BookCopy getBookCopy(int copyID);
	public abstract void addBookCopy(int bookID, BookCopy copy);
	public abstract void updateBookCopy(int bookID, BookCopy copy);
	public abstract void deleteBookCopy(int copyID);
	public abstract List<BookCopy> getAllBookCopies();

	public abstract Lend getLend(int lendID);
	public abstract void addLend(int readerID, int copyID, Lend lend);
	public abstract void updateLend(Lend lend);
	public abstract void deleteLend(int lendID);
	public abstract List<Lend> getAllLends();

	public abstract Reader getReader(int readerID);
	public abstract void addReader(Reader reader) ;
	public abstract void updateReader(Reader reader);
	public abstract void deleteReader(int readerID);
	public abstract List<Reader> getAllReaders();
}