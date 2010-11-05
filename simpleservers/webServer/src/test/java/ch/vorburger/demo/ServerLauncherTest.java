package ch.vorburger.demo;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * ServerLauncher Test.
 * 
 * @author Michael Vorburger
 */
public class ServerLauncherTest {

	@Test
	public void testStartServer() throws Exception {
		ServerLauncher serverLauncher = new ServerLauncher();
		serverLauncher.startServer();

		assertThat(fetch("index.txt"), containsString("Static Hello World"));
		assertThat(fetch("index.jsp"), containsString("Bla"));
		assertThat(fetch("index.jsp"), containsString("something"));
		assertThat(fetch("index.jsp"), containsString("somethingElse"));
		assertThat(fetch("SomeServlet"), containsString("SomeServlet!"));
		assertThat(fetch("secondo.txt"), containsString("Another static Hello World"));
		assertThat(fetch("secondo.jsp"), containsString("somethingElse"));
		serverLauncher.stopServer();
	}

	private String fetch(String url) throws MalformedURLException, IOException {
		return httpGet("http://localhost:8080/theapp/" + url);
	}
	
	private String httpGet(String urlString) throws MalformedURLException, IOException {
		// This is intentionally written with "pure JDK", and not using 
		// Apache HttpComponents, to avoid any external dependencies.
		//
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		// connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		connection.setReadTimeout(10000);
		// connection.connect();
		
		int c;
		InputStream is = connection.getInputStream();
		Reader r = new InputStreamReader(is);
		StringWriter sw = new StringWriter();
		while ((c = r.read()) != -1) {
			sw.write(c);
		}
		String pageContent = sw.toString();
		is.close();
		return pageContent;
	}

}
