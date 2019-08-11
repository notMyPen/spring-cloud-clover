-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.25-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 cn-biz 的数据库结构
CREATE DATABASE IF NOT EXISTS `cn-biz` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cn-biz`;

-- 导出  表 cn-biz.admin 结构
CREATE TABLE IF NOT EXISTS `admin` (
  `user_name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='后台管理员表，未做权限管理';

-- 正在导出表  cn-biz.admin 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;

-- 导出  表 cn-biz.board_award_log 结构
CREATE TABLE IF NOT EXISTS `board_award_log` (
  `id` bigint(20) unsigned NOT NULL,
  `uid` bigint(20) unsigned NOT NULL COMMENT '奖励对象',
  `award_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '奖励个数',
  `award_type` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '0：空；1：转发奖励；2: 认证奖励；3：首次审核奖励；4：生日奖励；5: 连续登陆奖励; 6: 管理员奖励',
  `relation_id` bigint(20) unsigned DEFAULT NULL COMMENT '关联id，根据award_type不同而不同',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='桃花券奖励记录';

-- 正在导出表  cn-biz.board_award_log 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `board_award_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `board_award_log` ENABLE KEYS */;

-- 导出  表 cn-biz.board_pic 结构
CREATE TABLE IF NOT EXISTS `board_pic` (
  `id` bigint(20) unsigned NOT NULL,
  `uid` bigint(20) unsigned NOT NULL,
  `pic_url` varchar(255) NOT NULL COMMENT '用户上传图片(压缩后)',
  `pic_url_thumb` varchar(255) NOT NULL COMMENT '用户上传图片的缩略图',
  `audit_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0未提交信息，1待审核，2审核通过，3被拒绝',
  `board_used` bit(1) NOT NULL DEFAULT b'0' COMMENT '图片是否正在被使用(一般使用最新上传的图片)：0-否 1-是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户上传的桃花牌图片';

-- 正在导出表  cn-biz.board_pic 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `board_pic` DISABLE KEYS */;
/*!40000 ALTER TABLE `board_pic` ENABLE KEYS */;

-- 导出  表 cn-biz.board_turn 结构
CREATE TABLE IF NOT EXISTS `board_turn` (
  `id` bigint(20) unsigned NOT NULL,
  `uid` bigint(20) unsigned NOT NULL COMMENT '翻牌子的人',
  `fuid` bigint(20) unsigned NOT NULL COMMENT '被翻牌子的人',
  `message` varchar(255) DEFAULT NULL COMMENT '留言',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `fuid` (`fuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户翻他人牌子记录';

-- 正在导出表  cn-biz.board_turn 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `board_turn` DISABLE KEYS */;
/*!40000 ALTER TABLE `board_turn` ENABLE KEYS */;

-- 导出  表 cn-biz.board_view 结构
CREATE TABLE IF NOT EXISTS `board_view` (
  `id` bigint(20) unsigned NOT NULL,
  `uid` bigint(20) unsigned NOT NULL COMMENT '浏览的人',
  `fuid` bigint(20) unsigned NOT NULL COMMENT '被浏览的人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `fuid` (`fuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户浏览他人桃花牌记录';

-- 正在导出表  cn-biz.board_view 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `board_view` DISABLE KEYS */;
/*!40000 ALTER TABLE `board_view` ENABLE KEYS */;

-- 导出  表 cn-biz.city 结构
CREATE TABLE IF NOT EXISTS `city` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `country` varchar(32) DEFAULT NULL,
  `province_id` bigint(20) NOT NULL,
  `province` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市';

-- 正在导出表  cn-biz.city 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
/*!40000 ALTER TABLE `city` ENABLE KEYS */;

-- 导出  表 cn-biz.province 结构
CREATE TABLE IF NOT EXISTS `province` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  cn-biz.province 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `province` DISABLE KEYS */;
/*!40000 ALTER TABLE `province` ENABLE KEYS */;

-- 导出  表 cn-biz.school 结构
CREATE TABLE IF NOT EXISTS `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `place` varchar(255) CHARACTER SET utf8mb4 DEFAULT '',
  `type` varchar(255) CHARACTER SET utf8mb4 DEFAULT '',
  `properties` varchar(255) CHARACTER SET utf8mb4 DEFAULT '',
  `college_level` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学校列表';

-- 正在导出表  cn-biz.school 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `school` DISABLE KEYS */;
/*!40000 ALTER TABLE `school` ENABLE KEYS */;

-- 导出  表 cn-biz.statis_business 结构
CREATE TABLE IF NOT EXISTS `statis_business` (
  `business_type` smallint(5) unsigned NOT NULL COMMENT '业务类型',
  `business_id` bigint(20) unsigned NOT NULL COMMENT '业务实体id',
  `statis_item_key` smallint(5) unsigned NOT NULL COMMENT '统计项key',
  `value` bigint(20) DEFAULT NULL COMMENT 'value',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`business_type`,`business_id`,`statis_item_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='某业务类型下某个实体的统计表';

-- 正在导出表  cn-biz.statis_business 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `statis_business` DISABLE KEYS */;
/*!40000 ALTER TABLE `statis_business` ENABLE KEYS */;

-- 导出  表 cn-biz.statis_daliy 结构
CREATE TABLE IF NOT EXISTS `statis_daliy` (
  `date` int(10) unsigned NOT NULL COMMENT '日期',
  `name` smallint(5) unsigned NOT NULL COMMENT '某个业务类型key',
  `value` bigint(20) unsigned DEFAULT NULL COMMENT 'value',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`date`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='每日统计表';

-- 正在导出表  cn-biz.statis_daliy 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `statis_daliy` DISABLE KEYS */;
/*!40000 ALTER TABLE `statis_daliy` ENABLE KEYS */;

-- 导出  表 cn-biz.statis_user 结构
CREATE TABLE IF NOT EXISTS `statis_user` (
  `uid` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `name` smallint(5) unsigned NOT NULL COMMENT '统计项key',
  `value` bigint(20) NOT NULL COMMENT 'value',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户统计表(相当于statis_item表业务类型是user的垂直分表)';

-- 正在导出表  cn-biz.statis_user 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `statis_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `statis_user` ENABLE KEYS */;

-- 导出  表 cn-biz.system_mq_consume_fail 结构
CREATE TABLE IF NOT EXISTS `system_mq_consume_fail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(50) NOT NULL COMMENT 'mq消息唯一Id，防止重复发送',
  `msg_body` varchar(2000) NOT NULL COMMENT '消息体',
  `fail_info` varchar(500) DEFAULT NULL COMMENT '处理失败时的错误提示',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correlation_id` (`correlation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='因程序自身bug或报错等造成mq消费失败且当时无法自动处理的情况，先落地mysql后续再做处理';

-- 正在导出表  cn-biz.system_mq_consume_fail 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `system_mq_consume_fail` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_mq_consume_fail` ENABLE KEYS */;

-- 导出  表 cn-biz.system_op_log 结构
CREATE TABLE IF NOT EXISTS `system_op_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `admin_id` int(10) unsigned NOT NULL COMMENT '后台管理员id',
  `uid` bigint(20) unsigned DEFAULT NULL COMMENT '被操作用户uid',
  `op_type` tinyint(3) unsigned DEFAULT NULL COMMENT '操作类型：1审核通过，2审核拒绝，3拉黑名单，4取消拉黑，5屏蔽所有桃花牌，6下线，7上线',
  `comment` text,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理员操作历史记录';

-- 正在导出表  cn-biz.system_op_log 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `system_op_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_op_log` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
