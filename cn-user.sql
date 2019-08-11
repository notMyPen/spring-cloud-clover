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


-- 导出 cn-user 的数据库结构
CREATE DATABASE IF NOT EXISTS `cn-user` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cn-user`;

-- 导出  表 cn-user.msg_form_id 结构
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

-- 正在导出表  cn-user.msg_form_id 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `msg_form_id` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_form_id` ENABLE KEYS */;

-- 导出  表 cn-user.msg_send_code 结构
CREATE TABLE IF NOT EXISTS `msg_send_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `telephone` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '电话号码',
  `voice_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是语音0.否1.是',
  `code` int(11) DEFAULT NULL COMMENT '发送的验证码',
  `state` bit(1) DEFAULT NULL COMMENT '0 ：失败，1：成功',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='向用户发送的验证码';

-- 正在导出表  cn-user.msg_send_code 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `msg_send_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_send_code` ENABLE KEYS */;

-- 导出  表 cn-user.msg_wechat 结构
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

-- 正在导出表  cn-user.msg_wechat 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `msg_wechat` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_wechat` ENABLE KEYS */;

-- 导出  表 cn-user.user_account 结构
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

-- 正在导出表  cn-user.user_account 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;

-- 导出  表 cn-user.user_account_list 结构
CREATE TABLE IF NOT EXISTS `user_account_list` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
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

-- 正在导出表  cn-user.user_account_list 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_account_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_account_list` ENABLE KEYS */;

