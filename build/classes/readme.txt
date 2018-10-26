
环境准备

1，使用开发工具
（a）eclipse,官方下载地址https://www.eclipse.org/downloads/
（b）myeclipse,下载地址http://www.myeclipsecn.com/ 或者 https://www.genuitec.com/products/myeclipse/
（c）idea,下载地址https://www.jetbrains.com/idea/
（d）spring tool,下载地址http://spring.io/tools

2，jdk环境，1.7或者1.8都可以。https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

3，web容器tomcat 下载地址http://tomcat.apache.org/。

4，下载准备spring mvc 所需要的jar

（a）下载地址：http://repo.spring.io/release/org/springframework/
（b）jar包简介 https://www.cnblogs.com/leskang/p/5426829.html


注意使用高版本的jdk，需要对应下载高版本的mvc jar包，防止版本不对应，导致的问题。
规则大致为：

JDK 8 中可以使用 Spring Framework 5.x

JDK 7 中可以使用 Spring Framework 4.x

JDK 6 中可以使用 Spring Framework 4.x

JDK 5 中可以使用 Spring Framework 3.x


搭建spring mvc，以eclipse开发工具为例

1,创建项目，新建Dynamic Web Project。

（为了使项目结构和myeclipse 结构一样，新建项目的时候可以将build\classes修改为WebRoot\WEB-INF\classes，如果修改则将web Model中的Content directory改成WebRoot（原先是WebContent））

2，添加jar包

3，配置web.xml,核心是配置DispatcherServlet
<servlet>
    <servlet-name>action</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath*:action-servlet.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>action</servlet-name>
    <url-pattern>*.do</url-pattern>
  </servlet-mapping>
  
  
 4，亦可以先配置spring mvc所需要的配置信息，通过<context-param>标签：
 
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath*:applicationContext.xml</param-value>
  </context-param>
 
 <context-param>初始化过程：
     在启动Web项目时，容器(比如Tomcat)会读web.xml配置文件中的两个节点<listener>和<contex-param>。
     接着容器会创建一个ServletContext(上下文),应用范围内即整个WEB项目都能使用这个上下文。
     接着容器会将读取到<context-param>转化为键值对,并交给ServletContext。
     容器创建<listener></listener>中的类实例,即创建监听（备注：listener定义的类可以是自定义的类但必须需要继承ServletContextListener）。
     在监听的类中会有一个contextInitialized(ServletContextEvent event)初始化方法，在这个方法中可以通过event.getServletContext().getInitParameter("contextConfigLocation") 来得到context-param 设定的值。在这个类中还必须有一个contextDestroyed(ServletContextEvent event) 销毁方法.用于关闭应用前释放资源，比如说数据库连接的关闭。
     得到这个context-param的值之后,你就可以做一些操作了.注意,这个时候你的WEB项目还没有完全启动完成.这个动作会比所有的Servlet都要早。
  
5,新建Controller类

拓展：注解含义
@Component         把对象加入ioc容器，对象引用名称是类名，第一个字母小写
@Component(“name”) 把指定名称的对象，加入ioc容器
@Repository        主要用于标识加入容器的对象是一个持久层的组件(类)
@Service           主要用于标识加入容器的对象是一个业务逻辑层的组件
@Controller        主要用于标识加入容器的对象是一个控制层的组件
@Resource          注入属性(DI), 会从容器中找对象注入到@Resource修饰的对象上
@Autowired         注入属性(DI), 会从容器中找对象注入到@Autowired修饰的对象上

6，测试



核心扩展：

