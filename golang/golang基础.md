# 基础

1. 包的导入导出
   > 在Go中，程序是通过将包链接在一起来构建的，也可以理解为最基本的调用单位是包，而不是go文件。包其实就是一个    文件夹，包内共享所有源文件的变量，常量，函数以及其他类型。包的命名风格建议都是小写字母，并且要尽量简短。
   
   导入：import
   
   导出：首字母大写即可

2. 只允许i++，不存在++i
3. 允许使用下划线对数字划分
   ```
   100_000
   ```

<br/>

<br/>

<br/>

# 数据类型

1. bool
2. 整型
3. 浮点型
4. 复数
5. 字符
6. 派生类型
   ```
   类型	例子
   数组	[5]int，长度为5的整型数组
   切片	[]float64，64位浮点数切片
   映射表	map[string]int，键为字符串类型，值为整型的映射表
   结构体	type Gopher struct{}，Gopher结构体
   指针	*int，整型指针
   函数	func()，一个没有参数，没有返回值的函数类型
   接口	type Gopher interface{}，Gopher接口
   通道	chan int，整型通道
   ```
7. 零值
   ```
   类型	零值
   数字类型	0
   布尔类型	false
   字符串类型	""
   数组	固定长度的对应类型的零值集合
   结构体	内部字段都是零值的结构体
   切片，映射表，函数，接口，通道，指针	nil
   ```

8. nil

<br/>

<br/>

# 常量

1. 使用const
2. ito
   > iota是一个内置的常量标识符，通常用于表示一个常量声明中的无类型整数序数，一般都是在括号中使用。
   ```golang
   const (
      Num = iota // 0
      Num1 // 1
      Num2 // 2
      Num3 // 3
      Num4 // 4
   )
   
   const (
      Num = iota*2 // 0
      Num1 // 2
      Num2 // 4
      Num3 // 6
      Num4 // 8
   )
   ```

<br/>

<br/>

# 输入输出

1. 输出
   > 在%与格式化动词之间加上一个空格便可以达到分隔符的效果
   ```
   func main() {
   	str := "abcdefg"
   	fmt.Printf("%x\n", str)
   	fmt.Printf("% x\n", str)
   }
   // 61626364656667
   // 61 62 63 64 65 66 67
   ```
   ```
   fmt.Printf("%q, %d, %f, %e\n", str, num, flt, flt)
   // Output: "ocean", 100, 31.400000, 3.140000e+01
   ```

2. 输入
   - fmt.Scan
     ```
     func main() {
        var s, s2 string
        fmt.Scan(&s, &s2)
        fmt.Println(s, s2)
     }
     
     //a
     //b
     //a b
     ```
   - fmt.Scanln
     ```
     func main() {
     	var s, s2 string
     	fmt.Scanln(&s, &s2)
     	fmt.Println(s, s2)
     }
     
     //a b
     //a b
     ```
   - fmt.Scanf
     ```
     func main() {
        var s, s2, s3 string
        scanf, err := fmt.Scanf("%s %s \n %s", &s, &s2, &s3)
        if err != nil {
           fmt.Println(scanf, err)
        }
        fmt.Println(s)
        fmt.Println(s2)
        fmt.Println(s3)
     }
     
     //aa bb
     //cc
     //aa
     //bb
     //cc
     ```

3. 缓冲
   > 当对性能有要求时可以使用bufio包进行读取
   ```
   func main() {
      // 读
      scanner := bufio.NewScanner(os.Stdin)
      scanner.Scan()
      fmt.Println(scanner.Text())
   }
   
   //abcedfg
   //abcedfg
   ```
   ```
   func main() {
      // 写
      writer := bufio.NewWriter(os.Stdout)
      writer.WriteString("hello world!\n")
      writer.Flush()
      fmt.Println(writer.Buffered())
   }
   
   //hello world!
   //0
   ```

<br/>

<br/>

# 条件控制

1. if的条件不用小括号
2. if语句也可以包含一些简单的语句
   ```
   x := 0
   if x++; x < 2 {
   	fmt.Println(x)
   }
   
   // 1
   ```

