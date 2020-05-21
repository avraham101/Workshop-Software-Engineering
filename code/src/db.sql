-- MySQL dump 10.13  Distrib 5.7.30, for Win64 (x86_64)
--
-- Host: localhost    Database: workshop
-- ------------------------------------------------------
-- Server version	5.7.30

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `username` varchar(45) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `name` FOREIGN KEY (`username`) REFERENCES `subscribe` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `and_discount`
--

DROP TABLE IF EXISTS `and_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `and_discount` (
  `id` int(11) NOT NULL,
  KEY `and_discount_idx` (`id`),
  CONSTRAINT `and_discount` FOREIGN KEY (`id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `and_term`
--

DROP TABLE IF EXISTS `and_term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `and_term` (
  `id` int(11) NOT NULL,
  KEY `term_and_idx` (`id`),
  CONSTRAINT `term_and` FOREIGN KEY (`id`) REFERENCES `term` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `base_term`
--

DROP TABLE IF EXISTS `base_term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `base_term` (
  `id` int(11) NOT NULL,
  `product` varchar(45) NOT NULL,
  `amount` int(11) NOT NULL,
  KEY `term_base_idx` (`id`),
  CONSTRAINT `term_base` FOREIGN KEY (`id`) REFERENCES `term` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `basket`
--

DROP TABLE IF EXISTS `basket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `basket` (
  `username` varchar(45) NOT NULL,
  `storename` varchar(45) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`username`,`storename`),
  KEY `store_idx` (`storename`),
  CONSTRAINT `cart` FOREIGN KEY (`username`) REFERENCES `cart` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `store` FOREIGN KEY (`storename`) REFERENCES `store` (`storename`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart` (
  `username` varchar(45) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `user` FOREIGN KEY (`username`) REFERENCES `subscribe` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categories_in_store`
--

DROP TABLE IF EXISTS `categories_in_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories_in_store` (
  `store` varchar(45) NOT NULL,
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`store`,`category`),
  KEY `category_in_store_idx` (`category`),
  CONSTRAINT `category_in_store` FOREIGN KEY (`category`) REFERENCES `category` (`name`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `store_cat` FOREIGN KEY (`store`) REFERENCES `store` (`storename`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category_product`
--

DROP TABLE IF EXISTS `category_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_product` (
  `category` varchar(45) NOT NULL,
  `store` varchar(45) NOT NULL,
  `product` varchar(45) NOT NULL,
  PRIMARY KEY (`category`,`store`,`product`),
  KEY `product_idx` (`store`,`product`),
  CONSTRAINT `category` FOREIGN KEY (`category`) REFERENCES `category` (`name`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `product` FOREIGN KEY (`store`, `product`) REFERENCES `product` (`storeName`, `productName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `discount_in_store`
--

DROP TABLE IF EXISTS `discount_in_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discount_in_store` (
  `discount_id` int(11) NOT NULL,
  `store` varchar(45) NOT NULL,
  PRIMARY KEY (`discount_id`,`store`),
  KEY `store_discount_idx` (`store`),
  CONSTRAINT `discount_store` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `store_discount` FOREIGN KEY (`store`) REFERENCES `store` (`storename`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `discounts_inside_discounts`
--

DROP TABLE IF EXISTS `discounts_inside_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discounts_inside_discounts` (
  `holder_id` int(11) NOT NULL,
  `holdee_id` int(11) NOT NULL,
  PRIMARY KEY (`holder_id`,`holdee_id`),
  KEY `holdee_idx` (`holdee_id`),
  CONSTRAINT `holdee` FOREIGN KEY (`holdee_id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `holder` FOREIGN KEY (`holder_id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `reason` varchar(45) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `or_discount`
--

DROP TABLE IF EXISTS `or_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `or_discount` (
  `id` int(11) NOT NULL,
  KEY `or_discount_idx` (`id`),
  CONSTRAINT `or_discount` FOREIGN KEY (`id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `or_term`
--

DROP TABLE IF EXISTS `or_term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `or_term` (
  `id` int(11) NOT NULL,
  KEY `term_or_idx` (`id`),
  CONSTRAINT `term_or` FOREIGN KEY (`id`) REFERENCES `term` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `store` varchar(45) NOT NULL,
  `owner` varchar(45) NOT NULL,
  `givenby` varchar(45) DEFAULT 'Null',
  PRIMARY KEY (`store`,`owner`),
  KEY `given_by_idx` (`givenby`),
  KEY `sub_per_idx` (`owner`),
  CONSTRAINT `given_by` FOREIGN KEY (`givenby`) REFERENCES `subscribe` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `store_per` FOREIGN KEY (`store`) REFERENCES `store` (`storename`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sub_per` FOREIGN KEY (`owner`) REFERENCES `subscribe` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permission_type`
--

DROP TABLE IF EXISTS `permission_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission_type` (
  `store` varchar(45) NOT NULL,
  `owner` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`store`,`owner`),
  CONSTRAINT `permission_types` FOREIGN KEY (`store`, `owner`) REFERENCES `permission` (`store`, `owner`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `storeName` varchar(45) NOT NULL,
  `productName` varchar(45) NOT NULL,
  `amount` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `purchaseType` enum('IMMEDDIATE') NOT NULL,
  PRIMARY KEY (`storeName`,`productName`),
  KEY `purchaseType_idx` (`purchaseType`),
  CONSTRAINT `purchaseType` FOREIGN KEY (`purchaseType`) REFERENCES `purchase_type` (`purchaseType`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `store_product` FOREIGN KEY (`storeName`) REFERENCES `store` (`storename`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_term_discount`
--

DROP TABLE IF EXISTS `product_term_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_term_discount` (
  `id` int(11) NOT NULL,
  `product` varchar(45) NOT NULL,
  `amount` int(11) NOT NULL,
  `term_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `term_idx` (`term_id`),
  CONSTRAINT `discount_term` FOREIGN KEY (`id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `term` FOREIGN KEY (`term_id`) REFERENCES `term` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchase_type`
--

DROP TABLE IF EXISTS `purchase_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_type` (
  `purchaseType` enum('IMMEDDIATE') NOT NULL,
  PRIMARY KEY (`purchaseType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `regular_discount`
--

DROP TABLE IF EXISTS `regular_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regular_discount` (
  `id` int(11) NOT NULL,
  `product` varchar(45) NOT NULL,
  `percantage` double unsigned NOT NULL,
  KEY `regular_disc_idx` (`id`),
  CONSTRAINT `reg` FOREIGN KEY (`id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store` varchar(45) NOT NULL,
  `sender` varchar(45) NOT NULL,
  `content` varchar(45) NOT NULL,
  `comment` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_idx` (`sender`),
  KEY `store_idx` (`store`),
  CONSTRAINT `sotre_req` FOREIGN KEY (`store`) REFERENCES `store` (`storename`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review` (
  `writer` varchar(45) NOT NULL,
  `store` varchar(45) NOT NULL,
  `productName` varchar(45) NOT NULL,
  `content` varchar(45) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `reviews_idx` (`store`,`productName`),
  CONSTRAINT `reviews` FOREIGN KEY (`store`, `productName`) REFERENCES `product` (`storeName`, `productName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store` (
  `storename` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`storename`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `store_discount`
--

DROP TABLE IF EXISTS `store_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store_discount` (
  `min_amount` int(11) NOT NULL,
  `percantage` double NOT NULL,
  `id` int(10) NOT NULL,
  KEY `id_idx` (`id`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscribe`
--

DROP TABLE IF EXISTS `subscribe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscribe` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `term`
--

DROP TABLE IF EXISTS `term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `term` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `terms_inside_terms`
--

DROP TABLE IF EXISTS `terms_inside_terms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terms_inside_terms` (
  `holder_id` int(11) NOT NULL,
  `holdee_id` int(11) NOT NULL,
  PRIMARY KEY (`holder_id`,`holdee_id`),
  KEY `holder_idx` (`holder_id`),
  KEY `holder_term_idx` (`holder_id`),
  KEY `term_holdee_idx` (`holdee_id`),
  CONSTRAINT `term_holdee` FOREIGN KEY (`holdee_id`) REFERENCES `term` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `term_holder` FOREIGN KEY (`holder_id`) REFERENCES `term` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `xor_discount`
--

DROP TABLE IF EXISTS `xor_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xor_discount` (
  `id` int(11) NOT NULL,
  KEY `xor_discount_idx` (`id`),
  CONSTRAINT `xor_discount` FOREIGN KEY (`id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `xor_term`
--

DROP TABLE IF EXISTS `xor_term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xor_term` (
  `id` int(11) NOT NULL,
  KEY `term_xor_idx` (`id`),
  CONSTRAINT `term_xor` FOREIGN KEY (`id`) REFERENCES `term` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-21 10:33:26
