# JSP脚本元素

## 声明变量与方法

```
<%!    声明变量      %>
里面声明的变量为类的成员变量
```

ps：多个用户打开同一个jsp文件共享同一个成员变量。



## Java程序片段

```
<%		%>
```



## 表达式

```
<%=     %>
```

## 指令标签

### page指令

```
<%@page 属性=“”	>
```

1. language

   默认为Java

2. import

   Java的导包

3. contentType

   默认写为

   ```
   "text/html;charset=uft-8"
   ```

4. session

   用于设置是否需要使用内置的session对象

   默认为true

5. buffer

   用来指定out设置的缓冲区的大小

   none为不使用缓冲区。默认大小8kb

### include指令

```
<%@ include file=""%>
```

常用于不同界面插入相同的页眉与尾注

## 动作标签

### include

与指令不同，这个属于动态插入，即Java代码编码完再进行加载

```
<jsp:include page=""/>
<jsp:include page=""></jsp:include>
```

### parameter

常用于传递值,与其他标签结合使用

传到其他jsp中时，用法与html标签的name使用类似

```
<jsp:param name="" value=""/>
```

### forward

重定向一个html、jsp文件

```jsp
<jsp:forward page={"path"}/>
<jsp:forward page={"path"}
	<jsp:param name="" value="">//目标必须为动态页面
</jsp:forward>
```

### useBean

<jsp:useBean>标签用于在指定的域范围内查找指定名称的JavaBean对象，如果存在则直接返回该JavaBean对象的引用，如果不存在则实例化一个新的JavaBean对象并将它以指定的名称存储到指定的域范围中。

```
<jsp:useBean id="" class="" scope="" />
```

1. id：实例名
2. class：完整的JavaBean包名
3. scope：作用范围
   1. page：当前页面
   2. request：在当前客户请求内有效
   3. session：对当前HttpSession内所有页面都有效，即一次会话
   4. appication：对具有相同ServletContext的页面都有效，即全局存储空间
4. type：指定引用该对象的变量的类型。
5. beanName：指定Bean的名字。



# JSP隐式对象

## request对象

常用方法

```
getParameter("");
setCharsetEncoding("utf-8");
getRequestDispatcher("目标路径").forward(request,response);
```

## response对象

略

## session对象

对于同一个客户端，在关闭浏览器前，session的id都是一致的

## application对象

所有客户端都共享一个application对象，直至服务器关闭。

```
setAttribute(key,value);
getAttibute(key);
getAttributeNames();
removeAttribute(key);
getServletInfo();	//获取Servlet编译器的当前版本信息
```

## out对象

```
println();
print();
```

## page对象

几乎不使用，略

## pageContext对象

| 方 法                                                        | 说 明                                                        |
| ------------------------------------------------------------ | :----------------------------------------------------------- |
| Object findAttribute (String AttributeName)                  | 按 page、request、session、application 的顺序查找指定的属性，并返回对应的属性值。如果没有相应的属性，则返回 NULL |
| Object getAttribute (String AttributeName, int Scope)        | 在指定范围内获取属性值。与 findAttribute 不同的是，getAttribute 需要指定查找范围 |
| void removeAttribute(String AttributeName, int Scope)        | 在指定范围内删除某属性                                       |
| void setAttribute(String AttributeName, Object AttributeValue, int Scope) | 在指定范围内设置属性和属性值                                 |
| Exception getException()                                     | 返回当前页的 Exception 对象                                  |
| ServletRequest getRequest()                                  | 返回当前页的 request 对象                                    |
| ServletResponse getResponse()                                | 返回当前页的 response 对象                                   |
| ServletConfig getServletConfig()                             | 返回当前页的 ServletConfig 对象                              |
| HttpSession getSession()                                     | 返回当前页的 session 对象                                    |
| Object getPage()                                             | 返回当前页的 page 对象                                       |
| ServletContext getServletContext()                           | 返回当前页的 application 对象                                |

## 四大作用域

1. application：应用程序级别
2. session：会话级别
3. request：请求级别
4. page：页面级别



