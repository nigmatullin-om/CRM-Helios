-- -----------------------------------------------------
-- Table `test_crm_helios.`.`task_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS test_crm_helios.task_type (
  id        SERIAL        NOT NULL,
  type_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
);

insert into test_crm_helios.task_type VALUES (1, 'Follow-up');

ALTER TABLE test_crm_helios.task ADD task_type_id INT NOT NULL DEFAULT 1;

ALTER TABLE test_crm_helios.task
ADD CONSTRAINT fk_task_type1
FOREIGN KEY (task_type_id)
REFERENCES test_crm_helios.task_type (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;
