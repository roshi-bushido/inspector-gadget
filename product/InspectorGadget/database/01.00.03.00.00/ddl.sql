DELETE FROM T_NOTIFICATION;

ALTER TABLE T_JOB ADD COLUMN `A_CREATION_DATE` datetime;
ALTER TABLE T_JOB ADD COLUMN `A_LAST_UPDATE_DATE` datetime;
ALTER TABLE T_NOTIFICATION ADD COLUMN `F_JOB_ID` bigint(20) NOT NULL;
ALTER TABLE T_NOTIFICATION MODIFY `F_JOB_INSTANCE_ID` bigint(20);
ALTER TABLE T_NOTIFICATION ADD CONSTRAINT `FK_T_NOTIFICATION_JOB` FOREIGN KEY (F_JOB_ID) REFERENCES T_JOB(P_ID);
ALTER TABLE T_NOTIFICATION ADD CONSTRAINT `FK_JOB_NOTIFICATION`   FOREIGN KEY (F_JOB_ID) REFERENCES T_JOB(P_ID);

CREATE TABLE `T_APPLICATION` (
  `P_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `A_NAME` varchar(100) NOT NULL,
  `A_CODE` varchar(100) NOT NULL,
  `A_CREATION_DATE` datetime DEFAULT NULL,
  `A_LAST_UPDATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`P_ID`)
) ENGINE=InnoDB;

ALTER TABLE T_APPLICATION ADD CONSTRAINT `UQ_CODE` UNIQUE (A_CODE);
INSERT INTO T_APPLICATION VALUES (1, 'DeAutos', 'deautos' , CURRENT_DATE(), CURRENT_DATE());
INSERT INTO T_APPLICATION VALUES (2, 'Empleos', 'empleos', CURRENT_DATE(), CURRENT_DATE());
INSERT INTO T_APPLICATION VALUES (3, 'Turismo', 'turismo', CURRENT_DATE(), CURRENT_DATE());
INSERT INTO T_APPLICATION VALUES (4, 'Inspector Gadget', 'inspector-gadget',CURRENT_DATE(), CURRENT_DATE());
INSERT INTO T_APPLICATION VALUES (5, 'Benefits Manager', 'benefits-manager',CURRENT_DATE(), CURRENT_DATE());
ALTER TABLE T_JOB ADD CONSTRAINT `FK_T_APPLICATION_JOB` FOREIGN KEY (F_APPLICATION_ID) REFERENCES T_APPLICATION(P_ID);

UPDATE T_JOB SET a_criticity=UPPER(a_criticity);
UPDATE T_JOB_INSTANCE SET a_status=UPPER(a_status);
UPDATE T_JOB_INSTANCE_EVENT SET a_status=UPPER(a_status);