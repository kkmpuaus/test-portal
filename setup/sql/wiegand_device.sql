CREATE TABLE `wiegand_device` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
`ip` varchar(100) not NULL,
`port` smallint unsigned not NULL,
`description` varchar(100) default NULL,
`camera_id` bigint(20) unsigned DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`camera_id`) REFERENCES `camera`(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
