 alter table `camera` add column `description` varchar(100) after url;
 alter table `wiegand_device` add column `description` varchar(100) after port;
 alter table`camera` modify column `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT;
 drop tavle`face_hsitory`;
 CREATE TABLE `face_history` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
`camera_url` varchar(100) default NULL,
`camera_description` varchar(100) default NULL,
`wiegand_ip` varchar(100) default NULL,
`wiegand_port` int(11) unsigned default NULL,
`wiegand_description` varchar(100) default NULL,
`cardNumber`  bigint(20) unsigned default NULL,
`galleryType` varchar(10) default NULL,
`created` TIMESTAMP default CURRENT_TIMESTAMP,
PRIMARY KEY (`id`),
INDEX(`cardNumber`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
alter table `face_user` drop column `fileName`;
alter table `face_user` drop column `archived`;
alter table `sys_param` modify column `param_name` varchar(50);
alter table `wiegand_device` modify column `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT;