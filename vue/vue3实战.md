# 路由

## 1. 基本配置

1. 安装路由

   ```
   npm i vue-router
   ```

2. /router/index.ts

   > 示例中的注意点：
   >
   > 1. 主页面“/”，注意设置重定向
   > 2. 子组件的path不能加上“/”！！！，在使用含有子组件的父组件时，要配置重定向，确定默认使用哪个子组件
   > 3. 命名视图后续介绍！！！

   ```
   import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
   
   const routes: RouteRecordRaw[] = [
       {
           path: '/',
           name: 'home',
           redirect() {
               return { name: 'login' }
           },
       },
       {
           path: '/login',
           name: 'login',
           component: () => import('../components/login/LoginMain.vue'),
           children: [
               {
                   path: 'header',
                   name: 'loginHeader',
                   component: () => import('../components/login/LoginHead.vue')
               },
               {
                   path: 'footer',
                   name: 'loginFooter',
                   component: () => import('../components/login/LoginFoot.vue')
               }
           ],
           redirect() {
               //return { name: 'loginHeader' }
               return { path: '/login/header' }
           }
       },
       {
           path: '/register',
           name: 'register',
           component: () => import('../components/register/RegisterMain.vue')
       }
   ]
   
   const router = createRouter({
       history: createWebHistory(),
       routes
   })
   
   export default router
   ```

3. main.ts

   ```
   import { createApp } from 'vue'
   import App from './App.vue'
   import router from './router'
   
   createApp(App).use(router).mount('#app')
   ```

4. 使用

   - 模板中

     ```
     <RouterView></RouterView>
     ```

   - 脚本中

     > 切换路由

     ```
     <script setup lang="ts">
     import router from './router';
     function goto() {
         router.push({
             path: '/register',
             query: {
                 id: 1
             }
         });
     }
     </script>
     ```



## 2. 参数传递

1. query传递

   > 该方法的原理是，将参数放在了url上面，所以刷新后仍存在（在vue2中的params传递，刷新后值会消失！）
   >
   > 不要用name跳转！！！

   - 传

     ```
     <script setup lang="ts">
     import router from './router';
     function goto() {
         router.push({
             path: '/register',
             query: {
                 id: 1
             }
         });
     }
     </script>
     ```

   - 收

     ```
     <script setup lang="ts">
     import { useRoute } from 'vue-router';
     import { ref } from 'vue';
     
     let id = ref(useRoute().query.id);
     console.log(id.value);
     </script>
     ```

     > 注意：是useRoute！没有r！！！

2. params（废弃）

   无法使用！

3. state

   ```
   router.push({
       name: 'register',
       state: {
           id: 1
       }
   });
   ```

   ```
   import { ref } from 'vue';
   let id = ref(history.state.id);
   ```

   





## 3. 命名视图

1. 配置ts

   > 要把不同渲染当成一个状态，配成子路由形式！！！

   ```
   import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
   
   const routes: RouteRecordRaw[] = [
       {
           path: '/',
           name: 'home',
           redirect() {
               return { path: '/login' }
           },
       },
       {
           path: '/login',
           name: 'login',
           component: () => import('../components/login/LoginMain.vue'),
           children: [
               {
                   path: 'test1',
                   components: {
                       default: () => import('../components/login/LoginMain.vue'),
                       a: () => import('../components/login/LoginHead.vue'),
                       b: () => import('../components/login/LoginFoot.vue'),
                   }
               }
           ],
           redirect() {
               return { path: '/login/test1' }
           }
       },
   ]
   
   const router = createRouter({
       history: createWebHistory(),
       routes
   })
   
   export default router
   ```

2. 模板

   ```
   <!-- 登录 主界面 -->
   <template>
       <RouterView name="a"></RouterView>
       <h1>login main</h1>
       <RouterView name="b"></RouterView>
   </template>
   
   <script lang="ts">
   export default {
   
   }
   </script>
   
   <style  scoped>
   
   </style>
   ```

   