/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50615
Source Host           : 127.0.0.1:3306
Source Database       : ergasia_10

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2014-01-23 19:07:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for domatia
-- ----------------------------
DROP TABLE IF EXISTS `domatia`;
CREATE TABLE `domatia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kodikos` mediumint(9) DEFAULT NULL,
  `atoma` mediumint(9) DEFAULT NULL,
  `timi` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `kodikos` (`kodikos`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of domatia
-- ----------------------------
INSERT INTO `domatia` VALUES ('1', '101', '2', '100');
INSERT INTO `domatia` VALUES ('2', '102', '3', '140');
INSERT INTO `domatia` VALUES ('3', '103', '2', '100');
INSERT INTO `domatia` VALUES ('4', '104', '2', '100');
INSERT INTO `domatia` VALUES ('5', '105', '3', '140');
INSERT INTO `domatia` VALUES ('6', '106', '1', '50');
INSERT INTO `domatia` VALUES ('7', '201', '4', '200');
INSERT INTO `domatia` VALUES ('8', '124', '4', '170');
INSERT INTO `domatia` VALUES ('9', '500', '4', '200');

-- ----------------------------
-- Table structure for kratiseis
-- ----------------------------
DROP TABLE IF EXISTS `kratiseis`;
CREATE TABLE `kratiseis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `onoma` varchar(255) DEFAULT NULL,
  `eponimo` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `imera_eisodou` datetime DEFAULT NULL,
  `imera_exodou` datetime DEFAULT NULL,
  `domatio_id` int(11) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `domatio_id` (`domatio_id`),
  CONSTRAINT `domatio_id` FOREIGN KEY (`domatio_id`) REFERENCES `domatia` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kratiseis
-- ----------------------------
INSERT INTO `kratiseis` VALUES ('11', 'asdads', 'assdsa', '1231231232', '2014-10-05 00:00:00', '2014-11-04 00:00:00', '1', '0000-00-00 00:00:00');
INSERT INTO `kratiseis` VALUES ('12', 'aaaa', 'bbbb', '1231231232', '2014-01-10 00:00:00', '2014-01-12 00:00:00', '2', '2014-01-22 11:55:52');
INSERT INTO `kratiseis` VALUES ('13', 'aaaa', 'bbbb', '1231231232', '2014-01-01 00:00:00', '2014-01-02 00:00:00', '1', '0000-00-00 00:00:00');
INSERT INTO `kratiseis` VALUES ('14', 'aaaa', 'bbbb', '1231231232', '2014-01-01 00:00:00', '2014-01-03 00:00:00', '3', '2014-01-22 11:57:25');
INSERT INTO `kratiseis` VALUES ('15', 'aaaaaaa', 'bbbbbbb', '1234123443', '2014-01-01 00:00:00', '2014-01-02 00:00:00', '2', '2014-01-22 12:38:42');
INSERT INTO `kratiseis` VALUES ('16', 'asdasdasd', 'asdadad', '1254789658', '2014-01-04 00:00:00', '2014-01-06 00:00:00', '2', '2014-01-22 12:41:48');
INSERT INTO `kratiseis` VALUES ('17', 'asdasdasd', 'asdadad', '1254789658', '2014-01-04 00:00:00', '2014-01-06 00:00:00', '5', '2014-01-22 12:42:15');
INSERT INTO `kratiseis` VALUES ('18', 'asdasdasd', 'asdadad', '1254789658', '2014-01-06 00:00:00', '2014-01-08 00:00:00', '2', '2014-01-22 12:42:46');
INSERT INTO `kratiseis` VALUES ('19', 'Γιάννης', 'Καππ', '1234321233', '2014-03-01 00:00:00', '2014-04-01 00:00:00', '2', '2014-01-22 15:30:16');
INSERT INTO `kratiseis` VALUES ('20', 'Γιάννης', 'Καππ', '1234321233', '2014-03-01 00:00:00', '2014-04-01 00:00:00', '5', '2014-01-22 15:30:36');
INSERT INTO `kratiseis` VALUES ('21', 'Γιώργος', 'Καππ', '6985245874', '2014-01-22 00:00:00', '2014-01-24 00:00:00', '6', '2014-01-22 15:46:18');
INSERT INTO `kratiseis` VALUES ('22', 'Κάποιος', 'Κάπου', '6978541277', '2014-01-20 00:00:00', '2014-01-23 00:00:00', '1', '2014-01-22 23:57:38');
INSERT INTO `kratiseis` VALUES ('23', 'Gian', 'Kapo', '6985247856', '2014-02-01 00:00:00', '2014-02-10 00:00:00', '7', '2014-01-23 00:54:35');
INSERT INTO `kratiseis` VALUES ('24', 'Gian', 'Kapo', '6985247856', '2014-02-01 00:00:00', '2014-02-10 00:00:00', '8', '2014-01-23 00:54:39');
INSERT INTO `kratiseis` VALUES ('25', 'Gian', 'Kapo', '6985247856', '2014-02-01 00:00:00', '2014-02-10 00:00:00', '1', '2014-01-23 00:55:18');
INSERT INTO `kratiseis` VALUES ('26', 'Gian', 'Kapo', '6985247856', '2014-02-01 00:00:00', '2014-02-10 00:00:00', '3', '2014-01-23 00:55:50');
INSERT INTO `kratiseis` VALUES ('27', 'Gian', 'Kapo', '6985247856', '2014-02-01 00:00:00', '2014-02-10 00:00:00', '4', '2014-01-23 00:56:58');
INSERT INTO `kratiseis` VALUES ('28', 'Giwrgos', 'Raku', '6989524125', '2014-02-12 00:00:00', '2014-02-14 00:00:00', '7', '2014-01-23 01:05:39');
INSERT INTO `kratiseis` VALUES ('29', 'Giwrgos', 'Raku', '6989524125', '2014-02-12 00:00:00', '2014-02-14 00:00:00', '8', '2014-01-23 01:06:07');
INSERT INTO `kratiseis` VALUES ('30', 'Dimitris', 'Manolis', '69895241aa', '2014-02-12 00:00:00', '2014-02-14 00:00:00', '9', '2014-01-23 01:09:58');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `who` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('0', 'idioktitis', 'idioktitis', '3');
INSERT INTO `users` VALUES ('1', 'ipalilos', 'ipalilos', '2');
INSERT INTO `users` VALUES ('2', 'pelatis', 'pelatis', '1');
