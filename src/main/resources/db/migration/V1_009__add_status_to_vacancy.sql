ALTER TABLE vacancy ADD COLUMN status VARCHAR(150) NOT NULL DEFAULT '';
update vacancy set status = '';

COMMIT;