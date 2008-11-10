
insert into amp_field(`field_name`)
select 'current completion date'
from DUAL
where (
       select count(amp_field_id)
       from amp_field
       where field_name = 'current completion date'
      ) < 1;

insert into amp_field(`field_name`)
select 'Objective Assumption'
from DUAL
where (
       select count(amp_field_id)
       from amp_field
       where field_name = 'Objective Assumption'
      ) < 1;


insert into amp_field(`field_name`)
select 'Objective Verification'
from DUAL
where (
       select count(amp_field_id)
       from amp_field
       where field_name = 'Objective Verification'
      ) < 1;

insert into amp_field(`field_name`)
select 'Purpose Assumption'
from DUAL
where (
       select count(amp_field_id)
       from amp_field
       where field_name = 'Purpose Assumption'
      ) < 1;

insert into amp_field(`field_name`)
select 'Purpose Verification'
from DUAL
where (
       select count(amp_field_id)
       from amp_field
       where field_name = 'Purpose Verification'
      ) < 1;

insert into amp_field(`field_name`)
select 'Results Assumption'
from DUAL
where (
       select count(amp_field_id)
       from amp_field
       where field_name = 'Results Assumption'
      ) < 1;


insert into amp_field(`field_name`)
select 'Results Verification'
from DUAL
where (
       select count(amp_field_id)
       from amp_field
       where field_name = 'Results Verification'
      ) < 1;

 insert into amp_field(`field_name`)
 select 'Objective Objectively Verifiable Indicators'
 from DUAL
 where (
        select count(amp_field_id)
        from amp_field
        where field_name = 'Objective Objectively Verifiable Indicators'
       ) < 1;
insert into amp_field(`field_name`)
select 'Purpose Objectively Verifiable Indicators'
from DUAL
where (
       select count(amp_field_id)
       from amp_field
       where field_name = 'Purpose Objectively Verifiable Indicators'
      ) < 1;
insert into amp_field(`field_name`)
select 'Results Objectively Verifiable Indicators'
from DUAL
where (
       select count(amp_field_id)
       from amp_field
       where field_name = 'Results Objectively Verifiable Indicators'
      ) < 1;
insert into amp_field(`field_name`)
select 'Final Date for Disbursements'
from DUAL
where (
       select count(amp_field_id)
       from amp_field
       where field_name = 'Final Date for Disbursements'
      ) < 1;