# vue相关

## 1. 路由配置

1. 终端安装插件

   ```
   npm i vue-router@3
   ```

   

2. 创建文件component/router/index.js

   示例如下：

   ```
   import VueRouter from "vue-router";
   import LoginDemo from "@/components/LoginDemo.vue";
   import MainDemo from "@/components/MainDemo.vue";
   import RegisterDemo from "@/components/RegisterDemo.vue";
   import FoodPage from "@/components/mainPages/FoodPage.vue";
   
   const router = new VueRouter({
       routes: [
           {
               path: '/',
               redirect: '/login'
           },
           {
               path: '/login',
               component: LoginDemo,
               name: 'login'
           },
           {
               path: '/main',
               component: MainDemo,
               name: 'main',
               redirect: '/main/selectFood',
               children: [
                   {
                       path: 'selectFood',
                       name: 'selectFood',
                       component: FoodPage
                   }
               ]
           },
           {
               path: '/register',
               component: RegisterDemo,
               name: 'register'
           }
       ]
   })
   
   export default router
   ```

   

3. 在main.js下添加

   引入路由插件，并全局挂载router

   ```
   // 引入路由
   import VueRouter from "vue-router";
   import router from "@/components/router";
   Vue.use(VueRouter)
   
   new Vue({
     render: h => h(App),
     router,
   }).$mount('#app')
   ```

4. 在相应位置引入路由标识

   ```
   <router-view></router-view>
   ```

5. 携带参数进行路由跳转

   ```
   发送方：
   this.$router.push({
                     name: 'main',
                     params: {
                       nickname: res.data.data.nickname
                     }
   });
   ```

   > 注意：是route，不是router！！！

   ```
   接收方：
   nickname: this.$route.params.nickname
   ```



## 2. elementui插件

```
npm i element-ui -S 
```



```
// 引入elementUI
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)
```



## 3. axios插件

1. 终端安装插件

   ```
   npm i axios
   ```

2. 挂载全局axios

   ```
   // 挂载全局axios
   import axios from "axios";
   Vue.prototype.$axios = axios
   ```

3. 使用（通用方法）

   ```
   this.$axios({
           url: "http://localhost:8080/food/page",
           method: 'get',
           params: data
         }).then(
             res => {
               this.tableData = res.data.data.records;
             }
         )
   ```



## 4. vuex

1. 安装

   ```
   npm i vuex@3
   ```

2. 创建components/store/index/js

   ```
   import Vue from "vue";
   import Vuex from "vuex"
   Vue.use(Vuex)
   
   const state = {
       nickname: '',
   }
   
   const mutations = {
       saveNickname(state, nick) {
           state.nickname = nick;
       }
   }
   
   const actions = {}
   
   import loginModule from "@/components/store/modules/loginModule";
   export default new Vuex.Store({
       actions,
       mutations,
       state,
       modules: {
           loginModule
       }
   })
   
   ```

3. 使用

   ```
   this.$store.state.nickname
   this.$store.commit("mutation名字",参数)
   this.$store.dispatch('actions的名字', 参数)
   ```

   



## 其他

1. 配置端口号

   ```
   devServer: {
   	port: 8081
   }
   ```

   





# html+css相关

## 1. 背景图片

```
background: url("@/assets/background/loginBackground.png") no-repeat center;
height: 100%;
width: 100%;
background-size: cover;
position: fixed;
```



## 2. flex布局

### 2.1 基本使用

> 父盒子加上 display:flex，表示使用弹性布局

```
/* 表示该盒子自动占满剩余空间（往下展开） */
  flex: 1;
 
```



### 2.2 flex-direction

```
flex-direction
flex-direction属性决定主轴的方向，即项目（或者说是子盒子）的排列方向。

它可能有4个值。

row（默认值）：主轴为水平方向，起点在左端。
row-reverse：主轴为水平方向，起点在右端。
column：主轴为垂直方向，起点在上沿。
column-reverse：主轴为垂直方向，起点在下沿。
```



### 2.3 **justify-content**

该属性定义了项目在主轴上的对齐方式。

它可能取5个值，具体对齐方式与轴的方向有关。下面假设主轴为从左到右。

此时主轴（`flex-direction`）为默认的row，即水平方向，从左到右。

```
flex-start（默认值）：左对齐（即上面页面展示效果）
flex-end：右对齐
center： 居中
space-between：两端对齐，项目之间的间隔都相等。
space-around：每个项目两侧的间隔相等。所以，项目之间的间隔比项目与边框的间隔大一倍。
```



### 2.4 **align-items**

该属性定义项目在交叉轴上如何对齐。（如果主轴为水平，那么交叉轴就是垂直）

它可能取5个值。具体的对齐方式与交叉轴的方向有关，下面假设交叉轴从上到下。

```
flex-start：交叉轴的起点对齐。
flex-end：交叉轴的终点对齐。
center：交叉轴的中点对齐。
baseline: 项目的第一行文字的基线对齐。
stretch（默认值）：如果项目未设置高度或设为auto，将占满整个容器的高度。
```



### 2.5 **flex-wrap**

默认情况下，项目都排在一条线（又称"轴线"）上。`flex-wrap`属性定义，如果一条轴线排不下，如何换行。在宽度总和超出父盒子的宽度时，希望保持每个子盒子的准确宽度，那就只能分行排列

```
nowrap（默认值）：不换行。
wrap：换行。
wrap-reverse：换行，第一行在下方。 
```





## 3. 字体操作

### 3.1 字体样式

```
font-style
normal（正常显示）、italic（文本以斜体显示）、oblique（文本为倾斜，与italic相似，但支持较少）

font-weight
属性值；normal、blod（加粗）

font-size
1em=16px
```



### 3.2 字体格式

```
text-align
center、right、left、justify（两端对齐）
```



### 3.3 文字的装饰

```
text-decoration
none（常用在超链接中使文字不带下划线）、overline（上划线）、line-through（删除线）、underline（下划线)
```



### 3.4 文字间距

```
text-indent：
（用于指定文本第一行的缩进，属性值：num px；可正可负。可以理解为首行缩进或悬挂缩进）

letter-spacing：
文本中字符的间距，同样可正可负。

word-spacing：
文本中单词之间的间距，同样可正可负

line-height：
用于指定行之间的间距（行间距），属性值也是可正可负。

white-space：
指定元素内部空白的处理方式，属性值：nowrap；可以起到禁用元素内的文本换行。
```



# elementui组件总结

## 1. 分页插件

示例如下

```
<div class="block" style="margin-left: 25%">
    <el-pagination
        layout="prev, pager, next"
        :total=this.dataCount
        :page-size="3"
        @current-change="handleCurrentChange">
    </el-pagination>
</div>
```

1.   :total=this.dataCount

   表示查询的总数据数，推荐在mounted时调用axios，去数据库获取数据总量

2.    :page-size="3"

   表示每页显示的数据数量

3. @current-change="handleCurrentChange"

   表示点击页码后，做出的事件反应

   ```
   handleCurrentChange(val) {
         this.page = val;
         this.getPageData();
   }
   ```

   > val返回当前的页码数！

