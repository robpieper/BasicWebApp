package co.ds.mybatis.mapper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.inject.Inject;

import co.ds.bean.Subscriber;
import co.ds.bean.Topic;


public class TopicMapperTest extends MapperTestBase {
	
	@Inject
	TopicMapper topicMapper;
	
	@Test
	public void should_list() {
		final List<Topic> topicList = topicMapper.list();
		assertEquals("Subscriber list should contain 4 records", 4, topicList.size());
	}

}
