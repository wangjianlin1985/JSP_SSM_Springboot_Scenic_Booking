/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : tour_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-04-19 17:51:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_dzline`
-- ----------------------------
DROP TABLE IF EXISTS `t_dzline`;
CREATE TABLE `t_dzline` (
  `lineId` int(11) NOT NULL auto_increment COMMENT '线路id',
  `lineName` varchar(60) NOT NULL COMMENT '线路名称',
  `linePhoto` varchar(60) NOT NULL COMMENT '线路图片',
  `startPlace` varchar(20) NOT NULL COMMENT '出发地',
  `zhuti` varchar(80) NOT NULL COMMENT '产品主题',
  `linePrice` float NOT NULL COMMENT '线路价格',
  `lineDesc` varchar(8000) NOT NULL COMMENT '线路描述',
  `tuijianFlag` varchar(20) NOT NULL COMMENT '是否推荐',
  `hitNum` int(11) NOT NULL COMMENT '点击率',
  `addTime` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`lineId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dzline
-- ----------------------------
INSERT INTO `t_dzline` VALUES ('1', '【踏春赏樱】韩国江原道5日定制游', 'upload/1598d309-0421-489f-aee6-5e26c6012f34.jpg', '北京', '情侣蜜月 亲密自然', '6980', '<ul class=\" list-paddingleft-2\"><li><p>酒店：</p></li><li><p>精选当地特色豪华酒店，带给您舒适旅程。</p></li><li><p>车导：</p></li><li><p>专业的司机兼导游，带您深度体验韩国。</p></li><li><p>航班：</p></li><li><p>国航直飞航班，免去您转机的烦恼。</p></li><li><p>亮点：</p></li><li><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; line-height: 23px;\">【樱花庆典】三月里，可以看到镜浦台的樱花庆典漫步在4.3公里长的樱花丛中，旁边是湛蓝的韩国东海，沐浴在温馨浪漫的庆典氛围里；&nbsp;<br/>【文礼古都】江陵是江原道东海岸最大的城市，保存有江原道丰富的文化遗产和传统文化；&nbsp;<br/>【镜浦台】镜浦台包括镜浦湖及东海海岸一带，在这里可欣赏到日出和海上明月等景观。海边绵延6公里的白沙滩，是天然的海水浴场，夏季的避暑胜地；&nbsp;<br/>【韩剧取景地】《宫》、《公主的男人》、《漏沙》等韩剧的在江陵取景，最近大火的《孤单又灿烂的神-鬼怪》也有在江原道取景；</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; line-height: 23px;\"><br/></p></li></ul><p>导游信息</p><p>导游身份：当地土著<br/>导游经验：3年以上<br/>导游类型：司机兼导游（双语+导游证）<br/>擅长领域：韩国<br/>语言能力：中文</p><p><br/></p>', '是', '20', '2018-04-08 17:57:16');
INSERT INTO `t_dzline` VALUES ('2', '【古寺樱雨】东京+大阪+京都8天定制游', 'upload/6eb226ee-3dfd-4007-bfc4-1ee6d9c1327c.jpg', '北京', '情侣蜜月 探秘文化', '18888', '<ul class=\" list-paddingleft-2\"><li><p>酒店：</p></li><li><p>全程星级精品酒店</p></li><li><p>用车：</p></li><li><p>专业司机，安全保障；</p></li><li><p>亮点：</p></li><li><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; line-height: 23px;\">【樱花百选】特别前往“樱花百选”名胜地-新宿御苑、千鸟渊、清水寺，换360个姿势赏樱花；&nbsp;<br/>【灿烂童话】走进童心满满的迪士尼，重返十七岁的快乐与无邪；&nbsp;<br/>【动感东京】银座、新宿、六本木，游走东京最繁华时尚的街头；&nbsp;<br/>【千年古都】在充满历史感的古刹中，感受日式茶道的悠远、宁静，体验身着和服的优雅意韵；&nbsp;<br/>【都市美宿】国际连锁四、五星，优享极致服务，城市美景，尽收眼底；&nbsp;<br/>【饕餮盛宴】打开舌尖的幸福开关，地道日式料理、关西小吃、米其林星级料理……一网打尽。</p></li></ul><p><span style=\"color: rgb(102, 102, 102); font-family: &quot;MicroSoft YaHei&quot;, arail, sans-sarif; font-size: 14px; background-color: rgb(255, 255, 255);\">行程：</span></p><p>【新宿御苑】东京樱花品种最多之处，在这里才能看到最多的其他种类樱花。</p><p>【东京塔】在东京铁塔第一次眺望，红白相间的塔身在樱花的映衬下更加的可爱动人，登上铁塔，讲东京尽收眼底。</p><p>【千鸟渊绿道】皇居的护城河边有一条约700米长的游漫步道，沿道种植约260株樱花树，晚上还会进行夜晚灯光照明。如果不介意排队，可以乘坐护城河上的手划游船，感受“樱吹雪”的美丽意境。</p><p>晚餐推荐：【银座寿司荣】寿司职人将一生的热情都倾注在指尖的握捏中，带来绽放味蕾的美食。</p><p><br/></p>', '是', '16', '2018-04-09 09:31:48');

