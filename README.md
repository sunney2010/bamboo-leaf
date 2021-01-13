# bamboo-leaf简介
> Bamboo-leaf是用Java开发的一款分布式id生成系统，基于数据库号段算法、雪花(snowflake)算法实现，基于数据库号段算法是参考了滴滴出行的tinyid及阿里巴巴的TDDL-Sequence的思路，取了两都的优点，同时加入了新的实现方式，让接入更方便简洁。雪花(snowflake)算法,参考了百度(uid-generator)实现方式，重定义了snowflake各段的长度，解决了workerid的节点限制问题及时间回拨序列重复的问题，提升了每毫秒产生的序列个数。
同时提供了Bamboo-leaf-client(sdk)使id生成本地化，获得了更好的性能与可用性。均通过Bamboo-leaf-client方式接入，每天生成百亿级别的id。

[更详细文档请看](https://gitee.com/sunney/bamboo-leaf/wikis/Home?sort_id=3295968)

# bamboo-leaf架构图
![架构图](./doc/image/bamboo-leaf.jpg)

# bamboo-leaf代码结构
bamboo-leaf
- ---bamboo-leaf-server
- ---bamboo-leaf-client
- ---bamboo-leaf-core
- ---bamboo-leaf-autoconfigure
- ---bamboo-leaf-demo

# bamboo-leaf接口列表
## 1、snowflake算法接口列表
   接口类：BambooLeafSnowflakeClient
| 序号 | 接口 | 名称 | 功能说明 |
|----|----|----|------|
|   1|public long snowflakeId(String namespace);| 雪花算法   |   返回Long类型   |
|   2|public String snowflakeId16(String namespace); |  雪花算法+随机数  | 返回16位字段串:<br>13位(原生雪花算法转36进制), <br>3位(随机数转36进制)   |
|   3|public String snowflakeId20(String namespace); |  雪花算法+namespace+随机数  |  返回20位字段串:<br>13位(原生雪花算法转36进制), <br>3位(namespace转36进制)， <br>3位(随机数转36进制)    |

## 2、segment算法接口列表
接口类：BambooLeafSnowflakeClient
| 序号 | 接口 | 名称 | 功能说明 |
|----|----|----|------|
|   1|public Long segmentId(String namespace);| 返回1~Long.MAX_VALUE   |   返回Long类型   |
|   2|public Long dateSegmentId(String namespace); |  返回19位日期long的序列<br>格式：8位yyyyMMdd+11位序列  | 例如:202101010000000001<br>序列大于99999999999后会重置。<br>该算法每天最大99999999999序号 |
|   3|public String dateSegmentId(String namespace, String prefix);| 返回前缀+日期String的序列<br>格式：前缀+8位yyyyMMdd+11位序列| 例如:P202101010000000001<br>序列大于99999999999后会重置。<br>该算法每天最大99999999999序号  |
|   4|public Long timeSegmentId(String namespace); |  返回19位时间long的序列<br>格式：12位yyMMddHHmmss+7位序列  | 例如:2101010101010000001<br>序列大于9999999后会重置。<br>该算法每秒最大9999999序号   |
|   5|public String timeSegmentId(String namespace, String prefix);|  返回前缀+时间String的序列<br>格式：前缀+12位yyMMddHHmmss+7位序列   | 例如:P2101010101010000001<br>序列大于9999999后会重置。<br>该算法每秒最大9999999序号     |


# 性能与可用性
## 性能
http方式访问，性能取决于http server的能力，网络传输速度。
bamboo-leaf-client方式，id为本地生成，号段长度(step)越长，qps越大，如果将号段设置足够大，则qps可达1000w+。
## 可用性
依赖db，当db不可用时，因为server有缓存，所以还可以使用一段时间，如果配置了多个db，则只要有1个db存活，则服务可用。使用bamboo-leaf-client，只要server有一台存活，则理论上可用，server全挂，因为client有缓存，也可以继续使用一段时间。


# Bamboo的特性
1. 全局唯一的long型id。
2. 趋势递增的id（即不保证下一个id一定比上一个大）.
3. 非连续性。
4. 提供中心化server模式和去中心化模式方式接入。
5. 支持批量获取id。
6. 支持segment及snowflake两种基础算法扩展算法。
7. segment支持生成1,3,5,7,9...序列的id.

# 使用场景
## 适用场景：
1. 纯数字的序列（如：日志表ID编号).
2. 有业务属性的序列（如订单号:P2101010101010000001).
3. 完全无序的字符串序列（如二维码号：023eqt01pd6o001a04ui）.
4. 分库分表，多机房部署的，各表的ID序列。

## 不适用场景:
1. 对顺序有严格要求的场景。

 # 依赖
JDK1.8+,maven,mysql, java client目前仅依赖jdk.

 # 加入QQ交流群
 ![架构图](./doc/image/QQ-club.png)