# 创建工程

1.  
  
   ```
   yarn create vite
   npm init vite-app vue3_test_vite
   ```
   

2. 进入项目

   ```
   npm i
   ```

3. 允许

   ```
   npm run dev
   ```






# 基础用法

1. ref：若要修改ref的值，需要：name.value = "sea"

   在模板中使用，不需要.value(模板自动解析)

   只能装配基本数据类型

   - 语法: ```const xxx = ref(initValue)``` 
     - 创建一个包含响应式数据的<strong style="color:#DD5145">引用对象（reference对象，简称ref对象）</strong>。
     - JS中操作数据： ```xxx.value```
     - 模板中读取数据: 不需要.value，直接：```<div>{{xxx}}</div>```
   - 备注：
     - 接收的数据可以是：基本类型、也可以是对象类型。
     - 基本类型的数据：响应式依然是靠``Object.defineProperty()``的```get```与```set```完成的。
     - 对象类型的数据：内部 <i style="color:gray;font-weight:bold">“ 求助 ”</i> 了Vue3.0中的一个新函数—— ```reactive```函数。

2. reactive，被proxy封装

   不需要value调用属性

   - 语法：```const 代理对象= reactive(源对象)```接收一个对象（或数组），返回一个<strong style="color:#DD5145">代理对象（Proxy的实例对象，简称proxy对象）</strong>
   - reactive定义的响应式数据是“深层次的”。
   - 内部基于 ES6 的 Proxy 实现，通过代理对象操作源对象内部数据进行操作。

   

3. 注意：return之后才能被vue挂载

  
   ```
   export default {
     name: 'Test',
     setup() {
       let name = ref("ocean");
       let age = ref(20);
       let job = reactive({
         type: '全栈工程师',
         salary: "10k"
     })
   ```
<script>
import { reactive, ref } from 'vue';

export default {
  name: 'Test',
  setup() {
    let name = ref("ocean");
    let age = ref(20);
    let job = reactive({
      type: '全栈工程师',
      salary: "10k"
    })

    function test() {
      name.value = "sea";
      age.value = 21;
      job.type = "Java工程师";
      job.salary = "30k";
    };
    
    return {
      name,
      age,
      job,
      test,
    }
  }
}
</script>
```



# setup

- setup执行的时机
  - 在beforeCreate之前执行一次，this是undefined。
- setup的参数
  - props：值为对象，包含：组件外部传递过来，且组件内部声明接收了的属性。
  - context：上下文对象
    - attrs: 值为对象，包含：组件外部传递过来，但没有在props配置中声明的属性, 相当于 ```this.$attrs```。
    - slots: 收到的插槽内容, 相当于 ```this.$slots```。
    - emit: 分发自定义事件的函数, 相当于 ```this.$emit```。



1. props参数使用-父传子

   父组件向子组件传了两个参数

```
   <Demo msg="你好啊" nickName="ocean" />
   ```

   子组件需要接收参数

   ```
   props: ["msg", "nickName"],
   ```

   同时setup的第一个参数也可以获取

   ```
   setup(props, context){
   	let data = reactive({
   		msg: props.msg,
   		nickName: props.nickName,
   	})
   }
   ```

2. context.attrs

   用来接收props没接收的参数

3. context.emit-自定义事件，子传父

   > 自定义事件

   父组件

   ```
   <Demo msg="你好啊" nickName="ocean" @hello="showHelloMsg" />

   function showHelloMsg(value1, value2) {
   	alert(`你触发了hello事件,收到的参数是:${value1},${value2}`)
   }
   ```

   子组件

   ```
   <button @click="test">测试demo组件的hello事件</button>

   emits: ["hello"],

   function test() {
   	context.emit("hello", 666, 111)
   }
   ```



4. slots

   父组件

   ```
   <Demo msg="你好啊" nickName="ocean" @hello="showHelloMsg">
       <template v-slot:slot1>
       	<span>东北大学</span>
       </template>
       <template v-slot:slot2>
       	<span>你好啊</span>
       </template>
   </Demo>
   ```

   子组件

   ```
   <slot name="slot1"></slot>
   <slot name="slot2"></slot>
   ```





# 生命周期

- Vue3.0中可以继续使用Vue2.x中的生命周期钩子，但有有两个被更名：
  - ```beforeDestroy```改名为 ```beforeUnmount```
  - ```destroyed```改名为 ```unmounted```
- Vue3.0也提供了 Composition API 形式的生命周期钩子，与Vue2.x中钩子对应关系如下：
  - `beforeCreate`===>`setup()`
  - `created`=======>`setup()`
  - `beforeMount` ===>`onBeforeMount`
  - `mounted`=======>`onMounted`
  - `beforeUpdate`===>`onBeforeUpdate`
  - `updated` =======>`onUpdated`
  - `beforeUnmount` ==>`onBeforeUnmount`
  - `unmounted` =====>`onUnmounted`





# 计算属性

   ```
// 简写计算属性
// data.info = computed(() => {
//   return "name: " + data.name + "   " + "age: " + data.age;
// })

// 完整模式
// data.info = computed({
//   get() {
//     return "name: " + data.name + "   " + "age: " + data.age;
//   },
//   set(value) {
//     console.log("正在修改");
//   },
// })

