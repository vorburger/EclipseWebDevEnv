package ch.vorburger.modudemo.server.launcher;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Launcher for Server.
 * 
 * This is to avoid writing those horrible *.sh/*.bat launcher scripts which
 * usually come with Java main applications. For the same purpose, ther tools
 * use things like Ant's ant-launcher.jar (org.apache.tools.Launcher), Maven's
 * Plexus Classworld, and similar.
 * 
 * @author Michael Vorburger
 */
public class Launcher {

	// TODO Make it actually work... ;)
	// ch.vorburger.modudemo.server-assembly/src/main/assembly/server/bin/startup.bat
	// launch will lead to a ClassNotFoundException/NoClassDefFoundError: org/eclipse/jetty/util/resource/Resource 
	
	// TODO Logging!
	
	public static void main(String[] args) throws Exception {
		List<File> jarsDirs = new LinkedList<File>();
		
		List<String> argsList = Arrays.asList(args);
		for (String arg : argsList) {
			File dir = new File(arg);
			if (dir.exists()) {
				jarsDirs.add(dir);
				argsList.remove(dir);
				log("Going to look for JARs in " + dir);
			}
		}

		List<URL> classloaderURLs = new LinkedList<URL>();
		for (File jarsDir : jarsDirs) {
			FileFilter filter = new FileFilter() {
				@Override
				public boolean accept(File file) {
					return  file.isFile() && file.getName().endsWith(".jar");
				}
			};
			File[] jarFiles = jarsDir.listFiles(filter);
			for (File file : jarFiles) {
				classloaderURLs.add(file.toURI().toURL());
				log("Found " + file);
			}
		}

		URLClassLoader cl = new URLClassLoader(classloaderURLs.toArray(new URL[0]));
		Thread.currentThread().setContextClassLoader(cl); // ?
		// NOT Main.main(argsList.toArray(new String[0]));
		// Class<?> mainClass = Class.forName("ch.vorburger.modudemo.server.Main", false, cl);
		Class<?> mainClass = cl.loadClass("ch.vorburger.modudemo.server.Main");
		Method mainMethod = mainClass.getMethod("main" /* TODO How to Array of , String.class */);
		mainMethod.invoke(null, null);
	}
	
	private static void log(String msg) {
		System.out.println(msg);
	}
}
