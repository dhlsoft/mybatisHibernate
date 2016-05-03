package cn.com.zte.ngbc.service.support;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.hibernate.Transaction;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.zte.ngbc.convertor.OrderConverter;
import cn.com.zte.ngbc.dao.BaseDAO;
import cn.com.zte.ngbc.dto.Order;
import cn.com.zte.ngbc.entity.TOrder;
import cn.com.zte.ngbc.mybatis.mapper.OrderMapper;
import cn.com.zte.ngbc.service.OrderService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;


@SuppressWarnings("all")
@Service("ordersServiceImpl")
public class OrdersServiceImpl extends BaseServiceImpl implements OrderService {

	@Autowired
	OrderMapper orderMapper;

	@Resource
	BaseDAO<TOrder> tOrderDAO;

	private static final long serialVersionUID = 1513133416493770048L;

	public String findUser() {
		return "ordersServiceImpl";
	}
	@Override
	public Order get(String id) {
		System.out.println("ordersServiceImpl");
		// hibernate实现
		Transaction transaction=	getCurrentSession().beginTransaction();
		
		SqlSession	session = SqlSessionUtils.getSqlSession(mybatisSessionFactory);
		// return OrderConverter.toDto((TOrder) getCurrentSession().get(
		// TOrder.class, new BigDecimal(Integer.parseInt(id))));
	//	orderMapper.getOrder(Integer.parseInt(id));
		//tOrderDAO.get(TOrder.class, new BigDecimal(Integer.parseInt(id)));
	//	orderMapper.insertOrder(7);
	
		//mybatisSessionFactory.openSession();
		orderMapper.insertOrder(7);
	//	session.commit();
		
			TOrder o=new TOrder();
			o.setId(new BigDecimal(71));
			o.setClientname("saaa");
			tOrderDAO.save(o);
			//getCurrentSession().getTransaction().commit();
			


	//	tOrderDAO.save(o);
		//getCurrentSession().flush();
		//getCurrentSession().clear();
		
		return OrderConverter.toDto(tOrderDAO.get(TOrder.class, new BigDecimal(Integer.parseInt(id))));
	//	TOrder orade_1=	tOrderDAO.get(TOrder.class, new BigDecimal(Integer.parseInt("6")));
	//	TOrder aa=	orderMapper.getOrder(61);
	//	System.out.println(orade_1);
//		Order o1=new Order();
//		o1.setId("123");
//		o1.setClientName("tets");
//		doSave(o1);
		
		// mybatis实现
//		transaction.commit();
	//	return  OrderConverter.toDto(orderMapper.getOrder(Integer.parseInt(id)));
	}

	@Override
	public List<Order> getAll() {
		List<TOrder> entities = orderMapper.getAllOrder();
		List<Order> orders = new ArrayList<Order>();
		for (TOrder entity : entities) {
			orders.add(OrderConverter.toDto(entity));
		}
		return orders;
	}

	@Override
	public void doSave(Order order) {
		// hibernate
		TOrder entity = OrderConverter.toEntity(order);
		if (entity.getId() != null) {
			entity = (TOrder) getCurrentSession().get(TOrder.class,
					entity.getId());

			// update fields
			entity.setClientname(order.getClientName());
			entity.setAmount(new BigDecimal(order.getAmount()));
		}
		getCurrentSession().saveOrUpdate(entity);
		
		//将插入成功后的值，更新到order上，以便UI上绑定时，可以反应出新值
		Order newOrder= OrderConverter.toDto(entity);
		BeanUtils.copyProperties(newOrder, order);
	}

	@Override
	public void doRemove(String id) {
		// mybatis
		orderMapper.deleteOrder(Integer.parseInt(id));
	}

	@Override
	public PageList<TOrder> getAll(int pageIndex, int pageSize)
			throws Exception {
		return (PageList<TOrder>) getPageList(OrderMapper.class, "getAllOrder",
				null, pageIndex, pageSize);

	}

}
