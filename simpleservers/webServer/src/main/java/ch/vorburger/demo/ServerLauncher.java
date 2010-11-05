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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
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
	private static final String WEB_TAG_FILE = ".web";

	private final int port;
	private final String context;
	
	private Server server;
	private WebAppContext webAppContext;

	
	public ServerLauncher() {
		this.port = 8080;
		this.context = "theapp";
	}
	
	public static void main(String[] args) throws Exception {
		final ServerLauncher serverLauncher = new ServerLauncher();
		serverLauncher.startServer();
	}

	public void startServer() throws Exception {
		if (server != null) {
			throw new IllegalStateException("HTTP Server already running, stop it first before starting it again");
		}
		server = new Server();

		final SocketConnector connector = new SocketConnector();
		connector.setPort(port);
		
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		server.setConnectors(new Connector[] { connector });
		
		webAppContext = new WebAppContext(null, "/" + context);
		webAppContext.setBaseResource(baseResources());
		webAppContext.setLogUrlOnStart(true);
		webAppContext.setServer(server);
		webAppContext.getServletHandler().setStartWithUnavailable(false); // this is great: if WAR couldn't start, don't swallow, but propagate!
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

	public void stopServer() throws Exception {
		webAppContext.stop();
		webAppContext = null;
		server.stop();
		server = null;
	}
	
	
	private ResourceCollection baseResources() throws IOException, MalformedURLException {
		final List<Resource> webResourceModules = new LinkedList<Resource>();
		webResourceModules.add(chop(webXmlUrl(), WEB_INF_WEB_XML));
		final Collection<URL> webTagURLs = getResources(WEB_TAG_FILE);
		for (final URL webTagFileURL : webTagURLs) {
			webResourceModules.add(chop(webTagFileURL, WEB_TAG_FILE));
		}
		return new ResourceCollection(webResourceModules.toArray(new Resource[webResourceModules.size()]));
	}

	private Resource chop(final URL baseURL, String toChop) throws MalformedURLException, IOException {
		String base = baseURL.toExternalForm();
		if (!base.endsWith(toChop)) {
			throw new IllegalArgumentException(base + " does not endWith " + toChop);
		}
		base = base.substring(0, base.length() - toChop.length());
		return Resource.newResource(base);
	}

	/**
	 * Finds the web.xml
	 * 
	 * @return URL of the web.xml on the Classpath
	 */
	private static URL webXmlUrl() throws IOException {
		final Collection<URL> urls = getResources(WEB_INF_WEB_XML);
		if (urls.isEmpty()) {
			throw new IllegalStateException(WEB_INF_WEB_XML + " not found on the classpath");
		}
		if (urls.size() !=1) {
			throw new IllegalStateException(WEB_INF_WEB_XML + " was found more than once on the classpath");
		}
		return urls.iterator().next();
	}

	private static Collection<URL> getResources(String resource) throws IOException {
		final ClassLoader cl = ServerLauncher.class.getClassLoader(); // OR Thread.currentThread().getContextClassLoader();
		final Enumeration<URL> urls = cl.getResources(resource);
		final LinkedList<URL> list = new LinkedList<URL>();
		while (urls.hasMoreElements()) {
			list.add(urls.nextElement());
		}
		return list;
	}
}
