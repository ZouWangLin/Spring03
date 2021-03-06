# Spring的学习-day02

## 1.applicationContext.xml中的配置

- 设置扫描和不扫描哪些包

  ```xml
  
  <!-- 1.设置扫描和不扫描哪些包 
  	   (1)属性resource-pattern:指定扫描哪个包
  	   (2)子标签context:include-filter:用户来设置只扫描哪个包下的类
  	      如果type的值是annotation,那么expression的值是注解的全类名
  	      如果type的值是assignable,那么expression的值是接口或者实现类的全类名
  	      如果想通过子标签context:include-filter来指定只扫描哪个包下的类，必须
  	      设置context:component-scan的use-default-filters的属性为   false
  	   (3)context:exclude-filter:用来设置不扫描哪个包的下类
  -->
  <!-- 设置扫描cn.itheima.entity.Student这个实体类 -->
  <context:component-scan base-package="cn.itheima" use-default-filters="false">
  	<context:exclude-filter type="annotation" 	                                                               expression="cn.itheima.entity.Student"/>
  </context:component-scan>
  ```

- spring4.x提供的依赖注入

  ```xml
  <!-- 2.泛型依赖注入spring4.x提供的 
  	详解:（1）我们创建BaseDao<T>类，又创建了BaseService<T>
  		（2）我们在BaseService<T>中注入BaseDao<T>
  		（3）我们创建一个类StudentService继承BaseService,我们又创建了一个类StudentDao继承                BaseDao，并将创建的对象注入IOC容器中
  		（4）这样一来我们发现StudentDao自动注入到了StudentService中
  -->
  ```

- 手写实现jdk代理类

  ```java
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
  
  ```

- 使用Spring实现代理（基于注解）

  ```xml
  <!-- 4.使用spring中的 aop实现代理（基于注解）
  		实现过程:（1）定义一个类，在此类上加上@Aspectj注解，声明这是一个切面类，还需要将它加入                       IOC容器中
  			       （2）给自定义类的方法添加@Before和@After注解，设置对应的切入点
  			       （3）将需要代理的类加入IOC容器中
  			       （4）这样我们就直接从IOC容器中获取需要的对象
   -->
  <!-- 开启aspectj注解支持 -->
  <aop:aspectj-autoproxy/>
  ```

  ```java
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
          
  		System.out.println("开始执行"+methodName+"这个方法"+"参                                  数"+Arrays.asList(args));
  	}
  	/**
  	 * 2.After后置通知（不管是否发生异常都执行，类似finally中的处理），括号内的参数同上（第二个          被执行）
  	 */
  	@After("execution(public void cn.itheima.dao.impl.UserDaoImpl.*(..))")
  	public void after(JoinPoint joinPoint) {
  		//1.获取方法名
  		String methodName = joinPoint.getSignature().getName();
  		//2.获取参数
  		Object[] args = joinPoint.getArgs();
  		System.out.println("方法结束执行"+methodName+"这个方法"+"参                                数"+Arrays.asList(args));
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
  ```

- 使用spring实现代理（基于xml）

  ```xml
  <!-- 5.基于xml的aop配置（基于xml） -->
  
  <!-- 1.实例化切面类 -->
  <bean class="cn.itheima.aop.SpringAop" id="springAop"></bean>
  
  <!-- 2.基于xml的配置AOP -->
  <aop:config>
  	<!-- 设置切入点 -->
  	<aop:pointcut expression="execution(* cn.itheima.dao.impl.UserDaoImpl.*(..))"       id="pointcut"/>
  	<!-- 配置切面 -->
  	<aop:aspect ref="springAop">
          <!-- 配置对应的前置方法 -->
  		<aop:before method="before" pointcut-ref="pointcut"/>
          <!-- 配置其他方法:after、after-returning -->
  	</aop:aspect>
  </aop:config>
  
  <!-- 3.创建要被代理的类实例 -->
  <bean class="cn.itheima.dao.impl.UserDaoImpl" id="userDao"></bean>
  ```

