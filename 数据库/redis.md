# 启动

```
redis-server		//启动redis
redis-cli			//进入控制界面
```



# 常用命令

1. 切换数据库

   > 0-15

   ```
   select index
   ```

2. 清空

   ```
   flushdb		//清空当前数据库
   flushall	//清空所有数据库
   ```

3. 设置/获取

   ```
   set name ocean
   get name
   keys *		//获取所有key
   ```

4. 是否存在

   ```
   exists name
   ```

5. 移除

   ```
   del name
   del k1 k2 k3 k4
   ```

6. 设置值的存在时间

   > -2代表已经过期，-1代表永久不过期，整数代表多少秒后过期

   ```
   expire name 10	//十秒后删除
   ttl name		//查看还有多久过期
   ```

7. 查看类型

   ```
   type name
   ```

8. keys

   > 搜索键，不推荐在生成环境下使用（效率低）

   ```
   keys *		// 查询全部
   keys k*		//	查询以k开头的key
   ```

9. 批量增加

   ```
   MSET k1 v1 k2 v2 k3 v3
   ```

10. key的层级

    ```
    set school:class:name dbdx:2103:ocean
    ```

    ```
    get school:class:name // dbdx:2103:ocean
    ```

    





# string命令

1. get，set

2. mget，mset

   ```
   MSET k1 v1 k2 v2 k3 v3
   mget k1 v1 k2 v2 k3 v3
   ```

3. 针对整数

   ```
   incr age	// +1
   incrby age 10	//	+10
   incrby age -10	// -10
   decr age	// -1
   decrby age 2	// -2
   ```

4. setnx

   > 若键存在，则添加失败

5. setex

   > 设置过期秒数

   ```
   setex name 10 ocean		// 10s过期
   ```

   



# hash命令

1. hset，hget

   ```
   hset school:class name ocean
   hget school:class name
   ```

   存储结构

   ```
   "school:class"
   ```

2. hmset，hmget

   ```
   hset school:class name sea age 20 sex male
   hmget school:class name age sex
   ```

3. hgetall

   ```
   hgetall school:class
   ```

   ```
   1) "name"
   2) "sea"
   3) "age"
   4) "20"
   5) "sex"
   6) "male"
   ```

4. hkeys,hvals

5. hincrby

6. hsetnx



# list命令

1. lpush，rpush

   > 在左侧、右侧插入

   ```
   lpush l 1 2 3
   ```

2. lpop，rpop

   > 移除并返回

   ```
   lpop l
   ```

3. lrange

   > 返回列表索引1-2的值

   ```
   lrange l 1 2
   ```





# set命令

> 只有key没有value

1. sadd

   > 向set添加一个或多个元素

   ```
   sadd s ocean sea
   ```

2. srem

   > 移除

   ```
   srem s sea
   ```

3. scard

   > 返回set元素个数

4. sismember

   > 判断一个元素是否存在于set

   ```
   sismember s ocean
   ```

5. smembers

   ```
   获取set中的所有元素
   ```

6. sinter

   > 求交集

   ```
   sinter s1 s2
   ```

7. sdiff

   > 求差集

8. sunion

   > 求并集



# sortedset命令

> Redis的SortedSet是一个可排序的set集合。SortedSet中的每一个元素都带有一个score属性，可以基于score属性对元素排序，底层的实现是一个跳表（SkipList）加 hash表。

> SortedSet具备下列特性：
>
> - 可排序
> - 元素不重复
> - 查询速度快

> 所有的排名默认都是升序，如果要降序则在命令的Z后面添加REV即可

1. zadd

   > 添加一个或多个元素到sorted set ，如果已经存在则更新其score值

   ```
   zadd z 1 ocean 2 sea 3 xi
   ```

2. zrem

   > 删除sorted set中的一个指定元素

   ```
   zrem z name
   ```

3. zscore

   >  获取sorted set中的指定元素的score值

   ```
   zscore z name
   ```

4. zrank

   > 获取sorted set 中的指定元素的排名，从0开始

   ```
   zrank z name
   ```

5. zcount

   > 统计score值在给定范围内的所有元素的个数

   ```
   zcount z 1 5
   ```

6. zincrby

   > 让sorted set中的指定元素自增，步长为指定的increment值

   ```
   zincrby z 10 xi		// 让xi的score + 10
   ```

7. zrange

   > 按照score排序后，获取指定排名范围内的元素
   >
   > ps：从0开始

   ```
   zrange z 0 2
   ```

8. zrangebyscore

   > 按照score排序后，获取指定score范围内的元素

   与上面对比，score范围！！！

9. zdiff，zinter，zunion

   > 求差集、交集、并集









# springboot整合

1. 导入依赖

   ```
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
       <version>3.0.6</version>
   </dependency>
   <!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
   <dependency>
       <groupId>redis.clients</groupId>
       <artifactId>jedis</artifactId>
       <version>5.0.0-beta1</version
   </dependency>
   ```

   