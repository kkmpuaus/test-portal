CREATE TABLE `portal_user_audit_log` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
`sub_id` varchar(20) NULL,
`audit_date` TIMESTAMP default CURRENT_TIMESTAMP,
`user_id` varchar(20) NOT NULL,
`display_name` nvarchar(30) NULL,
`detail` varchar(200) NOT NULL,
`remark` varchar(200) default NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
