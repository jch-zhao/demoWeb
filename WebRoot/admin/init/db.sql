--以root用户登录，创建新用户
--insert into mysql.User(Host,User,Password) values('localhost','courseware',password('courseware'));

--刷新系统权限表
--flush privileges;

--创建数据库
--create database courseware character set 'utf8' collate 'utf8_general_ci';

--为用户授权
--grant all privileges on courseware.* to courseware@localhost identified by 'courseware';
--show databases;

--刷新系统权限表
--flush privileges;

--以courseware用户登录

--切换数据库
--use coureseware;

--删除表
--DROP TABLE IF EXISTS `USER`;

--创建用户表
CREATE TABLE USER (uid int NOT NULL AUTO_INCREMENT,username varchar(50) NOT NULL,password varchar(50),isAdmin int DEFAULT 0,PRIMARY KEY (uid))ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE photo(photoID int NOT NULL AUTO_INCREMENT,time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,title varchar(100),filepath varchar(512),PRIMARY KEY (photoID))ENGINE=InnoDB DEFAULT CHARSET=utf8;

--创建管理员用户
insert into user(username,password,isadmin)values('zhang','zhang',1);

--数据初始化
insert into photo(title,filepath)values('demo1','images/22886.jpg');
insert into photo(title,filepath)values('demo2','images/23014.jpg');
insert into photo(title,filepath)values('demo3','images/39736.jpg');
insert into photo(title,filepath)values('demo4','images/76977.jpg');
insert into photo(title,filepath)values('demo5','images/22886.jpg');
insert into photo(title,filepath)values('demo6','images/23014.jpg');
insert into photo(title,filepath)values('demo7','images/39736.jpg');
insert into photo(title,filepath)values('demo8','images/76977.jpg');