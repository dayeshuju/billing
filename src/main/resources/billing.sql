SET FOREIGN_KEY_CHECKS=0;$$$

-- ----------------------------
-- Table structure for sysLogs
-- ----------------------------
CREATE TABLE `sysLogs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(50) DEFAULT NULL COMMENT '操作用户',
  `operation` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '操作名称',
  `method` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT 'IP地址',
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';$$$

-- ----------------------------
-- Records of sysLogs
-- ----------------------------

-- ----------------------------
-- Table structure for sysMenus
-- ----------------------------
CREATE TABLE `sysMenus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `zhname` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '资源名称',
  `esname` varchar(100) NOT NULL,
  `htmlid` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `url` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '资源URL',
  `type` int(11) DEFAULT NULL COMMENT '类型     1：菜单   2：按钮',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `parentId` int(11) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `permission` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '授权(如：user:create)',
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifiedTime` datetime DEFAULT NULL COMMENT '修改时间',
  `note` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';$$$

-- ----------------------------
-- Records of sysMenus
-- ----------------------------
INSERT INTO `sysMenus` VALUES ('1', '系统用户管理', 'Gestión de usuarios del sistema', 'sysUserId', 'sysUser', '1', '1', '18', 'sys:user', '2019-06-22 15:21:42', null, null);$$$
INSERT INTO `sysMenus` VALUES ('2', '权限管理', 'Gestion de permiso', 'sysPermission', 'sysPermission', '1', '2', '18', 'sys:permission', '2019-06-22 15:43:32', null, null);$$$
INSERT INTO `sysMenus` VALUES ('3', '用户类型管理', 'Gestión de tipos de usuario', 'tbYhlx', 'tbYhlx', '1', '3', '18', 'sys:tbyhlx', '2019-06-22 15:44:49', null, null);$$$
INSERT INTO `sysMenus` VALUES ('4', '个人设置', 'Ajustes personales', 'sysUserSelf', null, '1', '3', null, 'sys:userself', '2019-06-22 16:39:52', null, null);$$$
INSERT INTO `sysMenus` VALUES ('5', '收银台', 'Caja', 'tbSyt', 'tbSyt', '1', '1', '16', 'sys:tbsyt', '2019-06-27 20:51:11', null, null);$$$
INSERT INTO `sysMenus` VALUES ('6', '缴费记录管理', 'Gestión de registros de pago', 'tbJfjl', 'tbJfjl', '1', '1', '17', 'sys:tbjfjl', '2019-06-27 20:52:14', null, null);$$$
INSERT INTO `sysMenus` VALUES ('7', '用电用户管理', 'Gestión de usuarios consumidores', 'tbYdyh', 'tbYdyh', '1', '2', '17', 'sys:tbydyh', '2019-06-27 20:53:01', null, null);$$$
INSERT INTO `sysMenus` VALUES ('8', '抄表记录管理', 'Gestión de lectura de contadores', 'tbCbjl', 'tbCbjl', '1', '3', '17', 'sys:tbCbjl', '2019-06-27 20:53:37', null, null);$$$
INSERT INTO `sysMenus` VALUES ('9', '计量设备管理', 'Gestión de medidores', 'tbJlsb', 'tbJlsb', '1', '4', '17', 'sys:tbjlsb', '2019-06-27 20:54:29', null, null);$$$
INSERT INTO `sysMenus` VALUES ('10', '缴费功能', 'Funcion de pago', 'jfgn', null, '1', '1', null, 'sys:jfgn', '2019-06-27 23:05:19', null, null);$$$
INSERT INTO `sysMenus` VALUES ('11', '数据管理', 'Gestión de datos', 'sjgl', null, '1', '2', null, 'sys:sjgl', '2019-06-27 23:05:57', null, null);$$$
INSERT INTO `sysMenus` VALUES ('12', '系统管理', 'Gestion del sistema', 'xtgl', null, '1', '4', null, 'sys:xtgl', '2019-06-27 23:06:41', null, null);$$$
INSERT INTO `sysMenus` VALUES ('13', '收费统计管理', 'Gestión de estadísticas de carga', 'tbSftj', 'tbSftj', '1', '5', '17', 'sys:tbsftj', '2019-07-23 11:05:27', null, null);$$$
INSERT INTO `sysMenus` VALUES ('14', '修改密码', 'Cambiar contraseña', 'changePassword', 'changePassword', '1', '1', '4', 'sys:changePassword', '2019-07-23 13:32:00', null, null);$$$

-- ----------------------------
-- Table structure for sysRoleMenus
-- ----------------------------
CREATE TABLE `sysRoleMenus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL COMMENT '角色ID',
  `menuId` int(11) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单对应关系';$$$

