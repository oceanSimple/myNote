# 客户端脚本语言的标准

## 基本语法

1. 与html结合

   内部JS：包含在<script></script>标签内

   外部JS：<script src=""></script>

2. 注释

   与Java类似
   
3. 原始数据类型

   1. number-整数/小数/NaN
   2. string-字符/字符串
   3. boolean-true/false
   4. null-空
   5. undefined-未定义，变量没有赋值的状态

4. 变量

   1. 语法：var	a=1；

       			var	b="ocean"

   2. 返回变量类型的方法

      typeof();

      所有对象都为true

5. 运算符

   特殊：比较时，先转换成相同类型，再比较

   ​			"==="：先比较类型，类型不同直接返回false

6. 特殊

   1. ```javascript
      var b=4;	//定义的局部变量
      b=4;		//定义的全局变量
      ```

## 基本对象

### Function对象

1. 创建

   ```javascript
   function 方法名称（）{};
   ```

2. 定义的形参列表无用

   arguments数组封装了所有形参

### Array对象

1. 创建

   ```javascript
   var arr=new Array(元素列表);
   var arr=new Array(默认长度);
   var arr=[元素列表]
   ```

2. 特点

   1. 数组元素类型是可变

3. 方法

   ```javascript
   join();		//将数组中的元素按指定分隔符拼成字符串
   push();		//向数组尾部添加元素，并返回数组长度
   ```

### Date对象

1. 创建

   ```
   var date=new Date();
   ```

2. 方法

   ```javascript
   tolocaleString();	//返回当前date对象对应的时间本地字符串格式
   getTime();			//获取毫秒值
   ```

### Math对象

1. 创建

   ```javascript
   //静态方法，不用创建
   Math.PI
   ```

2. 方法

   ```javascript
   //随机返回一个【0，1）的随机数
   Math.random();
   ```

### RegExp对象

1. 正则表达式：定义字符串的组成规则

2. 语法：

   1. 单个字符：[]

      如：[a],[ab],[a-zA-Z0-9]

      *特殊符号

      1. \d-单个数字字符
      2. \w-单个单词字符-[a-zA-Z0-9]

   2. 量词符号

      1. ？：表示出现0或1次
      2. *：表示出现0或多次
      3. +：表示出现1或多次
      4. {m,n}：表示m<=数量<=n

   3. 开始结束符号

      *^：开始

      *$：结束

3. 方法

   1. 创建

      ```javascript
      var reg=new RegEXP("正则表达式");
      var reg=/正则表达式/;
      //实例
      var reg=/^\w{6,12}$/;
      ```

   2. 方法

      test（）;

### Global对象

全局变量，可以直接调用

方法

```javascript
encodeURI():url编码
decodeURI():url解码

encodeURIComponent():url编码，编码的字符更多
decodeURIComponent():url解码
```

gkb编码：一个中文字符串占2个字节，一个字节8个二进制位

utf-8：一个中文字符串占3个字节

```
parseInt():	将字符串转为数字
			逐一判断每一个字符是否为数字，直到不是数字为止
			返回前面的数字部分
eval():	将JavaScript字符串作为脚本代码来执行
```

# BOM

将浏览器的各个组成部分封装成对象

## 组成

### Window-窗口对象

1. 创建

   不需要创建对象，可以直接使用

2. 方法

   ```javascript
   //与弹出框有关的方法
   alert();	//显式带有一段消息和一个确定按钮的警告框
   confirm();	//带有一段消息以及确定取消按钮的对话框
   			//确定返回true，取消返回false
   prompt();	//可提示用户输入的对话框
   			//返回输入的值
   ```

   ```javascript
   //与打开关闭有关的方法
   close();		//关闭浏览器窗口，只能关闭当前窗口
   open("窗口路径");	//打开浏览器窗口
   				  //可以返回一个窗口对象，便于关闭
   ```

   ```JavaScript
   //与定时器相关的方法
   setTimeout();		//在指定时间后调用函数
   					//参数：js代码，毫秒值
   clearTimeout();		//取消timeout
   
   setInterval();		//按照指定的周期来调用函数
   clearInterval();
   ```

3. 属性

   1. 获取其他BOM对象

      history，location，Navigator，Screen

   2. 获取DOM对象

      document

### Location-地址栏对象

1. 创建

   ```javascript
   window.location;
   location;
   ```

2. 方法

   ```javascript
   reload();	//刷新
   location.href("")	//更改当前页面的路径
   ```

3. 属性

   href：设置或返回完整的URL

### History-历史记录对象

1. 创建

   ```javascript
   window.history;
   history;
   ```

2. 方法

   ```javascript
   back();
   forward();
   go();
   ```

3. 属性

   length：返回当前窗口历史列表中的url的数量

# DOM

## Document对象

1. 方法

   ```javascript
   //获取Element对象
   document.getElementByID();
   document.getElementsByTagName();	//根据元素名称
   document.getElementsByClassName();	//根据class
   document.getElementsByName;			//根据name
   ```

   ```javaScript
   //创建其他DOM对象
   createAttribute(name);	//创建属性节点
   createComment();		//注释节点
   createElement();		//元素节点
   createTextNode();		//文本节点
   ```

2. 属性

## Element对象

方法

```javascript
removeAttribute();
setAttribute();

//实例
var element=document.getElementsById("<a>中超链接");
element.setAttribute("href","网址链接");
```

## Node对象

1. 特点：

   1. 是所有类的父类
   2. 所有dom对象都可以认为是一个节点

2. 方法

   ```javascript
   appendChild();	//向当前节点的子节点列表的结尾添加新的  					子节点
   removeChild();	//删除指定指定的节点
   replace();		//替换子节点
   ```

# HTML DOM

方法：innerHTML