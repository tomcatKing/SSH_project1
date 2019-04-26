create table `mt_cart`(
	`cart_id` int(11) not null auto_increment,
	`open_id` int(11) not null,
	`food_id` int(11) default null comment'��Ʒid',
	`food_num` int(11) default null comment '��Ʒ����',
	`checked` int(11) default 0 comment '�Ƿ�ѡ��:0=δ��ѡ,1=�ѹ�ѡ',
	`create_time` datetime default null comment '����ʱ��',
	`update_time` datetime default null comment '����ʱ��',
	primary key(`cart_id`),
	key `open_id_index` (`open_id`) using btree
)engine=InnoDB auto_increment=121 default charset=utf8