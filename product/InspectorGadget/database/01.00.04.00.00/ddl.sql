ALTER TABLE T_JOB ADD COLUMN `A_EXECUTION_REG_EXP` VARCHAR(100);
ALTER TABLE T_JOB ADD COLUMN `A_USES_REG_EXP` BIT(1) NOT NULL DEFAULT 0;
ALTER TABLE T_JOB ADD COLUMN `A_ESCALATION_STEPS` TEXT;

