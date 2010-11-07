package ch.vorburger.demo.server;

import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * WebAppContext allowing to register additional Configurations.
 * 
 * @see TBD
 * 
 * @author Michael Vorburger
 */
public class WebContextWithExtraConfigurations extends WebAppContext {

	public WebContextWithExtraConfigurations(String webApp,String contextPath) {
		super(webApp, contextPath);
	}

	public <T extends Configuration> void replaceConfiguration(Class<T> toReplace, Configuration newConfiguration) throws Exception {
		loadConfigurations(); // Force loading of default configurations
		final Configuration[] configs = getConfigurations();
		for (int i = 0; i < configs.length; i++) {
			if (configs[i].getClass().equals(toReplace)) {
				configs[i] = newConfiguration;
			}
		}
	}
	
	public void addConfiguration(Configuration configuration) throws Exception {
		loadConfigurations(); // Force loading of default configurations
		final Configuration[] configs = getConfigurations();
		final Configuration[] newConfig = new Configuration[configs.length + 1];
		newConfig[configs.length] = configuration;
		setConfigurations(newConfig);
	}

}
