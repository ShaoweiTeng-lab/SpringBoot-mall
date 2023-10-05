-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mall
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `total_amount` int NOT NULL,
  `created_date` timestamp NOT NULL,
  `last_modified_date` timestamp NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,28,100110,'2022-06-02 08:51:49','2022-06-02 08:51:49'),(2,28,1700000,'2023-05-10 05:26:59','2023-05-10 05:26:59'),(3,29,600000,'2023-05-11 01:42:09','2023-05-11 01:42:09'),(4,29,1200000,'2023-05-11 01:55:02','2023-05-11 01:55:02'),(5,29,20,'2023-05-11 02:19:14','2023-05-11 02:19:14');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `amount` int NOT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `order_id_idx` (`order_id`),
  KEY `product_id_idx` (`product_id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`),
  CONSTRAINT `product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,5,2,60),(2,1,7,5,50),(3,1,8,1,100000),(4,2,9,1,500000),(5,2,10,2,1200000),(6,3,9,1,500000),(7,3,10,1,600000),(8,4,9,2,1000000),(9,4,10,2,1200000),(10,5,5,1,30),(11,5,7,2,20);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(128) NOT NULL,
  `category` varchar(32) NOT NULL,
  `image_url` varchar(256) NOT NULL,
  `price` int NOT NULL,
  `stock` int NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `created_date` timestamp NOT NULL,
  `last_modified_date` timestamp NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (5,'蘋果（澳洲）','FOOD','https://cdn.pixabay.com/photo/2016/11/30/15/00/apples-1872997_1280.jpg',30,9,'這是來自澳洲的蘋果！','2022-03-19 09:00:00','2023-05-11 02:19:14'),(6,'蘋果（日本北海道）','FOOD','https://cdn.pixabay.com/photo/2017/09/26/13/42/apple-2788662_1280.jpg',300,5,'這是來自日本北海道的蘋果！','2022-03-19 10:30:00','2022-03-19 10:30:00'),(7,'好吃又鮮甜的蘋果橘子','FOOD','https://cdn.pixabay.com/photo/2021/07/30/04/17/orange-6508617_1280.jpg',10,48,NULL,'2022-03-20 01:00:00','2023-05-11 02:19:14'),(8,'Toyota','CAR','https://cdn.pixabay.com/photo/2014/05/18/19/13/toyota-347288_1280.jpg',100000,5,NULL,'2022-03-20 01:20:00','2022-03-20 01:20:00'),(9,'BMW','CAR','https://cdn.pixabay.com/photo/2018/02/21/03/15/bmw-m4-3169357_1280.jpg',500000,5,'渦輪增壓，直列4缸，DOHC雙凸輪軸，16氣門','2022-03-20 04:30:00','2023-05-11 01:55:02'),(10,'Benz','CAR','https://cdn.pixabay.com/photo/2017/03/27/14/56/auto-2179220_1280.jpg',600000,5,NULL,'2022-03-21 12:10:00','2023-05-11 01:55:02'),(11,'Tesla','CAR','https://cdn.pixabay.com/photo/2021/01/15/16/49/tesla-5919764_1280.jpg',450000,5,'世界最暢銷的充電式汽車','2022-03-21 15:30:00','2022-03-21 15:30:00');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_Id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(256) NOT NULL,
  `password` varchar(400) NOT NULL,
  `created_date` timestamp NOT NULL,
  `last_modified_date` timestamp NOT NULL,
  PRIMARY KEY (`user_Id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (28,'user1@gmail.com','$2a$10$hYrX3tg3GtMsmVHr.Xx8oOipwq9v/Q9csd5Kue1ccBrkyIxRMWNii','2023-05-11 13:35:06','2023-05-11 13:35:06'),(29,'user2@gmail.com','$2a$10$sNVhGwXl1yGEfmmc35z4N.sNoSnJhSv9FBl1cyD4OzLkbdsUwlBZm','2023-05-11 13:35:16','2023-05-11 13:35:16');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertoken`
--

DROP TABLE IF EXISTS `usertoken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usertoken` (
  `token_Id` int NOT NULL AUTO_INCREMENT,
  `user_Id` int NOT NULL,
  `token` varchar(400) NOT NULL,
  PRIMARY KEY (`token_Id`),
  UNIQUE KEY `user_Id_UNIQUE` (`user_Id`),
  UNIQUE KEY `token_UNIQUE` (`token`),
  CONSTRAINT `fk_user_Id` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertoken`
--

LOCK TABLES `usertoken` WRITE;
/*!40000 ALTER TABLE `usertoken` DISABLE KEYS */;
INSERT INTO `usertoken` VALUES (2,29,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOSIsImV4cCI6MTY4NTk0Mzk2MX0.ZoZTlpp-NUqJqSxPIU4z3D0JM482OD-CEvcRGJeYeXE');
/*!40000 ALTER TABLE `usertoken` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-31 13:50:12
