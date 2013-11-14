package co.ds.stripes;

import co.ds.bean.Subscriber;
import co.ds.mybatis.mapper.SubscriberMapper;
import com.google.inject.Inject;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import java.util.List;

@UrlBinding("/subscriber")
public class SubscriberAction extends BaseAction {

	private static final String LIST_FORWARD = "/WEB-INF/jsp/subscriber/list.jsp";
	private static final String FORM_FORWARD = "/WEB-INF/jsp/subscriber/form.jsp";

	private SubscriberMapper subscriberMapper;

	private List<Subscriber> subscribers;

	@ValidateNestedProperties({
			@Validate(on = "save", field = "name", required = true),
			@Validate(on = "save", field = "email", required = true)
	})
	private Subscriber subscriber;

	@Inject
	public SubscriberAction(final SubscriberMapper subscriberMapper) {
		this.subscriberMapper = subscriberMapper;
	}

	@DefaultHandler
	public Resolution list() {
		subscribers = subscriberMapper.list();
		return new ForwardResolution(LIST_FORWARD);
	}

	public Resolution cancelList() {
		return new RedirectResolution(HomeAction.class);
	}

	public Resolution edit() {
		subscriber = subscriberMapper.fetch(subscriber.getId());
		return form();
	}

	public Resolution form() {
		return new ForwardResolution(FORM_FORWARD);
	}

	public Resolution cancelForm() {
		return new RedirectResolution(SubscriberAction.class);
	}

	public Resolution save() {
		if (subscriber.getId() == null) {
			subscriberMapper.insert(subscriber);
		} else {
			subscriberMapper.update(subscriber);
		}
		return new RedirectResolution(SubscriberAction.class);
	}

	/* READ-ONLY */

	public Subscriber getSubscriber() {
		return subscriber;
	}

	/* READ/WRITE */

	public List<Subscriber> getSubscribers() {
		return subscribers;
	}

	public void setSubscriber(final Subscriber subscriber) {
		this.subscriber = subscriber;
	}
}
