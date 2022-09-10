package pl.project.library.app;

import java.util.List;

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

import pl.kurs.komis.Cars;
import pl.project.library.entities.Book;
import pl.project.library.entities.BookCopies;
import pl.project.library.entities.BookCopy;
import pl.project.library.entities.Books;
import pl.project.library.entities.Borrow;
import pl.project.library.entities.Borrows;
import pl.project.library.entities.User;
import pl.project.library.entities.Users;

@Path("/library")
@Consumes({ "application/xml" })
@Produces({ "application/xml" })
public class LibraryREST implements ILibrary {
	
	@EJB
	LibraryEJB library;
	
	// ================================= BOOKS ================================= \\
	
	@Override
	@GET
	@Path("/{bookId}")
	public Book getBook(@PathParam("bookId") int bookId) {
		Book book = library.getBook(bookId);
		
		return book;
	}
	
	@Override
	@GET
	@Path("/getBooks")
	public Books getBooks() {
		List<Book> dbBooks = library.getAllBooks();
		Books books = new Books(dbBooks);
	
		return books;
	}
	
	@Override
	@POST
    @Path("/addBook")
	public String addBook(Book book) {
		boolean isAdded = library.addBook(book);
		
		return isAdded == true ? "Book was added successfully!" : "Book cannot be added!";
	}
	
	@Override
	@PUT
    @Path("/updateBook")
	public String updateBook(Book book) {
		boolean isUpdated = library.updateBook(book);
		
		return isUpdated == true ? "Book was updated successfully!" : "Book cannot be updated!";
	}
	
	@Override
	@DELETE
	@Path("/{bookId}")
	public void deleteBook(@PathParam("bookId") int bookId) {
		library.deleteBook(bookId);
	}
	
	// ================================= BOOK COPIES ================================= \\
	
	@Override
	@POST
    @Path("/addBookCopy/{bookId}")
	public String addBookCopy(@PathParam("bookId") int bookId, BookCopy bookCopy) {
		Book book = library.getBook(bookId);
		bookCopy.setBook(book);
		
		if (book == null) {
			return "Book copy cannot be added!";
		}
		
		boolean isAdded = library.addBookCopy(bookCopy);
		
		return isAdded == true ? "Book copy was added successfully!" : "Book copy cannot be added!";
	}
	
	@Override
	@GET
	@Path("/getBookCopy/{bookCopyId}")
	public BookCopy getBookCopy(@PathParam("bookCopyId") int bookCopyId) {
		BookCopy book = library.getBookCopy(bookCopyId);
		
		return book;
	}
	
	@Override
	@GET
	@Path("/getBookCopies")
	public BookCopies getBookCopies() {
		List<BookCopy> dbBooks = library.getAllBookCopies();
		BookCopies bookCopies = new BookCopies(dbBooks);
	
		return bookCopies;
	}
	
	@Override
	@PUT
    @Path("/updateBookCopy")
	public String updateBookCopy(BookCopy bookCopy) {
		boolean isUpdated = library.updateBookCopy(bookCopy);
		
		return isUpdated == true ? "Book was updated successfully!" : "Book cannot be updated!";
	}
	
	@Override
	@DELETE
	@Path("/deleteBookCopy/{bookId}")
	public void deleteBookCopy(@PathParam("bookId") int bookCopyId) {
		library.deleteBookCopy(bookCopyId);
	}
	
	// ================================= USERS ================================= \\
	
	@Override
	@GET
	@Path("/getUser/{userId}")
	public User getUser(@PathParam("userId") int userId) {
		User user = library.getUser(userId);
		
		return user;
	}
	
	@Override
	@GET
	@Path("/getUsers")
	public Users getUsers() {
		List<User> dbUsers = library.getAllUsers();
		Users users = new Users(dbUsers);
	
		return users;
	}
	
	@Override
	@POST
    @Path("/addUser")
	public String addUser(User user) {
		boolean isAdded = library.addUser(user);
		
		return isAdded == true ? "User was added successfully!" : "User cannot be added!";
	}
	
	@Override
	@PUT
    @Path("/updateUser")
	public String updateUser(User user) {
		boolean isUpdated = library.updateUser(user);
		
		return isUpdated == true ? "User was updated successfully!" : "User cannot be updated!";
	}
	
	@Override
	@DELETE
	@Path("/deleteUser/{userId}")
	public void deleteUser(@PathParam("userId") int userId) {
		library.deleteUser(userId);
	}
	
	// ================================= BORROWS ================================= \\
	
	@Override
	@GET
	@Path("/getBorrow/{borrowId}")
	public Borrow getBorrow(@PathParam("borrowId") int borrowId) {
		Borrow borrow = library.getBorrow(borrowId);
		
		return borrow;
	}
	
	@Override
	@GET
	@Path("/getBorrows")
	public Borrows getBorrows() {
		List<Borrow> dbBorrows = library.getAllBorrows();
		Borrows borrows = new Borrows(dbBorrows);
	
		return borrows;
	}
	
	@Override
	@POST
    @Path("/addBorrow")
	public String addBorrow(@QueryParam("bookCopyId") int bookCopyId, @QueryParam("userId") int userId, Borrow borrow) {
		BookCopy bookCopy = library.getBookCopy(bookCopyId);
		borrow.setBookCopy(bookCopy);
		User user = library.getUser(userId);
		borrow.setUser(user);
		
		if (user == null || bookCopy == null) {
			return "Book cannot be borrowed!";
		}
		boolean isAdded = library.addBorrow(borrow);
		
		return isAdded == true ? "User has borrowed a book!" : "Book cannot be borrowed!";
	}
	
	@Override
	@PUT
    @Path("/updateBorrow")
	public String updateBorrow(Borrow borrow) {
		boolean isUpdated = library.updateBorrow(borrow);
		
		return isUpdated == true ? "Borrow entity was updated successfully!" : "Borrow entity cannot be updated!";
	}
	
	@Override
	@DELETE
	@Path("/deleteBorrow/{borrowId}")
	public void deleteBorrow(@PathParam("borrowId") int borrowId) {
		library.deleteBorrow(borrowId);
	}
}
