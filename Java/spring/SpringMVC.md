# 入门案例

1. 导入坐标

   ```
       <dependencies>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-webmvc</artifactId>
               <version>5.3.23</version>
           </dependency>
           <dependency>
               <groupId>javax.servlet</groupId>
               <artifactId>javax.servlet-api</artifactId>
               <version>4.0.1</version>
           </dependency>
       </dependencies>
       
    <build>
       <plugins>
         <plugin>
           <groupId>org.apache.tomcat.maven</groupId>
           <artifactId>tomcat7-maven-plugin</artifactId>
           <version>2.1</version>
           <configuration>
             <port>80</port>
             <path>/</path>
           </configuration>
         </plugin>
       </plugins>
     </build>
   ```
   
2. config类

   ```
   @Configuration
   @ComponentScan("com.ocean.controller")
   public class SpringMvcConfig {
   }
   ```

   ```
   public class ServletContainerInitConfig extends AbstractDispatcherServletInitializer {
       @Override
       protected WebApplicationContext createServletApplicationContext() {
           AnnotationConfigWebApplicationContext ctx=new AnnotationConfigWebApplicationContext();
           ctx.register(SpringMvcConfig.class);
           return ctx;
       }
   
       @Override
       protected String[] getServletMappings() {
           return new String[] {"/"};
       }
   
       @Override
       protected WebApplicationContext createRootApplicationContext() {
           return null;
       }
   }
   ```

3. servlet类

   ```
   @Controller
   public class UserController {
       @RequestMapping("/save")
       @ResponseBody
       public String save(){
           System.out.println("save......");
           return "save is running...";
       }
   }
   ```

   简化

   ```
   public class ServletContainerInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
   
       @Override
       protected Class<?>[] getRootConfigClasses() {
           return new Class[] {SpringConfig.class};
       }
   
       @Override
       protected Class<?>[] getServletConfigClasses() {
           return new Class[] {SpringMvcConfig.class};
       }
   
       @Override
       protected String[] getServletMappings() {
           return new String[]{"/"};
       }
       
       //设置过滤器，解决乱码问题
        @Override
       protected Filter[] getServletFilters() {
           CharacterEncodingFilter fliter=new CharacterEncodingFilter();
           fliter.setEncoding("UTF-8");
           return new Filter[]{fliter};
       }
   }
   ```

   

# JSON数据

0. 导入jackson坐标

   ```
   <dependency>
       <groupId>com.fasterxml.jackson.core</groupId>
       <artifactId>jackson-databind</artifactId>
       <version>2.9.0</version>
   </dependency>
   ```

1. 配置类

   ```
   @Configuration
   @ComponentScan("ocean.controller")
   @EnableWebMvc
   public class SpringMvcConfig {
   }
   ```

2. pojo类

   ps：	提供set，get方法

   ​			提供空参构造方法！！！

3. controller类

   ```
   @Controller
   @RequestMapping("/book")
   public class BookDemo {
       @RequestMapping("/save")
       @ResponseBody
       public String save(@RequestBody List<Student> list){
           System.out.println(list);
           return "Book save...";
       }
   }
   ```

   ps：@RequestBody必加！

4. 添加json数据

   ```
   [
       {"name":"ocean","age":10},
       {"name":"sea","age":12}
   ]
   ```

# Date类型数据

```
@Controller
@RequestMapping("/Date")
public class DateDemo {
    @RequestMapping("/demo")
    @ResponseBody
    public String dateDemo(Date date01,
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date date02,
                           @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date date03){
        System.out.println(date01);
        System.out.println(date02);
        System.out.println(date03);
        return "going...";
    }
}
```

ps：标准格式：“yyyy/MM/dd”

# REST风格

## 格式

```
@RequestMapping(value="/users/{id}",method=RequestMethod.DELETE)
@ResponseBody
public String deleteById(@PathVariable Integer id){
}
```

 常用的：get/put/delete/post

## 简化

```
@RestController
相当于
@Controller
@ResponseBody
```

```
@RequestMapping(method=RequestMethod.POST)====@PostMapping
...===@DeleteMapping("/{id}")
...===@PutMapping
...===@GetMapping("/{id}")
```

 

# SSM整合

1. 导包

```
  <dependencies>
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>4.0.1</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>5.3.23</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>5.3.23</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>5.2.10.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.11</version>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>2.0.7</version>
    </dependency>
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.47</version>
    </dependency>
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.16</version>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.13.1</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.13.1</version>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <version>2.1</version>
        <configuration>
          <port>80</port>
          <path>/</path>
        </configuration>
      </plugin>
    </plugins>
  </build>
```

2. 详见SSM_Maven项目

# 异常处理器

```
@RestControllerAdvice
public class ProjectExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex){
        System.out.println("异常！！！");
        return new Result(1,404,"异常！！！！！");
    }
}
```

# 拦截器

```
@Configuration
@ComponentScan({"ocean.controller"})
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {
    @Autowired
    private ProjectInterceptor projectInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(projectInterceptor).addPathPatterns("/books","/books/*");
    }
}
```

```
@Component
public class ProjectInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("pre...");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("post...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("after...");
    }
}
```

