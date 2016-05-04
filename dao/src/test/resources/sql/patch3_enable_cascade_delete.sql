--Delete Old FKs
ALTER TABLE test_crm_helios.tag_deal DROP
  CONSTRAINT fk_tags_has_deal_tags1
;

ALTER TABLE  test_crm_helios.company DROP
  CONSTRAINT fk_company_phone_types1
;


ALTER TABLE  test_crm_helios.file DROP
  CONSTRAINT fk_files_company1
;


ALTER TABLE  test_crm_helios.person DROP
  CONSTRAINT fk_persons_role1
;

ALTER TABLE  test_crm_helios.person DROP
CONSTRAINT fk_persons_files1;

ALTER TABLE  test_crm_helios.person DROP
CONSTRAINT fk_person_lang1
;

ALTER TABLE test_crm_helios.deal DROP
  CONSTRAINT fk_deal_persons1;

ALTER TABLE test_crm_helios.deal DROP
  CONSTRAINT fk_deal_state1;

ALTER TABLE test_crm_helios.deal DROP
  CONSTRAINT fk_deals_company1;

ALTER TABLE test_crm_helios.deal DROP
  CONSTRAINT fk_deal_person1;

ALTER TABLE test_crm_helios.contact DROP
  CONSTRAINT fk_contact_persons1;

ALTER TABLE test_crm_helios.contact DROP
  CONSTRAINT fk_contact_phone_types1;

ALTER TABLE test_crm_helios.contact DROP
  CONSTRAINT fk_contact_company1;

ALTER TABLE test_crm_helios.contact DROP
  CONSTRAINT fk_contact_person1;


ALTER TABLE test_crm_helios.task DROP
  CONSTRAINT fk_tasks_persons1;

ALTER TABLE test_crm_helios.task DROP
  CONSTRAINT fk_tasks_persons2;

ALTER TABLE test_crm_helios.task DROP
  CONSTRAINT fk_task_contact1;

ALTER TABLE test_crm_helios.task DROP
  CONSTRAINT fk_task_deal1;

ALTER TABLE test_crm_helios.task DROP
  CONSTRAINT fk_task_company1
;


ALTER TABLE test_crm_helios.note DROP
  CONSTRAINT fk_notes_persons1;

ALTER TABLE test_crm_helios.note DROP
  CONSTRAINT fk_notes_contact1
;

ALTER TABLE test_crm_helios.note  DROP
  CONSTRAINT fk_notes_deal1
;

ALTER TABLE test_crm_helios.note DROP
  CONSTRAINT fk_note_company1
;

ALTER TABLE test_crm_helios.tag  DROP
  CONSTRAINT fk_tag_person1;

ALTER TABLE test_crm_helios.deal_contact DROP
  CONSTRAINT fk_deals_has_contact_deals1;

ALTER TABLE test_crm_helios.deal_contact DROP
  CONSTRAINT fk_deals_has_contact_contact1;

ALTER TABLE test_crm_helios.tag_deal DROP
  CONSTRAINT fk_tags_has_deal_deal1;

ALTER TABLE test_crm_helios.company DROP
  CONSTRAINT fk_company_persons1
;

ALTER TABLE test_crm_helios.company DROP
  CONSTRAINT fk_company_person1;

ALTER TABLE test_crm_helios.tag_contact_company  DROP
  CONSTRAINT fk_tags_has_contact_contact1;

ALTER TABLE test_crm_helios.file  DROP
  CONSTRAINT fk_files_contact1;

ALTER TABLE test_crm_helios.file  DROP
  CONSTRAINT fk_files_deal1;

ALTER TABLE test_crm_helios.file  DROP
  CONSTRAINT fk_files_notes1;

ALTER TABLE test_crm_helios.file  DROP
  CONSTRAINT fk_files_persons1;

--Creating:

ALTER TABLE test_crm_helios.tag_deal ADD
CONSTRAINT fk_tags_has_deal_tags1
FOREIGN KEY (tag_id)
REFERENCES test_crm_helios.tag (id)
ON DELETE CASCADE
ON UPDATE NO ACTION
;

ALTER TABLE  test_crm_helios.company ADD
CONSTRAINT fk_company_phone_types1
FOREIGN KEY (phone_type_id)
REFERENCES test_crm_helios.phone_type (id)
ON DELETE CASCADE
ON UPDATE NO ACTION
;


ALTER TABLE  test_crm_helios.file ADD
CONSTRAINT fk_files_company1
FOREIGN KEY (company_id)
REFERENCES test_crm_helios.company (id)
ON DELETE CASCADE
ON UPDATE NO ACTION
;


