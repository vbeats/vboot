/*
 Server Type    : MySQL
 Server Version : 80023
 File Encoding         : 65001

 Date: 04/05/2021 11:52:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for action
-- ----------------------------
DROP TABLE IF EXISTS `action`;
CREATE TABLE `action`
(
    `id`        bigint                                                        NOT NULL AUTO_INCREMENT,
    `tenant_id` bigint                                                        NOT NULL COMMENT '租户id',
    `api`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'api path路径',
    `action`    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT 'list/update/delete/add',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `tenant_index` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of action
-- ----------------------------
INSERT INTO `action`
VALUES (1, 1, '/admin/index', 'list');
INSERT INTO `action`
VALUES (2, 1, '/admin/user/add', 'add');
INSERT INTO `action`
VALUES (3, 1, '/admin/user/list', 'list');
INSERT INTO `action`
VALUES (4, 1, '/admin/user/update', 'update');
INSERT INTO `action`
VALUES (5, 1, '/admin/user/delete', 'delete');
INSERT INTO `action`
VALUES (6, 1, '/admin/role/add', 'add');
INSERT INTO `action`
VALUES (7, 1, '/admin/role/list', 'list');
INSERT INTO `action`
VALUES (8, 1, '/admin/role/update', 'update');
INSERT INTO `action`
VALUES (9, 1, '/admin/role/delete', 'delete');
INSERT INTO `action`
VALUES (10, 1, '/admin/action/list', 'list');
INSERT INTO `action`
VALUES (11, 1, '/admin/action/update', 'update');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`             bigint                                                       NOT NULL AUTO_INCREMENT,
    `tenant_id`      bigint                                                       NOT NULL COMMENT '租户id',
    `title`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
    `sort`           int                                                          NOT NULL COMMENT '菜单顺序',
    `key`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单唯一key',
    `path`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'path',
    `icon`           varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'icon图标',
    `p_id`           bigint                                                       NOT NULL COMMENT '父级菜单id  没有父级为0',
    `default_select` tinyint(1)                                                   NOT NULL COMMENT '是否默认选择  0:否   1:是',
    `default_open`   tinyint(1)                                                   NOT NULL COMMENT '二级菜单是否默认展开   0:否  1:是',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `tenant_index` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu`
VALUES (1, 1, '首页', 0, 'index', '/index', 'HomeOutlined', 0, 1, 0);
INSERT INTO `menu`
VALUES (2, 1, '系统设置', 99, 'setting', '/', 'SettingOutlined', 0, 0, 1);
INSERT INTO `menu`
VALUES (3, 1, '用户管理', 100, 'user', '/user', 'UserOutlined', 2, 0, 0);
INSERT INTO `menu`
VALUES (4, 1, '角色管理', 101, 'role', '/role', 'GoldOutlined', 2, 0, 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`        bigint                                                       NOT NULL AUTO_INCREMENT,
    `tenant_id` bigint                                                       NOT NULL COMMENT '租户id',
    `name`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `tenant_index` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role`
VALUES (1, 1, '超级管理员');

-- ----------------------------
-- Table structure for tenant
-- ----------------------------
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT,
    `code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编号',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户名称',
    `create_time` datetime(3)                                                  NOT NULL,
    `delete_time` datetime(3)                                                  NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant
-- ----------------------------
INSERT INTO `tenant`
VALUES (1, '000000', '运营平台', now(3), NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT,
    `username`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '用户名',
    `password`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '密码',
    `tenant_id`   bigint                                                        NOT NULL COMMENT '租户id',
    `role_id`     bigint                                                        NOT NULL COMMENT '角色id',
    `phone`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '手机号',
    `openid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '小程序openid',
    `nickname`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '昵称',
    `avatar`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头像',
    `status`      tinyint(1)                                                    NOT NULL COMMENT '账号状态 0:禁用  1:正常',
    `create_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                   NULL     DEFAULT NULL,
    `update_time` datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `ut_index` (`username`, `tenant_id`) USING BTREE,
    INDEX `username_index` (`username`) USING BTREE,
    INDEX `phone_index` (`phone`) USING BTREE,
    INDEX `tenant_index` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (1, 'admin', md5('123456'), 1, 1, '17705920000', '', '', '', 1, now(3), NULL, NULL);

-- ----------------------------
-- Table structure for user_menu
-- ----------------------------
DROP TABLE IF EXISTS `user_menu`;
CREATE TABLE `user_menu`
(
    `id`      bigint                                                        NOT NULL AUTO_INCREMENT,
    `user_id` bigint                                                        NOT NULL COMMENT '用户 id',
    `menu_id` bigint                                                        NOT NULL COMMENT 'menu id',
    `actions` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'action id ,分隔',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_menu
-- ----------------------------
INSERT INTO `user_menu`
VALUES (1, 1, 1, '1');
INSERT INTO `user_menu`
VALUES (2, 1, 2, '0');
INSERT INTO `user_menu`
VALUES (3, 1, 3, '2,3,4,5');
INSERT INTO `user_menu`
VALUES (4, 1, 4, '6,7,8,9,10,11');

SET FOREIGN_KEY_CHECKS = 1;
