CREATE TABLE `face_history` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
`camera_url` varchar(100) default NULL,
`camera_name` varchar(100) default NULL,
`wiegand_ip` varchar(100) default NULL,
`wiegand_port` int(11) unsigned default NULL,
`wiegand_description` varchar(100) default NULL,
`cardNumber`  bigint(20) unsigned default NULL,
`galleryType` varchar(10) default NULL,
`created` TIMESTAMP default CURRENT_TIMESTAMP,
PRIMARY KEY (`id`),
INDEX(`cardNumber`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;