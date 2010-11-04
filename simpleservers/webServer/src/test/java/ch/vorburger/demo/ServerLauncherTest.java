package ch.vorburger.demo;

import static org.junit.Assert.*;

import org.junit.Test;

public class ServerLauncherTest {

	@Test
	public void testStartServer() throws Exception {
		ServerLauncher serverLauncher = new ServerLauncher();
		serverLauncher.startServer();
		
		// TODO URL connect, check result
	}

}
