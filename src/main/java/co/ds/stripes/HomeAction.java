package co.ds.stripes;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/home")
public class HomeAction extends BaseAction {

	private static final String DISPLAY_FORWARD = "/WEB-INF/jsp/home/index.jsp";

	@DefaultHandler
	public Resolution display() {
		return new ForwardResolution(DISPLAY_FORWARD);
	}
}
