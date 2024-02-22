CREATE TABLE `sys_log` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
  `thread` int DEFAULT NULL COMMENT '线程id',
  `ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'ip',
  `uri` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求连接',
  `operate_module` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '功能模块',
  `operate_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作类型',
  `operate_method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作方法',
  `operate_desc` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作描述',
  `request_state` int DEFAULT NULL COMMENT '请求状态(1正常 2异常)',
  `request_param` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求参数',
  `response_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '返回参数',
  `exception_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '异常名称',
  `exception_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '异常信息',
  `create_time` timestamp(6) NULL DEFAULT NULL COMMENT '操作时间',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  `session_time` int DEFAULT NULL COMMENT '请求时长(秒)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;