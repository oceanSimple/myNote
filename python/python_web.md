# 入门

## 一. bs4

### 1. 入门标准案例

```
from urllib.error import HTTPError
from urllib.request import urlopen
from bs4 import BeautifulSoup

# 标准格式！！！
def getTitle(url):
    # 判断是否成功发送请求
    try:
        html = urlopen(url)
    except HTTPError as e:
        return None
    # 判断获取的请求是否为空
    try:
        bsObj = BeautifulSoup(html.read(), "html.parser")
        # 只会获取第一个h1标签
        title = bsObj.body.h1
    except AttributeError as e:
        return None
    return title


title = getTitle("http://www.pythonscraping.com/pages/page1.html")
if title == None:
    print("Title could not be found")
else:
    print(title)
```



### 2. findAll与find函数

> find相当于limit=1

```
findAll(tag, attributes, recursive, string, limit, keywords)
find(tag, attributes, recursive, text, keywords)
```



tag与attributes的使用

```
查找所有span标签，且属性class为green
nameList1 = bsObj.findAll("span", {"class": "green"})
for name in nameList1:
    print(name.get_text())

查找所有span标签，且属性class为green或red
nameList2 = bsObj.findAll("span", {"class": {"green", "red"}})
for name in nameList2:
    print(name.get_text())
```



string的使用

```
nameList3 = bsObj.findAll(string="the prince")
print(len(nameList3))
```



limit:限制查找数据的数量

```
limit限制查找数据的数量
nameList4 = bsObj.findAll(string="the prince", limit=4)
print(len(nameList4))   # 4
```



keyword参数：选择那些具有指定属性的标签

```
# 查找含有属性id=“text”的标签
allText = bsObj.findAll(id="text")
print(allText[0].get_text())
```



### 3. 导航树

#### 3.1 处理子标签和其他后代标签

> 用children只会显示子标签

```
for child in bsObj.find("table", {"id": "giftList"}).children:
    print(child)
```

> 用descendants会显示后代所有标签
>
> 举个例子，若子标签含有<table><img></img></table>
>
> 会产生两个对象：1. <table><img></img></table>；2. <img></img>

```
for child in bsObj.find("table", {"id": "giftList"}).descendants:
    print(child)
```



#### 3.2 处理兄弟标签

```
# 处理兄弟标签
# 改代码会打印产品列表中所有行的产品,第一行标题除外
# 原因：兄弟标签不包含自身
for sibling in bsObj.find("table", {"id": "giftList"}).tr.next_siblings:
    print(sibling)
```



#### 3.3 处理父标签

parent和parents



### 4. 正则表达式相结合

```
from urllib.request import urlopen
from bs4 import BeautifulSoup
import re

# 与正则表达式结合

html = urlopen("https://www.pythonscraping.com/pages/page3.html")
bsObj = BeautifulSoup(html, "html.parser")

# 使用re库
# 匹配符合"../img/gifts/img().jpg"的src属性
# （）内填任何符号
# 注意"/","."要记得转义符号！！！
images = bsObj.findAll("img", {"src": re.compile("\.\.\/img\/gifts/img.*\.jpg")})
for image in images:
    print(image)
```



### 5. 获取属性

```
images = bsObj.findAll("img", {"src": re.compile("\.\.\/img\/gifts/img.*\.jpg")})
for image in images:
	# 获取标签所有属性，
    print(image.attrs)
```



```
for image in images:
	# 获取特定标签
    print(image.attrs["src"])
```

