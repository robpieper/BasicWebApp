package co.ds.stripes;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.controller.NameBasedActionResolver;

import java.util.HashMap;
import java.util.Map;

public class MockActionResolver extends NameBasedActionResolver {

    Map<Class<? extends ActionBean>, ActionBean> mockActionBeans = new HashMap<Class<? extends ActionBean>, ActionBean>();

    public void addMockedAction(ActionBean actionBean) {
        this.mockActionBeans.put(actionBean.getClass(), actionBean);
    }

    @Override
    protected ActionBean makeNewActionBean(Class<? extends ActionBean> type, ActionBeanContext context) throws Exception {
        ActionBean newActionBean = mockActionBeans.get(type);

        if (newActionBean == null) {
            newActionBean = super.makeNewActionBean(type, context);
        }
        return newActionBean;
    }

    public void cleanup() {
        mockActionBeans.clear();
    }
}
