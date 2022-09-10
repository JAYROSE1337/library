package pl.project.library.app;

import pl.project.library.entities.Book;
import pl.project.library.entities.BookCopies;
import pl.project.library.entities.BookCopy;
import pl.project.library.entities.Books;
import pl.project.library.entities.Borrow;
import pl.project.library.entities.Borrows;
import pl.project.library.entities.User;
import pl.project.library.entities.Users;

public interface ILibrary {

	public abstract String addBook(Book book);

	public abstract Book getBook(int bookId);
	
	public abstract Books getBooks();
	
	public abstract String updateBook(Book book);
	
	public abstract void deleteBook(int bookId);
	
	public abstract String addBookCopy(int bookId, BookCopy bookCopy);
	
	public abstract BookCopy getBookCopy(int bookCopyId);
	
	public abstract BookCopies getBookCopies();
	
	public abstract String updateBookCopy(BookCopy bookCopy);
	
	public abstract void deleteBookCopy(int bookCopyId);
	
	public abstract User getUser(int userId);
	
	public abstract Users getUsers();
	
	public abstract String addUser(User user);

	public abstract String updateUser(User user);
	
	public abstract void deleteUser(int userId);
	
	public abstract Borrow getBorrow(int borrowId);
	
	public abstract Borrows getBorrows();
	
	public abstract String addBorrow(int bookCopyId, int userId, Borrow borrow);
	
	public abstract String updateBorrow(Borrow borrow);
	
	public abstract void deleteBorrow(int borrowId);
}
