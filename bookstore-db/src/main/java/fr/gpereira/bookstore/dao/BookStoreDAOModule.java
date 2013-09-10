package fr.gpereira.bookstore.dao;

import java.util.Properties;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;

import com.google.inject.name.Names;

import fr.gpereira.bookstore.mappers.BookMapper;
import fr.gpereira.bookstore.model.Book;

/**
 * Guice module used to bind DAOs and mappers
 * 
 * @author gpereira
 *
 */
public class BookStoreDAOModule extends MyBatisModule {

	private final String driver;
	private final String url;
	private final String username;
	private final String password;
	
	public BookStoreDAOModule(String driver, String url, String username,
			String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	protected void initialize() {
		// Bind DAOs
		bind(BookDAO.class).to(BookDAOImpl.class);
		
		
		// Bind mappers
		bindDataSourceProviderType(PooledDataSourceProvider.class);
		bindTransactionFactoryType(JdbcTransactionFactory.class);
		
		
		final Properties myBatisProperties = new Properties();
		myBatisProperties.setProperty("mybatis.environment.id", "bookstore");
		myBatisProperties.setProperty("JDBC.driver", driver);
		myBatisProperties.setProperty("JDBC.url", url);
		myBatisProperties.setProperty("JDBC.username", username);
		myBatisProperties.setProperty("JDBC.password", password);
		myBatisProperties.setProperty("JDBC.autoCommit", "false");
		
		// avoids autoCommit error when MySQL is restarted
		myBatisProperties.setProperty("poolPingQuery", "select count(id) from book");
		myBatisProperties.setProperty("poolPingEnabled", "true");

		Names.bindProperties(binder(), myBatisProperties);
		
		addMapperClass(BookMapper.class);
		
		addSimpleAlias(Book.class);
	}

}