```





# 监视属性

1. watch

```
   // 监视ref
   watch(info, (newValue, oldValue) => {
       console.log(newValue, oldValue);
   })

   // 监视reactive
   // 问题：oldValue始终和newValue相同，这是vue框架的缺陷
   // 解决方案：将需要oldValue的数据，用ref声明
   // 注意：强制开启了深度监视
   watch(data, (newValue, oldValue) => {
       console.log(newValue, oldValue);
   }, {immediate: true})
   ```

2. 监听reactive对象的一个属性，和ref类似，没有上述问题

   ```
   watch(() => data.name, (newValue, oldValue) => {
   	console.log(newValue, oldValue);
   })
   ```

3. 监听多个

   ```
   watch([info, () => data.name], (newValue, oldValue) => {
   	console.log(newValue, oldValue);
   })
   ```



> 总结
>
> 1. 如果直接监视reactive，强制开启了深度监视，且oldValue无法获取
> 2. 如果监视reactive的属性，使用函数回调
> 3. 如果监视的是reactive的一个属性，deep配置需要自行配置！



4. watchEffect

   ```
// 用到啥监视啥
// 自动调用了immediate
watchEffect(() => {
    const x1 = info.value;
    const x2 = data.name;
    console.log("改变了！！！");
})
```



# hooks

1. ```
   <template>
     <h2>当前点击时鼠标的坐标为: x: {{ point.x }}, y: {{ point.y }}</h2>
   </template>
   
   <script>
   import usePoint from './hooks/usePoint';
   
   export default {
     name: 'App',
     setup() {
       let point = usePoint();
   
       return {
         point,
       };
     }
   }
   </script>
```

2. ```
   import { reactive, onMounted, onbeforeunload } from 'vue';
   export default function usePoint() {
       let point = reactive({
           x: 0,
           y: 0,
         });
     
         // 保存点击坐标
         function savePoint(e) {
           point.x = e.pageX;
           point.y = e.pageY;
         }
     
         // 监听点击事件
         onMounted(() => {
           window.addEventListener('click', savePoint);
         });
     
         // 移除监听
         onbeforeunload = () => {
           window.removeEventListener('click', savePoint);
         };
   
       return point;
   }
   ```

   



# 常用API

## 1. toRef

反例：

```
setup() {
    let data = reactive({
        name: "ocean",
        age: 20,
    })

	return {
    	name: data.name,
    	age: data.age,
    }
}
```

> name,age是基本的数据类型，并不是代理类型，不是响应式！！！



正确使用：

```
setup() {
    let data = reactive({
       name: "ocean",
       age: 20,
    })

	return {
      name: toRef(data, "name"),
      age: toRef(data, "age"),
    }
}
```

> 修改name，即修改data内的name



​		若用ref

```
name: ref(data.name)
```

​		表示重现开辟空间存储name，与data.name无关





## 2. toRefs

将数据解构一层

```
return {
	...toRefs(data),
}
```



## 3. shallowReactive

> 只处理第一层响应式数据



## 4. shallowRef

> 1. 传入基本类型,与ref一样
> 2. 传入对象,不做响应式





## 5. readOnly

> 让数据变成深只读, 不可更改

```
data = readOnly(data)
```



## 6. shallowReadOnly

> 第一层数据只读, 深度数据可改



## 7. toRaw

> 将响应式数据变回普通数据

使用场景

ajax,axios向后端提交数据



## 8. markRow

> 1. 向响应式数据添加属性,属性也是响应式
> 2. 若不想使其响应式,可用markRow

```
let car = {...};
data.car = markRaw(car);
```





# customRef

> 自定义ref

```
function myRef(value) {
	let timer;
    return customRef((track, trigger) => {
        return {
            get() {
                track();  // 收集依赖
                return value;
            },
            set(newValue) {
                console.log('set');
                clearTimeout(timer);
                value = newValue;
                timer = setTimeout(() => {
                	trigger();  // 触发更新
                }, 1000);
            }
        }
    });
}

let a = myRef("");
```

1. track和trigger必不可少
2. 可以设置延时器
3. 实例中clearTimeout等语句，是防抖操作，让用户一次性输入完后再进行模板解析





# provide和inject

> 实现祖孙传值

祖

```
provide("data", data)
```



子组件

```
let data = inject("data")
```





# 响应式数据的判断

- isRef: 检查一个值是否为一个 ref 对象
- isReactive: 检查一个对象是否是由 `reactive` 创建的响应式代理
- isReadonly: 检查一个对象是否是由 `readonly` 创建的只读代理
- isProxy: 检查一个对象是否是由 `reactive` 或者 `readonly` 方法创建的代理





# 新的组件

1. Teleport

   > 什么是Teleport？—— `Teleport` 是一种能够将我们的<strong style="color:#DD5145">组件html结构</strong>移动到指定位置的技术。

```
<teleport to="移动位置">
	<div v-if="isShow" class="mask">
		<div class="dialog">
			<h3>我是一个弹窗</h3>
			<button @click="isShow = false">关闭弹窗</button>
		</div>
	</div>
</teleport>
```



2. Suspense

- 等待异步组件时渲染一些额外内容，让应用有更好的用户体验

- 使用步骤：

  - 异步引入组件

    ```js
    import {defineAsyncComponent} from 'vue'
    const Child = defineAsyncComponent(()=>import('./components/Child.vue'))
    ```

  - 使用```Suspense```包裹组件，并配置好```default``` 与 ```fallback```

    ```vue
    <template>
    	<div class="app">
    		<h3>我是App组件</h3>
    		<Suspense>
    			<template v-slot:default>
    				<Child/>
    			</template>
    			<template v-slot:fallback>
    				<h3>加载中.....</h3>
    			</template>
    		</Suspense>
    	</div>
    </template>
    ```
