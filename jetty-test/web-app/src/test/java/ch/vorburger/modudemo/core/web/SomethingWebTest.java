package ch.vorburger.modudemo.core.web;

import org.apache.catalina.Server;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;
import org.junit.Test;

/**
 * Tomcat embedding, instead of Jetty.
 * 
 * @see http://www.copperykeenclaws.com/embedding-tomcat-7/
 * 
 * @author Michael Vorburger
 */
public class SomethingWebTest {

	private Server server;

	@Test
	public void test() throws Exception {
		startServer();

		SimplisticWebTester.assertPageContains("webapp-filebased.txt", "Duh");
		SimplisticWebTester.assertPageContains("index.txt", "Static Hello World");
		SimplisticWebTester.assertPageContains("SomeServlet", "SomeServlet!");

		server.stop();
	}

	private void startServer() throws Exception {
		String appBase = "src/main/webapp"; // TODO ???
		
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		tomcat.setBaseDir("."); // ???
		tomcat.getHost().setAppBase("."); // TODO ???

		StandardServer server = (StandardServer) tomcat.getServer();
		AprLifecycleListener listener = new AprLifecycleListener();
		server.addLifecycleListener(listener);

		tomcat.addWebapp("/theapp", appBase);
		tomcat.start();
		// tomcat.getServer().await();
	}

}
