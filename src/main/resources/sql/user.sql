--用户表--
create table `mt_user`(
	`user_id` int(11) not null auto_increment comment '用户主键',
	`user_name` varchar(100) default null comment '用户名称',
	`user_eamil` varchar(100) default null comment '用户邮箱',
	`user_room` int(11) default null comment '用户当前房间',
	`create_time` datetime default null comment '创建时间',
	`update_time` datetime default null comment '更新时间',
	primary key (`user_id`)
)ENGINE=InnoDB	auto_increment=26 default charset=utf8