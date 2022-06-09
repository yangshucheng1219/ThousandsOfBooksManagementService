-- MySQL dump 10.13  Distrib 5.7.31, for macos10.14 (x86_64)
--
-- Host: 211.159.175.128    Database: o2o
-- ------------------------------------------------------
-- Server version	5.6.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_area`
--

DROP TABLE IF EXISTS `tb_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_area` (
  `area_id` int(2) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(200) NOT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `UK_AREA` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_area`
--

LOCK TABLES `tb_area` WRITE;
/*!40000 ALTER TABLE `tb_area` DISABLE KEYS */;
INSERT INTO `tb_area` VALUES (3,'西苑',1,'2021-04-04 19:13:18','2021-05-08 14:39:48'),(4,'南苑',0,'2021-05-04 19:13:18','2021-06-04 19:13:18'),(5,'北苑',0,'2021-05-04 19:13:18','2021-06-04 19:13:18'),(6,'东苑',2,'2021-05-04 19:13:18','2021-06-04 14:40:05'),(7,'北北',2,'2021-05-08 14:41:20','2021-06-04 14:41:20');
/*!40000 ALTER TABLE `tb_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_award`
--

DROP TABLE IF EXISTS `tb_award`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_award` (
  `award_id` int(10) NOT NULL AUTO_INCREMENT,
  `award_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `award_desc` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `award_img` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `point` int(10) NOT NULL DEFAULT '0',
  `priority` int(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `shop_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`award_id`),
  KEY `fk_award_shop_idx` (`shop_id`),
  CONSTRAINT `fk_award_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_award`
--

LOCK TABLES `tb_award` WRITE;
/*!40000 ALTER TABLE `tb_award` DISABLE KEYS */;
INSERT INTO `tb_award` VALUES (13,'美式咖啡','咖啡','/upload/images/item/shop/15/2017060523302118864.jpg',6,6,NULL,NULL,1,20),(14,'红豆奶茶','红豆奶茶','/upload/images/item/shop/20/2017060620363014331.jpg',5,5,NULL,NULL,1,20),(15,'绿豆冰','绿豆冰','/upload/images/item/shop/20/2017060620384620536.jpg',3,7,NULL,NULL,1,20);
/*!40000 ALTER TABLE `tb_award` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_head_line`
--

DROP TABLE IF EXISTS `tb_head_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_head_line` (
  `line_id` int(100) NOT NULL AUTO_INCREMENT,
  `line_name` varchar(1000) DEFAULT NULL,
  `line_link` varchar(2000) NOT NULL,
  `line_img` varchar(2000) NOT NULL,
  `priority` int(2) DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_head_line`
--

LOCK TABLES `tb_head_line` WRITE;
/*!40000 ALTER TABLE `tb_head_line` DISABLE KEYS */;
INSERT INTO `tb_head_line` VALUES (11,'1','1','/upload/images/item/headtitle/2017061320315746624.jpg',1,1,'2021-05-13 20:31:57','2021-06-01 20:31:57'),(12,'2','2','/upload/images/item/headtitle/2017061320371786788.jpg',2,1,'2021-05-13 20:37:17','2021-06-01 20:37:17'),(14,'3','3','/upload/images/item/headtitle/2017061320393452772.jpg',3,1,'2021-05-13 20:39:34','2021-06-02 20:39:34'),(15,'4','4','/upload/images/item/headtitle/2017061320400198256.jpg',5,1,'2021-05-13 20:40:01','2021-06-01 16:53:05');
/*!40000 ALTER TABLE `tb_head_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_local_auth`
--

DROP TABLE IF EXISTS `tb_local_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_local_auth` (
  `local_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `username` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`local_auth_id`),
  UNIQUE KEY `uk_local_profile` (`username`),
  KEY `fk_localauth_profile` (`user_id`),
  CONSTRAINT `fk_localauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_local_auth`
--

LOCK TABLES `tb_local_auth` WRITE;
/*!40000 ALTER TABLE `tb_local_auth` DISABLE KEYS */;
INSERT INTO `tb_local_auth` VALUES (14,8,'yang','s05bse6q2qlb9qblls96s592y55y556s',NULL,NULL),(17,11,'test','s05bse6q2qlb9qblls96s592y55y556s',NULL,NULL),(18,12,'测试一下','s05bse6q2qlb9qblls96s592y55y556s',NULL,NULL);
/*!40000 ALTER TABLE `tb_local_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_person_info`
--

DROP TABLE IF EXISTS `tb_person_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_person_info` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `profile_img` varchar(1024) DEFAULT NULL,
  `email` varchar(1024) DEFAULT NULL,
  `gender` varchar(2) DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '0:禁止使用本商城，1:允许使用本商城',
  `user_type` int(2) NOT NULL DEFAULT '1' COMMENT '1:顾客，2:店家，3:超级管理员',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_person_info`
--

LOCK TABLES `tb_person_info` WRITE;
/*!40000 ALTER TABLE `tb_person_info` DISABLE KEYS */;
INSERT INTO `tb_person_info` VALUES (8,'杨','http://wx.qlogo.cn/mmopen/XZumId0qMA815ApfWI2zibDnRMahic6SU0wHib2HgGJj5narL2ymRaI4Kn2Tx2Q8UfkicibvjVicu3De6fDYRMfo0uGW0SGicibxVnJ9/0',NULL,'1',1,3,'2021-06-04 19:01:09','2021-06-05 19:01:09'),(11,'淼仙','http://wx.qlogo.cn/mmopen/XZumId0qMA815ApfWI2zibDnRMahic6SU0wHib2HgGJj5narL2ymRaI4Kn2Tx2Q8UfkicibvjVicu3De6fDYRMfo0uGW0SGicibxVnJ9/0',NULL,'1',1,1,NULL,NULL),(12,'测试一下',NULL,NULL,NULL,1,1,'2021-05-08 17:56:49',NULL),(13,'呜呜呜呜测试一下',NULL,NULL,NULL,1,1,'2021-05-25 12:35:40',NULL),(14,'Django','https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLJpYuZ0hOPlBXovvKdlrjSibKx7ZtcMiaRC92JTpUia6dgfIcAiaawqgrXYtm57veiaeu1Dyuenc3umCw/132',NULL,'1',1,1,'2021-05-28 10:55:25',NULL);
/*!40000 ALTER TABLE `tb_person_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_product`
--

DROP TABLE IF EXISTS `tb_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_product` (
  `product_id` int(100) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `product_desc` varchar(2000) DEFAULT NULL,
  `img_addr` varchar(2000) DEFAULT '',
  `normal_price` varchar(100) DEFAULT NULL,
  `promotion_price` varchar(100) DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `product_category_id` int(11) DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT '0',
  `point` int(10) DEFAULT '0',
  PRIMARY KEY (`product_id`),
  KEY `fk_product_procate` (`product_category_id`),
  KEY `fk_product_shop` (`shop_id`),
  CONSTRAINT `fk_product_procate` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`),
  CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_product`
--

LOCK TABLES `tb_product` WRITE;
/*!40000 ALTER TABLE `tb_product` DISABLE KEYS */;
INSERT INTO `tb_product` VALUES (4,'美式咖啡','一丝醇香，流连忘返','/upload/images/item/shop/15/2017060523302118864.jpg','12','11',12,'2017-06-05 23:30:21','2017-06-05 23:49:34',1,NULL,15,3),(5,'转让八成新XX牌小车','诚心转让八成新XX牌小车，有意者请连续8866666','/upload/images/item/shop/15/2017060523485289817.jpg','100000','60000',100,'2017-06-05 23:48:52','2017-06-05 23:48:52',1,9,15,0),(6,'转让电瓶车一辆','转让电瓶车一辆，可当面看车，电话：1111222','/upload/images/item/shop/15/2017060608490188656.jpg','3000','1200',99,'2017-06-06 08:49:01','2017-06-06 08:50:57',1,9,15,0),(7,'转让半新旧男装摩托车一辆','转让半新旧男装摩托车一辆，当面验车，电话：3333666','/upload/images/item/shop/15/2017060608502085437.jpg','8000','3000',98,'2017-06-06 08:50:20','2017-06-06 08:51:19',1,9,15,0),(8,'大量二手书籍转让','大量二手书籍转让，电话详谈，或上门看书。联系电话：5556666   地址：东苑XX楼','/upload/images/item/shop/16/2017060608574074561.jpg','0','0',100,'2017-06-06 08:57:40','2017-06-06 08:57:40',1,10,16,0),(9,'<十万个为什么>','出手一本《十万个为什么》，8成新，想要的可以联系：9998886','/upload/images/item/shop/16/2017060609025850665.png','25','10',98,'2017-06-06 09:02:58','2017-06-06 09:02:58',1,10,16,0),(10,'珍珠奶茶','珍珠奶茶，弹性十足，香甜美味。','/upload/images/item/shop/20/2017060620114126875.jpg','10','8',100,'2017-06-06 20:11:41','2017-06-06 20:11:41',1,11,20,0),(11,'红豆奶茶','红豆和奶茶的完美结合，夏天不错的选择。','/upload/images/item/shop/20/2017060620363014331.jpg','10','8',99,'2017-06-06 20:36:30','2017-06-06 20:36:30',1,11,20,1),(12,'绿豆冰','清热解毒。','/upload/images/item/shop/20/2017060620384620536.jpg','8','7',98,'2017-06-06 20:38:46','2017-06-06 20:38:46',1,11,20,5),(13,'芒果冰沙','新鲜芒果制作。','/upload/images/item/shop/20/2017060620472125629.jpg','15','13',95,'2017-06-06 20:47:21','2017-06-06 20:47:21',1,11,20,2),(14,'鲜榨芒果汁','新鲜芒果新鲜榨，香甜可口，解暑降温。','/upload/images/item/shop/20/2017060620492297296.jpg','8','8',93,'2017-06-06 20:49:22','2017-06-06 20:49:22',1,11,20,3),(15,'鲜榨西瓜汁','每一杯都是鲜榨的，现榨现卖。','/upload/images/item/shop/20/2017060621052824735.jpg','8','8',90,'2017-06-06 21:05:28','2017-06-06 21:05:28',1,11,20,6),(16,'咖啡','非常好喝','/upload/images/item/shop/20/2021052815264870991.png','99','9',21,'2021-05-28 06:54:50','2021-05-28 07:27:06',1,11,20,12),(18,'SAD','12123','/upload/images/item/shop/20/2021052816201075679.jpg','122','21',123,'2021-05-28 08:20:11','2021-05-28 08:20:11',1,11,20,12),(19,'happy奶茶','1312','/upload/images/item/shop/20/2021052816234681296.jpg','12','2',13,'2021-05-28 08:23:47','2021-05-28 08:23:47',1,11,20,13),(24,'旺旺','22 ','/upload/images/item/shop/20/2021052819094191011.jpg','12','2',129,'2021-05-28 08:58:12','2021-05-28 11:09:42',1,11,20,12),(25,'喝不动','好喝','/upload/images/item/shop/27/2021052817225797369.jpg','211','21',12,'2021-05-28 09:14:46','2021-05-28 09:22:58',1,18,27,21),(26,'欣欣','122','/upload/images/item/shop/27/2021052817260696684.jpg','21','12',21,'2021-05-28 09:26:06','2021-05-28 09:26:06',1,18,27,21),(27,'来吧','好喝','/upload/images/item/shop/27/2021052818454236441.jpg','21','11',12,'2021-05-28 09:40:51','2021-05-28 10:45:42',1,18,27,21);
/*!40000 ALTER TABLE `tb_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_product_category`
--

DROP TABLE IF EXISTS `tb_product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_product_category` (
  `product_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_category_name` varchar(100) NOT NULL,
  `priority` int(2) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_category_id`),
  KEY `fk_procate_shop` (`shop_id`),
  CONSTRAINT `fk_procate_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_product_category`
--

LOCK TABLES `tb_product_category` WRITE;
/*!40000 ALTER TABLE `tb_product_category` DISABLE KEYS */;
INSERT INTO `tb_product_category` VALUES (9,'二手车',100,NULL,15),(10,'二手书籍',100,NULL,16),(11,'奶茶',100,NULL,20),(12,'咖啡',50,NULL,20),(13,'甜品',30,NULL,20),(14,'小吃',20,NULL,20),(15,'茗茶',10,NULL,20),(16,'七层新书',10,NULL,16),(17,'五层旧书',5,NULL,16),(18,'奶茶',12,NULL,27);
/*!40000 ALTER TABLE `tb_product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_product_img`
--

DROP TABLE IF EXISTS `tb_product_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_product_img` (
  `product_img_id` int(20) NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(2000) NOT NULL,
  `img_desc` varchar(2000) DEFAULT NULL,
  `priority` int(2) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `product_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`product_img_id`),
  KEY `fk_proimg_product` (`product_id`),
  CONSTRAINT `fk_proimg_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_product_img`
--

LOCK TABLES `tb_product_img` WRITE;
/*!40000 ALTER TABLE `tb_product_img` DISABLE KEYS */;
INSERT INTO `tb_product_img` VALUES (19,'/upload/images/item/shop/15/20170605233021865310.jpg',NULL,NULL,'2017-06-05 23:30:22',4),(20,'/upload/images/item/shop/15/20170605233022618071.jpg',NULL,NULL,'2017-06-05 23:30:22',4),(21,'/upload/images/item/shop/15/20170605233022246642.jpg',NULL,NULL,'2017-06-05 23:30:22',4),(22,'/upload/images/item/shop/15/20170605234852321010.jpg',NULL,NULL,'2017-06-05 23:48:52',5),(23,'/upload/images/item/shop/15/20170606084902162950.jpg',NULL,NULL,'2017-06-06 08:49:02',6),(24,'/upload/images/item/shop/15/20170606085020558290.jpg',NULL,NULL,'2017-06-06 08:50:20',7),(25,'/upload/images/item/shop/16/20170606085740956160.jpg',NULL,NULL,'2017-06-06 08:57:40',8),(26,'/upload/images/item/shop/16/20170606090259397060.png',NULL,NULL,'2017-06-06 09:02:59',9),(27,'/upload/images/item/shop/20/20170606201141425050.jpg',NULL,NULL,'2017-06-06 20:11:42',10),(28,'/upload/images/item/shop/20/20170606201141387851.jpg',NULL,NULL,'2017-06-06 20:11:42',10),(29,'/upload/images/item/shop/20/20170606201141503752.png',NULL,NULL,'2017-06-06 20:11:42',10),(30,'/upload/images/item/shop/20/20170606203630923430.jpg',NULL,NULL,'2017-06-06 20:36:31',11),(31,'/upload/images/item/shop/20/20170606203631552081.png',NULL,NULL,'2017-06-06 20:36:31',11),(32,'/upload/images/item/shop/20/20170606203631972862.jpg',NULL,NULL,'2017-06-06 20:36:31',11),(33,'/upload/images/item/shop/20/20170606203846623120.jpg',NULL,NULL,'2017-06-06 20:38:47',12),(34,'/upload/images/item/shop/20/20170606204721744860.jpg',NULL,NULL,'2017-06-06 20:47:21',13),(35,'/upload/images/item/shop/20/20170606204922968580.jpg',NULL,NULL,'2017-06-06 20:49:23',14),(36,'/upload/images/item/shop/20/20170606210528529220.jpg',NULL,NULL,'2017-06-06 21:05:28',15),(37,'/upload/images/item/shop/20/20170606210528132921.jpg',NULL,NULL,'2017-06-06 21:05:28',15),(46,'/upload/images/item/shop/20/2021052815264867323.jpg',NULL,NULL,'2021-05-28 07:26:49',16),(47,'/upload/images/item/shop/20/2021052815264840482.jpg',NULL,NULL,'2021-05-28 07:26:49',16),(48,'/upload/images/item/shop/20/2021052816201032132.jpg',NULL,NULL,'2021-05-28 08:20:11',18),(49,'/upload/images/item/shop/20/2021052816234699166.jpg',NULL,NULL,'2021-05-28 08:23:47',19),(53,'/upload/images/item/shop/27/2021052817225738343.jpg',NULL,NULL,'2021-05-28 09:22:58',25),(54,'/upload/images/item/shop/27/2021052817260638718.jpg',NULL,NULL,'2021-05-28 09:26:07',26),(55,'/upload/images/item/shop/27/2021052817260692780.jpg',NULL,NULL,'2021-05-28 09:26:07',26),(58,'/upload/images/item/shop/27/2021052818454240406.jpg',NULL,NULL,'2021-05-28 10:45:42',27),(59,'/upload/images/item/shop/20/2021052819094141370.jpg',NULL,NULL,'2021-05-28 11:09:42',24),(60,'/upload/images/item/shop/20/2021052819094255583.jpg',NULL,NULL,'2021-05-28 11:09:42',24),(61,'/upload/images/item/shop/20/2021052819094243731.jpg',NULL,NULL,'2021-05-28 11:09:43',24);
/*!40000 ALTER TABLE `tb_product_img` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_product_sell_daily`
--

DROP TABLE IF EXISTS `tb_product_sell_daily`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_product_sell_daily` (
  `product_sell_daily_id` int(100) NOT NULL AUTO_INCREMENT,
  `product_id` int(100) DEFAULT NULL,
  `shop_id` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `total` int(10) DEFAULT '0',
  PRIMARY KEY (`product_sell_daily_id`),
  UNIQUE KEY `uc_product_sell` (`product_id`,`shop_id`,`create_time`),
  KEY `fk_product_sell_product` (`product_id`),
  KEY `fk_product_sell_shop` (`shop_id`),
  CONSTRAINT `fk_product_sell_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`),
  CONSTRAINT `fk_product_sell_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_product_sell_daily`
--

LOCK TABLES `tb_product_sell_daily` WRITE;
/*!40000 ALTER TABLE `tb_product_sell_daily` DISABLE KEYS */;
INSERT INTO `tb_product_sell_daily` VALUES (87,12,20,'2017-12-18 00:00:00',1),(88,12,20,'2017-12-17 00:00:00',3),(89,12,20,'2017-12-16 00:00:00',2),(90,12,20,'2017-12-15 00:00:00',5),(91,12,20,'2017-12-14 00:00:00',3),(92,12,20,'2017-12-13 00:00:00',4),(93,12,20,'2017-12-12 00:00:00',2),(96,13,20,'2017-12-18 00:00:00',2),(97,13,20,'2017-12-17 00:00:00',2),(98,13,20,'2017-12-16 00:00:00',3),(99,13,20,'2017-12-15 00:00:00',3),(100,13,20,'2017-12-14 00:00:00',4),(101,13,20,'2017-12-13 00:00:00',4),(102,13,20,'2017-12-12 00:00:00',5),(103,14,20,'2017-12-18 00:00:00',7),(104,14,20,'2017-12-17 00:00:00',6),(105,14,20,'2017-12-16 00:00:00',5),(106,14,20,'2017-12-15 00:00:00',4),(107,14,20,'2017-12-14 00:00:00',1),(108,14,20,'2017-12-13 00:00:00',2),(109,14,20,'2017-12-12 00:00:00',3),(110,4,15,'2017-12-18 00:00:00',0),(111,5,15,'2017-12-18 00:00:00',0),(112,6,15,'2017-12-18 00:00:00',0),(113,7,15,'2017-12-18 00:00:00',0),(114,8,16,'2017-12-18 00:00:00',0),(115,9,16,'2017-12-18 00:00:00',0),(116,10,20,'2017-12-18 00:00:00',0),(117,11,20,'2017-12-18 00:00:00',0),(118,15,20,'2017-12-18 00:00:00',0),(125,4,15,'2018-01-07 00:00:00',0),(126,5,15,'2018-01-07 00:00:00',0),(127,6,15,'2018-01-07 00:00:00',0),(128,7,15,'2018-01-07 00:00:00',0),(129,8,16,'2018-01-07 00:00:00',0),(130,9,16,'2018-01-07 00:00:00',0),(131,10,20,'2018-01-07 00:00:00',0),(132,11,20,'2018-01-07 00:00:00',0),(133,12,20,'2018-01-07 00:00:00',0),(134,13,20,'2018-01-07 00:00:00',0),(135,14,20,'2018-01-07 00:00:00',0),(136,15,20,'2018-01-07 00:00:00',0),(137,13,20,'2021-05-30 00:00:00',1),(138,16,20,'2021-05-30 00:00:00',1),(139,19,20,'2021-05-30 00:00:00',1),(140,24,20,'2021-05-30 00:00:00',1),(144,4,15,'2021-06-01 00:00:00',0),(145,5,15,'2021-06-01 00:00:00',0),(146,6,15,'2021-06-01 00:00:00',0),(147,7,15,'2021-06-01 00:00:00',0),(148,8,16,'2021-06-01 00:00:00',0),(149,9,16,'2021-06-01 00:00:00',0),(150,10,20,'2021-06-01 00:00:00',0),(151,11,20,'2021-06-01 00:00:00',0),(152,12,20,'2021-06-01 00:00:00',0),(153,13,20,'2021-06-01 00:00:00',0),(154,14,20,'2021-06-01 00:00:00',0),(155,15,20,'2021-06-01 00:00:00',0),(156,16,20,'2021-06-01 00:00:00',0),(157,18,20,'2021-06-01 00:00:00',0),(158,19,20,'2021-06-01 00:00:00',0),(159,24,20,'2021-06-01 00:00:00',0),(160,25,27,'2021-06-01 00:00:00',0),(161,26,27,'2021-06-01 00:00:00',0),(162,27,27,'2021-06-01 00:00:00',0);
/*!40000 ALTER TABLE `tb_product_sell_daily` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_shop`
--

DROP TABLE IF EXISTS `tb_shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_shop` (
  `shop_id` int(10) NOT NULL AUTO_INCREMENT,
  `owner_id` int(10) NOT NULL COMMENT '店铺创建人',
  `area_id` int(5) DEFAULT NULL,
  `shop_category_id` int(11) DEFAULT NULL,
  `shop_name` varchar(256) NOT NULL,
  `shop_desc` varchar(1024) DEFAULT NULL,
  `shop_addr` varchar(200) DEFAULT NULL,
  `phone` varchar(128) DEFAULT NULL,
  `shop_img` varchar(1024) DEFAULT NULL,
  `priority` int(3) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `advice` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `fk_shop_area` (`area_id`),
  KEY `fk_shop_profile` (`owner_id`),
  KEY `fk_shop_shopcate` (`shop_category_id`),
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`),
  CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_shop_shopcate` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_shop`
--

LOCK TABLES `tb_shop` WRITE;
/*!40000 ALTER TABLE `tb_shop` DISABLE KEYS */;
INSERT INTO `tb_shop` VALUES (15,8,3,14,'二手车辆','二手汽车、摩托车、电车等交通工具交易信息。','面向全市','0000000','/upload/images/item/shop/15/2017060522042982266.png',100,'2021-05-05 22:04:29','2021-06-05 10:50:16',1,NULL),(16,8,3,15,'旧书籍交易','旧书籍交易信息','旧书籍交易板块','0000000','/upload/images/item/shop/16/2017060608534289617.png',99,'2021-05-05 08:53:42','2021-06-06 08:54:40',1,NULL),(17,8,3,17,'靓仔靓妹美容护理中心','二十年手艺，专业护理秀发受损头发。美容美发首选。','东苑北面二号门','4445556','/upload/images/item/shop/17/2017060609084595067.jpg',0,'2021-05-05 09:08:45','2021-06-06 08:54:40',1,NULL),(18,8,3,18,'一剪没理发中心','专业洗剪吹，又好又便宜。','东苑北面3号门面','9998887','/upload/images/item/shop/18/2017060609110899956.jpg',0,'2021-05-05 09:11:08','2021-06-06 09:45:38',1,NULL),(19,8,4,20,'吃得饱大排档','吃得好又吃得饱，朋友聚会好地方。可预约。','南苑东面10号门面','1234567','/upload/images/item/shop/19/2017060609140699548.jpg',0,'2021-05-05 09:14:06','2021-06-06 09:45:43',1,NULL),(20,8,4,22,'香喷喷奶茶店','鲜榨果汁、奶茶等饮品。','南苑东面5号门面','77788444','/upload/images/item/shop/20/2017060609163395401.jpg',30,'2021-05-05 09:16:33','2021-06-07 16:24:07',1,''),(21,8,5,25,'海陆空量贩KTV','订包厢电话：8889997。节假日请预约。','西苑1号门面','8889997','/upload/images/item/shop/21/2017060609194286080.jpg',0,'2021-05-05 09:19:42','2021-06-07 09:45:59',1,NULL),(22,8,5,24,'幽城室逃生娱乐城','考验你的智商，和小伙伴们一起来挑战吧。','西苑3号楼第二层','6666333','/upload/images/item/shop/22/2017060609223853062.jpg',0,'2021-05-05 09:22:38','2021-06-07 09:46:04',1,NULL),(23,8,6,29,'威水程序设计培训教育','保教抱会，前途无量。','北苑2栋5楼','66633111','/upload/images/item/shop/23/2017060609275777519.png',0,'2021-05-05 09:27:57','2021-06-07 09:46:09',1,NULL),(24,8,6,30,'武林风舞蹈培训','专业培训舞蹈，声乐。','北苑9懂10楼','5555555','/upload/images/item/shop/24/2017060609354459045.png',1,'2021-05-05 09:35:44','2021-06-07 17:19:25',1,''),(25,8,6,14,'易行交通工具租赁服务中心','本店租赁各种汽车，摩托车等。详情请拨打电话咨询。电话：2222222','1栋3号4号门面','2222222','/upload/images/item/shop/25/2017060609381150709.png',40,'2021-05-05 09:38:11','2021-06-07 19:58:32',1,NULL),(26,8,6,31,'有声有色','出租各种演出道具，乐器，服装等。','北苑15号门面','7777777','/upload/images/item/shop/26/2017060609431259039.png',41,'2021-05-05 09:43:12','2021-06-07 19:58:45',1,NULL),(27,8,3,22,'冰冻夏天奶茶店','本店出售各种冷饮，奶茶，冰花，鲜榨果汁。','东苑7懂2号门面','8889999','/upload/images/item/shop/27/2017060715512185473.jpg',10,'2021-05-05 15:51:21','2021-06-07 16:22:28',1,''),(28,11,6,14,'欣欣旧车行','全部十八成新','武汉理工大学东教1-111','12312312','\\upload\\item\\shop\\28\\2021052814271387196.jpg',NULL,'2021-05-05 06:27:13','2021-06-07 06:27:13',0,'审核中');
/*!40000 ALTER TABLE `tb_shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_shop_auth_map`
--

DROP TABLE IF EXISTS `tb_shop_auth_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_shop_auth_map` (
  `shop_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `employee_id` int(10) NOT NULL,
  `shop_id` int(10) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `title_flag` int(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`shop_auth_id`),
  KEY `fk_shop_auth_map_shop` (`shop_id`),
  KEY `uk_shop_auth_map` (`employee_id`,`shop_id`),
  CONSTRAINT `fk_shop_auth_map_employee` FOREIGN KEY (`employee_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_shop_auth_map_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_shop_auth_map`
--

LOCK TABLES `tb_shop_auth_map` WRITE;
/*!40000 ALTER TABLE `tb_shop_auth_map` DISABLE KEYS */;
INSERT INTO `tb_shop_auth_map` VALUES (3,11,20,'店家',0,NULL,NULL,1),(5,14,20,'员工',1,'2021-06-01 09:57:14','2021-06-01 09:57:14',1);
/*!40000 ALTER TABLE `tb_shop_auth_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_shop_category`
--

DROP TABLE IF EXISTS `tb_shop_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_shop_category` (
  `shop_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_category_name` varchar(100) NOT NULL DEFAULT '',
  `shop_category_desc` varchar(1000) DEFAULT '',
  `shop_category_img` varchar(2000) DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shop_category_id`),
  KEY `fk_shop_category_self` (`parent_id`),
  CONSTRAINT `fk_shop_category_self` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_shop_category`
--

LOCK TABLES `tb_shop_category` WRITE;
/*!40000 ALTER TABLE `tb_shop_category` DISABLE KEYS */;
INSERT INTO `tb_shop_category` VALUES (10,'二手市场','二手商品交易','/upload/images/item/shopcategory/2017061223272255687.png',100,'2021-05-04 20:10:58','2021-05-12 23:27:22',NULL),(11,'美容美发','美容美发','/upload/images/item/shopcategory/2017061223273314635.png',99,'2021-05-04 20:12:57','2021-06-08 16:52:50',NULL),(12,'美食饮品','美食饮品','/upload/images/item/shopcategory/2017061223274213433.png',98,'2021-05-04 20:15:21','2021-05-12 23:27:42',NULL),(13,'休闲娱乐','休闲娱乐','/upload/images/item/shopcategory/2017061223275121460.png',97,'2021-05-04 20:19:29','2021-05-12 23:27:51',NULL),(14,'旧车','旧车','/upload/images/item/shopcategory/2017060420315183203.png',80,'2021-05-04 20:31:51','2021-05-04 20:31:51',10),(15,'二手书籍','二手书籍','/upload/images/item/shopcategory/2017060420322333745.png',79,'2021-05-04 20:32:23','2021-05-04 20:32:23',10),(17,'护理','护理','/upload/images/item/shopcategory/2017060420372391702.png',76,'2021-05-04 20:37:23','2021-05-04 20:37:23',11),(18,'理发','理发','/upload/images/item/shopcategory/2017060420374775350.png',74,'2021-05-04 20:37:47','2021-05-04 20:37:47',11),(20,'大排档','大排档','/upload/images/item/shopcategory/2017060420460491494.png',59,'2021-05-04 20:46:04','2021-05-04 20:46:04',12),(22,'奶茶店','奶茶店','/upload/images/item/shopcategory/2017060420464594520.png',58,'2021-05-04 20:46:45','2021-05-04 20:46:45',12),(24,'密室逃生','密室逃生','/upload/images/item/shopcategory/2017060420500783376.png',56,'2021-05-04 20:50:07','2021-05-04 21:45:53',13),(25,'KTV','KTV','/upload/images/item/shopcategory/2017060420505834244.png',57,'2021-05-04 20:50:58','2021-05-04 20:51:14',13),(27,'培训教育','培训教育','/upload/images/item/shopcategory/2017061223280082147.png',96,'2021-05-04 21:51:36','2021-05-12 23:28:00',NULL),(28,'租赁市场','租赁市场','/upload/images/item/shopcategory/2017061223281361578.png',95,'2021-05-04 21:53:52','2021-05-12 23:28:13',NULL),(29,'程序设计','程序设计','/upload/images/item/shopcategory/2017060421593496807.png',50,'2021-05-04 21:59:34','2021-05-04 21:59:34',27),(30,'声乐舞蹈','声乐舞蹈','/upload/images/item/shopcategory/2017060421595843693.png',49,'2021-05-04 21:59:58','2021-05-04 21:59:58',27),(31,'演出道具','演出道具','/upload/images/item/shopcategory/2017060422114076152.png',45,'2021-05-04 22:11:40','2021-05-04 22:11:40',28),(32,'交通工具','交通工具','/upload/images/item/shopcategory/2017060422121144586.png',44,'2021-05-04 22:12:11','2021-05-04 22:12:11',28),(36,'test','test','/upload/images/item/shopcategory/2018010816553911795.JPG',2,'2021-06-09 16:55:39','2021-06-10 16:55:39',13);
/*!40000 ALTER TABLE `tb_shop_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_award_map`
--

DROP TABLE IF EXISTS `tb_user_award_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_award_map` (
  `user_award_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `award_id` int(10) NOT NULL,
  `shop_id` int(10) NOT NULL,
  `operator_id` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `used_status` int(2) NOT NULL DEFAULT '0',
  `point` int(10) DEFAULT NULL,
  PRIMARY KEY (`user_award_id`),
  KEY `fk_user_award_map_profile` (`user_id`),
  KEY `fk_user_award_map_award` (`award_id`),
  KEY `fk_user_award_map_shop` (`shop_id`),
  KEY `fk_user_award_map_operator` (`operator_id`),
  CONSTRAINT `fk_user_award_map_award` FOREIGN KEY (`award_id`) REFERENCES `tb_award` (`award_id`),
  CONSTRAINT `fk_user_award_map_operator` FOREIGN KEY (`operator_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_user_award_map_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_user_award_map_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_award_map`
--

LOCK TABLES `tb_user_award_map` WRITE;
/*!40000 ALTER TABLE `tb_user_award_map` DISABLE KEYS */;
INSERT INTO `tb_user_award_map` VALUES (1,12,13,27,11,'2021-05-31 03:45:38',1,1),(2,12,13,27,11,'2021-05-31 03:45:39',1,1),(3,14,15,20,14,'2021-06-04 09:52:40',1,3),(4,14,15,20,14,'2021-06-04 09:52:47',1,3),(5,14,13,20,14,'2021-06-04 09:52:49',1,6),(6,14,14,20,NULL,'2021-06-05 01:26:35',0,5),(7,14,15,20,NULL,'2021-06-05 01:26:47',0,3),(8,14,13,20,NULL,'2021-06-05 01:26:49',0,6);
/*!40000 ALTER TABLE `tb_user_award_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_product_map`
--

DROP TABLE IF EXISTS `tb_user_product_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_product_map` (
  `user_product_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `product_id` int(100) DEFAULT NULL,
  `shop_id` int(10) DEFAULT NULL,
  `operator_id` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `point` int(10) DEFAULT '0',
  PRIMARY KEY (`user_product_id`),
  KEY `fk_user_product_map_profile` (`user_id`),
  KEY `fk_user_product_map_product` (`product_id`),
  KEY `fk_user_product_map_shop` (`shop_id`),
  KEY `fk_user_product_map_operator` (`operator_id`),
  CONSTRAINT `fk_user_product_map_operator` FOREIGN KEY (`operator_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_user_product_map_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`),
  CONSTRAINT `fk_user_product_map_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_user_product_map_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_product_map`
--

LOCK TABLES `tb_user_product_map` WRITE;
/*!40000 ALTER TABLE `tb_user_product_map` DISABLE KEYS */;
INSERT INTO `tb_user_product_map` VALUES (1,12,16,20,11,'2021-05-30 08:23:44',NULL),(2,12,19,20,11,'2021-05-30 08:23:44',NULL),(3,12,24,20,11,'2021-05-30 08:23:44',NULL),(4,12,13,20,11,'2021-05-30 08:23:44',NULL),(5,14,18,20,14,'2021-06-04 09:51:43',12),(6,14,11,20,14,'2021-06-04 09:52:00',1),(7,14,12,20,14,'2021-06-05 01:24:22',5),(8,14,14,20,14,'2021-06-05 01:25:54',3),(9,14,12,20,14,'2021-06-05 01:26:08',5);
/*!40000 ALTER TABLE `tb_user_product_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_shop_map`
--

DROP TABLE IF EXISTS `tb_user_shop_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_shop_map` (
  `user_shop_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `shop_id` int(10) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `point` int(10) DEFAULT NULL,
  PRIMARY KEY (`user_shop_id`),
  UNIQUE KEY `uq_user_shop` (`user_id`,`shop_id`),
  KEY `fk_user_shop_shop` (`shop_id`),
  CONSTRAINT `fk_user_shop_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`),
  CONSTRAINT `fk_user_shop_user` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_shop_map`
--

LOCK TABLES `tb_user_shop_map` WRITE;
/*!40000 ALTER TABLE `tb_user_shop_map` DISABLE KEYS */;
INSERT INTO `tb_user_shop_map` VALUES (1,11,20,'2021-05-31 09:23:43',2),(2,12,21,'2021-05-31 09:23:43',1),(3,14,20,'2021-06-04 09:51:43',0);
/*!40000 ALTER TABLE `tb_user_shop_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_wechat_auth`
--

DROP TABLE IF EXISTS `tb_wechat_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_wechat_auth` (
  `wechat_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `open_id` varchar(80) NOT NULL DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`wechat_auth_id`),
  UNIQUE KEY `open_id` (`open_id`),
  KEY `fk_wechatauth_profile` (`user_id`),
  CONSTRAINT `fk_wechatauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_wechat_auth`
--

LOCK TABLES `tb_wechat_auth` WRITE;
/*!40000 ALTER TABLE `tb_wechat_auth` DISABLE KEYS */;
INSERT INTO `tb_wechat_auth` VALUES (9,12,'dafahizhfdhaih','2021-08-08 17:56:49'),(10,13,'qqqqqqqqdafahizhfdhaih','2021-05-25 12:35:40'),(11,14,'oA_Fd6TedxU2BTNd1n0glqgjbG_s','2021-05-28 10:55:25');
/*!40000 ALTER TABLE `tb_wechat_auth` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-18 23:21:52
