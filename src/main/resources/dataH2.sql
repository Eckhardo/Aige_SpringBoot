
--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS COUNTRY;
/*!40101 SET @saved_cs_client     = @@character_set_client */;

CREATE TABLE COUNTRY (
  id IDENTITY  auto_increment primary key,
  name varchar NOT NULL,
  code varchar NOT NULL
) 
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--



INSERT INTO `country` VALUES (1,'Germany','DE'),(2,'Danmark','DK'),(3,'Italy','IT'),(4,'France','FR'),(5,'Finland','FI'),(6,'Norway','NO'),(7,'Sweden','SE'),(8,'Switzerland','CH'),(9,'Portugal','PT'),(10,'Spain','ES'),(11,'Brazil','BR'),(12,'Argentina','AR'),(13,'Japan','JP'),(14,'China','CN'),(15,'Canada','CA'),(16,'Mexico','MX');



--
-- Table structure for table `geoscope`
--

DROP TABLE IF EXISTS `geoscope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 
CREATE TABLE `geoscope` (
  `id` int(20) NOT NULL auto_increment,
  `country_code` varchar(255) NOT NULL,
  `geo_scope_type` varchar(255) NOT NULL,
  `location_code` varchar(255) DEFAULT NULL,
  `location_name` varchar(255) DEFAULT NULL,
  `is_port` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `geoscope`
--



INSERT INTO `geoscope` VALUES (1,'DE','L','DEBRV','Bremerhaven', TRUE),(2,'DE','L','DEHAM','Hamburg',TRUE),(3,'NL','L','NLRTM','Rotterdam',TRUE),(4,'BE','L','BEANR','Antwerp',TRUE),(5,'DE','L','DEDOR','',FALSE),(6,'BR','L','BRSSZ','Santos',TRUE),(7,'BR','L','BRITJ','Itajai',TRUE),(8,'BR','L','BRPNG','Paranagua',TRUE),(9,'DE','L','DEDUS','',FALSE),(10,'DE','L','DEDUI','',FALSE),(11,'DE','T','BREMERHAVEN','',FALSE),(12,'DE','T','HAMBURG','',FALSE),(13,'NL','T','ROTTERDAM','',FALSE),(14,'BE','T','ANTWERP','',FALSE),(15,'CO','T','CARTAGENA','',FALSE),(16,'BR','T','SANTOS','',FALSE),(17,'BR','T','MANAUS','',FALSE),(18,'BR','T','SUAPE','',FALSE),(19,'DE','T','BERLIN','',FALSE),(20,'DE','T','BOCHUM','',FALSE),(21,'DE','T','BOTTROP','',FALSE),(22,'DE','T','HAMBORN','',FALSE),(23,'DE','T','HAMM','',FALSE),(24,'DE','T','HAMFELDE','',FALSE),(25,'DE','T','DUSSELDORF','',FALSE),(26,'DE','T','DUISBURG','',FALSE),(27,'DE','F','DEBRVCTAX','',FALSE),(28,'DE','F','DEHAMCTBX','',FALSE),(29,'NL','F','NLRTMCTAA','',FALSE),(30,'BE','F','BEANRCTSM','',FALSE),(31,'CO','F','COCTGCCFF','',FALSE),(32,'BR','F','BRSSZSANT','',FALSE),(33,'BR','F','BRMANCTTX','',FALSE),(34,'DE','F','DEHAMATBX','',FALSE),(35,'DE','F','DEHAMEURO','',FALSE),(36,'CN','L','HKHKG','Hong Kong',TRUE),(37,'CN','L','CNSHA','Shanghai',TRUE),(38,'CO','L','COCTG','Cartagena',TRUE),(39,'AR','L','ARBUE','Buenos Aires',TRUE),(40,'UY','L','UYMVD','Montevideo',TRUE),(41,'CN','L','CNSZX','Shenzhen',TRUE),(42,'JPK','L','JPYOK','Yokohama',TRUE),(43,'JP','L','JPTYO','Tokyo',TRUE);

--
-- Table structure for table `keyfigure`
--

DROP TABLE IF EXISTS `keyfigure`;
 
CREATE TABLE `keyfigure` (
  `id` int(20) NOT NULL auto_increment,
  `currency` varchar(255) DEFAULT NULL,
  `def_eq_size` varchar(255) DEFAULT NULL,
  `eq_group` varchar(255) DEFAULT NULL,
  `eq_size` varchar(255) DEFAULT NULL,
  `weight_class` int(11) DEFAULT NULL,
  `im_route` int(20) DEFAULT NULL,
  `preferred` bit(1) DEFAULT NULL,
  `rate` decimal(10,2) DEFAULT '0.00',
  `start_date` date DEFAULT NULL,
  `tpmode` varchar(255) DEFAULT NULL,
  `from_id` int(20) DEFAULT NULL,
  `to_id` int(20) DEFAULT NULL,
  `via_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
 FOREIGN KEY  (`from_id`)  references geoscope(ID),
  FOREIGN KEY  (`to_id`) references geoscope(ID),
  FOREIGN KEY  (`via_id`) references geoscope(ID)
) 
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyfigure`
--



INSERT INTO `keyfigure` VALUES (44,'EUR','40','RF','20',0,0,FALSE,200.00,'2018-10-08','TRUCK',9,1,NULL),(45,'EUR','40','GP','20',0,0,FALSE,400.35,'2018-10-08','TRUCK',9,2,NULL),(46,'EUR','40','GP','20',0,0,FALSE,600.35,'2018-10-08','TRUCK',9,3,NULL),(47,'EUR','40','GP','20',0,0,FALSE,800.34,'2018-10-08','TRUCK',9,4,NULL),(48,'EUR','40','GP','20',0,0,TRUE,1000.32,'2018-10-08','BARGE',9,4,10),(49,'EUR','40','RF','40',0,0,FALSE,900.30,'2018-10-08','TRUCK',9,1,NULL),(50,'EUR','40','GP','40',0,0,FALSE,1100.30,'2018-10-08','TRUCK',9,2,NULL),(51,'EUR','40','GP','40',0,0,FALSE,1300.35,'2018-10-08','TRUCK',9,3,NULL),(52,'EUR','40','GP','40',0,0,FALSE,1500.30,'2018-10-08','TRUCK',9,4,NULL),(53,'EUR','40','GP','40',0,0,TRUE,1700.30,'2018-10-08','RAIL/ROAD',9,2,10),(54,'EUR','40','GP','40',0,0,TRUE,1300.30,'2018-10-08','RAIL/ROAD',9,2,10),(55,'EUR','40','GP','40',0,0,TRUE,1400.30,'2018-10-08','RAIL/ROAD',9,4,10),(56,'EUR','40','GP','40',0,0,TRUE,1500.30,'2018-10-08','RAIL/ROAD',9,3,10),(57,'EUR','40','GP','40',0,0,TRUE,1600.30,'2018-10-08','RAIL/ROAD',9,1,10),(58,'EUR','40','GP','40',0,0,TRUE,1000.30,'2018-10-08','RAIL',9,2,10),(59,'EUR','40','GP','40',0,0,TRUE,700.30,'2018-10-08','BARGE',9,2,10),(60,'EUR','40','RF','20',0,0,FALSE,200.00,'2018-10-08','TRUCK',25,1,NULL),(61,'EUR','40','GP','20',0,0,FALSE,400.35,'2018-10-08','TRUCK',25,2,NULL),(62,'EUR','40','GP','20',0,0,FALSE,600.35,'2018-10-08','TRUCK',25,3,NULL),(63,'EUR','40','GP','20',0,0,FALSE,800.34,'2018-10-08','TRUCK',25,4,NULL),(64,'EUR','40','GP','20',0,0,TRUE,1000.32,'2018-10-08','BARGE',25,4,10),(65,'EUR','40','RF','40',0,0,FALSE,900.30,'2018-10-08','TRUCK',25,1,NULL),(66,'EUR','40','GP','40',0,0,FALSE,1100.30,'2018-10-08','TRUCK',25,2,NULL),(67,'EUR','40','GP','40',0,0,FALSE,1300.35,'2018-10-08','TRUCK',25,3,NULL),(68,'EUR','40','GP','40',0,0,FALSE,1500.30,'2018-10-08','TRUCK',25,4,NULL),(69,'EUR','40','GP','40',0,0,TRUE,1700.30,'2018-10-08','RAIL/ROAD',25,2,10),(70,'EUR','40','GP','40',0,0,TRUE,1300.30,'2018-10-08','RAIL/ROAD',25,2,10),(71,'EUR','40','GP','40',0,0,TRUE,1400.30,'2018-10-08','RAIL/ROAD',25,4,10),(72,'EUR','40','GP','40',0,0,TRUE,1500.30,'2018-10-08','RAIL/ROAD',25,3,10),(73,'EUR','40','GP','40',0,0,TRUE,1600.30,'2018-10-08','RAIL/ROAD',25,1,10),(74,'EUR','40','GP','40',0,0,TRUE,1000.30,'2018-10-08','RAIL',25,2,10),(75,'EUR','40','GP','40',0,0,TRUE,700.30,'2018-10-08','BARGE',25,2,10);


--
-- Table structure for table `oceanroute`
--

DROP TABLE IF EXISTS `oceanroute`;
 
CREATE TABLE `oceanroute` (
  `id` int(20) NOT NULL auto_increment,
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
) 

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ocean_route_errors`
--

