# 通用配置

## 1. pom

```
<!--springboot依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!--mybatis-plus依赖-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.3.1</version>
</dependency>

<!--@data注解依赖-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>

<!--mysql数据库依赖-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.32</version>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.15</version>
</dependency>
```



## 2. application.yml

> 记得修改数据库名称！

```
server:
  port: 8080
spring:
  application:
    name: reggie_take_out
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/web_course_work?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: root
mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: ASSIGN_ID
```



## 3. 通用结果返回类

```
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果类
 * @param <T>
 */
@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
```



## 4. 全局异常处理类

> 示例如下，处理的是向数据库注册数据时，引发的unique异常

```
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        // 处理重复异常
        // Duplicate entry 'ocean' for key 'idx_username'
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }

        return R.error("未知错误");
    }
}
```



## 5. 解决跨域问题

> 最直接的方式是在controller类上加注解
>
> @CrossOrigin



另一个方法就是添加配置类

```
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //请求的服务路径
                //申请请求的地址
                .allowedOrigins("http://localhost:8081")
                //是否允许携带cookie
                .allowCredentials(true)
                //请求方式
                .allowedMethods("GET","POST","DELETE","PUT","OPTIONS");
    }
}
```



## 6. 过滤器

```
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@WebFilter("/*")
@Slf4j
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("过滤器运行...");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
```



## 7. 启动类注解

1. @ServletComponentScan

   ```
   在SpringBootApplication上使用@ServletComponentScan注解后，
   Servlet（控制器）、Filter（过滤器）、Listener（监听器）可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册到Spring容器中，无需其他代码。
   ```

   

## 8. 配置静态映射

```
package com.ocean.reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
@Configuration
public class WebMvcConfig aextends WebMvcConfigurationSupport {
    /**
     * 设置静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("静态资源映射启动");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
}
```



# mybatis-plus

## 1. 使用

> mapper类上的@Mapper
>
> serviceImpl上的@Service

1. service接口

   ```
   public interface FoodService extends IService<Food>
   ```

2. mapper接口

   ```
   @Mapper
   public interface FoodMapper extends BaseMapper<Food> {
   }
   ```

3. serviceImpl类

   ```
   @Service
   public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService 
   ```

   



## 2. 分页查询

> 具体用法，详见

1. 在config下添加分页查询插件

   ```
   import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
   import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   /**
    * 配置mp的分页插件
    */
   @Configuration
   public class MybatisPlusConfig {
       @Bean
       public MybatisPlusInterceptor mybatisPlusInterceptor() {
           MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
           mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
           return mybatisPlusInterceptor;
       }
   }
   ```



2. 示例

   ```
   public R<Page> page(int page, int pageSize) {
           // 创建pageInfo对象
           Page<Food> pageInfo = new Page<>(page, pageSize);
           // 条件构造器
           QueryWrapper wrapper = new QueryWrapper();
           wrapper.orderByAsc("fid");
           // 进行分页查询
           page(pageInfo,wrapper);
           // pageInfo中的records属性保存了查询的结果
           return R.success(pageInfo);
   }
   ```

   



# 自定义异常处理

## 1. 创建类

```
/**
 * 自定义业务异常
 */
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
```



## 2. 使用

```
throw new CustomException("当前分类下关联了套餐，不能删除");
```







# 邮件系统

> 推荐学习网站
>
> https://www.cnblogs.com/tianmengwei/p/5058088.html#:~:text=MimeMessages

## 1. 导入依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```



## 2. 代码使用

使用SimpleMailMessage

```
JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

javaMailSender.setHost("smtp.qq.com");  //设置发送方的邮箱格式
javaMailSender.setProtocol("smtp");     //设置协议
javaMailSender.setUsername("1252074183@qq.com");    //发送方账号
javaMailSender.setPassword("vennihzjcgaxjbji");     //发送方授权码
javaMailSender.setPort(587);                        //端口号
javaMailSender.setDefaultEncoding("UTF-8");         //编码

SimpleMailMessage message = new SimpleMailMessage();
message.setTo("20216928@stu.neu.edu.cn");           //收件方邮箱
message.setFrom("1252074183@qq.com");               //必须与发送发账号相同！！！
message.setSubject("测试！");                        //标题
message.setText("hello mail!!!");                   //正文内容

Properties properties = new Properties();           //配置
properties.put("mail.smtp.auth", "true");
properties.put("mail.smtp.timeout", "25000");

javaMailSender.setJavaMailProperties(properties);
javaMailSender.send(message);
```



## 3. 发送html格式

使用MimeMessage

