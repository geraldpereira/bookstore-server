package fr.gpereira.bookstore.rest;

import java.util.HashMap;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.byob.rest.CORSHeadersFilter;
import com.byob.rest.ObjectMapperProvider;
import com.byob.rest.exception.RestExceptionMapper;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Guice Module used to configure Jersey (REST WS) servlet configuration 
 * 
 * @author gpereira
 *
 */
public class RESTJerseyServletModule extends JerseyServletModule {

	@Override
	protected void configureServlets() {
		bind(RestExceptionMapper.class).asEagerSingleton();		
		bind(ObjectMapperProvider.class).asEagerSingleton();
		
		/* bind jackson converters for JAXB/JSON serialization */
		bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
		bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);

		final HashMap<String, String> initParams = new HashMap<String, String>();
		// <init-param>
		// <param-name>com.sun.jersey.api.json.POJOMappingFeature</param-name>
		// <param-value>true</param-value>
		// </init-param>

		initParams.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
		// ;com.byob.xmltv.rest.v1x
		initParams.put("com.sun.jersey.config.property.packages", "com.byob.rest.exception");
		// http://stackoverflow.com/questions/8275064/google-app-engine-500-error
		initParams.put("com.sun.jersey.config.feature.DisableWADL", "true");

		serve("/api/*").with(GuiceContainer.class, initParams);
		filter("/*").through(CORSHeadersFilter.class);
	}
}
