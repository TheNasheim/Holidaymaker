-- --------------------------------------------------------
-- Värd:                         192.168.0.90
-- Serverversion:                5.7.29-log - MySQL Community Server (GPL)
-- Server-OS:                    Win64
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumpar databasstruktur för holidaymaker
DROP DATABASE IF EXISTS `holidaymaker`;
CREATE DATABASE IF NOT EXISTS `holidaymaker` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `holidaymaker`;

-- Dumpar struktur för tabell holidaymaker.booking
DROP TABLE IF EXISTS `booking`;
CREATE TABLE IF NOT EXISTS `booking` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) unsigned NOT NULL,
  `guests` int(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK1_booking_customer` (`customer_id`),
  CONSTRAINT `FK1_booking_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell holidaymaker.booking: ~17 rows (ungefär)
DELETE FROM `booking`;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` (`id`, `customer_id`, `guests`) VALUES
	(1, 5, 2),
	(2, 3, 2),
	(3, 1, 4),
	(4, 8, 4),
	(5, 13, 6),
	(6, 3, 3),
	(7, 15, 5),
	(8, 9, 1),
	(9, 12, 2),
	(10, 11, 2),
	(11, 11, 4),
	(12, 13, 4),
	(13, 7, 4),
	(14, 10, 4),
	(15, 1, 3),
	(16, 7, 5),
	(17, 4, 2);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;

-- Dumpar struktur för tabell holidaymaker.customer
DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL DEFAULT '',
  `last_name` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell holidaymaker.customer: ~15 rows (ungefär)
DELETE FROM `customer`;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`, `first_name`, `last_name`) VALUES
	(1, 'Tammie', 'Saines'),
	(2, 'Brigida', 'Lillico'),
	(3, 'Allan', 'Parradice'),
	(4, 'Gabby', 'Inge'),
	(5, 'Winifred', 'Durker'),
	(6, 'Carmon', 'Springham'),
	(7, 'Ronny', 'Carlino'),
	(8, 'Kalie', 'Tunesi'),
	(9, 'Angelique', 'Hargess'),
	(10, 'Care', 'Guilloneau'),
	(11, 'Madeline', 'Johanchon'),
	(12, 'Winifield', 'D\'Aguanno'),
	(13, 'Allx', 'Adderson'),
	(14, 'Clea', 'Robbe'),
	(15, 'Lane', 'Francie');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;

