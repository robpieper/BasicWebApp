package co.ds.stripes;

import java.util.List;

import com.google.inject.Inject;


import co.ds.bean.Topic;

import co.ds.mybatis.mapper.TopicMapper;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

@UrlBinding("/topic")

public class TopicAction extends BaseAction {
	
	private static final String LIST_FORWARD = "/WEB-INF/jsp/topic/list.jsp";
	private static final String FORM_FORWARD = "/WEB-INF/jsp/topic/form.jsp";

	
	private TopicMapper topicMapper;
	

	private List<Topic> topics;

	@ValidateNestedProperties({
			@Validate(on = "save", field = "name", required = true),
			@Validate(on = "save", field = "email", required = true)
	})

	private Topic topic;

	@Inject
	public TopicAction( final TopicMapper topicMapper) {
		//this.subscriberMapper = subscriberMapper;
		this.topicMapper = topicMapper;
		
	}

	@DefaultHandler
	public Resolution list() {
		topics = topicMapper.list();
		return new ForwardResolution(LIST_FORWARD);
	}

	public Resolution cancelList() {
		return new RedirectResolution(HomeAction.class);
	}

	public Resolution edit() {
		topic = topicMapper.fetch(topic.getId());
		final List<Integer> topics = topicMapper.list(topic.getId());
		topic.setTopicIds(topics);
		return form();
	}

	public Resolution form() {
		topics = topicMapper.list();
		return new ForwardResolution(FORM_FORWARD);
	}

	public Resolution cancelForm() {
		return new RedirectResolution(SubscriberAction.class);
	}

	public Resolution save() {
		if (topic.getId() == null) {
			topicMapper.insert(topic);
			for(final Integer topicId : topic.getTopicIds()) {
				topicMapper.insert(topic.getId(), topicId);
			}
		} else {
			topicMapper.update(topic);
			topicMapper.deleteForTopic(topic.getId());
			for(final Integer topicId : topic.getTopicIds()) {
				topicMapper.insert(topic.getId(), topicId);
			}
		}
		return new RedirectResolution(SubscriberAction.class);
	}

	/* READ-ONLY */


	/* READ/WRITE */


	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(final List<Topic> topics) {
		this.topics = topics;
	}

	
	

}
