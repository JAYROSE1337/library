package pl.project.library.app;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.kurs.komis.Car;
import pl.project.library.entities.Book;
import pl.project.library.entities.BookCopy;
import pl.project.library.entities.Borrow;
import pl.project.library.entities.User;

@Stateful
public class LibraryEJB extends BaseDAO {
    
	// ================================= BOOKS ================================= \\
	
    public Book getBook(int bookId) {
        Book book = get(bookId, Book.class);
        
        return book;
    }

	public boolean addBook(Book book) {
	    create(book);

        return true;
	}
	
	public boolean updateBook(Book book) {
	    update(book);

        return true;
	}
	
	public void deleteBook(int bookId) {
		Book book = getBook(bookId);
		
		delete(book);
	}
	
    public List<Book> getAllBooks() {
		List<Book> books = getBooks();
    	
    	return books;
    }
    
	// ================================= BOOK COPIES ================================= \\
    
	public boolean addBookCopy(BookCopy bookCopy) {
	    create(bookCopy);

        return true;
	}
	
    public BookCopy getBookCopy(int bookCopyId) {
        BookCopy book = get(bookCopyId, BookCopy.class);
        
        return book;
    }
    
    public List<BookCopy> getAllBookCopies() {
		List<BookCopy> bookCopies = getBookCopies();
    	
    	return bookCopies;
    }
    
	public boolean updateBookCopy(BookCopy bookCopy) {
	    update(bookCopy);

        return true;
	}
    
	public void deleteBookCopy(int bookCopyId) {
		BookCopy bookCopy = getBookCopy(bookCopyId);
		
		delete(bookCopy);
	}
	
	// ================================= USERS ================================= \\
	
    public User getUser(int userId) {
        User user = get(userId, User.class);
        
        return user;
    }

	public boolean addUser(User user) {
	    create(user);

        return true;
	}
	
	public boolean updateUser(User user) {
	    update(user);

        return true;
	}
	
	public void deleteUser(int userId) {
		User user = getUser(userId);
		
		delete(user);
	}
	
    public List<User> getAllUsers() {
		List<User> users = getUsers();
    	
    	return users;
    }
    
	// ================================= BORROWS ================================= \\
    
    public Borrow getBorrow(int borrowId) {
        Borrow borrow = get(borrowId, Borrow.class);
        
        return borrow;
    }

	public boolean addBorrow(Borrow borrow) {
	    create(borrow);

        return true;
	}
	
	public boolean updateBorrow(Borrow borrow) {
	    update(borrow);

        return true;
	}
	
	public void deleteBorrow(int borrowId) {
		Borrow borrow = getBorrow(borrowId);
		
		delete(borrow);
	}
	
    public List<Borrow> getAllBorrows() {
		List<Borrow> borrows = getBorrows();
    	
    	return borrows;
    }

}
