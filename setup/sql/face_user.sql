CREATE TABLE `face_user` (
`cardNumber` bigint(20) unsigned NOT NULL,   
`personName` varchar(100) default NULL,
`galleryType` varchar(10) default NULL,
`galleryIndex` bigint(20) unsigned default NULL,
`personId` varchar(40) default NULL,
`created` TIMESTAMP default CURRENT_TIMESTAMP,
`updated` TIMESTAMP NULL default NULL,
`status` varchar(10) NOT NULL,
PRIMARY KEY (`cardNumber`),
INDEX (`galleryType`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;