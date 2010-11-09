package ch.vorburger.modudemo.core.web;

import org.junit.Test;

import ch.vorburger.modudemo.server.ServerLauncher;
import ch.vorburger.modudemo.testutils.SimplisticWebTester;

/**
 * ServerLauncher Test.
 * 
 * @author Michael Vorburger
 */
public class SomethingWebTest {

	@Test
	public void testSomethingViaIndexJSP() throws Exception {
		final ServerLauncher serverLauncher = new ServerLauncher();
		serverLauncher.startServer();

		SimplisticWebTester.assertPageContains("index.txt", "Static Hello World");
		SimplisticWebTester.assertPageContains("index.jsp", "Bla");
		SimplisticWebTester.assertPageContains("index.jsp", "something");
		SimplisticWebTester.assertPageContains("SomeServlet", "SomeServlet!");

		serverLauncher.stopServer();
	}

	// TODO testSomeServlet factored out

}
