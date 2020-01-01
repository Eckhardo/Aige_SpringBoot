

CREATE DATABASE  IF NOT EXISTS `nre` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `nre`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: nre
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `country` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `code` varchar(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'Germany','DE'),(2,'Danmark','DK'),(3,'Italy','IT'),(4,'France','FR'),(5,'Finland','FI'),(6,'Norway','NO'),(7,'Sweden','SE'),(8,'Switzerland','CH'),(9,'Portugal','PT'),(10,'Spain','ES'),(11,'Brazil','BR'),(12,'Argentina','AR'),(13,'Japan','JP'),(14,'China','CN'),(15,'Canada','CA'),(16,'Mexico','MX');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-08 18:25:01

CREATE DATABASE  IF NOT EXISTS `nre` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `nre`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: nre
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `geoscope`
--

DROP TABLE IF EXISTS `geoscope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `geoscope` (
  `id` bigint(20) NOT NULL,
  `country_code` varchar(255) NOT NULL,
  `geo_scope_type` varchar(255) NOT NULL,
  `location_code` varchar(255) DEFAULT NULL,
  `location_name` varchar(255) DEFAULT NULL,
  `is_port` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `geoscope`
--

LOCK TABLES `geoscope` WRITE;
/*!40000 ALTER TABLE `geoscope` DISABLE KEYS */;
INSERT INTO `geoscope` VALUES (1,'DE','L','DEBRV','Bremerhaven',_binary ''),(2,'DE','L','DEHAM','Hamburg',_binary ''),(3,'NL','L','NLRTM','Rotterdam',_binary ''),(4,'BE','L','BEANR','Antwerp',_binary ''),(5,'DE','L','DEDOR','',_binary '\0'),(6,'BR','L','BRSSZ','Santos',_binary ''),(7,'BR','L','BRITJ','Itajai',_binary ''),(8,'BR','L','BRPNG','Paranagua',_binary ''),(9,'DE','L','DEDUS','',_binary '\0'),(10,'DE','L','DEDUI','',_binary '\0'),(11,'DE','T','BREMERHAVEN','',_binary '\0'),(12,'DE','T','HAMBURG','',_binary '\0'),(13,'NL','T','ROTTERDAM','',_binary '\0'),(14,'BE','T','ANTWERP','',_binary '\0'),(15,'CO','T','CARTAGENA','',_binary '\0'),(16,'BR','T','SANTOS','',_binary '\0'),(17,'BR','T','MANAUS','',_binary '\0'),(18,'BR','T','SUAPE','',_binary '\0'),(19,'DE','T','BERLIN','',_binary '\0'),(20,'DE','T','BOCHUM','',_binary '\0'),(21,'DE','T','BOTTROP','',_binary '\0'),(22,'DE','T','HAMBORN','',_binary '\0'),(23,'DE','T','HAMM','',_binary '\0'),(24,'DE','T','HAMFELDE','',_binary '\0'),(25,'DE','T','DUSSELDORF','',_binary '\0'),(26,'DE','T','DUISBURG','',_binary '\0'),(27,'DE','F','DEBRVCTAX','',_binary '\0'),(28,'DE','F','DEHAMCTBX','',_binary '\0'),(29,'NL','F','NLRTMCTAA','',_binary '\0'),(30,'BE','F','BEANRCTSM','',_binary '\0'),(31,'CO','F','COCTGCCFF','',_binary '\0'),(32,'BR','F','BRSSZSANT','',_binary '\0'),(33,'BR','F','BRMANCTTX','',_binary '\0'),(34,'DE','F','DEHAMATBX','',_binary '\0'),(35,'DE','F','DEHAMEURO','',_binary '\0'),(36,'CN','L','HKHKG','Hong Kong',_binary ''),(37,'CN','L','CNSHA','Shanghai',_binary ''),(38,'CO','L','COCTG','Cartagena',_binary ''),(39,'AR','L','ARBUE','Buenos Aires',_binary ''),(40,'UY','L','UYMVD','Montevideo',_binary ''),(41,'CN','L','CNSZX','Shenzhen',_binary ''),(42,'JPK','L','JPYOK','Yokohama',_binary ''),(43,'JP','L','JPTYO','Tokyo',_binary '');
/*!40000 ALTER TABLE `geoscope` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-08 18:25:02
CREATE DATABASE  IF NOT EXISTS `nre` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `nre`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: nre
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `keyfigure`
--

DROP TABLE IF EXISTS `keyfigure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `keyfigure` (
  `id` bigint(20) NOT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `def_eq_size` varchar(255) DEFAULT NULL,
  `eq_group` varchar(255) DEFAULT NULL,
  `eq_size` varchar(255) DEFAULT NULL,
  `weight_class` int(11) DEFAULT NULL,
  `im_route` bigint(20) DEFAULT NULL,
  `preferred` bit(1) DEFAULT NULL,
  `rate` decimal(10,2) DEFAULT '0.00',
  `start_date` date DEFAULT NULL,
  `tpmode` varchar(255) DEFAULT NULL,
  `from_id` bigint(20) DEFAULT NULL,
  `to_id` bigint(20) DEFAULT NULL,
  `via_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcxj9cj47hrad2nupobo4ox44a` (`from_id`),
  KEY `FKbe03j61kgfnleagg9h1tsqqtf` (`to_id`),
  KEY `FK2ve4o01blfayvjc60we3f5bmo` (`via_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyfigure`
--

LOCK TABLES `keyfigure` WRITE;
/*!40000 ALTER TABLE `keyfigure` DISABLE KEYS */;
INSERT INTO `keyfigure` VALUES (44,'EUR','40','RF','20',0,0,_binary '\0',200.00,'2018-10-08','TRUCK',9,1,NULL),(45,'EUR','40','GP','20',0,0,_binary '\0',400.35,'2018-10-08','TRUCK',9,2,NULL),(46,'EUR','40','GP','20',0,0,_binary '\0',600.35,'2018-10-08','TRUCK',9,3,NULL),(47,'EUR','40','GP','20',0,0,_binary '\0',800.34,'2018-10-08','TRUCK',9,4,NULL),(48,'EUR','40','GP','20',0,0,_binary '',1000.32,'2018-10-08','BARGE',9,4,10),(49,'EUR','40','RF','40',0,0,_binary '\0',900.30,'2018-10-08','TRUCK',9,1,NULL),(50,'EUR','40','GP','40',0,0,_binary '\0',1100.30,'2018-10-08','TRUCK',9,2,NULL),(51,'EUR','40','GP','40',0,0,_binary '\0',1300.35,'2018-10-08','TRUCK',9,3,NULL),(52,'EUR','40','GP','40',0,0,_binary '\0',1500.30,'2018-10-08','TRUCK',9,4,NULL),(53,'EUR','40','GP','40',0,0,_binary '',1700.30,'2018-10-08','RAIL/ROAD',9,2,10),(54,'EUR','40','GP','40',0,0,_binary '',1300.30,'2018-10-08','RAIL/ROAD',9,2,10),(55,'EUR','40','GP','40',0,0,_binary '',1400.30,'2018-10-08','RAIL/ROAD',9,4,10),(56,'EUR','40','GP','40',0,0,_binary '',1500.30,'2018-10-08','RAIL/ROAD',9,3,10),(57,'EUR','40','GP','40',0,0,_binary '',1600.30,'2018-10-08','RAIL/ROAD',9,1,10),(58,'EUR','40','GP','40',0,0,_binary '',1000.30,'2018-10-08','RAIL',9,2,10),(59,'EUR','40','GP','40',0,0,_binary '',700.30,'2018-10-08','BARGE',9,2,10),(60,'EUR','40','RF','20',0,0,_binary '\0',200.00,'2018-10-08','TRUCK',25,1,NULL),(61,'EUR','40','GP','20',0,0,_binary '\0',400.35,'2018-10-08','TRUCK',25,2,NULL),(62,'EUR','40','GP','20',0,0,_binary '\0',600.35,'2018-10-08','TRUCK',25,3,NULL),(63,'EUR','40','GP','20',0,0,_binary '\0',800.34,'2018-10-08','TRUCK',25,4,NULL),(64,'EUR','40','GP','20',0,0,_binary '',1000.32,'2018-10-08','BARGE',25,4,10),(65,'EUR','40','RF','40',0,0,_binary '\0',900.30,'2018-10-08','TRUCK',25,1,NULL),(66,'EUR','40','GP','40',0,0,_binary '\0',1100.30,'2018-10-08','TRUCK',25,2,NULL),(67,'EUR','40','GP','40',0,0,_binary '\0',1300.35,'2018-10-08','TRUCK',25,3,NULL),(68,'EUR','40','GP','40',0,0,_binary '\0',1500.30,'2018-10-08','TRUCK',25,4,NULL),(69,'EUR','40','GP','40',0,0,_binary '',1700.30,'2018-10-08','RAIL/ROAD',25,2,10),(70,'EUR','40','GP','40',0,0,_binary '',1300.30,'2018-10-08','RAIL/ROAD',25,2,10),(71,'EUR','40','GP','40',0,0,_binary '',1400.30,'2018-10-08','RAIL/ROAD',25,4,10),(72,'EUR','40','GP','40',0,0,_binary '',1500.30,'2018-10-08','RAIL/ROAD',25,3,10),(73,'EUR','40','GP','40',0,0,_binary '',1600.30,'2018-10-08','RAIL/ROAD',25,1,10),(74,'EUR','40','GP','40',0,0,_binary '',1000.30,'2018-10-08','RAIL',25,2,10),(75,'EUR','40','GP','40',0,0,_binary '',700.30,'2018-10-08','BARGE',25,2,10);
/*!40000 ALTER TABLE `keyfigure` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-08 18:25:02
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: nre
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ocean_route_errors`
--

DROP TABLE IF EXISTS `ocean_route_errors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ocean_route_errors` (
  `ocean_route_id` bigint(20) NOT NULL,
  `errors` varchar(255) DEFAULT NULL,
  KEY `FK49eeaepvrb8x57y5jhlo32557` (`ocean_route_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocean_route_errors`
--

LOCK TABLES `ocean_route_errors` WRITE;
/*!40000 ALTER TABLE `ocean_route_errors` DISABLE KEYS */;
INSERT INTO `ocean_route_errors` VALUES (83,'MIN_TRANSIT_RATIO'),(84,'NSHKF'),(84,'MIN_TRANSIT_RATIO'),(85,'REMAX'),(85,'MAX_TRANSIT_RATIO'),(85,'MIN_TRANSIT_RATIO'),(86,'MIN_TRANSIT_RATIO');
/*!40000 ALTER TABLE `ocean_route_errors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oceanroute`
--

DROP TABLE IF EXISTS `oceanroute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `oceanroute` (
  `id` bigint(20) NOT NULL,
  `pod` varchar(255) DEFAULT NULL,
  `pod_fac` varchar(255) DEFAULT NULL,
  `pol` varchar(255) DEFAULT NULL,
  `pol_fac` varchar(255) DEFAULT NULL,
  `prof` varchar(255) DEFAULT NULL,
  `prof2` varchar(255) DEFAULT NULL,
  `prof3` varchar(255) DEFAULT NULL,
  `tt` int(11) DEFAULT NULL,
  `ts1` varchar(255) DEFAULT NULL,
  `ts1fac2` varchar(255) DEFAULT NULL,
  `ts1fac3` varchar(255) DEFAULT NULL,
  `ts2` varchar(255) DEFAULT NULL,
  `ts3` varchar(255) DEFAULT NULL,
  `ts_fac1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oceanroute`
--

LOCK TABLES `oceanroute` WRITE;
/*!40000 ALTER TABLE `oceanroute` DISABLE KEYS */;
INSERT INTO `oceanroute` VALUES (77,'HKHKG','564077','BRSSZ','115861','41020SIM1768','','',31,'','','','','',''),(78,'HKHKG','564077','BRSSZ','115861','41020SIM1763','','',32,'','','','','',''),(79,'HKHKG','564077','BRSSZ','565152 ','41060SIM1887','','',33,'','','','','',''),(80,'HKHKG','564077','BRSSZ','565151','61010SIM1827','41060SIM1886','',48,'BRSPB','','','','','565153'),(81,'HKHKG','564077','BRSSZ','1680535','11050SIM0004','71020SIM1810','',44,'ESALG','','','','','1621517'),(82,'HKHKG','564077','BRSSZ','565151','52015SIM1670','','75010SIM1963',37,'COCTG','568229','','COCTG','','1528540'),(83,'HKHKG','564077','BRSSZ','565151','61010SIM1827','41060SIM1886','',47,'BRRIO','','','','','568761'),(84,'HKHKG','564077','BRSSZ','565151','61030SIM1923','','41070SIM0006',49,'ARBUE','568677','','ARBUE','','568675'),(85,'HKHKG','564077','BRSSZ','565151','42610SIM1703','41020SIM1768','',86,'BRPNG','','','','','565149'),(86,'HKHKG','564077','BRSSZ','565151','41020SIM1763','71010SIM1884','',39,'SGSIN','','','','','568079');
/*!40000 ALTER TABLE `oceanroute` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-13 19:27:47
