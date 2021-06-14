create table `userInfo` (
	`id` varchar(16) PRIMARY KEY COMMENT '用户名，应为特殊字符、数字、字母的组合',
	`pswd` varchar(16) NOT NULL COMMENT '密码，有条件应MD5加密',
	`name` char(5) NOT NULL COMMENT '姓名',
	`IDNO` varchar(25) NOT NULL COMMENT '身份证号',
	`phone` char(13) NOT NULL COMMENT '手机号',
	`email` varchar(20) COMMENT '邮箱',
	`age` char(2) default null COMMENT '年龄',
	`interest` varchar(1024) default null COMMENT '空格分割的感兴趣的方向，元素值需要提前约定好，例如01表示开发岗位',
	`resume` varchar(1024) default null COMMENT '一个文件夹地址，里面存的是该用户的简历'
)CHARSET=utf8mb4; -- 用户

create table `enterprise` (
	`id` bigint(32) PRIMARY KEY AUTO_INCREMENT COMMENT '企业id',
	`name` varchar(20) NOT NULL COMMENT '企业名称',
	`code` varchar(10) NOT NULL COMMENT '用于HR注册的验证码',
	`introduction` varchar(1024) COMMENT '简介',
	`position` varchar(1024) NOT NULL COMMENT '一个数组，与`userInfo`中的`interset`取值应该相同',
	`address` varchar(20) COMMENT '详细地址'
)CHARSET=utf8mb4; -- 企业

create table `humanResource` (
	`id`  varchar(16) PRIMARY KEY COMMENT 'hr用户名，应为特殊字符、数字、字母的组合',
	`pswd` varchar(16) NOT NULL COMMENT '密码，有条件应MD5加密',
	`name` char(5) NOT NULL COMMENT '姓名',
	`enterpriseId` bigint(32) NOT NULL COMMENT '企业id',
	`department` varchar(10) COMMENT '部门名称',
	`phone` char(13) NOT NULL COMMENT '联系电话',
	`email` varchar(20) COMMENT '邮箱',
	constraint `enterpriseId_hrid`  foreign key (`enterpriseId`) references `enterprise`(`id`) 
)CHARSET=utf8mb4; -- hr用户

create table `positionInfo` (
	`id` bigint(32) PRIMARY KEY Auto_increment,
	`position` varchar(8) NOT NULL COMMENT '一个值，取值与`userInfo`中的`interset`相同',
	`enterpriseId` bigint(32)  COMMENT '发布公司',
	CONSTRAINT `enterpriseId_c` foreign key (`enterpriseId`) references `enterprise`(`id`),
	`title` varchar(20) NOT NULL COMMENT '招聘标题',
	`content` TEXT NOT NULL COMMENT '招聘正文',
	`salary` varchar(10) COMMENT '薪酬',
	`hrId` varchar(16) COMMENT '联系人',
	CONSTRAINT `hrId_c`  foreign key(`hrId`) references `humanResource`(`id`),
	`degree` char(1) NOT NULL COMMENT '0为不限 1为高中 2为专科 3为本科 4为硕士 5为博士',
	`views` int(10) unsigned default 0 COMMENT '浏览量'
)CHARSET=utf8mb4; -- 招聘信息

create table `resume` (
	`positionId` bigint(32),
  CONSTRAINT `positionId` foreign key (`positionId`) references `positionInfo`(`id`),
	`userId` varchar(16) COMMENT '用户id',
	constraint `userId_c` foreign key(`userId`) references `userInfo`(`id`) ,
	`resume` varchar(1024) COMMENT '一个文件夹地址，里面存的是该用户的简历',
	`isDealed` boolean COMMENT '处理状态，false为未处理',
	PRIMARY Key `id` (`positionId`,`userId`)
)CHARSET=utf8mb4; -- 简历

create table `info` (
	`id` bigint(32) PRIMARY Key AUTO_INCREMENT,
	`title` varchar(20) NOT NULL COMMENT '资讯标题',
	`content` TEXT NOT NULL COMMENT '资讯正文'
)CHARSET=utf8mb4; -- 资讯板块

create table `talk` (
	`userId` varchar(16) COMMENT '会话人1应是普通用户',
	constraint `talk_userId_c` foreign key (`userId`) references `userInfo`(`id`),
	`hrId` varchar(16) COMMENT '会话人2应是hr',
	constraint `taklk_hrId_c` foreign key (`hrId`) references `humanResource`(`id`),
	key `id` (`userId`,`hrId`)
) CHARSET=utf8mb4;

