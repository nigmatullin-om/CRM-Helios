--Delete Old FKs
ALTER TABLE tag_deal DROP
  CONSTRAINT fk_tags_has_deal_tags1
;

ALTER TABLE  company DROP
  CONSTRAINT fk_company_phone_types1
;


ALTER TABLE  file DROP
  CONSTRAINT fk_files_company1
;


ALTER TABLE  person DROP
  CONSTRAINT fk_persons_role1
;

ALTER TABLE  person DROP
CONSTRAINT fk_persons_files1;

ALTER TABLE  person DROP
CONSTRAINT fk_person_lang1
;

ALTER TABLE deal DROP
  CONSTRAINT fk_deal_persons1;

ALTER TABLE deal DROP
  CONSTRAINT fk_deal_state1;

ALTER TABLE deal DROP
  CONSTRAINT fk_deals_company1;

ALTER TABLE deal DROP
  CONSTRAINT fk_deal_person1;

ALTER TABLE contact DROP
  CONSTRAINT fk_contact_persons1;

ALTER TABLE contact DROP
  CONSTRAINT fk_contact_phone_types1;

ALTER TABLE contact DROP
  CONSTRAINT fk_contact_company1;

ALTER TABLE contact DROP
  CONSTRAINT fk_contact_person1;


ALTER TABLE task DROP
  CONSTRAINT fk_tasks_persons1;

ALTER TABLE task DROP
  CONSTRAINT fk_tasks_persons2;

ALTER TABLE task DROP
  CONSTRAINT fk_task_contact1;

ALTER TABLE task DROP
  CONSTRAINT fk_task_deal1;

ALTER TABLE task DROP
  CONSTRAINT fk_task_company1
;


ALTER TABLE note DROP
  CONSTRAINT fk_notes_persons1;

ALTER TABLE note DROP
  CONSTRAINT fk_notes_contact1
;

ALTER TABLE note  DROP
  CONSTRAINT fk_notes_deal1
;

ALTER TABLE note DROP
  CONSTRAINT fk_note_company1
;

ALTER TABLE tag  DROP
  CONSTRAINT fk_tag_person1;

ALTER TABLE deal_contact DROP
  CONSTRAINT fk_deals_has_contact_deals1;

ALTER TABLE deal_contact DROP
  CONSTRAINT fk_deals_has_contact_contact1;

ALTER TABLE tag_deal DROP
  CONSTRAINT fk_tags_has_deal_deal1;

ALTER TABLE company DROP
  CONSTRAINT fk_company_persons1
;

ALTER TABLE company DROP
  CONSTRAINT fk_company_person1;

ALTER TABLE tag_contact_company  DROP
  CONSTRAINT fk_tags_has_contact_contact1;

ALTER TABLE file  DROP
  CONSTRAINT fk_files_contact1;

ALTER TABLE file  DROP
  CONSTRAINT fk_files_deal1;

ALTER TABLE file  DROP
  CONSTRAINT fk_files_notes1;

ALTER TABLE file  DROP
  CONSTRAINT fk_files_persons1;

--Creating:

ALTER TABLE tag_deal ADD
CONSTRAINT fk_tags_has_deal_tags1
FOREIGN KEY (tag_id)
REFERENCES tag (id)
ON DELETE CASCADE
ON UPDATE NO ACTION
;

ALTER TABLE  company ADD
CONSTRAINT fk_company_phone_types1
FOREIGN KEY (phone_type_id)
REFERENCES phone_type (id)
ON DELETE CASCADE
ON UPDATE NO ACTION
;


ALTER TABLE  file ADD
CONSTRAINT fk_files_company1
FOREIGN KEY (company_id)
REFERENCES company (id)
ON DELETE CASCADE
ON UPDATE NO ACTION
;


