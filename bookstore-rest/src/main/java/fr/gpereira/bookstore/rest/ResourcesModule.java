package fr.gpereira.bookstore.rest;

import com.google.inject.AbstractModule;

import fr.gpereira.bookstore.rest.v1x.resource.BookResource;

/**
 * Guice module to configure REST resources
 * 
 * @author Gerald PEREIRA
 *
 */
public class ResourcesModule extends AbstractModule{

	@Override
	protected void configure() {
		/* bind the REST resources */
		bind(BookResource.class);
	}

}
