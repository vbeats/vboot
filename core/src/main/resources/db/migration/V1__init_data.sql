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
    `id`     bigint                                                        NOT NULL AUTO_INCREMENT,
    `api`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'api path路径',
    `action` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '按钮:list/update/delete/add',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `api_index` (`api`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of action
-- ----------------------------
INSERT INTO `action`
VALUES (1, '/admin/index', 'index:list');
INSERT INTO `action`
VALUES (2, '/admin/user/add', 'user:add');
INSERT INTO `action`
VALUES (3, '/admin/user/list', 'user:list');
INSERT INTO `action`
VALUES (4, '/admin/user/update', 'user:update');
INSERT INTO `action`
VALUES (5, '/admin/user/delete', 'user:delete');
INSERT INTO `action`
VALUES (6, '/admin/role/add', 'role:add');
INSERT INTO `action`
VALUES (7, '/admin/role/list', 'role:list');
INSERT INTO `action`
VALUES (8, '/admin/role/update', 'role:update');
INSERT INTO `action`
VALUES (9, '/admin/role/delete', 'role:delete');
INSERT INTO `action`
VALUES (10, '/admin/action/list', 'action:list');
INSERT INTO `action`
VALUES (11, '/admin/action/update', 'action:update');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`             bigint                                                       NOT NULL AUTO_INCREMENT,
    `title`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
    `sort`           int                                                          NOT NULL COMMENT '菜单顺序',
    `key`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单唯一key',
    `path`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'path',
    `icon`           varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'icon图标',
    `p_id`           bigint                                                       NOT NULL COMMENT '父级菜单id  没有父级为0',
    `default_select` tinyint(1)                                                   NOT NULL DEFAULT 0 COMMENT '是否默认选择  0:否   1:是',
    `default_open`   tinyint(1)                                                   NOT NULL DEFAULT 0 COMMENT '二级菜单是否默认展开   0:否  1:是',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu`
VALUES (1, '首页', 0, 'index', '/index', 'HomeOutlined', 0, 1, 0);
INSERT INTO `menu`
VALUES (2, '系统设置', 100, 'setting', '/', 'SettingOutlined', 0, 0, 1);
INSERT INTO `menu`
VALUES (3, '用户管理', 101, 'user', '/user', '', 2, 0, 0);
INSERT INTO `menu`
VALUES (4, '角色管理', 102, 'role', '/role', '', 2, 0, 0);

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
-- Table structure for role_menu_action
-- ----------------------------
DROP TABLE IF EXISTS `role_menu_action`;
CREATE TABLE `role_menu_action`
(
    `id`         bigint                                                         NOT NULL AUTO_INCREMENT,
    `role_id`    bigint                                                         NOT NULL COMMENT '角色id',
    `menu_id`    bigint                                                         NOT NULL COMMENT 'menu id',
    `action_ids` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'action id ,分隔',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu_action
-- ----------------------------
INSERT INTO `role_menu_action`
VALUES (1, 1, 1, '1');
INSERT INTO `role_menu_action`
VALUES (2, 1, 2, '0');
INSERT INTO `role_menu_action`
VALUES (3, 1, 3, '2,3,4,5,7,8');
INSERT INTO `role_menu_action`
VALUES (4, 1, 4, '6,7,8,9,10,11');

-- ----------------------------
-- Table structure for tenant
-- ----------------------------
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT,
    `code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编号',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户名称',
    `create_time` datetime(3)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                  NULL     DEFAULT NULL,
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
    `role_id`     bigint                                                        NOT NULL DEFAULT 0 COMMENT '角色id',
    `phone`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT '' COMMENT '手机号',
    `openid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT '' COMMENT '小程序openid',
    `nickname`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT '' COMMENT '昵称',
    `avatar`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '头像',
    `status`      tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '账号状态 0:禁用  1:正常',
    `create_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                   NULL     DEFAULT NULL,
    `delete_time` datetime(3)                                                   NULL     DEFAULT NULL,
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

SET FOREIGN_KEY_CHECKS = 1;