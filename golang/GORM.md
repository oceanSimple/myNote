# 连接mysql

1. 下载包
   ```
   go get -u gorm.io/gorm
   ```
   ```
   import (
     "gorm.io/driver/mysql"
     "gorm.io/gorm"
   )
   ```

2. 配置连接参数
   ```
     //配置MySQL连接参数
   	username := "root"  //账号
   	password := "123456" //密码
   	host := "127.0.0.1" //数据库地址，可以是Ip或者域名
   	port := 3306 //数据库端口
   	Dbname := "tizi365" //数据库名
   	timeout := "10s" //连接超时，10秒
   	
   	//拼接下dsn参数, dsn格式可以参考上面的语法，这里使用Sprintf动态拼接dsn参数，因为一般数据库连接参数，我们都是保存在配置文件里面，需要从配置文件加载参数，然后拼接dsn。
   	dsn := fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8&parseTime=True&loc=Local&timeout=%s", username, password, host, port, Dbname, timeout)
   ```

3. 连接数据库
   ```
   	//连接MYSQL, 获得DB类型实例，用于后面的数据库读写操作。
   db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{})
   if err != nil {
     panic("连接数据库失败, error=" + err.Error())
   }
   ```

<br/>

<br/>

# 工具类

```
package tools

import (
	"fmt"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

// 定义一个全局变量db
var _db *gorm.DB

func init() {
	//配置MySQL连接参数
	username := "root"  //账号
	password := "root"  //密码
	host := "127.0.0.1" //数据库地址，可以是Ip或者域名
	port := 3306        //数据库端口
	Dbname := "data01"  //数据库名
	timeout := "10s"    //连接超时，10秒
	//拼接下dsn参数, dsn格式可以参考上面的语法，这里使用Sprintf动态拼接dsn参数，因为一般数据库连接参数，我们都是保存在配置文件里面，需要从配置文件加载参数，然后拼接dsn。
	dsn := fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8&parseTime=True&loc=Local&timeout=%s", username, password, host, port, Dbname, timeout)

	// 声明err变量，下面不能使用:=赋值运算符，否则_db变量会当成局部变量，导致外部无法访问_db变量
	var err error
	//连接MySQL数据库
	//连接MYSQL, 获得DB类型实例，用于后面的数据库读写操作。
	_db, err = gorm.Open(mysql.Open(dsn), &gorm.Config{})
	if err != nil {
		panic("连接数据库失败, error=" + err.Error())
	}

	sqlDB, _ := _db.DB()

	sqlDB.SetMaxIdleConns(20)  // SetMaxIdleConns 设置空闲连接池中连接的最大数量
	sqlDB.SetMaxOpenConns(100) // SetMaxOpenConns 设置打开数据库连接的最大数量
}

// GetDB 获取数据库连接对象
func GetDB() *gorm.DB {
	return _db
}

```

> 使用

```
db := tools.GetDB()
```

<br/>

# 增

```
// 查询单条数据
//var stu Stu
//db.Debug().First(&stu, "id=?", 2)
//println(stu.Id, stu.Name)

// 创建单条数据
//reslut := db.Debug().Create(&Stu{Name: "夕"})
//println(reslut.RowsAffected)

// 创建多条数据
//reslut := db.Debug().Create([]Stu{{Name: "年"}, {Name: "令"}, {Name: "零"}})
//println(reslut.RowsAffected)

// 用指定的字段创建记录
//reslut := db.Debug().Select("name").Create(&Stu{Name: "银灰"})
//println(reslut.RowsAffected)

// 创建记录并忽略要省略的传递字段的值
//result := db.Debug().Omit("id").Create(&Stu{Name: "艾雅法拉"})
//println(result.RowsAffected)
```

<br/>

# 查

```
// 查询多条数据
var stus []Stu
db.Debug().Where("id > ?", 5).Find(&stus)
for _, stu := range stus {
	println(stu.Id, stu.Name)
}
```

```
// 查询多条数据，指定查询字段，指定排序字段，指定偏移量和限制条数
db.Debug().Select("id, name").Where("id > ?", 5).Order("id desc").Offset(1).Limit(2).Find(&stus)
for _, stu := range stus {
	println(stu.Id, stu.Name)
}
```

<br/>

<br/>

# 改

1. 更改整条信息
   ```
   stu := Stu{}
   // 查询id为1的记录
   db.Debug().Where("id = ?", 1).Find(&stu)
   fmt.Println(stu)
   stu.Name = "远牙"
   // 更新记录
   result := db.Debug().Save(&stu)
   println(result.RowsAffected)
   ```

2. 更改特定列
   > stu中含有id，通过id进行修改！
   ```
   db.Model(&stu).Update("name", "稀音")
   ```

<br/>

# 删

1. 根据主键删除
   ```
   db.Delete(&User{}, 10)
   // DELETE FROM users WHERE id = 10;
   
   db.Delete(&User{}, "10")
   // DELETE FROM users WHERE id = 10;
   
   db.Delete(&users, []int{1,2,3})
   // DELETE FROM users WHERE id IN (1,2,3);
   
   ```

2. 删除一条记录
   ```
   // Email 的 ID 是 `10`
   db.Delete(&email)
   // DELETE from emails where id = 10;
   
   // 带额外条件的删除
   db.Where("name = ?", "jinzhu").Delete(&email)
   // DELETE from emails where id = 10 AND name = "jinzhu";
   ```

3. 批量删除
   ```
   // 通过主键删除
   var users = []User{{ID: 1}, {ID: 2}, {ID: 3}}
   db.Delete(&users)
   // DELETE FROM users WHERE id IN (1,2,3);
   
   db.Delete(&users, "name LIKE ?", "%jinzhu%")
   // DELETE FROM users WHERE name LIKE "%jinzhu%" AND id IN (1,2,3); 
   ```
   ```
   // 模糊删除
   db.Where("email LIKE ?", "%jinzhu%").Delete(&Email{})
   // DELETE from emails where email LIKE "%jinzhu%";
   
   db.Delete(&Email{}, "email LIKE ?", "%jinzhu%")
   // DELETE from emails where email LIKE "%jinzhu%";
   ```