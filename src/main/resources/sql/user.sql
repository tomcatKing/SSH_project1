--�û���--
create table `mt_user`(
	`user_id` int(11) not null auto_increment comment '�û�����',
	`user_name` varchar(100) default null comment '�û�����',
	`user_eamil` varchar(100) default null comment '�û�����',
	`user_room` int(11) default null comment '�û���ǰ����',
	`create_time` datetime default null comment '����ʱ��',
	`update_time` datetime default null comment '����ʱ��',
	primary key (`user_id`)
)ENGINE=InnoDB	auto_increment=26 default charset=utf8