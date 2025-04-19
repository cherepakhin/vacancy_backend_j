ALTER TABLE vacancy ADD COLUMN comment VARCHAR(150) NOT NULL DEFAULT '';
update vacancy set comment = '';

COMMIT;