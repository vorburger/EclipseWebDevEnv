package ch.vorburger.modudemo.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jetty.util.resource.Resource;

public final class Util {
	private Util() {
	}

	public static Resource chop(final URL baseURL, String toChop) throws MalformedURLException, IOException {
		String base = baseURL.toExternalForm();
		if (!base.endsWith(toChop)) {
			throw new IllegalArgumentException(base + " does not endWith " + toChop);
		}
		base = base.substring(0, base.length() - toChop.length());
		return Resource.newResource(base);
	}
}
