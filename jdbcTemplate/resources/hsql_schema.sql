-- -----------------------------------------------------
-- Schema crm_helios
-- -----------------------------------------------------
--CREATE SCHEMA crm_helios;

-- -----------------------------------------------------
-- Table `crm_helios`.`role`
-- -----------------------------------------------------
CREATE TABLE role (
  id        INT         NOT NULL,
  role_name VARCHAR(45) NULL,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table `crm_helios`.`task_type`
-- -----------------------------------------------------
CREATE TABLE task_type (
  id        INT        NOT NULL,
  type_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
);
-- -----------------------------------------------------
-- Table `crm_helios`.`stage`
-- -----------------------------------------------------

CREATE TABLE stage (
  id   INT          NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table `crm_helios`.`currencies`
-- -----------------------------------------------------
CREATE TABLE currencies (
  id              INT         NOT NULL,
  name            VARCHAR(45) NOT NULL,
  active_currency BOOLEAN,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table `crm_helios`.`phone_type`
-- -----------------------------------------------------
CREATE TABLE phone_type (
  id        INT         NOT NULL,
  type_name VARCHAR(45) NULL,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table `crm_helios`.`lang`
-- -----------------------------------------------------

CREATE TABLE lang (
  id   INT         NOT NULL,
  lang VARCHAR(45) NULL,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table `crm_helios`.`tag`
-- -----------------------------------------------------
CREATE TABLE tag (
  id          INT       NOT NULL CHECK (id > 0),
  name        VARCHAR(255) NOT NULL,
  created_by  INT          NOT NULL,
  date_create TIMESTAMP    NOT NULL,
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX tag_name_UNIQUE ON tag (name);
CREATE INDEX fk_tag_person1_idx ON tag (created_by);

-- -----------------------------------------------------
-- Table `crm_helios`.`deal_contact`
-- -----------------------------------------------------
CREATE TABLE deal_contact (
  deal_id    INT NOT NULL,
  contact_id INT NOT NULL,
  PRIMARY KEY (deal_id, contact_id)
);

CREATE INDEX fk_deals_has_contact_contact1_idx ON deal_contact (contact_id);
CREATE INDEX fk_deals_has_contact_deals1_idx ON deal_contact (deal_id);

-- -----------------------------------------------------
-- Table `crm_helios`.`tag_deal`
-- -----------------------------------------------------
CREATE TABLE tag_deal (
  tag_id  INT NOT NULL,
  deal_id INT NOT NULL,
  PRIMARY KEY (tag_id, deal_id),
  CONSTRAINT fk_tags_has_deal_tags1
  FOREIGN KEY (tag_id)
  REFERENCES tag (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_tags_has_deal_deal1_idx ON tag_deal (deal_id);
CREATE INDEX fk_tags_has_deal_tags1_idx ON tag_deal (tag_id);

-- -----------------------------------------------------
-- Table `crm_helios`.`company`
-- -----------------------------------------------------
CREATE TABLE company (
  id             INT       NOT NULL,
  name           VARCHAR(255) NOT NULL,
  responsible_id INT          NULL,
  web            VARCHAR(255) NULL,
  email          VARCHAR(255) NOT NULL,
  adress         VARCHAR(255) NULL,
  phone          VARCHAR(45)  NOT NULL,
  phone_type_id  INT          NOT NULL,
  created_by     INT          NOT NULL,
  date_create    TIMESTAMP    NOT NULL,
  deleted        BOOLEAN      DEFAULT FALSE NOT NULL ,
  date_modify    TIMESTAMP    NULL,
  user_modify_id INT          NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_company_phone_types1
  FOREIGN KEY (phone_type_id)
  REFERENCES phone_type (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);




CREATE INDEX fk_company_persons1_idx ON company (responsible_id);
CREATE INDEX fk_company_phone_types1_idx ON company (phone_type_id);
CREATE INDEX fk_company_person1_idx ON company (created_by);

-- -----------------------------------------------------
-- Table `crm_helios`.`tag_contact_company`
-- -----------------------------------------------------
CREATE TABLE tag_contact_company (
  tag_id     INT NOT NULL,
  contact_id INT NOT NULL,
  company_id INT NOT NULL,
  PRIMARY KEY (tag_id, contact_id, company_id),
  CONSTRAINT fk_tags_has_contact_tags1
  FOREIGN KEY (tag_id)
  REFERENCES tag (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_tag_contact_company1
  FOREIGN KEY (company_id)
  REFERENCES company (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_tags_has_contact_contact1_idx ON tag_contact_company (contact_id);
CREATE INDEX fk_tags_has_contact_tags1_idx ON tag_contact_company (tag_id);
CREATE INDEX fk_tag_contact_company1_idx ON tag_contact_company (company_id);

-- -----------------------------------------------------
-- Table `crm_helios`.`file`
-- -----------------------------------------------------

CREATE TABLE file (
  id          INT    NOT NULL,
  path        VARCHAR(100)  NULL,
  blob_data   BLOB     NULL,
  contact_id  INT       NULL,
  deal_id     INT       NULL,
  notes_id    INT       NULL,
  company_id  INT       NULL,
  created_by  INT       NOT NULL,
  date_create TIMESTAMP NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_files_company1
  FOREIGN KEY (company_id)
  REFERENCES company (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_files_contact1_idx ON file (contact_id);
CREATE INDEX fk_files_deal1_idx ON file (deal_id);
CREATE INDEX fk_files_notes1_idx ON file (notes_id);
CREATE INDEX fk_files_persons1_idx ON file (created_by);
CREATE INDEX fk_files_company1_idx ON file (company_id);

-- -----------------------------------------------------
-- Table `crm_helios`.`person`
-- -----------------------------------------------------

CREATE TABLE person (
  id            INT       NOT NULL,
  name          VARCHAR(255) NOT NULL,
  password      VARCHAR(255) NOT NULL,
  photo_file_id INT          NULL,
  email         VARCHAR(255) NOT NULL,
  phone_mobile  VARCHAR(255) NULL,
  phone_work    VARCHAR(255) NULL,
  lang_id       INT          NOT NULL,
  role_id       INT          NOT NULL,
  note          VARCHAR(1000)         NULL,
  date_create   TIMESTAMP    NOT NULL,
  deleted       BOOLEAN      DEFAULT FALSE NOT NULL ,
  PRIMARY KEY (id),
  CONSTRAINT fk_persons_role1
  FOREIGN KEY (role_id)
  REFERENCES role (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_persons_files1
  FOREIGN KEY (photo_file_id)
  REFERENCES file (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_person_lang1
  FOREIGN KEY (lang_id)
  REFERENCES lang (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_persons_role1_idx ON person (role_id);
CREATE INDEX fk_persons_files1_idx ON person (photo_file_id);
CREATE INDEX fk_person_lang1_idx ON person (lang_id);

-- -----------------------------------------------------
-- Table `crm_helios`.`deal`
-- -----------------------------------------------------
CREATE TABLE deal (
  id             INT         NOT NULL,
  name           VARCHAR(255)   NOT NULL,
  budget         DECIMAL(20, 2) NULL,
  responsible_id INT            NOT NULL,
  stage_id       INT            NOT NULL,
  company_id     INT            NOT NULL,
  created_by     INT            NOT NULL,
  date_create    TIMESTAMP      NOT NULL,
  deleted        BOOLEAN       DEFAULT FALSE NOT NULL ,
  PRIMARY KEY (id),
  CONSTRAINT fk_deal_persons1
  FOREIGN KEY (responsible_id)
  REFERENCES person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_deal_state1
  FOREIGN KEY (stage_id)
  REFERENCES stage (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_deals_company1
  FOREIGN KEY (company_id)
  REFERENCES company (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_deal_person1
  FOREIGN KEY (created_by)
  REFERENCES person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_deal_persons1 ON deal (responsible_id);
CREATE INDEX fk_deal_state1 ON deal (stage_id);
CREATE UNIQUE INDEX deal_id_UNIQUE ON deal (id);
CREATE INDEX fk_deals_company1_idx ON deal (company_id);
CREATE INDEX fk_deal_person1_idx ON deal (created_by);

-- -----------------------------------------------------
-- Table `crm_helios`.`contact`
-- -----------------------------------------------------

CREATE TABLE contact (
  id             INT       NOT NULL CHECK (id > 0),
  name           VARCHAR(255) NOT NULL,
  phone          VARCHAR(255) NULL,
  email          VARCHAR(255) NULL,
  skype          VARCHAR(255) NULL,
  position       VARCHAR(255) NOT NULL,
  responsible_id INT          NULL,
  phone_type_id  INT          NOT NULL,
  company_id     INT          NULL,
  created_by     INT          NOT NULL,
  date_create    TIMESTAMP    NULL,
  deleted        BOOLEAN      DEFAULT FALSE NOT NULL,
  date_modify    TIMESTAMP    NULL,
  user_modify_id INT          NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_contact_persons1
  FOREIGN KEY (responsible_id)
  REFERENCES person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_contact_phone_types1
  FOREIGN KEY (phone_type_id)
  REFERENCES phone_type (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_contact_company1
  FOREIGN KEY (company_id)
  REFERENCES company (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_contact_person1
  FOREIGN KEY (created_by)
  REFERENCES person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_contact_persons1_idx ON contact (responsible_id);
CREATE INDEX fk_contact_phone_types1_idx ON contact (phone_type_id);
CREATE INDEX fk_contact_company1_idx ON contact (company_id);
CREATE INDEX fk_contact_person1_idx ON contact (created_by);

-- -----------------------------------------------------
-- Table `crm_helios`.`task`
-- -----------------------------------------------------
CREATE TABLE  task (
  id             INT       NOT NULL,
  name           VARCHAR(255) NOT NULL,
  finish_date    TIMESTAMP    NOT NULL,
  responsible_id INT          NOT NULL,
  description    VARCHAR(1000) NOT NULL,
  contact_id     INT          NULL,
  period         INT          NULL,
  deal_id        INT          NULL,
  company_id     INT          NULL,
  created_by     INT          NOT NULL,
  date_create    TIMESTAMP    NOT NULL,
  done           BOOLEAN,
  deleted        BOOLEAN      DEFAULT FALSE NOT NULL,
  task_type_id   INT DEFAULT 1 NOT NULL,
    PRIMARY KEY (id),
  CONSTRAINT fk_tasks_persons1
  FOREIGN KEY (responsible_id)
  REFERENCES person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_tasks_persons2
  FOREIGN KEY (created_by)
  REFERENCES person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_task_contact1
  FOREIGN KEY (contact_id)
  REFERENCES contact (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_task_deal1
  FOREIGN KEY (deal_id)
  REFERENCES deal (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_task_company1
  FOREIGN KEY (company_id)
  REFERENCES company (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_task_type1
  FOREIGN KEY (task_type_id)
  REFERENCES task_type (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);




CREATE INDEX fk_tasks_persons1_idx ON task (responsible_id);
CREATE INDEX fk_tasks_persons2_idx ON task (created_by);
CREATE UNIQUE INDEX task_id_UNIQUE ON task (id);
CREATE INDEX fk_task_contact1_idx ON task (contact_id);
CREATE INDEX fk_task_deal1_idx ON task (deal_id);
CREATE INDEX fk_task_company1_idx ON task (company_id);

-- -----------------------------------------------------
-- Table `crm_helios`.`note`
-- -----------------------------------------------------
CREATE TABLE note (
  id          INT    NOT NULL,
  text        VARCHAR(1000)  NOT NULL,
  contact_id  INT       NOT NULL,
  deal_id     INT       NULL,
  company_id  INT       NULL,
  created_by  INT       NOT NULL,
  date_create TIMESTAMP NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_notes_persons1
  FOREIGN KEY (created_by)
  REFERENCES person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_notes_contact1
  FOREIGN KEY (contact_id)
  REFERENCES contact (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_notes_deal1
  FOREIGN KEY (deal_id)
  REFERENCES deal (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_note_company1
  FOREIGN KEY (company_id)
  REFERENCES company (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE UNIQUE INDEX note_id_UNIQUE ON note (id);
CREATE INDEX fk_notes_persons1_idx ON note (created_by);
CREATE INDEX fk_notes_contact1_idx ON note (contact_id);
CREATE INDEX fk_notes_deal1_idx ON note (deal_id);
CREATE INDEX fk_note_company1_idx ON note (company_id);

--CREATE REST OF REFERENCES:
ALTER TABLE tag ADD CONSTRAINT fk_tag_person1 FOREIGN KEY (created_by) REFERENCES person (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE deal_contact ADD CONSTRAINT fk_deals_has_contact_deals1 FOREIGN KEY (deal_id) REFERENCES deal (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE deal_contact ADD CONSTRAINT fk_deals_has_contact_contact1 FOREIGN KEY (contact_id) REFERENCES contact (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE tag_deal ADD CONSTRAINT fk_tags_has_deal_deal1 FOREIGN KEY (deal_id) REFERENCES deal (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE company ADD CONSTRAINT fk_company_persons1 FOREIGN KEY (responsible_id) REFERENCES person (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE company ADD CONSTRAINT fk_company_person1 FOREIGN KEY (created_by) REFERENCES person (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE tag_contact_company ADD CONSTRAINT fk_tags_has_contact_contact1 FOREIGN KEY (contact_id) REFERENCES contact (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE file ADD CONSTRAINT fk_files_contact1 FOREIGN KEY (contact_id) REFERENCES contact (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE file ADD CONSTRAINT fk_files_deal1 FOREIGN KEY (deal_id) REFERENCES deal (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE file ADD CONSTRAINT fk_files_notes1 FOREIGN KEY (notes_id) REFERENCES note (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE file ADD CONSTRAINT fk_files_persons1 FOREIGN KEY (created_by) REFERENCES person (id) ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE contact
ADD CONSTRAINT fk_contact_type1
FOREIGN KEY (user_modify_id)
REFERENCES person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;
CREATE INDEX fk_contact_persons2_idx ON contact (user_modify_id);

ALTER TABLE company
ADD CONSTRAINT fk_company_type1
FOREIGN KEY (user_modify_id)
REFERENCES person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE INDEX fk_company_persons2_idx ON company (user_modify_id);


