# 配置父项目

> 注意点：
> 
> 1. <dependencyManagement>中的依赖，如果是<artifactId>spring-cloud-dependencies</artifactId>、<artifactId>spring-boot-dependencies</artifactId>，由于他们里面的依赖管理也是<dependencyManagement>，所以如果要规范子项目的版本，需要添加
>    ```
>    <dependency>
>        <groupId>org.springframework.cloud</groupId>
>        <artifactId>spring-cloud-dependencies</artifactId>
>        <version>${srping-cloud.version}</version>
>        <type>pom</type>
>        <scope>import</scope>
>    </dependency>
>    ```
> 2. 所有依赖版本放入<properties>中进行统一管理

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <packaging>pom</packaging>
    <modules>
        <module>micro-service-cloud-api-8000</module>
        <module>micro-service-cloud-eureka-8001</module>
        <module>micro-service-cloud-provider-dept-8002</module>
        <module>micro-service-cloud-consumer-8003</module>
    </modules>

    <groupId>com.ocean</groupId>
    <artifactId>springcloud</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <java.version>1.8</java.version>
        <maven.compiler.source>18</maven.compiler.source>
        <maven.compiler.target>18</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <!--依赖版本管理-->
        <spring-boot.version>2.6.3</spring-boot.version>
        <srping-cloud.version>2021.0.7</srping-cloud.version>
        <alibaba-cloud.version>2021.0.1.0</alibaba-cloud.version>
        <mysql-connector-j.version>8.0.33</mysql-connector-j.version>
        <druid.version>1.2.18</druid.version>
        <lombok.version>1.18.28</lombok.version>
        <mybatis-plus-boot-starter.version>3.5.3.1</mybatis-plus-boot-starter.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!--spring-cloud依赖版本-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${srping-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring-boot依赖版本-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring-cloud-alibaba依赖版本-->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${alibaba-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--mysql依赖版本-->
            <dependency>
                <groupId>com.mysql</groupId>
                <artifactId>mysql-connector-j</artifactId>
                <version>${mysql-connector-j.version}</version>
            </dependency>
            <!--druid依赖版本-->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <!--lombok依赖版本-->
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
            </dependency>
            <!--mybatis-plus依赖-->
            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-boot-starter</artifactId>
                <version>${mybatis-plus-boot-starter.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

<br/>

# 通用实体项目

> 1. 用来存放实体类、工具类
> 2. 用来导入日常的工具依赖：例如Lombok

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.ocean</groupId>
        <artifactId>springcloud</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <artifactId>micro-service-cloud-api-8000</artifactId>

    <properties>
        <maven.compiler.source>18</maven.compiler.source>
        <maven.compiler.target>18</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>
</project>
```

<br/>

# 注册中心

1. pom
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <parent>
           <groupId>com.ocean</groupId>
           <artifactId>springcloud</artifactId>
           <version>1.0-SNAPSHOT</version>
       </parent>
       <artifactId>micro-service-cloud-eureka-8001</artifactId>
       <version>1.0</version>
       <properties>
           <maven.compiler.source>18</maven.compiler.source>
           <maven.compiler.target>18</maven.compiler.target>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
       </properties>
   
       <dependencies>
           <!--spring-boot-web-->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <!--Eureka服务注册中心依赖-->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
           </dependency>
           <!--lombok-->
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <optional>true</optional>
           </dependency>
       </dependencies>
   
       <build>
           <plugins>
               <plugin>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
               </plugin>
           </plugins>
       </build>
   </project>
   ```

2. yml配置文件
   ```
   server:
     port: 8001  #该 Module 的端口号
   eureka:
     instance:
       hostname: localhost #eureka服务端的实例名称，
     client:
       register-with-eureka: false #false表示不向注册中心注册自己。
       fetch-registry: false #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
       service-url:
         defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #单机版服务注册中心
   ```

3. 启动类
   ```
   @SpringBootApplication
   @EnableEurekaServer // EurekaServer服务器端启动类，接受其它微服务注册进来
   public class MicroServiceCloudEureka8001Application {
       public static void main(String[] args) {
           org.springframework.boot.SpringApplication.run(MicroServiceCloudEureka8001Application.class, args);
       }
   }
   ```

<br/>

<br/>

# 服务端-provider

1. pom
   > 项目采用了mybatis-plus进行数据库访问
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <parent>
           <groupId>com.ocean</groupId>
           <artifactId>springcloud</artifactId>
           <version>1.0-SNAPSHOT</version>
       </parent>
   
       <artifactId>micro-service-cloud-provider-dept-8002</artifactId>
       <version>1.0</version>
   
       <properties>
           <maven.compiler.source>18</maven.compiler.source>
           <maven.compiler.target>18</maven.compiler.target>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
       </properties>
   
       <dependencies>
           <!--springboot-web-->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <!--api-8000-->
           <dependency>
               <groupId>com.ocean</groupId>
               <artifactId>micro-service-cloud-api-8000</artifactId>
               <version>1.0-SNAPSHOT</version>
           </dependency>
           <!--mysql-->
           <dependency>
               <groupId>com.mysql</groupId>
               <artifactId>mysql-connector-j</artifactId>
           </dependency>
           <!--druid-->
           <dependency>
               <groupId>com.alibaba</groupId>
               <artifactId>druid</artifactId>
           </dependency>
           <!--mybatis-plus-->
           <dependency>
               <groupId>com.baomidou</groupId>
               <artifactId>mybatis-plus-boot-starter</artifactId>
           </dependency>
           <!--引入 Eureka Client 的依赖，将服务注册到 Eureka Server-->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
           </dependency>
       </dependencies>
   
       <build>
           <plugins>
               <plugin>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
               </plugin>
           </plugins>
       </build>
   </project>  
   ```

2. yml配置文件
   ```
   server:
     port: 8002 #服务端口号
   spring:
     application:
       name: microServiceCloudProviderDept  #微服务名称，对外暴漏的微服务名称，十分重要
     datasource:
       type: com.zaxxer.hikari.HikariDataSource
       driver-class-name: com.mysql.cj.jdbc.Driver
       url: jdbc:mysql://localhost:3306/project02?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
       username: root
       password: root
   
   mybatis-plus:
     configuration:
       map-underscore-to-camel-case: true
       log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
     global-config:
       db-config:
         id-type: ASSIGN_ID
   ########################################### Spring cloud 自定义服务名称和 ip 地址###############################################
   eureka:
     client: #将客户端注册到 eureka 服务列表内
       service-url:
         defaultZone: http://localhost:8001/eureka/  #这个地址是 8001注册中心在 application.yml 中暴露出来额注册地址 （单机版）
     instance:
       instance-id: spring-cloud-provider-8002 #自定义服务名称信息
       prefer-ip-address: true  #显示访问路径的 ip 地址
   ```

3. 启动类
   ```java
   @SpringBootApplication
   @EnableEurekaClient // EnableEurekaClient: EnableEurekaClient注解在服务启动后自动注册到Eureka中
   public class MicroServiceCloudProviderDept8002Application {
       public static void main(String[] args) {
           SpringApplication.run(MicroServiceCloudProviderDept8002Application.class, args);
       }
   }
   ```

4. 提供的服务
   
   提供localhost:8002/dept/get接口返回List<Dept>
   
   <br/>

<br/>

# 服务端-consumer

1. pom
   > 服务间调用使用feign依赖
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <parent>
           <groupId>com.ocean</groupId>
           <artifactId>springcloud</artifactId>
           <version>1.0-SNAPSHOT</version>
       </parent>
   
       <artifactId>micro-service-cloud-consumer-8003</artifactId>
   
       <properties>
           <maven.compiler.source>18</maven.compiler.source>
           <maven.compiler.target>18</maven.compiler.target>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
       </properties>
   
       <dependencies>
           <!--springboot-web-->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <!--api-8000-->
           <dependency>
               <groupId>com.ocean</groupId>
               <artifactId>micro-service-cloud-api-8000</artifactId>
               <version>1.0-SNAPSHOT</version>
           </dependency>
           <!--引入 Eureka Client 的依赖，将服务注册到 Eureka Server-->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
           </dependency>
           <!--feign依赖-->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-openfeign</artifactId>
           </dependency>
       </dependencies>
   
       <build>
           <plugins>
               <plugin>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
               </plugin>
           </plugins>
       </build>
   
   </project>
   ```

2. yml
   ```
   server:
     port: 8003 #服务端口号
   spring:
     application:
       name: microServiceCloudProviderDept  #微服务名称，对外暴漏的微服务名称，十分重要
   
   eureka:
     client: #将客户端注册到 eureka 服务列表内
       service-url:
         defaultZone: http://localhost:8001/eureka/  #这个地址是 8001注册中心在 application.yml 中暴露出来额注册地址 （单机版）
     instance:
       instance-id: spring-cloud-consumer-8003 #自定义服务名称信息
       prefer-ip-address: true  #显示访问路径的 ip 地址
   ```

3. 启动类
   ```
   package com.ocean;
   
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
   import org.springframework.cloud.openfeign.EnableFeignClients;
   
   @SpringBootApplication
   @EnableEurekaClient // 本服务启动后会自动注册进eureka服务中
   @EnableFeignClients
   public class MicroServiceCloudConsumer8003Application {
       public static void main(String[] args) {
           SpringApplication.run(MicroServiceCloudConsumer8003Application.class, args);
       }
   }
   ```

4. service层调用8002的http接口
   ```
   @Service
   @FeignClient(value = "microServiceCloudProviderDept")
   public interface DeptService {
       @GetMapping("/dept/get")
       List<Dept> findAll();
   }
   ```

5. controller层
   > 访问http://localhost:8003/consumer/dept/get可以调用localhost:8002/dept/get
   ```
   @RestController
   @RequestMapping("/consumer")
   public class DeptController {
      @Autowired
      private DeptService service;
   
      @GetMapping("/dept/get")
      public List<Dept> selectAll() {
          return service.findAll();
      }
   }
   ```