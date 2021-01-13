# 概述
# 详细参数配置
## 1. client属性配置

| 序号 | 参数名称 | 参数描述 | 默认值 |  说明 |
|----|------|------|-----|---|
|  1 | bamboo.leaf.client.leafToken|服务端访问授权Token |   无  | Remote模式必填  |
|  2 | bamboo.leaf.client.leafServer|服务器HTTP访问地址  |   无  |  Remote模式必填  |
|  3 | bamboo.leaf.client.leafPort|  服务器HTTP端口  |  无   |  Remote模式必填  |
|  4 | bamboo.leaf.client.readTimeout|  服务读超时时间(ms) |  30000ms   |  Remote模式选填  |
|  5 | bamboo.leaf.client.connectTimeout|  服务连接超时时间(ms) |  30000ms   |  Remote模式选填  |
|  6 | bamboo.leaf.client.mode|   模式(Remote,Local) |  无|   |


## 2. segment性能配置

| 序号 | 参数名称 | 参数描述 | 默认值 | 说明  |
|----|------|------|-----|---|
|  1 |bamboo.leaf.configure.retry | 重试次数 |  10   | 获取数据段更新数据库时重试次数  |
|  2 |bamboo.leaf.configure.loadingPercent| 预加载比例  |   25  | 上个数据段消耗还剩下百分比时，<br>加载下个数据段  |
|  3 |bamboo.leaf.configure.step|步长|1000| 每次从数据库获取的数据段大小  |

说明：这三个参数可以提高的segment性能。

## 3. 自定义表名及字段配置

| 序号 | 参数名称 | 参数描述 | 默认值 |  说明 |
|----|------|------|-----|---|
|  1 |bamboo.leaf.table.segmentTableName| segment表名 |bamboo_leaf_segment|   |
|  2 |bamboo.leaf.table.workerIdTableName|workerId表名|bamboo_leaf_workerId|   |
|  3 |bamboo.leaf.table.namespaceColumnName|命名空间字段字段|namespace|   |
|  4 |bamboo.leaf.table.leafValueColumnName|当前段最大值字段|leaf_val|   |
|  5 |bamboo.leaf.table.versionColumnName| 版本号字段|version     |   |
|  6 |bamboo.leaf.table.retryColumnName| 重试次数字段|retry |   |
|  7 |bamboo.leaf.table.deltaColumnName| 每次id增量字段|delta |   |
|  8 |bamboo.leaf.table.stepColumnName| 步长字段 |  step   |   |
|  9 |bamboo.leaf.table.remainderColumnName|余数字段  | remainder    |   |
|  10|bamboo.leaf.table.createColumnName|创建时间字段 |create_time|   |
|  11|bamboo.leaf.table.updateColumnName|更新时间字段 |update_time     |   |
|  12|bamboo.leaf.tableremarkColumnName|  备注字段   |remark     |   |
|  13|bamboo.leaf.table.workerIdColumnName| 点节序号字段| worker_id    |   |
|  14|bamboo.leaf.tablehostIpColumnName| 主机IP字段| host_ip|   |

说明：表名与字段名不建议修改，请默认SQL脚本执行。