# 快速入门

## 基本语法

1. 后缀名为.xml
2. xml第一行必须定义文档声明
3. xml文档中，有且仅有一个根标签
4. 属性值必须使用引号包含，单双都可
5. 标签必须正确关闭
6. 标签名称区分大小写

## 组成部分

1. 文档声明

   ```xml
   <?xml 属性列表 ?>
   <?xml version="1.0" encoding="utf-8"?>
   ```

   属性列表：

   1. version-版本号，必须属性
   2. encoding-编码方式，默认值ISO-8859-1
   3. standalone-是否独立

2. 指令：与html类似，可以通过css进行渲染

3. 标签：标签名称自定义

   规则：

   1. 不能数字开头
   2. 不能以xml开头等

4. 属性：id属性唯一

5. 文本内容：

   CDATA区：该区域中的内容会被原样展示

   ```xml
   <![CDATA[
   	可以输入任意内容
   ]]>
   ```

# 约束

## DTD约束

引入dtd文档到xml文档中

1. 内部dtd-将约束规则定义在xml文档中,不常用

2. 外部dtd-将约束规则定义在外部的dtd文件中

   ```xml-dtd
   //本地
   <!DOCTYPE 根标签名 SYSTEM "文件路径.dtd">
   //网路
   <!DOCTYPE 跟标签名 PUBLIC "文件路径.dtd" "dtd文件的文字url">
   ```

## schema约束

```xml
<students   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 			 xmlns="http://www.itcast.cn/xml" 
 		   xsi:schemaLocation="http://www.itcast.cn/xml  student.xsd"
 		    >
```

# xml解析

## 解析方式

1. DOM：将标记语言文档一次性加载进内存，在内存中形成一个DOM树

   优点：操作方便，可以进行所有操作

   缺点：占内存

2. SAX：逐行读取，基于事件驱动

   优点：不占内存

   缺点：只能读取，不能增删改

## JSoup使用

### 快速入门

1. 导入jar包

2. 获取Document对象

3. 获取对应的标签Element对象

4. 获取数据

## 对象的使用

### Jsoup

工具类，可以解析html或xml文档，返回document对象

parse：解析html和xml文档，返回document

parse(File in,String charset);

------

### Document

---文档对象，代表dom树

获取Element对象

getsElementById-根据id属性值

getsElementByTag(String tagName)-根据标签名

getElementsByAttribute(String key)-根据属性名

getElementsByAttributeValue(String key,String value)-根据属性名和属性值



------

### Elements

---元素Element对象的集合，可以当作ArrayList<Element>来使用

------

### Element

元素对象

1. 获取子元素对象

   getsElementById-根据id属性值

   getsElementByTag(String tagName)-根据标签名

   getElementsByAttribute(String key)-根据属性名

   getElementsByAttributeValue(String key,String value)-根据属性名和属性值

2. 获取属性值

   String attr(String key)：根据属性名称

3. 获取文本内容

   String text()：获取子标签的纯文本内容

   String html()：获取标签体的所有内容，包括子标签的字符串内容

------

### Node

节点对象，是document和element的父类



# 快捷查找方式

## selector选择器

## XPath

导入jar包