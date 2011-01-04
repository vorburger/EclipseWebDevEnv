package ch.vorburger.modudemo.core.web;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Test;

import ch.vorburger.modudemo.server.ServerLauncher;

/**
 * ServerLauncher Test.
 * 
 * Makes sure that the ServletContext resources works as intended.
 * 
 * @author Michael Vorburger
 */
public class ServletContextTest {

	/**
	 * Check that the Struts JAR is seen in WEB-INF/lib
	 * (even if, physically, it isn't).
	 */
	@Test
	public void testServletContextResources() throws Exception {
		final ServerLauncher serverLauncher = new ServerLauncher();
		serverLauncher.startServer();
		
		ServletContext sc = serverLauncher.getServletContext();
		checkServlet(sc);
		serverLauncher.stopServer();
	}
	
	private void checkServlet(ServletContext sc) throws Exception {
		Set<String> jars = sc.getResourcePaths("/WEB-INF/lib");
		for (String jar : jars) {
			if (!jar.startsWith("/WEB-INF/lib")) {
				Assert.fail("Huh, I'm looking into WEB-INF/lib and something doesn't start with WEB-INF/lib: " + jar);
			}
			if (jar.contains("struts")) {
				URL resourceURL = sc.getResource(jar);
				if (!resourceURL.getFile().contains("struts")) {
					Assert.fail("Found '*struts*' JAR: " + jar + ", but something is wrong with the URL: " + resourceURL);
				}
				InputStream is = sc.getResourceAsStream(jar);
				Assert.assertNotNull(is);
				is.close();
				return; // OK!
			}
		}
		Assert.fail("No '*struts*' JAR seen: " + jars);
	}
}
