package co.ds.guice;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BasicWebAppGuiceServletContextListenerTest {

	@Test
	public void should_initialize_guice_serlvet_context_listener() {
		BasicWebAppGuiceServletContextListener listener = new BasicWebAppGuiceServletContextListener();
		assertNotNull("Injector should not be null", listener.getInjector());
	}

}
