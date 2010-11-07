package ch.vorburger.demo.server;

public class Main {
	
	// TODO Read a conf/server.properties, set to tmp/, configure a logs/ etc.
	
	public static void main(String[] args) throws Exception {
		final ServerLauncher serverLauncher = new ServerLauncher();
		serverLauncher.startServer();
		// System.exit(r);
	}
}
