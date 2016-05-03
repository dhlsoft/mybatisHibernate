package cn.com.zte.ngbc.test;

import java.math.BigDecimal;

import org.springframework.context.support.GenericXmlApplicationContext;

import cn.com.zte.ngbc.dto.Order;
import cn.com.zte.ngbc.entity.TOrder;
import cn.com.zte.ngbc.service.OrderService;
import cn.com.zte.ngbc.service.support.BaseServiceImpl;
import cn.com.zte.ngbc.service.support.OrdersServiceImpl;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.hibernate.engine.transaction.internal.TransactionImpl;


public class Testdb {

	public static void main(String[] args) throws InterruptedException {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.setValidating(false);
		context.load("classpath*:spring.xml");
		context.refresh();
	   OrderService userService = (OrderService) context.getBean("ordersServiceImpl");
		//while (true) {
		userService.get("1");
//		int a=0;
//		while(a<100000){
//			new Thread(new Runnable(){
//				public void run(){
//					userService.get("1");
//					System.out.println("running");}
//				}).start();
//			a++;
//		}
			
		//	System.out.println(userService.get("1"));
		//	userService.get("1");
//			Thread.sleep(10000);
//		}
	}
	
	private static void name() {
		
		
//		OrdersServiceImpl
	}
	
	
}