DROP TABLE IF EXISTS `ocean_route_errors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 
CREATE TABLE `ocean_route_errors` (
  `ocean_route_id` int(20) NOT NULL,
  `errors` varchar(255) DEFAULT NULL,
  
   FOREIGN KEY  (`ocean_route_id`) references oceanroute(ID)
) 


/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oceanroute`
--


INSERT INTO `oceanroute` VALUES (77,'HKHKG','564077','BRSSZ','115861','41020SIM1768','','',31,'','','','','',''),(78,'HKHKG','564077','BRSSZ','115861','41020SIM1763','','',32,'','','','','',''),(79,'HKHKG','564077','BRSSZ','565152 ','41060SIM1887','','',33,'','','','','',''),(80,'HKHKG','564077','BRSSZ','565151','61010SIM1827','41060SIM1886','',48,'BRSPB','','','','','565153'),(81,'HKHKG','564077','BRSSZ','1680535','11050SIM0004','71020SIM1810','',44,'ESALG','','','','','1621517'),(82,'HKHKG','564077','BRSSZ','565151','52015SIM1670','','75010SIM1963',37,'COCTG','568229','','COCTG','','1528540'),(83,'HKHKG','564077','BRSSZ','565151','61010SIM1827','41060SIM1886','',47,'BRRIO','','','','','568761'),(84,'HKHKG','564077','BRSSZ','565151','61030SIM1923','','41070SIM0006',49,'ARBUE','568677','','ARBUE','','568675'),(85,'HKHKG','564077','BRSSZ','565151','42610SIM1703','41020SIM1768','',86,'BRPNG','','','','','565149'),(86,'HKHKG','564077','BRSSZ','565151','41020SIM1763','71010SIM1884','',39,'SGSIN','','','','','568079');


/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `ocean_route_errors` VALUES (83,'MIN_TRANSIT_RATIO'),(84,'NSHKF'),(84,'MIN_TRANSIT_RATIO'),(85,'REMAX'),(85,'MAX_TRANSIT_RATIO'),(85,'MIN_TRANSIT_RATIO'),(86,'MIN_TRANSIT_RATIO');

