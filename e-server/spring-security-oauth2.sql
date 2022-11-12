CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `account_non_expired` tinyint(1) NOT NULL,
  `account_non_locked` tinyint(1) NOT NULL,
  `credentials_non_expired` tinyint(1) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_user` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8

CREATE TABLE `role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `description` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin

CREATE TABLE `permission` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_user` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


create table user_role
(
    user_id bigint(11) not null,
    role_id bigint(11) not null
)
    charset = utf8;

create table role_permission
(
    role_id       bigint(11) not null,
    permission_id bigint(11) not null
)
    charset = utf8;

create table oauth_client_details
(
    client_id               varchar(48)   not null
        primary key,
    resource_ids            varchar(256)  null,
    client_secret           varchar(256)  null,
    scope                   varchar(256)  null,
    authorized_grant_types  varchar(256)  null,
    web_server_redirect_uri varchar(256)  null,
    authorities             varchar(256)  null,
    access_token_validity   int           null,
    refresh_token_validity  int           null,
    additional_information  varchar(4096) null,
    autoapprove             varchar(256)  null
)
    charset = utf8;
