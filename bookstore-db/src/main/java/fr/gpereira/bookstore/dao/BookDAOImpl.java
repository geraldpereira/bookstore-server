package fr.gpereira.bookstore.dao;

import java.util.List;

import javax.validation.groups.Default;

import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.guice.transactional.Transactional;

import com.byob.db.exception.DAOException;
import com.byob.db.validation.ValidationUtils;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import fr.gpereira.bookstore.mappers.BookMapper;
import fr.gpereira.bookstore.model.Book;
import fr.gpereira.bookstore.model.validation.CreateChecks;
import fr.gpereira.bookstore.model.validation.UpdateChecks;

/**
 * MyBatis implementation of the BookDAO interface
 * 
 * @author gpereira
 *
 */
class BookDAOImpl implements BookDAO {

	private final BookMapper mapper;
	private final ValidationUtils validator;
	
	/**
	 * Constructor
	 * @param mapper MyBatis mapper
	 * @param validator
	 */
	@Inject
	public BookDAOImpl(final BookMapper mapper, final ValidationUtils validator){
		this.mapper = mapper;
		this.validator = validator;
	}
	
	@Override
	@Transactional
	public Integer create(Book book) throws DAOException {
		validator.validate(book, Default.class, CreateChecks.class);
		try{
			mapper.createBook(book);
		}catch(PersistenceException e){
			throw new DAOException("Failed to create book.",e);
		}
		return book.getId();
	}

	@Override
	@Transactional
	public void update(Book book) throws DAOException {
		validator.validate(book, Default.class, UpdateChecks.class);
		try{
			mapper.updateBook(book);
		}catch(PersistenceException e){
			throw new DAOException("Failed to update book.",e);
		}
	}

	@Override
	@Transactional
	public void delete(Integer bookId) throws DAOException {
		try{
			Preconditions.checkNotNull(bookId);
			mapper.deleteBook(bookId);
		}catch(PersistenceException | NullPointerException e){
			throw new DAOException("Failed to delete book.",e);
		}
	}

	@Override
	public List<Book> list() throws DAOException {
		try{
			return mapper.listBooks();
		}catch(PersistenceException e){
			throw new DAOException("Failed to list campaigns.",e);
		}
	}

}
