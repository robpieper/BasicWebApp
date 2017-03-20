package co.ds.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubscriberTopicMapper {

	List<Integer> list(Integer subscriberId);

	void insert(@Param("subscriber_id") Integer subscriberId, @Param("topic_id") Integer topicId);

	void deleteForSubscriber(Integer subscriberId);

}
