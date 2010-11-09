package ch.vorburger.modudemo.core.web;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.junit.Test;

/**
 * ServerLauncher Test.
 * 
 * @author Michael Vorburger
 */
public class SomethingWebTest {

	private Server server;

	@Test
	public void test() throws Exception {
		startServer();

		SimplisticWebTester.assertPageContains("index.txt", "Static Hello World");
		SimplisticWebTester.assertPageContains("SomeServlet", "SomeServlet!");
		
		server.stop();
	}

	private void startServer() throws Exception {
		server = new Server();

		final SocketConnector connector = new SocketConnector();
		connector.setPort(8080);
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		server.setConnectors(new Connector[] { connector });
		
		WebAppContext webAppContext = new WebAppContext(".", "/theapp");
		webAppContext.setLogUrlOnStart(true);
		// TODO webAppContext.setParentLoaderPriority(false); ???
		// webAppContext.setCompactPath(true);
		webAppContext.setServer(server);
		webAppContext.getServletHandler().setStartWithUnavailable(false); // this is great: if WAR couldn't start, don't swallow, but propagate!
		server.setHandler(webAppContext);
		
		// webAppContext.setTempDirectory(...);
		
		// TODO Review - this will make EVERYTHING on the classpath be
		// scanned for META-INF/resources and web-fragment.xml - great for dev!
		// NOTE: Several patterns can be listed, separate by comma
		webAppContext.setAttribute(WebInfConfiguration.CONTAINER_JAR_PATTERN, ".*");
		
		// No sure how much use that is, as we'll terminate this via Ctrl-C, but it doesn't hurt either:
		server.setStopAtShutdown(true);
		
		server.start();	
	}

}
