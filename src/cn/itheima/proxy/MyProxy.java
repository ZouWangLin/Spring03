package cn.itheima.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * 手动实现jdk代理
 */
public class MyProxy implements InvocationHandler{
	private Object target;
	
	public MyProxy(Object target) {
		this.target=target;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//设置前置执行的内容
		System.out.println("代理前置方法执行!");
		//执行方法
		Object result = method.invoke(target, args);
		//设置后置执行的内容
		System.out.println("代理后置方法执行!");
		//返回结果
		return result;
	}
	
	public Object getInstance() {
		//1.获取类加载器
		ClassLoader loader=target.getClass().getClassLoader();
		//2.获取类接口
		Class<?>[] interfaces=target.getClass().getInterfaces();
		return Proxy.newProxyInstance(loader, interfaces, new MyProxy(target));
		
	}
}
