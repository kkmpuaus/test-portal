CREATE TABLE `camera` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT;
`url` varchar(100) NOT NULL,
`cameraName` varchar(100) default NULL,
`min_face_size` bigint(20) unsigned NOT NULL,
`min_liveness` double(4,2) unsigned NOT NULL,
`status` varchar(10) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
