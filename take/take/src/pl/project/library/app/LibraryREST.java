package pl.project.library.app;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pl.project.library.entities.Author;
import pl.project.library.entities.Book;
import pl.project.library.entities.BookCopy;
import pl.project.library.entities.Lend;
import pl.project.library.entities.Reader;

@Path("/library")
@Consumes({MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_XML})
public class LibraryREST implements ILibrary {

	@EJB
	LibraryEJB library;

	@Override
	@GET
	@Path("/authors/{authorID}")
	public Author getAuthor(@PathParam("authorID") int authorID) {
		return library.getAuthor(authorID);
	}

	@Override
	@POST
	@Path("/authors")
	public void addAuthor(Author author) {
		library.addAuthor(author);
	}

	@Override
	@PUT
	@Path("/authors")
	public void updateAuthor(Author author) {
		library.updateAuthor(author);
	}

	@Override
	@DELETE
	@Path("/authors/{authorID}")
	public void deleteAuthor(@PathParam("authorID") int authorID) {
		library.deleteAuthor(authorID);
	}

	@Override
	@GET
	@Path("/authors")
	public List<Author> getAllAuthors() {
		return library.getAllAuthors();
	}

	@Override
	@GET
	@Path("/books/{bookID}")
	public Book getBook(@PathParam("bookID") int bookID) {
		return library.getBook(bookID);
	}

	@Override
	@POST
	@Path("/books")
	public void addBook(@QueryParam("authorID") int authorID, Book book) {
		Author author = library.getAuthor(authorID);
		book.setAuthor(author);
		library.addBook(book);
	}

	@Override
	@PUT
	@Path("/books")
	public void updateBook(Book book) {
		library.updateBook(book);
	}


	@Override
	@DELETE
	@Path("/books/{bookID}")
	public void deleteBook(@PathParam("bookID") int bookID) {
		library.deleteBook(bookID);
	}

	@Override
	@GET
	@Path("/books")
	public List<Book> getAllBooks() {
		return library.getAllBooks();
	}

	@Override
	@GET
	@Path("/copies/{copyID}")
	public BookCopy getBookCopy(@PathParam("copyID") int copyID) {
		return library.getBookCopy(copyID);
	}

	@Override
	@POST
	@Path("/copies")
	public void addBookCopy(@QueryParam("bookID") int bookID, BookCopy copy) {
		Book book = library.getBook(bookID);
		copy.setBook(book);
		library.addBookCopy(copy);
	}

	@Override
	@PUT
	@Path("/copies")
	public void updateBookCopy(@QueryParam("bookID") int bookID, BookCopy copy) {
		Book book = library.getBook(bookID);
		copy.setBook(book);
		library.updateBookCopy(copy);
	}

	@Override
	@DELETE
	@Path("/copies/{copyID}")
	public void deleteBookCopy(@PathParam("copyID") int copyID) {
		library.deleteBookCopy(copyID);
	}

	@Override
	@GET
	@Path("/copies")
	public List<BookCopy> getAllBookCopies() {
		return library.getBookCopies();
	}

	@Override
	@GET
	@Path("/lends/{lendID}")
	public Lend getLend(@PathParam("lendID") int lendID) {
		return library.getLend(lendID);
	}

	@Override
	@POST
	@Path("/lends")
	public void addLend(@QueryParam("copyID") int copyID, @QueryParam("readerID") int readerID, Lend lend) {
		Reader reader = library.getReader(readerID);
		BookCopy copy = library.getBookCopy(copyID);
		lend.setBorrowedCopy(copy);
		lend.setReader(reader);
		library.addLend(lend);
	}

	@Override
	@PUT
	@Path("/lends")
	public void updateLend(Lend lend) {
		library.updateLend(lend);
	}

	@Override
	@DELETE
	@Path("/lends/{lendID}")
	public void deleteLend(@PathParam("lendID") int lendID) {
		library.deleteLend(lendID);
	}

	@Override
	@GET
	@Path("/lends")
	public List<Lend> getAllLends() {
		return library.getAllLends();
	}

	@Override
	@GET
	@Path("/readers/{readerID}")
	public Reader getReader(@PathParam("readerID") int readerID) {
		return library.getReader(readerID);
	}

	@Override
	@POST
	@Path("/readers")
	public void addReader(Reader reader) {
		library.addReader(reader);
	}

	@Override
	@PUT
	@Path("/readers")
	public void updateReader(Reader reader) {
		library.updateReader(reader);
	}

	@Override
	@DELETE
	@Path("/readers/{readerID}")
	public void deleteReader(@PathParam("readerID") int readerID) {
		library.deleteReader(readerID);
	}

	@Override
	@GET
	@Path("/readers")
	public List<Reader> getAllReaders() {
		return library.getAllReaders();
	}
}