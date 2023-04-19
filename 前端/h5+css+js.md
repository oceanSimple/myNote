# 1. HTML标签

## 基础标签

```
标题标签：<h1>	<h2>
段落标签：<p>
换行标签：<br/>
水平线标签：<hr/>
粗体标签：<strong>
斜体标签：<em>

特殊符号：
	空格:&nbsp
```

## 图像标签

```
<img src="资源路径" path="" alt="加载失败后显示" title="鼠标悬停文字" width="" height="">
```

## 超链接标签

```
<a href="目标跳转" target="打开方式">
	文字或图片均可
</a>

锚点链接
<a id="top"/>
<a href="#top">回到顶部</a>

功能性标签：邮箱标签
<a href="mailto:1252074183@qq.com">点击</a>
```

## 行内元素与块元素

```
**块元素
无论内容多少，独占一行
p，h1等

**行内元素
内容撑开宽度，左右都是行内元素的可以排在一行
a，strong，em
```

## 列表标签

```
<!--有序列表-->
<ol>
    <li>a</li>
    <li>b</li>
    <li>c</li>
</ol>
<hr>
<!--无序列表-->
<ul>
    <li>a</li>
    <li>b</li>
    <li>c</li>
</ul>

<!--自定义列表-->
<dl>
    <dt>标题1</dt>
    <dd>a</dd>
    <dd>b</dd>
    <dd>c</dd>

    <dt>标题2</dt>
    <dd>a</dd>
    <dd>b</dd>
    <dd>c</dd>
</dl>
```

## 表格标签

行	tr

列	td

跨列	colspan

跨列	rowspan

## 媒体标签

```
视频
<!--资源路径，控制条，自动播放-->
<video src=".mp4" controls autoplay></video>
音频
<audio src=".mp3" controls autoplay></audio>
```

## 页面结构

```
header	头部
section	web页面中一个独立区域
footer	尾部
article	独立文章内容
aside	相关内容或应用（常用于侧边栏）
nav		导航类辅助内容
```

## iframe内联框架

```
<iframe src="https://www.bilibili.com/" frameborder="0" name="bilibili"  width="1080px" height="800px"></iframe>
```

## 表单标签

```
<!--
    action：表单提交的位置，可以是网站，也可以是一个请求处理地址
    method：提交方式

    value：标识初始值
    maxlength：为text时最大输入长度
    size：长度
-->
<form action="demo01.html" method="get">
    <p>名字：<input type="text" id="name" value="ocean"></p>
    <p>密码：<input type="password" id="pwd"></p>
    <p><input type="submit"></p>
    <p><input type="reset"></input></p>

    <p>下拉框：
        <select name="列表名称">
            <option value="la">中国</option>
            <option value="la">美国</option>
            <option value="la" selected>日本</option>
            <option value="la">瑞士</option>
        </select>
    </p>

    <p>
    文本域：
    <textarea name="textarea" id="" cols="30" rows="10"></textarea>
    </p>

    <p>
        <input type="file" value="hello">
        <input type="button" value="上传" name="upload">
    </p>
</form>
```

```
<p>email：<input type="email"></p>
<p>url：<input type="url"></p>
<p>number：<input type="number" max="100" min="0" step="10"></p>
<p>音量（滑块）：<input type="range" min="0" max="100" name="volume" value="0"></p>
<p>搜索框：<input type="search"></p>
```

## 表单属性

```
readonly	只读
disabled	禁用
hidden		隐藏
```

```
<!-- 增强鼠标可用性 -->
<p>
    <label for="textarea">文本域：</label>
    <textarea id="textarea"  cols="30" rows="10"></textarea>
</p>
```

## 表单验证

```
提示消息：placeholder
<p>名字：<input type="text" id="name" placeholder="请输入用户名"></p>

非空判断：required
<p>密码：<input type="password" id="pwd" required></p>

正则表达式验证：pattern
<p>密码：<input type="password" id="pwd" pattern="正则表达式" required></p>
```





# 2. CSS-选择器

## 2.1 四种css导入方式

1. 行内样式：在标签元素中，编写一个style属性，编写样式（不推荐）

2. 内部样式表：写在head、style标签内，优先级低于行内样式

3. 外部样式：优先级遵从就近原则

   ```
   链接式
   <link rel="stylesheet" href="css/style.css">
   导入式（不推荐）
   <style>
   	@import "css/style.css";
   </style>
   ```

## 2.2 选择器

作用：选择页面上的某一个或某一类元素标签

不遵从就近原则

优先级：id>类>标签选择器

1. 标签选择器

   会选择页面上所有这个标签

   ```
   <style>
       h1{
       	background: aqua;
       }
   </style>
   ```

2. 类选择器

   好处，同类归类

   ```
   .ocean{
   	background: red;
   }
   <h2 class="ocean">好</h2>
   ```

3. id选择器

   id全局唯一

   ```
   #id{
   }
   ```

## 2.3 层次选择器（略）

1. 后代选择器：在某个元素的后面

   ```
   body p{
   	background:red;
   }
   ```

   body后面所有的p选择器

2. 子选择器

   ```
   body>p{
   	background:red;
   }
   ```

   body下一层的p选择器

   

## 2.4 结构伪类选择器（略）

