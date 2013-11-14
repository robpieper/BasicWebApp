package co.ds.guice;

import co.ds.servlet.HsqlDbSetupServlet;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.binder.AnnotatedBindingBuilder;
import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.DynamicMappingFilter;
import net.sourceforge.stripes.controller.StripesFilter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class BasicServletModuleTest {

	public static final String COM_GOOGLE_INJECT_SERVLET_INTERNAL_SERVLET_MODULE = "com.google.inject.servlet.InternalServletModule";
	public static final String COM_GOOGLE_INJECT_SERVLET_FILTERS_MODULE_BUILDER = "com.google.inject.servlet.FiltersModuleBuilder";
	public static final String COM_GOOGLE_INJECT_SERVLET_SERVLETS_MODULE_BUILDER = "com.google.inject.servlet.ServletsModuleBuilder";
	@Mock
	public AnnotatedBindingBuilder<DynamicMappingFilter> dynamicMappingFilterAnnotatedBindingBuilder;
	@Mock
	public AnnotatedBindingBuilder<HsqlDbSetupServlet> hsqlDbSetupServletAnnotatedBindingBuilder;
	@Mock
	public AnnotatedBindingBuilder<DispatcherServlet> dispatcherServletAnnotatedBindingBuilder;
	@Mock
	public AnnotatedBindingBuilder<StripesFilter> stripesFilterAnnotatedBindingBuilder;
	@Mock
	public Binder builder;

	@Before
	public void before() {
		initMocks(this);
	}

	@Test
	public void should_build_servlet_module() throws NoSuchFieldException, IllegalAccessException {

		final BasicServletModule appServletModule = new BasicServletModule();

		when(builder.bind(HsqlDbSetupServlet.class)).thenReturn(hsqlDbSetupServletAnnotatedBindingBuilder);
		when(builder.bind(DispatcherServlet.class)).thenReturn(dispatcherServletAnnotatedBindingBuilder);
		when(builder.bind(StripesFilter.class)).thenReturn(stripesFilterAnnotatedBindingBuilder);
		when(builder.bind(DynamicMappingFilter.class)).thenReturn(dynamicMappingFilterAnnotatedBindingBuilder);
		final Map<String, AbstractModule> moduleMap = new HashMap<String, AbstractModule>();
		doAnswer(new Answer() {
			public Object answer(final InvocationOnMock invocationOnMock) throws Throwable {
				for (Object argument : invocationOnMock.getArguments()) {
					if (argument.getClass().getName().equals(COM_GOOGLE_INJECT_SERVLET_INTERNAL_SERVLET_MODULE)) {
						moduleMap.put(COM_GOOGLE_INJECT_SERVLET_INTERNAL_SERVLET_MODULE, (AbstractModule) argument);
					} else if (argument.getClass().getName().equals(COM_GOOGLE_INJECT_SERVLET_FILTERS_MODULE_BUILDER)) {
						moduleMap.put(COM_GOOGLE_INJECT_SERVLET_FILTERS_MODULE_BUILDER, (AbstractModule) argument);
					} else if (argument.getClass().getName().equals(COM_GOOGLE_INJECT_SERVLET_SERVLETS_MODULE_BUILDER)) {
						moduleMap.put(COM_GOOGLE_INJECT_SERVLET_SERVLETS_MODULE_BUILDER, (AbstractModule) argument);

					}
				}
				return null;
			}
		}).when(builder).install(isA(AbstractModule.class));

		appServletModule.configure(builder);

		verify(dynamicMappingFilterAnnotatedBindingBuilder, atLeastOnce()).in(Singleton.class);
		verify(dispatcherServletAnnotatedBindingBuilder, atLeastOnce()).in(Singleton.class);
		verify(stripesFilterAnnotatedBindingBuilder, atLeastOnce()).in(Singleton.class);

		assertTrue("Unexpected number of submodules", moduleMap.size() == 3);

		final AbstractModule servletsModuleBuilder = moduleMap.get(COM_GOOGLE_INJECT_SERVLET_SERVLETS_MODULE_BUILDER);
		assertServlets(servletsModuleBuilder);

		final AbstractModule filtersModuleBuilder = moduleMap.get(COM_GOOGLE_INJECT_SERVLET_FILTERS_MODULE_BUILDER);
		assertFilters(filtersModuleBuilder);

	}

	private void assertServlets(final AbstractModule servletsModuleBuilder) throws IllegalAccessException, NoSuchFieldException {
		final Class servletsModuleBuilderClass = servletsModuleBuilder.getClass();
		final Field servletDefinitionsField = servletsModuleBuilderClass.getDeclaredField("servletDefinitions");
		servletDefinitionsField.setAccessible(true);
		final ArrayList servletDefinitionList = (ArrayList) servletDefinitionsField.get(servletsModuleBuilder);
		assertTrue("Unexpected number of servlet definitions", servletDefinitionList.size() == 2);

		final Field pattern = servletDefinitionList.get(0).getClass().getDeclaredField("pattern");
		pattern.setAccessible(true);
		final Field servletKey = servletDefinitionList.get(0).getClass().getDeclaredField("servletKey");
		servletKey.setAccessible(true);

		final Object hsqlDbSetupServletDefinition = servletDefinitionList.get(0);

		final String hsqlDbPatternValue = (String) pattern.get(hsqlDbSetupServletDefinition);
		assertNotNull("Pattern should not be null", hsqlDbPatternValue);
		assertEquals("Unexpected pattern", "/hidden/setup", hsqlDbPatternValue);

		final Object dispatchServletDefinition = servletDefinitionList.get(1);

		final String dispatchServletPatternValue = (String) pattern.get(dispatchServletDefinition);
		assertNotNull("Pattern should not be null", dispatchServletPatternValue);
		assertEquals("Unexpected pattern", "*.action", dispatchServletPatternValue);

		final Key servletKeyValue = (Key) servletKey.get(dispatchServletDefinition);
		assertEquals("Unexpected servlet type", DispatcherServlet.class, servletKeyValue.getTypeLiteral().getRawType());

	}

	private void assertFilters(final AbstractModule filterModuleBuilder) throws IllegalAccessException, NoSuchFieldException {
		final Class filterModuleBuilderClass = filterModuleBuilder.getClass();
		final Field filterDefinitionsField = filterModuleBuilderClass.getDeclaredField("filterDefinitions");
		filterDefinitionsField.setAccessible(true);
		final ArrayList filterDefinitionList = (ArrayList) filterDefinitionsField.get(filterModuleBuilder);
		assertTrue("Unexpected number of filter definitions", filterDefinitionList.size() == 2);
		final Class filterDefinitionClass = filterDefinitionList.get(0).getClass();
		final Field filterKey = filterDefinitionClass.getDeclaredField("filterKey");
		filterKey.setAccessible(true);
		final Field pattern = filterDefinitionClass.getDeclaredField("pattern");
		pattern.setAccessible(true);
		final Field initParams = filterDefinitionClass.getDeclaredField("initParams");
		initParams.setAccessible(true);

		for (Object filterDefinition : filterDefinitionList) {
			final Key filterKeyValue = (Key) filterKey.get(filterDefinition);
			assertNotNull("Filter Key should not be null", filterKeyValue.getTypeLiteral());

			final Class rawType = filterKeyValue.getTypeLiteral().getRawType();

			if (rawType == StripesFilter.class) {
				assertStripesFilter(pattern, initParams, filterDefinition);
			} else if (rawType == DynamicMappingFilter.class) {
				assertDynamicMappingFilter(pattern, initParams, filterDefinition);
			}

		}
	}

	private void assertDynamicMappingFilter(final Field pattern, final Field initParams, final Object filterDefinition) throws IllegalAccessException {
		final String patternValue = (String) pattern.get(filterDefinition);
		assertNotNull("Pattern should not be null for dynamic mapping filter", patternValue);
		assertEquals("Unexpected pattern value for dynamic mapping filter", "/*", patternValue);
		final Map initParamsValue = (Map) initParams.get(filterDefinition);
		assertTrue("Filter init params should be empty for dynamic mapping filter ", initParamsValue.isEmpty());
	}

	private void assertStripesFilter(final Field pattern, final Field initParams, final Object filterDefinition) throws IllegalAccessException {
		final String patternValue = (String) pattern.get(filterDefinition);
		assertNotNull("Pattern should not be null for stripes filter", patternValue);
		assertEquals("Unexpected pattern value for stripes filter", "", patternValue);
		final Map initParamsValue = (Map) initParams.get(filterDefinition);
		assertTrue("Filter init params should not be empty for stripes filter ", !initParamsValue.isEmpty());
	}

}
