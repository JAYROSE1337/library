package pl.project.library.app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.project.library.entities.*;

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
	public List<Author> getAuthors() {
		Query query = em.createQuery(String.format("select auth from %s auth", Author.TABLENAME));
		@SuppressWarnings("unchecked")
		List<Author> authors = query.getResultList();

		return authors;
	}

	public List<Book> getBooks() {
		Query query = em.createQuery(String.format("select bk from %s bk", Book.TABLENAME));
		@SuppressWarnings("unchecked")
		List<Book> books = query.getResultList();
		
		return books;
	}
	
	public List<BookCopy> getBookCopies() {
		Query query = em.createQuery(String.format("select cp from %s cp", BookCopy.TABLENAME));
		@SuppressWarnings("unchecked")
		List<BookCopy> copies = query.getResultList();
		
		return copies;
	}
	
	public List<Lend> getLends() {
		Query query = em.createQuery(String.format("select lnd from %s lnd", Lend.TABLENAME));
		@SuppressWarnings("unchecked")
		List<Lend> lends = query.getResultList();
		
		return lends;
	}
	
	public List<Reader> getReaders() {
		Query query = em.createQuery(String.format("select rds from %s rds", Reader.TABLENAME));
		@SuppressWarnings("unchecked")
		List<Reader> readers = query.getResultList();
		
		return readers;
	}
}
