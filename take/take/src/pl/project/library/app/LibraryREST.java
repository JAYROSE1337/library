package pl.project.library.app;

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

import pl.project.library.entities.*;

@Path("/library")
@Consumes({ "application/xml" })
@Produces({ "application/xml" })
public class LibraryREST implements ILibrary {

	@EJB
	LibraryEJB library;

	@Override
	@GET
	@Path("/authors/{authorID}")
	public Author getAuthor(@PathParam("authorID") UUID authorID) {
		return library.getAuthor(authorID);
	}

	@Override
	@POST
	@Path("/authors")
	public int addAuthor(Author author) {
		try {
			library.addAuthor(author);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	@PUT
	@Path("authors")
	public int updateAuthor(Author author) {
		try {
			library.addAuthor(author);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	@DELETE
	@Path("authors/{authorID}")
	public int deleteAuthor(@PathParam("authorID") UUID authorID) {
		try {
			library.deleteAuthor(authorID);
			return 200;
		} catch (Exception e) {
			return 400;
		}
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
	public Book getBook(@PathParam("bookID") UUID bookID) {
		return library.getBook(bookID);
	}

	@Override
	@POST
	@Path("/books")
	public int addBook(Book book) {
		try {
			library.addBook(book);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	@PUT
	@Path("/books")
	public int updateBook(Book book) {
		try {
			library.updateBook(book);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}


	@Override
	@DELETE
	@Path("/books/{bookID}")
	public int deleteBook(@PathParam("bookID") UUID bookID) {
		try {
			library.deleteBook(bookID);
			return 200;
		} catch (Exception e) {
			return 400;
		}
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
	public BookCopy getBookCopy(@PathParam("copyID") UUID copyID) {
		return library.getBookCopy(copyID);
	}

	@Override
	@POST
	@Path("/copies")
	public int addBookCopy(BookCopy copy) {
		try {
			library.addBookCopy(copy);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	@PUT
	@Path("/copies")
	public int updateBookCopy(BookCopy copy) {
		try {
			library.updateBookCopy(copy);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	@DELETE
	@Path("/copies/{copyID}")
	public int deleteBookCopy(@PathParam("copyID") UUID copyID) {
		try {
			library.deleteBookCopy(copyID);
			return 200;
		} catch (Exception e) {
			return 400;
		}
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
	public Lend getLend(@PathParam("lendID") UUID lendID) {
		return library.getLend(lendID);
	}

	@Override
	@POST
	@Path("/lends")
	public int addLend(Lend lend) {
		try {
			library.addLend(lend);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	@PUT
	@Path("/lends")
	public int updateLend(Lend lend) {
		try {
			library.updateLend(lend);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	@DELETE
	@Path("/lends/{lendID}")
	public int deleteLend(@PathParam("lendID") UUID lendID) {
		try {
			library.deleteLend(lendID);
			return 200;
		} catch (Exception e) {
			return 400;
		}
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
	public Reader getReader(@PathParam("readerID") UUID readerID) {
		return null;
	}

	@Override
	@POST
	@Path("/readers")
	public int addReader(Reader reader) {
		try {
			library.addReader(reader);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	@PUT
	@Path("/readers")
	public int updateReader(Reader reader) {
		try {
			library.updateReader(reader);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	@DELETE
	@Path("/readers/{readerID}")
	public int deleteReader(@PathParam("readerID") UUID readerID) {
		try {
			library.deleteReader(readerID);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	@GET
	@Path("/readers")
	public List<Reader> getAllReaders() {
		return library.getAllReaders();
	}
}