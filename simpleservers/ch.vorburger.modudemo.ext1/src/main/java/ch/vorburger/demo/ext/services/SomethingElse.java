package ch.vorburger.demo.ext.services;

import org.apache.log4j.Logger;

import ch.vorburger.demo.core.services.Something;


public class SomethingElse {
	private static final Logger LOGGER = Logger.getLogger(SomethingElse.class);

	public static String message() {
		LOGGER.info("Bonjour");
		return Something.message() + "; somethingElse, hallo";
	}
}
