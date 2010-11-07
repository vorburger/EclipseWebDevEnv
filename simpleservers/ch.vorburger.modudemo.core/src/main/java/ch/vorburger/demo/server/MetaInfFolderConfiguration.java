package ch.vorburger.demo.server;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * MetaInfFolderConfiguration.
 * 
 * Extension of MetaInfConfiguration, which also scans META-INF of
 * all folders (not only JARs) on the classpath to find resources, web-fragment.xml, tld.
 * 
 * @see MetaInfConfiguration
 * 
 * @see TBD
 * 
 * @author Michael Vorburger
 */
public class MetaInfFolderConfiguration extends MetaInfConfiguration {

	@Override
	@SuppressWarnings("unchecked")
	public void preConfigure(final WebAppContext context) throws Exception {
		super.preConfigure(context); // let original MetaInfConfiguration do it's thing first
		
        final ArrayList<Resource> resources = new ArrayList<Resource>();
        resources.addAll((List<Resource>)context.getAttribute(MetaInfConfiguration.CONTAINER_JAR_RESOURCES));

        for (final Resource resource : resources) {
        	final Resource metaInfResourcesResource = resource.addPath("META-INF/resources/");
        	// TODO Am I doing *.jar again, like this, and will have duplicate entries?
			if (metaInfResourcesResource.exists() && resource.isDirectory()) {
				addResource(context, MetaInfConfiguration.METAINF_RESOURCES, metaInfResourcesResource);
			}
			
			// TODO "META-INF/web-fragment.xml"
			
			// TODO "*.tld" handling
		}
	}

}
