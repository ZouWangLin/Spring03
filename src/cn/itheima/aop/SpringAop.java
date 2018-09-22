package cn.itheima.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 自定义切面类
 */
@Aspect//生命当前类为切面类
@Component//将当前切面类加入IOC容器中
public class SpringAop {
	
	/**
	 * 1.Before前置通知，括号内的参数需要设置切入点（第一个被执行）
	 * 		(1)（完整写法=方法权限+方法返回值+复制方法全路径）
	 * 		(2)可以将方法方法修饰符和方法返回值省略到，最终只要写一个*就可以代替两个东西
	 * 		(3)对于方法的参数问题，如果不考虑参数问题写(..)
	 * 		(4)设置JoinPoint这个参数的目的：为了获取执行的方法名，这个参数会被自动注入
	 */
	@Before("execution(public void cn.itheima.dao.impl.UserDaoImpl.*(..))")
	public void before(JoinPoint joinPoint) {
		//获取方法名
		String methodName = joinPoint.getSignature().getName();
		//获取参数
		Object[] args=joinPoint.getArgs();
		
		System.out.println("开始执行"+methodName+"这个方法"+"参数"+Arrays.asList(args));
	}
	/**
	 * 2.After后置通知（不管是否发生异常都执行，类似finally中的处理），括号内的参数同上（第二个被执行）
	 */
	@After("execution(public void cn.itheima.dao.impl.UserDaoImpl.*(..))")
	public void after(JoinPoint joinPoint) {
		//1.获取方法名
		String methodName = joinPoint.getSignature().getName();
		//2.获取参数
		Object[] args = joinPoint.getArgs();
		System.out.println("方法结束执行"+methodName+"这个方法"+"参数"+Arrays.asList(args));
	}
	
	
	/**
	 * 3.AfterReturning（返回通知）:方法执行完成后执行（第三个被执行）
	 * 		(1)returning属性值一定要和下面括号内的参数一致
	 * 		(2)如果当前方法有返回值，这个时候result就为方法返回值
	 */
	@AfterReturning(pointcut="execution(public void cn.itheima.dao.impl.UserDaoImpl.*(..))",returning="result")
	public void afterRuning(JoinPoint joinPoint,Object result) {
		System.out.println("返回通知结果:"+result);
	}
	
	/**
	 * 4.异常通知:在方法发生异常的时候执行（出现异常时候执行）
	 * 		(1)throwing的属性值一定要和下面括号内的参数一致
	 */
	@AfterThrowing(pointcut="execution(public void cn.itheima.dao.impl.UserDaoImpl.*(..))",throwing="e")
	public void afterThrowing(JoinPoint joinPoint,Exception e) {
		System.out.println("出现异常:"+e);
	}
	
	
	 /**
	  * 5.环绕通知:相当于动态代理的全过程（贯穿整个过程）
	  * 		(1)ProceedingJoinPoint这个对象可用获取方法名，以及和方法有关的其他东西
	  */
	@Around(value="execution(public void cn.itheima.dao.impl.UserDaoImpl.*(..))")
	public void around(ProceedingJoinPoint pjoinPoint) {
		
	}
	
	/**
	 * 6.声明切入点表达式
	 * 		作用：实现切入点表达式的复用，其他方法只需要在注解括号内心写("pointcut()")
	 */
	@Pointcut("execution(public void cn.itheima.dao.impl.UserDaoImpl.*(..))")
	public void pointcut() {};
	
	/**
	 * 7.切面的优先级
	 * （1）添加@Order注解指定切面的优先级
	 * 		eg:@Order(value)，value值越小优先级越高，默认值最大，优先级最低
	 */
	
	/**
	 * 8.根据xml形式配置AOP
	 */

}
