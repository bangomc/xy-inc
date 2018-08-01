insert into tabela(id,nome) values(1,'produto');
insert into coluna(id,nome,tipo_dado) values(1,'codigo',1);
insert into coluna(id,nome,tipo_dado) values(2,'descricao',1);
insert into coluna(id,nome,tipo_dado) values(3,'valor',3);
insert into tabela_colunas(tabela_id,colunas_id) values(1,1);
insert into tabela_colunas(tabela_id,colunas_id) values(1,2);
insert into tabela_colunas(tabela_id,colunas_id) values(1,3);


CREATE TABLE produto(id bigint auto_increment,codigo bigint, descricao varchar(255), valor decimal(20,2), CONSTRAINT Produto_pkey PRIMARY KEY (id));
INSERT INTO produto(id,codigo,descricao,valor) VALUES(default,123,'sapato',10.2);