ALTER TABLE  person  ADD
CONSTRAINT fk_persons_role1
FOREIGN KEY (role_id)
REFERENCES role (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE  person  ADD
CONSTRAINT fk_persons_files1
FOREIGN KEY (photo_file_id)
REFERENCES file (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE  person  ADD
CONSTRAINT fk_person_lang1
FOREIGN KEY (lang_id)
REFERENCES lang (id)
ON DELETE CASCADE
ON UPDATE NO ACTION
;


ALTER TABLE deal ADD
CONSTRAINT fk_deal_persons1
FOREIGN KEY (responsible_id)
REFERENCES person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE deal ADD
CONSTRAINT fk_deal_state1
FOREIGN KEY (stage_id)
REFERENCES stage (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE deal ADD
CONSTRAINT fk_deals_company1
FOREIGN KEY (company_id)
REFERENCES company (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE deal ADD
CONSTRAINT fk_deal_person1
FOREIGN KEY (created_by)
REFERENCES person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE contact ADD
CONSTRAINT fk_contact_persons1
FOREIGN KEY (responsible_id)
REFERENCES person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE contact ADD
CONSTRAINT fk_contact_phone_types1
FOREIGN KEY (phone_type_id)
REFERENCES phone_type (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE contact ADD
CONSTRAINT fk_contact_company1
FOREIGN KEY (company_id)
REFERENCES company (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE contact ADD
CONSTRAINT fk_contact_person1
FOREIGN KEY (created_by)
REFERENCES person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;


ALTER TABLE task ADD
CONSTRAINT fk_tasks_persons1
FOREIGN KEY (responsible_id)
REFERENCES person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE task ADD
CONSTRAINT fk_tasks_persons2
FOREIGN KEY (created_by)
REFERENCES person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE task ADD
CONSTRAINT fk_task_contact1
FOREIGN KEY (contact_id)
REFERENCES contact (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE task ADD
CONSTRAINT fk_task_deal1
FOREIGN KEY (deal_id)
REFERENCES deal (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE task ADD
CONSTRAINT fk_task_company1
FOREIGN KEY (company_id)
REFERENCES company (id)
ON DELETE CASCADE
ON UPDATE NO ACTION
;


ALTER TABLE note ADD
CONSTRAINT fk_notes_persons1
FOREIGN KEY (created_by)
REFERENCES person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE note ADD
CONSTRAINT fk_notes_contact1
FOREIGN KEY (contact_id)
REFERENCES contact (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE note ADD
CONSTRAINT fk_notes_deal1
FOREIGN KEY (deal_id)
REFERENCES deal (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE note ADD
CONSTRAINT fk_note_company1
FOREIGN KEY (company_id)
REFERENCES company (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE tag ADD
CONSTRAINT fk_tag_person1
FOREIGN KEY (created_by)
REFERENCES person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE deal_contact
ADD CONSTRAINT fk_deals_has_contact_deals1
FOREIGN KEY (deal_id)
REFERENCES deal (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE deal_contact
ADD CONSTRAINT fk_deals_has_contact_contact1
FOREIGN KEY (contact_id)
REFERENCES contact (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE tag_deal
ADD CONSTRAINT fk_tags_has_deal_deal1
FOREIGN KEY (deal_id)
REFERENCES deal (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE company
ADD CONSTRAINT fk_company_persons1
FOREIGN KEY (responsible_id)
REFERENCES person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE company
ADD CONSTRAINT fk_company_person1
FOREIGN KEY (created_by)
REFERENCES person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE tag_contact_company
ADD CONSTRAINT fk_tags_has_contact_contact1
FOREIGN KEY (contact_id)
REFERENCES contact (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE file
ADD CONSTRAINT fk_files_contact1
FOREIGN KEY (contact_id)
REFERENCES contact (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE file
ADD CONSTRAINT fk_files_deal1
FOREIGN KEY (deal_id)
REFERENCES deal (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE file
ADD CONSTRAINT fk_files_notes1
FOREIGN KEY (notes_id)
REFERENCES note (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE file
ADD CONSTRAINT fk_files_persons1
FOREIGN KEY (created_by)
REFERENCES person (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

