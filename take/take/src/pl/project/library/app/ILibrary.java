package pl.project.library.app;

import pl.project.library.entities.Book;

public interface ILibrary {

	public abstract String addBook(Book book);

	public abstract Book getBook(int bookId);
}