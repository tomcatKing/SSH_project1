--ʳ���--
create table `mt_food`(
	`food_id` int(11) not null auto_increment comment '��ʳ����',
	`food_type` int(11) not null comment '����id,��Ӧ��ʳ����:1-���,2-�ز�,3-���,4-С��,5-����,6-����',
	`food_name` varchar(100) not null comment '��ʳ����',
	`food_img`	varchar(500) not null comment '��ʳͼƬ',
	`food_detail`	varchar(300) default '����ζ�޷������������' comment '��ʳ����',
	`food_price`	decimal(20,2) unsigned default '20.0' comment '��ʳ���',
	`food_status` int(6) default '1' comment '��Ʒ״̬:1-����,2-�¼�,3-ɾ��',
	`create_time` datetime default null comment '����ʱ��',
	`update_time` datetime default null comment '����ʱ��',
	primary key (`food_id`)
)ENGINE=InnoDB	auto_increment=26 default charset=utf8