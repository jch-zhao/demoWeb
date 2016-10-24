--前面几步可以由系统自动完成，也可手工执行
--创建数据库
--create database wsddata_example character set 'utf8' collate 'utf8_general_ci';

--以root用户登录，创建新用户(命令行下两种方式都可以;代码执行用第二种)
--insert into mysql.User(Host,User,Password) values('localhost','wsddata',password('wsddata'));
--grant all privileges on wsddata_example.* to wsddata@localhost identified by 'wsddata';

--刷新系统权限表
--flush privileges;

--以wsddata用户登录
--切换数据库
--use wsddata_example;

--删除表
--DROP TABLE IF EXISTS `USER`;

--创建用户表
CREATE TABLE USER (uid int NOT NULL AUTO_INCREMENT,username varchar(50) NOT NULL,password varchar(50),isAdmin int DEFAULT 0,ipRange varchar(200) DEFAULT '*',PRIMARY KEY (uid))ENGINE=InnoDB DEFAULT CHARSET=utf8;

--创建用户
insert into user(username,password,isadmin)values('admin',password('admin'),1);
insert into user(username,password,isadmin,iprange)values('zhangsan',password('123456'),0,'202.106.1.1-202.106.127.255');

--其他初始化语句