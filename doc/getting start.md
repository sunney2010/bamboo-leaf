# 概述
> Bamboo-leaf有两种接入方式分别为:Remote,Local.两种方式的接方式是的配置是不一样的。

# 引入JAR
基于spring boot为例

```
<dependency>
     <groupId>com.bamboo.leaf</groupId>
     <artifactId>bamboo-leaf-autoconfigure</artifactId>
     <version>1.0.0</version>
</dependency>
```
该JAR自动引入：bamboo-leaf-core,bamboo-leaf-client两个JAR.

# 应用配置
1. Remote模式

```
bamboo:
  leaf:
    client:
      leafToken: 0504c00e-7971-41d1-87e8-0952399c5816
      leafServer: 127.0.0.1
      leafPort: 8080
      mode: Remote
```
配置说明：
leafToken:   服务端分配的密钥，用于授权访问API。
leafServer:  服务端HTTP接口IP.
leafPort:    服务端HTTP接口端口.
mode:        远程模式

2. Local模式

```
bamboo:
  leaf:
    client:
      mode: Local
```
配置说明：
mode:        本地模式

更多参数配置请见：[bamboo-leaf详细参数配置](https://gitee.com/sunney/bamboo-leaf/wikis/Bamboo-leaf%E8%AF%A6%E7%BB%86%E5%8F%82%E6%95%B0%E9%85%8D%E7%BD%AE?sort_id=3408379)

# 序列应用

1. segment算法应用

```
 @Resource
 BambooLeafSegmentClient bambooLeafSegmentClient;

 public void segment(String namespace) {
    //原始算法
    long leafVal = bambooLeafSegmentClient.segmentId(namespace);
    //日期算法
    long dateVal = bambooLeafSegmentClient.dateSegmentId(namespace);
    //时间算法
    long timeVal = bambooLeafSegmentClient.timeSegmentId(namespace);
    //日期前缀算法
    String datePixedVal = bambooLeafSegmentClient.dateSegmentId(namespace, "O");
    //时间前缀算法
    String timePixedVal = bambooLeafSegmentClient.timeSegmentId(namespace, "P");
}
```
2. snowflake算法应用

```
 @Resource
 BambooLeafSnowflakeClient bambooLeafSnowflakeClient;

public void snowflake(String namespace) {
    //雪花算法
    long snowflakeId = bambooLeafSnowflakeClient.snowflakeId(namespace);
    //雪花算法固定16位
    String snowflakeId16 = bambooLeafSnowflakeClient.snowflakeId16(namespace);
    //雪花算法固定20位
    String snowflakeId20 = bambooLeafSnowflakeClient.snowflakeId20(namespace);

}
```
