ALTER TABLE vacancy ADD source VARCHAR(150) NOT NULL DEFAULT "";
update vacancy set source = '';

COMMIT;