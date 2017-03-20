package co.ds.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Subscriber implements Serializable {

	private Integer id;
	private String name;
	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public List<Integer> getTopicIds() {
		return topicIds;
	}

	public void setTopicIds(final List<Integer> topicIds) {
		this.topicIds = topicIds;
	}
}
