<--insert portal_login-->
insert into portal_login (
user_id, display_name, password, 
sub_id, user_role, create_date,pwd_reset,
retry,locked,status)
values (
'admin','Administrator01','$2a$10$FlBgFxj0PgVkyjXFYCxdWOa6niNqkP6/7gRKCPIh6xtodw4j0xmqG',
'TALAPPAREL','ROLE_ADMIN',now(), 0,
0,0,'A');

<-- insert sys param-->
insert into sys_param(`param_name`,`param_value`) values('tal.dir.acrsoft', '/sdk/arcsoft/linux/libs/LINUX64');
insert into sys_param(`param_name`,`param_value`) values('tal.dir.base', '/opt/faceRecognition');
insert into sys_param(`param_name`,`param_value`) values('tal.dir.gallery.guest', '/gallery/guest.f');
insert into sys_param(`param_name`,`param_value`) values('tal.dir.gallery.staff', '/gallery/staff.f');
insert into sys_param(`param_name`,`param_value`) values('tal.dir.gallery.vip', '/gallery/vip.f');
insert into sys_param(`param_name`,`param_value`) values('tal.dir.image', '/images/');
insert into sys_param(`param_name`,`param_value`) values('tal.dir.license', '/license/ROC.lic');
insert into sys_param(`param_name`,`param_value`) values('tal.dir.license.arcface', '/license/LICENSE');
insert into sys_param(`param_name`,`param_value`) values('tal.dir.thumbnails', '/thumbnails/');
insert into sys_param(`param_name`,`param_value`) values('tal.dir.wiegand', '/opt/faceRecognition/wiegand/');
insert into sys_param(`param_name`,`param_value`) values('tal.face.liveness', 'ON');
insert into sys_param(`param_name`,`param_value`) values('tal.face.liveness.timecamlock', '1');
insert into sys_param(`param_name`,`param_value`) values('tal.face.min.similarity', '0.7f');
insert into sys_param(`param_name`,`param_value`) values('tal.face.min.size.abs', '400');
insert into sys_param(`param_name`,`param_value`) values('tal.face.min.size.rel', '0.01f');
insert into sys_param(`param_name`,`param_value`) values('tal.face.thumbnail.remove', 'ON');
insert into sys_param(`param_name`,`param_value`) values('tal.video.fps', '5');
insert into sys_param(`param_name`,`param_value`) values('tal.endpoint.reg', 'http://192.168.1.104:8080/talface-system/face/api/v1/reg');
insert into sys_param(`param_name`,`param_value`) values('tal.endpoint.dereg', 'http://192.168.1.104:8080/talface-system/face/api/v1/dereg');
insert into sys_param(`param_name`,`param_value`) values('tal.portal.login.retry.max', '5');
insert into sys_param(`param_name`,`param_value`) values('tal.portal.login.retry.reminder', '3');




