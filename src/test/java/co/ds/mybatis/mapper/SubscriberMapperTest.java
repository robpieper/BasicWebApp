package co.ds.mybatis.mapper;

import co.ds.bean.Subscriber;
import com.google.inject.Inject;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SubscriberMapperTest extends MapperTestBase {

	public static final String SOMETHING_SOMETHING = "Something Something";
	public static final String DARK_SIDE_EXAMPLE_COM = "dark.side@example.com";
	@Inject
	SubscriberMapper subscriberMapper;

	@Test
	public void should_insert_and_update() {
		final Subscriber subscriber = new Subscriber() {{
			setName("Darth Maul");
			setEmail("splitting@example.com");
		}};
		subscriberMapper.insert(subscriber);
		assertNotNull("ID should not be null", subscriber.getId());
	}

	@Test
	public void should_list() {
		final List<Subscriber> subscriberList = subscriberMapper.list();
		assertEquals("Subscriber list should contain 4 records", 4, subscriberList.size());
	}

	@Test
	public void should_fetch() {
		final List<Subscriber> subscriberList = subscriberMapper.list();
		assertEquals("Subscriber list should contain 4 records", 4, subscriberList.size());

		final Subscriber originalSubscriber = subscriberList.get(0);
		final Subscriber subscriber = subscriberMapper.fetch(originalSubscriber.getId());

		assertEquals("ID should be the same", originalSubscriber.getId(), subscriber.getId());
		assertEquals("Name should be the same", originalSubscriber.getName(), subscriber.getName());
		assertEquals("Email should be the same", originalSubscriber.getEmail(), subscriber.getEmail());

	}

	@Test
	public void should_update() {

		final List<Subscriber> originalSubscriberList = subscriberMapper.list();
		final Subscriber originalSubscriber = originalSubscriberList.get(0);

		originalSubscriber.setName(SOMETHING_SOMETHING);
		originalSubscriber.setEmail(DARK_SIDE_EXAMPLE_COM);
		subscriberMapper.update(originalSubscriber);

		final List<Subscriber> updatedSubscriberList = subscriberMapper.list();
		final Subscriber updatedSubscriber = updatedSubscriberList.get(0);
		assertEquals("Original and Updated subscriber should have the same ID", originalSubscriber.getId(), updatedSubscriber.getId());
		assertEquals("Unexpected name value for updated subscriber", SOMETHING_SOMETHING, updatedSubscriber.getName());
		assertEquals("Unexpected email value for updated subscriber", DARK_SIDE_EXAMPLE_COM, updatedSubscriber.getEmail());

	}

}
