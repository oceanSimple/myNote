# 入门

下载node.js



# express的使用

## 1. 在终端运行代码

```
npm init --yes
npm install express
```

## 2. 编辑文件

filename:	express.js

```
//1.引入express
const express=require('express');
//2.创建用户
const app=express();
//3.创建路由规则
app.get("/server",(request,response)=>{
    //设置响应头
    response.setHeader('Access-Control-Allow-Origin','*');
    //设置响应体
    response.send('hello get express');
});
app.post("/server",(request,response)=>{
    //设置响应头
    response.setHeader('Access-Control-Allow-Origin','*');
    //设置响应体
    response.send('hello post express');
});
//4.监听端口启动服务
app.listen(8000,()=>{
    console.log('服务启动，8000端口监听中。。。')
});
```

## 3. 在终端运行

```
node express.js
```

## 4. get与post页面

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>AJAX 请求</title>
    <style>
      #result{
        width: 200px;
        height: 100px;
        border: solid 1px red;
      }
    </style>
</head>
<body>
  <button>点击发送请求</button>
  <div id="result"></div>

    <script>
        let btn = document.getElementsByTagName("button")[0];
        let result = document.getElementById('result');

        btn.onclick=function (){
            //创建对线
            let xhr = new XMLHttpRequest();
            //初始化，设置请求方法和url
            xhr.open('GET','http://127.0.0.1:8000/server')
            //发送
            xhr.send();
            //事件绑定，处理服务端返回的结果
            xhr.onreadystatechange=function (){
                //判断(服务端返回了所有的结果)
                if (xhr.readyState===4){
                    //判断相应状态码
                    if (xhr.status>=200&&xhr.status<=300){
                        //处理结果
                        console.log(xhr.status);//状态码
                        console.log(xhr.statusText);//状态字符串
                        console.log(xhr.getAllResponseHeaders());//响应头
                        console.log(xhr.response);//响应体

                        result.innerHTML=xhr.response;
                    }
                }
            }
        }
    </script>
</body>
</html>
```

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>post</title>
    <style>
      #result{
        width: 200px;
        height: 100px;
        border: solid 1px red;
      }
    </style>
</head>
<body>
  <div id="result">

  </div>

  <script>
    let result = document.getElementById('result');
    result.addEventListener("mouseover",function (){
      let xhr = new XMLHttpRequest();
      xhr.open('POST',"http://127.0.0.1:8000/server");
      xhr.send();
      xhr.onreadystatechange=function (){
        if (xhr.readyState===4){
          //判断相应状态码
          if (xhr.status>=200&&xhr.status<=300){
            //处理结果
            console.log(xhr.status);//状态码
            console.log(xhr.statusText);//状态字符串
            console.log(xhr.getAllResponseHeaders());//响应头
            console.log(xhr.response);//响应体

            result.innerHTML=xhr.response;
          }
        }
      }
    })

  </script>
</body>
</html>
```

