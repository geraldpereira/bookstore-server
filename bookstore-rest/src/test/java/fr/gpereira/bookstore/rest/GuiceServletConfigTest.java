package fr.gpereira.bookstore.rest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.byob.db.exception.DAOException;
import com.byob.rest.ObjectMapperProvider;
import com.google.inject.Injector;

import fr.gpereira.bookstore.dao.BookDAO;
import fr.gpereira.bookstore.mappers.BookMapper;
import fr.gpereira.bookstore.model.Book;

public class GuiceServletConfigTest {

	@Test
	public synchronized void servletConfigTest() throws DAOException {
		System.setProperty("properties.file", "inject.properties");
		final GuiceServletConfig servletConfig = new GuiceServletConfig();
		final Injector injector = servletConfig.getInjector();
		assertNotNull(injector);
		assertNotNull(injector.getInstance(BookMapper.class));
		
		final BookDAO dao = injector.getInstance(BookDAO.class);
		assertNotNull(dao);
		
		final List<Book> books = dao.list();
		assertFalse(books.isEmpty());
		
		final ObjectMapperProvider provider = injector.getInstance(ObjectMapperProvider.class);
		assertTrue(provider.getContext(null) instanceof ObjectMapper);
	}
	
	@Test
	public synchronized void servletConfigNotFoundTest() {
		System.setProperty("properties.file", "notfound.properties");
		GuiceServletConfig servletConfig = new GuiceServletConfig();
		assertNull(servletConfig.getInjector());
	}
	
}
