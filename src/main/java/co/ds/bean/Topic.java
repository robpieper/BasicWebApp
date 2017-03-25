package co.ds.bean;

import java.util.ArrayList;
import java.util.List;

public class Topic {

	private Integer id;
	private String name;
	private List<Integer> topicIds = new ArrayList<Integer>();
	
	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<Integer> getTopicIds() {
		return topicIds;
	}
	public void setTopicIds(final List<Integer> topicIds) {
		this.topicIds = topicIds;
	}
}
