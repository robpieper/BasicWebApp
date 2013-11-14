package co.ds.stripes;

import net.sourceforge.stripes.action.ActionBean;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.MockitoAnnotations.initMocks;

public abstract class AbstractBaseActionTest<ActionClass extends ActionBean> extends AbstractActionTest<ActionClass> {

	@BeforeClass
	public static void beforeClass() {
		final Map<String, String> filterParams = new HashMap<String, String>();
		filterParams.put("ActionResolver.Class", "co.ds.stripes.MockActionResolver");
		filterParams.put("PopulationStrategy.Class", "net.sourceforge.stripes.tag.BeanFirstPopulationStrategy");
		filterParams.put("ActionResolver.Packages", "co.ds.stripes");
		filterParams.put("Extension.Packages", "co.ds.stripes.extensions");
		setupStripesEnvironment("", filterParams);
	}

	@Before
	public void before() throws Exception {
		setupAction();
	}

	@Override
	protected void setupMocks() {
		initMocks(this);
	}
}
