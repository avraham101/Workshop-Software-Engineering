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
-- Table structure for table `and_policy`
--

DROP TABLE IF EXISTS `and_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `and_policy` (
  `pol_id` int(11) NOT NULL,
  PRIMARY KEY (`pol_id`),
  KEY `and_policy_idx` (`pol_id`),
  CONSTRAINT `and_policy` FOREIGN KEY (`pol_id`) REFERENCES `policy` (`pol_id`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `basket_policy`
--

DROP TABLE IF EXISTS `basket_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `basket_policy` (
  `max_amount` int(11) NOT NULL,
  `pol_id` int(11) NOT NULL,
  PRIMARY KEY (`pol_id`),
  CONSTRAINT `basket_policy_id` FOREIGN KEY (`pol_id`) REFERENCES `policy` (`pol_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `buy_notification`
--

DROP TABLE IF EXISTS `buy_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `buy_notification` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `buy_notification` FOREIGN KEY (`id`) REFERENCES `notification` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  PRIMARY KEY (`username`)
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
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
-- Table structure for table `min_max_and_policies`
--

DROP TABLE IF EXISTS `min_max_and_policies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `min_max_and_policies` (
  `pol_id` int(11) NOT NULL,
  `min_max_id` int(11) NOT NULL,
  PRIMARY KEY (`pol_id`,`min_max_id`),
  KEY `min_max_idx` (`min_max_id`),
  CONSTRAINT `min_max` FOREIGN KEY (`min_max_id`) REFERENCES `product_min_max` (`pr_min_max_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `policy_for_min_max` FOREIGN KEY (`pol_id`) REFERENCES `policy` (`pol_id`) ON DELETE CASCADE ON UPDATE CASCADE
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
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
-- Table structure for table `or_policy`
--

DROP TABLE IF EXISTS `or_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `or_policy` (
  `pol_id` int(11) NOT NULL,
  PRIMARY KEY (`pol_id`),
  KEY `or_policy_idx` (`pol_id`),
  CONSTRAINT `or_policy` FOREIGN KEY (`pol_id`) REFERENCES `policy` (`pol_id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  PRIMARY KEY (`store`,`owner`,`type`),
  CONSTRAINT `permission_types` FOREIGN KEY (`store`, `owner`) REFERENCES `permission` (`store`, `owner`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pol`
--

DROP TABLE IF EXISTS `pol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pol` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`p_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `policy`
--

DROP TABLE IF EXISTS `policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `policy` (
  `pol_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`pol_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1257 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `policy_country`
--

DROP TABLE IF EXISTS `policy_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `policy_country` (
  `id` int(11) NOT NULL,
  `country` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`country`),
  CONSTRAINT `policy_country_id` FOREIGN KEY (`id`) REFERENCES `user_policy` (`pol_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `policy_in_store`
--

DROP TABLE IF EXISTS `policy_in_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `policy_in_store` (
  `store` varchar(45) NOT NULL,
  `pol_id` int(11) NOT NULL,
  PRIMARY KEY (`store`,`pol_id`),
  KEY `pol_store_idx` (`pol_id`),
  CONSTRAINT `pol_store` FOREIGN KEY (`pol_id`) REFERENCES `policy` (`pol_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `store_pol` FOREIGN KEY (`store`) REFERENCES `store` (`storename`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `policy_inside_policy`
--

DROP TABLE IF EXISTS `policy_inside_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `policy_inside_policy` (
  `holder_id` int(11) NOT NULL,
  `holdee_id` int(11) NOT NULL,
  PRIMARY KEY (`holder_id`,`holdee_id`),
  KEY `holdee_policy_idx` (`holdee_id`),
  CONSTRAINT `holdee_policy` FOREIGN KEY (`holdee_id`) REFERENCES `policy` (`pol_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `holder_policy` FOREIGN KEY (`holder_id`) REFERENCES `policy` (`pol_id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  KEY `product_name_idx` (`productName`),
  CONSTRAINT `purchaseType` FOREIGN KEY (`purchaseType`) REFERENCES `purchase_type` (`purchaseType`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `store_product` FOREIGN KEY (`storeName`) REFERENCES `store` (`storename`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_for_purchase`
--

DROP TABLE IF EXISTS `product_for_purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_for_purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product` varchar(45) NOT NULL,
  `category` varchar(45) NOT NULL,
  `amount` int(11) NOT NULL,
  `price` double NOT NULL,
  `purchaseType` varchar(45) NOT NULL,
  `store` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_min_max`
--

DROP TABLE IF EXISTS `product_min_max`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_min_max` (
  `min` int(11) NOT NULL,
  `max` int(11) NOT NULL,
  `product_name` varchar(45) NOT NULL,
  `pr_min_max_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`pr_min_max_id`),
  KEY `product_policy_id_idx` (`pr_min_max_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_policy`
--

DROP TABLE IF EXISTS `product_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_policy` (
  `pol_id` int(11) NOT NULL,
  PRIMARY KEY (`pol_id`),
  CONSTRAINT `produt_policy_id` FOREIGN KEY (`pol_id`) REFERENCES `policy` (`pol_id`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `productdata_inside_notification`
--

DROP TABLE IF EXISTS `productdata_inside_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productdata_inside_notification` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`product_id`),
  KEY `productdata_id_idx` (`product_id`),
  CONSTRAINT `notification_buy` FOREIGN KEY (`id`) REFERENCES `notification` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `productdata_id` FOREIGN KEY (`product_id`) REFERENCES `product_for_purchase` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `products_in_basket`
--

DROP TABLE IF EXISTS `products_in_basket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products_in_basket` (
  `buyer` varchar(45) NOT NULL,
  `storename` varchar(45) NOT NULL,
  `productname` varchar(45) NOT NULL,
  `amount` int(11) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`buyer`,`storename`,`productname`),
  CONSTRAINT `pib_store_buyer` FOREIGN KEY (`buyer`, `storename`) REFERENCES `basket` (`username`, `storename`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase` (
  `storeName` varchar(45) NOT NULL,
  `buyer` varchar(45) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `price` double NOT NULL,
  PRIMARY KEY (`storeName`,`buyer`,`date`),
  KEY `sub_purchase_idx` (`buyer`),
  CONSTRAINT `store_purchase` FOREIGN KEY (`storeName`) REFERENCES `store` (`storename`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sub_purchase` FOREIGN KEY (`buyer`) REFERENCES `subscribe` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `purchases_and_products`
--

DROP TABLE IF EXISTS `purchases_and_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchases_and_products` (
  `storeName` varchar(45) NOT NULL,
  `buyer` varchar(45) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`storeName`,`buyer`,`date`,`id`),
  KEY `id_purchase_product_idx` (`id`),
  CONSTRAINT `id_purchase_product` FOREIGN KEY (`id`) REFERENCES `product_for_purchase` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_product` FOREIGN KEY (`storeName`, `buyer`, `date`) REFERENCES `purchase` (`storeName`, `buyer`, `date`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `remove_notification`
--

DROP TABLE IF EXISTS `remove_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `remove_notification` (
  `id` int(11) NOT NULL,
  `store` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `remove_notification` FOREIGN KEY (`id`) REFERENCES `notification` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `request_notification`
--

DROP TABLE IF EXISTS `request_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request_notification` (
  `id` int(11) NOT NULL,
  `request_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `request notification_idx` (`request_id`),
  CONSTRAINT `request notification` FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `request_notification` FOREIGN KEY (`id`) REFERENCES `notification` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `subscibe_notifications`
--

DROP TABLE IF EXISTS `subscibe_notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscibe_notifications` (
  `username` varchar(45) NOT NULL,
  `notfiication_id` int(11) NOT NULL,
  PRIMARY KEY (`username`,`notfiication_id`),
  KEY `noti_user_idx` (`notfiication_id`),
  CONSTRAINT `noti_user` FOREIGN KEY (`notfiication_id`) REFERENCES `notification` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_noti` FOREIGN KEY (`username`) REFERENCES `subscribe` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `password` varchar(512) NOT NULL,
  `sessionNumber` int(11) DEFAULT '-1',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_pol`
--

DROP TABLE IF EXISTS `system_pol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_pol` (
  `p_id` int(11) NOT NULL,
  `age` int(11) NOT NULL,
  KEY `system_policy_idx` (`p_id`),
  CONSTRAINT `system_policy` FOREIGN KEY (`p_id`) REFERENCES `pol` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_policy`
--

DROP TABLE IF EXISTS `system_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_policy` (
  `pol_id` int(11) NOT NULL,
  `age` int(11) NOT NULL,
  KEY `pol_sys_idx` (`pol_id`),
  CONSTRAINT `pol_sys` FOREIGN KEY (`pol_id`) REFERENCES `policy` (`pol_id`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `user_policy`
--

DROP TABLE IF EXISTS `user_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_policy` (
  `pol_id` int(11) NOT NULL,
  PRIMARY KEY (`pol_id`),
  CONSTRAINT `user_policy_id` FOREIGN KEY (`pol_id`) REFERENCES `policy` (`pol_id`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `xor_policy`
--

DROP TABLE IF EXISTS `xor_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xor_policy` (
  `pol_id` int(11) NOT NULL,
  PRIMARY KEY (`pol_id`),
  KEY `xor_policy_idx` (`pol_id`),
  CONSTRAINT `xor_policy` FOREIGN KEY (`pol_id`) REFERENCES `policy` (`pol_id`) ON DELETE CASCADE ON UPDATE CASCADE
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

-- Dump completed on 2020-05-23 23:14:33
