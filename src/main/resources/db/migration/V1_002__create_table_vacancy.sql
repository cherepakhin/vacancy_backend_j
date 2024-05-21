CREATE table if not exists vacancy
(
    "n"            serial not null UNIQUE,
    "title"     varchar(120) not null default '',
    "description"     varchar(400) not null default '',
    "company_n"     integer not null default -1,
    CONSTRAINT vacancy_pkey PRIMARY KEY (n),
    CONSTRAINT fk_company_n FOREIGN KEY (company_n) REFERENCES company (n)
);
