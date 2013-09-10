package fr.gpereira.bookstore.dao;

import java.util.List;

import com.byob.db.exception.DAOException;

import fr.gpereira.bookstore.model.Book;

/**
 * 
 * Book Data Access Object
 * 
 * @author gpereira
 *
 */
public interface BookDAO {


	/**
	 * Creates a book
	 * @param book the book to create
	 * @throws DAOException
	 */
	Integer create(Book book) throws DAOException;
	
	
	/**
	 * Updates a book
	 * @param book the book to update
	 * @throws DAOException
	 */
	void update(Book book) throws DAOException;
	
	/**
	 * Deletes a book
	 * @param bookId the book id to delete
	 * @throws DAOException
	 */
	void delete(Integer bookId) throws DAOException;
	
	/**
	 * List all books
	 * @return
	 * @throws DAOException
	 */
	List<Book> list() throws DAOException;
	
}
