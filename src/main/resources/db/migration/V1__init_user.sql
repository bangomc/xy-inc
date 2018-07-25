CREATE TABLE usuario
(
  id serial NOT NULL,
  email character varying(255),
  senha character varying(255),
  CONSTRAINT usuario_pkey PRIMARY KEY (id)
);
insert into usuario (email,senha) values('bangomc@gmail.com','adminadmin');