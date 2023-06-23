# 安装包

```
go get -u github.com/gin-gonic/gin
```

简单示例：

```
package main

import (
	"github.com/gin-gonic/gin"
)

func main() {
	// 创建一个默认的路由引擎
	r := gin.Default()
	// GET：请求方式；/hello：请求的路径
	// 当客户端以GET方法请求/hello路径时，会执行后面的匿名函数
	r.GET("/hello", func(c *gin.Context) {
		// c.JSON：返回JSON格式的数据
		c.JSON(200, gin.H{
			"message": "Hello world!",
		})
	})
	// 启动HTTP服务，默认在0.0.0.0:8080启动服务
	r.Run()
}
```

<br/>

<br/>

# 获取前端的参数

1. get请求
   ```
   // 获取get请求参数
   name := c.Query("name")
   id := c.Query("id")
   ```
2. post请求
   ```
   // 获取post请求参数
   name := c.PostForm("name")
   id := c.PostForm("id")
   ```
3. json数据
   > 结构体
   ```
   type Person struct {
   	Name string `json:"name"`
   	Id   string `json:"id"`
   }
   ```
   ```
   // 获取post请求参数
   var person Person
   c.BindJSON(&person)
   ```
4. restful风格
   ```
   // restful风格的get请求
   r.GET("/getTest02/:name/:id", func(c *gin.Context) {
   	// 获取get请求参数
   	name := c.Param("name")
   	id := c.Param("id")
   
   	fmt.Println("name:", name)
   	fmt.Println("id:", id)
   	c.JSON(200, gin.H{
   		"message": "pong",
   	})
   })
   ```

5. shouldBind
   ```
   type Person struct {
   	Name string `json:"name" form:"name"`
   	Id   string `json:"id" form:"id"`
   }
   ```
   ```
   var person Person
   c.ShouldBind(&person)
   ```

<br/>

# 路由

```
shopGroup := r.Group("/shop")
  {
      shopGroup.GET("/index", func(c *gin.Context) {...})
      shopGroup.GET("/cart", func(c *gin.Context) {...})
      shopGroup.POST("/checkout", func(c *gin.Context) {...})
      // 嵌套路由组
      xx := shopGroup.Group("xx")
      xx.GET("/oo", func(c *gin.Context) {...})
  }
```

<br/>

<br/>

# gin标准开发

1. 安装gin、gorm、viper
   ```
   go get -u github.com/gin-gonic/gin
   go get -u gorm.io/gorm
   go get github.com/spf13/viper
   ```

2. 初始化配置文件
   > config/application.yml
   ```
   server:
     port: 8080
   datasource:
       driverName: mysql
       host: 127.0.0.1
       port: 3306
       database: data01
       username: root
       password: root
       charset: utf8mb4
       loc: Asia/Shanghai
   ```

3. 加载配置文件
   ```
   // 初始化配置文件
   InitConfig()
   
   func InitConfig() {
   	println("初始化配置文件")
   	workDir, _ := os.Getwd()
   	println(workDir)
   	viper.SetConfigName("application")
   	viper.SetConfigType("yml")
   	viper.AddConfigPath(workDir + "/config")
   	err := viper.ReadInConfig()
   	if err != nil {
   		panic("读取配置文件失败")
   	}
   }
   ```

4. 配置数据库工具类
   ```
   package mysqlConnect
   
   import (
   	"fmt"
   	"github.com/spf13/viper"
   	"gorm.io/driver/mysql"
   	"gorm.io/gorm"
   	"net/url"
   )
   
   // 定义一个全局变量db
   var DB *gorm.DB
   
   func Init() *gorm.DB {
   	//配置MySQL连接参数
   	username := viper.GetString("datasource.username") //账号
   	password := viper.GetString("datasource.password") //密码
   	host := viper.GetString("datasource.host")         //数据库地址，可以是Ip或者域名
   	port := viper.GetString("datasource.port")         //数据库端口
   	database := viper.GetString("datasource.database") //数据库名
   	charset := viper.GetString("datasource.charset")   // 字符集
   	loc := viper.GetString("datasource.loc")           // 时区
   	timeout := "10s"                                   //连接超时，10秒
   	//拼接下dsn参数
   	dsn := fmt.Sprintf("%s:%s@tcp(%s:%s)/%s?charset=%s&parseTime=True&loc=%s&timeout=%s", username, password, host, port, database, charset, url.QueryEscape(loc), timeout)
   	println(dsn)
   	// 声明err变量，下面不能使用:=赋值运算符，否则_db变量会当成局部变量，导致外部无法访问_db变量
   	var err error
   	//连接MySQL数据库
   	//连接MYSQL, 获得DB类型实例，用于后面的数据库读写操作。
   	DB, err = gorm.Open(mysql.Open(dsn), &gorm.Config{})
   	if err != nil {
   		panic("连接数据库失败, error=" + err.Error())
   	}
   
   	sqlDB, _ := DB.DB()
   
   	sqlDB.SetMaxIdleConns(20)  // SetMaxIdleConns 设置空闲连接池中连接的最大数量
   	sqlDB.SetMaxOpenConns(100) // SetMaxOpenConns 设置打开数据库连接的最大数量
   	return DB
   }
   
   // GetDB 获取数据库连接对象
   func GetDB() *gorm.DB {
   	return DB
   }
   ```

5. 设置路由
   ```
   package main
   
   import (
   	"gin/controller"
   	"github.com/gin-gonic/gin"
   )
   
   func UrlRoute(r *gin.Engine) *gin.Engine {
   	stuUrl := r.Group("/student")
   	stuUrl.GET("/getById", controller.GetStuById)
   	return r
   }
   
   
   使用路由
   r := gin.Default()
   r = UrlRoute(r)
   ```

6. controller
   ```
   package controller
   
   import (
   	"gin/mysqlConnect"
   	"gin/response"
   	"github.com/gin-gonic/gin"
   )
   
   type Stu struct {
   	Name string `json:"name" form:"name"`
   	Id   string `json:"id" form:"id"`
   }
   
   func GetStuById(ctx *gin.Context) {
   	db := mysqlConnect.GetDB()
   	stu := Stu{}
   	ctx.ShouldBind(&stu)
   	db.Debug().Find(&stu, stu.Id)
   	response.Success(ctx, gin.H{"stu": stu}, "success")
   }
   ```

7. response模板
   ```
   package response
   
   import (
   	"github.com/gin-gonic/gin"
   	"net/http"
   )
   
   func Response(ctx *gin.Context, httpStatus int, code int, data gin.H, msg string) {
   	ctx.JSON(httpStatus, gin.H{
   		"code": code,
   		"data": data,
   		"msg":  msg,
   	})
   }
   
   func Success(ctx *gin.Context, data gin.H, msg string) {
   	Response(ctx, http.StatusOK, 200, data, msg)
   }
   
   func Fail(ctx *gin.Context, data gin.H, msg string) {
   	Response(ctx, http.StatusOK, 400, data, msg)
   }
   ```

8. 启动端口
   ```
   port := viper.GetString("server.port")
   if port != "" {
   	r.Run(":8080")
   } else {
   	println("端口号为空")
   }
   ```