CREATE TABLE `m_user` (
  `user_id` varchar(24) NOT NULL,
  `password` varchar(32) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `update_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `m_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) NOT NULL,
  `update_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `m_status` (
  `status_code` char(2) NOT NULL,
  `status_name` varchar(20) NOT NULL,
  `update_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`status_code`),
  UNIQUE KEY `status_name` (`status_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(50) NOT NULL,
  `category_id` int(11) NOT NULL,
  `limit_date` date DEFAULT NULL,
  `user_id` varchar(24) NOT NULL,
  `status_code` char(2) NOT NULL,
  `memo` varchar(100) DEFAULT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`task_id`),
  KEY `fk_task_category` (`category_id`),
  KEY `fk_task_user` (`user_id`),
  KEY `fk_task_status` (`status_code`),
  CONSTRAINT `fk_task_category` FOREIGN KEY (`category_id`) REFERENCES `m_category` (`category_id`),
  CONSTRAINT `fk_task_status` FOREIGN KEY (`status_code`) REFERENCES `m_status` (`status_code`),
  CONSTRAINT `fk_task_user` FOREIGN KEY (`user_id`) REFERENCES `m_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