ALTER TABLE  test_crm_helios.person  ADD
CONSTRAINT fk_persons_role1
FOREIGN KEY (role_id)
REFERENCES test_crm_helios.role (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE  test_crm_helios.person  ADD
CONSTRAINT fk_persons_files1
FOREIGN KEY (photo_file_id)
REFERENCES test_crm_helios.file (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE  test_crm_helios.person  ADD
CONSTRAINT fk_person_lang1
FOREIGN KEY (lang_id)
REFERENCES test_crm_helios.lang (id)
ON DELETE CASCADE
ON UPDATE NO ACTION
;


ALTER TABLE test_crm_helios.deal ADD
CONSTRAINT fk_deal_persons1
FOREIGN KEY (responsible_id)
REFERENCES test_crm_helios.person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.deal ADD
CONSTRAINT fk_deal_state1
FOREIGN KEY (stage_id)
REFERENCES test_crm_helios.stage (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.deal ADD
CONSTRAINT fk_deals_company1
FOREIGN KEY (company_id)
REFERENCES test_crm_helios.company (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.deal ADD
CONSTRAINT fk_deal_person1
FOREIGN KEY (created_by)
REFERENCES test_crm_helios.person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.contact ADD
CONSTRAINT fk_contact_persons1
FOREIGN KEY (responsible_id)
REFERENCES test_crm_helios.person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.contact ADD
CONSTRAINT fk_contact_phone_types1
FOREIGN KEY (phone_type_id)
REFERENCES test_crm_helios.phone_type (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.contact ADD
CONSTRAINT fk_contact_company1
FOREIGN KEY (company_id)
REFERENCES test_crm_helios.company (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.contact ADD
CONSTRAINT fk_contact_person1
FOREIGN KEY (created_by)
REFERENCES test_crm_helios.person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;


ALTER TABLE test_crm_helios.task ADD
CONSTRAINT fk_tasks_persons1
FOREIGN KEY (responsible_id)
REFERENCES test_crm_helios.person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.task ADD
CONSTRAINT fk_tasks_persons2
FOREIGN KEY (created_by)
REFERENCES test_crm_helios.person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.task ADD
CONSTRAINT fk_task_contact1
FOREIGN KEY (contact_id)
REFERENCES test_crm_helios.contact (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.task ADD
CONSTRAINT fk_task_deal1
FOREIGN KEY (deal_id)
REFERENCES test_crm_helios.deal (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.task ADD
CONSTRAINT fk_task_company1
FOREIGN KEY (company_id)
REFERENCES test_crm_helios.company (id)
ON DELETE CASCADE
ON UPDATE NO ACTION
;


ALTER TABLE test_crm_helios.note ADD
CONSTRAINT fk_notes_persons1
FOREIGN KEY (created_by)
REFERENCES test_crm_helios.person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.note ADD
CONSTRAINT fk_notes_contact1
FOREIGN KEY (contact_id)
REFERENCES test_crm_helios.contact (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.note ADD
CONSTRAINT fk_notes_deal1
FOREIGN KEY (deal_id)
REFERENCES test_crm_helios.deal (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.note ADD
CONSTRAINT fk_note_company1
FOREIGN KEY (company_id)
REFERENCES test_crm_helios.company (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.tag ADD
CONSTRAINT fk_tag_person1
FOREIGN KEY (created_by)
REFERENCES test_crm_helios.person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.deal_contact
ADD CONSTRAINT fk_deals_has_contact_deals1
FOREIGN KEY (deal_id)
REFERENCES test_crm_helios.deal (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.deal_contact
ADD CONSTRAINT fk_deals_has_contact_contact1
FOREIGN KEY (contact_id)
REFERENCES test_crm_helios.contact (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.tag_deal
ADD CONSTRAINT fk_tags_has_deal_deal1
FOREIGN KEY (deal_id)
REFERENCES test_crm_helios.deal (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.company
ADD CONSTRAINT fk_company_persons1
FOREIGN KEY (responsible_id)
REFERENCES test_crm_helios.person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.company
ADD CONSTRAINT fk_company_person1
FOREIGN KEY (created_by)
REFERENCES test_crm_helios.person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.tag_contact_company
ADD CONSTRAINT fk_tags_has_contact_contact1
FOREIGN KEY (contact_id)
REFERENCES test_crm_helios.contact (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.file
ADD CONSTRAINT fk_files_contact1
FOREIGN KEY (contact_id)
REFERENCES test_crm_helios.contact (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.file
ADD CONSTRAINT fk_files_deal1
FOREIGN KEY (deal_id)
REFERENCES test_crm_helios.deal (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.file
ADD CONSTRAINT fk_files_notes1
FOREIGN KEY (notes_id)
REFERENCES test_crm_helios.note (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE test_crm_helios.file
ADD CONSTRAINT fk_files_persons1
FOREIGN KEY (created_by)
REFERENCES test_crm_helios.person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

