# Druid连接池的使用

1. 将配置文件导入src/main/resources文件夹中

   ps；记得更改要使用的数据库的名称

2. 将lib目录复制到src/main/webapp/WEB-INF目录下

   ps：不是webapp下！！！！

3. 构建与数据库列相同的对象类

   成员变量的名称与数据库列名称保持一致

   成员变量的类型尽量写成Inter，Double等，可以赋值null

   类生成getter和setter，以及toString函数

4. 构建连接数据库的工具类

   ```java
   import com.alibaba.druid.pool.DruidDataSource;
   import com.alibaba.druid.pool.DruidDataSourceFactory;
   
   import javax.sql.DataSource;
   import java.io.IOException;
   import java.io.InputStream;
   import java.sql.Connection;
   import java.sql.SQLException;
   import java.util.Properties;
   
   public class JDBCUtils {
       private static DataSource ds;
   
       static {
           try {
               //加载配置文件
               Properties pro=new Properties();
               InputStream path=JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
               pro.load(path);
   
               //初始化连接池对象
               ds= DruidDataSourceFactory.createDataSource(pro);
           } catch (IOException e) {
               throw new RuntimeException(e);
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
       }
   
       public static DataSource getDataSource() {
           return ds;
       }
   
       //获取连接对象
       public static Connection getConnection(){
           try {
               return ds.getConnection();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }
   }
   ```

5. 调用方法对数据库进行操作

   ```java
   private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
   ```

   1. 用于执行新增、更新、删除等语句；

   ```java
   //用于执行新增、更新、删除等语句；
   //sql：需要执行的 SQL 语句；
   //args 表示需要传入到 SQL 语句中的参数。
   public int update(String sql);
   public int update(String sql,Object... args);
   ```

   ```java
   //实例操作
   User user=new User();
           user.setId(10);
           user.setName("海洋");
           user.setAge(20);
        user.setGender("男");
           String sql="insert into employee values (?,?,?,?)";
        int update = template.update(sql, user.getId(),user.getName(),user.getAge(),user.getGender());
   ```

   2. 查询语句

      queryForObject

   ```java
   String sql="select * from employee where id=1";
   User user = template.queryForObject(sql, 			new BeanPropertyRowMapper<User>(User.class));
   System.out.println(user);
   
   //Bean类会自动封装对象，前提是按要求写User类
   ```

   ```java
   //queryForList求所有数据，封装成Object类的List
   String sql="select * from employee";
   List<Map<String, Object>> maps = 		       template.queryForList(sql);
   for (Map<String, Object> map : maps) {
       System.out.println(map);
   }
   ```

   ```java
   //query求所有数据，封装成User类的List
   String sql="select * from employee";
   List<User> maps = template.query(sql,          new RowMapper<User>() {
       @Override
       public User mapRow(ResultSet resultSet, int i) throws SQLException {
           User user = new User();
           user.setId(resultSet.getInt("id"));
          user.setName(resultSet.getString("name"));
           user.setAge(resultSet.getInt("age"));
           user.setGender(resultSet.getString("gender"));
           return user;
       }
   });
   for (User map : maps) {
       System.out.println(map);
   }
   ```

   通过反射简化query封装所有数据

   ```java
   String sql="select * from employee";
   List<User> query = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
   for (User user : query) {
       System.out.println(user);
   }
   ```

   