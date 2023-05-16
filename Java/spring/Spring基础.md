# 入门

## IOC-控制反转-入门

由主动new产生对象，转换为由外部提供对象

对象控制权由程序转移到外部

> spring提供一个容器，称为IOC容器，用来充当“外部”
>
> IOC容器负责对象的创建、初始化等一系列工作，被创建或被管理的对象在IOC容器中统称为Bean

> DI-依赖注入
>
> 在容器中建立bean与bean之间的依赖关系的整个过程

**步骤**

1. maven配置

   ```
   <dependency>
   <groupId>org.springframework</groupId>
   <artifactId>spring-context</artifactId>
   <version>5.3.23</version>
   </dependency>
   ```

2. 配置文件

   在main.resources下添加xml配置文件（Spring）配置

   ```
   <!--配置bean-->
   <!--id表示起名字     class表示bean定义类型-->
   <bean id="bookDao" class="com.ocean.dao.impl.BookDaoImpl"/>
   <bean id="bookService" class="com.ocean.service.impl.BoocServiceImpl"/>
   ```

3. 使用（与反射类似）

   ```
   //获取IOC容器
   ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
   //获取bean
   BookDao bookDao = (BookDao) applicationContext.getBean("bookDao");
   bookDao.say();
   BookService bookService=(BookService) applicationContext.getBean("bookService");
   bookService.say();
   ```

## DI入门

```
private BookDao bookDao;
public void setBookDao(BookDao bookDao) {
	this.bookDao = bookDao;
}
```

```
<bean id="bookService" class="com.ocean.service.impl.BoocServiceImpl">
    <!--配置service与dao的关系-->
    <!--表示配置当前bean的属性，name表示配置哪一个具体的属性，ref表示参照哪一个bean-->
    <property name="bookDao" ref="bookDao"/>
</bean>
```



# Bean

## 基础配置

```
name：	别名
id：		唯一标识
class：	属性
scope：	范围	（sigleton-单例（默认）,prototype-非单例）
```

## 实例化bean的三种方法

### 1. 构造方法（常用）

对象类提供空参的构造方法

配置不变

### 2. 静态工厂

```
//静态工厂实例化
public class BeanStaticFactory {
    private static BeanClass beanIstance=new BeanClass("调用静态工厂方法实例bean");
    public  static BeanClass createInstance(){
        return beanIstance;
    }
}

```

```
<bean id="staticStaticFactory" class="instance.BeanStaticFactory"
         factory-method="createInstance"/>
```

### 3. 实例工厂

```
public class BeanClass {
    public  String message;
    public  BeanClass(){
        message="构造方法实例化bean";
    }
    public BeanClass(String s){
        message=s;
    }
}

//实例工厂实例化
public class BeanInstanceFactory {
    public  BeanClass createBeanClassInstance(){
        return  new BeanClass("调用实例工厂方法实例化Bean");
    }
}
```

```
<bean id="myFactory" class="instance.BeanInstanceFactory"/>
<!--使用factory属性指定配置工厂,使用factory-method属性指定使用工厂中的那个实例化bean-->
<bean id="instanceFactoryInstance" factory-bean="myFactory" factory-method="createBeanClassInstance"/>
```

**改良**

```
public class UserDaoFactoryBean implements FactoryBean<T>{
	public T getObject(){
		return new T;
	}
	public Class<T> getObjectTypt(){
		return T.class;
	}
	public boolean isSingleton(){
		return true;//默认单例，false为非单例
	}
}
```

```
<bean id="	" class="com.ocean....UserDaoFactoryBean">
```

## 生命周期

```
public void init(){
	
}
public void destroy(){
	
}
```

```
<bean id	class	init-method="init" destroy-method="destroy"/>
```

或者（了解）

```
public class BookDaoImpl implements BookDao, InitializingBean, DisposableBean {
    @Override
    public void say() {
        System.out.println("Dao");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("init...");
    }
}
```

这样不用重新修改配置文件



若要destroy方法运行，必须关闭容器

```
ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
{body.....}
.......
applicationContext.close();
```

或者(推荐)：注册关闭钩子，先关闭容器，再退出虚拟机

```
ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
applicationContext.registerShutdownHook();
```





