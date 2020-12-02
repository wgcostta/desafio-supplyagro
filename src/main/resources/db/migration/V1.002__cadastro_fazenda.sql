CREATE TABLE fazenda (
    id uuid not null primary key,
    descricao character varying(255) not null,
    cnpj character varying(255) not null,
    unidade_administrativa_id uuid not null,
    CONSTRAINT fazenda_unidade_fk FOREIGN KEY (unidade_administrativa_id) REFERENCES unidadeadm(id)
);