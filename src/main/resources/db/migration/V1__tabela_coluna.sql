CREATE TABLE PUBLIC.tabela(id bigint auto_increment,nome varchar(255), CONSTRAINT tabela_pkey PRIMARY KEY (id));
CREATE TABLE PUBLIC.coluna(id bigint auto_increment,nome varchar(255),tipo_dado int, CONSTRAINT coluna_pkey PRIMARY KEY (id));
CREATE TABLE PUBLIC.tabela_colunas(tabela_id bigint,colunas_id bigint);

ALTER TABLE PUBLIC.tabela_colunas ADD FOREIGN KEY (tabela_id) REFERENCES PUBLIC.tabela(id);
ALTER TABLE PUBLIC.tabela_colunas ADD FOREIGN KEY (colunas_id) REFERENCES PUBLIC.coluna(id);