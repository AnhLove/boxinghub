CREATE DATABASE  IF NOT EXISTS `boxinghub` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `boxinghub`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: boxinghub
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `chat_messages`
--

DROP TABLE IF EXISTS `chat_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `content` text NOT NULL,
  `is_read` bit(1) DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `receiver_id` bigint NOT NULL,
  `sender_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmh3qvfjsjms66wvv3jxgumpyy` (`receiver_id`),
  KEY `FKmf86klrrgnufxig1bgb94kafu` (`sender_id`),
  CONSTRAINT `FKmf86klrrgnufxig1bgb94kafu` FOREIGN KEY (`sender_id`) REFERENCES `members` (`id`),
  CONSTRAINT `FKmh3qvfjsjms66wvv3jxgumpyy` FOREIGN KEY (`receiver_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_messages`
--

LOCK TABLES `chat_messages` WRITE;
/*!40000 ALTER TABLE `chat_messages` DISABLE KEYS */;
INSERT INTO `chat_messages` VALUES (1,'2026-05-10 23:46:59.906229','2026-05-11 15:34:56.282447','hello',_binary '','2026-05-10 23:46:59.899846',16,15),(2,'2026-05-10 23:47:44.986221','2026-05-11 15:36:15.671908','hello',_binary '','2026-05-10 23:47:44.986221',15,16),(3,'2026-05-10 23:47:51.378733','2026-05-11 15:34:56.287643','ok',_binary '','2026-05-10 23:47:51.378733',16,15),(4,'2026-05-11 15:15:11.307217','2026-05-11 15:34:56.287643','ờ',_binary '','2026-05-11 15:15:11.306228',16,15),(5,'2026-05-11 15:15:33.540672','2026-05-11 15:36:15.671908','sao bạn ơi',_binary '','2026-05-11 15:15:33.540672',15,16),(6,'2026-05-11 15:35:01.127091','2026-05-11 15:36:15.671908','bận ko',_binary '','2026-05-11 15:35:01.126087',15,16),(7,'2026-05-11 15:36:06.689659','2026-05-11 15:36:15.671908','ê',_binary '','2026-05-11 15:36:06.689659',15,16),(8,'2026-05-11 15:36:24.358870','2026-05-11 15:36:24.384321','nói đi bạn',_binary '','2026-05-11 15:36:24.358870',16,15),(9,'2026-05-11 15:55:35.242604','2026-05-11 15:55:42.373982','alo',_binary '','2026-05-11 15:55:35.237623',16,15),(10,'2026-05-11 16:00:20.284277','2026-05-11 16:03:27.592421','hello',_binary '','2026-05-11 16:00:20.282265',15,16),(11,'2026-05-11 16:03:23.069117','2026-05-11 16:03:27.598924','hello',_binary '','2026-05-11 16:03:23.062966',15,16),(12,'2026-05-11 16:03:41.076541','2026-05-11 16:03:44.860701','làm gì đấy bạn',_binary '','2026-05-11 16:03:41.075484',15,16),(13,'2026-05-11 16:03:56.005867','2026-05-11 16:04:03.413328','ok b',_binary '','2026-05-11 16:03:56.005867',15,16),(14,'2026-05-11 16:04:42.052334','2026-05-11 16:04:42.077316','ok',_binary '','2026-05-11 16:04:42.052334',16,15),(15,'2026-05-11 16:04:54.559031','2026-05-11 16:05:01.590226','ok',_binary '','2026-05-11 16:04:54.559031',16,15),(16,'2026-05-11 16:09:17.649440','2026-05-11 16:09:28.051402','ok',_binary '','2026-05-11 16:09:17.647343',16,15),(17,'2026-05-11 16:09:49.158426','2026-05-11 16:09:51.380885','ok',_binary '','2026-05-11 16:09:49.157397',16,15),(18,'2026-05-11 16:11:18.365772','2026-05-11 16:11:23.389461','ok',_binary '','2026-05-11 16:11:18.361761',16,15),(19,'2026-05-11 16:13:43.138085','2026-05-11 16:13:43.187686','ok',_binary '','2026-05-11 16:13:43.134057',16,15),(20,'2026-05-11 16:13:54.828638','2026-05-11 16:14:01.149940','ok',_binary '','2026-05-11 16:13:54.828638',16,15),(21,'2026-05-11 16:14:07.521623','2026-05-11 16:14:17.751181','ok',_binary '','2026-05-11 16:14:07.521623',16,15),(22,'2026-05-11 16:14:08.709135','2026-05-11 16:14:17.751181','ok',_binary '','2026-05-11 16:14:08.709135',16,15),(23,'2026-05-11 16:14:09.927104','2026-05-11 16:14:17.751181','ok',_binary '','2026-05-11 16:14:09.927104',16,15),(24,'2026-05-11 16:14:11.392163','2026-05-11 16:14:17.751181','ok',_binary '','2026-05-11 16:14:11.392163',16,15),(25,'2026-05-11 16:23:00.890079','2026-05-11 16:23:12.716518','ok',_binary '','2026-05-11 16:23:00.889061',16,15),(26,'2026-05-11 16:26:58.284454','2026-05-11 16:27:04.807127','ok',_binary '','2026-05-11 16:26:58.280455',16,15),(27,'2026-05-11 16:29:19.676106','2026-05-11 16:29:27.631297','ok',_binary '','2026-05-11 16:29:19.675102',15,16),(28,'2026-05-11 16:33:44.195583','2026-05-11 16:33:44.208926','ok',_binary '','2026-05-11 16:33:44.195583',15,16),(29,'2026-05-11 16:33:54.502217','2026-05-11 16:34:15.818678','ok',_binary '','2026-05-11 16:33:54.502217',16,15),(30,'2026-05-11 16:34:18.532642','2026-05-11 16:38:12.055579','ok',_binary '','2026-05-11 16:34:18.531642',15,16),(31,'2026-05-11 16:34:31.549721','2026-05-11 16:38:12.058825','ok',_binary '','2026-05-11 16:34:31.549721',15,16),(32,'2026-05-11 16:38:16.087844','2026-05-11 16:41:21.013543','ok',_binary '','2026-05-11 16:38:16.086794',16,15),(33,'2026-05-11 16:38:29.873118','2026-05-11 16:41:21.017766','ok',_binary '','2026-05-11 16:38:29.873118',16,15),(34,'2026-05-11 16:40:25.625933','2026-05-11 16:41:21.017766','ok',_binary '','2026-05-11 16:40:25.622931',16,15),(35,'2026-05-11 16:40:28.382633','2026-05-11 16:41:21.017766','ok',_binary '','2026-05-11 16:40:28.381628',16,15),(36,'2026-05-11 16:41:14.585646','2026-05-11 16:41:21.017766','ok',_binary '','2026-05-11 16:41:14.581860',16,15),(37,'2026-05-11 16:41:23.619178','2026-05-11 16:41:23.638883','ok',_binary '','2026-05-11 16:41:23.619178',15,16),(38,'2026-05-11 16:41:29.554122','2026-05-11 20:56:45.961097','ok',_binary '','2026-05-11 16:41:29.554122',15,16),(39,'2026-05-11 16:41:36.305476','2026-05-11 20:56:45.961097','ok',_binary '','2026-05-11 16:41:36.305476',15,16),(40,'2026-05-11 20:56:28.767764','2026-05-11 20:56:45.961097','ok',_binary '','2026-05-11 20:56:28.766211',15,16),(41,'2026-05-11 20:56:52.518621','2026-05-11 20:57:29.576712','ok',_binary '','2026-05-11 20:56:52.518621',15,16),(42,'2026-05-11 20:57:32.237920','2026-05-11 21:10:07.577894','ok',_binary '','2026-05-11 20:57:32.237920',16,15),(43,'2026-05-11 21:10:14.583674','2026-05-11 21:10:31.899321','ok',_binary '','2026-05-11 21:10:14.582674',15,16),(44,'2026-05-11 21:10:27.493404','2026-05-11 21:10:31.899321','ok',_binary '','2026-05-11 21:10:27.493404',15,16),(45,'2026-05-11 21:10:41.495462','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:10:41.494454',16,15),(46,'2026-05-11 21:10:50.209812','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:10:50.209812',16,15),(47,'2026-05-11 21:10:53.490747','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:10:53.490747',16,15),(48,'2026-05-11 21:10:55.012367','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:10:55.012367',16,15),(49,'2026-05-11 21:10:56.736626','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:10:56.735624',16,15),(50,'2026-05-11 21:10:58.127427','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:10:58.126412',16,15),(51,'2026-05-11 21:11:01.369347','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:11:01.369347',16,15),(52,'2026-05-11 21:11:02.401610','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:11:02.401610',16,15),(53,'2026-05-11 21:11:03.398122','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:11:03.396629',16,15),(54,'2026-05-11 21:11:04.422875','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:11:04.422875',16,15),(55,'2026-05-11 21:11:05.459981','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:11:05.459981',16,15),(56,'2026-05-11 21:11:06.499988','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:11:06.498978',16,15),(57,'2026-05-11 21:11:07.464576','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:11:07.464576',16,15),(58,'2026-05-11 21:11:08.562469','2026-05-11 21:11:25.754043','ok',_binary '','2026-05-11 21:11:08.560945',16,15),(59,'2026-05-11 21:11:09.612596','2026-05-11 21:11:25.754043','ôk',_binary '','2026-05-11 21:11:09.612596',16,15),(60,'2026-05-11 21:32:46.038374','2026-05-11 21:33:10.038858','ok',_binary '','2026-05-11 21:32:46.036847',15,16),(61,'2026-05-11 21:32:56.151416','2026-05-11 21:33:10.039872','ok',_binary '','2026-05-11 21:32:56.151416',15,16),(62,'2026-05-11 21:32:58.162180','2026-05-11 21:33:10.039872','ok',_binary '','2026-05-11 21:32:58.162180',15,16),(63,'2026-05-11 21:33:05.140470','2026-05-11 21:33:10.039872','ok',_binary '','2026-05-11 21:33:05.139503',15,16),(64,'2026-05-11 21:33:17.815218','2026-05-11 21:37:00.256307','ok',_binary '','2026-05-11 21:33:17.814213',15,16),(65,'2026-05-11 21:33:29.739426','2026-05-11 21:37:00.259825','ok',_binary '','2026-05-11 21:33:29.739426',15,16),(66,'2026-05-11 21:33:31.044507','2026-05-11 21:37:00.259825','ok',_binary '','2026-05-11 21:33:31.043511',15,16),(67,'2026-05-11 21:33:32.025086','2026-05-11 21:37:00.259825','ok',_binary '','2026-05-11 21:33:32.024071',15,16),(68,'2026-05-11 21:33:32.994817','2026-05-11 21:37:00.259825','ok',_binary '','2026-05-11 21:33:32.994817',15,16),(69,'2026-05-11 21:33:33.917375','2026-05-11 21:37:00.259825','ok',_binary '','2026-05-11 21:33:33.917375',15,16),(70,'2026-05-11 21:37:02.705976','2026-05-11 21:37:33.870621','ok',_binary '','2026-05-11 21:37:02.704373',16,15),(71,'2026-05-11 21:37:16.406042','2026-05-11 21:37:33.870621','ok',_binary '','2026-05-11 21:37:16.406042',16,15),(72,'2026-05-11 21:37:17.605063','2026-05-11 21:37:33.870621','ok',_binary '','2026-05-11 21:37:17.605063',16,15),(73,'2026-05-11 21:37:19.435290','2026-05-11 21:37:33.870621','ok',_binary '','2026-05-11 21:37:19.435290',16,15),(74,'2026-05-11 21:37:20.381317','2026-05-11 21:37:33.870621','ok',_binary '','2026-05-11 21:37:20.381317',16,15),(75,'2026-05-11 21:37:21.344397','2026-05-11 21:37:33.870621','ok',_binary '','2026-05-11 21:37:21.344397',16,15),(76,'2026-05-11 21:38:57.278877','2026-05-11 22:14:36.871521','ok',_binary '','2026-05-11 21:38:57.274342',16,15),(77,'2026-05-11 21:39:45.250437','2026-05-11 22:14:36.873521','ok',_binary '','2026-05-11 21:39:45.250437',16,15),(78,'2026-05-11 21:39:55.684744','2026-05-11 22:14:36.873521','ok',_binary '','2026-05-11 21:39:55.684744',16,15),(79,'2026-05-11 22:14:40.141613','2026-05-11 22:14:46.581707','ok',_binary '','2026-05-11 22:14:40.141613',15,16),(80,'2026-05-11 22:14:54.330180','2026-05-11 22:15:02.820958','ok',_binary '','2026-05-11 22:14:54.330180',16,15),(81,'2026-05-11 22:15:05.432576','2026-05-11 22:26:19.537950','ok',_binary '','2026-05-11 22:15:05.432576',15,16),(82,'2026-05-11 22:26:37.893875','2026-05-11 22:26:44.010980','ok',_binary '','2026-05-11 22:26:37.892887',16,15),(83,'2026-05-11 22:26:39.302719','2026-05-11 22:26:44.010980','ok',_binary '','2026-05-11 22:26:39.302719',16,15),(84,'2026-05-11 22:26:46.139102','2026-05-11 22:26:46.155267','ok',_binary '','2026-05-11 22:26:46.139102',15,16),(85,'2026-05-11 22:26:54.492180','2026-05-11 22:32:56.990742','ok',_binary '','2026-05-11 22:26:54.492180',16,15),(86,'2026-05-11 22:37:41.594275','2026-05-11 22:45:10.925196','ok',_binary '','2026-05-11 22:37:41.593229',15,16),(87,'2026-05-11 22:45:05.010891','2026-05-11 22:45:10.927631','ok',_binary '','2026-05-11 22:45:05.009891',15,16),(88,'2026-05-11 22:45:13.802376','2026-05-11 22:45:13.834434','ok',_binary '','2026-05-11 22:45:13.802376',16,15),(89,'2026-05-11 22:45:18.813047','2026-05-11 22:56:47.331150','ok',_binary '','2026-05-11 22:45:18.813047',15,16),(90,'2026-05-11 22:56:49.873967','2026-05-11 23:00:37.285632','ok',_binary '','2026-05-11 22:56:49.872965',16,15),(91,'2026-05-11 23:00:28.623224','2026-05-11 23:00:37.285632','ok',_binary '','2026-05-11 23:00:28.623224',16,15),(92,'2026-05-11 23:00:48.528985','2026-05-11 23:00:48.551075','ok',_binary '','2026-05-11 23:00:48.528985',15,16),(93,'2026-05-11 23:14:01.155214','2026-05-12 22:16:00.017284','ghê',_binary '','2026-05-11 23:14:01.155214',15,16),(94,'2026-05-12 22:16:21.550233','2026-05-12 22:42:25.528637','gay',_binary '','2026-05-12 22:16:21.549223',15,16),(95,'2026-05-12 22:41:21.889207','2026-05-12 22:41:49.704328','ok',_binary '','2026-05-12 22:41:21.887210',16,14),(96,'2026-05-12 22:41:53.155261','2026-05-12 22:41:53.191460','ok',_binary '','2026-05-12 22:41:53.154262',14,16),(97,'2026-05-12 22:42:01.987343','2026-05-12 22:42:33.749443','ok',_binary '','2026-05-12 22:42:01.987343',16,14),(98,'2026-05-12 22:42:28.122850','2026-05-12 22:43:37.003902','ok',_binary '','2026-05-12 22:42:28.122850',16,15),(99,'2026-05-12 22:42:37.017593','2026-05-12 22:42:37.017593','ok',_binary '\0','2026-05-12 22:42:37.017593',14,16),(100,'2026-05-12 22:43:26.632476','2026-05-12 22:43:26.632476','ok',_binary '\0','2026-05-12 22:43:26.627467',14,16),(101,'2026-05-12 22:43:51.053946','2026-05-13 23:04:33.914934','ok',_binary '','2026-05-12 22:43:51.053946',16,15),(102,'2026-05-13 23:04:05.238381','2026-05-13 23:04:33.914934','ok',_binary '','2026-05-13 23:04:05.237315',16,15),(103,'2026-05-13 23:04:36.548583','2026-05-13 23:04:36.570955','ok',_binary '','2026-05-13 23:04:36.548583',15,16),(104,'2026-05-13 23:04:41.927438','2026-05-13 23:04:41.949774','ok',_binary '','2026-05-13 23:04:41.926289',16,15),(105,'2026-05-13 23:04:48.049258','2026-05-18 11:49:57.768679','ok',_binary '','2026-05-13 23:04:48.049258',15,16),(106,'2026-05-15 16:10:12.469764','2026-05-18 11:44:01.953556','hello',_binary '','2026-05-15 16:10:12.469764',16,18),(107,'2026-05-18 11:44:05.429996','2026-05-18 11:44:05.429996','ok',_binary '\0','2026-05-18 11:44:05.429996',18,16),(108,'2026-05-18 11:50:00.483068','2026-05-18 11:50:11.425548','ok',_binary '','2026-05-18 11:50:00.482034',16,15),(109,'2026-05-18 11:50:04.262616','2026-05-18 11:50:11.426549','ok',_binary '','2026-05-18 11:50:04.261609',16,15),(110,'2026-05-18 11:50:05.119543','2026-05-18 11:50:11.426549','ok',_binary '','2026-05-18 11:50:05.119543',16,15),(111,'2026-05-18 11:50:05.893533','2026-05-18 11:50:11.426549','ok',_binary '','2026-05-18 11:50:05.893533',16,15),(112,'2026-05-18 11:50:06.547267','2026-05-18 11:50:11.426549','ok',_binary '','2026-05-18 11:50:06.547267',16,15),(113,'2026-05-18 11:50:13.633949','2026-05-18 11:50:13.659465','ok',_binary '','2026-05-18 11:50:13.633949',15,16),(114,'2026-05-18 11:50:14.449467','2026-05-18 11:50:14.466684','ok',_binary '','2026-05-18 11:50:14.448460',15,16),(115,'2026-05-18 11:50:20.309343','2026-05-18 11:50:20.309343','ok',_binary '\0','2026-05-18 11:50:20.309343',15,16),(116,'2026-05-18 11:50:20.894748','2026-05-18 11:50:20.894748','ok',_binary '\0','2026-05-18 11:50:20.894748',15,16),(117,'2026-05-18 11:50:21.356503','2026-05-18 11:50:21.356503','ok',_binary '\0','2026-05-18 11:50:21.356503',15,16);
/*!40000 ALTER TABLE `chat_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `member_id` bigint NOT NULL,
  `post_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkv22t54g17a6hvj7hbn6byh5s` (`member_id`),
  KEY `FKh4c7lvsc298whoyd4w9ta25cr` (`post_id`),
  CONSTRAINT `FKh4c7lvsc298whoyd4w9ta25cr` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `FKkv22t54g17a6hvj7hbn6byh5s` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (22,'ok','2026-05-11 21:16:08.393057',16,9),(23,'ok','2026-05-11 21:16:10.598036',16,9),(24,'ok','2026-05-11 21:16:10.826640',16,9),(25,'ok','2026-05-11 21:16:11.094766',16,9),(26,'ok','2026-05-11 21:16:11.294033',16,9),(27,'ok','2026-05-11 21:16:11.473864',16,9),(28,'ok','2026-05-11 21:16:11.672585',16,9),(29,'ok','2026-05-11 21:16:11.865136',16,9),(30,'ok','2026-05-11 21:16:12.067189',16,9),(31,'ok','2026-05-11 21:16:12.273877',16,9),(32,'ok','2026-05-11 21:19:25.866919',16,9),(33,'ok','2026-05-11 21:19:29.356264',16,9),(34,'ok','2026-05-11 21:19:40.899567',16,9),(35,'ok1','2026-05-11 21:19:58.431788',16,9),(36,'ok12','2026-05-11 21:22:25.089392',16,9),(37,'ok122','2026-05-11 21:25:26.886171',16,9),(38,'ok122','2026-05-11 21:25:51.073591',16,9),(39,'ok122','2026-05-11 21:27:31.118419',16,9),(40,'hi','2026-05-11 21:32:18.590727',16,9),(41,'hi','2026-05-11 21:32:26.943563',16,9),(42,'oll','2026-05-11 22:44:34.947506',16,9),(43,'ok','2026-05-11 22:45:31.220993',16,3),(44,'ok','2026-05-11 22:46:22.529237',16,9),(46,'ok','2026-05-11 23:02:08.700878',16,5),(47,'ổn','2026-05-11 23:02:17.506643',16,5),(55,'ok','2026-05-15 16:09:52.617099',18,9);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit_transactions`
--

DROP TABLE IF EXISTS `credit_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit_transactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` int DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `money_amount` double DEFAULT NULL,
  `payment_code` varchar(255) DEFAULT NULL,
  `status` enum('CANCELLED','PENDING','SUCCESS') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKlsp5vv8skd7j9bk3hgij0fyad` (`payment_code`),
  KEY `FKrpdcrmg00hu99eo19swghdw3g` (`member_id`),
  CONSTRAINT `FKrpdcrmg00hu99eo19swghdw3g` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_transactions`
--

LOCK TABLES `credit_transactions` WRITE;
/*!40000 ALTER TABLE `credit_transactions` DISABLE KEYS */;
INSERT INTO `credit_transactions` VALUES (1,10,'','2026-05-06 22:46:04.203305',16,NULL,NULL,NULL),(2,10,'','2026-05-06 22:46:41.640444',16,NULL,NULL,NULL),(3,10,'','2026-05-06 22:46:47.552418',16,NULL,NULL,NULL),(4,10,'ok','2026-05-06 22:58:00.026109',16,NULL,NULL,NULL),(5,10,'Nạp tiền qua Momo/Chuyển khoản','2026-05-07 21:35:19.637451',16,1000000,'BH636122','SUCCESS'),(6,1,'Nạp tiền qua Momo/Chuyển khoản','2026-05-07 21:12:07.984010',16,100000,'BH127984','PENDING'),(7,1,'Nạp tiền qua Momo/Chuyển khoản','2026-05-07 21:14:32.846926',16,100000,'BH272846','PENDING'),(8,1,'Nạp tiền qua Momo/Chuyển khoản','2026-05-07 21:20:17.400953',16,1000,'BH617400','PENDING'),(9,1,'Nạp tiền qua Momo/Chuyển khoản','2026-05-07 21:22:23.448307',16,2000,'BH743448','PENDING'),(10,1,'Nạp tiền qua Momo/Chuyển khoản','2026-05-07 21:25:43.418982',16,2000,'BH943418','PENDING'),(11,1,'Nạp tiền qua Momo/Chuyển khoản','2026-05-07 21:27:33.128431',16,2000,'BH53128','PENDING'),(12,1,'Nạp tiền qua Momo/Chuyển khoản','2026-05-07 21:30:06.350423',16,2000,'BH131069','SUCCESS'),(13,10,'Nạp tiền qua Momo/Chuyển khoản','2026-05-07 21:30:35.819950',16,20000,'BH221040','SUCCESS'),(14,10,'Nạp tiền qua Momo/Chuyển khoản','2026-05-07 21:30:39.955529',16,20000,'BH239955','PENDING'),(15,1,'Nạp tiền qua Momo/Chuyển khoản','2026-05-07 22:39:14.298841',17,2000,'BH193547','SUCCESS'),(16,1,'Nạp tiền qua Momo/Chuyển khoản','2026-05-07 22:39:32.555240',17,2000,'BH372555','PENDING'),(17,10,'Nạp tiền qua Momo/Chuyển khoản','2026-05-09 22:08:42.614925',16,20000,'BH765909','SUCCESS'),(18,1,'Nạp tiền qua Momo/Chuyển khoản','2026-05-12 23:02:17.807250',16,100000,'BH766795','SUCCESS'),(19,10,'','2026-05-18 11:48:07.362415',16,NULL,NULL,'SUCCESS');
/*!40000 ALTER TABLE `credit_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_classes`
--

DROP TABLE IF EXISTS `group_classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_classes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `capacity` int DEFAULT NULL,
  `class_name` varchar(255) NOT NULL,
  `current_enrolled` int DEFAULT NULL,
  `description` text,
  `duration_minutes` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `schedule` datetime(6) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `trainer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiyhg1ono4oyvttisxk9pepbhy` (`trainer_id`),
  CONSTRAINT `FKiyhg1ono4oyvttisxk9pepbhy` FOREIGN KEY (`trainer_id`) REFERENCES `trainers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_classes`
