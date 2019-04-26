create table `mt_order`(
	`order_id` int(11) not null auto_increment comment '����id',
	`order_no` bigint(20) default null comment '������',
	`open_id` varchar(100) default null comment '�û�id',
	`room_id` int(11) default null comment '����id',
	`payment` decimal(20,2) default null comment 'ʵ��֧�����,��λԪ',
	`payment_type` int(4) default null comment '֧������,1-֧����֧��',
	`status` int(10) default null comment '����״̬:0-��ȡ��,10-δ����,20-��֧��,30-���׳ɹ�',
	`order_desc` varchar(100) default null comment '�û�����',
	`payment_time` datetime default null comment '֧��ʱ��',
	`send_time`	datetime default null comment '����ʱ��',
	`end_time` datetime default null comment '�������ʱ��',
	`close_time` datetime default null comment '���׹ر�ʱ��',
	`create_time` datetime default null comment '����ʱ��',
	`update_time` datetime default null comment '����ʱ��',
	primary key (`order_id`),
	unique key `order_no_index` (`order_no`) using btree
)engine=InnoDB auto_increment=103 default charset=utf8