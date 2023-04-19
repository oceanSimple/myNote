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

   