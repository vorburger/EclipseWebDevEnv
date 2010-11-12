package ch.vorburger.modudemo.server;

import java.util.List;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;

/**
 * Extended WebInfConfiguration.
 * 
 * Helps to accept e.g. web-fragment.xml from anywhere on the classpath,
 * folders or JARs, and not only from JARs neccessarily inside a WEB-INF/lib.
 * 
 * @see WebInfConfiguration
 * 
 * @see TBD
 * 
 * @author Michael Vorburger
 */
public class WebInfFolderExtendedConfiguration extends WebInfConfiguration {

	@Override
	protected List<Resource> findJars(WebAppContext context) throws Exception {
		final List<Resource> r = super.findJars(context); // let original WebInfConfiguration do it's thing first
		
		final List<Resource> containerJarResources = (List<Resource>) context.getAttribute(CONTAINER_JAR_RESOURCES);  
		r.addAll(containerJarResources);
		
		return r;
	}

}
