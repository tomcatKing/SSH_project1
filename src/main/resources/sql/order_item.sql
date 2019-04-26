create table `mt_order_item`(
	`order_item_id` int(11) not null auto_increment comment '�����ֱ�id',
	`open_id` varchar(100) default null,
	`order_no` bigint(20) default null comment '�������',
	`food_id` int(11) default null comment '��ʳid',
	`food_name` varchar(100) default null comment '��Ʒ����',
	`food_img` varchar(200) default null comment '��ƷͼƬ����',
	`food_price` decimal(20,2) default null comment '��Ʒ����',
	`food_num` int(10) default null comment '��Ʒ����',
	`total_price` decimal(20,2) default null comment '��Ʒ�ܼ�,��λ��Ԫ,����2λС��',
	`create_time` datetime default null,
	`update_time` datetime default null,
	primary key(`order_item_id`),
	key `order_no_index` (`order_no`) using btree,
	key `order_no_user_id_index`(`open_id`,`order_no`) using btree
)engine=InnoDB auto_increment=113 default charset=utf8