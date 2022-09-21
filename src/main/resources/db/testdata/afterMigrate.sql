set foreign_key_checks = 0;

DELETE from cidade;
DELETE from cozinha;
DELETE from estado;
DELETE from forma_pagamento;
DELETE from grupo;
DELETE from grupo_permissao;
DELETE from permissao;
DELETE from produto;
DELETE from restaurante;
DELETE from restaurante_forma_pagamento;
DELETE from usuario;
DELETE FROM usuario_grupo;
DELETE from pedido;
DELETE from item_pedido;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;

insert into cozinha (id, nome) values(1,'Tailandesa');
insert into cozinha (id, nome) values(2,'São Paulo');
insert into cozinha (id, nome) values(3,'Argentina');
insert into cozinha (id, nome) values(4,'Brasileira');

insert into estado (id, nome) values (1,'Minas Gerais');
insert into estado (id, nome) values (2,'São Paulo');
insert into estado (id, nome) values (3,'Ceará');

insert into cidade (id, nome, estado_id) values(1,'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values(2,'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values(3,'São Paulo', 2);
insert into cidade (id, nome, estado_id) values(4,'Campinas', 2);
insert into cidade (id, nome, estado_id) values(5,'Fortaleza', 3);

insert into restaurante (cozinha_id, nome, taxa_frete, data_atualizacao, data_cadastro, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro) 
values(1, 'Sal & Brasa',9.90, '2022-08-20 08:00:00', '2022-08-20 08:00:00', 1, '65000-000', 'Rua Santo Adalberto', '25', 'ao lado da AABEM', 'Forquilha');

insert into usuario(nome, email, senha, data_cadastro)
values ('Marcio Willian Chaves Cardoso','willian_mw.cc@hotmail.com','123456','2022-08-05 08:00:00');

insert into produto (restaurante_id, nome, descricao, preco, ativo)
values(1, 'Pizza Calabreza', 'Pizza de 8 fatias', 29.90, 1);

insert into produto (restaurante_id, nome, descricao, preco, ativo)
values(1, 'Pizza de Carne de Sol', 'Pizza de 8 fatias', 34.90, 1);

insert into forma_pagamento(descricao) values ('debito');
insert into forma_pagamento(descricao) values ('credito');
insert into forma_pagamento(descricao) values ('pix');

insert into pedido
	(subtotal,
	taxa_frete,
	valor_total,
	data_criacao,
	data_confirmacao,
	data_entrega,
	restaurante_id,
	forma_pagamento_id,
	status,
	usuario_cliente_id,
	endereco_cep,
	endereco_logradouro,
	endereco_numero,
	endereco_complemento,
	endereco_bairro,
	endereco_cidade_id)
values (3000.00,
99.90,
3099.90,
'2022-08-20 08:00:00',
'2022-08-20 08:30:00',
'2022-08-20 12:20:00',
1,
1,
'entregue',
1,
'65054-115',
'Rua Santo Adalberto',
'25',
'Ao lado da AABEM',
'forquilha',
1);

insert into item_pedido (quantidade,preco_unitario, preco_total, observacao, pedido_id, produto_id)
values (1, 29.9, 29.9, 'Valor da pizza inteira', 1, 1);

insert into item_pedido (quantidade,preco_unitario, preco_total, observacao, pedido_id, produto_id)
values (1, 34.9, 34.9, 'Valor da pizza inteira', 1, 2);

insert into algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id)
values (1, 1);