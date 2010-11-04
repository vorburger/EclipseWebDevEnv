/*
 * Copyright (c) 2010 Michael Vorburger
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */
package ch.vorburger.demo;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Sample Jetty-based all-classpath web application starter.
 * 
 * @see https://sites.google.com/site/michaelvorburger/simpleservers
 * 
 * @author Michael Vorburger
 */
public class ServerLauncher {
	
	private static final String WEB_INF_WEB_XML = "WEB-INF/web.xml";

	private int port;
	private String context;
	
	private Server server;
	private WebAppContext webAppContext;

	
	public ServerLauncher() {
		this.port = 8080;
		this.context = "theapp";
	}
	
	public static void main(String[] args) throws Exception {
		ServerLauncher serverLauncher = new ServerLauncher();
		serverLauncher.startServer();
	}

	public void startServer() throws Exception {
		server = new Server();

		SocketConnector connector = new SocketConnector();
		connector.setPort(port);
		
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		server.setConnectors(new Connector[] { connector });
		
		webAppContext = new WebAppContext(webXmlUrl(), "/" + context);
		webAppContext.setLogUrlOnStart(true);
		webAppContext.setServer(server);
		webAppContext.getServletHandler().setStartWithUnavailable(false); // this is great: if WUI couldn't start, don't swallow, but propagate!
		server.setHandler(webAppContext);
		
		// No sure how much use that is, as we'll terminate this via Ctrl-C, but it doesn't hurt either:
		server.setStopAtShutdown(true);
		
		// webAppContext.setTempDirectory(...);

		server.start();
		
		if (!webAppContext.isAvailable() || webAppContext.isFailed() || !webAppContext.isRunning() || !webAppContext.isStarted() 
				|| server.isFailed() || server.isFailed() || !server.isRunning() || !server.isStarted()) 
		{
			// We must (try to) STOP the server, otherwise the forked background
			// thread keeps running, and the JVM won't exit (e.g. in JUnit
			// Tests)
			server.stop();
			if (webAppContext.getUnavailableException() != null) {
				throw new IllegalStateException("Web App in Jetty Server does not seem to have started up; CHECK THE LOG! PS: Chained exception is: ", webAppContext.getUnavailableException()); 
			} else {
				throw new IllegalStateException("Web App in Jetty Server does not seem to have started up; CHECK THE LOG! (NO chained exception)");
			}
		}
	}

	/**
	 * Finds the web.xml
	 * 
	 * @return URL of the web.xml on the Classpath
	 */
	private static String webXmlUrl() throws IOException {
		ClassLoader cl = ServerLauncher.class.getClassLoader(); // OR Thread.currentThread().getContextClassLoader();
		Enumeration urls = cl.getResources(WEB_INF_WEB_XML);
		
		if (!urls.hasMoreElements()) {
			throw new IllegalStateException(WEB_INF_WEB_XML + " not found on the classpath");
		}
	
		URL url = (URL) urls.nextElement();
		if (urls.hasMoreElements()) {
			throw new IllegalStateException(WEB_INF_WEB_XML + " was found more than once on the classpath");
		}
		
		return url.toExternalForm();
	}
}
