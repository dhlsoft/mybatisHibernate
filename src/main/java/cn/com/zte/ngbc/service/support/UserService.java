package cn.com.zte.ngbc.service.support;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.com.zte.ngbc.dto.Order;
import cn.com.zte.ngbc.entity.TOrder;
import cn.com.zte.ngbc.service.OrderService;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service("userService")
public class UserService extends BaseServiceImpl implements OrderService{
	public String findUser() {
		return "javaniu";
	}

	@Override
	public Order get(String id) {
		System.out.println("userService");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageList<TOrder> getAll(int pageIndex, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doSave(Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doRemove(String id) {
		// TODO Auto-generated method stub
		
	}
}
