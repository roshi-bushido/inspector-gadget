DROP TABLE IF EXISTS T_NOTIFICATION;
DROP TABLE IF EXISTS T_JOB_INSTANCE;
DROP TABLE IF EXISTS T_JOB;
DROP TABLE IF EXISTS T_USER_ROLES;
DROP TABLE IF EXISTS T_ROLE;
DROP TABLE IF EXISTS T_USER;
DROP TABLE IF EXISTS T_CONFIGURATION;

CREATE TABLE `T_CONFIGURATION` (
  `P_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `A_NAME` varchar(255) NOT NULL,
  `A_NOTIFICATION_EMAIL` varchar(255) NOT NULL,
  `A_IS_NOTIFICATION_ENABLED` bit(1) NOT NULL,
  PRIMARY KEY (`P_ID`)
) ENGINE=InnoDB;

CREATE TABLE `T_NOTIFICATION` (
  `P_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `F_JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `A_ERROR_MESSAGE` varchar(500) NOT NULL,
  `A_CREATE_DATE` datetime NOT NULL,
  `A_WAS_SENT` bit(1) NOT NULL,
  PRIMARY KEY (`P_ID`)
) ENGINE=InnoDB;

CREATE TABLE `T_JOB` (
  `P_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `F_APPLICATION_ID` bigint(20) NOT NULL,
  `F_CONFIGURATION_ID` bigint(20) NOT NULL,
  `A_DESCRIPTION` varchar(255) DEFAULT NULL,
  `A_IS_ENABLED` bit(1) NOT NULL,
  `A_EXECUTION_INTERVAL_IN_SECONDS` int(11) NOT NULL,
  `A_NAME` varchar(255) NOT NULL,
  `A_ERROR_DURATION_THRESHOLD_IN_SECONDS` int(11) NOT NULL,
  `A_WARN_DURATION_THRESHOLD_IN_SECONDS` int(11) NOT NULL,
  PRIMARY KEY (`P_ID`)
) ENGINE=InnoDB;

CREATE TABLE `T_JOB_INSTANCE` (
  `P_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `A_ENDED_AT_DATE` datetime DEFAULT NULL,
  `F_JOB_ID` bigint(20) NOT NULL,
  `A_STARTED_AT_DATE` datetime NOT NULL,
  `A_STATUS` varchar(7) NOT NULL,
  `A_DESCRIPTION` text,
  PRIMARY KEY (`P_ID`)
) ENGINE=InnoDB;

CREATE TABLE `T_ROLE` (
  `P_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `A_AUTHORITY` varchar(255) NOT NULL,
  PRIMARY KEY (`P_ID`)
) ENGINE=InnoDB;

CREATE TABLE `T_USER` (
  `P_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `A_ACCOUNT_EXPIRED` bit(1) NOT NULL,
  `A_ACCOUNT_LOCKED` bit(1) NOT NULL,
  `A_CREATION_DATE` datetime NOT NULL,
  `A_IS_ENABLED` bit(1) NOT NULL,
  `A_FIRSTNAME` varchar(255) NOT NULL,
  `A_LASTNAME` varchar(255) NOT NULL,
  `A_LAST_MODIFICATION_DATE` datetime NOT NULL,
  `A_PASSWORD` varchar(255) NOT NULL,
  `A_PASSWORD_EXPIRED` bit(1) NOT NULL,
  `A_USERNAME` varchar(255) NOT NULL,
  PRIMARY KEY (`P_ID`)
) ENGINE=InnoDB;

CREATE TABLE `T_USER_ROLES` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`)
) ENGINE=InnoDB;

ALTER TABLE T_NOTIFICATION ADD CONSTRAINT `FK_JOB_INSTANCE_NOTIFICATION` FOREIGN KEY (F_JOB_INSTANCE_ID) REFERENCES T_JOB_INSTANCE(P_ID);
ALTER TABLE T_JOB          ADD CONSTRAINT `FK_JOB_CONFIGURATION` FOREIGN KEY (F_CONFIGURATION_ID) REFERENCES T_CONFIGURATION(P_ID);
ALTER TABLE T_JOB_INSTANCE ADD CONSTRAINT `FK_JOB_INSTANCE_JOB` FOREIGN KEY (F_JOB_ID) REFERENCES T_JOB(P_ID);
ALTER TABLE T_USER_ROLES   ADD CONSTRAINT `FK_USER_ROLES_USER` FOREIGN KEY (user_id) REFERENCES T_USER(P_ID);
ALTER TABLE T_USER_ROLES   ADD CONSTRAINT `FK_JOB_INSTANCE_ROLE` FOREIGN KEY (role_id) REFERENCES T_ROLE(P_ID);