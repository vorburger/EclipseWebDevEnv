package test;

import org.apache.log4j.Logger;

public class Greeter {

	public static String message() { Logger.getLogger(Greeter.class).debug("log"); return "Saluton!"; }
}
