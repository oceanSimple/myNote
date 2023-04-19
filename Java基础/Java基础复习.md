# 数据类型
1. Long型数据，结尾要加上L,16进制数以0x开头，二进制数以0b开头
    ```
    Long a = 1256362L;
    Long b = 0x1002L;
    ```
2. 对于浮点数，计算2.0 - 1.1 ，结果为0.8999999
3. 在Java中，整型值与boolean值不能相互转化
4. 常量声明：final int MAX_COLUMN = 10;
5. 整数被0除会报错，浮点数被0除会得到一个无穷大或NaN结果
6. -1 % 12 = -1
   Math.floorMod(-1,12) = 11
7. switch的使用方法之一
    ```
    String result = switch (2) {
      case 1 -> "one";
      case 2 -> "two";
      default -> "null";
    };
    System.out.println(result);
    ```
8. 