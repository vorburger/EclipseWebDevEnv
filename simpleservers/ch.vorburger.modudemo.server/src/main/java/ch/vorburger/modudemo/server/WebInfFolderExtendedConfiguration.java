package ch.vorburger.modudemo.server;

import java.util.LinkedList;
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
	@SuppressWarnings("unchecked")
	protected List<Resource> findJars(WebAppContext context) throws Exception {
		List<Resource> r = super.findJars(context); // let original WebInfConfiguration do it's thing first
		if (r == null) {
			r = new LinkedList<Resource>();
		}
		
		final List<Resource> containerJarResources = (List<Resource>) context.getAttribute(CONTAINER_JAR_RESOURCES);  
		r.addAll(containerJarResources);
		
		return r;
	}

}