-- ----------------------------
-- Table structure for `t_leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword` (
  `leaveWordId` int(11) NOT NULL auto_increment COMMENT '留言id',
  `leaveTitle` varchar(80) NOT NULL COMMENT '留言标题',
  `leaveContent` varchar(2000) NOT NULL COMMENT '留言内容',
  `userObj` varchar(30) NOT NULL COMMENT '留言人',
  `leaveTime` varchar(20) default NULL COMMENT '留言时间',
  `replyContent` varchar(1000) default NULL COMMENT '管理回复',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`leaveWordId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES ('1', '有到日本去看樱花的吗？', '我想抱团去日本看樱花', 'user1', '2018-04-07 17:51:15', '可以的哈', '2018-04-08 17:51:23');
INSERT INTO `t_leaveword` VALUES ('2', 'ddd', 'bbbb', 'user1', '2018-04-19 16:21:06', '--', '--');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL auto_increment COMMENT '公告id',
  `title` varchar(80) NOT NULL COMMENT '标题',
  `content` varchar(5000) NOT NULL COMMENT '公告内容',
  `publishDate` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '旅游景点网站成立了', '<p>这里你可以看到各个国家的旅游风景，也可以定制去玩哈！</p>', '2018-04-08 17:51:34');

-- ----------------------------
-- Table structure for `t_tour`
-- ----------------------------
DROP TABLE IF EXISTS `t_tour`;
CREATE TABLE `t_tour` (
  `tourId` int(11) NOT NULL auto_increment COMMENT '旅游id',
  `tourName` varchar(60) NOT NULL COMMENT '旅游景点名称',
  `tourPhoto` varchar(60) NOT NULL COMMENT '旅游图片',
  `startPlace` varchar(20) NOT NULL COMMENT '出发地',
  `endPlace` varchar(20) NOT NULL COMMENT '目的地',
  `tourPrice` float NOT NULL COMMENT '旅游价格',
  `tourDesc` varchar(8000) NOT NULL COMMENT '旅游景点介绍',
  `tuijianFlag` varchar(20) NOT NULL COMMENT '是否推荐',
  `hitNum` int(11) NOT NULL COMMENT '浏览次数',
  `addTime` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`tourId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_tour
-- ----------------------------
INSERT INTO `t_tour` VALUES ('1', '南京直飞北海道札幌5-6天', 'upload/49dc6e72-e34c-4b89-8709-a6213ca5088a.jpg', '南京', '北海道', '1799', '<p>费用说明</p><h3 style=\"margin: 0px 0px 10px; padding: 0px; font-size: 14px; border-bottom: none; color: rgb(51, 51, 51);\">费用包含</h3><p>1. 机票：南京直飞札幌往返含税机票<br/>2.&nbsp;行李额：2件行李托运，每件23kg</p><h3 style=\"margin: 0px 0px 10px; padding: 0px; font-size: 14px; border-bottom: none; color: rgb(51, 51, 51);\">费用不包含</h3><ol class=\" list-paddingleft-2\" style=\"list-style-type: decimal;\"><li><p>酒店住宿费用；<br/>2. 单次个人旅游签证；<br/>3. 接送机：当地机场至酒店往返费用；<br/>4. 旅游人身意外保险。建议购买旅游人身意外保险；<br/>5. 因交通延阻、战争、政变、罢工、大风、大雾、飞机故障、航班取消或更改时间等不可抗力原因所引致的额外费用；<br/>6. 一切个人消费以及“费用包含”中未提及的任何费用。</p></li></ol><p><br/></p><p><span class=\"part_xz\" style=\"display: inline-block; float: left; margin-right: 10px; margin-top: 3px; background: url(&quot;/images-2.1/travel/detailicon.png&quot;) -96px -245px no-repeat; width: 50px; height: 40px;\"></span>预订须知</p><h3 style=\"margin: 0px 0px 10px; padding: 0px; font-size: 14px; border-bottom: none; color: rgb(51, 51, 51);\">预订条款</h3><p>2018年5月航班换季<br/>HO1367 南京-札幌 1030-1600<br/>HO1368 札幌-南京 1700-2100</p><h3 class=\"mt20\" style=\"margin: 20px 0px 10px; padding: 0px; font-size: 14px; border-bottom: none; color: rgb(51, 51, 51);\">退改政策</h3><p>特价产品，用户支付成功后不得做任何变更，也不予退款，敬请您阅知并认清相关标签</p><p><br/></p>', '是', '12', '2018-04-08 17:49:20');
INSERT INTO `t_tour` VALUES ('2', '冲绳4/5天往返单机票', 'upload/b488b6ab-2e08-4f6c-b047-d64389042a3c.jpg', '杭州', '冲绳', '1850', '<h3 style=\"margin: 0px 0px 10px; padding: 0px; font-size: 14px; border-bottom: none; color: rgb(51, 51, 51);\">费用包含</h3><p>杭州-冲绳往返机票（含税）</p><h3 style=\"margin: 0px 0px 10px; padding: 0px; font-size: 14px; border-bottom: none; color: rgb(51, 51, 51);\">费用不包含</h3><p>1、护照、签证费用<br/>2、当地住宿<br/>3、以上“费用包含”中未注明的各项费用&nbsp;</p><p>4、其它个人消费</p><p><span class=\"part_xz\" style=\"display: inline-block; float: left; margin-right: 10px; margin-top: 3px; background: url(&quot;/images-2.1/travel/detailicon.png&quot;) -96px -245px no-repeat; width: 50px; height: 40px;\"></span>预订须知</p><h3 style=\"margin: 0px 0px 10px; padding: 0px; font-size: 14px; border-bottom: none; color: rgb(51, 51, 51);\">预订条款</h3><p>1、签证需自备<br/>2、一人即可出票，保证成行<br/>3、直飞航班，标明航班时刻均为当地时间，如有变动以航空公司公布时刻为准<br/>4、不得更改、签转、退票<br/>5、我社保留因航空公司机位调整、燃油税上涨及不可抗力等因素或调整价格及出发日期的权利</p><h3 class=\"mt20\" style=\"margin: 20px 0px 10px; padding: 0px; font-size: 14px; border-bottom: none; color: rgb(51, 51, 51);\">退改政策</h3><p>特价产品，用户支付成功后不得做任何变更，也不予退款，敬请您阅知并认清相关标签</p><p><br/></p>', '是', '26', '2018-04-09 09:26:09');

-- ----------------------------
-- Table structure for `t_tourorder`
-- ----------------------------
DROP TABLE IF EXISTS `t_tourorder`;
CREATE TABLE `t_tourorder` (
  `orderId` int(11) NOT NULL auto_increment COMMENT '订单id',
  `tourObj` int(11) NOT NULL COMMENT '旅游景点',
  `startDate` varchar(20) default NULL COMMENT '出发日期',
  `totalPersonNum` int(11) NOT NULL COMMENT '出行人数',
  `totalPrice` float NOT NULL COMMENT '总价格',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `orderMemo` varchar(500) default NULL COMMENT '订单备注',
  `userObj` varchar(30) NOT NULL COMMENT '订单用户',
  `orderTime` varchar(20) default NULL COMMENT '下单时间',
  `shzt` varchar(20) NOT NULL COMMENT '审核状态',
  `shhf` varchar(500) NOT NULL COMMENT '审核回复',
  PRIMARY KEY  (`orderId`),
  KEY `tourObj` (`tourObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_tourorder_ibfk_1` FOREIGN KEY (`tourObj`) REFERENCES `t_tour` (`tourId`),
  CONSTRAINT `t_tourorder_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_tourorder
-- ----------------------------
INSERT INTO `t_tourorder` VALUES ('1', '1', '2018-04-08', '2', '3598', '13908309834', '我要去玩', 'user1', '2018-04-08 17:50:07', '待审核', '--');
INSERT INTO `t_tourorder` VALUES ('2', '2', '2018-04-30', '2', '3700', '13980983424', '这我留2个位置，我和我女朋友来！', 'user1', '2018-04-19 17:37:46', '已审核', '可以来一起去玩');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user1', '123', '李明芬', '女', '2018-04-03', 'upload/a3655043-99f3-4668-bcc1-b792a416a7df.jpg', '13980829052', 'mingfen@163.com', '四川成都红星路13号', '2018-04-08 17:49:04');
INSERT INTO `t_userinfo` VALUES ('user2', '123', '王小蓉', '女', '2018-04-12', 'upload/cb2b4512-46d2-4da9-a42f-367eb70b7602.jpg', '13508300834', 'xiaorong@163.com', '四川达州大足县祥光镇', '2018-04-19 17:42:49');