-- ----------------------------
-- Records of sysRoleMenus
-- ----------------------------
INSERT INTO `sysRoleMenus` VALUES ('1', '1', '1');$$$
INSERT INTO `sysRoleMenus` VALUES ('2', '1', '2');$$$
INSERT INTO `sysRoleMenus` VALUES ('3', '1', '3');$$$
INSERT INTO `sysRoleMenus` VALUES ('4', '1', '4');$$$
INSERT INTO `sysRoleMenus` VALUES ('5', '1', '5');$$$
INSERT INTO `sysRoleMenus` VALUES ('6', '1', '6');$$$
INSERT INTO `sysRoleMenus` VALUES ('7', '1', '7');$$$
INSERT INTO `sysRoleMenus` VALUES ('8', '1', '8');$$$
INSERT INTO `sysRoleMenus` VALUES ('9', '1', '9');$$$
INSERT INTO `sysRoleMenus` VALUES ('10', '1', '10');$$$
INSERT INTO `sysRoleMenus` VALUES ('11', '1', '11');$$$
INSERT INTO `sysRoleMenus` VALUES ('12', '1', '12');$$$
INSERT INTO `sysRoleMenus` VALUES ('13', '1', '13');$$$
INSERT INTO `sysRoleMenus` VALUES ('14', '1', '14');$$$

-- ----------------------------
-- Table structure for sysRoles
-- ----------------------------
CREATE TABLE `sysRoles` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '角色名称',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifiedTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='角色';$$$

-- ----------------------------
-- Records of sysRoles
-- ----------------------------
INSERT INTO `sysRoles` VALUES ('1', 'superAdmin', '', '2019-06-22 16:27:05', '0000-00-00 00:00:00');$$$

-- ----------------------------
-- Table structure for sysUsers
-- ----------------------------
CREATE TABLE `sysUsers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '密码（默认123456）',
  `salt` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '盐  密码加密时前缀，使加密后的值不同',
  `name` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `roleId` int(10) NOT NULL,
  `email` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮箱',
  `officePhone` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '办工电话',
  `mobile` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号',
  `valid` tinyint(4) DEFAULT '1' COMMENT '状态  0：禁用   1：正常  默认值 ：1',
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifiedTime` datetime DEFAULT NULL COMMENT '修改时间',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`nickname`),
  UNIQUE KEY `nickname` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';$$$

-- ----------------------------
-- Records of sysUsers
-- ----------------------------
INSERT INTO `sysUsers` VALUES ('1', 'root', '9271e3e4d21fbff207dc8b800597e31f', 'b76d97a3-da55-4dd6-8fc3-6d08d7abe633', 'root', '1', null, null, null, '1', '2019-09-05 18:17:35', null, null);$$$

-- ----------------------------
-- Table structure for tbCbjl
-- ----------------------------
CREATE TABLE `tbCbjl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `meterId` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '表号',
  `regisTime` datetime NOT NULL COMMENT '抄表时间',
  `meterNum` double(20,5) NOT NULL COMMENT '电表示数',
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifiedTime` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表记录';$$$

-- ----------------------------
-- Records of tbCbjl
-- ----------------------------

