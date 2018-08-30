/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.7.17-log : Database - mmns_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mmns_db` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

/*Table structure for table `tb_activity` */

DROP TABLE IF EXISTS `tb_activity`;

CREATE TABLE `tb_activity` (
  `activity_id` varchar(32) NOT NULL COMMENT '活动表主键',
  PRIMARY KEY (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_activity` */

/*Table structure for table `tb_address` */

DROP TABLE IF EXISTS `tb_address`;

CREATE TABLE `tb_address` (
  `address_id` varchar(32) NOT NULL COMMENT '收货地址主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `address_name` varchar(32) NOT NULL COMMENT '收货人姓名',
  `address_phone` varchar(13) NOT NULL COMMENT '收货人手机',
  `address_province` varchar(16) NOT NULL COMMENT '省',
  `address_city` varchar(16) NOT NULL COMMENT '市',
  `address_area` varchar(64) NOT NULL COMMENT '区',
  `address_row` varchar(64) NOT NULL COMMENT '街道',
  `postcode` varchar(6) NOT NULL COMMENT '邮编',
  `address_detail` varchar(128) NOT NULL COMMENT '详细地址',
  `personinfo_id` varchar(32) DEFAULT NULL COMMENT '外键；个人信息主键',
  `is_default` int(2) NOT NULL COMMENT '是否默认地址：1是；0否',
  PRIMARY KEY (`address_id`),
  KEY `id` (`id`),
  KEY `idx_personinfo_id` (`personinfo_id`),
  KEY `idx_is_default` (`is_default`),
  CONSTRAINT `tb_address_ibfk_1` FOREIGN KEY (`personinfo_id`) REFERENCES `tb_personinfo` (`personinfo_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

/*Data for the table `tb_address` */

insert  into `tb_address`(`address_id`,`id`,`address_name`,`address_phone`,`address_province`,`address_city`,`address_area`,`address_row`,`postcode`,`address_detail`,`personinfo_id`,`is_default`) values 
('033fbc8d6bc8464a9261b735e49e90d4',55,'了解','18955285977','湖北省','武汉市','江岸区','了咯了具体','233400','螺母我','2a922aff15fa41ce8debbde99577eaec',1),
('1fe3c446f9514a5cbf2ae7e50c40f1de',53,'就看到肯定','18963253146','北京市','北京市','东城区','上计算机课','123456','大家觉得看什么辛苦','90f1ceab1ced47bc949077f982ce0774',1),
('637e6766353a4e86a1d3abbbbbe8b9c3',54,'哪不舒服吧都不是','15632458962','北京市','北京市','东城区','继续开心','123456','继续计时开始看看书','90f1ceab1ced47bc949077f982ce0774',0),
('c70a96265d2849d9b5afee98b1cc67bd',56,'改一下','18955284966','北京市','北京市','东城区','希望可以','233400','嘻嘻嘻嘻','2a922aff15fa41ce8debbde99577eaec',0);

/*Table structure for table `tb_banner_category` */

DROP TABLE IF EXISTS `tb_banner_category`;

CREATE TABLE `tb_banner_category` (
  `bannercate_id` varchar(32) NOT NULL COMMENT '轮播图主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `bannercate_name` varchar(32) NOT NULL COMMENT '种类名字',
  PRIMARY KEY (`bannercate_id`),
  UNIQUE KEY `bannercate_name` (`bannercate_name`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_banner_category` */

insert  into `tb_banner_category`(`bannercate_id`,`id`,`bannercate_name`) values 
('23a28fbfc47b4f1d8eca5ee6bce3ca6f',2,'活动'),
('43aa684775e24daf97f0aa99ff6e7f99',1,'商品');

/*Table structure for table `tb_combine` */

DROP TABLE IF EXISTS `tb_combine`;

CREATE TABLE `tb_combine` (
  `combine_id` varchar(32) NOT NULL COMMENT '组合类别id',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `combine_order` int(2) NOT NULL COMMENT '编号：角色编号+道具类别编号+道具编号+~~~（道具类别顺序）',
  `combine_url` varchar(200) NOT NULL COMMENT '链接',
  PRIMARY KEY (`combine_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_combine` */

/*Table structure for table `tb_combo` */

DROP TABLE IF EXISTS `tb_combo`;

CREATE TABLE `tb_combo` (
  `combo_id` varchar(32) NOT NULL COMMENT '商品套餐表,与product主键同步',
  `aproduct_id` varchar(32) DEFAULT NULL COMMENT '商品a主键',
  `bproduct_id` varchar(32) DEFAULT NULL COMMENT '商品b主键',
  `cproduct_id` varchar(32) DEFAULT NULL COMMENT '商品c主键',
  `free_id` varchar(32) DEFAULT NULL COMMENT '赠送物品id',
  `combo_name` varchar(32) NOT NULL COMMENT '套餐名称',
  PRIMARY KEY (`combo_id`),
  KEY `product_id` (`aproduct_id`),
  KEY `bproduct_id` (`bproduct_id`),
  KEY `cproduct_id` (`cproduct_id`),
  CONSTRAINT `tb_combo_ibfk_1` FOREIGN KEY (`aproduct_id`) REFERENCES `tb_product` (`product_id`) ON DELETE SET NULL,
  CONSTRAINT `tb_combo_ibfk_2` FOREIGN KEY (`bproduct_id`) REFERENCES `tb_product` (`product_id`) ON DELETE SET NULL,
  CONSTRAINT `tb_combo_ibfk_3` FOREIGN KEY (`cproduct_id`) REFERENCES `tb_product` (`product_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_combo` */

insert  into `tb_combo`(`combo_id`,`aproduct_id`,`bproduct_id`,`cproduct_id`,`free_id`,`combo_name`) values 
('7ef9d05a616c4d4a9e2009a9e84f7060','1e78de30aab545c897a9ad32d0bfcfc7','7a8693d73b4c4ac7b45bbc69dfeda0d2',NULL,NULL,'水果');

/*Table structure for table `tb_crashcategory` */

DROP TABLE IF EXISTS `tb_crashcategory`;

CREATE TABLE `tb_crashcategory` (
  `crashcate_id` varchar(32) NOT NULL COMMENT '碰撞种类主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `crashcate_name` varchar(16) NOT NULL COMMENT '碰撞种类名称',
  `crashcate_url` varchar(200) NOT NULL COMMENT '种类链接',
  `crashcate-integral` int(2) NOT NULL COMMENT '对应积分',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `lastedit_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`crashcate_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_crashcategory` */

/*Table structure for table `tb_dzp_result` */

DROP TABLE IF EXISTS `tb_dzp_result`;

CREATE TABLE `tb_dzp_result` (
  `dr_id` varchar(32) NOT NULL COMMENT '大转盘结算表',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `openid` varchar(32) NOT NULL COMMENT 'openId',
  `reward_id` varchar(32) DEFAULT NULL COMMENT '奖品表id',
  PRIMARY KEY (`dr_id`),
  KEY `id` (`id`),
  KEY `reward_id` (`reward_id`),
  CONSTRAINT `tb_dzp_result_ibfk_1` FOREIGN KEY (`reward_id`) REFERENCES `tb_reward` (`reward_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_dzp_result` */

/*Table structure for table `tb_free` */

DROP TABLE IF EXISTS `tb_free`;

CREATE TABLE `tb_free` (
  `free_id` varchar(32) NOT NULL COMMENT '赠送物品',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `free_name` varchar(32) NOT NULL COMMENT '名称',
  `free_mobi` int(2) NOT NULL COMMENT '价值',
  `free_desc` varchar(128) DEFAULT NULL COMMENT '描述',
  `free_num` int(2) NOT NULL COMMENT '数量',
  PRIMARY KEY (`free_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_free` */

/*Table structure for table `tb_gameimg` */

DROP TABLE IF EXISTS `tb_gameimg`;

CREATE TABLE `tb_gameimg` (
  `gameimg_id` varchar(32) NOT NULL COMMENT '游戏界面图资源表主键',
  `gameimg_url` varchar(200) NOT NULL COMMENT '链接',
  `priority` int(2) NOT NULL COMMENT '权重',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `enable_status` int(2) NOT NULL DEFAULT '1' COMMENT '0不可用1可用',
  PRIMARY KEY (`gameimg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_gameimg` */

insert  into `tb_gameimg`(`gameimg_id`,`gameimg_url`,`priority`,`create_time`,`enable_status`) values 
('0700b99954e54fd1b564d1ff245a0906','/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',1,'2017-12-05 14:32:55',1),
('1f948c710fcb4671b9529559ef30f7c8','/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',2,'2017-12-12 14:33:18',1),
('8799abaf231c43fca069620bbd4e900a','/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',3,'2017-12-21 14:33:38',1);

/*Table structure for table `tb_lexicon` */

DROP TABLE IF EXISTS `tb_lexicon`;

CREATE TABLE `tb_lexicon` (
  `lexicon_id` varchar(32) NOT NULL COMMENT '词库主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `normal_entry` varchar(16) NOT NULL COMMENT '普通词语',
  `lucky_entry` varchar(16) NOT NULL COMMENT '幸运词语',
  `lexicon_order` int(2) NOT NULL COMMENT '编号',
  PRIMARY KEY (`lexicon_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_lexicon` */

/*Table structure for table `tb_main_banner` */

DROP TABLE IF EXISTS `tb_main_banner`;

CREATE TABLE `tb_main_banner` (
  `banner_id` varchar(32) NOT NULL COMMENT '主页轮播图主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `banner_url` varchar(200) NOT NULL COMMENT '轮播图地址',
  `banner_redirectid` varchar(32) DEFAULT NULL COMMENT '跳转详情主键，商品还是活动那个',
  `priority` int(2) NOT NULL COMMENT '权重',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `lastedit_time` datetime NOT NULL COMMENT '修改时间',
  `enable_status` int(2) NOT NULL DEFAULT '1' COMMENT '状态：0不可用，1可用',
  `bannercate_id` varchar(32) DEFAULT NULL COMMENT '轮播图种类',
  PRIMARY KEY (`banner_id`),
  KEY `banner_id` (`id`),
  KEY `tb_main_banner_ibfk_1` (`bannercate_id`),
  CONSTRAINT `tb_main_banner_ibfk_1` FOREIGN KEY (`bannercate_id`) REFERENCES `tb_banner_category` (`bannercate_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `tb_main_banner` */

insert  into `tb_main_banner`(`banner_id`,`id`,`banner_url`,`banner_redirectid`,`priority`,`create_time`,`lastedit_time`,`enable_status`,`bannercate_id`) values 
('307dff7042004666b9fd4d70d539c988',2,'/upload/872eea50fbcda99824787518f387e5db.jpg','7a8693d73b4c4ac7b45bbc69dfeda0d2',2,'2017-12-14 14:35:07','2017-12-15 14:35:10',1,'43aa684775e24daf97f0aa99ff6e7f99'),
('4b212f376d7b46179c503c775d285db1',3,'/upload/179ed76f61f292d058e77adcfa84f470.jpg','7a8693d73b4c4ac7b45bbc69dfeda0d2',3,'2017-12-07 14:35:37','2017-12-13 14:35:39',1,'43aa684775e24daf97f0aa99ff6e7f99'),
('99ffcb2f5b7542bbb5711026214ff780',1,'/upload/8707bef6ce22848dc4e5ffdc61102748.jpg','7a8693d73b4c4ac7b45bbc69dfeda0d2',1,'2017-12-01 14:34:37','2017-12-14 14:34:38',1,'43aa684775e24daf97f0aa99ff6e7f99');

/*Table structure for table `tb_mmuser` */

DROP TABLE IF EXISTS `tb_mmuser`;

CREATE TABLE `tb_mmuser` (
  `mmuser_id` varchar(32) NOT NULL COMMENT '膜面游戏用户表主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `mmuser_headimg` varchar(200) NOT NULL COMMENT '拍照照片路径',
  `mmuser_role` int(2) NOT NULL COMMENT '0普通角色1为幸运角色',
  `mmuser_ticket` int(2) NOT NULL DEFAULT '0' COMMENT '获得票数',
  `mmuser_good` int(2) NOT NULL DEFAULT '0' COMMENT '获得赞数',
  `mmuser_status` int(2) NOT NULL DEFAULT '1' COMMENT '发言状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `personinfo_id` varchar(32) DEFAULT NULL COMMENT '个人信息表主键',
  PRIMARY KEY (`mmuser_id`),
  KEY `id` (`id`),
  KEY `personinfo_id` (`personinfo_id`),
  CONSTRAINT `tb_mmuser_ibfk_1` FOREIGN KEY (`personinfo_id`) REFERENCES `tb_personinfo` (`personinfo_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_mmuser` */

/*Table structure for table `tb_order` */

DROP TABLE IF EXISTS `tb_order`;

CREATE TABLE `tb_order` (
  `order_id` varchar(32) NOT NULL COMMENT '微信支付订单主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(128) NOT NULL COMMENT '支付名称，默认微信名',
  `openid` varchar(28) NOT NULL COMMENT '支付者openid',
  `ip` varchar(15) NOT NULL COMMENT '支付者ip',
  `paymoney` int(2) NOT NULL COMMENT '支付金额，以元计',
  `order_num` varchar(32) NOT NULL COMMENT '订单号',
  `accepter` varchar(128) NOT NULL COMMENT '收款方',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `finish_time` datetime NOT NULL COMMENT '更新时间',
  `enable_status` int(2) NOT NULL COMMENT '状态',
  `paybody` varchar(500) NOT NULL COMMENT '描述',
  `personinfo_id` varchar(32) DEFAULT NULL COMMENT '用户表id',
  PRIMARY KEY (`order_id`),
  KEY `id` (`id`),
  KEY `personinfo_id` (`personinfo_id`),
  CONSTRAINT `tb_order_ibfk_1` FOREIGN KEY (`personinfo_id`) REFERENCES `tb_personinfo` (`personinfo_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_order` */

/*Table structure for table `tb_order_record` */

DROP TABLE IF EXISTS `tb_order_record`;

CREATE TABLE `tb_order_record` (
  `orderrecord_id` varchar(32) NOT NULL COMMENT '订单记录表主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `personinfo_id` varchar(32) DEFAULT NULL COMMENT '外键：个人信息主键',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `money` int(2) NOT NULL COMMENT '金额',
  `orderrecord_desc` varchar(500) NOT NULL COMMENT '描述：商品*数量+商品*数量~~~',
  PRIMARY KEY (`orderrecord_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_order_record` */

/*Table structure for table `tb_personinfo` */

DROP TABLE IF EXISTS `tb_personinfo`;

CREATE TABLE `tb_personinfo` (
  `personinfo_id` varchar(32) NOT NULL COMMENT '个人信息表主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `mobi` int(2) NOT NULL COMMENT '个人膜币总数',
  `iage` int(2) NOT NULL DEFAULT '1' COMMENT '瑞莎龄，默认0',
  `power` int(2) NOT NULL COMMENT '体力值',
  `gameintegral` int(2) NOT NULL DEFAULT '0' COMMENT '游戏积分',
  `shopintegral` int(2) NOT NULL DEFAULT '0' COMMENT '商城积分',
  `chance_qmm` int(2) NOT NULL COMMENT '抢面膜',
  `chance_dzp` int(2) NOT NULL COMMENT '大转盘',
  PRIMARY KEY (`personinfo_id`),
  KEY `personinfo_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8;

/*Data for the table `tb_personinfo` */

insert  into `tb_personinfo`(`personinfo_id`,`id`,`mobi`,`iage`,`power`,`gameintegral`,`shopintegral`,`chance_qmm`,`chance_dzp`) values 
('2a922aff15fa41ce8debbde99577eaec',225,0,1,3,0,0,3,3),
('90f1ceab1ced47bc949077f982ce0774',176,0,1,3,0,0,3,3),
('999e5959c13a4acb856f02859ea8e418',226,0,1,600,0,0,3,3);

/*Table structure for table `tb_possessproper` */

DROP TABLE IF EXISTS `tb_possessproper`;

CREATE TABLE `tb_possessproper` (
  `possessproper_id` varchar(32) NOT NULL COMMENT '拥有道具表',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proper_id` varchar(32) DEFAULT NULL COMMENT '外键：道具表主键',
  `personinfo_id` varchar(32) DEFAULT NULL COMMENT '外键：个人信息主键',
  `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '1默认 0否',
  `propercate_id` varchar(32) DEFAULT NULL COMMENT '道具种类',
  PRIMARY KEY (`possessproper_id`),
  KEY `id` (`id`),
  KEY `personinfo_id` (`personinfo_id`),
  KEY `proper_id` (`proper_id`),
  CONSTRAINT `tb_possessproper_ibfk_1` FOREIGN KEY (`personinfo_id`) REFERENCES `tb_personinfo` (`personinfo_id`) ON DELETE SET NULL,
  CONSTRAINT `tb_possessproper_ibfk_2` FOREIGN KEY (`proper_id`) REFERENCES `tb_proper` (`proper_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_possessproper` */

/*Table structure for table `tb_possessrole` */

DROP TABLE IF EXISTS `tb_possessrole`;

CREATE TABLE `tb_possessrole` (
  `possessrole_id` varchar(32) NOT NULL COMMENT '已购买角色主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` varchar(32) DEFAULT NULL COMMENT '外键：角色表主键',
  `personinfo_id` varchar(32) DEFAULT NULL COMMENT '外键：个人信息主键',
  `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '1：默认角色 0：不是',
  PRIMARY KEY (`possessrole_id`),
  KEY `id` (`id`),
  KEY `role_id` (`role_id`),
  KEY `personinfo_id` (`personinfo_id`),
  CONSTRAINT `tb_possessrole_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`role_id`) ON DELETE SET NULL,
  CONSTRAINT `tb_possessrole_ibfk_2` FOREIGN KEY (`personinfo_id`) REFERENCES `tb_personinfo` (`personinfo_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_possessrole` */

/*Table structure for table `tb_ppmanage` */

DROP TABLE IF EXISTS `tb_ppmanage`;

CREATE TABLE `tb_ppmanage` (
  `ppmanage_id` varchar(32) NOT NULL COMMENT '商品参数组合表主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ppmanage_mobi` int(2) NOT NULL COMMENT '价格',
  `ppmanage_num` int(2) NOT NULL COMMENT '数量',
  `product_id` varchar(32) DEFAULT NULL COMMENT '商品主键',
  `ppmanage_integral` int(2) NOT NULL COMMENT '对应积分',
  `pp_unique` varchar(32) NOT NULL COMMENT 'name1+value1+name2+value2',
  PRIMARY KEY (`ppmanage_id`),
  KEY `product_id` (`product_id`),
  KEY `id` (`id`),
  CONSTRAINT `tb_ppmanage_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_ppmanage` */

insert  into `tb_ppmanage`(`ppmanage_id`,`id`,`ppmanage_mobi`,`ppmanage_num`,`product_id`,`ppmanage_integral`,`pp_unique`) values 
('11b803170a1247bf9c99ab840db51fae',1,20,169,'6bfe568f04bc40138e1e74b42f7b9bbd',200,'112335'),
('34ba8adab5fa489a81d007c2542e5f5b',2,22,267,'6bfe568f04bc40138e1e74b42f7b9bbd',300,'182435');

/*Table structure for table `tb_product` */

DROP TABLE IF EXISTS `tb_product`;

CREATE TABLE `tb_product` (
  `product_id` varchar(32) NOT NULL COMMENT '产品主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `product_name` varchar(32) NOT NULL COMMENT '产品名称',
  `normal_mobi` int(2) NOT NULL COMMENT '产品正常价',
  `promotion_mobi` int(2) DEFAULT NULL COMMENT '产品折扣价',
  `is_boom` int(2) NOT NULL DEFAULT '0' COMMENT '产品是否爆款项0；否1：是',
  `product_integral` int(2) NOT NULL COMMENT '产品对应积分',
  `product_img` varchar(200) NOT NULL COMMENT '产品缩略图地址',
  `enable_status` int(2) NOT NULL DEFAULT '1' COMMENT '产品状态：1可用0不可用',
  `priority` int(2) NOT NULL COMMENT '权重',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `lastedit_time` datetime NOT NULL COMMENT '更新时间',
  `procate_id` varchar(32) DEFAULT NULL COMMENT '外键：产品种类主键',
  `product_intro` varchar(500) DEFAULT NULL COMMENT '商品简介',
  `prodesc_id` varchar(32) DEFAULT NULL COMMENT '商品详情介绍表id',
  `boom_deadline` datetime DEFAULT NULL COMMENT '爆款商品截至时间',
  `prorep_id` varchar(32) DEFAULT NULL COMMENT '库存',
  `is_combo` int(2) NOT NULL COMMENT '是否为套餐选项0否1是',
  `is_free` varchar(32) DEFAULT NULL COMMENT '赠品主键',
  PRIMARY KEY (`product_id`),
  KEY `idx_id` (`id`),
  KEY `idx_is_boom` (`is_boom`),
  KEY `idx_procate_id` (`procate_id`),
  KEY `prodesc_id` (`prodesc_id`),
  KEY `prorep_id` (`prorep_id`),
  CONSTRAINT `tb_product_ibfk_1` FOREIGN KEY (`prodesc_id`) REFERENCES `tb_product_desc` (`prodesc_id`) ON DELETE SET NULL,
  CONSTRAINT `tb_product_ibfk_2` FOREIGN KEY (`prodesc_id`) REFERENCES `tb_product_desc` (`prodesc_id`) ON DELETE SET NULL,
  CONSTRAINT `tb_product_ibfk_3` FOREIGN KEY (`prorep_id`) REFERENCES `tb_product_repertory` (`prorep_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `tb_product` */

insert  into `tb_product`(`product_id`,`id`,`product_name`,`normal_mobi`,`promotion_mobi`,`is_boom`,`product_integral`,`product_img`,`enable_status`,`priority`,`create_time`,`lastedit_time`,`procate_id`,`product_intro`,`prodesc_id`,`boom_deadline`,`prorep_id`,`is_combo`,`is_free`) values 
('1e78de30aab545c897a9ad32d0bfcfc7',2,'苹果',100,80,1,100,'/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',1,1,'2017-12-12 15:24:47','2017-12-07 15:24:45','2d89fbb4c8e24d5ab0c9ee3b156adf1c','苹果天','9478bd2333304cd5abca53b90245d16e','2017-12-14 15:24:30','fce12e3ff9d049478126bafa39f140c4',0,'0'),
('6bfe568f04bc40138e1e74b42f7b9bbd',4,'备长炭面膜',199,189,1,199,'/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',1,3,'2017-12-12 15:29:43','2017-12-15 15:29:47','8ef5f4e5e3c945d8aa02216a05a3eb6d','吸附污垢','9478bd2333304cd5abca53b90245d16e','2017-12-20 15:30:11','fce12e3ff9d049478126bafa39f140c4',0,'0'),
('7a8693d73b4c4ac7b45bbc69dfeda0d2',5,'葡萄',22,NULL,0,22,'/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',1,4,'2017-12-14 15:31:19','2017-12-30 15:31:23','2d89fbb4c8e24d5ab0c9ee3b156adf1c','酸甜','9478bd2333304cd5abca53b90245d16e',NULL,'fce12e3ff9d049478126bafa39f140c4',0,'0'),
('7ef9d05a616c4d4a9e2009a9e84f7060',6,'水果',120,110,0,120,'/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',1,5,'2017-12-16 15:49:33','2017-12-23 15:49:36','8db302f601824e26be2dfe18b8f8991d','水果组合','9478bd2333304cd5abca53b90245d16e',NULL,'fce12e3ff9d049478126bafa39f140c4',1,'0'),
('9d6ee6d68494407891920cd079ec8f44',3,'玻尿酸面膜',99,89,1,99,'/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',1,2,'2017-12-06 15:27:52','2017-12-16 15:27:54','8ef5f4e5e3c945d8aa02216a05a3eb6d','补充水分','9478bd2333304cd5abca53b90245d16e','2017-12-19 15:28:14','fce12e3ff9d049478126bafa39f140c4',0,'0');

/*Table structure for table `tb_product_banner` */

DROP TABLE IF EXISTS `tb_product_banner`;

CREATE TABLE `tb_product_banner` (
  `probanner_id` varchar(32) NOT NULL COMMENT '商品轮播图主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `probanner_url` varchar(200) NOT NULL COMMENT '轮播图地址',
  `priority` int(2) NOT NULL COMMENT '权重',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `lastedit_time` datetime NOT NULL COMMENT '更新时间',
  `enable_status` int(2) NOT NULL DEFAULT '1' COMMENT '状态；0不可用1：可用',
  `product_id` varchar(32) DEFAULT NULL COMMENT '外键：product表主键',
  PRIMARY KEY (`probanner_id`),
  KEY `id` (`id`),
  KEY `idx_enable_status` (`enable_status`),
  KEY `idx_product_id` (`product_id`),
  CONSTRAINT `fk_product_banner_product_id` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_product_banner` */

insert  into `tb_product_banner`(`probanner_id`,`id`,`probanner_url`,`priority`,`create_time`,`lastedit_time`,`enable_status`,`product_id`) values 
('4c4fbaeb0fae462989c186e1e8baf935',2,'/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',2,'2017-12-07 15:41:14','2017-12-08 15:41:16',1,'6bfe568f04bc40138e1e74b42f7b9bbd'),
('d158330840654407b05a02b839424fd8',1,'/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',1,'2017-12-21 15:40:36','2017-12-29 15:40:38',1,'6bfe568f04bc40138e1e74b42f7b9bbd');

/*Table structure for table `tb_product_category` */

DROP TABLE IF EXISTS `tb_product_category`;

CREATE TABLE `tb_product_category` (
  `procate_id` varchar(32) NOT NULL COMMENT '产品类别主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '产品类别自增id',
  `procate_name` varchar(32) NOT NULL COMMENT '产品类别名称',
  `priority` int(2) NOT NULL COMMENT '权重',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `lastedit_time` datetime NOT NULL COMMENT '更新时间',
  `enable_status` int(2) NOT NULL DEFAULT '1' COMMENT '状态1可用，0不可用',
  `procate_num` int(2) NOT NULL COMMENT '种类编号',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级类别主键',
  PRIMARY KEY (`procate_id`),
  KEY `procate_id` (`id`),
  KEY `enableStatus` (`enable_status`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `tb_product_category_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `tb_product_category` (`procate_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `tb_product_category` */

insert  into `tb_product_category`(`procate_id`,`id`,`procate_name`,`priority`,`create_time`,`lastedit_time`,`enable_status`,`procate_num`,`parent_id`) values 
('155dc96507284317b310329b4aa4574f',2,'食品',2,'2017-12-06 14:37:59','2017-12-07 14:38:01',1,2000,NULL),
('2d89fbb4c8e24d5ab0c9ee3b156adf1c',3,'洗发露',3,'2017-12-01 14:38:36','2017-12-02 14:39:00',1,1000,'a267ff8dd19648b4a158806fd71e6e14'),
('8db302f601824e26be2dfe18b8f8991d',5,'面包',5,'2017-12-06 14:40:19','2017-12-21 14:40:21',1,1000,'155dc96507284317b310329b4aa4574f'),
('8ef5f4e5e3c945d8aa02216a05a3eb6d',4,'面膜',4,'2017-12-02 14:39:31','2017-12-03 14:39:34',1,1000,'a267ff8dd19648b4a158806fd71e6e14'),
('a267ff8dd19648b4a158806fd71e6e14',1,'化妆品',1,'2017-12-01 14:37:11','2017-12-02 14:37:16',1,2000,NULL),
('b67a2035eb434994bb6062f538aeb6c7',6,'牛奶',6,'2017-12-08 14:40:45','2017-12-15 14:40:48',1,1000,'155dc96507284317b310329b4aa4574f');

/*Table structure for table `tb_product_desc` */

DROP TABLE IF EXISTS `tb_product_desc`;

CREATE TABLE `tb_product_desc` (
  `prodesc_id` varchar(32) NOT NULL COMMENT '产品描述主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `prodesc_text` text COMMENT '产品描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`prodesc_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_product_desc` */

insert  into `tb_product_desc`(`prodesc_id`,`id`,`prodesc_text`,`create_time`) values 
('9478bd2333304cd5abca53b90245d16e',1,'撒大苏打撒旦撒啊实打实的阿斯顿阿斯顿撒旦撒撒','2017-12-06 15:23:40');

/*Table structure for table `tb_product_img` */

DROP TABLE IF EXISTS `tb_product_img`;

CREATE TABLE `tb_product_img` (
  `proimg_id` varchar(32) NOT NULL COMMENT '商品详情图主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `proimg_url` varchar(200) NOT NULL COMMENT '详情图链接',
  `priority` int(2) NOT NULL COMMENT '权重',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `enable_status` int(1) NOT NULL COMMENT '状态：0不可用1：可用',
  `product_id` varchar(32) DEFAULT NULL COMMENT '外键：product主键',
  PRIMARY KEY (`proimg_id`),
  KEY `id` (`id`),
  KEY `idx_enable_status` (`enable_status`),
  KEY `idx_product_id` (`product_id`),
  CONSTRAINT `tb_product_img_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_product_img` */

insert  into `tb_product_img`(`proimg_id`,`id`,`proimg_url`,`priority`,`create_time`,`enable_status`,`product_id`) values 
('0a12b08d6c4d4291b4e72d8a4d920758',2,'/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',2,'2017-12-06 15:16:47',1,'6bfe568f04bc40138e1e74b42f7b9bbd'),
('0e2b737cbfa04ac8aa4487f8aae9edaa',1,'/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',1,'2017-12-06 15:16:15',1,'6bfe568f04bc40138e1e74b42f7b9bbd');

/*Table structure for table `tb_product_order` */

DROP TABLE IF EXISTS `tb_product_order`;

CREATE TABLE `tb_product_order` (
  `proorder_id` varchar(32) NOT NULL COMMENT '商品订单主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `accepter` varchar(128) NOT NULL COMMENT '收款方',
  `proorder_number` varchar(64) NOT NULL COMMENT '订单编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `proorder_desc` varchar(300) NOT NULL COMMENT '订单描述 150字',
  `proorder_msg` varchar(300) DEFAULT NULL COMMENT '买家留言 150字',
  `totalmobi` int(2) NOT NULL COMMENT '价格总计，单位：膜币',
  `express_fee` int(2) NOT NULL COMMENT '快递费，转换膜币',
  `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '状态：4已过期 0待支付，1待发货，2待收货，3已完成',
  `personinfo_id` varchar(32) DEFAULT NULL COMMENT '外键：个人信息主键',
  `lastedit_time` datetime NOT NULL COMMENT '更新时间',
  `address_id` varchar(32) DEFAULT NULL COMMENT '地址',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `put_time` datetime DEFAULT NULL COMMENT '发货时间',
  `product_mobi` int(2) NOT NULL COMMENT '商品价格',
  `product_integral` int(2) NOT NULL COMMENT '总积分',
  PRIMARY KEY (`proorder_id`),
  KEY `id` (`id`),
  KEY `address_id` (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

/*Data for the table `tb_product_order` */

insert  into `tb_product_order`(`proorder_id`,`id`,`accepter`,`proorder_number`,`create_time`,`proorder_desc`,`proorder_msg`,`totalmobi`,`express_fee`,`enable_status`,`personinfo_id`,`lastedit_time`,`address_id`,`pay_time`,`put_time`,`product_mobi`,`product_integral`) values 
('01f69596b213484cae5ec2447a78049b',51,'瑞莎','76CFDD5054271512724772565','2017-12-08 17:19:33','购买商品',NULL,50,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 17:19:33','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,40,400),
('0adbc0cbd3b54881849c6f4538e127b4',31,'瑞莎','D5EF538001351512720533274','2017-12-08 16:08:53','购买商品',NULL,90,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:10:16','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,80,800),
('0ba4301aef564b60a2540c0c98f271a3',66,'瑞莎','66AB346845791512727595400','2017-12-08 18:06:35','购买商品',NULL,330,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 18:06:36','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,330,360),
('0cda13c5d3d24c10b9f2217230ed9da9',68,'瑞莎','52EE951347991512727971434','2017-12-08 18:12:51','购买商品',NULL,132,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 18:12:52','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,132,142),
('0cea0eb5adf0497e9639fec75196b576',37,'瑞莎','F94D9D7090571512721296915','2017-12-08 16:21:37','购买商品',NULL,110,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:21:48','637e6766353a4e86a1d3abbbbbe8b9c3',NULL,NULL,110,120),
('131b37ee4f9b40478028168c9025ee04',61,'瑞莎','6755CC3484311512726988292','2017-12-08 17:56:28','购买商品',NULL,152,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 17:58:26','637e6766353a4e86a1d3abbbbbe8b9c3',NULL,NULL,152,342),
('136e78618d3e48e2b5e5c782c7c15df2',69,'瑞莎','1393D69081111512728061513','2017-12-08 18:14:22','购买商品',NULL,176,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 18:14:23','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,176,742),
('1381fe645f7445ee95da92e3810a7a62',77,'瑞莎','0D19312465631512730105976','2017-12-08 18:48:26','购买商品',NULL,110,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 18:48:27','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,110,120),
('14727acb4a8847d19c69e92c98fbb826',36,'瑞莎','A0E4C59760521512721099666','2017-12-08 16:18:20','购买商品',NULL,110,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 16:18:20','null',NULL,NULL,110,120),
('1db8a9fca23a48c8986cc9044ede9201',59,'瑞莎','5675988818431512726666507','2017-12-08 17:51:07','购买商品',NULL,32,10,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 17:51:07','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,22,22),
('233ca28fb2fb42839aa0edcd35b64425',49,'瑞莎','5805B03708031512723940286','2017-12-08 17:05:40','购买商品',NULL,90,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 17:13:39','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,80,800),
('2a066473654e4f1a82c9facd7fa3c0c3',64,'瑞莎','6907541922101512727461788','2017-12-08 18:04:22','购买商品',NULL,32,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 18:05:36','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,22,22),
('2b9dd4750a87474ead5795924a0f7456',54,'瑞莎','BCEDE44250811512725007505','2017-12-08 17:23:28','购买商品',NULL,32,10,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 17:23:36','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,22,22),
('3829ac726ae84ea0a0bb7c616c9b2b22',32,'瑞莎','CC549C7651751512720773929','2017-12-08 16:12:54','购买商品',NULL,30,10,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 16:12:55','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,20,200),
('3a830bf1775d4dd88c51a000488dccbe',30,'瑞莎','3352401419571512720282949','2017-12-08 16:04:43','购买商品',NULL,32,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:05:07','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,22,22),
('3b517f5ae4024d5392c54b5ff131f714',73,'瑞莎','00D9661541861512728984201','2017-12-08 18:29:44','购买商品',NULL,32,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 18:33:30','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,22,300),
('3ed1a218aa554dcfa9787534018ed626',18,'瑞莎','C81F964019851512714327345','2017-12-08 14:25:27','购买商品',NULL,340,10,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 14:25:27','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,330,360),
('42941745129348f7b22c9224faec7c08',27,'瑞莎','76129E3327211512719919276','2017-12-08 15:58:39','购买商品',NULL,330,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 15:58:59','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,330,360),
('4e9c37f09cb64e7a812b2d05c04fb6d5',50,'瑞莎','43492A3785941512723941417','2017-12-08 17:05:41','购买商品',NULL,32,10,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 17:05:42','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,22,22),
('4f5ee6c7f0bf48219edff1dd5cc0c582',41,'瑞莎','0A4AC89603321512721679098','2017-12-08 16:27:59','购买商品',NULL,110,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:28:00','null',NULL,NULL,110,120),
('4ff43b30257e44ceacafc8275140505d',75,'瑞莎','9485838894101512729614243','2017-12-08 18:40:14','购买商品',NULL,132,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 18:40:15','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,132,142),
('54fae94de4c44d37ac5dae68a59f9d6e',58,'瑞莎','C994E79602971512726104961','2017-12-08 17:41:45','购买商品',NULL,76,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 17:41:46','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,66,900),
('55ba23ea37aa4320aeae0894497cdb43',26,'瑞莎','C5ABCC5289081512719269842','2017-12-08 15:47:50','购买商品',NULL,32,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 15:51:04','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,22,300),
('5b182978f2684932be8341fed76900ce',76,'瑞莎','4DCCFA7711951512729895407','2017-12-08 18:44:55','购买商品',NULL,110,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 18:44:56','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,110,120),
('5c4617eba33a42c48086d5eb15f604b6',57,'瑞莎','D78B052926011512726040140','2017-12-08 17:40:40','购买商品',NULL,110,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 17:41:06','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,110,120),
('6439536d4d8245fdaf755f837ad29a2a',65,'瑞莎','89DF355291431512727481270','2017-12-08 18:04:41','购买商品',NULL,54,10,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 18:04:42','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,44,600),
('67a5406bd1e5478bb8e72ef40340c9db',56,'瑞莎','A05FE77350211512725254466','2017-12-08 17:27:34','购买商品',NULL,110,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 17:27:35','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,110,120),
('690c7b2e5b984604bb81734f3445865c',42,'瑞莎','578D6F1686721512721805121','2017-12-08 16:30:05','购买商品',NULL,30,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:30:06','null',NULL,NULL,20,200),
('6b22bc32e564416c8e41835edf6664dd',67,'瑞莎','20A0247628991512727936426','2017-12-08 18:12:16','购买商品',NULL,32,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 18:12:17','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,22,22),
('6fc050a995a14bff806a86c27412db70',63,'瑞莎','5E792F7536291512727336813','2017-12-08 18:02:17','购买商品',NULL,76,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 18:02:17','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,66,900),
('733356405bcb4e20911ddcecd6b40cfe',47,'瑞莎','2873A29896221512723606207','2017-12-08 17:00:06','购买商品',NULL,100,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 17:00:06',NULL,NULL,NULL,100,1000),
('746dd0745ff8445681bfb4c54c1f37a8',60,'瑞莎','32096D1339031512726678832','2017-12-08 17:51:19','购买商品',NULL,54,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 17:51:29','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,44,600),
('781fcb5b99b74f30bf78171b99c29bfb',55,'瑞莎','6417643362381512725196163','2017-12-08 17:26:36','购买商品',NULL,110,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 17:26:37','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,110,120),
('7940f8c9e905483fb49c93fae238f289',71,'瑞莎','C7E3DF8012201512728240556','2017-12-08 18:17:21','购买商品',NULL,132,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 18:17:21','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,132,142),
('79bc10564fa4492b91c6a154174f3f75',48,'瑞莎','0B03DD7315611512723639929','2017-12-08 17:00:40','购买商品',NULL,90,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 17:00:40',NULL,NULL,NULL,80,800),
('83c44448449c4827ac8c127e3f3d5ceb',35,'瑞莎','0084497606901512720995036','2017-12-08 16:16:35','购买商品',NULL,32,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:16:41','null',NULL,NULL,22,22),
('87b729704e404ffda0362dd0f0b1a763',78,'瑞莎','A9317B6656211512730218295','2017-12-08 18:50:18','购买商品',NULL,110,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 18:51:25','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,110,120),
('8c48098022e84603bafc290698ff6c16',40,'瑞莎','B5805C4969511512721575261','2017-12-08 16:26:15','购买商品',NULL,32,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:26:16','null',NULL,NULL,22,300),
('8c7e96ad745845918f49df515107c642',39,'瑞莎','CA01655300671512721534496','2017-12-08 16:25:34','购买商品',NULL,32,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:25:38','null',NULL,NULL,22,300),
('9352e9a096f6427188e62cab3843d932',72,'瑞莎','AE3A6E9374821512728388093','2017-12-08 18:19:48','购买商品',NULL,660,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 18:19:49','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,660,2090),
('a141661c80394e609876f21b1969924f',19,'瑞莎','55F44B3553561512714413934','2017-12-08 14:26:54','购买商品',NULL,88,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 14:26:54','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,88,1200),
('a6ab6767c54a44eebfa3957f84796e1b',45,'瑞莎','5BABA22104431512722482105','2017-12-08 16:41:22','购买商品',NULL,30,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:41:23','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,20,200),
('a83da10f239241949fb3268c7394f654',43,'瑞莎','1ED5DF4614691512721998561','2017-12-08 16:33:19','购买商品',NULL,32,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:36:35','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,22,22),
('acc7620480cc41d6bd2575ec3ffe25e9',29,'瑞莎','E011431679391512720132542','2017-12-08 16:02:13','购买商品',NULL,98,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:04:12','637e6766353a4e86a1d3abbbbbe8b9c3',NULL,NULL,88,88),
('b30c73dc585e4a0a81a83c936d2f6e7d',33,'瑞莎','CE79287683101512720912490','2017-12-08 16:15:12','购买商品',NULL,32,10,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 16:15:13','null',NULL,NULL,22,22),
('b776944b111b4445b27cf771e5f41ee5',24,'瑞莎','C618778238431512719037343','2017-12-08 15:43:57','购买商品',NULL,98,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 15:51:04','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,88,1200),
('ba973704507d475c81233b6038de496d',70,'瑞莎','7FCC739351581512728218565','2017-12-08 18:16:59','购买商品',NULL,132,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 18:16:59','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,132,142),
('c50cc64dd8f640a69bf5e5974379e783',62,'瑞莎','CAC0B14350451512727002815','2017-12-08 17:56:43','购买商品',NULL,154,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 17:56:44','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,154,442),
('c50e42ff819845baa9f7b536537a9056',44,'瑞莎','12E9AA1456471512722476858','2017-12-08 16:41:17','购买商品',NULL,32,10,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 16:42:57','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,22,22),
('c6c5cc5198a942e9994efb49349283d9',21,'瑞莎','060D809682801512718159300','2017-12-08 15:29:19','购买商品',NULL,1430,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 15:52:35','637e6766353a4e86a1d3abbbbbe8b9c3',NULL,NULL,1430,1560),
('c7777a3cfb6b4b7ea9a4646cb4f21f26',25,'瑞莎','1B88188465111512719169298','2017-12-08 15:46:09','购买商品',NULL,30,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 15:51:04','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,20,200),
('ce533621c221418db0e78b37cd3a2302',46,'瑞莎','2B3BD45260171512722983260','2017-12-08 16:49:43','购买商品',NULL,110,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 16:52:25','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,110,120),
('cf034501787f4bada562cf7f48fc3649',23,'瑞莎','E47FA17206831512718788571','2017-12-08 15:39:49','购买商品',NULL,30,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 15:51:04','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,20,200),
('d0a9fcf26fff4ebe9c7e9eb3723ff0f5',22,'瑞莎','8ED14E7166801512718389315','2017-12-08 15:33:09','购买商品',NULL,32,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 15:51:04','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,22,22),
('d62d4afe442e43eda8765f5fcbf8ad79',74,'瑞莎','9D99C21761631512729418037','2017-12-08 18:36:58','购买商品',NULL,110,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 18:37:08','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,110,120),
('df4e4b1d6fce44fe83f5c8e19c874fb0',34,'瑞莎','3BE9A24850411512720977138','2017-12-08 16:16:17','购买商品',NULL,30,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:16:18','null',NULL,NULL,20,200),
('e1f361bd83d24612bf46baaced271bef',28,'瑞莎','5675643057121512719983438','2017-12-08 15:59:43','购买商品',NULL,76,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 15:59:43','1fe3c446f9514a5cbf2ae7e50c40f1de',NULL,NULL,66,66),
('e2a04d23fcbb4a5bacccd9ea2256c2be',38,'瑞莎','CCCD1E9216611512721407837','2017-12-08 16:23:28','购买商品',NULL,32,10,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 16:23:57','null',NULL,NULL,22,22),
('efdff5e97852432891ba114e0ac97d8f',10,'瑞莎','02AF692456741512704315499','2017-12-08 11:38:35','购买商品',NULL,40,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 15:52:35','637e6766353a4e86a1d3abbbbbe8b9c3',NULL,NULL,40,400),
('f02785f4d49e4acdbdaaf2dd9d3be376',17,'瑞莎','8F1D532314371512709869668','2017-12-08 13:11:10','购买商品',NULL,80,0,1,'90f1ceab1ced47bc949077f982ce0774','2017-12-08 15:52:35','637e6766353a4e86a1d3abbbbbe8b9c3',NULL,NULL,80,100),
('f114042aeb9943ab8a223c62e12c1b4a',20,'瑞莎','E2443B9584401512715620561','2017-12-08 14:47:01','购买商品',NULL,32,10,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 14:47:01','c70a96265d2849d9b5afee98b1cc67bd',NULL,NULL,22,22),
('f23b60424f6a46f8af453aa0845bf926',52,'瑞莎','7EF7B25187801512724775839','2017-12-08 17:19:36','购买商品',NULL,110,0,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 17:19:36','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,110,120),
('f8bb105d2d0e469bbe3ac96c81d2294a',53,'瑞莎','85CEAA3542181512724946751','2017-12-08 17:22:27','购买商品',NULL,32,10,1,'2a922aff15fa41ce8debbde99577eaec','2017-12-08 17:22:27','033fbc8d6bc8464a9261b735e49e90d4',NULL,NULL,22,22);

/*Table structure for table `tb_product_property` */

DROP TABLE IF EXISTS `tb_product_property`;

CREATE TABLE `tb_product_property` (
  `pp_id` varchar(32) NOT NULL COMMENT '产品属性关联',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `pv_id` varchar(32) DEFAULT NULL COMMENT '对应属性值表主键',
  `lastedit_time` datetime NOT NULL COMMENT '更新时间',
  `ppmanage_id` varchar(32) DEFAULT NULL COMMENT '组合表主键',
  `product_id` varchar(32) DEFAULT NULL COMMENT '商品主键',
  PRIMARY KEY (`pp_id`),
  KEY `id` (`id`),
  KEY `ppmanage_id` (`ppmanage_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `tb_product_property_ibfk_3` FOREIGN KEY (`ppmanage_id`) REFERENCES `tb_ppmanage` (`ppmanage_id`) ON DELETE SET NULL,
  CONSTRAINT `tb_product_property_ibfk_4` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `tb_product_property` */

insert  into `tb_product_property`(`pp_id`,`id`,`create_time`,`pv_id`,`lastedit_time`,`ppmanage_id`,`product_id`) values 
('35901901f8464cca801586f7c11d9f99',1,'2017-12-20 15:35:50','53474cc4e722436f9742b0156a95c038','2017-12-21 15:35:44','11b803170a1247bf9c99ab840db51fae','6bfe568f04bc40138e1e74b42f7b9bbd'),
('3b5ded2b0fd1497d823f348dc1692162',2,'2017-12-01 15:37:06','eee37787d37c4c8483a99bfb05008d24','2017-12-01 15:37:05','11b803170a1247bf9c99ab840db51fae','6bfe568f04bc40138e1e74b42f7b9bbd'),
('a8bf2f0cfba248199ff2ee3cc64c8d9f',5,'2017-12-05 14:31:55','dc7afd8d221a4e4c9dab9c557830fabf','2017-12-06 14:32:05','34ba8adab5fa489a81d007c2542e5f5b','6bfe568f04bc40138e1e74b42f7b9bbd'),
('b52f6d3351e14fa89f839d8b3f5fe11d',3,'2017-12-06 14:22:12','dc7afd8d221a4e4c9dab9c557830fabf','2017-12-06 14:22:22','11b803170a1247bf9c99ab840db51fae','6bfe568f04bc40138e1e74b42f7b9bbd'),
('d9167cdbe5bd4a2abbc79c4b56795687',6,'2017-12-05 14:32:35','73cb60df428445f79c6b1dd8076c9ec7','2017-12-05 14:32:41','34ba8adab5fa489a81d007c2542e5f5b','6bfe568f04bc40138e1e74b42f7b9bbd'),
('ffcac811aa624c2a84b2bd8f06adf409',4,'2017-12-13 14:30:29','375d4968c5924b15a54db715a0044a0f','2017-12-13 14:30:12','34ba8adab5fa489a81d007c2542e5f5b','6bfe568f04bc40138e1e74b42f7b9bbd');

/*Table structure for table `tb_product_repertory` */

DROP TABLE IF EXISTS `tb_product_repertory`;

CREATE TABLE `tb_product_repertory` (
  `prorep_id` varchar(32) NOT NULL COMMENT '产品库存表主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `sale_num` int(2) NOT NULL DEFAULT '0' COMMENT '产品销量',
  `current_num` int(2) NOT NULL DEFAULT '0' COMMENT '产品现量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `lastedit_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`prorep_id`),
  KEY `idx_prorep_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_product_repertory` */

insert  into `tb_product_repertory`(`prorep_id`,`id`,`sale_num`,`current_num`,`create_time`,`lastedit_time`) values 
('fce12e3ff9d049478126bafa39f140c4',1,0,1922,'2017-12-05 15:17:44','2017-12-08 18:50:18');

/*Table structure for table `tb_productoperation_recoder` */

DROP TABLE IF EXISTS `tb_productoperation_recoder`;

CREATE TABLE `tb_productoperation_recoder` (
  `operation_id` varchar(32) NOT NULL COMMENT '产品修改记录表主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `last_num` int(2) NOT NULL COMMENT '上次总量',
  `current_num` int(2) NOT NULL COMMENT '当前数量',
  `create_time` datetime NOT NULL COMMENT '操作时间',
  `operation_name` varchar(32) NOT NULL COMMENT '操作人',
  `operation_desc` varchar(128) NOT NULL COMMENT '操作描述',
  `product_id` varchar(32) DEFAULT NULL COMMENT '商品主键',
  PRIMARY KEY (`operation_id`),
  KEY `id` (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `tb_productoperation_recoder_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_productoperation_recoder` */

/*Table structure for table `tb_proper` */

DROP TABLE IF EXISTS `tb_proper`;

CREATE TABLE `tb_proper` (
  `proper_id` varchar(32) NOT NULL COMMENT '道具表主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `proper_name` varchar(32) NOT NULL COMMENT '道具名称',
  `proper_desc` varchar(500) NOT NULL COMMENT '道具描述',
  `proper_url` varchar(200) NOT NULL COMMENT '道具链接',
  `proper_integral` int(2) NOT NULL COMMENT '道具对应积分',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `lastedit_time` datetime NOT NULL COMMENT '更新时间',
  `priority` int(2) NOT NULL COMMENT '权重',
  `propercate_id` varchar(32) DEFAULT NULL COMMENT '道具种类',
  `proper_order` varchar(32) NOT NULL COMMENT '道具编号',
  PRIMARY KEY (`proper_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `tb_proper` */

insert  into `tb_proper`(`proper_id`,`id`,`proper_name`,`proper_desc`,`proper_url`,`proper_integral`,`create_time`,`lastedit_time`,`priority`,`propercate_id`,`proper_order`) values 
('37d2d0a57aa14bcf80360aed770c9d30',7,'花冠','花冠','1',500,'2017-12-05 09:48:17','2017-12-05 09:48:19',6,'1','611021512438481540'),
('52afc635ea3d4e8093cba21002bc0a28',8,'体力豆X20','体力豆','1',50,'2017-12-05 09:49:25','2017-12-05 09:49:27',7,'1','606621512438517395'),
('ba64394ecaa243de8d23e1126246e947',4,'中级骑士','中级骑士','1',50,'2017-12-05 09:44:59','2017-12-05 09:45:03',3,'1','801441512438260359'),
('bb0cebf379c44ad89b2758ac87635787',6,'草冠','草冠','1',200,'2017-12-05 09:47:35','2017-12-05 09:47:37',5,'1','385351512438432983'),
('bd1c4b377459416089e613cb44797bcd',5,'皇冠','皇冠','1',1000,'2017-12-05 09:46:55','2017-12-05 09:46:57',4,'5d650f958bd84d7686ae99d29bcd7677','578281512438389091'),
('ce93f02797a94c7fb57915798500eaa7',3,'初级公主','初级公主','1',0,'2017-12-05 09:43:51','2017-12-05 09:43:53',2,'1','961771512438212642'),
('d42f94eae46c4c5989e74027fad4a4a4',1,'初级骑士','初级骑士','1',0,'2017-12-05 09:42:33','2017-12-05 09:42:31',1,'1','127491512437809846');

/*Table structure for table `tb_proper_category` */

DROP TABLE IF EXISTS `tb_proper_category`;

CREATE TABLE `tb_proper_category` (
  `propercate_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '道具种类',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `propercate_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '种类名称',
  `propercate_order` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '种类编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `lastedit_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`propercate_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `tb_proper_category` */

insert  into `tb_proper_category`(`propercate_id`,`id`,`propercate_name`,`propercate_order`,`create_time`,`lastedit_time`) values 
('5c540bcadf374e76be0e54298c5d9f4d',1,'角色','771181512437686638','2017-12-05 09:35:02','2017-12-05 09:35:03'),
('5d650f958bd84d7686ae99d29bcd7677',2,'头饰','783001512437714345','2017-12-05 09:35:27','2017-12-05 09:35:29'),
('76252ae8d78740ba8e493958100f6785',3,'体力豆','648951512437737130','2017-12-05 09:36:01','2017-12-05 09:36:03');

/*Table structure for table `tb_property` */

DROP TABLE IF EXISTS `tb_property`;

CREATE TABLE `tb_property` (
  `property_id` varchar(32) NOT NULL COMMENT '商品属性主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `property_name` varchar(32) NOT NULL COMMENT '名称',
  `property_order` varchar(100) NOT NULL COMMENT '编号',
  PRIMARY KEY (`property_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `tb_property` */

insert  into `tb_property`(`property_id`,`id`,`property_name`,`property_order`) values 
('546445302bd74ae49355660ff4ecc746',2,'大小','222'),
('5c150ff76be745e1b7af1b2ff0628b63',1,'颜色','111'),
('802c7cb816094251b6bedc0f6c706c8d',3,'规格','333');

/*Table structure for table `tb_property_value` */

DROP TABLE IF EXISTS `tb_property_value`;

CREATE TABLE `tb_property_value` (
  `pv_id` varchar(32) NOT NULL COMMENT '主键',
  `vid` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `property_id` varchar(32) DEFAULT NULL COMMENT '属性表主键',
  `property_value` varchar(32) NOT NULL COMMENT '属性值',
  PRIMARY KEY (`pv_id`),
  KEY `id` (`vid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `tb_property_value` */

insert  into `tb_property_value`(`pv_id`,`vid`,`property_id`,`property_value`) values 
('375d4968c5924b15a54db715a0044a0f',8,'5c150ff76be745e1b7af1b2ff0628b63','黑色'),
('3dfd864266614e8d9e381513d6841445',11,'546445302bd74ae49355660ff4ecc746','35'),
('53474cc4e722436f9742b0156a95c038',3,'546445302bd74ae49355660ff4ecc746','33'),
('73cb60df428445f79c6b1dd8076c9ec7',4,'546445302bd74ae49355660ff4ecc746','34'),
('8177b665c2d94741896fb250d0e83f22',7,'802c7cb816094251b6bedc0f6c706c8d','16G'),
('8b4fcf22b18343e99d2a2b9468ebbee7',10,'546445302bd74ae49355660ff4ecc746','31'),
('da971857f2eb4c859a9ab4c19d56516a',2,'5c150ff76be745e1b7af1b2ff0628b63','白色'),
('dc7afd8d221a4e4c9dab9c557830fabf',5,'802c7cb816094251b6bedc0f6c706c8d','128G'),
('eee37787d37c4c8483a99bfb05008d24',1,'5c150ff76be745e1b7af1b2ff0628b63','红色'),
('f177048efa634c2da6df6b4726bbbc50',9,'546445302bd74ae49355660ff4ecc746','32'),
('f95b92377437406db7e5fd7567ce8ab5',6,'802c7cb816094251b6bedc0f6c706c8d','32G');

/*Table structure for table `tb_qmm_result` */

DROP TABLE IF EXISTS `tb_qmm_result`;

CREATE TABLE `tb_qmm_result` (
  `qr_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '抢面膜结果记录主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `openid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '个人信息主键',
  `qr_integral` int(2) NOT NULL COMMENT '获得积分',
  PRIMARY KEY (`qr_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `tb_qmm_result` */

/*Table structure for table `tb_recharge` */

DROP TABLE IF EXISTS `tb_recharge`;

CREATE TABLE `tb_recharge` (
  `recharge_id` varchar(32) NOT NULL COMMENT '充值订单主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `recharge_order` varchar(32) NOT NULL COMMENT '订单编号',
  `recharge_accepter` varchar(128) NOT NULL COMMENT '收款方',
  `recharge_fee` int(2) NOT NULL COMMENT '充值金额',
  `recharge_desc` varchar(500) NOT NULL COMMENT '描述',
  `ratio` int(2) NOT NULL COMMENT '转化比例，10的整数倍',
  `enable_status` int(2) NOT NULL COMMENT '0:未支付 1已支付',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `personinfo_id` varchar(32) DEFAULT NULL COMMENT '外键：个人信息主键',
  PRIMARY KEY (`recharge_id`),
  KEY `id` (`id`),
  KEY `idx_enable_status` (`enable_status`),
  KEY `personinfo_id` (`personinfo_id`),
  CONSTRAINT `tb_recharge_ibfk_1` FOREIGN KEY (`personinfo_id`) REFERENCES `tb_personinfo` (`personinfo_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_recharge` */

/*Table structure for table `tb_recharge_record` */

DROP TABLE IF EXISTS `tb_recharge_record`;

CREATE TABLE `tb_recharge_record` (
  `rechargerecord_id` varchar(32) NOT NULL COMMENT '充值记录表主键；在充值成功后插入',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `personinfo_id` varchar(32) DEFAULT NULL COMMENT '个人信息',
  `create_time` datetime NOT NULL COMMENT '时间',
  `money` int(2) NOT NULL COMMENT '充值金额',
  `mobi` int(2) NOT NULL COMMENT '转换膜币',
  PRIMARY KEY (`rechargerecord_id`),
  KEY `id` (`id`),
  KEY `personinfo_id` (`personinfo_id`),
  CONSTRAINT `tb_recharge_record_ibfk_1` FOREIGN KEY (`personinfo_id`) REFERENCES `tb_personinfo` (`personinfo_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_recharge_record` */

/*Table structure for table `tb_reward` */

DROP TABLE IF EXISTS `tb_reward`;

CREATE TABLE `tb_reward` (
  `reward_id` varchar(32) NOT NULL COMMENT '奖品类表主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `reward_name` varchar(32) NOT NULL COMMENT '奖品名称',
  `reward_num` int(2) NOT NULL COMMENT '奖品当前数量',
  `reward_desc` varchar(500) NOT NULL COMMENT '奖品描述',
  `reward_img` varchar(200) NOT NULL COMMENT '奖品链接',
  `enable_status` int(2) NOT NULL DEFAULT '1' COMMENT '2需要邮递1不需要0谢谢',
  `reward_salenum` int(2) NOT NULL COMMENT '送出数量',
  PRIMARY KEY (`reward_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_reward` */

insert  into `tb_reward`(`reward_id`,`id`,`reward_name`,`reward_num`,`reward_desc`,`reward_img`,`enable_status`,`reward_salenum`) values 
('fb9129785dc44374b3f825d93ef8295a',1,'谢谢',1,'谢谢','1',0,0);

/*Table structure for table `tb_role` */

DROP TABLE IF EXISTS `tb_role`;

CREATE TABLE `tb_role` (
  `role_id` varchar(32) NOT NULL COMMENT '抢面膜角色主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` varchar(16) NOT NULL COMMENT '角色名称',
  `role_url` varchar(200) NOT NULL COMMENT '角色链接',
  `role_desc` varchar(128) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `lastedit_time` datetime NOT NULL COMMENT '更新时间',
  `personinfo_id` varchar(32) DEFAULT NULL COMMENT '外键；个人信息id',
  `role_order` int(2) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`role_id`),
  KEY `id` (`id`),
  KEY `idx_personinfo_id` (`personinfo_id`),
  CONSTRAINT `tb_role_ibfk_1` FOREIGN KEY (`personinfo_id`) REFERENCES `tb_personinfo` (`personinfo_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_role` */

/*Table structure for table `tb_room` */

DROP TABLE IF EXISTS `tb_room`;

CREATE TABLE `tb_room` (
  `room_id` varchar(32) NOT NULL COMMENT '房间主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `current_num` int(2) NOT NULL COMMENT '房间现有人数',
  `roomcate_id` varchar(32) DEFAULT NULL COMMENT '房间类型主键',
  PRIMARY KEY (`room_id`),
  KEY `id` (`id`),
  KEY `roomcate_id` (`roomcate_id`),
  CONSTRAINT `tb_room_ibfk_1` FOREIGN KEY (`roomcate_id`) REFERENCES `tb_room_category` (`roomcate_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_room` */

/*Table structure for table `tb_room_category` */

DROP TABLE IF EXISTS `tb_room_category`;

CREATE TABLE `tb_room_category` (
  `roomcate_id` varchar(32) NOT NULL COMMENT '房间类型id',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roocate_name` varchar(32) NOT NULL COMMENT '房间类型名',
  `rootcate_num` int(2) NOT NULL COMMENT '房间人数',
  `rootcate_img` varchar(200) NOT NULL COMMENT '房间类型图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `enable_status` int(2) NOT NULL DEFAULT '1' COMMENT '0不可用1可用',
  PRIMARY KEY (`roomcate_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_room_category` */

/*Table structure for table `tb_spnum` */

DROP TABLE IF EXISTS `tb_spnum`;

CREATE TABLE `tb_spnum` (
  `spnum_id` varchar(32) NOT NULL COMMENT '购物表id',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `spnum_num` int(2) NOT NULL COMMENT '数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `product_id` varchar(32) DEFAULT NULL COMMENT '商品主键',
  `shopping_cart` int(2) NOT NULL COMMENT '是否购物车0否1是',
  `enable_status` int(2) NOT NULL DEFAULT '1' COMMENT '1可用 0 不可用',
  `prooder_id` varchar(32) DEFAULT NULL COMMENT '外键：订单编号',
  `ppmanage_id` varchar(32) DEFAULT NULL COMMENT '参数联系表主键',
  `personInfo_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`spnum_id`),
  KEY `id` (`id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_shopping_cart` (`shopping_cart`),
  CONSTRAINT `tb_spnum_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;

/*Data for the table `tb_spnum` */

insert  into `tb_spnum`(`spnum_id`,`id`,`spnum_num`,`create_time`,`product_id`,`shopping_cart`,`enable_status`,`prooder_id`,`ppmanage_id`,`personInfo_id`) values 
('0ac7a348f5024f8e925e4275dcbb9dbe',43,2,'2017-12-08 17:19:27','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'f8bb105d2d0e469bbe3ac96c81d2294a','','2a922aff15fa41ce8debbde99577eaec'),
('0c1618b85b2b41689c3618f0c93de069',81,1,'2017-12-08 18:50:15','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'87b729704e404ffda0362dd0f0b1a763','','2a922aff15fa41ce8debbde99577eaec'),
('0d71003960914e928d4148d76ba9242a',57,1,'2017-12-08 17:56:34','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'c50cc64dd8f640a69bf5e5974379e783','182435','2a922aff15fa41ce8debbde99577eaec'),
('0e548660f0a24e2599d763adcbc4fc09',53,1,'2017-12-08 17:56:14','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'131b37ee4f9b40478028168c9025ee04','','90f1ceab1ced47bc949077f982ce0774'),
('163e7204500a47d49233885a3aba2f08',70,1,'2017-12-08 18:17:14','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'7940f8c9e905483fb49c93fae238f289','','2a922aff15fa41ce8debbde99577eaec'),
('18c13c2f856c4caba2e0b23d85f9fac2',79,1,'2017-12-08 18:44:51','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'5b182978f2684932be8341fed76900ce','','2a922aff15fa41ce8debbde99577eaec'),
('1984a05a844949c9be205a4aa6c1628f',7,13,'2017-12-07 15:50:00','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'c6c5cc5198a942e9994efb49349283d9','','90f1ceab1ced47bc949077f982ce0774'),
('1b5a85f3ad274d4895044f05a9850f36',16,1,'2017-12-08 15:45:46','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'c7777a3cfb6b4b7ea9a4646cb4f21f26','112335','90f1ceab1ced47bc949077f982ce0774'),
('1cf9a7a440cb4cc68f8dc33e3b4615fb',62,1,'2017-12-08 18:12:12','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'6b22bc32e564416c8e41835edf6664dd','','90f1ceab1ced47bc949077f982ce0774'),
('1fe66a7af61a41f190bda1db1be6e10c',44,2,'2017-12-08 17:19:32','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'f23b60424f6a46f8af453aa0845bf926','','2a922aff15fa41ce8debbde99577eaec'),
('2814497ad071463e9dd9f4e30f102c58',49,1,'2017-12-08 17:40:36','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'1db8a9fca23a48c8986cc9044ede9201','','2a922aff15fa41ce8debbde99577eaec'),
('2a49bfba844140f99d95d07fe6c0b368',8,1,'2017-12-07 15:50:05','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'d0a9fcf26fff4ebe9c7e9eb3723ff0f5','','90f1ceab1ced47bc949077f982ce0774'),
('2ad729ae0fe9474aa7a40df79678f914',24,1,'2017-12-08 16:12:46','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'3829ac726ae84ea0a0bb7c616c9b2b22','112335','2a922aff15fa41ce8debbde99577eaec'),
('2af1ad85c87941fab7c0c0dea9228e88',6,2,'2017-12-07 15:30:25','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'efdff5e97852432891ba114e0ac97d8f','112335','90f1ceab1ced47bc949077f982ce0774'),
('2fbfd1572e65418bb15c8f13080313ad',14,1,'2017-12-08 15:39:44','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'cf034501787f4bada562cf7f48fc3649','112335','90f1ceab1ced47bc949077f982ce0774'),
('301c196bdd284ec482406bfd5ae83156',35,1,'2017-12-08 16:33:14','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'a83da10f239241949fb3268c7394f654','','90f1ceab1ced47bc949077f982ce0774'),
('309ffbb4872d4ac58a4cc12a4939432f',77,1,'2017-12-08 18:40:09','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'4ff43b30257e44ceacafc8275140505d','','2a922aff15fa41ce8debbde99577eaec'),
('330e0a642a4e476da08ddbb371aeb221',68,1,'2017-12-08 18:16:52','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'ba973704507d475c81233b6038de496d','','2a922aff15fa41ce8debbde99577eaec'),
('3387d8632ca0489aa572a1fbf6943bae',30,1,'2017-12-08 16:21:33','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'0cea0eb5adf0497e9639fec75196b576','','90f1ceab1ced47bc949077f982ce0774'),
('34526b2f3d524b7bb058e4b71924ae68',67,2,'2017-12-08 18:14:18','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'136e78618d3e48e2b5e5c782c7c15df2','182435','90f1ceab1ced47bc949077f982ce0774'),
('374a528bde454932bbd0e044ad83259c',40,1,'2017-12-08 17:00:35','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'79bc10564fa4492b91c6a154174f3f75','112335','90f1ceab1ced47bc949077f982ce0774'),
('385f88e145e44824aeaf267c6b08ddd0',18,1,'2017-12-08 15:58:34','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'42941745129348f7b22c9224faec7c08','','90f1ceab1ced47bc949077f982ce0774'),
('3cb7b2d34efa4fb8befeed023ef6ae48',19,1,'2017-12-08 15:59:38','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'e1f361bd83d24612bf46baaced271bef','','90f1ceab1ced47bc949077f982ce0774'),
('44ca10dbae944a2b8ded4f1439827aec',71,1,'2017-12-08 18:17:16','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'7940f8c9e905483fb49c93fae238f289','','2a922aff15fa41ce8debbde99577eaec'),
('47075e6b391e4292be88f720cc81388a',59,1,'2017-12-08 18:04:15','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'2a066473654e4f1a82c9facd7fa3c0c3','','90f1ceab1ced47bc949077f982ce0774'),
('493009b6a01142aeabb3ac13bd129a40',13,1,'2017-12-07 18:52:43','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'0568ea7e239048f3b18c22261ce0d4da','182435','90f1ceab1ced47bc949077f982ce0774'),
('4c05beb0aed544b88c89d35b0e7b88a2',5,4,'2017-12-06 14:26:54','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'a141661c80394e609876f21b1969924f','182435','2a922aff15fa41ce8debbde99577eaec'),
('518785b270be4c65a7d976764604ffad',23,1,'2017-12-08 16:12:38','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'b30c73dc585e4a0a81a83c936d2f6e7d','','2a922aff15fa41ce8debbde99577eaec'),
('51997e6b337447858e0efc89bb6b4ade',21,1,'2017-12-08 16:04:38','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'3a830bf1775d4dd88c51a000488dccbe','','90f1ceab1ced47bc949077f982ce0774'),
('53db38ab0fbf4cfa89ad85843b1dcb5a',12,3,'2017-12-07 15:51:51','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'3ed1a218aa554dcfa9787534018ed626','','2a922aff15fa41ce8debbde99577eaec'),
('575d09f9718545ee9a679de1f59300ae',75,1,'2017-12-08 18:29:38','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'3b517f5ae4024d5392c54b5ff131f714','182435','90f1ceab1ced47bc949077f982ce0774'),
('5e64773936c0471bbecf6c2739d7e20f',39,1,'2017-12-08 16:59:54','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'733356405bcb4e20911ddcecd6b40cfe','112335','90f1ceab1ced47bc949077f982ce0774'),
('62d839574d994b669a4e3017997c992b',80,1,'2017-12-08 18:48:22','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'1381fe645f7445ee95da92e3810a7a62','','2a922aff15fa41ce8debbde99577eaec'),
('6e7e6da5a2a846c68b429a0e0fe24a15',46,2,'2017-12-08 17:23:24','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'2b9dd4750a87474ead5795924a0f7456','','2a922aff15fa41ce8debbde99577eaec'),
('755f011da5764e4e89b274152999ed27',29,1,'2017-12-08 16:21:30','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'e2a04d23fcbb4a5bacccd9ea2256c2be','','90f1ceab1ced47bc949077f982ce0774'),
('7824a0bce9a54c82b4ae6d4021233f0e',60,2,'2017-12-08 18:04:18','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'6439536d4d8245fdaf755f837ad29a2a','182435','2a922aff15fa41ce8debbde99577eaec'),
('7c87e51cb90d41d09f708bf18d8219c4',69,1,'2017-12-08 18:16:54','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'ba973704507d475c81233b6038de496d','','2a922aff15fa41ce8debbde99577eaec'),
('7c93c64579214d9fa389780392551efc',76,1,'2017-12-08 18:36:54','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'d62d4afe442e43eda8765f5fcbf8ad79','','90f1ceab1ced47bc949077f982ce0774'),
('8027b18419af44ec8d3442ab37e1870b',56,1,'2017-12-08 17:56:25','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'c50cc64dd8f640a69bf5e5974379e783','','2a922aff15fa41ce8debbde99577eaec'),
('82041ab291914911849441bab95ba9ee',38,1,'2017-12-08 16:49:30','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'ce533621c221418db0e78b37cd3a2302','','2a922aff15fa41ce8debbde99577eaec'),
('838d8d46dac04a49a9a4554ccfcc3a73',9,1,'2017-12-07 15:50:11','1e78de30aab545c897a9ad32d0bfcfc7',0,1,'f02785f4d49e4acdbdaaf2dd9d3be376','','90f1ceab1ced47bc949077f982ce0774'),
('8b8d9edb76884e4483ca89a22b9c7814',51,2,'2017-12-08 17:51:11','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'746dd0745ff8445681bfb4c54c1f37a8','182435','90f1ceab1ced47bc949077f982ce0774'),
('929a9b27e7904aa4ac80fac204f6993f',55,1,'2017-12-08 17:56:23','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'c50cc64dd8f640a69bf5e5974379e783','','2a922aff15fa41ce8debbde99577eaec'),
('9442c1731cae4d26bf89493dbfe1be10',20,1,'2017-12-08 16:02:06','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'acc7620480cc41d6bd2575ec3ffe25e9','','90f1ceab1ced47bc949077f982ce0774'),
('97f41fe2788a4905983ba9a78aedc8e3',10,1,'2017-12-07 15:50:48','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'f114042aeb9943ab8a223c62e12c1b4a','','2a922aff15fa41ce8debbde99577eaec'),
('a03844e6e165477a802e00fd43adbb0b',4,3,'2017-12-06 13:55:47','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'fc4379cc9cfa424683ed5cfa948ef37e','112335','2a922aff15fa41ce8debbde99577eaec'),
('a27cc1e82c864258b31663d0bfbfde58',27,1,'2017-12-08 16:18:11','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'c50e42ff819845baa9f7b536537a9056','','2a922aff15fa41ce8debbde99577eaec'),
('a7cc52215dda408094001fff3499aaf8',73,5,'2017-12-08 18:19:05','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'9352e9a096f6427188e62cab3843d932','','2a922aff15fa41ce8debbde99577eaec'),
('a853c7b850034a15915bd4f6d0551307',34,1,'2017-12-08 16:30:01','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'690c7b2e5b984604bb81734f3445865c','112335','90f1ceab1ced47bc949077f982ce0774'),
('acc479c7ab794274b5e95861d5e377d9',2,2,'2017-12-06 13:01:42','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'734dad47642747b6a0ac1f098f51508a','182435','90f1ceab1ced47bc949077f982ce0774'),
('aceb1936b083418caa83f61aacd612f9',52,1,'2017-12-08 17:56:11','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'131b37ee4f9b40478028168c9025ee04','','90f1ceab1ced47bc949077f982ce0774'),
('ae46f341898643f5a7318d8958f4f6ba',37,1,'2017-12-08 16:49:26','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'4e9c37f09cb64e7a812b2d05c04fb6d5','','2a922aff15fa41ce8debbde99577eaec'),
('b076e8c04f4849209710e041ae852480',48,1,'2017-12-08 17:40:33','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'5c4617eba33a42c48086d5eb15f604b6','','2a922aff15fa41ce8debbde99577eaec'),
('b9fd4cb7782d49d095c60c9750de177b',36,1,'2017-12-08 16:41:18','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'a6ab6767c54a44eebfa3957f84796e1b','112335','90f1ceab1ced47bc949077f982ce0774'),
('bd92da8e80854fb6bb525011f46ef2cc',50,3,'2017-12-08 17:41:40','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'54fae94de4c44d37ac5dae68a59f9d6e','182435','90f1ceab1ced47bc949077f982ce0774'),
('bf2085ad17c14cb587e1f342a2ef2121',78,1,'2017-12-08 18:40:11','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'4ff43b30257e44ceacafc8275140505d','','2a922aff15fa41ce8debbde99577eaec'),
('c356b4b92caf496d93543a9cf64d29c8',74,5,'2017-12-08 18:19:35','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'9352e9a096f6427188e62cab3843d932','182435','2a922aff15fa41ce8debbde99577eaec'),
('c4eebe8ef2c546c2b6f1ea5166eeb50a',33,1,'2017-12-08 16:27:53','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'4f5ee6c7f0bf48219edff1dd5cc0c582','','90f1ceab1ced47bc949077f982ce0774'),
('c73c7886fef647eaab75b9d0d510aede',15,1,'2017-12-08 15:43:34','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'b776944b111b4445b27cf771e5f41ee5','182435','90f1ceab1ced47bc949077f982ce0774'),
('c7ba492b9cf34f74810c14d05deb423b',41,1,'2017-12-08 17:05:35','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'233ca28fb2fb42839aa0edcd35b64425','112335','90f1ceab1ced47bc949077f982ce0774'),
('d02c6766f93f47ebb577d9959ca30fc5',22,4,'2017-12-08 16:08:49','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'0adbc0cbd3b54881849c6f4538e127b4','112335','90f1ceab1ced47bc949077f982ce0774'),
('d14507c97e494795a54ededb23fca633',42,4,'2017-12-08 17:19:27','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'01f69596b213484cae5ec2447a78049b','112335','90f1ceab1ced47bc949077f982ce0774'),
('d2d3173170ae423ab96cd50efd990d75',63,1,'2017-12-08 18:12:44','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'0cda13c5d3d24c10b9f2217230ed9da9','','90f1ceab1ced47bc949077f982ce0774'),
('d31eb7964433427bb317790dfbc02638',54,1,'2017-12-08 17:56:20','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'131b37ee4f9b40478028168c9025ee04','112335','90f1ceab1ced47bc949077f982ce0774'),
('d7aa88788c3e477fa5e6547b981a382b',47,2,'2017-12-08 17:26:33','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'781fcb5b99b74f30bf78171b99c29bfb','','90f1ceab1ced47bc949077f982ce0774'),
('d8cea10ad9894144aa55a94eaaee5f84',45,2,'2017-12-08 17:23:17','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'67a5406bd1e5478bb8e72ef40340c9db','','2a922aff15fa41ce8debbde99577eaec'),
('dd8a9013959b4c71b1b34dd4442cacb8',61,3,'2017-12-08 18:06:29','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'0ba4301aef564b60a2540c0c98f271a3','','90f1ceab1ced47bc949077f982ce0774'),
('e0303269349046fa97cd9c7345edcbab',25,1,'2017-12-08 16:16:11','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'df4e4b1d6fce44fe83f5c8e19c874fb0','112335','90f1ceab1ced47bc949077f982ce0774'),
('e07c7f1077994ff496c8bd396d44e384',66,1,'2017-12-08 18:14:12','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'136e78618d3e48e2b5e5c782c7c15df2','','90f1ceab1ced47bc949077f982ce0774'),
('e1c195b573a44c66878afeff9e4fa5da',31,1,'2017-12-08 16:25:31','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'8c7e96ad745845918f49df515107c642','182435','90f1ceab1ced47bc949077f982ce0774'),
('e1dcf3d732fb45db879a1850c8962174',65,1,'2017-12-08 18:14:10','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'136e78618d3e48e2b5e5c782c7c15df2','','90f1ceab1ced47bc949077f982ce0774'),
('e536fa1cdf1f4719846bdad61d78759e',17,1,'2017-12-08 15:47:45','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'55ba23ea37aa4320aeae0894497cdb43','182435','90f1ceab1ced47bc949077f982ce0774'),
('eaa4b0e03df94bb4b644fcb1716af251',58,3,'2017-12-08 18:02:12','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'6fc050a995a14bff806a86c27412db70','182435','90f1ceab1ced47bc949077f982ce0774'),
('f01ec37635c442589854102a48796809',26,1,'2017-12-08 16:16:30','7a8693d73b4c4ac7b45bbc69dfeda0d2',0,1,'83c44448449c4827ac8c127e3f3d5ceb','','90f1ceab1ced47bc949077f982ce0774'),
('f3986516fc804a70b0bf40c69f0ee647',64,1,'2017-12-08 18:12:48','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'0cda13c5d3d24c10b9f2217230ed9da9','','90f1ceab1ced47bc949077f982ce0774'),
('f3ae1d100a174f929146617132cbf0f7',72,4,'2017-12-08 18:18:57','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'9352e9a096f6427188e62cab3843d932','','2a922aff15fa41ce8debbde99577eaec'),
('f6311a44a54449c9bf8fa04590eec83b',32,1,'2017-12-08 16:26:06','6bfe568f04bc40138e1e74b42f7b9bbd',0,1,'8c48098022e84603bafc290698ff6c16','182435','90f1ceab1ced47bc949077f982ce0774'),
('fce5d4d1ee7c421f80d736ef6cdc6a4f',28,1,'2017-12-08 16:18:15','7ef9d05a616c4d4a9e2009a9e84f7060',0,1,'14727acb4a8847d19c69e92c98fbb826','','2a922aff15fa41ce8debbde99577eaec');

/*Table structure for table `tb_ticket` */

DROP TABLE IF EXISTS `tb_ticket`;

CREATE TABLE `tb_ticket` (
  `ticket_id` varchar(32) NOT NULL COMMENT 'token主键',
  `ticket` varchar(128) NOT NULL COMMENT 'access_token',
  `expire_in` int(2) NOT NULL DEFAULT '6000' COMMENT '过期时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`ticket_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_ticket` */

/*Table structure for table `tb_track` */

DROP TABLE IF EXISTS `tb_track`;

CREATE TABLE `tb_track` (
  `track_id` varchar(32) NOT NULL COMMENT '物流表',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proorder_id` varchar(32) DEFAULT NULL COMMENT '订单表id',
  `com` varchar(32) NOT NULL COMMENT '物流公司编号',
  `num` varchar(32) NOT NULL COMMENT '快递单编号',
  `track_name` varchar(32) NOT NULL COMMENT '物流公司名称',
  PRIMARY KEY (`track_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_track` */

/*Table structure for table `tb_wechat_auth` */

DROP TABLE IF EXISTS `tb_wechat_auth`;

CREATE TABLE `tb_wechat_auth` (
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `wechat_id` varchar(32) NOT NULL COMMENT '主键',
  `wechat_name` varchar(100) DEFAULT NULL COMMENT '微信名称',
  `openid` varchar(500) NOT NULL DEFAULT '' COMMENT '微信openid',
  `wechat_headimg` varchar(500) NOT NULL COMMENT '微信头像',
  `wechat_sex` varchar(2) NOT NULL COMMENT '微信性别',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `personinfo_id` varchar(32) DEFAULT NULL COMMENT '外键：个人信息表主键',
  `num` int(2) NOT NULL COMMENT '第几位',
  `address` varchar(128) NOT NULL COMMENT '地址',
  PRIMARY KEY (`wechat_id`),
  UNIQUE KEY `idx_openid` (`openid`),
  UNIQUE KEY `idx_personinfo_id` (`personinfo_id`),
  KEY `idx_wechat_id` (`id`),
  CONSTRAINT `fk_personinfo_pid` FOREIGN KEY (`personinfo_id`) REFERENCES `tb_personinfo` (`personinfo_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

/*Data for the table `tb_wechat_auth` */

insert  into `tb_wechat_auth`(`id`,`wechat_id`,`wechat_name`,`openid`,`wechat_headimg`,`wechat_sex`,`create_time`,`personinfo_id`,`num`,`address`) values 
(64,'514e85888c794f5a978e0bb33bb82506','韩永雷','o22h00VprxMHzx1-eWkjvV6Eh6xw','http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLk4uzJdPQfaqAu7jKlkjxcgalvcO0zic15VjbBfhSMv2xrP2ibq1RHBIsJlBtyUt8UeQQht7o6zcsA/0','1','2017-12-05 18:29:18','999e5959c13a4acb856f02859ea8e418',3,'中国区河南省郑州市'),
(62,'7ba30b539caa42e4a66152a20641a607','Sakura。','o22h00R7uwx3IjTCSqBCcJSsCygI','http://wx.qlogo.cn/mmopen/vi_32/QKwesY1bGxGCibbYBB8cGWWUnujvG2H2jEOC6hVoNia88NxTyRUCf0ibtzQy4oFve9osUs0iarhDvXbic0Kvj4yy20w/0','1','2017-11-28 16:46:53','90f1ceab1ced47bc949077f982ce0774',1,'中国区辽宁省朝阳市'),
(63,'83f73a61a8d746029711a64703e01af9','哎呦喂','o22h00XyvxWc96iPA23YOnr9mKgc','http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJNiaqn4rUO9pgOjRE8Wn5jsFqCOmLIRibDwib1e6r1I0QkbH6gcgJPumiaEgvicOYmxtH22TL2YWIalWg/0','1','2017-12-01 18:27:48','2a922aff15fa41ce8debbde99577eaec',2,'中国区辽宁省沈阳市');

/*Table structure for table `tb_win` */

DROP TABLE IF EXISTS `tb_win`;

CREATE TABLE `tb_win` (
  `win_id` varchar(32) NOT NULL COMMENT '中奖表主键',
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `personinfo_id` varchar(32) DEFAULT NULL COMMENT '外键：个人信息表主键',
  `reward_id` varchar(32) DEFAULT NULL COMMENT '外键：奖品表主键',
  PRIMARY KEY (`win_id`),
  KEY `id` (`id`),
  KEY `personinfo_id` (`personinfo_id`),
  KEY `reward_id` (`reward_id`),
  CONSTRAINT `tb_win_ibfk_1` FOREIGN KEY (`personinfo_id`) REFERENCES `tb_personinfo` (`personinfo_id`) ON DELETE SET NULL,
  CONSTRAINT `tb_win_ibfk_2` FOREIGN KEY (`reward_id`) REFERENCES `tb_reward` (`reward_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_win` */

/*Table structure for table `tb_worldimg` */

DROP TABLE IF EXISTS `tb_worldimg`;

CREATE TABLE `tb_worldimg` (
  `worldimg_id` varchar(32) NOT NULL COMMENT '游戏世界界面图资源表主键',
  `worldimg_url` varchar(200) NOT NULL COMMENT '链接',
  `priority` int(2) NOT NULL COMMENT '权重',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `enable_status` int(2) NOT NULL COMMENT '0不可用1可用',
  PRIMARY KEY (`worldimg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_worldimg` */

insert  into `tb_worldimg`(`worldimg_id`,`worldimg_url`,`priority`,`create_time`,`enable_status`) values 
('14630784c43840c1afa6bfa821c3cea9','/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',1,'2017-12-22 14:27:57',1),
('4f4f6001ea6045c787ffa077363c9303','/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',2,'2017-12-06 14:28:38',1),
('f6004a6926294c508b71939b892c1fdb','/upload/8707bef6ce22848dc4e5ffdc61102748.jpg',3,'2017-12-13 14:28:58',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
