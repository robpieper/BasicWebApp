package co.ds.stripes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HomeActionTest extends AbstractBaseActionTest<HomeAction> {

	private static final String DISPLAY_FORWARD = "/WEB-INF/jsp/home/index.jsp";

	@Test
	public void should_display_home() throws Exception {
		trip.execute();
		assertEquals("Unexpected resolution", DISPLAY_FORWARD, trip.getDestination());
	}
}
