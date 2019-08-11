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


-- 导出 cn-order 的数据库结构
CREATE DATABASE IF NOT EXISTS `cn-order` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cn-order`;

-- 导出  表 cn-order.system_capital_list 结构
CREATE TABLE IF NOT EXISTS `system_capital_list` (
  `trade_id` bigint(20) NOT NULL COMMENT '交易id',
  `uid` bigint(20) NOT NULL COMMENT '用户Id',
  `trade_type` int(11) DEFAULT NULL COMMENT '支付通道',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '金额',
  `valid_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效0.否 1.是',
  `receive_bank_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否收钱结束0.否 1.是',
  `reconciliation_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否对过账了0.否 1.是',
  `receive_time` int(10) unsigned DEFAULT NULL COMMENT '回盘时间',
  `reconciliation_time` int(10) unsigned DEFAULT NULL COMMENT '对账时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`trade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='垫资明细记录表';

-- 正在导出表  cn-order.system_capital_list 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `system_capital_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_capital_list` ENABLE KEYS */;

-- 导出  表 cn-order.system_task 结构
CREATE TABLE IF NOT EXISTS `system_task` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `function_name` varchar(50) NOT NULL COMMENT '执行方法名',
  `result` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '执行结果',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务执行表';

-- 正在导出表  cn-order.system_task 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `system_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_task` ENABLE KEYS */;

-- 导出  表 cn-order.trade 结构
CREATE TABLE IF NOT EXISTS `trade` (
  `id` bigint(20) unsigned NOT NULL,
  `pay_order_no` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '第三方平台订单号',
  `trade_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '支付通道',
  `uid` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `withdraw_type` bit(1) NOT NULL DEFAULT b'0' COMMENT '现金流动方向：0-代收/代扣；1-代付',
  `amount` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '交易金额',
  `reserve_data` varchar(2000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '预存数据',
  `trade_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '交易状态：1-已申请未确认；2-已确认(已报盘未回盘)；3-支付成功；4支付失败；5订单作废(第三方过期或关闭)',
  `business_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '业务关联类型：1-充值；2-提现；3-显示手机号；4-成交分钱；',
  `send_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '报盘时间',
  `receive_time` int(10) unsigned DEFAULT NULL COMMENT '回盘时间',
  `reconciliation_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否对过账0.否 1.是((对过账相当于之前进历史表))',
  `reconciliation_time` int(10) unsigned DEFAULT NULL COMMENT '对账时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `send_time` (`send_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易主表';

-- 正在导出表  cn-order.trade 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `trade` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade` ENABLE KEYS */;

-- 导出  表 cn-order.trade_abnormal 结构
CREATE TABLE IF NOT EXISTS `trade_abnormal` (
  `trade_id` bigint(20) NOT NULL COMMENT '订单id',
  `trade_type` tinyint(4) DEFAULT NULL COMMENT '支付通道',
  `type` tinyint(4) DEFAULT NULL COMMENT '现金流动类型：0-代收 1代付',
  `recon_date` date DEFAULT NULL COMMENT '对账日(精确到天)',
  `result` bit(1) DEFAULT NULL COMMENT '第三方支付结果(如果是第三方没有的订单，显示我们的订单支付结果)',
  `amount` int(11) DEFAULT NULL COMMENT '第三方的金额(如果是第三方没有的订单，显示我们的订单金额)',
  `abnormal_type` tinyint(4) DEFAULT NULL COMMENT '异常类型：1-该订单我们有第三方没有；2-该订单我们没有第三方有；3，状态不一致；4-金额不一致；5-状态和金额都不一致',
  `send_msg_status` bit(1) DEFAULT b'0' COMMENT '是否已经发送消息预警',
  PRIMARY KEY (`trade_id`),
  KEY `bill_date` (`recon_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对账后异常的订单(给后台运维人员看的)，由该表发出预警';

-- 正在导出表  cn-order.trade_abnormal 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `trade_abnormal` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade_abnormal` ENABLE KEYS */;

-- 导出  表 cn-order.trade_buycard 结构
CREATE TABLE IF NOT EXISTS `trade_buycard` (
  `trade_id` bigint(20) unsigned NOT NULL COMMENT '交易id',
  `uid` bigint(20) unsigned NOT NULL,
  `amount` int(10) unsigned NOT NULL COMMENT '缴费金额(单位分)',
  `buy_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '购买类型：0-未知；1-买5张；2-买10张；3-买20张',
  `award_board_num` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '购买券时奖励的券',
  `valid_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '交易是否有效0.否 1.是',
  `receive_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '收钱是否结束0.否1.是',
  `used` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否使用：0-否 1-是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`trade_id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信用认证缴费记录';

-- 正在导出表  cn-order.trade_buycard 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `trade_buycard` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade_buycard` ENABLE KEYS */;

-- 导出  表 cn-order.trade_credit_fee 结构
CREATE TABLE IF NOT EXISTS `trade_credit_fee` (
  `trade_id` bigint(20) unsigned NOT NULL COMMENT '交易id',
  `uid` bigint(20) unsigned NOT NULL,
  `amount` int(10) unsigned NOT NULL COMMENT '缴费金额(单位分)',
  `credit_type` tinyint(3) unsigned NOT NULL COMMENT '认证类型：1-基础认证 2-二级认证',
  `valid_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '交易是否有效0.否 1.是',
  `receive_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '收钱是否结束0.否1.是',
  `used` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否使用：0-否 1-是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`trade_id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信用认证缴费记录';

-- 正在导出表  cn-order.trade_credit_fee 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `trade_credit_fee` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade_credit_fee` ENABLE KEYS */;

-- 导出  表 cn-order.trade_reconciliation 结构
CREATE TABLE IF NOT EXISTS `trade_reconciliation` (
  `trade_id` bigint(20) unsigned NOT NULL COMMENT '交易订单id',
  `trade_type` tinyint(3) unsigned DEFAULT NULL COMMENT '支付通道',
  `type` tinyint(4) unsigned DEFAULT NULL COMMENT '代收0；代付1；退款2；验证3；其他4',
  `recon_date` int(10) DEFAULT NULL COMMENT '对账日(精确到天)',
  `bill_date` int(10) NOT NULL DEFAULT '0' COMMENT '账单日(精确到天)',
  `result` bit(1) DEFAULT NULL COMMENT '是否成功0.否1.是',
  `amount` int(10) unsigned DEFAULT NULL COMMENT '金额',
  `commision` int(10) unsigned DEFAULT '0' COMMENT '第三方交易手续费',
  `bill_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否对过账',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`trade_id`),
  KEY `bill_date` (`bill_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账单表';

-- 正在导出表  cn-order.trade_reconciliation 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `trade_reconciliation` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade_reconciliation` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
