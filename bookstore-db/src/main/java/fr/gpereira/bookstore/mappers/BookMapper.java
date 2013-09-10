package fr.gpereira.bookstore.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import fr.gpereira.bookstore.model.Book;

/**
 * Book mapper
 * 
 * @author gpereira
 *
 */
public interface BookMapper {
	/**
	 * Creates a book
	 * @param book the book to create
	 */
	void createBook(Book book);
	
	
	/**
	 * Updates a book
	 * @param book the book to update
	 */
	void updateBook(Book book);
	
	/**
	 * Deletes a book
	 * @param bookId the book Id
	 */
	void deleteBook(@Param("bookId") Integer bookId);
	
	/**
	 * List all books
	 * @return
	 */
	List<Book> listBooks();
}
