package co.ds.mybatis.mapper;

import co.ds.bean.Subscriber;

import java.util.List;

public interface SubscriberMapper {

	List<Subscriber> list();

	void insert(Subscriber subscriber);

	void update(Subscriber subscriber);

	Subscriber fetch(Integer id);
}
