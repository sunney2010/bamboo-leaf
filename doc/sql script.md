# 概述
> Bamboo-leaf的部署方式有两种分别为：Local、Remote.两种部署方式数据的数量是不一样的。


# 数据库表介绍
| 序号 | 表名                   | 描述                  | 模式           |
|----|----------------------|---------------------|--------------|
| 1  | bamboo-leaf-segment  | segment算法数据段的记录。    | Local、Remote |
| 2  | bamboo-leaf-workerid | snowflake算法机器码生成及记录 | Local、Remote |
| 3  | bamboo-leaf-token    | server对客户端的Token管理  |    Remote     |

# 数据库脚本
1. bamboo-leaf-segment

```
CREATE TABLE `bamboo_leaf_segment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `namespace` varchar(64) NOT NULL  COMMENT '业务类型，唯一',
  `leaf_val` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '当前值',
  `step`  int(11) NOT NULL DEFAULT '1000' COMMENT '步长',
  `delta` int(11) NOT NULL DEFAULT '1' COMMENT '每次id增量',
  `remainder` int(11) NOT NULL DEFAULT '0' COMMENT '余数',
  `retry`  int(11) NOT NULL DEFAULT 10 COMMENT '重试次数',
  `version` bigint(20) NOT NULL DEFAULT 0 COMMENT '版本号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255)  NULL  COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNI_NAMESPACE` (`namespace`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT 'id信息表';

```

2. bamboo-leaf-workerid 

```
CREATE TABLE `bamboo_leaf_workerId` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `namespace` varchar(64) NOT NULL  COMMENT '业务类型标识',
  `host_ip` varchar(64) NOT NULL  COMMENT '主机IP',
  `worker_id` int(11) NOT NULL COMMENT '节点编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255)  NULL  COMMENT '备注',
  PRIMARY KEY (`id`),
   UNIQUE KEY `UNI_NAMESPACE_HOSTIP` (`namespace`,`host_ip`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT 'workId信息表';


```

3. bamboo-leaf-token 


```
CREATE TABLE `bamboo_leaf_token` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `namespace` varchar(64) NOT NULL  COMMENT '此token可访问的业务类型标识',
  `token` varchar(255) NOT NULL  COMMENT 'token',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255)  NULL  COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNI_NAMESPACE_TOKEN` (`namespace`,`token`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT 'token信息表';

```

