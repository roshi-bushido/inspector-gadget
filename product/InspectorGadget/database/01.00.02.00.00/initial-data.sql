DELETE FROM T_JOB_INSTANCE_EVENT;
DELETE FROM T_JOB_INSTANCE;
INSERT INTO T_SYSTEM_CONFIGURATION VALUES (4, 'config.jobs.notification.creation.enabled', 'true');
INSERT INTO T_SYSTEM_CONFIGURATION VALUES (5, 'config.jobs.notification.creation.email', 'masuarez@agea.com.ar');