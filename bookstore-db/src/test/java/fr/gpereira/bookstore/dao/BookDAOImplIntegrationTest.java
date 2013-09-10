package fr.gpereira.bookstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.byob.db.exception.DAOException;
import com.byob.db.validation.ValidationModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.gpereira.bookstore.model.Book;

/**
 * TODO Create a simple unit test (Use a mock for DB) to create error during list books operation and during delete book operation
 * 
 * @author gpereira
 *
 */
public class BookDAOImplIntegrationTest {
	protected static Injector injector;

	@BeforeClass
	public static void beforeClass() {
		injector = Guice.createInjector(new BookStoreDAOModule("org.h2.Driver",
				"jdbc:h2:~/bookstore", "sa", ""), new ValidationModule());
	}

	private BookDAO dao;
	private Book book;

	@Before
	public void before() {
		dao = injector.getInstance(BookDAO.class);
		assertNotNull(dao);
		book = new Book();
	}

	@Test
	public void create() throws DAOException {
		book.setTitle("title");
		book.setAuthor("author");
		book.setReleaseDate(new Date());
		dao.create(book);
		assertEquals(Integer.valueOf(1), book.getId());
	}

	@Test(expected = DAOException.class)
	public void createFail1() throws DAOException {
		book.setId(1);
		book.setTitle("title");
		book.setAuthor("author");
		book.setReleaseDate(new Date());
		// There should be no id !
		dao.create(book);
	}

	@Test(expected = DAOException.class)
	public void createFail2() throws DAOException {
		book.setAuthor("author");
		book.setReleaseDate(new Date());
		// No title
		dao.create(book);
	}

	@Test(expected = DAOException.class)
	public void createFail3() throws DAOException {
		book.setTitle("title");
		book.setAuthor("au");
		book.setReleaseDate(new Date());
		// Author too short
		dao.create(book);
	}

	@Test
	public void update() throws DAOException {
		book.setId(1);
		book.setTitle("title");
		book.setAuthor("author");
		book.setReleaseDate(new Date());
		dao.update(book);
		assertEquals("title", book.getTitle());
	}

	@Test(expected = DAOException.class)
	public void updateFail1() throws DAOException {
		book.setTitle("title");
		book.setAuthor("author");
		book.setReleaseDate(new Date());
		// No id !
		dao.update(book);
	}

	@Test(expected = DAOException.class)
	public void updateFail2() throws DAOException {
		book.setId(1);
		book.setAuthor("author");
		book.setReleaseDate(new Date());
		// No title !
		dao.update(book);
	}
	
	@Test
	public void delete() throws DAOException {
		dao.delete(2);
	}
	
	@Test(expected = NullPointerException.class)
	public void deleteFail1() throws DAOException {
		dao.delete(null);
	}	
	
	@Test
	public void list() throws DAOException {
		List<Book> books = dao.list();
		System.out.println(books);
		assertEquals(1, books.size());
	}
}