```
JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

javaMailSender.setHost("smtp.qq.com");  //设置发送方的邮箱格式
javaMailSender.setProtocol("smtp");     //设置协议
javaMailSender.setUsername("1252074183@qq.com");    //发送方账号
javaMailSender.setPassword("vennihzjcgaxjbji");     //发送方授权码
javaMailSender.setPort(587);                        //端口号
javaMailSender.setDefaultEncoding("UTF-8");         //编码

MimeMessage message = javaMailSender.createMimeMessage();
MimeMessageHelper helper = new MimeMessageHelper(message, true);//通过helper配置

helper.setTo("20216928@stu.neu.edu.cn");           //收件方邮箱
helper.setFrom("1252074183@qq.com");               //必须与发送发账号相同！！！
helper.setSubject("测试！");                        //标题
helper.setText("<h1>hello mail!!!</h1>", true);//正文内容，true为发送html格式

Properties properties = new Properties();           //配置
properties.put("mail.smtp.auth", "true");
properties.put("mail.smtp.timeout", "25000");
javaMailSender.setJavaMailProperties(properties);

javaMailSender.send(message);
```





# 处理excel

## 写

### 1. 导入依赖

```
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>3.1.1</version>
</dependency>
```



### 2. 实体类

> @ExcelProperty("用户编号")	列名	还有个index属性，指定列的顺序
>
> @ExcelIgnore	排除的列名

```
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @ExcelProperty("用户编号")
    private Integer userId;
    @ExcelProperty("姓名")
    private String userName;
    @ExcelProperty("性别")
    private String gender;
    @ExcelProperty("工资")
    private Double salary;
    @ExcelProperty("入职时间")
    private Date hireDate;

    // 排除的属性!
    @ExcelIgnore
    private String desc;
}
```



### 3. @ExcelProperty详解

属性：

1. value：列名，不写默认变量名

   如果写成一个数组

   ```
   @ExcelProperty(value = {"group1", "用户编号"}, index = 0)
   private Integer userId;
   @ExcelProperty(value = {"group1", "姓名"}, index = 1)
   private String userName;
   ```

   会实现分组效果

2. index：列顺序



### 4. 格式化

```
@NumberFormat(value = "###.#") // 数字格式化,保留1位小数
private Double salary;
@ExcelProperty(value = "入职时间", index = 2)
@DateTimeFormat(value = "yyyy年MM月dd日 HH时mm分ss秒") // 日期格式化
private Date hireDate;
```



### 5. 控制excel样式

```
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@HeadRowHeight(value = 30) // 头部行高
@ContentRowHeight(value = 25) // 内容行高
@ColumnWidth(value = 20) // 列宽
// 头背景设置成红色 IndexedColors.RED.getIndex()
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 10)
// 头字体设置成20, 字体默认宋体
@HeadFontStyle(fontName = "宋体", fontHeightInPoints = 20)
// 内容的背景设置成绿色  IndexedColors.GREEN.getIndex()
@ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 17)
// 内容字体设置成20, 字体默认宋体
@ContentFontStyle(fontName = "宋体", fontHeightInPoints = 20)
public class DemoStyleData {

    // 字符串的头背景设置成粉红 IndexedColors.PINK.getIndex()
    @HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 14)
    // 字符串的头字体设置成20
    @HeadFontStyle(fontHeightInPoints = 30)
    // 字符串的内容背景设置成天蓝 IndexedColors.SKY_BLUE.getIndex()
    @ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 40)
    // 字符串的内容字体设置成20,默认宋体
    @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 20)
    @ExcelProperty(value = "字符串标题")
    private String string;
    @ExcelProperty(value = "日期标题")
    private Date date;
    @ExcelProperty(value = "数字标题")
    private Double doubleData;
    // lombok 会生成getter/setter方法
}
```



## 读

实体类与上面一样

```
List<User> users = new ArrayList<>();
EasyExcel.read(path, User.class, new AnalysisEventListener() {
    /*每读取一行就调用一次*/
    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        User user = (User) o;
        users.add(user);
    }
    /*全部读取完后调用*/
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(users);
        for (User user : users) {
            System.out.println(user);
        }
    }
}).sheet().doRead();
```

若要读取全部sheet

```
.sheet().doRead()改成.doReadAll()
```





# 常用功能

## 1. 字符串中变量添加

举例：

```
String context = """
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>测试系统</title>
    </head>
    <body>
        <h1>{0}</h1>
        <h1>{1}</h1>
        <h1>hello mail</h1><br>
        账号：<input type="text"><br>
        密码：<input type="password">
    </body>
    </html>
""";

context = MessageFormat.format(context,"ocean", "sea");
```



## 2. 常用正则表达式

```
m-n位的数字：^\d{m,n}$

英文和数字：
^[A-Za-z0-9]+$ 或 ^[A-Za-z0-9]{4,40}$

长度为3-20的所有字符：^.{3,20}$

由数字、26个英文字母或者下划线组成的字符串：
 ^\w+$ 或 ^\w{3,20}$
 
 中文、英文、数字包括下划线：
 ^[\u4E00-\u9FA5A-Za-z0-9_]+$
```