--

LOCK TABLES `group_classes` WRITE;
/*!40000 ALTER TABLE `group_classes` DISABLE KEYS */;
INSERT INTO `group_classes` VALUES (1,'2026-04-30 23:11:18.137651','2026-05-06 21:33:00.074373',30,'Boxing2',0,'Đã đóng',120,100000,'2026-05-01 22:05:00.000000','CLOSED',1),(2,'2026-05-01 21:48:52.876832','2026-05-02 15:21:00.132383',30,'Boxing1',0,'Đủ ',120,100000,'2026-05-01 21:48:00.000000','CLOSED',1),(5,'2026-05-04 21:12:14.307070','2026-05-08 21:35:34.463633',30,'Boxing1',1,'',120,100000,'2026-05-08 21:35:00.000000','OPEN',1),(6,'2026-05-18 11:41:48.858741','2026-05-18 11:42:59.830666',30,'Boxing3',1,'',120,100000,'2026-05-18 11:41:00.000000','OPEN',4);
/*!40000 ALTER TABLE `group_classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_classes`
--

DROP TABLE IF EXISTS `member_classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_classes` (
  `member_id` bigint NOT NULL,
  `class_id` bigint NOT NULL,
  KEY `FKcaajc8ukt4qrisgislyiumrgg` (`class_id`),
  KEY `FKro3fklxl8gmkg578u8dikpdst` (`member_id`),
  CONSTRAINT `FKcaajc8ukt4qrisgislyiumrgg` FOREIGN KEY (`class_id`) REFERENCES `group_classes` (`id`),
  CONSTRAINT `FKro3fklxl8gmkg578u8dikpdst` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_classes`
--

LOCK TABLES `member_classes` WRITE;
/*!40000 ALTER TABLE `member_classes` DISABLE KEYS */;
INSERT INTO `member_classes` VALUES (14,5),(15,5),(17,5),(16,5),(16,6);
/*!40000 ALTER TABLE `member_classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `full_name` varchar(255) NOT NULL,
  `gender` enum('FEMALE','MALE') DEFAULT NULL,
  `height` double DEFAULT NULL,
  `level` enum('ADVANCED','BEGINNER','INTERMEDIATE') DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `remaining_sessions` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKda61ga2jecphdliwvkqyt6sw2` (`user_id`),
  CONSTRAINT `FKpj3n6wh5muoeakc485whgs3x5` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (2,'2026-04-27 10:54:16.085059','2026-04-29 13:26:02.965668','Minh Quang','MALE',170,'INTERMEDIATE','0972978206',60,NULL,NULL,NULL),(3,'2026-04-28 21:38:38.775010','2026-04-28 21:38:38.775010','Trịnh Trần Phương Tuán','MALE',172,'INTERMEDIATE','0972978197',65.5,NULL,NULL,NULL),(4,'2026-05-01 22:44:33.543211','2026-05-01 22:44:33.543211','Nam Hoàng ','MALE',168,'BEGINNER','0972978206',62,NULL,NULL,NULL),(5,'2026-05-01 23:36:14.807231','2026-05-01 23:36:14.807231','Đức Dũng','MALE',172,'BEGINNER','0972978210',63,NULL,NULL,NULL),(6,'2026-05-01 23:37:02.941058','2026-05-01 23:37:02.941058','Oanh Tạc','MALE',170,'BEGINNER','0972978211',64,NULL,NULL,NULL),(7,'2026-05-01 23:37:50.075339','2026-05-01 23:37:50.075339','Hoàng Tú','MALE',175,NULL,'0972978212',66,NULL,NULL,NULL),(9,'2026-05-01 23:39:40.732233','2026-05-01 23:39:40.732233','Hoàng Dũng','MALE',173,NULL,'0972978213',70,NULL,NULL,NULL),(12,'2026-05-04 21:08:15.930496','2026-05-04 21:08:15.930496','Trần Hoàng Ý ',NULL,NULL,NULL,'0972978220',NULL,0,3,NULL),(13,'2026-05-06 18:38:47.649980','2026-05-06 19:24:17.331757','Hoàng Nam','MALE',172,'BEGINNER','0972978221',64,10,4,NULL),(14,'2026-05-06 19:25:24.955963','2026-05-06 19:26:51.557029','Như Ý',NULL,NULL,NULL,'0972978222',NULL,20,5,NULL),(15,'2026-05-06 21:44:02.284806','2026-05-10 23:22:25.799274','Như Lan','MALE',168,'BEGINNER','0972978223',76,0,6,'/uploads/avatars/avatar_15_1778430119599.png'),(16,'2026-05-06 21:49:56.055128','2026-05-18 11:48:07.383703','Như O1','MALE',167,'BEGINNER','097297822412',60,101,7,'/uploads/avatars/avatar_16_1779079374100.jpg'),(17,'2026-05-07 22:35:30.883945','2026-05-07 22:39:36.111971','Hoàng Thi','MALE',170,'BEGINNER','0972978225',75,0,8,NULL),(18,'2026-05-15 15:50:04.143837','2026-05-15 16:09:06.893244','Hoàng Tú','MALE',180,'BEGINNER','09729782013',80,0,9,'/uploads/avatars/avatar_18_1778836146883.png');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_reset_tokens`
--

DROP TABLE IF EXISTS `password_reset_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password_reset_tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK71lqwbwtklmljk3qlsugr1mig` (`token`),
  UNIQUE KEY `UKla2ts67g4oh2sreayswhox1i6` (`user_id`),
  CONSTRAINT `FKk3ndxg5xp6v7wd4gjyusp15gq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_reset_tokens`
--

LOCK TABLES `password_reset_tokens` WRITE;
/*!40000 ALTER TABLE `password_reset_tokens` DISABLE KEYS */;
/*!40000 ALTER TABLE `password_reset_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_likes`
--

DROP TABLE IF EXISTS `post_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_likes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `post_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsr9uiilpnflc32a04yyx7bun` (`member_id`),
  KEY `FKa5wxsgl4doibhbed9gm7ikie2` (`post_id`),
  CONSTRAINT `FKa5wxsgl4doibhbed9gm7ikie2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `FKsr9uiilpnflc32a04yyx7bun` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_likes`
--

LOCK TABLES `post_likes` WRITE;
/*!40000 ALTER TABLE `post_likes` DISABLE KEYS */;
INSERT INTO `post_likes` VALUES (15,16,4),(26,16,3),(28,16,9),(29,16,5),(32,15,9),(33,15,7),(35,16,7),(36,16,15);
/*!40000 ALTER TABLE `post_likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `content` text NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `is_hidden` bit(1) NOT NULL,
  `likes` int DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `member_id` bigint NOT NULL,
  `media_type` varchar(255) DEFAULT NULL,
  `media_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlguag5cl5yccrl9hdnw53fdhf` (`member_id`),
  CONSTRAINT `FKlguag5cl5yccrl9hdnw53fdhf` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (2,'2026-05-09 17:08:17.134628','2026-05-09 22:50:16.814309','Ngầu ',NULL,_binary '\0',3,'Naraka',16,'IMAGE','/uploads/posts/2916c97c-cfb7-48c2-8e52-3edc8d5587ea_63897089750681.png'),(3,'2026-05-09 17:17:29.331005','2026-05-11 22:45:25.910172','tôi đây mn',NULL,_binary '\0',4,'Hello',15,'IMAGE','/uploads/posts/ff2ed1ed-c499-42e3-9a40-b4b8fb5d1b92_z7089387285783_b12b2fb4e457b07653cfa823fd747a1f.jpg'),(4,'2026-05-09 17:25:53.307071','2026-05-09 23:56:02.122633','ai chơi ko ',NULL,_binary '\0',6,'Naraka ',16,'IMAGE','/uploads/posts/295e5c19-d7e1-4cf1-94d4-ad291177348f_63890627616384.png'),(5,'2026-05-09 17:33:26.534849','2026-05-12 22:56:06.734389','Bạn thấy sao ?',NULL,_binary '\0',3,'idol tôi ',16,'IMAGE','/uploads/posts/22215841-4365-455c-ad29-e040f1d14c68_doiVoKy.jpg'),(7,'2026-05-09 17:41:11.493559','2026-05-18 11:46:14.123046','Hehee',NULL,_binary '\0',5,'Top Miền ',16,'IMAGE','/uploads/posts/fa09daed-72b9-4378-a8f6-0b1b85e25083_Screenshot 2025-08-13 085845.png'),(9,'2026-05-11 21:02:23.779675','2026-05-15 16:09:45.663266','code game',NULL,_binary '\0',2,'Tu Tiên',16,'VIDEO','/uploads/posts/28f372d4-bc66-4546-9e35-0de41ec8dfdf_Screen Recording 2026-02-25 223453.mp4'),(15,'2026-05-18 11:50:37.608120','2026-05-18 11:50:39.885735','ok',NULL,_binary '\0',1,'ok',16,'IMAGE','/uploads/posts/a78bf9a5-5e86-440a-a306-3d339763eca9_laptoploq.jpg');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` enum('DISMISSED','PENDING','RESOLVED') DEFAULT NULL,
  `comment_id` bigint DEFAULT NULL,
  `post_id` bigint DEFAULT NULL,
  `reporter_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3x8ylsypiesh2gkwdy5ug7qe7` (`comment_id`),
  KEY `FKneu1viyp671jjiwukyfv6dsy` (`post_id`),
  KEY `FKstudkqwdqpfvo0xkc8kg8e5fb` (`reporter_id`),
  CONSTRAINT `FK3x8ylsypiesh2gkwdy5ug7qe7` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `FKneu1viyp671jjiwukyfv6dsy` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `FKstudkqwdqpfvo0xkc8kg8e5fb` FOREIGN KEY (`reporter_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
INSERT INTO `reports` VALUES (1,'2026-05-13 19:46:26.982614','2026-05-13 22:30:48.801099','Nội dung không phù hợp','DISMISSED',NULL,9,15),(2,'2026-05-13 22:15:08.456951','2026-05-13 22:30:37.502294','Thông tin sai lệch','DISMISSED',NULL,9,15),(3,'2026-05-13 22:33:29.716601','2026-05-13 22:58:38.001725','Spam hoặc quảng cáo','DISMISSED',NULL,9,15);
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `comment` text,
  `rating` int NOT NULL,
  `member_id` bigint NOT NULL,
  `trainer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6wsc8rr8tb1fc782foh3mjc8q` (`member_id`),
  KEY `FKksna0ysspv7vdf2wujgsle79g` (`trainer_id`),
  CONSTRAINT `FK6wsc8rr8tb1fc782foh3mjc8q` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
  CONSTRAINT `FKksna0ysspv7vdf2wujgsle79g` FOREIGN KEY (`trainer_id`) REFERENCES `trainers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,'2026-05-08 17:34:27.905749','2026-05-08 21:45:44.328578','ok',5,16,1),(2,'2026-05-18 11:43:11.334862','2026-05-18 11:43:11.335967','',3,16,4);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `support_ticket`
--

DROP TABLE IF EXISTS `support_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `support_ticket` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `content` text,
  `created_at` datetime(6) DEFAULT NULL,
  `status` enum('IN_PROGRESS','PENDING','REJECTED','RESOLVED') DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg9fgil9l0iycxnl1lh0h2rqu3` (`member_id`),
  CONSTRAINT `FKg9fgil9l0iycxnl1lh0h2rqu3` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `support_ticket`
--

LOCK TABLES `support_ticket` WRITE;
/*!40000 ALTER TABLE `support_ticket` DISABLE KEYS */;
INSERT INTO `support_ticket` VALUES (1,'Facilities','Tôi đấm rách bao rơi xuống đất ko treo lại được ','2026-05-08 22:07:53.348735','IN_PROGRESS','Hỏng bao cát ',16),(2,'Technical','lỗi darkmode','2026-05-08 22:45:49.318964','REJECTED','Lỗi app ',16),(3,'Trainer','Chúng tôi đợi 30p HLV mới tới ','2026-05-18 11:43:44.795707','RESOLVED','HLV đến muộn ',16);
/*!40000 ALTER TABLE `support_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainers`
--

DROP TABLE IF EXISTS `trainers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `bio` text,
  `email` varchar(255) NOT NULL,
  `experience_years` int DEFAULT NULL,
  `full_name` varchar(255) NOT NULL,
  `gender` enum('FEMALE','MALE') DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `specialization` varchar(255) DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKp760ma5h90yjnu0s0c94jawyb` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainers`
--

LOCK TABLES `trainers` WRITE;
/*!40000 ALTER TABLE `trainers` DISABLE KEYS */;
INSERT INTO `trainers` VALUES (1,'2026-04-27 21:33:28.223495','2026-04-30 23:33:21.899564','Tôi là một người có kinh nghiệm lâu năm trên các sàn đấu chuyên nghiệp gặp qua nhiều cao thủ và từng thắng liên tiếp 36 trận boxing hạng trung ','tuankaito6005@gmail.com',4,'Trần Nguyễn Bảo Châu','MALE','0972978206','Boxing','ACTIVE'),(2,'2026-04-28 21:05:28.454206','2026-04-28 21:05:28.454206','Tôi có hơn 7 năm kinh nghiệm trong lĩnh vực Muay Thai, từng tham gia thi đấu và đạt nhiều thành tích cấp quốc gia. Tôi chuyên huấn luyện từ cơ bản đến nâng cao, giúp học viên cải thiện thể lực, kỹ thuật và khả năng thực chiến. Phong cách giảng dạy kỷ luật nhưng dễ hiểu, phù hợp cho cả người mới bắt đầu lẫn vận động viên chuyên nghiệp.','tuankaito5006@gmail.com',3,'Hoàng Đức Mạnh ','MALE','0972978201','Muay Thai','ACTIVE'),(3,'2026-05-01 23:06:53.794882','2026-05-01 23:06:53.795898','Đẳng cấp Huyền đai đệ tứ đẳng Judo. Chuyên gia về các kỹ thuật quật (Nage-waza) và kiểm soát mặt đất (Katame-waza). Đã đào tạo nhiều võ sinh đạt giải cao tại các giải trẻ thành phố.','tuankaito7006@gmail.com',7,'Lê Văn Nam','MALE','0972978209','JuDo','ACTIVE'),(4,'2026-05-18 11:41:06.810589','2026-05-18 11:41:06.810589','','quangminh77777777771@gmail.com',6,'Minh Quang1','MALE','09729782066','Boxing','ACTIVE');
/*!40000 ALTER TABLE `trainers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `is_active` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-05-03 21:13:36.438588','2026-05-03 21:13:36.438588','admin@boxinghub.com','$2a$10$pxN57EOCCe/0.77sT9MXo.H1OXpvxGowazDHdjfyK5JrBzv3THhgO','ROLE_ADMIN','',_binary '\0'),(3,'2026-05-04 21:08:15.901990','2026-05-04 21:08:15.901990','tuankaito7009@gmail.com','$2a$10$8nJouVOCbDvvVX95uRViteur2csEek.JqAHL8G2IaSeNwM/Eh8WRq','ROLE_MEMBER','Trần Hoàng Ý ',_binary ''),(4,'2026-05-06 18:38:47.617938','2026-05-06 18:38:47.617938','tuankaito7010@gmail.com','$2a$10$JRY5tB3wMdcq.IR3X2vQvOrB0b6TszWGPqLvcWRnCB2fvwf9oy8IO','ROLE_MEMBER','Hoàng Nam',_binary ''),(5,'2026-05-06 19:25:24.940346','2026-05-06 19:25:24.940346','tuankaito7011@gmail.com','$2a$10$OSvIevpGCvbmSowMrOwGuetXu9btlPHQOC12FX4BSNxg7rbbwhKTK','ROLE_MEMBER','Như Ý',_binary ''),(6,'2026-05-06 21:44:02.259198','2026-05-10 23:22:07.336640','tuankaito7012@gmail.com','$2a$10$8h9U8hyaz6bbMn/40QtxaenIToIxhNGdXslpbFLJ8Ku97c73Wzo3y','ROLE_MEMBER','Như Hoa',_binary ''),(7,'2026-05-06 21:49:56.042067','2026-05-18 11:42:54.106377','tuankaito7013@gmail.com','$2a$10$K/eQPEwKMmjIPCteRkC00eDI1T.gfDp9cWGOHCsaU/ewf4bBBWzxm','ROLE_MEMBER','Như O1',_binary ''),(8,'2026-05-07 22:35:30.845264','2026-05-07 22:35:30.845264','tuankaito7014@gmail.com','$2a$10$SoiVANWlEUSc7SM7Wobv0urVTd7GvEdR1hEeBFK5ZqYhrU4zEIp..','ROLE_MEMBER','Hoàng Thi',_binary ''),(9,'2026-05-15 15:50:04.100915','2026-05-15 16:09:32.002694','quangminh77777777777777777@gmail.com','$2a$10$c75gv2lkfxI8l6f3UZKhnOUylTBcaCXGamzZwUGWx4mUiL3LSaleC','ROLE_MEMBER','Hoàng Tú',_binary '');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-18 12:58:35
