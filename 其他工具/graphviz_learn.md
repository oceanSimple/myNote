# 基本语法

1. 注释

   ```
   //或/* */
   ```

2. 有向图   digraph   ->

3. 无向图   graph     --

4. 属性 node[attribute1=value1, attribute2=value2]
   大小： size=”2,2”; 单位为英寸
   标签： label=”显示在图上的内容”
   边：edge [color=red,style=dotted]; 这句话之后生效
   节点：node [color=navy]; 这句话之后生效
   边方向：rankdir=参数值；LR（从左到右），RL，BT，TB
   节点形状： a[shape=box]; 默认是椭圆
   边框大小：a[width=.1,height=2.5]; 单位为英寸
   边框颜色：a[color=red];

5. 构造边

   | 关系   | 有向图              | 无向图              |
   | ------ | ------------------- | ------------------- |
   | 一对一 | a->b;               | a–b;                |
   | 一对多 | a->{b;c;d};         | a–{b;c;d};          |
   | 多对一 | {b;c;d}->a;         | {b;c;d}–a;          |
   | 多对多 | {m,n,p,q}->{b;c;d}; | {m,n,p,q}->{b;c;d}; |



# 常用语句

```
//虚线
a -> b [style=dooted];
```

# 设置节点类型

```
a [shape="形状",sides="边数",peripheries="层数",color="",style="filled"]
```

shape:建议写polygon（多边形）

sides：指定多边形的边数



若要设置节点的统一格式

```
Node [];
```

# 标签

设置节点内的文本内容（不设置则默认为节点名称）

```
a [label="ocean"];
```

# 二分查找树的写法

```
Node [shape=record];
node01 [label=label="<f0>|<f1> G|<f2>"]
```

example

```
digraph{
	Node [shape=record];
	a [label="<f0>|<f1> A|<f2>"];
	b [label="<f0>|<f1> B|<f2>"];
	c [label="<f0>|<f1> C|<f2>"];
	a:f0 -> b;
	a:f2 -> c;
}
```