-- ----------------------------
-- Table structure for tbJfjl
-- ----------------------------
CREATE TABLE `tbJfjl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `meterId` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '表号',
  `ydyhId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `periodElecNum` double(20,5) DEFAULT NULL COMMENT '本期电量',
  `amountDue` decimal(50,5) DEFAULT NULL COMMENT '应缴电费',
  `actualAmount` decimal(50,5) DEFAULT NULL COMMENT '实缴电费',
  `payStatus` tinyint(5) DEFAULT '0' COMMENT '缴费状态(0:未缴费,1:欠费,2:已缴费)',
  `receiptStatus` tinyint(5) DEFAULT '0' COMMENT '是否打印收据(1:打印,0:未打印)',
  `payTime` datetime DEFAULT NULL COMMENT '缴费时间',
  `regisTime` datetime DEFAULT NULL,
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifiedTime` datetime DEFAULT NULL COMMENT '操作时间',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `tate` decimal(50,5) NOT NULL COMMENT '电费费率*10000',
  `printTime` datetime DEFAULT NULL,
  `cashier` bigint(20) DEFAULT NULL COMMENT '收银员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='缴费记录';$$$

-- ----------------------------
-- Records of tbJfjl
-- ----------------------------

-- ----------------------------
-- Table structure for tbJlsb
-- ----------------------------
CREATE TABLE `tbJlsb` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `meterId` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '表号',
  `meterBoxId` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '表箱号',
  `ydyhId` bigint(20) DEFAULT NULL COMMENT '用电用户id',
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifiedTime` datetime DEFAULT NULL COMMENT '操作时间',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `meterId` (`meterId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计量设备';$$$

-- ----------------------------
-- Records of tbJlsb
-- ----------------------------

-- ----------------------------
-- Table structure for tbYdyh
-- ----------------------------
CREATE TABLE `tbYdyh` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用电用户id',
  `idCode` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '用户身份id',
  `name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '用户姓名',
  `address` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户地址',
  `phoneNum` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '电话号码',
  `userTypeId` bigint(20) NOT NULL COMMENT '用户类型',
  `valid` tinyint(3) DEFAULT '1' COMMENT '用户状态(0:禁用,1:启用)',
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifiedTime` datetime DEFAULT NULL COMMENT '操作时间',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idCode` (`idCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用电用户';$$$

-- ----------------------------
-- Records of tbYdyh
-- ----------------------------

-- ----------------------------
-- Table structure for tbYhlx
-- ----------------------------
CREATE TABLE `tbYhlx` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userType` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '用户类型',
  `tate` decimal(20,5) NOT NULL COMMENT '电费费率*10000',
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifiedTime` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userType` (`userType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户类型';$$$

-- ----------------------------
-- Records of tbYhlx
-- ----------------------------

-- ----------------------------
-- Procedure structure for insertJfjlFromCbjl
-- ----------------------------
CREATE DEFINER=`daye`@`%` PROCEDURE `insertJfjlFromCbjl`(IN `v_count` int,IN `v_insId` int,OUT `v_insCount` int)
BEGIN
	DECLARE v_meterId VARCHAR(50);  			-- 要插入缴费记录的表号
	DECLARE v_regisTime DATETIME;
	DECLARE v_ydyhId INTEGER;							-- 要插入缴费记录的用户id
	DECLARE v_bcbss DECIMAL(50,5);								-- 本次表示数
	DECLARE v_scbss DECIMAL(50,5);								-- 上次表示数
	DECLARE v_periodElecNum DECIMAL(50,5);				-- 要插入缴费记录的本期电量
	DECLARE v_tate DECIMAL(50,5);								-- 用电用户费率
	DECLARE v_amountDue DECIMAL(50,5);						-- 要插入缴费记录的应缴电费
	DECLARE i INT;  											-- 循环标识
	START TRANSACTION;
	set i=0;
	set v_insCount=0;
	WHILE i<v_count DO
		set v_scbss=null;
		select meterId into v_meterId from tbCbjl where id=(v_insId+i);
		select regisTime into v_regisTime from tbCbjl where id=(v_insId+i);
		select a.ydyhId into v_ydyhId from tbJlsb a,tbCbjl b
			where a.meterId=b.meterId and b.meterId=v_meterId and b.id=(v_insId+i);
		select meterNum into v_bcbss from tbCbjl where id=(v_insId+i);
		select meterNum into v_scbss from tbCbjl where
			id=(select max(id) from tbCbjl where id<(v_insId+i) and meterId=v_meterId);
		IF v_scbss is not null THEN
			SET v_periodElecNum=v_bcbss-v_scbss;
		ELSE
			set v_periodElecNum=v_bcbss;
		END IF;
		if v_periodElecNum <>0 THEN
			SELECT a.tate INTO v_tate from tbYhlx a,tbYdyh b where a.id=b.userTypeId and b.id=v_ydyhId;
			SET v_amountDue=v_tate*v_periodElecNum;
			INSERT INTO tbJfjl (meterId,ydyhId,periodElecNum,amountDue,regisTime,tate)
			VALUES (v_meterId,v_ydyhId,v_periodElecNum,v_amountDue,v_regisTime,v_tate);
			set v_insCount=v_insCount+1;
		END IF;
		SET i=i+1;
	end WHILE;
	COMMIT;
END;$$$
