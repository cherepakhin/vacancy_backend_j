ALTER TABLE vacancy ADD COLUMN link VARCHAR(150) NOT NULL DEFAULT '';
update vacancy set link = '';

COMMIT;