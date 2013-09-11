package fr.gpereira.bookstore.rest.v1x.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.byob.db.exception.DAOException;
import com.byob.inject.logger.InjectLogger;
import com.byob.rest.exception.WSException;
import com.byob.rest.v1x.V1XConstants;
import com.google.inject.Inject;

import fr.gpereira.bookstore.dao.BookDAO;
import fr.gpereira.bookstore.model.Book;

/**
 * 
 * REST endpoint for the Book DAO
 * 
 * @author Gerald PEREIRA
 * 
 */
@Path(V1XConstants.PATH + "/book/")
public class BookResource {

	@InjectLogger
	private Logger log;

	private final BookDAO dao;

	/**
	 * Guice injected constructor
	 * 
	 * Warning : the logger will be null if this constructor is called outside
	 * of Guice
	 * 
	 * @param dao
	 */
	@Inject
	public BookResource(final BookDAO dao) {
		this.dao = dao;
	}

	/**
	 * Creates a nbook
	 * 
	 * @param book the book to create
	 * @return the created book id
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/add")
	public Integer add(final Book book) {
		log.info("Add " + book.toString());
		try {
			return dao.create(book);
		} catch (final DAOException e) {
			log.error("Add failed", e);
			throw new WSException(e);
		}
	}
	
	/**
	 * Edits a book
	 * 
	 * @param book
	 *            the book to edit
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/edit")
	public void edit(final Book book) {
		log.info("Edit " + book.toString());
		try {
			dao.update(book);
		} catch (final DAOException e) {
			log.error("Edit failed", e);
			throw new WSException(e);
		}
	}

	/**
	 * Removes a book
	 * 
	 * @param bookId
	 *            the id of the book to delete
	 */
	@DELETE
	@Path("/delete/{id}")
	public void delete(@PathParam("id") Integer bookId) {
		log.info("Delete " + bookId);
		try {
			dao.delete(bookId);
		} catch (final DAOException e) {
			log.error("Delete failed", e);
			throw new WSException(e);
		}
	}
	
	/**
	 * List all books
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/")
	public Book[] list() throws WSException {
		log.info("List books");
		try {
			return dao.list().toArray(new Book[] {});
		} catch (final DAOException e) {
			log.error("List books", e);
			throw new WSException(e);
		}
	}
	
}
