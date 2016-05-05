ALTER TABLE crm_helios.note ALTER COLUMN contact_id DROP NOT NULL;

CREATE OR REPLACE FUNCTION crm_helios.note_fields_check() RETURNS  TRIGGER as $note_fields_check$
  BEGIN
    --Note should be related to contact or
    IF NOT( (NEW.contact_id IS NOT NULL
            AND NEW.company_id IS NULL
            AND NEW.deal_id IS NULL )
       OR (NEW.contact_id IS NULL
           AND NEW.company_id IS NOT NULL
           AND NEW.deal_id IS NULL )
       OR (NEW.contact_id IS NULL
           AND NEW.company_id IS NULL
           AND NEW.deal_id IS NOT NULL))
    THEN
      RAISE EXCEPTION 'Note should be related only for one entity (contact[%], company[%] or deal[%])',
        NEW.contact_id, NEW.company_id, NEW.deal_id;
    END IF;
    RETURN NEW;
END;
$note_fields_check$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS note_fields_check ON crm_helios.note;

CREATE TRIGGER note_fields_check BEFORE INSERT ON crm_helios.note
FOR EACH ROW EXECUTE PROCEDURE crm_helios.note_fields_check();