package pl.project.library.app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.project.library.entities.Book;
import pl.project.library.entities.BookCopy;
import pl.project.library.entities.Borrow;
import pl.project.library.entities.User;

public class BaseDAO {
	
	@PersistenceContext
    private EntityManager em;
	
	// ================================= GENERICS ================================= \\

	public <T> void create(T t) {
		em.persist(t);
	}
	
	public <T> T get(Object id, Class<T> type) {
		return em.find(type, id);
	}
	
	public <T> void update(T t) {
		em.merge(t);
	}
	
	public void delete(Object t) {
		Object ref = em.merge(t);
		em.remove(ref);
	}
	
	// ================================= DEDICATED ================================= \\
	
	public List<Book> getBooks() {
		Query query = em.createQuery("select b from BOOKS b");
		@SuppressWarnings("unchecked")
		List<Book> books = query.getResultList();
		
		return books;
	}
	
	public List<BookCopy> getBookCopies() {
		Query query = em.createQuery("select bc from BOOKCOPIES bc");
		@SuppressWarnings("unchecked")
		List<BookCopy> books = query.getResultList();
		
		return books;
	}
	
	public List<User> getUsers() {
		Query query = em.createQuery("select u from USERS u");
		@SuppressWarnings("unchecked")
		List<User> users = query.getResultList();
		
		return users;
	}
	
	public List<Borrow> getBorrows() {
		Query query = em.createQuery("select b from BORROWS b");
		@SuppressWarnings("unchecked")
		List<Borrow> borrows = query.getResultList();
		
		return borrows;
	}
}