# 依赖注入

## setter注入

为变量设置set方法

然后配置文件

```
<bean id="bookDao" name="ocean1 ocean2" class="com.ocean.dao.impl.BookDaoImpl"/>
<bean id="bookService" class="com.ocean.service.impl.BoocServiceImpl">
    <!--配置service与dao的关系-->
    <!--表示配置当前bean的属性，name表示配置哪一个具体的属性，ref表示参照哪一个bean-->
    <property name="bookDao" ref="bookDao"/>
</bean>
```

ref是识别bean

若要赋值简单数据类型，用value

## 构造器注入

生成有参构造器

```
<constructor-arg	name(形参名)	ref/value		/>
```

## 依赖自动装配

1. 提供set方法

2. ```
   <bean id="bookDao" name="ocean1 ocean2" class="com.ocean.dao.impl.BookDaoImpl"/>
   <bean id="bookService" class="com.ocean.service.impl.BoocServiceImpl" autowire="byType"/>
   ```

常用byType

若byName，即set方法的名称与上面id名称相同

ps：适用于引用类型

## 集合注入（了解即可）

```
    <bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl">
        <!--数组注入-->
        <property name="array">
            <array>
                <value>100</value>
                <value>200</value>
                <value>300</value>
            </array>
        </property>
        <!--list集合注入-->
        <property name="list">
            <list>
                <value>itcast</value>
                <value>itheima</value>
                <value>boxuegu</value>
                <value>chuanzhihui</value>
            </list>
        </property>
        <!--set集合注入-->
        <property name="set">
            <set>
                <value>itcast</value>
                <value>itheima</value>
                <value>boxuegu</value>
                <value>boxuegu</value>
            </set>
        </property>
        <!--map集合注入-->
        <property name="map">
            <map>
                <entry key="country" value="china"/>
                <entry key="province" value="henan"/>
                <entry key="city" value="kaifeng"/>
            </map>
        </property>
        <!--Properties注入-->
        <property name="properties">
            <props>
                <prop key="country">china</prop>
                <prop key="province">henan</prop>
                <prop key="city">kaifeng</prop>
            </props>
        </property>
    </bean>
```

## 数据源对象管理

1. 导入druid

   ```
   <dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>druid</artifactId>
       <version>1.1.16</version>
   </dependency>
   ```

2. ```
   <!--管理DruidDataSource对象-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
           <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
           <property name="url" value="jdbc:mysql://localhost:3306/webdata"/>
           <property name="username" value="root"/>
           <property name="password" value="root"/>
   
       </bean>
   ```

3. ```
   public class App02 {
       public static void main(String[] args) {
           ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
           DataSource dataSource= (DataSource) ctx.getBean("dataSource");
           System.out.println(dataSource);
       }
   }
   ```

   ## c3p0案例
   
   ```
   <dependency>
       <groupId>c3p0</groupId>
       <artifactId>c3p0</artifactId>
       <version>0.9.1.2</version>
   </dependency>
   ```
   
   ```
   <bean id="dataSource2" class="com.mchange.v2.c3p0.ComboPooledDataSource">
       <property name="driverClass" value="com.mysql.jdbc.Driver"/>
       <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/webdata"/>
       <property name="user" value="root"/>
       <property name="password" value="root"/>
   </bean>
   ```
   
   

# 加载properties文件

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"

       xmlns:context="http://www.springframework.org/schema/context"						//
       
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context 					//
                           http://www.springframework.org/schema/beans/spring-context.xsd	//	
