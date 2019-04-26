create table `mt_cart`(
	`cart_id` int(11) not null auto_increment,
	`open_id` int(11) not null,
	`food_id` int(11) default null comment'商品id',
	`food_num` int(11) default null comment '商品数量',
	`checked` int(11) default 0 comment '是否选择:0=未勾选,1=已勾选',
	`create_time` datetime default null comment '创建时间',
	`update_time` datetime default null comment '更新时间',
	primary key(`cart_id`),
	key `open_id_index` (`open_id`) using btree
)engine=InnoDB auto_increment=121 default charset=utf8