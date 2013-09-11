package fr.gpereira.bookstore.rest.v1x.resource;

import java.util.Arrays;
import java.util.Date;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.byob.db.exception.DAOException;
import com.byob.inject.logger.LoggerModule;
import com.byob.rest.exception.WSException;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.gpereira.bookstore.dao.BookDAO;
import fr.gpereira.bookstore.model.Book;

public class BookResourceTest extends AbstractModule{

	
	private static final int FAKE_ID = 1; 
	
	private BookResource resource;
	private BookDAO dao;
	private Injector injector;
	private Book book;
	
	@Override
	protected void configure() {
		dao = mock(BookDAO.class);
		bind(BookDAO.class).toInstance(dao);
		bind(BookResource.class);
	}
	
	@Before
	public void before() {
		injector = Guice.createInjector(this, new LoggerModule());
		resource = injector.getInstance(BookResource.class);
		assertNotNull(resource);
		book = new Book();		
	}

	@Test
	public void createRetrieveListAndDeleteTest() throws DAOException {
		book.setTitle("title");
		book.setAuthor("author");
		book.setReleaseDate(new Date());
		doAnswer(new Answer<Integer>() {
			@Override
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				book.setId(FAKE_ID);
				return FAKE_ID;
			}
		}).when(dao).create(book);
		final Integer id = resource.add(book);

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				book.setTitle("TADA");
				return null;
			}
		}).when(dao).update(book);

		resource.edit(book);
		
		assertEquals("TADA", book.getTitle());
		
		when(dao.list()).thenReturn(Arrays.asList(book));
		final Book[] books = resource.list();
		assertTrue(Arrays.asList(books).contains(book));

		resource.delete(id);
	}

	@Test(expected = WSException.class)
	public void createFail() throws DAOException {
		doThrow(DAOException.class).when(dao).create(book);
		resource.add(book);
	}

	@Test(expected = WSException.class)
	public void updateFail() throws DAOException {
		doThrow(DAOException.class).when(dao).update(book);
		resource.edit(book);
	}

	
	@Test(expected = WSException.class)
	public void deleteFail() throws DAOException {
		doThrow(DAOException.class).when(dao).delete(FAKE_ID);
		resource.delete(FAKE_ID);
	}
	
	@Test(expected = WSException.class)
	public void listFail() throws DAOException {
		doThrow(DAOException.class).when(dao).list();
		resource.list();
	}


}