">
```

```
    <context:property-placeholder location="classpath:jdbc.properties"/>
    <!--管理DruidDataSource对象-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${jdbc.dirver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
```





# 注解开发



## @Component

@Component(value="名字")



注解扫描

```
<context:component-scan base-package="ocean.dao.impl"/>
```

或者

```
@Configuration
@ComponentScan({"ocean.dao.impl","ocean.service.impl"})
public class SpringConfig {
    
}
```

```
public class AppForAnnotation {
    public static void main(String[] args) {
        ApplicationContext ctx=new AnnotationConfigApplicationContext(SpringConfig.class);	//
        BookDao bookDao = (BookDao) ctx.getBean("bookDao");
        bookDao.say();
    }
}
```

提供了三个衍生注解

```
@Service		//服务层
@Repository		//数据仓库
@Controller		//控制层
```



设置范围与生命周期

```
@Scope("prototype")
```

```
@Repository("bookDao")
@Scope("singleton")
public class BookDaoImpl implements BookDao{
    @Override
    public void say() {
        System.out.println("Dao");
    }

    @PostConstruct
    public void init(){
        System.out.println("init...");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("destroy...");
    }
}
```



## @Autowired

直接在成员变量上面加，系统会自动装配

```
@Autowired
@Qualifier("bean名称")
private BookDao bookDao;
```



加载配置文件，并给基础数据类型赋值

```
@Configuration
@ComponentScan({"ocean.dao.impl","ocean.service.impl"})
@PropertySource({"name.properties"})					//加载配置文件
public class SpringConfig {

}
```

```
@Value("${name}")
private String name;
```



## 注册管理第三方bean

@Import

```
@Configuration
@ComponentScan({"ocean.dao.impl","ocean.service.impl"})
@PropertySource("name.properties")
@Import({JdbcConfig.class})			//
public class SpringConfig {
}
```

```
public class JdbcConfig {
    //添加@Bean，表示当前方法返回值是一个bean
    @Bean
    public DataSource dataSource(){
        DruidDataSource ds=new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/webdata");
        ds.setUsername("root");
        ds.setPassword("root");
        return ds;
    }
}
```



# Spring整合mybatis

1. 导入配置依赖

   ```
   <dependencies>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-context</artifactId>
               <version>5.3.23</version>
           </dependency>
   
           <dependency>
               <groupId>com.alibaba</groupId>
               <artifactId>druid</artifactId>
               <version>1.1.16</version>
           </dependency>
   
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>8.0.31</version>
           </dependency>
   
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis</artifactId>
               <version>3.5.11</version>
           </dependency>
   
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-jdbc</artifactId>
               <version>5.3.23</version>
           </dependency>
   
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis-spring</artifactId>
               <version>2.0.7</version>
           </dependency>
   
       </dependencies>
   ```

   2. jdbc.properties

      ```
      jdbc.driver=com.mysql.jdbc.Driver
      jdbc.url=jdbc:mysql://localhost:3306/webdata
      jdbc.username=root
      jdbc.password=root
      ```

   3. 初始化注解开发

      SpringConfig类

      ```
      import org.springframework.context.annotation.ComponentScan;
      import org.springframework.context.annotation.Configuration;
      import org.springframework.context.annotation.Import;
      import org.springframework.context.annotation.PropertySource;
      
      @Configuration
      @ComponentScan("ocean")
      @PropertySource("classpath:jdbc.properties")
      @Import({JdbcConfig.class,MybatisConfig.class})
      public class SpringConfig {
      
      }
      ```

      JdbcConfig类

      ```
      import com.alibaba.druid.pool.DruidDataSource;
      import org.springframework.beans.factory.annotation.Value;
      import org.springframework.context.annotation.Bean;
      import javax.sql.DataSource;
      
      public class JdbcConfig {
          @Value("${jdbc.driver}")
          private String driver;
          @Value("${jdbc.url}")
          private String url;
          @Value("${jdbc.username}")
          private String userName;
          @Value("${jdbc.password}")
          private String password;
      
          //添加@Bean，表示当前方法返回值是一个bean
          @Bean
          public DataSource dataSource(){
              DruidDataSource ds=new DruidDataSource();
              ds.setDriverClassName(driver);
              ds.setUrl(url);
              ds.setUsername(userName);
              ds.setPassword(password);
              return ds;
          }
      }
      ```

      MybatisConfig类

      ```
      import org.mybatis.spring.SqlSessionFactoryBean;
      import org.mybatis.spring.mapper.MapperScannerConfigurer;
      import org.springframework.context.annotation.Bean;
      import javax.sql.DataSource;
      
      public class MybatisConfig {
          @Bean
          public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource){
              SqlSessionFactoryBean ssfb=new SqlSessionFactoryBean();
              ssfb.setTypeAliasesPackage("ocean.domain");
              ssfb.setDataSource(dataSource);
              return ssfb;
          }
      
          //定义bean，返回MapperScannerConfigurer对象
          @Bean
          public MapperScannerConfigurer mapperScannerConfigurer(){
              MapperScannerConfigurer msc = new MapperScannerConfigurer();
              msc.setBasePackage("ocean.dao");
              return msc;
          }
      }
      ```

      4. 实现工具类

         ```
         import ocean.domain.User;
         import org.apache.ibatis.annotations.Delete;
         import org.apache.ibatis.annotations.Insert;
         import org.apache.ibatis.annotations.Select;
         import org.apache.ibatis.annotations.Update;
         import org.springframework.stereotype.Controller;
         
         import java.util.List;
         
         @Controller
         public interface UserDao {
         
             @Insert("insert into userinfo(Uid,Upassword,Uname,Uemail) values(#{Uid},#{Upassword},#{Uname},#{Uemail})")
             void save(User account);
         
             @Delete("delete from userinfo where id = #{Uid} ")
             void delete(String  id);
         
             @Update("update userinfo set Uname = #{Uname} , Upassword = #{Upassword},Uemail= #{Uemail} where Uid = #{Uid} ")
             void update(User account);
         
             @Select("select * from userinfo")
             List<User> findAll();
         
             @Select("select * from userinfo where Uid = #{Uid} ")
             User findById(String  id);
         }
         ```

         ```
         import org.springframework.stereotype.Controller;
         
         @Controller
         public class User {
             private String Uid;
             private String Upassword;
             private String Uname;
             private String Uemail;
         
             public User() {
             }
         
             public User(String uid, String upassword, String uname, String uemail) {
                 Uid = uid;
                 Upassword = upassword;
                 Uname = uname;
                 Uemail = uemail;
             }
         
             public String getUid() {
                 return Uid;
             }
         
             public void setUid(String uid) {
                 Uid = uid;
             }
         
             public String getUpassword() {
                 return Upassword;
             }
         
             public void setUpassword(String upassword) {
                 Upassword = upassword;
             }
         
             public String getUname() {
                 return Uname;
             }
         
             public void setUname(String uname) {
                 Uname = uname;
             }
         
             public String getUemail() {
                 return Uemail;
             }
         
             public void setUemail(String uemail) {
                 Uemail = uemail;
             }
         
             @Override
             public String toString() {
                 return "User{" +
                         "Uid='" + Uid + '\'' +
                         ", Upassword='" + Upassword + '\'' +
                         ", Uname='" + Uname + '\'' +
                         ", Uemail='" + Uemail + '\'' +
                         '}';
             }
         }
         
         ```

         ```
         import ocean.domain.User;
         
         import java.util.List;
         
         public interface UserService {
             void save(User user);
             void delete(String  id);
             void update(User user);
             List<User> findAll();
             User findById(String  id);
         }
         ```

         ```
         import ocean.dao.UserDao;
         import ocean.domain.User;
         import org.springframework.beans.factory.annotation.Autowired;
         import org.springframework.stereotype.Service;
         
         import java.util.List;
         
         @Service
         public class UserServiceImi implements UserService{
             @Autowired
             private UserDao userDao;
         
             @Override
             public void save(User user) {
                 userDao.save(user);
             }
         
             @Override
             public void delete(String  id) {
                 userDao.delete(id);
             }
         
             @Override
             public void update(User user) {
                 userDao.update(user);
             }
         
             @Override
             public List<User> findAll() {
                 return userDao.findAll();
             }
         
             @Override
             public User findById(String  id) {
                 return userDao.findById(id);
             }
         }
         ```

         5. 使用

            ```
            import ocean.Service.UserService;
            import ocean.config.SpringConfig;
            import ocean.domain.User;
            import org.springframework.context.ApplicationContext;
            import org.springframework.context.annotation.AnnotationConfigApplicationContext;
            
            public class App {
                public static void main(String[] args) {
                    ApplicationContext ctx=new AnnotationConfigApplicationContext(SpringConfig.class);
                    UserService userService = ctx.getBean(UserService.class);
            
                    for (User user : userService.findAll()) {
                        System.out.println(user);
                    }
            
                    System.out.println("----------------------------------------");
            
                    User byId = userService.findById("13971645451");
                    System.out.println(byId);
            
                }
            }
            ```

            

# AOP

## 入门

```
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.3.23</version>
    </dependency>
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.9.4</version>
    </dependency>