1，配置数据库
   
   (a),配置jdbc.properties数据库连接信息,PropertyPlaceholderConfigurer使用类加载配置信息
   	<bean id="propertyConfigurer" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
		<property name="locations">
			<list>
				<value>classpath*:jdbc.properties</value>
			</list>
		</property>
	</bean>
     (b)，配置连接池
     <!-- DBCP数据源 -->
	<bean id="defaultDataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
		 <!-- 基本属性 url、user、password -->
		<property name="driverClassName" value="${jdbc.driver}"></property>
		<property name="url" value="${jdbc.url}"></property>
		<property name="username" value="${jdbc.username}"></property>
		<property name="password" value="${jdbc.password}"></property>
		<!-- 配置初始化大小、最小、最大 -->
		<property name="initialSize" value="1" />
		<property name="minIdle" value="1" /> 
		<property name="maxActive" value="20" />
		<!-- 配置获取连接等待超时的时间 -->
		<property name="maxWait" value="60000" />
		<!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
		<property name="timeBetweenEvictionRunsMillis" value="60000" />
		<!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
		<property name="minEvictableIdleTimeMillis" value="300000" />
		<property name="validationQuery" value="SELECT 'x'" />
		<property name="testWhileIdle" value="true" />
		<property name="testOnBorrow" value="false" />
		<property name="testOnReturn" value="false" />
		<!-- 打开PSCache，并且指定每个连接上PSCache的大小 -->
		<property name="poolPreparedStatements" value="false" />
		<property name="maxPoolPreparedStatementPerConnectionSize" value="20" />
		<property name="connectionProperties" value="druid.stat.slowSqlMillis=3000" />
	</bean>
	 
	
	<bean id="dataSource" class="com.framework.core.datasource.DataSources">
		<property name="lenientFallback" value="false"></property>
		<property name="targetDataSources">
			<map key-type="java.lang.String">
				<entry value-ref="defaultDataSource" key="defaultDataSource"></entry>
			</map>
		</property>
		<property name="defaultTargetDataSource" ref="defaultDataSource"></property>
	</bean>
	
	
	<!-- begin Spring Jdbc 操作类注入-->
	<bean id="nameJdbcTemplate" class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
		<constructor-arg>
			<ref bean="dataSource" />
		</constructor-arg>
	</bean>
	
	<!-- JdbcTemplate -->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
		<constructor-arg>
			<ref bean="dataSource" />
		</constructor-arg>
    </bean>
    
	<bean id="sjdbcTemplate" class="org.springframework.jdbc.core.simple.SimpleJdbcTemplate">
		<constructor-arg>
			<ref bean="dataSource" />
		</constructor-arg>
	</bean>
	


2，熟悉了解Spring框架IOC容器和AOP解析

知识点一：

Spring DAO：Spring提供了对JDBC的操作支持：JdbcTemplate模板工具类 。

Spring ORM：Spring可以与ORM框架整合。例如Spring整合Hibernate框架，其中Spring还提供HibernateDaoSupport工具类，简化了Hibernate的操作 。

Spring WEB：Spring提供了对Struts、Springmvc的支持，支持WEB开发。与此同时Spring自身也提供了基于MVC的解决方案 。

Spring  AOP：Spring提供面向切面的编程，可以给某一层提供事务管理，例如在Service层添加事物控制 。

Spring   JEE：J2EE开发规范的支持，例如EJB 。

Spring Core：提供IOC容器对象的创建和处理依赖对象关系 。

知识点二：

Spring下IOC容器和DI(依赖注入Dependency injection)

　　IOC容器：就是具有依赖注入功能的容器，是可以创建对象的容器，IOC容器负责实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。通常new一个实例，控制权由程序员控制，而"控制反转"是指new实例工作不由程序员来做而是交给Spring容器来做。。在Spring中BeanFactory是IOC容器的实际代表者。

　　DI(依赖注入Dependency injection) ：在容器创建对象后，处理对象的依赖关系。

　　依赖注入spring的注入方式：

set注入方式
静态工厂注入方式
构造方法注入方式
基于注解的方式

知识点三：
Spring下面向切面编程(AOP)和事务管理配置 

 　　AOP就是纵向的编程，如业务1和业务2都需要一个共同的操作，与其往每个业务中都添加同样的代码，不如写一遍代码，让两个业务共同使用这段代码。
 在日常有订单管理、商品管理、资金管理、库存管理等业务，都会需要到类似日志记录、事务控制、权限控制、性能统计、异常处理及事务处理等。
 AOP把所有共有代码全部抽取出来，放置到某个地方集中管理，然后在具体运行时，再由容器动态织入这些共有代码。

参考资料：https://www.cnblogs.com/xiaoxing/p/5836835.html





