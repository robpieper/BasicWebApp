package co.ds.stripes;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.mock.MockHttpSession;
import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.mock.MockServletContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractActionTest<ActionClass extends ActionBean> {

	private static MockServletContext context;

	protected ActionClass action;
	protected Class<ActionClass> actionClassType;
	protected MockHttpSession session;
	protected MockRoundtrip trip;

	@SuppressWarnings("unchecked")
	public AbstractActionTest() {
		actionClassType =
				((Class<ActionClass>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}


	public static void setupStripesEnvironment(final String servletContextName, final Map<String, String> filterParams) {
		if (context == null) {
			context = new MockServletContext(servletContextName);
			context.addFilter(StripesFilter.class, "StripesFilter", filterParams);
			context.setServlet(DispatcherServlet.class, "StripesDispatcher", null);
		}
	}

	@SuppressWarnings("unchecked")
	public void setupAction() throws Exception {

		setupMocks();

		final Field[] fields = this.getClass().getDeclaredFields();
		final Map<Class<?>, Object> fieldMap = new HashMap<Class<?>, Object>();

		for (final Field f : fields) {
			for (final Annotation anno : f.getAnnotations()) {
				if (anno.annotationType().equals(ConstructorParam.class)) {
					f.setAccessible(true);
					fieldMap.put(f.getType(), f.get(this));
					break;
				}
			}
		}

		// get the constructor for the action we are testing (assumption - one constructor)
		final Constructor<?> constructor = actionClassType.getConstructors()[0];

		// build the array for the constructor parameters
		final Class<?>[] constructorTypes = constructor.getParameterTypes();
		final Object[] constructorParms = new Object[constructorTypes.length];

		for (int i = 0; i < constructorParms.length; i++) {
			constructorParms[i] = fieldMap.get(constructorTypes[i]);
		}

		// create the action
		action = (ActionClass) constructor.newInstance(constructorParms);
		session = new MockHttpSession(context);

		getActionResolver().addMockedAction(action);
		trip = new MockRoundtrip(context, actionClassType, session);

	}

	abstract protected void setupMocks();

	public static Configuration getConfiguration() {
		return StripesFilter.getConfiguration();
	}

	public static MockActionResolver getActionResolver() {
		return (MockActionResolver) getConfiguration().getActionResolver();
	}
}
