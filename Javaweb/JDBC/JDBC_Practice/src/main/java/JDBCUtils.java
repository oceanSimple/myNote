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
