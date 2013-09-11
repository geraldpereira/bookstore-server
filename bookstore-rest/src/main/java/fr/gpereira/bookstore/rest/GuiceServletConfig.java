package fr.gpereira.bookstore.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;

import com.byob.db.validation.ValidationModule;
import com.byob.inject.logger.LoggerModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import fr.gpereira.bookstore.dao.BookStoreDAOModule;


public class GuiceServletConfig extends GuiceServletContextListener {

	private static final String PROPERTIES_FILE_DEFAULT_VALUE = "inject.properties";
	private static final String PROPERTIES_FILE_SYSTEM_PROPERTY = "properties.file";
	private static final String DB_PASSWORD = "db.password";
	private static final String DB_LOGIN = "db.login";
	private static final String DB_URL = "db.url";
	private static final String DB_DRIVER = "db.driver";
	
	private final Logger log = LoggerModule.newLogger(this.getClass());

	@Override
	protected Injector getInjector() {
		final Properties properties = new Properties();
		final String propertiesFile = System.getProperty(PROPERTIES_FILE_SYSTEM_PROPERTY, PROPERTIES_FILE_DEFAULT_VALUE);
		final InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(propertiesFile);
		
		if (inputStream == null){
			log.error("GuiceServletConfig.getInjector() failed to find properties file : "+propertiesFile);
			return null;
		}
		
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			log.error("GuiceServletConfig.getInjector() failed to load properties file : "+propertiesFile,e);
			return null;
		}
		
		final String dbDriver = properties.getProperty(DB_DRIVER);
		final String dbURL = properties.getProperty(DB_URL);
		final String dbLogin = properties.getProperty(DB_LOGIN);
		final String dbPassword= properties.getProperty(DB_PASSWORD);
		
		log.debug("DB_DRIVER "+dbDriver);
		log.debug("DB_URL "+dbURL);
		log.debug("DB_LOGIN "+dbLogin);
		
		Injector inj =  Guice.createInjector(
				new LoggerModule(), 
				new ResourcesModule(),
				new RESTJerseyServletModule(),
				new BookStoreDAOModule(dbDriver,dbURL,dbLogin,dbPassword),
				new ValidationModule()
				);
		
		return inj;
	}
}