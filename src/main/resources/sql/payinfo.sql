create table `mt_payinfo`(
	`pay_id` int(11) not null auto_increment comment '֧������',
	`user_id`	varchar(100) default null comment '�û�id',
	`order_no` bigint(20) default null comment '������',
	`pay_platform` int(10) default null comment '֧��ƽ̨:1-֧����',
	`platform_number` varchar(200) default null comment '֧����֧����ˮ��',
	`platform_status` varchar(20) default null comment '֧��״̬',
	`create_time` datetime default null comment '����ʱ��',
	`update_time` datetime default null comment '����ʱ��',
	primary key(`pay_id`)
)engine=innoDb auto_increment=53 default charset=utf8