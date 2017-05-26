CREATE DATABASE  IF NOT EXISTS `storemanagmentdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `storemanagmentdb`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: storemanagmentdb
-- ------------------------------------------------------
-- Server version	5.7.13-log

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
-- Table structure for table `items_cart`
--

DROP TABLE IF EXISTS `items_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items_cart` (
  `cart_id` int(100) NOT NULL AUTO_INCREMENT,
  `item_id` int(100) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `item_quantity` varchar(100) NOT NULL,
  `transaction_quantity` varchar(70) DEFAULT NULL,
  `transaction_quantity_in` varchar(70) DEFAULT NULL,
  `loanedtype` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`cart_id`),
  UNIQUE KEY `item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items_cart`
--

LOCK TABLES `items_cart` WRITE;
/*!40000 ALTER TABLE `items_cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `items_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items_loaned_table`
--

DROP TABLE IF EXISTS `items_loaned_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items_loaned_table` (
  `loan_id` int(100) NOT NULL AUTO_INCREMENT,
  `item_id` int(100) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `item_type` varchar(70) NOT NULL,
  `item_quantity` varchar(70) DEFAULT NULL,
  `item_quantity_in` varchar(70) DEFAULT NULL,
  `transaction_to` varchar(200) DEFAULT NULL,
  `transaction_receipt_no_out` varchar(70) DEFAULT NULL,
  `transaction_officer_incharge` varchar(70) DEFAULT NULL,
  `transaction_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`loan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items_loaned_table`
--

LOCK TABLES `items_loaned_table` WRITE;
/*!40000 ALTER TABLE `items_loaned_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `items_loaned_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items_received_cart`
--

DROP TABLE IF EXISTS `items_received_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items_received_cart` (
  `cart_id` int(100) NOT NULL AUTO_INCREMENT,
  `item_id` int(100) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `item_quantity` varchar(100) NOT NULL,
  `transaction_quantity` varchar(70) DEFAULT NULL,
  `transaction_quantity_in` varchar(70) DEFAULT NULL,
  `transaction_cash` varchar(70) DEFAULT NULL,
  `transaction_type` varchar(70) DEFAULT NULL,
  `transaction_item_cash` varchar(70) DEFAULT NULL,
  `transaction_purchase_order_no` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items_received_cart`
--

LOCK TABLES `items_received_cart` WRITE;
/*!40000 ALTER TABLE `items_received_cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `items_received_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items_table`
--

DROP TABLE IF EXISTS `items_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items_table` (
  `item_id` int(100) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL,
  `item_type` varchar(70) NOT NULL,
  `item_quantity` varchar(70) DEFAULT NULL,
  `item_quantity_in` varchar(70) DEFAULT NULL,
  `item_updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`item_id`),
  UNIQUE KEY `item_name` (`item_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items_table`
--

LOCK TABLES `items_table` WRITE;
/*!40000 ALTER TABLE `items_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `items_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prefrences`
--

DROP TABLE IF EXISTS `prefrences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prefrences` (
  `id` varchar(100) NOT NULL DEFAULT '1',
  `name` varchar(70) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `website` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `imgurl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prefrences`
--

LOCK TABLES `prefrences` WRITE;
/*!40000 ALTER TABLE `prefrences` DISABLE KEYS */;
INSERT INTO `prefrences` VALUES ('1','Moi singoiri Girls High School','PO.BOX 1234 Uasin Gishu','moisongoiri@mosongoiri.co.ke','www.mosongoiri.co.ke','0712345678','C:\\Users\\kimani kogi\\Pictures\\icons\\store\\img\\bus2.jpg');
/*!40000 ALTER TABLE `prefrences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_user`
--

DROP TABLE IF EXISTS `system_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_user` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `user_password` varchar(100) DEFAULT NULL,
  `hash` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_user`
--

LOCK TABLES `system_user` WRITE;
/*!40000 ALTER TABLE `system_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions_archive_table`
--

DROP TABLE IF EXISTS `transactions_archive_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions_archive_table` (
  `transaction_id` int(100) NOT NULL AUTO_INCREMENT,
  `item_id` int(100) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `item_type` varchar(70) NOT NULL,
  `transaction_revert_status` varchar(70) DEFAULT '0',
  `transaction_quantity` varchar(70) DEFAULT NULL,
  `transaction_quantity_in` varchar(70) DEFAULT NULL,
  `transaction_type` varchar(70) DEFAULT NULL,
  `transaction_to` varchar(200) DEFAULT NULL,
  `transaction_from` varchar(200) DEFAULT '--',
  `transaction_cash` varchar(70) DEFAULT '--',
  `transaction_receipt_no_in` varchar(70) DEFAULT '--',
  `transaction_receipt_no_out` varchar(70) DEFAULT '--',
  `transaction_officer_incharge` varchar(70) DEFAULT '--',
  `transaction_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `transaction_item_cash` varchar(200) DEFAULT '--',
  `transaction_purchase_order_no` varchar(200) DEFAULT '--',
  `transaction_from_address` varchar(200) DEFAULT '--',
  `transaction_received_by` varchar(200) DEFAULT '--',
  `transaction_receiver_designation` varchar(200) DEFAULT '--',
  `transaction_department` varchar(200) DEFAULT '--',
  `transaction_delivered_by` varchar(200) DEFAULT '--',
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions_archive_table`
--

LOCK TABLES `transactions_archive_table` WRITE;
/*!40000 ALTER TABLE `transactions_archive_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactions_archive_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions_reciepts`
--

DROP TABLE IF EXISTS `transactions_reciepts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions_reciepts` (
  `receipt_id` int(100) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL,
  `item_id` varchar(100) NOT NULL,
  `receipt_total` varchar(70) NOT NULL DEFAULT '--',
  `receipt_no` varchar(70) NOT NULL,
  `receipt_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`receipt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions_reciepts`
--

LOCK TABLES `transactions_reciepts` WRITE;
/*!40000 ALTER TABLE `transactions_reciepts` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactions_reciepts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions_table`
--

DROP TABLE IF EXISTS `transactions_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions_table` (
  `transaction_id` int(100) NOT NULL AUTO_INCREMENT,
  `item_id` int(100) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `item_type` varchar(70) NOT NULL,
  `transaction_revert_status` varchar(70) DEFAULT '0',
  `transaction_quantity` varchar(70) DEFAULT NULL,
  `transaction_quantity_in` varchar(70) DEFAULT NULL,
  `transaction_type` varchar(70) DEFAULT NULL,
  `transaction_to` varchar(200) DEFAULT NULL,
  `transaction_from` varchar(200) DEFAULT '--',
  `transaction_cash` varchar(70) DEFAULT '--',
  `transaction_receipt_no_in` varchar(70) DEFAULT '--',
  `transaction_receipt_no_out` varchar(70) DEFAULT '--',
  `transaction_officer_incharge` varchar(70) DEFAULT '--',
  `transaction_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `transaction_item_cash` varchar(200) DEFAULT '--',
  `transaction_purchase_order_no` varchar(200) DEFAULT '--',
  `transaction_from_address` varchar(200) DEFAULT '--',
  `transaction_received_by` varchar(200) DEFAULT '--',
  `transaction_receiver_designation` varchar(200) DEFAULT '--',
  `transaction_department` varchar(200) DEFAULT '--',
  `transaction_delivered_by` varchar(200) DEFAULT '--',
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions_table`
--

LOCK TABLES `transactions_table` WRITE;
/*!40000 ALTER TABLE `transactions_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactions_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-24  7:13:31
