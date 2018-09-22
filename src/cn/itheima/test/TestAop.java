package cn.itheima.test;


import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itheima.dao.UserDao;
import cn.itheima.dao.impl.UserDaoImpl;
import cn.itheima.entity.Student;
import cn.itheima.proxy.MyProxy;
import cn.itheima.service.UserService;
class TestAop {
	
	/**
	 * 1.测试xml中配置的排除扫描
	 */
	@Test
	void test1() {
		//1.初始化IOC容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-annotion.xml");
		//2.从容器中获取对应的IOC对象
		Student student = (Student) applicationContext.getBean("student");
		//3.验证结果
		System.out.println(student);
	}
	
	/**
	 * 2.测试spring中的泛型依赖注入
	 */
	@Test
	void test2() {
		//1.初始化IOC容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-annotion.xml");
		//2.从容器中获取对应的IOC对象
		UserService userService = (UserService) applicationContext.getBean("userService");
		//3.验证结果
		userService.save();
	}
	
	/**
	 * 3.测试自定义代理
	 */
	@Test
	void test3() {
		//1.获取代理对象
		UserDao userDao = (UserDao) new MyProxy(new UserDaoImpl()).getInstance();
		//2.调用代理对象的方法
		userDao.save(10);
		
				
	}
	

	/**
	 * 4.测试spring的自动代理:基于注解
	 */
	@Test
	void test4() {
		//1.初始化IOC容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-annotion.xml");
		//2.从容器中获取对应的IOC对象
		UserDao userDao = (UserDao) applicationContext.getBean("userDao");
		//3.调用对应的方法
		userDao.save(10);
		userDao.update(20);
	}
	
	/**
	 * 5.测试spring的自动代理：基于xml
	 */
	@Test
	void test5() {
		//1.初始化IOC容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-xml.xml");
		//2.从容器中获取对应的IOC对象
		UserDao userDao = (UserDao) applicationContext.getBean("userDao");
		//3.调用对应的方法
		userDao.save(10);
		userDao.update(20);
	}

}
