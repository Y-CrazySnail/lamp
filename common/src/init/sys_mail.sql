CREATE TABLE `sys_mail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `from_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '发件人邮箱',
  `to_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收件人邮箱',
  `subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮件主题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '邮件正文',
  `attachment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '邮件附件URL',
  `business_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务名称（对应模板表name字段）',
  `business_id` int DEFAULT NULL COMMENT '业务ID',
  `timing_flag` int DEFAULT NULL COMMENT '定时发送标识 0否 1是',
  `timing_time` datetime DEFAULT NULL COMMENT '定时发送时间',
  `state` int DEFAULT '0' COMMENT '邮件状态 0未发送 1发送成功 9发送失败',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `try_time` int DEFAULT '0' COMMENT '重试次数',
  `exception` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '异常信息',
  `html_flag` tinyint(1) DEFAULT '1' COMMENT 'HTML标识 0否 1是',
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;