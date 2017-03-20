package co.ds.stripes;

import co.ds.bean.Subscriber;
import co.ds.mybatis.mapper.SubscriberMapper;
import co.ds.mybatis.mapper.SubscriberTopicMapper;
import co.ds.mybatis.mapper.TopicMapper;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SubscriberActionTest extends AbstractBaseActionTest<SubscriberAction> {

	private static final String LIST_FORWARD = "/WEB-INF/jsp/subscriber/list.jsp";
	private static final String FORM_FORWARD = "/WEB-INF/jsp/subscriber/form.jsp";
	private static final String HOME_REDIRECT = "/home";
	private static final String SUBSCRIBER_REDIRECT = "/subscriber";
	public static final Integer SUBSCRIBER_ID = 111111;
	public static final String SUBSCRIBER_NAME = "SUBSCRIBER NAME";
	public static final String SUBSCRIBER_EMAIL = "SUBSCRIBER EMAIL";

	@ConstructorParam
	@Mock
	private SubscriberMapper subscriberMapper;
	@ConstructorParam
	@Mock
	private TopicMapper topicMapper;
	@ConstructorParam
	@Mock
	private SubscriberTopicMapper subscriberTopicMapper;

	@Captor
	private ArgumentCaptor<Subscriber> subscriberArgumentCaptor;

	@Test
	public void should_display_form() throws Exception {
		when(subscriberMapper.list()).thenReturn(new ArrayList<Subscriber>() {{
			add(getSubscriber());
		}});
		trip.execute();
		assertEquals("Action should have a subscriber list of 1", 1, action.getSubscribers().size());
		assertEquals("Subscriber should have name of SUBSCRIBER NAME", SUBSCRIBER_NAME, action.getSubscribers().get(0).getName());
		assertEquals("Unexpected resolution", LIST_FORWARD, trip.getDestination());
	}

	@Test
	public void should_cancel_list() throws Exception {
		trip.execute("cancelList");
		assertEquals("Unexpected resolution", HOME_REDIRECT, trip.getRedirectUrl());
	}

	@Test
	public void should_display_edit() throws Exception {
		final List<Integer> subscriberList = new ArrayList<Integer>();
		subscriberList.add(1);
		subscriberList.add(2);
		trip.setParameter("subscriber.id", SUBSCRIBER_ID.toString());
		when(subscriberMapper.fetch(SUBSCRIBER_ID)).thenReturn(getSubscriber());
		when(subscriberTopicMapper.list(SUBSCRIBER_ID)).thenReturn(subscriberList);
		trip.execute("edit");
		assertEquals("Unexpected resolution", FORM_FORWARD, trip.getDestination());
	}

	@Test
	public void should_cancel_form() throws Exception {
		trip.execute("cancelForm");
		assertEquals("Unexpected resolution", SUBSCRIBER_REDIRECT, trip.getRedirectUrl());
	}

	@Test
	public void should_save_new() throws Exception {
		trip.setParameter("subscriber.name", SUBSCRIBER_NAME);
		trip.setParameter("subscriber.email", SUBSCRIBER_EMAIL);
		trip.execute("save");
		verify(subscriberMapper).insert(subscriberArgumentCaptor.capture());
		final Subscriber subscriber = subscriberArgumentCaptor.getValue();
		assertNull("ID should be null", subscriber.getId());
		assertEquals("Unexpected name value", subscriber.getName(), SUBSCRIBER_NAME);
		assertEquals("Unexpected email value", subscriber.getEmail(), SUBSCRIBER_EMAIL);
		assertEquals("Unexpected resolution", SUBSCRIBER_REDIRECT, trip.getRedirectUrl());
	}

	@Test
	public void should_save_update() throws Exception {
		trip.setParameter("subscriber.id", SUBSCRIBER_ID.toString());
		trip.setParameter("subscriber.name", SUBSCRIBER_NAME);
		trip.setParameter("subscriber.email", SUBSCRIBER_EMAIL);
		trip.execute("save");
		verify(subscriberMapper).update(subscriberArgumentCaptor.capture());
		final Subscriber subscriber = subscriberArgumentCaptor.getValue();
		assertEquals("Unexpected ID value", subscriber.getId(), SUBSCRIBER_ID);
		assertEquals("Unexpected name value", subscriber.getName(), SUBSCRIBER_NAME);
		assertEquals("Unexpected email value", subscriber.getEmail(), SUBSCRIBER_EMAIL);
		assertEquals("Unexpected resolution", SUBSCRIBER_REDIRECT, trip.getRedirectUrl());
	}
	/* HELPERS */

	private Subscriber getSubscriber() {
		return new Subscriber() {{
			setId(SUBSCRIBER_ID);
			setName(SUBSCRIBER_NAME);
			setEmail(SUBSCRIBER_EMAIL);
		}};
	}
}