# EL表达式

```
${}
```

基本语法

1. EL提供.、[]两种运算符来导航数据。

2. 内置对象

   

| 内置对象         | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| pageScope        | 获取 page 范围的变量                                         |
| requestScope     | 获取 request 范围的变量                                      |
| sessionScope     | 获取 session 范围的变量                                      |
| applicationScope | 获取 application 范围的变量                                  |
| param            | 相当于 request.getParameter(String name)，获取单个参数的值   |
| paramValues      | 相当于 request.getParameterValues(String name)，获取参数集合中的变量值 |
| header           | 相当于 request.getHeader(String name)，获取 HTTP 请求头信息  |
| headerValues     | 相当于 request.getHeaders(String name)，获取 HTTP 请求头数组信息 |
| initParam        | 相当于 application.getInitParameter(String name)，获取 web.xml 文件中的参数值 |
| cookie           | 相当于 request.getCookies()，获取 cookie 中的值              |
| pageContext      | 表示当前 JSP 页面的 pageContext 对象                         |

3. 运算符

   | EL比较运算符 | 说明     | 范例                                              | 结果        |
   | ------------ | -------- | ------------------------------------------------- | ----------- |
   | == 或 eq     | 等于     | ${6==6} 或 ${6 eq 6} ${"A"="a"} 或 ${"A" eq "a"}  | true false  |
   | != 或 ne     | 不等于   | ${6!=6} 或 ${6 ne 6} ${“A"!=“a”} 或 ${“A” ne “a”} | false true  |
   | < 或 lt      | 小于     | ${3<8} 或 ${3 lt 8} ${"A"<"a"} 或 ${"A" lt "a"}   | true true   |
   | > 或 gt      | 大于     | ${3>8} 或 ${3 gt 8} ${"A">"a"} 或 ${"A" gt "a"}   | false false |
   | <= 或 le     | 小于等于 | ${3<=8} 或 ${3 le 8} ${"A"<="a"} 或 ${"A" le "a"} | true true   |
   | >= 或 ge     | 大于等于 | ${3>=8} 或 ${3 ge 8} ${"A">="a"} 或 ${"A" ge "a"} | false false |

4. 禁用/启用EL表达式

   ```
   <%@page isELIgnoed="true|false"%>
   默认为false
   ```

   

# JSTL标签

## 导包

将lib文件导入到web-inf下。

```
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

## 核心标签

1. <c:out value="" escapeXml="" default=""/>

   | **属性**  | **描述**            | **是否必要** | **默认值**   |
   | :-------- | :------------------ | :----------- | :----------- |
   | value     | 要输出的内容        | 是           | 无           |
   | default   | 输出的默认值        | 否           | 主体中的内容 |
   | escapeXml | 是否忽略XML特殊字符 | 否           | true         |

   若value=null，则输出默认值

2. set

   | **属性** | **描述**               | **是否必要** | **默认值** |
   | :------- | :--------------------- | :----------- | :--------- |
   | value    | 要存储的值             | 否           | 主体的内容 |
   | target   | 要修改的属性所属的对象 | 否           | 无         |
   | property | 要修改的属性           | 否           | 无         |
   | var      | 存储信息的变量         | 否           | 无         |
   | scope    | var属性的作用域        | 否           | Page       |

```
<c:set
   var="<string>"
 *  value="<string>"
 *  target="<string>"  //绑定
   property="<string>"
   scope="<string>"/>
```

3. remove

   | **属性** | **描述**         | **是否必要** | **默认值** |
   | :------- | :--------------- | :----------- | :--------- |
   | var      | 要移除的变量名称 | 是           | 无         |
   | scope    | 变量所属的作用域 | 否           | 所有作用域 |

```
<c:remove var="<string>" scope="<string>"/>
```

4. error

   ```
   <c:catch var="<string>">
   ...
   </c:catch>
   ```

5. if

   ```
   <c:if test="<boolean>" var="<string>" scope="<string>">
      ...
   </c:if>
   ```

   | **属性** | **描述**               | **是否必要** | **默认值** |
   | :------- | :--------------------- | :----------- | :--------- |
   | test     | 条件                   | 是           | 无         |
   | var      | 用于存储条件结果的变量 | 否           | 无         |
   | scope    | var属性的作用域        | 否           | page       |

5. ```
   <body>
   <c:set var="salary" scope="session" value="${2000*2}"/>
   <p>你的工资为 : <c:out value="${salary}"/></p>
   <c:choose>
       <c:when test="${salary <= 0}">
          太惨了。
       </c:when>
       <c:when test="${salary > 1000}">
          不错的薪水，还能生活。
       </c:when>
       <c:otherwise>
           什么都没有。
       </c:otherwise>
   </c:choose>
   </body>
   ```

6. forEach，forTokens

   ```
   <c:forEach
       items="<object>"
       begin="<int>"
       end="<int>"
       step="<int>"
       var="<string>"
       varStatus="<string>">
   
       ...
   ```

   ```
   <c:forTokens
       items="<string>"
       delims="<string>"
       begin="<int>"
       end="<int>"
       step="<int>"
       var="<string>"
       varStatus="<string>">
   ```

   | **属性**  | **描述**                                   | **是否必要** | **默认值**   |
   | :-------- | :----------------------------------------- | :----------- | :----------- |
   | items     | 要被循环的信息                             | 否           | 无           |
   | begin     | 开始的元素（0=第一个元素，1=第二个元素）   | 否           | 0            |
   | end       | 最后一个元素（0=第一个元素，1=第二个元素） | 否           | Last element |
   | step      | 每一次迭代的步长                           | 否           | 1            |
   | var       | 代表当前条目的变量名称                     | 否           | 无           |
   | varStatus | 代表循环状态的变量名称                     | 否           | 无           |

forTokens多一个属性

| **属性** | **描述** | **是否必要** | **默认值** |
| :------- | :------- | :----------- | :--------- |
| delims   | 分隔符   | 是           | 无         |

**c:forEach varStatus 属性**

-  **current**: 当前这次迭代的（集合中的）项
-  **index**: 当前这次迭代从 0 开始的迭代索引
-  **count**: 当前这次迭代从 1 开始的迭代计数
-  **first**: 用来表明当前这轮迭代是否为第一次迭代的标志
-  **last**: 用来表明当前这轮迭代是否为最后一次迭代的标志
-  **begin**: 属性值
-  **end**: 属性值
-  **step**: 属性值

7. import

   ```
   <c:import
      url="<string>"
      var="<string>"
      scope="<string>"
      varReader="<string>"
      context="<string>"
      charEncoding="<string>"/>
   ```

   | **属性**     | **描述**                                                     | **是否必要** | **默认值**   |
   | :----------- | :----------------------------------------------------------- | :----------- | :----------- |
   | url          | 待导入资源的URL，可以是相对路径和绝对路径，并且可以导入其他主机资源 | 是           | 无           |
   | context      | 当使用相对路径访问外部context资源时，context指定了这个资源的名字。 | 否           | 当前应用程序 |
   | charEncoding | 所引入的数据的字符编码集                                     | 否           | ISO-8859-1   |
   | var          | 用于存储所引入的文本的变量                                   | 否           | 无           |
   | scope        | var属性的作用域                                              | 否           | page         |
   | varReader    | 可选的用于提供java.io.Reader对象的变量                       | 否           | 无           |

```
<body>
<c:import var="data" url="http://www.runoob.com"/>
<c:out value="${data}"/>
</body>
```

8. redirect

   ```
   <c:redirect url="<string>" context="<string>"/>
   ```

   | **属性** | **描述**                         | **是否必要** | **默认值**   |
   | :------- | :------------------------------- | :----------- | :----------- |
   | url      | 目标URL                          | 是           | 无           |
   | context  | 紧接着一个本地网络应用程序的名称 | 否           | 当前应用程序 |

```
<body>
<c:redirect url="http://www.runoob.com"/>
</body>
```

9. url

   ```
   <c:url
     var="<string>"
     scope="<string>"
     value="<string>"
     context="<string>"/>
   ```

   | **属性** | **描述**               | **是否必要** | **默认值**    |
   | :------- | :--------------------- | :----------- | :------------ |
   | value    | 基础URL                | 是           | 无            |
   | context  | 本地网络应用程序的名称 | 否           | 当前应用程序  |
   | var      | 代表URL的变量名        | 否           | Print to page |
   | scope    | var属性的作用域        | 否           | Page          |

```
<body>
    <h1>&lt;c:url&gt实例 Demo</h1>
    <a href="<c:url value="http://www.runoob.com"/>">
     这个链接通过 &lt;c:url&gt; 标签生成。
    </a>
</body>
```

## 国际化标签库

```
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
```

略

## SQL标签库

```
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
```

### 设置数据源

```
<sql:setDataSource
  var="<string>"
  scope="<string>"
  dataSource="<string>"
  driver="<string>"
  url="<string>"
  user="<string>"
  password="<string>"/>
```

| **属性**   | **描述**             | **是否必要** | **默认值** |
| :--------- | :------------------- | :----------- | :--------- |
| driver     | 要注册的JDBC驱动     | 否           | 无         |
| url        | 数据库连接的JDBC URL | 否           | 无         |
| user       | 数据库用户名         | 否           | 无         |
| password   | 数据库密码           | 否           | 无         |
| dataSource | 事先准备好的数据库   | 否           | 无         |
| var        | 代表数据库的变量     | 否           | 默认设置   |
| scope      | var属性的作用域      | 否           | Page       |

```
<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
                       url="jdbc:mysql://localhost/data01"
                       user="root"  password="root"/>
    <sql:query dataSource="${snapshot}" sql="..." var="result" />
```

### 查询标签

```
<sql:query
  var="<string>"
  scope="<string>"
  sql="<string>"
  dataSource="<string>"
  startRow="<string>"
  maxRows="<string>"/>
```

| **属性**   | **描述**                                  | **是否必要** | **默认值** |
| :--------- | :---------------------------------------- | :----------- | :--------- |
| sql        | 需要执行的SQL命令（返回一个ResultSet对象) | 否           | Body       |
| dataSource | 所使用的数据库连接（覆盖默认值）          | 否           | 默认数据库 |
| maxRows    | 存储在变量中的最大结果数                  | 否           | 无穷大     |
| startRow   | 开始记录的结果的行数                      | 否           | 0          |
| var        | 代表数据库的变量                          | 否           | 默认设置   |
| scope      | var属性的作用域                           | 否           | Page       |

查询案例实例

```jsp
<body>
<sql:setDataSource var="snapshot"driver="com.mysql.jdbc.Driver"
url="jdbc:mysql://localhost/data01"
user="root"  password="root"/>

<sql:query dataSource="${snapshot}" var="result">
        SELECT * from stu;
</sql:query>

    <table border="1" width="100%">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Age</th>
            <th>Gender</th>
        </tr>
        <c:forEach var="row" items="${result.rows}">
            <tr>
                <td><c:out value="${row.id}"/></td>
                <td><c:out value="${row.name}"/></td>
                <td><c:out value="${row.age}"/></td>
                <td><c:out value="${row.sex}"/></td>
            </tr>
        </c:forEach>
    </table>

</body>
```

### 修改标签

```
<sql:update var="<string>" scope="<string>" sql="<string>" dataSource="<string>"/>
```

```
    <sql:update dataSource="${snapshot}">
        delete from stu where id=1
    </sql:update>
```

| **属性**   | **描述**                                 | **是否必要** | **默认值** |
| :--------- | :--------------------------------------- | :----------- | :--------- |
| sql        | 需要执行的SQL命令（不返回ResultSet对象） | 否           | Body       |
| dataSource | 所使用的数据库连接（覆盖默认值）         | 否           | 默认数据库 |
| var        | 用来存储所影响行数的变量                 | 否           | 无         |
| scope      | var属性的作用域                          |              |            |