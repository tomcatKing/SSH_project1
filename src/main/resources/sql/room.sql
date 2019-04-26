create table `mt_room`(
	`room_id` int(11) not null auto_increment,
	`room_name` varchar(20) default null comment '��������',
	`room_address` varchar(200) default null comment '������ϸ��ַ',
	`room_type` int(11) default null comment '��������,0=��ͨ����,1=vip����',
	`room_price` decimal(20,2) default null comment '�������',
	`room_status` int(11) default null comment '��������,0=��ʹ��,1=��ʹ��,2=�޷�ʹ��',
	`create_time` datetime default null,
	`update_time` datetime default null,
	primary key(`room_id`)
)engine=innodb auto_increment=32 default charset=utf8