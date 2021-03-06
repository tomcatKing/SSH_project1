--食物表--
create table `mt_food`(
	`food_id` int(11) not null auto_increment comment '美食主键',
	`food_type` int(11) not null comment '分类id,对应美食分类:1-荤菜,2-素菜,3-甜点,4-小吃,5-饮料,6-高汤',
	`food_name` varchar(100) not null comment '美食名称',
	`food_img`	varchar(500) not null comment '美食图片',
	`food_detail`	varchar(300) default '该美味无法用言语来表达' comment '美食描述',
	`food_price`	decimal(20,2) unsigned default '20.0' comment '美食标价',
	`food_status` int(6) default '1' comment '商品状态:1-在售,2-下架,3-删除',
	`create_time` datetime default null comment '创建时间',
	`update_time` datetime default null comment '更新时间',
	primary key (`food_id`)
)ENGINE=InnoDB	auto_increment=26 default charset=utf8