-- Dumpar struktur för tabell holidaymaker.hotel
DROP TABLE IF EXISTS `hotel`;
CREATE TABLE IF NOT EXISTS `hotel` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `hotel_name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `zip` varchar(6) NOT NULL,
  `city` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  `phone` varchar(25) NOT NULL,
  `pool` enum('true','false') NOT NULL DEFAULT 'false',
  `childactivity` enum('true','false') NOT NULL DEFAULT 'false',
  `eveningevents` enum('true','false') NOT NULL DEFAULT 'false',
  `distancetobeach` int(11) NOT NULL DEFAULT '0',
  `distancetocenter` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell holidaymaker.hotel: ~5 rows (ungefär)
DELETE FROM `hotel`;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
INSERT INTO `hotel` (`id`, `hotel_name`, `address`, `zip`, `city`, `country`, `phone`, `pool`, `childactivity`, `eveningevents`, `distancetobeach`, `distancetocenter`) VALUES
	(1, 'Hotel Flamenco', 'Calle Luis Antunez 51', '35006', 'Las Palmas', 'Spain', '+34 6541 3884', 'true', 'false', 'true', 200, 700),
	(2, 'Hotel Metzgerwirt', 'Pöllmühle 7', '6365 ', 'Kirchberg', 'Austria', '+43 6849 2684', 'false', 'true', 'true', 201000, 200),
	(3, 'Hotel Cristina', 'Gomera 6', '35008', 'Las Palmas', 'Spain', '+34 6541 8328', 'true', 'false', 'true', 50, 2400),
	(4, 'Marble Arch Inn Hotel', '49/50 Upper Berkeley Street', '684235', 'London', 'England', '+002 34545 345 23', 'false', 'false', 'true', 2450, 0),
	(5, 'Grande Villas Resort', '12118 Turtle Cay Circle', '32836', 'Orlando', 'USA', '+1 800 498 565', 'true', 'true', 'true', 45000, 2000);
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;

-- Dumpar struktur för vy holidaymaker.hotels_room_count
DROP VIEW IF EXISTS `hotels_room_count`;
-- Skapar temporärtabell för att hantera VIEW-beroendefel
CREATE TABLE `hotels_room_count` (
	`id` INT(11) UNSIGNED NOT NULL,
	`hotel_name` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`single_room` BIGINT(21) NOT NULL,
	`double_room` BIGINT(21) NOT NULL,
	`quad_room` BIGINT(21) NOT NULL,
	`queen_room` BIGINT(21) NOT NULL,
	`king_room` BIGINT(21) NOT NULL
) ENGINE=MyISAM;

-- Dumpar struktur för vy holidaymaker.hotels_with_rating
DROP VIEW IF EXISTS `hotels_with_rating`;
-- Skapar temporärtabell för att hantera VIEW-beroendefel
CREATE TABLE `hotels_with_rating` (
	`id` INT(11) UNSIGNED NOT NULL,
	`hotel_name` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`address` VARCHAR(100) NOT NULL COLLATE 'latin1_swedish_ci',
	`zip` VARCHAR(6) NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`country` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`phone` VARCHAR(25) NOT NULL COLLATE 'latin1_swedish_ci',
	`stars` DECIMAL(12,1) NULL,
	`pool` ENUM('true','false') NOT NULL COLLATE 'latin1_swedish_ci',
	`childactivity` ENUM('true','false') NOT NULL COLLATE 'latin1_swedish_ci',
	`eveningevents` ENUM('true','false') NOT NULL COLLATE 'latin1_swedish_ci',
	`distancetobeach` INT(11) NOT NULL,
	`distancetocenter` INT(11) NOT NULL
) ENGINE=MyISAM;

-- Dumpar struktur för tabell holidaymaker.price
DROP TABLE IF EXISTS `price`;
CREATE TABLE IF NOT EXISTS `price` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) unsigned NOT NULL DEFAULT '0',
  `start_date` date NOT NULL DEFAULT '0000-00-00',
  `end_date` date NOT NULL DEFAULT '0000-00-00',
  `single_room` double(11,2) DEFAULT NULL,
  `double_room` double(11,2) DEFAULT NULL,
  `quad_room` double(11,2) DEFAULT NULL,
  `queen_room` double(11,2) DEFAULT NULL,
  `king_room` double(11,2) DEFAULT NULL,
  `extra_bed` double(11,2) DEFAULT NULL,
  `breakfast` double(11,2) DEFAULT NULL,
  `half_board` double(11,2) DEFAULT NULL,
  `full_board` double(11,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell holidaymaker.price: ~7 rows (ungefär)
DELETE FROM `price`;
/*!40000 ALTER TABLE `price` DISABLE KEYS */;
INSERT INTO `price` (`id`, `hotel_id`, `start_date`, `end_date`, `single_room`, `double_room`, `quad_room`, `queen_room`, `king_room`, `extra_bed`, `breakfast`, `half_board`, `full_board`) VALUES
	(1, 1, '2020-06-01', '2020-06-21', 480.00, 512.00, 0.00, 0.00, 0.00, 14.00, 100.00, 212.00, 402.00),
	(2, 2, '2020-06-01', '2020-08-01', 223.00, 285.00, 0.00, 442.00, 843.00, 20.00, 0.00, 150.00, 0.00),
	(3, 1, '2020-06-22', '2020-08-01', 520.00, 581.00, 0.00, 0.00, 0.00, 15.00, 100.00, 245.00, 457.00),
	(4, 3, '2020-06-01', '2020-08-01', 470.00, 557.00, 680.00, 710.00, 1400.00, 21.00, 144.00, 270.00, 512.00),
	(5, 4, '2020-06-01', '2020-07-31', 481.00, 670.00, 1145.00, 1850.00, 0.00, 88.00, 351.00, 481.00, 780.00),
	(6, 5, '2020-06-01', '2020-06-30', 354.00, 485.00, 875.00, 1187.00, 3587.00, 20.00, 254.00, 288.00, 852.00),
	(7, 5, '2020-07-01', '2020-08-01', 0.00, 471.00, 753.00, 1102.00, 0.00, 20.00, 210.00, 240.00, 352.00);
/*!40000 ALTER TABLE `price` ENABLE KEYS */;

-- Dumpar struktur för tabell holidaymaker.rating
DROP TABLE IF EXISTS `rating`;
CREATE TABLE IF NOT EXISTS `rating` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) unsigned NOT NULL DEFAULT '0',
  `stars` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK1_review_hotel` (`hotel_id`),
  CONSTRAINT `FK1_review_hotel` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell holidaymaker.rating: ~19 rows (ungefär)
