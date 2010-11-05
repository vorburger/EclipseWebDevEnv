package pp;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Something {

	private static final Logger LOGGER = Logger.getLogger(Something.class);
	
	static {
		BasicConfigurator.configure();
	}
	
	public static String message() {
		LOGGER.info("Salut");
		// LOGGER.info(SomethingElse.message());
		return "something";
	}
}