伪类：

```html
<style>
    ul li:first-child{
      background: red;
    }
    /*选中当前父级元素的第n个为p的元素*/
    p:nth-of-type(2){
      background: red;
    }
</style>

<body>
  <p>p1</p>
  <p>p2</p>
  <p>p3</p>
  <ul>
    <li>li1</li>
    <li>li2</li>
    <li>li3</li>
  </ul>
</body>
```



## 2.5 属性选择器（常用）

```
a[id]{
	选中含a标签中含有id属性的
}
a[id=ocean]{
	选中a标签中id=ocean的标签
}
a[class="ocean"]{
	=：类等于ocean
}
a[class*="ocean"]{
	*=：包含ocean类
}
a[href^="ocean"]{
	^=：以ocean开头的href
}
$=：以ocean结尾
```

# 3. CSS-美化网页的元素

## 3.1 字体样式

```
font-family		字体
font-size		大小
font-weight		粗细
color			颜色

font:oblique bolder 12px "楷体"；
（斜体，粗细，大小，字体）
```

## 3.2 文本样式

```
color:rgb(0,0,0);
	  rgba(0,0,0,a)	a=[0,1];透明度
text-align			文本排版
text-indent:2em		首行缩进两个字体
line-height			行高
height				块高（当行高与块高相同时，上下居中）

text-decoration:underline;	下划线
				linethrough；中划线
				overline	上划线
				none		去掉a标签的下划线
				
文本图片水平对齐
img,span{
	vertical-align:middle;
}
```

## 3.3 文本阴影和超链接伪类

a的伪类

```
a:link        有链接属性时
a:visited    链接地址已被访问过
a:active     被用户激活(在鼠标点击与释放之间发生的事件)
a:hover      其鼠标悬停
```

文本阴影

```
#ocean{
      text-shadow: aqua 2px -2px;
}
(颜色，水平偏移，垂直偏移)
```

## 3.4 列表

```
<style>
	ul li{
        /*none：去掉圆点
        circle：空心圆
        decimal：数字
        square：正方形
        */
		list-style: none;
		}
</style>
```

## 3.5 背景图片

```
<style>
    div{
        /*颜色，图片，横坐标，纵坐标，不重复*/
        background:red url("") 100px 10px no-repeat;
        background-image: url("") ;
        /*水平平铺*/
        background-repeat: repeat-x;
        /*垂直平铺*/
        background-repeat: repeat-y;
        /*不平铺*/
        background-repeat: no-repeat;
    }
</style>
```

# 4. CSS-盒子模型

## 4.1 基础介绍

margin：外边距

padding：内边距

border：边框

## 4.2 边框

```
width: 300px;
/*边框粗细，样式，颜色*/
/*bold：实线   dashed：虚线*/
border: 1px solid red;
```

## 4.3 内外边距

外边距用处：居中元素

```
margin:0 auto;		上下为0，左右自动---居中
```

## 4.4 圆角边框与阴影

```
//画圆
border-radius: 100px;
//阴影
box-shadow
```

| *h-shadow* | 必需。水平阴影的位置。允许负值。         |
| ---------- | ---------------------------------------- |
| *v-shadow* | 必需。垂直阴影的位置。允许负值。         |
| *blur*     | 可选。模糊距离。                         |
| *spread*   | 可选。阴影的尺寸。                       |
| *color*    | 可选。阴影的颜色。请参阅 CSS 颜色值。    |
| inset      | 可选。将外部阴影 (outset) 改为内部阴影。 |

## 4.5 浮动(略)

块级元素可以包含行内元素

```
display:block			将行变块
		inline			行内元素
		inline-block	块元素，但可以内联，在一行
		none
```



# 5. JavaScript-数据类型

## 5.1 引入script

严格性检测：'use strict'

```
<script src="script/demo01.js"></script>
```

## 5.2 数据类型-概要

number：包括整数，小数，科学计数法，负数，NaN，Infinity

string：‘ ‘ ，“　”

布尔值：true,false

逻辑运算：&&,||,!

比较运算符：	=		赋值

​							==	等于，类型不一样，值一样

​							===	绝对等于，类型也要一样

null，undefined

对象：

```
var person={
	name:'ocean',
	age:3,
	gender:'male'
}
```

## 5.3字符串

1. 单引号或双引号包裹

2. 注意转义字符

3. 多行字符串编写

   ```
   var str=`
   lalala
   ocean
   `;
   ```

4. substring方法

   ```
   str.substring(1);	//[1,尾)
   str.substring(1,3);	//[1,3)
   ```

## 5.4 foreach

```
'use strict'
let a=[11,12,13,14,15];
a[9]=19;
a.forEach(function (value){
    console.log(value);
})


for (const number of a) {
    console.log(number)
}
```

## 5.5 Map与Set

```
Map：
    <script>
      'use strict'
      let map=new Map([['sea',20],['hello',30]]);
      map.set('ocean',100);
      console.log(map.get('ocean'));
      console.log(map.get('sea'));
    </script>
```

Set去重集合

## 5.6 函数

```
function abs(){

}
```





# 6. BOM对象

```
document.getElementById();
document.getElementsByName();
document.getElementsByTagName();
```

```
document.cookie;
```



# 7. DOM对象