DELETE FROM `rating`;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` (`id`, `hotel_id`, `stars`) VALUES
	(1, 1, 3),
	(2, 1, 4),
	(3, 1, 2),
	(4, 1, 3),
	(5, 1, 5),
	(6, 1, 4),
	(7, 1, 1),
	(8, 1, 4),
	(9, 2, 3),
	(10, 2, 2),
	(11, 3, 2),
	(12, 3, 3),
	(13, 2, 5),
	(14, 2, 4),
	(15, 2, 2),
	(16, 4, 2),
	(17, 4, 3),
	(18, 5, 2),
	(19, 5, 3);
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;

-- Dumpar struktur för tabell holidaymaker.reservation
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) unsigned NOT NULL DEFAULT '0',
  `room_id` int(11) unsigned NOT NULL DEFAULT '0',
  `checkin_date` date NOT NULL,
  `checkout_date` date NOT NULL,
  `extra_bed` enum('true','false') NOT NULL DEFAULT 'false',
  `board` enum('none','breakfast','half','full') NOT NULL DEFAULT 'none',
  `price` double(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `FK1_reserv_booking_id` (`booking_id`),
  KEY `FK2_reserv_room_id` (`room_id`),
  CONSTRAINT `FK1_reserv_booking_id` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK2_reserv_room_id` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell holidaymaker.reservation: ~25 rows (ungefär)
DELETE FROM `reservation`;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` (`id`, `booking_id`, `room_id`, `checkin_date`, `checkout_date`, `extra_bed`, `board`, `price`) VALUES
	(1, 1, 2, '2020-06-05', '2020-06-07', 'false', 'none', 1536.00),
	(2, 2, 2, '2020-06-09', '2020-06-12', 'false', 'half', 2048.00),
	(3, 4, 19, '2020-06-19', '2020-06-21', 'false', 'breakfast', 2103.00),
	(4, 4, 20, '2020-06-19', '2020-06-21', 'false', 'breakfast', 2103.00),
	(5, 5, 44, '2020-06-04', '2020-06-07', 'false', 'none', 2680.00),
	(6, 5, 45, '2020-06-04', '2020-06-07', 'false', 'none', 4580.00),
	(7, 6, 2, '2020-06-15', '2020-06-21', 'true', 'none', 3682.00),
	(8, 7, 8, '2020-07-01', '2020-07-05', 'false', 'none', 1115.00),
	(9, 7, 9, '2020-07-01', '2020-07-05', 'false', 'none', 1425.00),
	(10, 7, 10, '2020-07-01', '2020-07-05', 'false', 'none', 1425.00),
	(11, 8, 33, '2020-07-28', '2020-07-29', 'false', 'breakfast', 1664.00),
	(12, 9, 16, '2020-07-05', '2020-07-05', 'false', 'none', 843.00),
	(13, 10, 60, '2020-06-05', '2020-06-07', 'false', 'none', 1455.00),
	(14, 11, 63, '2020-06-09', '2020-06-11', 'false', 'none', 1455.00),
	(15, 11, 64, '2020-06-09', '2020-06-11', 'false', 'none', 1455.00),
	(16, 12, 39, '2020-06-09', '2020-06-11', 'false', 'full', 2010.00),
	(17, 12, 40, '2020-06-09', '2020-06-11', 'false', 'full', 2010.00),
	(18, 13, 65, '2020-06-26', '2020-06-26', 'false', 'none', 875.00),
	(19, 14, 4, '2020-06-17', '2020-06-21', 'false', 'full', 2560.00),
	(20, 14, 5, '2020-06-17', '2020-06-21', 'false', 'full', 2560.00),
	(21, 15, 14, '2020-07-09', '2020-07-12', 'true', 'none', 1848.00),
	(22, 16, 18, '2020-07-24', '2020-07-26', 'true', 'breakfast', 1905.00),
	(23, 16, 19, '2020-07-24', '2020-07-26', 'true', 'breakfast', 2166.00),
	(24, 16, 20, '2020-07-24', '2020-07-26', 'true', 'breakfast', 2166.00),
	(25, 17, 63, '2020-07-16', '2020-07-16', 'false', 'full', 471.00);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;

-- Dumpar struktur för tabell holidaymaker.room
DROP TABLE IF EXISTS `room`;
CREATE TABLE IF NOT EXISTS `room` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) unsigned NOT NULL DEFAULT '0',
  `room_type` enum('Single','Double','Quad','Queen','King') NOT NULL DEFAULT 'Single',
  PRIMARY KEY (`id`),
  KEY `FK1_room_hotel_id` (`hotel_id`),
  CONSTRAINT `FK1_room_hotel_id` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell holidaymaker.room: ~71 rows (ungefär)
DELETE FROM `room`;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` (`id`, `hotel_id`, `room_type`) VALUES
	(1, 1, 'Double'),
	(2, 1, 'Double'),
	(3, 1, 'Double'),
	(4, 1, 'Double'),
	(5, 1, 'Double'),
	(6, 1, 'Single'),
	(7, 1, 'Single'),
	(8, 2, 'Single'),
	(9, 2, 'Double'),
	(10, 2, 'Double'),
	(11, 2, 'Double'),
	(12, 2, 'Double'),
	(13, 2, 'Double'),
	(14, 2, 'Queen'),
	(15, 2, 'Queen'),
	(16, 2, 'King'),
	(17, 3, 'Single'),
	(18, 3, 'Single'),
	(19, 3, 'Double'),
	(20, 3, 'Double'),
	(21, 3, 'Double'),
	(22, 3, 'Double'),
	(23, 3, 'Double'),
	(24, 3, 'Quad'),
	(25, 3, 'Quad'),
	(26, 3, 'Quad'),
	(27, 3, 'Queen'),
	(28, 3, 'Queen'),
	(29, 3, 'King'),
	(30, 4, 'Single'),
	(31, 4, 'Single'),
	(32, 4, 'Single'),
	(33, 4, 'Single'),
	(34, 4, 'Single'),
	(35, 4, 'Double'),
	(36, 4, 'Double'),
	(37, 4, 'Double'),
	(38, 4, 'Double'),
	(39, 4, 'Double'),
	(40, 4, 'Double'),
	(41, 4, 'Double'),
	(42, 4, 'Double'),
	(43, 4, 'Double'),
	(44, 4, 'Double'),
	(45, 4, 'Quad'),
	(46, 4, 'Quad'),
	(47, 4, 'Quad'),
	(48, 4, 'Quad'),
	(49, 4, 'Queen'),
	(50, 4, 'Queen'),
	(51, 5, 'Single'),
	(52, 5, 'Single'),
	(53, 5, 'Single'),
	(54, 5, 'Single'),
	(55, 5, 'Single'),
	(56, 5, 'Single'),
	(57, 5, 'Single'),
	(58, 5, 'Single'),
	(59, 5, 'Double'),
	(60, 5, 'Double'),
	(61, 5, 'Double'),
	(62, 5, 'Double'),
	(63, 5, 'Double'),
	(64, 5, 'Double'),
	(65, 5, 'Quad'),
	(66, 5, 'Quad'),
	(67, 5, 'Quad'),
	(68, 5, 'Quad'),
	(69, 5, 'Quad'),
	(70, 5, 'Quad'),
	(71, 5, 'Quad');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;

