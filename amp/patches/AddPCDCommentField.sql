insert into amp_field(`field_name`) select 'Proposed Completion Date' from DUAL where (select count(amp_field_id)from amp_field where field_name = 'Proposed Completion Date') < 1;