3. switch
   > 通过fallthrough关键字来继续执行相邻的下一个分支
   ```
   func main() {
      str := "a"
      switch str {
      case "a":
         str += "a"
         str += "c"
      case "b":
         str += "bb"
         str += "aaaa"
      default: // 当所有case都不匹配后，就会执行default分支
         str += "CCCC"
      }
      fmt.Println(str)
   }
   
   func main() {
      num := 2
      switch {
      case num >= 0 && num <= 1:
         num++
      case num > 1:
         num--
         fallthrough // 执行完该分支后，会继续执行下一个分支
      case num < 0:
         num += num
      }
      fmt.Println(num)
   }
   ```

4. 标签
   ```
   func main() {
      a := 1
      if a == 1 {
         goto A
      } else {
         fmt.Println("b")
      }
   A:
      fmt.Println("a")
   }
   ```
5. for循环
   ```
   for init statement; expression; post statement {
   	execute statement
   }
   
   // 等于while
   for expression {
   	execute statement
   }
   ```

6. for range
   ```
   func main() {
   	sequence := "hello world"
   	for index, value := range sequence {
   		fmt.Println(index, value)
   	}
   }
   ```

<br/>

# 数组、切片

## 数组

1. 初始化
   ```
   nums := [5]int{1, 2, 3}
   nums := new([5]int)
   ```

2. 切割
   > 区间为左闭右开
   ```
   nums := [5]int{1, 2, 3, 4, 5}
   for k, v := range nums[2:] {
   	println(k, v)
   }
   
   nums := [5]int{1, 2, 3, 4, 5}
   nums[1:] // 子数组范围[1,5) ->1 2 3 4
   nums[:5] // 子数组范围[0,5) -> 0 1 2 3 4
   nums[2:3] // 子数组范围[2,3) -> 2
   nums[1:3] // 子数组范围[1,3) -> 1 2
   ```

<br/>

<br/>

## 切片

1. 初始化
   ```
   var nums []int // 值
   nums := []int{1, 2, 3} // 值
   nums := make([]int, 0, 0) // 值
   nums := new([]int) // 指针
   ```

2. 插入-append
   ```
   nums := make([]int, 0, 0)
   nums = append(nums, 1, 2, 3, 4, 5, 6, 7)
   fmt.Println(len(nums), cap(nums)) // 7 8 可以看到长度与容量并不一致。
   ```
   - 从头部插入元素
     ```
     nums := []int{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
     nums = append([]int{-1, 0}, nums...)
     fmt.Println(nums) // [-1 0 1 2 3 4 5 6 7 8 9 10]
     ```
   - 从中间下标i插入元素（数组的拼接）
     ```
     nums = append(nums[:i+1], append([]int{999, 999}, nums[i+1:]...)...)
     fmt.Println(nums) // i=3，[1 2 3 4 999 999 5 6 7 8 9 10]
     ```
   - 从尾部插入元素，就是append最原始的用法
     ```
     nums = append(nums, 99, 100)
     fmt.Println(nums) // [1 2 3 4 5 6 7 8 9 10 99 100]
     ```
3. 删除
   ```
   nums := []int{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
   ```
   - 从头部删除n个元素
     ```
     nums = nums[n:]
     fmt.Println(nums) //n=3 [4 5 6 7 8 9 10]
     ```
   - 从尾部删除n个元素
     ```
     nums = nums[:len(nums)-n]
     fmt.Println(nums) //n=3 [1 2 3 4 5 6 7]
     ```
   - 从中间指定下标i位置开始删除n个元素
     ```
     nums = append(nums[:i], nums[i+n:]...)
     fmt.Println(nums)// i=2，n=3，[1 2 6 7 8 9 10]
     ```
   - 删除所有元素
     ```
     nums = nums[:0]
     fmt.Println(nums) // []
     ```

4. 拷贝
   > 切片在拷贝时需要确保目标切片有足够的长度
   ```
   func main() {
   	dest := make([]int, 0)
   	src := []int{1, 2, 3, 4, 5, 6, 7, 8, 9}
   	fmt.Println(src, dest)
   	fmt.Println(copy(dest, src))
   	fmt.Println(src, dest)
   }
   
   [1 2 3 4 5 6 7 8 9] []
   0                     
   [1 2 3 4 5 6 7 8 9] []
   
   将dest的长度改为10
   [1 2 3 4 5 6 7 8 9] [0 0 0 0 0 0 0 0 0 0]
   9                                        
   [1 2 3 4 5 6 7 8 9] [1 2 3 4 5 6 7 8 9 0]
   ```