-- Dumpar struktur för vy holidaymaker.hotels_room_count
DROP VIEW IF EXISTS `hotels_room_count`;
-- Tar bort temporärtabell och skapar slutgiltlig VIEW-struktur
DROP TABLE IF EXISTS `hotels_room_count`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `hotels_room_count` AS select `hotel`.`id` AS `id`,`hotel`.`hotel_name` AS `hotel_name`,`hotel`.`city` AS `city`,count(if((`room`.`room_type` = 'Single'),1,NULL)) AS `single_room`,count(if((`room`.`room_type` = 'Double'),1,NULL)) AS `double_room`,count(if((`room`.`room_type` = 'Quad'),1,NULL)) AS `quad_room`,count(if((`room`.`room_type` = 'Queen'),1,NULL)) AS `queen_room`,count(if((`room`.`room_type` = 'King'),1,NULL)) AS `king_room` from (`hotel` left join `room` on((`hotel`.`id` = `room`.`hotel_id`))) group by `hotel`.`id`;

-- Dumpar struktur för vy holidaymaker.hotels_with_rating
DROP VIEW IF EXISTS `hotels_with_rating`;
-- Tar bort temporärtabell och skapar slutgiltlig VIEW-struktur
DROP TABLE IF EXISTS `hotels_with_rating`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `hotels_with_rating` AS select `hotel`.`id` AS `id`,`hotel`.`hotel_name` AS `hotel_name`,`hotel`.`address` AS `address`,`hotel`.`zip` AS `zip`,`hotel`.`city` AS `city`,`hotel`.`country` AS `country`,`hotel`.`phone` AS `phone`,round(avg(`rating`.`stars`),1) AS `stars`,`hotel`.`pool` AS `pool`,`hotel`.`childactivity` AS `childactivity`,`hotel`.`eveningevents` AS `eveningevents`,`hotel`.`distancetobeach` AS `distancetobeach`,`hotel`.`distancetocenter` AS `distancetocenter` from (`hotel` join `rating`) where (`rating`.`hotel_id` = `hotel`.`id`) group by `hotel`.`id` order by `stars` desc;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
