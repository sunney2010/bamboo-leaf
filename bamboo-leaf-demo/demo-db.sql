CREATE TABLE `bamboo_leaf_demo` (
  `id` varchar(32)  NOT NULL  COMMENT 'id',
  `namespace` varchar(64) NOT NULL  COMMENT '业务类型标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255)  NULL  COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT 'workId信息表';

CREATE TABLE `bamboo_leaf_demo_error` (
  `id` varchar(32)  NOT NULL  COMMENT 'id',
  `namespace` varchar(64) NOT NULL  COMMENT '业务类型标识',
  `seq` varchar(32) NOT NULL  COMMENT '序列',
  `message` varchar(512) NOT NULL COMMENT '错误信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255)  NULL  COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'workId信息表';
