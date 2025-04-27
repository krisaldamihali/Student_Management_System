-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: student_management_system
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
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_id` varchar(50) NOT NULL,
  `course_name` varchar(255) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `instructor` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('CS101','Introduction to Computer Science','Computer Science','Dr. Smith'),('CS102','Data Structures and Algorithms','Computer Science','Prof. Johnson'),('CS103','Calculus II','Maths','Dr. John Smith'),('ENG201','English Literature','Arts','Ms. Williams'),('MATH101','Calculus I','Mathematics','Dr. Lee');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grade_log`
--

DROP TABLE IF EXISTS `grade_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grade_log` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `course_id` varchar(50) NOT NULL,
  `grade` decimal(5,2) DEFAULT NULL,
  `action_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `action` varchar(50) NOT NULL,
  PRIMARY KEY (`log_id`),
  KEY `student_id` (`student_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `grade_log_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE,
  CONSTRAINT `grade_log_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grade_log`
--

LOCK TABLES `grade_log` WRITE;
/*!40000 ALTER TABLE `grade_log` DISABLE KEYS */;
INSERT INTO `grade_log` VALUES (1,5,'CS101',95.00,'2025-01-24 10:15:32','INSERT'),(2,23,'CS101',95.00,'2025-01-24 11:03:07','INSERT');
/*!40000 ALTER TABLE `grade_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS `grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grades` (
  `grade_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `course_id` varchar(50) DEFAULT NULL,
  `grade` decimal(5,2) NOT NULL,
  `semester` varchar(20) NOT NULL,
  `year` int NOT NULL,
  PRIMARY KEY (`grade_id`),
  KEY `student_id` (`student_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `grades_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  CONSTRAINT `grades_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades`
--

LOCK TABLES `grades` WRITE;
/*!40000 ALTER TABLE `grades` DISABLE KEYS */;
INSERT INTO `grades` VALUES (18,3,'ENG201',78.30,'Fall',2023),(19,1,'CS102',88.70,'Spring',2023),(20,4,'CS101',95.00,'Fall',2023),(36,2,'MATH101',96.00,'Fall',2023),(37,2,'MATH101',75.10,'Spring',2025),(39,5,'CS101',95.00,'Fall',2025),(40,23,'CS101',95.00,'Fall',2025);
/*!40000 ALTER TABLE `grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` enum('male','female') NOT NULL,
  `study_year` int NOT NULL,
  `department` varchar(100) NOT NULL,
  `enrollment_date` date DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `status` enum('active','inactive') DEFAULT 'active',
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=234568 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'Ardit','Bajrami','2000-05-15','male',2,'Computer Science','2024-10-20','ardit.bajrami@email.com','123456789','active'),(2,'Elira','Krasniqi','2001-08-22','female',1,'Electrical Engineering','2024-10-20','elira.krasniqi@email.com','987654321','active'),(3,'Driton','Rexhepi','1999-02-10','male',3,'Business Administration','2024-10-21','driton.rexhepi@email.com','555123456','active'),(4,'Mirjeta','Hoxha','2000-11-01','female',2,'Law','2024-10-21','mirjeta.hoxha@email.com','555987654','active'),(5,'Arben','Shala','1998-06-30','male',4,'Mechanical Engineering','2024-10-22','arben.shala@email.com','555321654','active'),(6,'Jona','Nikaj','2000-03-17','female',1,'Architecture','2024-10-22','jona.nikaj@email.com','555654987','inactive'),(7,'Edi','Luka','1999-09-12','male',2,'Medicine','2024-10-20','edi.luka@email.com','555321987','active'),(8,'Flora','Gashi','2001-12-05','female',3,'Psychology','2024-10-20','flora.gashi@email.com','555789654','active'),(9,'Luan','Aliu','2000-07-23','male',4,'Civil Engineering','2024-10-21','luan.aliu@email.com','555432876','inactive'),(10,'Dajana','Krasniqi','2001-01-18','female',1,'History','2024-10-21','dajana.krasniqi@email.com','555654321','active'),(11,'Mirlinda','Xhaferi','1999-10-25','female',2,'Sociology','2024-10-22','mirlinda.xhaferi@email.com','555987321','active'),(12,'Faton','Hajdari','2001-04-03','male',3,'Economics','2024-10-22','faton.hajdari@email.com','555654123','active'),(13,'Teuta','Gashi','2000-06-15','female',1,'Political Science','2024-10-21','teuta.gashi@email.com','555876543','active'),(14,'Lindita','Shabani','1999-11-10','female',2,'Biology','2024-10-21','lindita.shabani@email.com','555123789','inactive'),(15,'Kastriot','Berisha','2000-02-18','male',3,'Mathematics','2024-10-20','kastriot.berisha@email.com','555987123','active'),(16,'Adelina','Mujaj','2001-07-25','female',1,'Physics','2024-10-22','adelina.mujaj@email.com','555432765','active'),(17,'Dardan','Xhemajli','2000-09-30','male',4,'Chemistry','2024-10-20','dardan.xhemajli@email.com','555321654','inactive'),(18,'Maja','Daci','1999-03-07','female',2,'Linguistics','2024-10-21','maja.daci@email.com','555876123','active'),(19,'Milenko','Popovic','2000-12-17','male',1,'Engineering','2024-10-20','milenko.popovic@email.com','555987654','active'),(20,'Valbona','Sadiku','2001-05-29','female',4,'Philosophy','2024-10-22','valbona.sadiku@email.com','555654321','inactive'),(21,'John','Doe','2006-01-10','male',2,'CS','2023-01-27','john@gmail.com','1234567890','active'),(23,'Alice','Smith','2025-01-14','male',2,'CS','2025-01-17','afagah@gmail.com','1234567890','active');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin@gmail.com','Admin123!'),(2,'kris','kris@gmail.com','Admin123!'),(3,'User26','user26@gmail.com','User26**'),(4,'aaaaaaa','aaaa@umt.edu.al','Admin123!'),(5,'admin1','aadmin@gmail.com','Admin1234!');
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

-- Dump completed on 2025-04-28  1:39:28
