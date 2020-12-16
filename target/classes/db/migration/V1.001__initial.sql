CREATE TABLE unidadeadm (
    id uuid not null primary key,
    descricao character varying(255) not null,
    municipio character varying(255) not null,
    habilitada boolean not null default true
);
ALTER TABLE unidadeadm ADD CONSTRAINT unidadeadm_un UNIQUE (descricao);