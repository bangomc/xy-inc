insert into usuario (id,email,senha) values(nextval('HIBERNATE_SEQUENCE'),'bangomc@gmail.com','adminadmin');
insert into tabela(id,nome) values(1,'cliente');
insert into coluna(id,nome,tipo_dado) values(1,'nome',1);
insert into coluna(id,nome,tipo_dado) values(2,'telefone',1);
insert into tabela_colunas(tabela_id,colunas_id) values(1,1);
insert into tabela_colunas(tabela_id,colunas_id) values(1,2);