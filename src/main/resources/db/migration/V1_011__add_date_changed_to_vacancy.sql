ALTER TABLE vacancy ADD COLUMN date_changed TIMESTAMP default now();
update vacancy set date_changed = now();

COMMIT;