-- 导出  表 cn-user.user_basic_info 结构
CREATE TABLE IF NOT EXISTS `user_basic_info` (
  `uid` bigint(20) unsigned NOT NULL COMMENT '用户uid',
  `full_name` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `id_card_no` varchar(50) DEFAULT NULL COMMENT '用户身份证号',
  `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别：0-空；1-男；2-女',
  `wx_account` varchar(50) DEFAULT NULL COMMENT '微信号',
  `birthday` char(10) DEFAULT NULL COMMENT '生日(yyyy-MM-dd格式的字符串)',
  `height` smallint(6) DEFAULT NULL COMMENT '身高(单位cm)',
  `weight` smallint(6) DEFAULT NULL COMMENT '体重(单位kg)',
  `marital_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '婚姻状态：0-未知；1-未婚；2-已婚；3-离异',
  `have_children` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有无子女：0-未知；1-有；2无',
  `now_city` varchar(50) DEFAULT NULL COMMENT '现居住地所在城市，省-市结构(eg：北京-朝阳)',
  `college` varchar(255) DEFAULT NULL COMMENT '毕业院校',
  `academic` tinyint(4) NOT NULL DEFAULT '0' COMMENT '学历：0-空、1-博士、2-硕士、3-本科、4-大专及以下',
  `monthly_income` tinyint(4) NOT NULL DEFAULT '0' COMMENT '月收入：0-空；1 - 2000元以下; 2 - 2000-4000元; 3 - 4000-6000元; 4 - 6000-10000元; 5 - 10000-15000元; 6 - 15000-20000元; 7 - 20000-50000元; 8 - 50000元以上;',
  `house_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '住房情况：0-空；1 - 已购房; 2 - 租房; 3 - 单位宿舍; 4 - 和家人同住; ',
  `car_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '购车情况：0-空；1 - 已购车;2 - 未购车; ',
  `card_num` int(11) NOT NULL DEFAULT '0' COMMENT '券个数',
  `base_credit_pass` bit(1) NOT NULL DEFAULT b'0' COMMENT '基础认证是否全部通过：0-否 1-是',
  `audit_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态:0未提交信息;1待审核;2审核通过(通过后即上线);3被拒绝',
  `offline` bit(1) NOT NULL DEFAULT b'0' COMMENT '该用户是否下线，1下线，0展示',
  `high_quality` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是高质量用户：0-否 1-是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息表';

-- 正在导出表  cn-user.user_basic_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_basic_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_basic_info` ENABLE KEYS */;

-- 导出  表 cn-user.user_credit_manual 结构
CREATE TABLE IF NOT EXISTS `user_credit_manual` (
  `id` bigint(20) unsigned NOT NULL,
  `uid` bigint(20) unsigned NOT NULL,
  `pic_url_list` varchar(1000) NOT NULL COMMENT '用户上传图片(压缩后)，多个图片用分号(;)分割',
  `pic_type` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '图片类型：0-未知；1-收入照片；2-房产照片；3-车产照片',
  `audit_status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '0未提交信息，1待审核，2审核通过，3被拒绝',
  `board_used` bit(1) NOT NULL DEFAULT b'0' COMMENT '图片是否正在被使用(一般使用最新上传的图片)：0-否 1-是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户手动认证时上传的图片';

-- 正在导出表  cn-user.user_credit_manual 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_credit_manual` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_credit_manual` ENABLE KEYS */;

-- 导出  表 cn-user.user_credit_selection 结构
CREATE TABLE IF NOT EXISTS `user_credit_selection` (
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `xuexin_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '学信认证状态',
  `shebao_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '社保认证状态',
  `gjj_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '公积金认证状态',
  `dishonest_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '法院失信认证状态',
  `taobao_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '淘宝认证状态',
  `alipay_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付宝认证状态',
  `jingdong_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '京东认证状态',
  `zhengxin_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '征信数据认证状态',
  `zhima_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '芝麻信用认证状态',
  `marry_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '婚姻认证状态',
  `duotou_lend_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '多头借贷认证状态',
  `house_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '房产认证状态',
  `car_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '车产认证状态',
  `income_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '收入认证状态',
  `job_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '工作认证状态',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- 正在导出表  cn-user.user_credit_selection 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_credit_selection` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_credit_selection` ENABLE KEYS */;

-- 导出  表 cn-user.user_credit_status 结构
CREATE TABLE IF NOT EXISTS `user_credit_status` (
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `face_action_end` bit(1) NOT NULL DEFAULT b'0' COMMENT '人脸认证行为是否结束',
  `face_verify_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '人脸认证状态',
  `face_verify_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '人脸更新时间',
  `face_check_video` varchar(255) DEFAULT NULL COMMENT '人脸识别OSSkey',
  `baseInfo_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '基础信息认证状态',
  `base_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '基础信息更新时间',
  `xuexin_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '学信认证状态',
  `student_status` tinyint(3) DEFAULT '-1' COMMENT '是否在校大学生，1：是， 0：否， -1：未知（用户没填学信）',
  `xuexin_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '学信更新时间',
  `shebao_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '社保认证状态',
  `shebao_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '社保更新时间',
  `gjj_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '公积金认证状态',
  `gjj_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '公积金更新时间',
  `dishonest_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '法院失信认证状态',
  `dishonest_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '法院失信更新时间',
  `taobao_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '淘宝认证状态',
  `taobao_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '淘宝更新时间',
  `alipay_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付宝认证状态',
  `alipay_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '支付宝更新时间',
  `jingdong_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '京东认证状态',
  `jingdong_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '京东更新时间',
  `zhengxin_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '征信数据认证状态',
  `zhengxin_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '征信数据更新时间',
  `zhima_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '芝麻信用认证状态',
  `zhima_month` int(10) unsigned DEFAULT NULL COMMENT '芝麻分月份(yyyy-MM-dd格式的时间转成秒)',
  `marry_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '婚姻认证状态',
  `marry_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '婚姻状态更新时间',
  `duotou_lend_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '多头借贷认证状态',
  `duotou_lend_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '多头借贷更新时间',
  `house_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '房产认证状态',
  `house_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '房产更新时间',
  `car_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '车产认证状态',
  `car_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '车产更新时间',
  `income_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '收入认证状态',
  `income_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '收入更新时间',
  `job_credit_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '工作认证状态',
  `job_upd_tm` int(10) unsigned DEFAULT NULL COMMENT '工作更新时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存用户各项认证状态（状态分类：0.未认证 1.待完善 2.认证中 3.认证成功 4.认证失败 5.已过期）';

-- 正在导出表  cn-user.user_credit_status 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_credit_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_credit_status` ENABLE KEYS */;

-- 导出  表 cn-user.user_detail_info 结构
CREATE TABLE IF NOT EXISTS `user_detail_info` (
  `uid` bigint(20) unsigned NOT NULL COMMENT '用户uid',
  `zodiac` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '属相：0-未知；按顺序1-12分别表示鼠、牛、虎、兔、龙、蛇、马、羊、猴、鸡、狗、猪',
  `constellation` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '星座：0-未知；按顺序1-12依次表示白羊座、金牛座、双子座、巨蟹座、狮子座、处女座、天秤座、天蝎座、射手座、摩羯座、水瓶座、双鱼座',
  `blood_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '血型：0-未知；1-A、2-B、3-O、4-AB',
  `native_place` varchar(50) DEFAULT NULL COMMENT '籍贯',
  `regist_residence` varchar(50) DEFAULT NULL COMMENT '户口所在地',
  `nation` varchar(20) DEFAULT NULL COMMENT '民族',
  `smoking` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0-未知；1-是；2-否',
  `drinking` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0-未知；1-是；2-否',
  `religious_belief` varchar(50) DEFAULT NULL COMMENT '宗教信仰',
  `company` varchar(80) DEFAULT NULL COMMENT '公司(工作单位)',
  `industry` varchar(80) DEFAULT NULL COMMENT '行业',
  `occupation` varchar(80) DEFAULT NULL COMMENT '职业',
  `home_ranking` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '家中排行：0-未知；1-老大；2-老二 (依次)',
  `parental_situation` varchar(255) DEFAULT NULL COMMENT '父母情况',
  `father_job` varchar(80) DEFAULT NULL COMMENT '父亲工作',
  `mother_job` varchar(80) DEFAULT NULL COMMENT '母亲工作',
  `parental_economy` varchar(255) DEFAULT NULL COMMENT '父母经济',
  `parental_health_insurance` varchar(255) DEFAULT NULL COMMENT '父母医保',
  `marry_year` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '何时结婚(年份)：0-未知',
  `want_children` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否要子女：0-未知；1-是；2-否',
  `living_with_parents` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否需要跟父母同住：0-未知；1-是；2-否',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户详细信息表';

-- 正在导出表  cn-user.user_detail_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_detail_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_detail_info` ENABLE KEYS */;

-- 导出  表 cn-user.user_dishonest_cases 结构
CREATE TABLE IF NOT EXISTS `user_dishonest_cases` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户高发失信案件';

-- 正在导出表  cn-user.user_dishonest_cases 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_dishonest_cases` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_dishonest_cases` ENABLE KEYS */;

-- 导出  表 cn-user.user_dt_lend_detail 结构
CREATE TABLE IF NOT EXISTS `user_dt_lend_detail` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户多头借贷明细';

-- 正在导出表  cn-user.user_dt_lend_detail 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_dt_lend_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_dt_lend_detail` ENABLE KEYS */;

-- 导出  表 cn-user.user_dt_lend_summary 结构
CREATE TABLE IF NOT EXISTS `user_dt_lend_summary` (
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

-- 正在导出表  cn-user.user_dt_lend_summary 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_dt_lend_summary` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_dt_lend_summary` ENABLE KEYS */;

-- 导出  表 cn-user.user_login_log 结构
CREATE TABLE IF NOT EXISTS `user_login_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) unsigned NOT NULL,
  `give_node` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是赠送节点(用于连续登陆5天赠送券)：0-否；1-是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录历史';

-- 正在导出表  cn-user.user_login_log 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_login_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_login_log` ENABLE KEYS */;

-- 导出  表 cn-user.user_passport 结构
CREATE TABLE IF NOT EXISTS `user_passport` (
  `uid` bigint(20) NOT NULL,
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `salt` varchar(50) DEFAULT NULL COMMENT '登录密码用的盐',
  `regist_tel` varchar(15) NOT NULL DEFAULT '0' COMMENT '登陆手机号',
  `open_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '微信公众号openId',
  `mini_open_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '微信小程序openId',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '微信昵称',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像(用户未上传之前默认将微信头像上传oss)',
  `subscribe` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否关注了公众号：0-否 1-是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户最后登录时间(由于该表中可更新的字段几乎没有)',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `regist_tel` (`regist_tel`),
  UNIQUE KEY `open_id` (`open_id`),
  UNIQUE KEY `mini_open_id` (`mini_open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户同行证(同时支持手机号/密码登录、手机号/短信验证码登录、微信公众号自动登录、微信小程序自动登录)';

-- 正在导出表  cn-user.user_passport 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_passport` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_passport` ENABLE KEYS */;

-- 导出  表 cn-user.user_spouse_selection 结构
CREATE TABLE IF NOT EXISTS `user_spouse_selection` (
  `uid` bigint(20) unsigned NOT NULL COMMENT '用户uid',
  `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别：0-不限；1-男；2-女',
  `birthday` char(10) DEFAULT NULL COMMENT '生日(yyyy-MM-dd格式的字符串)',
  `height` tinyint(4) DEFAULT NULL COMMENT '身高(单位cm)',
  `weight` tinyint(4) DEFAULT NULL COMMENT '体重(单位kg)',
  `marital_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '婚姻状态：0-不限；1-未婚；2-已婚；3-离异',
  `have_children` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有无子女：0-不限；1-有；2无',
  `now_province_id` int(11) DEFAULT NULL COMMENT '现居住地所在城市-省id',
  `now_city_id` int(11) DEFAULT NULL COMMENT '现居住地所在城市-市id',
  `academic` tinyint(4) NOT NULL DEFAULT '0' COMMENT '学历：0-不限、1-博士、2-硕士、3-本科、4-大专及以下',
  `monthly_income` tinyint(4) NOT NULL DEFAULT '0' COMMENT '月收入：0-不限；1 - 2000元以下; 2 - 2000-4000元; 3 - 4000-6000元; 4 - 6000-10000元; 5 - 10000-15000元; 6 - 15000-20000元; 7 - 20000-50000元; 8 - 50000元以上;',
  `house_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '住房情况：0-不限；1 - 已购房; 2 - 租房; 3 - 单位宿舍; 4 - 和家人同住; ',
  `car_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '购车情况：0-不限；1 - 已购车;2 - 未购车; ',
  `zodiac` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '属相：0-不限；按顺序1-12分别表示鼠、牛、虎、兔、龙、蛇、马、羊、猴、鸡、狗、猪',
  `constellation` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '星座：0-不限；按顺序1-12依次表示白羊座、金牛座、双子座、巨蟹座、狮子座、处女座、天秤座、天蝎座、射手座、摩羯座、水瓶座、双鱼座',
  `blood_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '血型：0-不限；1-A、2-B、3-O、4-AB',
  `native_place` varchar(50) DEFAULT NULL COMMENT '籍贯',
  `regist_residence` varchar(50) DEFAULT NULL COMMENT '户口所在地',
  `nation` varchar(20) DEFAULT NULL COMMENT '民族',
  `smoking` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0-不限；1-是；2-否',
  `drinking` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0-不限；1-是；2-否',
  `religious_belief` varchar(50) DEFAULT NULL COMMENT '宗教信仰',
  `industry` varchar(80) DEFAULT NULL COMMENT '行业',
  `occupation` varchar(80) DEFAULT NULL COMMENT '职业',
  `home_ranking` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '家中排行：0-不限；1-老大；2-老二 (依次)',
  `parental_situation` varchar(255) DEFAULT NULL COMMENT '父母情况',
  `father_job` varchar(80) DEFAULT NULL COMMENT '父亲工作',
  `mother_job` varchar(80) DEFAULT NULL COMMENT '母亲工作',
  `parental_economy` varchar(255) DEFAULT NULL COMMENT '父母经济',
  `parental_health_insurance` varchar(255) DEFAULT NULL COMMENT '父母医保',
  `marry_year` smallint(5) unsigned DEFAULT NULL COMMENT '何时结婚(年份)',
  `want_children` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否要子女：0-不限；1-是；2-否',
  `living_with_parents` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否需要跟父母同住：0-不限；1-是；2-否',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户的择偶条件表';

-- 正在导出表  cn-user.user_spouse_selection 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_spouse_selection` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_spouse_selection` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
