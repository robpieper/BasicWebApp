package co.ds.stripes;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

public class BaseAction implements ActionBean {

	private ActionBeanContext context;

	public void setContext(final ActionBeanContext context) {
		this.context = context;
	}

	public ActionBeanContext getContext() {
		return context;
	}
}
