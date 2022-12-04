-- ----------------------------
-- Table structure for Task
-- ----------------------------
CREATE TABLE IF NOT EXISTS `crontask` (
  `id` int NOT NULL,
  `desc` varchar(100) NOT NULL,
  `expression` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
