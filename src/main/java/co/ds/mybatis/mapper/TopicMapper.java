package co.ds.mybatis.mapper;


import co.ds.bean.Topic;

import java.util.List;

public interface TopicMapper {

	List<Topic> list();
	
	void insert(Topic topic);

	void update(Topic topic);

	Topic fetch(Integer id);

	void insert(Integer id, Integer topicId);

	void deleteForTopic(Integer id);

	List<Integer> list(Integer id);

}
