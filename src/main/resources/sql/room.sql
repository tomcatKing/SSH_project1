create table `mt_room`(
	`room_id` int(11) not null auto_increment,
	`room_name` varchar(20) default null comment '房间名称',
	`room_address` varchar(200) default null comment '房间详细地址',
	`room_type` int(11) default null comment '房间类型,0=普通房间,1=vip房间',
	`room_price` decimal(20,2) default null comment '房间租金',
	`room_status` int(11) default null comment '房间类型,0=可使用,1=已使用,2=无法使用',
	`create_time` datetime default null,
	`update_time` datetime default null,
	primary key(`room_id`)
)engine=innodb auto_increment=32 default charset=utf8