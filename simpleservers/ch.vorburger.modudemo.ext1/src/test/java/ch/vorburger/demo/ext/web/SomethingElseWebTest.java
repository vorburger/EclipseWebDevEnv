package ch.vorburger.demo.ext.web;

import org.junit.Test;

import ch.vorburger.modudemo.server.ServerLauncher;
import ch.vorburger.modudemo.testutils.SimplisticWebTester;

public class SomethingElseWebTest {

	@Test
	public void testSomethingElseViaSecondoJSP() throws Exception {
		final ServerLauncher serverLauncher = new ServerLauncher();
		serverLauncher.startServer();

		SimplisticWebTester.assertPageContains("secondo.txt", "Another static Hello World");
		SimplisticWebTester.assertPageContains("secondo.jsp", "somethingElse");
		SimplisticWebTester.assertPageContains("AnotherServlet", "AnotherServlet!");
		
		serverLauncher.stopServer();
	}


}
