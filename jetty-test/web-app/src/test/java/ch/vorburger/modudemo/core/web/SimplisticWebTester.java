package ch.vorburger.modudemo.core.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Very simple Web Test tool.
 * 
 * Meant for illustrative purposes only;
 * in real-world e.g. WebDriver (Selenium 2.0) should be used of course.
 * 
 * @author Michael Vorburger
 */
public class SimplisticWebTester {

	public static void assertPageContains(String url, String string) throws Exception {
		assertThat(fetch(url), containsString(string));
		
	}
	

	private static String fetch(String url) throws MalformedURLException, IOException {
		return httpGet("http://localhost:8080/theapp/" + url);
	}
	
	private static String httpGet(String urlString) throws MalformedURLException, IOException {
		// This is intentionally written with "pure JDK", and not using 
		// Apache HttpComponents, to avoid any external dependencies.
		//
		final URL url = new URL(urlString);
		final URLConnection connection = url.openConnection();
		// connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		connection.setReadTimeout(10000);
		// connection.connect();
		
		int c;
		final InputStream is = connection.getInputStream();
		final Reader r = new InputStreamReader(is);
		final StringWriter sw = new StringWriter();
		while ((c = r.read()) != -1) {
			sw.write(c);
		}
		final String pageContent = sw.toString();
		is.close();
		return pageContent;
	}

}
