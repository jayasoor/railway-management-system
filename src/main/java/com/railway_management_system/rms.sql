-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 06, 2021 at 12:47 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rms`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `ad_uname` varchar(20) NOT NULL,
  `ad_pw` varchar(20) NOT NULL,
  `ad_id` varchar(15) NOT NULL,
  `ad_fname` varchar(20) NOT NULL,
  `ad_lname` varchar(20) NOT NULL,
  `ad_email` varchar(50) NOT NULL,
  PRIMARY KEY (`ad_id`),
  UNIQUE KEY `ad_uname` (`ad_uname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `gen_ticket`
--

DROP TABLE IF EXISTS `gen_ticket`;
CREATE TABLE IF NOT EXISTS `gen_ticket` (
  `tick_id` varchar(10) NOT NULL,
  `pas_id` varchar(10) NOT NULL,
  `route_id` varchar(10) NOT NULL,
  `tick_date` date NOT NULL,
  `pay_method` varchar(10) NOT NULL,
  `source` varchar(10) NOT NULL,
  `destination` varchar(10) NOT NULL,
  `price` int(20) NOT NULL,
  PRIMARY KEY (`tick_id`),
  KEY `pas_id` (`pas_id`),
  KEY `route_id` (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `manipulate`
--

DROP TABLE IF EXISTS `manipulate`;
CREATE TABLE IF NOT EXISTS `manipulate` (
  `u_name` varchar(20) NOT NULL,
  `date` datetime(6) NOT NULL,
  `note` varchar(30) NOT NULL,
  PRIMARY KEY (`u_name`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
CREATE TABLE IF NOT EXISTS `passenger` (
  `pas_id` varchar(15) NOT NULL,
  `pas_uname` varchar(15) NOT NULL,
  `pas_pw` varchar(12) NOT NULL,
  `pas_stat` tinyint(1) NOT NULL,
  `pas_fname` varchar(20) NOT NULL,
  `pas_lname` varchar(20) NOT NULL,
  `pas_email` varchar(50) NOT NULL,
  `pas_tele` varchar(15) NOT NULL,
  `pas_credit` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`pas_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pri_ticket`
--

DROP TABLE IF EXISTS `pri_ticket`;
CREATE TABLE IF NOT EXISTS `pri_ticket` (
  `tick_id` varchar(10) NOT NULL,
  `pas_id` varchar(10) NOT NULL,
  `route_id` varchar(10) NOT NULL,
  `tick_date` varchar(6) NOT NULL,
  `paymnet_method` varchar(10) NOT NULL,
  `source` varchar(10) NOT NULL,
  `destination` varchar(10) NOT NULL,
  PRIMARY KEY (`tick_id`),
  KEY `pas_id` (`pas_id`),
  KEY `route_id` (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
CREATE TABLE IF NOT EXISTS `purchase` (
  `p_num` varchar(10) NOT NULL,
  `tick_id` varchar(10) NOT NULL,
  `p_date` date NOT NULL,
  PRIMARY KEY (`p_num`),
  KEY `tick_id` (`tick_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
CREATE TABLE IF NOT EXISTS `route` (
  `r_id` varchar(10) NOT NULL,
  `r_source` varchar(10) NOT NULL,
  `r_gen_price` varchar(10) NOT NULL,
  `r_pri_price` varchar(10) NOT NULL,
  `r_distance` int(10) NOT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `seat`
--

DROP TABLE IF EXISTS `seat`;
CREATE TABLE IF NOT EXISTS `seat` (
  `s_num` int(10) NOT NULL,
  `train_id` varchar(10) NOT NULL,
  `comp_number` int(10) NOT NULL,
  `s_availability` varchar(10) NOT NULL,
  PRIMARY KEY (`s_num`,`train_id`),
  KEY `train_id` (`train_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `timetable`
--

DROP TABLE IF EXISTS `timetable`;
CREATE TABLE IF NOT EXISTS `timetable` (
  `tb_slot` varchar(10) NOT NULL,
  `tb_trainid` varchar(10) NOT NULL,
  `tb_routeid` varchar(10) NOT NULL,
  `tb_station` varchar(10) NOT NULL,
  `tb_arrival` datetime(6) NOT NULL,
  `tb_departure` datetime(6) NOT NULL,
  PRIMARY KEY (`tb_slot`),
  KEY `tb_routeid` (`tb_routeid`),
  KEY `tb_trainid` (`tb_trainid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `train`
--

DROP TABLE IF EXISTS `train`;
CREATE TABLE IF NOT EXISTS `train` (
  `t_id` varchar(10) NOT NULL,
  `t_capacity` int(20) NOT NULL,
  `t_availablecap` int(20) NOT NULL,
  `t_routeid` varchar(10) NOT NULL,
  `t_1st` varchar(3) NOT NULL,
  `t_2nd` varchar(3) NOT NULL,
  `t_3rd` varchar(3) NOT NULL,
  PRIMARY KEY (`t_id`),
  KEY `t_routeid` (`t_routeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`ad_uname`) REFERENCES `manipulate` (`u_name`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `gen_ticket`
--
ALTER TABLE `gen_ticket`
  ADD CONSTRAINT `gen_ticket_ibfk_1` FOREIGN KEY (`pas_id`) REFERENCES `passenger` (`pas_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `gen_ticket_ibfk_2` FOREIGN KEY (`route_id`) REFERENCES `route` (`r_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `gen_ticket_ibfk_3` FOREIGN KEY (`tick_id`) REFERENCES `pri_ticket` (`tick_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `pri_ticket`
--
ALTER TABLE `pri_ticket`
  ADD CONSTRAINT `pri_ticket_ibfk_1` FOREIGN KEY (`pas_id`) REFERENCES `passenger` (`pas_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `pri_ticket_ibfk_2` FOREIGN KEY (`route_id`) REFERENCES `route` (`r_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `purchase`
--
ALTER TABLE `purchase`
  ADD CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`tick_id`) REFERENCES `gen_ticket` (`tick_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `seat`
--
ALTER TABLE `seat`
  ADD CONSTRAINT `seat_ibfk_1` FOREIGN KEY (`train_id`) REFERENCES `train` (`t_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `timetable`
--
ALTER TABLE `timetable`
  ADD CONSTRAINT `timetable_ibfk_1` FOREIGN KEY (`tb_routeid`) REFERENCES `route` (`r_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `timetable_ibfk_2` FOREIGN KEY (`tb_trainid`) REFERENCES `train` (`t_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `train`
--
ALTER TABLE `train`
  ADD CONSTRAINT `train_ibfk_1` FOREIGN KEY (`t_routeid`) REFERENCES `route` (`r_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
