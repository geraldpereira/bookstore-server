package fr.gpereira.bookstore.dao;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.Before;
import org.junit.Test;

import com.byob.db.exception.DAOException;
import com.byob.db.validation.ValidationModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.gpereira.bookstore.mappers.BookMapper;
import fr.gpereira.bookstore.model.Book;

/**
 * 
 * @author gpereira
 *
 */
public class BookDAOImplTest extends AbstractModule{
	private Injector injector;
	private BookDAO dao;
	private BookMapper mapper;
	private Book book;
	
	@Override
	protected void configure() {
		mapper = mock(BookMapper.class);
		bind(BookMapper.class).toInstance(mapper);
		bind(BookDAO.class).to(BookDAOImpl.class);
		
	}

	@Before
	public void before() {
		injector = Guice.createInjector(this, new ValidationModule());
		dao = injector.getInstance(BookDAO.class);
		assertNotNull(dao);
		book = new Book();
	}
	
	@Test(expected = DAOException.class)
	public void createFail1() throws DAOException {
		doThrow(PersistenceException.class).when(mapper).createBook(any(Book.class));
		book.setTitle("title");
		book.setAuthor("author");
		book.setReleaseDate(new Date());
		dao.create(book);
	}	
	
	
	@Test(expected = DAOException.class)
	public void updateFail1() throws DAOException {
		doThrow(PersistenceException.class).when(mapper).updateBook(any(Book.class));
		book.setId(1);
		book.setTitle("title");
		book.setAuthor("author");
		book.setReleaseDate(new Date());
		dao.update(book);
	}	
	
	
	@Test(expected = DAOException.class)
	public void deleteFail1() throws DAOException {
		doThrow(PersistenceException.class).when(mapper).deleteBook(1);
		dao.delete(1);
	}	
	
	@Test(expected = DAOException.class)
	public void listFail1() throws DAOException {
		doThrow(PersistenceException.class).when(mapper).listBooks();
		dao.list();
	}
}
