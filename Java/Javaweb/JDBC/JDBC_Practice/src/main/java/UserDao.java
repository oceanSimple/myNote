import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDao {

    //声明JDBCTemplate对象共用
    private static JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    public static void main(String[] args) {
        String sql="select * from employee";
        List<User> query = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        for (User user : query) {
            System.out.println(user);
        }
    }
}