</dependencies>
```

```
@Configuration
@ComponentScan("com.ocean")
@EnableAspectJAutoProxy         //启动注解开发AOP步骤
public class SpringConfig {
}
```

```
@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(void com.ocean.dao.BookDao.update())")
    private void pt(){}

    @Before("pt()")
    public void method(){
        System.out.println(new Date());
    }
}
```



## 切入点表达式

标准格式

```
execution (public 返回类型 包名.接口名.方法名（参数）)
```

通配符

```
1. *	：匹配一个任意符号
2. ..	：匹配多个任意符号.
3. +	：专用于匹配子类类型
```



## 通知类型

```
@Before		//前置
@After		//后置
@Around		//环绕
@AfterReturning
```

```
@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(void com.ocean.dao.BookDao.update())")
    private void pt(){}

    @Around("pt()")
    public void method(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("start...");
        pjp.proceed();          //对原始操作的调用
        System.out.println("end...");
    }
}
```

```
Sinature sign=pjp.getSignature();
sign.getDeclaringType();		//类名
sign.getName();					//方法名
```



# 事务管理

1. 在要进行事务管理的类上添加标签

   ```
   @Transactional				/////
   public interface UserService {
       void save(User user);
       void delete(String  id);
       void update(User user);
       List<User> findAll();
       User findById(String  id);
   }
   ```

2. 设置事务管理器

   ```
   @Bean
   public PlatformTransactionManager transactionManager(DataSource dataSource){
       DataSourceTransactionManager transactionManager=new DataSourceTransactionManager();
       transactionManager.setDataSource(dataSource);
       return transactionManager;
   }
   ```

3. 开启注解式事务驱动

   ```
   @Configuration
   @ComponentScan("ocean")
   @PropertySource("classpath:jdbc.properties")
   @Import({JdbcConfig.class,MybatisConfig.class})
   @EnableTransactionManagement			////////
   public class SpringConfig {
   
   }
   ```

   

# 总结

## 1. 注解式编程

```
//导包
<dependencies>
<dependency>	//spring包
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.23</version>
</dependency>
<dependency>	//AOP包
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.4</version>
</dependency>
</dependencies>
```

```
//config类
@Configuration							//配置文件标识
@ComponentScan("ocean")					//扫描
@PropertySource("student.properties")	//导入配置文件
@EnableAspectJAutoProxy    			 //启动注解开发AOP
public class SpringConfig {			
}
```

```
//基本数据类型赋值
@Value("${student.name}")
private String name;
@Value("${student.age}")
private int age;
@Value("${student.gender}")
private String gender;
```

```
//自动填装
@Autowired
public Service01 service01;
```

```
@Component
@Service		//服务层
@Repository		//数据仓库
@Controller		//控制层
```

```
//使用
ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
Service01 bean = ctx.getBean(Service01.class);	//类查找
```

## 2. AOP

原函数

```
@Repository
public class Service01 {
    public int service(int a,String b){
        System.out.println("service01...");
        return 100;
    }
}
```

配置

```
@Configuration
@ComponentScan("ocean")
@PropertySource("student.properties")
@EnableAspectJAutoProxy     //启动注解开发AOP
public class SpringConfig {
}
```

AOP

```
@Component
@Aspect
public class ServiceAOP {
	//切入点
    @Pointcut("execution(int ocean.service.Service01.service(int,String))")
    private void pt(){}
	
	//切片
    @Around("pt()")
    public int addition(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("before");

        Signature signature = pjp.getSignature();
        System.out.println(signature.getName());                //方法名
        System.out.println(signature.getDeclaringType());       //类名（class +类名）
        System.out.println(signature.getDeclaringTypeName());   //类名

        Object[] args = pjp.getArgs();      //获取传入的形参
        System.out.println(Arrays.toString(args));
        Integer proceed = (Integer)pjp.proceed(args);   //可以在此过程前对传参进行修改

        System.out.println("after");
        return proceed;
    }
}
```

