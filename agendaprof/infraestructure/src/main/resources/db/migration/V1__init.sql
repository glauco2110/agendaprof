CREATE TABLE TB_USUARIO
(
    id_usuario   serial       NOT NULL,
    nome         varchar(255) NOT NULL,
    login        varchar(255) NOT NULL,
    senha        varchar(500) NOT NULL,
    ultimo_login timestamp,
    CONSTRAINT USUARIO_PKEY PRIMARY KEY (id_usuario),
    CONSTRAINT USUARIO_UKEY UNIQUE (login)
);

insert into TB_USUARIO(nome, login, senha)
values ('admin', 'admin', '6AutLCJNOpFCutVn2VP9nA==:$argon2id$v=19$m=65536,t=3,p=1$mPMJS/g2Y6Jg8PWA5JTtdA$K90jsTpi8N6gWK4rqBzyBAhDT1gvLKzmOXhb4gFQnc4');


CREATE TABLE TB_SERVICO
(
    id_servico serial        NOT NULL,
    nome       varchar(255)  NOT NULL,
    descricao  varchar(2000) NOT NULL,
    valor      double precision,
    CONSTRAINT SERVICO_PKEY PRIMARY KEY (id_servico),
    CONSTRAINT SERVICO_UKEY UNIQUE (nome)
);