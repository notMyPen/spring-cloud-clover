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


-- 导出 clover-biz 的数据库结构
CREATE DATABASE IF NOT EXISTS `clover-biz` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `clover-biz`;

-- 导出  表 clover-biz.admin 结构
CREATE TABLE IF NOT EXISTS `admin` (
  `user_name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='后台管理员表，未做权限管理';

-- 正在导出表  clover-biz.admin 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;

-- 导出  表 clover-biz.board_turn 结构
CREATE TABLE IF NOT EXISTS `board_turn` (
  `id` bigint(20) unsigned NOT NULL,
  `uid` bigint(20) unsigned NOT NULL COMMENT '翻牌子的人',
  `fuid` bigint(20) unsigned NOT NULL COMMENT '被翻牌子的人',
  `message` varchar(255) DEFAULT NULL COMMENT '留言',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `fuid` (`fuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户翻他人牌子(要微信号)记录';

-- 正在导出表  clover-biz.board_turn 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `board_turn` DISABLE KEYS */;
/*!40000 ALTER TABLE `board_turn` ENABLE KEYS */;

-- 导出  表 clover-biz.board_view 结构
CREATE TABLE IF NOT EXISTS `board_view` (
  `id` bigint(20) unsigned NOT NULL,
  `uid` bigint(20) unsigned NOT NULL COMMENT '浏览的人',
  `fuid` bigint(20) unsigned NOT NULL COMMENT '被浏览的人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `fuid` (`fuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户浏览他人桃花牌记录';

-- 正在导出表  clover-biz.board_view 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `board_view` DISABLE KEYS */;
/*!40000 ALTER TABLE `board_view` ENABLE KEYS */;

-- 导出  表 clover-biz.card_award_record 结构
CREATE TABLE IF NOT EXISTS `card_award_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) unsigned NOT NULL COMMENT '奖励对象',
  `award_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '奖励个数',
  `award_type` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '0：空；1：转发奖励；2: 认证奖励；3：首次审核奖励；4：生日奖励；5: 连续登陆奖励; 6: 管理员奖励',
  `relation_id` bigint(20) unsigned DEFAULT NULL COMMENT '关联id，根据award_type不同而不同',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='礼券奖励记录';

-- 正在导出表  clover-biz.card_award_record 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `card_award_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `card_award_record` ENABLE KEYS */;

-- 导出  表 clover-biz.card_use_record 结构
CREATE TABLE IF NOT EXISTS `card_use_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) unsigned NOT NULL COMMENT '奖励对象',
  `award_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '奖励个数',
  `award_type` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '0：空；1：转发奖励；2: 认证奖励；3：首次审核奖励；4：生日奖励；5: 连续登陆奖励; 6: 管理员奖励',
  `relation_id` bigint(20) unsigned DEFAULT NULL COMMENT '关联id，根据award_type不同而不同',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='礼券使用记录';

-- 正在导出表  clover-biz.card_use_record 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `card_use_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `card_use_record` ENABLE KEYS */;

-- 导出  表 clover-biz.city 结构
CREATE TABLE IF NOT EXISTS `city` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `country` varchar(32) DEFAULT NULL,
  `province_id` bigint(20) NOT NULL,
  `province` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市';

-- 正在导出表  clover-biz.city 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
/*!40000 ALTER TABLE `city` ENABLE KEYS */;

-- 导出  表 clover-biz.credit_dishonest_cases 结构
CREATE TABLE IF NOT EXISTS `credit_dishonest_cases` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `type` tinyint(3) unsigned NOT NULL COMMENT '失信类型，0：被执行人；1：限制消费人员；2：失信被执行人',
  `case_id` varchar(50) NOT NULL COMMENT '案件号',
  `case_create_time` varchar(50) DEFAULT NULL COMMENT '立案时间',
  `court_name` varchar(50) DEFAULT NULL COMMENT '法院名称',
  `province` varchar(50) DEFAULT NULL COMMENT '法院所在省份',
  `case_document_id` varchar(50) DEFAULT NULL COMMENT '执行依据文件号',
  `discredit_publish_time` varchar(50) DEFAULT NULL COMMENT '如为失信被执行人，则此字段为失信发布日期',
  `execution_target` varchar(50) DEFAULT NULL COMMENT '执行标的',
  `left_target` varchar(50) DEFAULT NULL COMMENT '未履行标的',
  `execution_description` varchar(1000) DEFAULT NULL COMMENT '执行情况描述',
  `case_end_time` varchar(50) DEFAULT NULL COMMENT '如为终本案件，则此字段为终本时间；否则无此字段',
  PRIMARY KEY (`id`),
  KEY `user_id` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户高发失信案件列表';

-- 正在导出表  clover-biz.credit_dishonest_cases 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `credit_dishonest_cases` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit_dishonest_cases` ENABLE KEYS */;

-- 导出  表 clover-biz.credit_dt_lend_detail 结构
CREATE TABLE IF NOT EXISTS `credit_dt_lend_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL COMMENT '用户uid',
  `borrow_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '借款类型0.未知1.个人信贷2.个人抵押3.企业信贷4.企业抵押',
  `borrow_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '借款状态0.未知1.拒贷2.批贷已放款4.借款人放弃申请5.审核中6.待放款（3同6意义相同）',
  `borrow_amount` int(11) NOT NULL DEFAULT '0' COMMENT '合同金额-7.[0,0.1) -6.[0.1,0.2) -5.[0.2,0.3) -4.[0.3,0.4) -3.[0.4,0.6) -2.[0.6,0.8) -1.[0.8,1) 0.未知 1.[1,2) 2.[2,4) 3.[4,6) 4.[6,8)…….(单位:万元)2万一档依次类推',
  `contract_date` int(11) DEFAULT NULL COMMENT '合同日期(未批贷时为借款日期)',
  `loan_period` int(11) DEFAULT NULL COMMENT '批贷期数',
  `repay_state` int(11) NOT NULL DEFAULT '0' COMMENT '还款状态0.未知1.正常2.M1 3.M2 4.M3 5.M4 6.M5 7.M6 8.M6+9.已还清',
  `arrears_amount` int(11) DEFAULT NULL COMMENT '欠款金额(实际金额=91返回金额/100000)',
  `company_code` varchar(50) DEFAULT NULL COMMENT '公司编码有三种一、以（P2P）开头的例：P2P12345678不作为具体公司标识仅作为参与反馈公司的标示可以用于识别反馈公司的数量 二、（人人催）开头的这类数据是经过催收的人员名单。三、（风险名单）的这类数据是由人人催系统提供的风险预警名单表示客户存在风险',
  PRIMARY KEY (`id`),
  KEY `user_id` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户多头借贷明细列表';

-- 正在导出表  clover-biz.credit_dt_lend_detail 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `credit_dt_lend_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit_dt_lend_detail` ENABLE KEYS */;

-- 导出  表 clover-biz.credit_dt_lend_summary 结构
CREATE TABLE IF NOT EXISTS `credit_dt_lend_summary` (
  `uid` bigint(20) NOT NULL COMMENT '用户uid',
  `loan_success_rate` smallint(6) NOT NULL COMMENT '申贷成功率(乘以100后的数)',
  `loan_platform_count` smallint(6) NOT NULL COMMENT '金融借贷平台总数',
  `badness_platform_count` smallint(6) NOT NULL COMMENT '不良记录平台数',
  `borrow_cnt` int(11) NOT NULL COMMENT '借贷总次数',
  `borrow_success_cnt` int(11) NOT NULL COMMENT '借贷成功次数',
  `refuse_count` int(11) NOT NULL COMMENT '拒贷次数',
  `overdue_cnt` int(11) NOT NULL COMMENT '逾期次数',
  `overdue_amt` bigint(20) NOT NULL COMMENT '逾期金额(需要除以100000)',
  `audit_count` int(11) NOT NULL COMMENT '审核中次数',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户多头借贷汇总信息';

-- 正在导出表  clover-biz.credit_dt_lend_summary 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `credit_dt_lend_summary` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit_dt_lend_summary` ENABLE KEYS */;

-- 导出  表 clover-biz.credit_manual 结构
CREATE TABLE IF NOT EXISTS `credit_manual` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) unsigned NOT NULL,
  `pic_url_list` varchar(1000) NOT NULL COMMENT '用户上传图片(压缩后)，多个图片用分号(;)分割',
  `pic_type` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '图片类型：0-海外大学认证；1-收入照片；2-房产照片；3-车产照片',
  `audit_status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '0未提交信息，1待审核，2审核通过，3被拒绝',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户手动认证上传的图片的审核情况';

-- 正在导出表  clover-biz.credit_manual 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `credit_manual` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit_manual` ENABLE KEYS */;

-- 导出  表 clover-biz.credit_status 结构
CREATE TABLE IF NOT EXISTS `credit_status` (
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `idcard_verify_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '身份证认证状态',
  `face_verify_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '人脸认证状态',
  `xuexin_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '学信认证状态',
  `shebao_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '社保认证状态',
  `gjj_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '公积金认证状态',
  `duotou_lend_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '多头借贷认证状态',
  `dishonest_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '高法失信认证状态',
  `marry_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '婚姻认证状态',
  `taobao_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '淘宝认证状态',
  `alipay_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付宝认证状态',
  `jingdong_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '京东认证状态',
  `zhengxin_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '征信数据认证状态',
  `zhima_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '芝麻信用认证状态',
  `zhima_month` int(10) unsigned DEFAULT NULL COMMENT '芝麻分月份(yyyy-MM-dd格式的时间转成秒)',
  `house_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '房产认证状态',
  `car_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '车产认证状态',
  `income_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '收入认证状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存用户各项认证状态（状态分类：0.未认证 1.待完善 2.认证中 3.认证成功 4.认证失败 5.已过期）';

-- 正在导出表  clover-biz.credit_status 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `credit_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit_status` ENABLE KEYS */;

-- 导出  表 clover-biz.province 结构
CREATE TABLE IF NOT EXISTS `province` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  clover-biz.province 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `province` DISABLE KEYS */;
/*!40000 ALTER TABLE `province` ENABLE KEYS */;

-- 导出  表 clover-biz.school 结构
CREATE TABLE IF NOT EXISTS `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `place` varchar(255) CHARACTER SET utf8mb4 DEFAULT '',
  `type` varchar(255) CHARACTER SET utf8mb4 DEFAULT '',
  `properties` varchar(255) CHARACTER SET utf8mb4 DEFAULT '',
  `college_level` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学校列表';

-- 正在导出表  clover-biz.school 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `school` DISABLE KEYS */;
/*!40000 ALTER TABLE `school` ENABLE KEYS */;

-- 导出  表 clover-biz.statis_business 结构
CREATE TABLE IF NOT EXISTS `statis_business` (
  `business_type` smallint(5) unsigned NOT NULL COMMENT '业务类型',
  `business_id` bigint(20) unsigned NOT NULL COMMENT '业务实体id',
  `statis_item_key` smallint(5) unsigned NOT NULL COMMENT '统计项key',
  `value` bigint(20) DEFAULT NULL COMMENT 'value',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`business_type`,`business_id`,`statis_item_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='某业务类型下某个实体的统计表（业务类新是user的数据已分到statis_user表）';

-- 正在导出表  clover-biz.statis_business 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `statis_business` DISABLE KEYS */;
/*!40000 ALTER TABLE `statis_business` ENABLE KEYS */;

-- 导出  表 clover-biz.statis_daliy 结构
CREATE TABLE IF NOT EXISTS `statis_daliy` (
  `date` int(10) unsigned NOT NULL COMMENT '日期',
  `name` smallint(5) unsigned NOT NULL COMMENT '某个业务类型key',
  `value` bigint(20) unsigned DEFAULT NULL COMMENT 'value',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`date`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='每日统计表';

-- 正在导出表  clover-biz.statis_daliy 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `statis_daliy` DISABLE KEYS */;
/*!40000 ALTER TABLE `statis_daliy` ENABLE KEYS */;

-- 导出  表 clover-biz.statis_user 结构
CREATE TABLE IF NOT EXISTS `statis_user` (
  `uid` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `name` smallint(5) unsigned NOT NULL COMMENT '统计项key',
  `value` bigint(20) NOT NULL COMMENT 'value',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户统计表(相当于statis_business表业务类型是user的垂直分表)';

-- 正在导出表  clover-biz.statis_user 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `statis_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `statis_user` ENABLE KEYS */;

-- 导出  表 clover-biz.system_mq_consume_fail 结构
CREATE TABLE IF NOT EXISTS `system_mq_consume_fail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(50) NOT NULL COMMENT 'mq消息唯一Id，防止重复发送',
  `msg_body` varchar(2000) NOT NULL COMMENT '消息体',
  `fail_info` varchar(500) DEFAULT NULL COMMENT '处理失败时的错误提示',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correlation_id` (`correlation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='因程序自身bug或报错等造成mq消费失败且当时无法自动处理的情况，先落地mysql后续再做处理';

-- 正在导出表  clover-biz.system_mq_consume_fail 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `system_mq_consume_fail` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_mq_consume_fail` ENABLE KEYS */;

-- 导出  表 clover-biz.system_op_log 结构
CREATE TABLE IF NOT EXISTS `system_op_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `admin_id` int(10) unsigned NOT NULL COMMENT '后台管理员id',
  `uid` bigint(20) unsigned DEFAULT NULL COMMENT '被操作用户uid',
  `op_type` tinyint(3) unsigned DEFAULT NULL COMMENT '操作类型：1审核通过，2审核拒绝，3拉黑名单，4取消拉黑，5屏蔽所有桃花牌，6下线，7上线',
  `comment` text,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理员操作历史记录';

-- 正在导出表  clover-biz.system_op_log 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `system_op_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_op_log` ENABLE KEYS */;


-- 导出 clover-order 的数据库结构
CREATE DATABASE IF NOT EXISTS `clover-order` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `clover-order`;

-- 导出  表 clover-order.system_capital_list 结构
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

-- 正在导出表  clover-order.system_capital_list 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `system_capital_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_capital_list` ENABLE KEYS */;

-- 导出  表 clover-order.system_task 结构
CREATE TABLE IF NOT EXISTS `system_task` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `function_name` varchar(50) NOT NULL COMMENT '执行方法名',
  `result` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '执行结果',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务执行表';

-- 正在导出表  clover-order.system_task 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `system_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_task` ENABLE KEYS */;

-- 导出  表 clover-order.trade 结构
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

-- 正在导出表  clover-order.trade 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `trade` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade` ENABLE KEYS */;

-- 导出  表 clover-order.trade_abnormal 结构
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

-- 正在导出表  clover-order.trade_abnormal 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `trade_abnormal` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade_abnormal` ENABLE KEYS */;

-- 导出  表 clover-order.trade_buycard 结构
CREATE TABLE IF NOT EXISTS `trade_buycard` (
  `trade_id` bigint(20) unsigned NOT NULL COMMENT '交易id',
  `uid` bigint(20) unsigned NOT NULL,
  `amount` int(10) unsigned NOT NULL COMMENT '缴费金额(单位分)',
  `buy_num` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '购买券数',
  `award_board_num` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '购买时奖励的券数',
  `valid_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '交易是否有效0.否 1.是',
  `receive_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '收钱是否结束0.否1.是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`trade_id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购买券缴费记录';

-- 正在导出表  clover-order.trade_buycard 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `trade_buycard` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade_buycard` ENABLE KEYS */;

-- 导出  表 clover-order.trade_credit_fee 结构
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

-- 正在导出表  clover-order.trade_credit_fee 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `trade_credit_fee` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade_credit_fee` ENABLE KEYS */;

-- 导出  表 clover-order.trade_reconciliation 结构
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

-- 正在导出表  clover-order.trade_reconciliation 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `trade_reconciliation` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade_reconciliation` ENABLE KEYS */;


-- 导出 clover-user 的数据库结构
CREATE DATABASE IF NOT EXISTS `clover-user` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `clover-user`;

-- 导出  表 clover-user.msg_form_id 结构
CREATE TABLE IF NOT EXISTS `msg_form_id` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` bigint(20) NOT NULL COMMENT '用户uid',
  `form_id` varchar(50) NOT NULL COMMENT 'formid',
  `expire_time` bigint(20) NOT NULL COMMENT '失效时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `k_uid` (`uid`),
  KEY `k_expire_time` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小程序消息句柄';

-- 正在导出表  clover-user.msg_form_id 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `msg_form_id` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_form_id` ENABLE KEYS */;

-- 导出  表 clover-user.msg_send_code 结构
CREATE TABLE IF NOT EXISTS `msg_send_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `telephone` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '电话号码',
  `voice_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是语音0.否1.是',
  `code` int(11) DEFAULT NULL COMMENT '发送的验证码',
  `state` bit(1) DEFAULT NULL COMMENT '0 ：失败，1：成功',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='向用户发送的验证码';

-- 正在导出表  clover-user.msg_send_code 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `msg_send_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_send_code` ENABLE KEYS */;

-- 导出  表 clover-user.msg_wechat 结构
CREATE TABLE IF NOT EXISTS `msg_wechat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) unsigned NOT NULL,
  `view_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '用户是否看过',
  `msg_type` tinyint(4) unsigned NOT NULL COMMENT '发送的类型，不同类型对应不同消息模板',
  `msg_value` varchar(50) DEFAULT NULL COMMENT '消息内容中变量部分的值，不同值用#号分割并和消息模板中%s符号顺序对应',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='向用户发送的微信消息';

-- 正在导出表  clover-user.msg_wechat 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `msg_wechat` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_wechat` ENABLE KEYS */;

-- 导出  表 clover-user.user_account 结构
CREATE TABLE IF NOT EXISTS `user_account` (
  `uid` bigint(20) unsigned NOT NULL,
  `pay_salt` varchar(50) DEFAULT NULL COMMENT '支付密码的盐',
  `pay_password` varchar(32) DEFAULT NULL COMMENT '支付密码',
  `balance` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户账户实际余额',
  `withdraw_balance` int(11) NOT NULL DEFAULT '0' COMMENT '用户账户当天可提现余额',
  `open_account` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否在支付中心开户：true-已开户；false-未开户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户汇总表';

-- 正在导出表  clover-user.user_account 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;

-- 导出  表 clover-user.user_account_list 结构
CREATE TABLE IF NOT EXISTS `user_account_list` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trade_id` bigint(20) unsigned NOT NULL COMMENT '订单id',
  `uid` bigint(20) unsigned NOT NULL COMMENT '流入uid',
  `fuid` bigint(20) unsigned NOT NULL COMMENT '来源uid',
  `amount` int(10) NOT NULL COMMENT '金额,单位:分',
  `acount_type` tinyint(3) unsigned NOT NULL COMMENT '动账类型:1:充值, 2:提现，3收到查看手机号手续费，4支付查看手机号手续费，5收到赏金，6支付赏金',
  `business_type` tinyint(3) unsigned NOT NULL COMMENT '业务关联类型：1-充值；2-提现；3-显示手机号；4-成交分钱；',
  `trade_type` tinyint(3) unsigned DEFAULT NULL COMMENT '支付通道',
  `valid_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效0.否 1.是',
  `receive_bank_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否收钱结束0.否 1.是',
  `reconciliation_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否对过账了0.否 1.是',
  `receive_time` int(11) unsigned DEFAULT NULL COMMENT '回盘时间',
  `reconciliation_time` int(10) unsigned DEFAULT NULL COMMENT '对账时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `trade_id` (`trade_id`),
  KEY `k_uid` (`uid`,`acount_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户账目明细表';

-- 正在导出表  clover-user.user_account_list 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_account_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_account_list` ENABLE KEYS */;

-- 导出  表 clover-user.user_basic_info 结构
CREATE TABLE IF NOT EXISTS `user_basic_info` (
  `uid` bigint(20) unsigned NOT NULL COMMENT '用户uid',
  `id_card_no` varchar(50) NOT NULL DEFAULT '0' COMMENT '用户身份证号',
  `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别：0-未知；1-男；2-女',
  `wx_account` varchar(50) NOT NULL DEFAULT '0' COMMENT '微信号',
  `birthday` int(11) NOT NULL DEFAULT '0' COMMENT '生日(yyyy-MM-dd格式的时间转成的秒)',
  `zodiac` tinyint(4) NOT NULL DEFAULT '0' COMMENT '属相：0-未知；按顺序1-12分别表示鼠、牛、虎、兔、龙、蛇、马、羊、猴、鸡、狗、猪',
  `constellation` tinyint(4) NOT NULL DEFAULT '0' COMMENT '星座：0-未知；按顺序1-12依次表示白羊座、金牛座、双子座、巨蟹座、狮子座、处女座、天秤座、天蝎座、射手座、摩羯座、水瓶座、双鱼座',
  `height` smallint(6) NOT NULL DEFAULT '0' COMMENT '身高(单位cm)',
  `figure_list` varchar(50) DEFAULT NULL COMMENT '身材(JsonArray转成的字符串)：0-一般、11-瘦长(男)、12-运动员型(男)、13-较胖(男)、14-体格魁梧(男)、15-壮实(男)、21-瘦长(女)、22-苗条(女)、23-高大美丽(女)、24-丰满(女)、25-富线条美(女)',
  `home_province_id` int(11) NOT NULL DEFAULT '0' COMMENT '故乡省份id',
  `home_city_id` int(11) NOT NULL DEFAULT '0' COMMENT '故乡地级市id',
  `home_province_city` varchar(50) DEFAULT NULL COMMENT '故乡地名称(省-市)，冗余存储便于显示',
  `now_province_id` int(11) NOT NULL DEFAULT '0' COMMENT '当前工作生活地省份id',
  `now_city_id` int(11) NOT NULL DEFAULT '0' COMMENT '当前工作生活地级市id',
  `now_province_city` varchar(50) DEFAULT NULL COMMENT '当前工作生活地名称(省-市)，冗余存储便于显示',
  `marital_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '婚姻状态：0-未知；1-未婚；2-离异；3-丧偶',
  `have_children` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有无子女：0-未知；1-没有孩子、2-有孩子且住在一起、3-有孩子偶尔一起住、4-有孩子但不在身边',
  `character_list` varchar(255) DEFAULT NULL COMMENT '性格(JsonArray转成的字符串)：1-善良、2-乐观、3-独立、4-大方、5-活泼、6-可爱、7-直率、8-内敛、9-害羞、10-温柔、11-急躁、12-泼辣、13-谦虚、14-随和、15-理性、16-好胜、17-古怪精灵、18-大大咧咧、19-开朗、20-中二、21-文静、22-急脾气、23-沉稳、25-其他',
  `academic` tinyint(4) NOT NULL DEFAULT '0' COMMENT '学历：0-未知、1-博士、2-硕士、3-本科、4-大专、5高中及以下',
  `college_id` int(11) NOT NULL DEFAULT '0' COMMENT '毕业院校id',
  `college` varchar(50) DEFAULT NULL COMMENT '毕业院校名称，冗余存储便于显示',
  `school_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '学校类型：0-未知、1-985，2-211，3-985且211、4-一般全日制大学，5-海外院校（根据学校名称自动生成）',
  `year_income` tinyint(4) NOT NULL DEFAULT '0' COMMENT '年收入：0-未知；1-10W以下、2-10W-20W、3-20W-30W、4-30W-50W、5-50W-100W、6-100W以上',
  `house_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '住房情况：0-空；1-和家人同住、2-已购房有贷款、3-已购房无贷款、4-租房、5-打算婚后买房、6-住单位宿舍',
  `car_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '购车情况：0-空；1 - 已购车;2 - 未购车; ',
  `asset_level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '资产水平：0-未知、1-所在城市基本生活水平、2-所在城市小康生活水平、3-所在城市家境优渥',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息表';

-- 正在导出表  clover-user.user_basic_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_basic_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_basic_info` ENABLE KEYS */;

-- 导出  表 clover-user.user_detail_info 结构
CREATE TABLE IF NOT EXISTS `user_detail_info` (
  `uid` bigint(20) unsigned NOT NULL COMMENT '用户uid',
  `company_name` varchar(255) DEFAULT NULL COMMENT '所在单位名称',
  `company_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '所在单位类型：0-未知、1-央企、2-国企、3-事业单位、4-私企、5-外企、6-其他',
  `industry_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '所在行业：0-未知、1-计算机/互联网/通信、2-公务员/事业单位、3-教师、4-医生、5-护士、6-空乘人员、7-生产/工艺/制造、8-商业/服务业/个体经营、9-金融/银行/投资/保险、10-文化/广告/传媒、11-娱乐/艺术/表演、12-律师/法务、13-教育/培训/管理咨询、14-建筑/房地产/物业、15-消费零售/贸易/交通物流、16-酒店旅游、17-现代农业、18在校学生、19-其他',
  `rank_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '所处职级：0-未知、1-企业主或单位负责人、2-高层管理、3-中层管理、4-普通职员、5-其他',
  `dishonest_credit_result` tinyint(4) NOT NULL DEFAULT '0' COMMENT '高发失信认证结果：0-未认证(无结果)；1-无失信记录(安全)；2-有失信记录',
  `duotou_lend_result` tinyint(4) NOT NULL DEFAULT '0' COMMENT '多头借贷认证结果：0-未认证(无结果)；1-无借贷记录；2-有借贷记录',
  `parental_situation` tinyint(4) NOT NULL DEFAULT '0' COMMENT '父母情况：0-未知、1-父母健在、2-单亲家庭、3-父亲健在、4-母亲健在、5-父母均离世',
  `relation_with_parents_list` varchar(50) DEFAULT NULL COMMENT '和父母的关系(JsonArray转成的字符串)：1-像朋友一样、2-尊重孝顺、3-长伴依赖、4-各自独立',
  `only_child` tinyint(4) NOT NULL DEFAULT '0' COMMENT '独生子女：0-未知、1-独生子女、2-有兄弟姐妹',
  `have_brother` bit(1) NOT NULL DEFAULT b'0' COMMENT '有无哥哥：0-无、1-有',
  `have_younger_brother` bit(1) NOT NULL DEFAULT b'0' COMMENT '有无弟弟：0-无、1-有',
  `have_sister` bit(1) NOT NULL DEFAULT b'0' COMMENT '有无姐姐：0-无、1-有',
  `have_younger_sister` bit(1) NOT NULL DEFAULT b'0' COMMENT '有无妹妹：0-无、1-有',
  `interest_list` varchar(50) DEFAULT NULL COMMENT '兴趣爱好(JsonArray转成的字符串)：1-音乐、2-电影、3-摄影、4-健身、5-跑步、6-游泳、7-潜水、8-徒步、9-攀岩、10-跳伞、11-滑雪、12-极限挑战、13-宠物、14-旅行、15-乐器、16-表演、17-看书、18-绘画、19-收藏、20-美食、21-手工、22-赛车、23-网游、24-cosplay、25-其他',
  `like_eat_list` varchar(50) DEFAULT NULL COMMENT '喜欢吃的(JsonArray转成的字符串)：1-川菜、2-湘菜、3-粤菜、4-鲁菜、5-徽菜、6-江浙菜、7-淮扬菜、8-西北菜、9-东北菜、10-北京菜、11-云贵菜、12-港式、13-日料、14-韩餐、15-泰餐、16-意餐、17-法餐、18-德国肘子、19-西班牙火腿、20-墨西哥甩饼、21-土尔其烤肉、22-甜品、23-其他',
  `marry_plan` tinyint(4) NOT NULL DEFAULT '0' COMMENT '结婚计划：0-未知、1-愿意闪婚、2-一年内、3-两年内、4-时机成熟就结婚',
  `child_plan` tinyint(4) NOT NULL DEFAULT '0' COMMENT '对孩子的计划：0-未知、1-要一个孩子、2-要多个孩子、3-不想要孩子、4-视情况而定',
  `ideal_partner_type_list` varchar(50) DEFAULT NULL COMMENT '理想的伴侣类型(JsonArray转成的字符串)：11-成熟稳重(男)、12-温暖阳光(男)、13-寡言内秀(男)、14-活跃幽默(男)、21-温柔贤淑(女)、22-活泼可爱(女)、23-爽朗直率(女)、24-理性独立(女)',
  `consume_attitude` tinyint(4) NOT NULL DEFAULT '0' COMMENT '对花钱的态度：0-未知、1-少花多存、2-花一半存一半、3-多花少存、4-月光、5-及时行乐花了再说',
  `active_pursuit` tinyint(4) NOT NULL DEFAULT '0' COMMENT '遇到心仪的异性时是否会主动追求：1-会,爱情要靠自己争取、2-不会主动追求但会有所暗示、3-不会,等待对方主动靠近',
  `mind_emotion_experiences` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否介意对方感情经历：1-必须交待清楚才能开始我和Ta之间的感情、2-有点介意,希望Ta能坦诚相告、3-完全不介意',
  `single_reason_list` varchar(50) DEFAULT NULL COMMENT '单身原因(JsonArray转成的字符串)：1-生活圈子太小、2-不够积极主动、3-工作太忙、4-择偶标准高、5-经济压力、6-父母影响、7-情感曾经受挫、8-对婚姻没有安全感、9-崇尚单身主义、10-对自己不自信、11-性格原因、12-暂时不想脱单、13-觉得自己年龄还小、14-不知道如何与异性相处、15-其他',
  `faith` tinyint(4) NOT NULL DEFAULT '0' COMMENT '宗教信仰：0-未知、1-无宗教信仰、2-佛教、3-道教、4-基督教、5-伊斯兰教、6-印度教、7-萨满教、8-其他教派',
  `smok_drink` tinyint(4) NOT NULL DEFAULT '0' COMMENT '吸烟喝酒状况：0-未知、1-不吸烟不饮酒、2-不吸烟饮酒、3-吸烟不饮酒、4-烟酒不离手',
  `housework` tinyint(4) NOT NULL DEFAULT '0' COMMENT '对家务活的态度：0-未知、1-家务小能手、2-分工合作、3-不太会但愿意为了对方学习、4-对方能承包家务就太好了',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户详细信息表';

-- 正在导出表  clover-user.user_detail_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_detail_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_detail_info` ENABLE KEYS */;

-- 导出  表 clover-user.user_login_record 结构
CREATE TABLE IF NOT EXISTS `user_login_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) unsigned NOT NULL,
  `give_node` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是赠送节点(用于连续登陆5天赠送券)：0-否；1-是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录记录';

-- 正在导出表  clover-user.user_login_record 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_login_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_login_record` ENABLE KEYS */;

-- 导出  表 clover-user.user_passport 结构
CREATE TABLE IF NOT EXISTS `user_passport` (
  `uid` bigint(20) NOT NULL,
  `regist_tel` varchar(15) NOT NULL DEFAULT '0' COMMENT '登陆手机号',
  `mini_open_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '微信小程序openId',
  `open_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '微信公众号openId',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称(用户未修改默认用微信昵称)',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户主照片(用户未上传之前默认将微信头像上传oss)',
  `card_num` int(11) NOT NULL DEFAULT '0' COMMENT '翻牌券个数',
  `credit_pass` bit(1) NOT NULL DEFAULT b'0' COMMENT '信用认证是否全部通过：0-否 1-是',
  `board_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户桃花牌(是否首页展示)状态：0-不展示、1-展示、2-下线(当某个用户一个有效且审核通过的照片都没有时会自动下线桃花牌)',
  `high_quality` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是高质量用户：0-否 1-是',
  `subscribe` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否关注公众号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户最后登录时间(由于该表中可更新的字段几乎没有)',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `regist_tel` (`regist_tel`),
  UNIQUE KEY `mini_open_id` (`mini_open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户通行证(包括用户微信授权信息和各种状态)';

-- 正在导出表  clover-user.user_passport 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_passport` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_passport` ENABLE KEYS */;

-- 导出  表 clover-user.user_pic 结构
CREATE TABLE IF NOT EXISTS `user_pic` (
  `id` bigint(20) unsigned NOT NULL,
  `uid` bigint(20) unsigned NOT NULL COMMENT '用户uid',
  `pic_url` varchar(255) NOT NULL COMMENT '用户上传图片压缩图(用户展示)',
  `pic_url_thumb` varchar(255) NOT NULL COMMENT '用户上传图片的缩略图(用于头像)',
  `pic_url_origin` varchar(255) NOT NULL COMMENT '用户上传图片的原图',
  `pic_order` tinyint(4) NOT NULL DEFAULT '0' COMMENT '图片顺序',
  `valid_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '图片是否有效',
  `audit_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态(自动和手动)：0未提交信息，1待审核(未进行信用认证而需人工审核时)，2审核通过，3审核未通过',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户上传自身照片列表(当某个用户一个有效且审核通过的照片都没有时会自动下线桃花牌)';

-- 正在导出表  clover-user.user_pic 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_pic` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_pic` ENABLE KEYS */;

-- 导出  表 clover-user.user_spouse_selection 结构
CREATE TABLE IF NOT EXISTS `user_spouse_selection` (
  `uid` bigint(20) unsigned NOT NULL COMMENT '用户uid',
  `age_bgn` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Ta的年龄上限：18岁-60岁/0-不限',
  `age_end` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Ta的年龄下限：18岁-60岁/0-不限',
  `height_bgn` smallint(6) NOT NULL DEFAULT '0' COMMENT 'Ta的身高(单位cm)上限：140cm - 200cm /0-不限',
  `height_end` smallint(6) NOT NULL DEFAULT '0' COMMENT 'Ta的身高(单位cm)下限：140cm - 200cm /0-不限',
  `figure_list` varchar(255) DEFAULT NULL COMMENT 'Ta的身材(JsonArray转成的字符串)：-1-不限、0-一般、11-瘦长(男)、12-运动员型(男)、13-较胖(男)、14-体格魁梧(男)、15-壮实(男)、21-瘦长(女)、22-苗条(女)、23-高大美丽(女)、24-丰满(女)、25-富线条美(女)',
  `home_province_id_list` varchar(255) DEFAULT NULL COMMENT 'Ta的故乡省份id(JsonArray转成的字符串)',
  `work_province_id_list` varchar(255) DEFAULT NULL COMMENT 'Ta的工作生活地省份id(JsonArray转成的字符串)',
  `marital_status` tinyint(4) DEFAULT NULL COMMENT 'Ta的婚姻状态：0-不限；1-未婚；2-离异；3-丧偶',
  `have_children_list` varchar(50) DEFAULT NULL COMMENT 'Ta的希望Ta有无子女：0-不限、1-没有孩子、2-有孩子且住在一起、3-有孩子偶尔一起住、4-有孩子但不在身边',
  `academic_list` varchar(80) DEFAULT NULL COMMENT 'Ta的学历要求(JsonArray转成的字符串)：0-不限、1-博士、2-硕士、3-本科、4-大专',
  `school_type_list` varchar(80) DEFAULT NULL COMMENT 'Ta的毕业学校类型(JsonArray转成的字符串)：0-不限、1-985，2-211，3-985且211、4-一般全日制大学，5-海外院校',
  `company_type_list` varchar(80) DEFAULT NULL COMMENT 'Ta的所在公司类型(JsonArray转成的字符串)：0-不限、1-央企、2-国企、3-事业单位、4-私企、5-外企、6-其他',
  `industry_type_list` varchar(80) DEFAULT NULL COMMENT 'Ta的所在行业类型(JsonArray转成的字符串)：0-不限、1-计算机/互联网/通信、2-公务员/事业单位、3-教师、4-医生、5-护士、6-空乘人员、7-生产/工艺/制造、8-商业/服务业/个体经营、9-金融/银行/投资/保险、10-文化/广告/传媒、11-娱乐/艺术/表演、12-律师/法务、13-教育/培训/管理咨询、14-建筑/房地产/物业、15-消费零售/贸易/交通物流、16-酒店旅游、17-现代农业、18在校学生、19-其他',
  `rank_type_list` varchar(80) DEFAULT NULL COMMENT 'Ta的职场职级(JsonArray转成的字符串)：0-不限、1-企业主或单位负责人、2-高层管理、3-中层管理、4-普通职员、5-其他',
  `year_income_list` varchar(50) DEFAULT NULL COMMENT 'Ta的年收入(JsonArray转成的字符串)：0-不限；1-10W以下、2-10W-20W、3-20W-30W、4-30W-50W、5-50W-100W、6-100W以上',
  `house_status_list` varchar(50) DEFAULT NULL COMMENT 'Ta的住房情况(JsonArray转成的字符串)：0-不限；1-和家人同住、2-已购房有贷款、3-已购房无贷款、4-租房、5-打算婚后买房、6-住单位宿舍',
  `car_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Ta的购车情况：0-不限；1 - 已购车;2 - 未购车; ',
  `asset_level_list` varchar(50) DEFAULT NULL COMMENT 'Ta的资产水平：0-不限、1-所在城市基本生活水平、2-所在城市小康生活水平、3-所在城市家境优渥',
  `relation_with_parents_list` varchar(50) DEFAULT NULL COMMENT 'Ta的和父母的关系(JsonArray转成的字符串)：0-不限、1-像朋友一样、2-尊重孝顺、3-长伴依赖、4-各自独立',
  `only_child_list` varchar(50) DEFAULT NULL COMMENT '希望Ta是独生子女(JsonArray转成的字符串)：0-不限、1-独生子女、2-有哥哥、3-有姐姐、4-有弟弟、5-有妹妹',
  `marry_plan_list` varchar(50) DEFAULT NULL COMMENT 'Ta的结婚计划(JsonArray转成的字符串)：0-不限、1-愿意闪婚、2-一年内、3-两年内、4-时机成熟就结婚',
  `child_plan_list` varchar(50) DEFAULT NULL COMMENT 'Ta对孩子的计划(JsonArray转成的字符串)：0-不限、1-要一个孩子、2-要多个孩子、3-不想要孩子、4-视情况而定',
  `consume_attitude_list` varchar(50) DEFAULT NULL COMMENT 'Ta对花钱的态度(JsonArray转成的字符串)：0-不限、1-少花多存、2-花一半存一半、3-多花少存、4-月光、5-及时行乐花了再说',
  `faith` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Ta的宗教信仰：0-不限、1-无宗教信仰、2-佛教、3-道教、4-基督教、5-伊斯兰教、6-印度教、7-萨满教、8-其他教派',
  `smok_drink` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Ta的吸烟喝酒态度：0-不限、1-不吸烟不饮酒、2-不吸烟可以饮酒、3-可以吸烟不要饮酒',
  `housework` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Ta对家务活的态度：0-不限、1-家务小能手、2-分工合作、3-不太会但愿意为了对方学习',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户的择偶条件表（对理想对象的要求）';

-- 正在导出表  clover-user.user_spouse_selection 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_spouse_selection` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_spouse_selection` ENABLE KEYS */;


-- 导出 tx-manager 的数据库结构
CREATE DATABASE IF NOT EXISTS `tx-manager` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tx-manager`;

-- 导出  表 tx-manager.hibernate_sequence 结构
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  tx-manager.hibernate_sequence 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` (`next_val`) VALUES
	(7);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

-- 导出  表 tx-manager.t_tx_exception 结构
CREATE TABLE IF NOT EXISTS `t_tx_exception` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(64) DEFAULT NULL,
  `unit_id` varchar(32) DEFAULT NULL,
  `mod_id` varchar(128) DEFAULT NULL,
  `transaction_state` tinyint(4) DEFAULT NULL,
  `registrar` tinyint(4) DEFAULT NULL,
  `ex_state` tinyint(4) DEFAULT NULL COMMENT '0 待处理 1已处理',
  `remark` varchar(10240) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- 正在导出表  tx-manager.t_tx_exception 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `t_tx_exception` DISABLE KEYS */;
INSERT INTO `t_tx_exception` (`id`, `group_id`, `unit_id`, `mod_id`, `transaction_state`, `registrar`, `ex_state`, `remark`, `create_time`) VALUES
	(1, '3ce95234f2f537', '6d2bef961b5cf6e4a975ca3e0e93b86b', 'order-service', 0, 1, 0, NULL, '2019-08-09 17:40:27'),
	(2, '3ce95234f2f537', 'd6d5364ff36433f417ee801c728087f2', 'biz-service', 0, 1, 0, NULL, '2019-08-09 17:40:31'),
	(3, '3ce95234f2f537', '6d2bef961b5cf6e4a975ca3e0e93b86b', 'order-service', 0, 1, 0, NULL, '2019-08-09 17:40:27'),
	(4, '3ce95234f2f537', 'd6d5364ff36433f417ee801c728087f2', 'biz-service', 0, 1, 0, NULL, '2019-08-09 17:40:31'),
	(5, '3ee058abd63537', 'de9d27194cc640105e0e949114980776', 'order-service', 0, 1, 0, NULL, '2019-08-15 20:11:57'),
	(6, '3ee058abd63537', 'de9d27194cc640105e0e949114980776', 'order-service', 0, 1, 0, NULL, '2019-08-15 20:11:58');
/*!40000 ALTER TABLE `t_tx_exception` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
