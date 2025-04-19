CREATE table if not exists company
(
    "n"            serial not null UNIQUE,
    "name"     varchar(120) not null default '',
    CONSTRAINT company_pkey PRIMARY KEY (n)
);

-- default value for null company
insert into company(n, name) values (-1,'-');

COMMIT;
