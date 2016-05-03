package cn.com.zte.ngbc.mybatis.mapper;

import java.util.List;

import cn.com.zte.ngbc.entity.TOrder;

public interface OrderMapper {

	void insertOrder(int entity);

	List<TOrder> getAllOrder();

	TOrder getOrder(int id);

	void deleteOrder(int id);
}
