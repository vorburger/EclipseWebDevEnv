package ch.vorburger.demo.ext;

import org.apache.log4j.Logger;


public class SomethingElse {
	private static final Logger LOGGER = Logger.getLogger(SomethingElse.class);

	public static String message() {
		LOGGER.info("Bonjour");
		return "somethingElse, hallo";
	}
}
