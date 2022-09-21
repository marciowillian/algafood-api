insert into cozinha (nome) values ('Tailandesa');
insert into cozinha (nome) values ('Indiana');



insert into forma_pagamento (descricao) values ('Credito');
insert into forma_pagamento (descricao) values ('Debito');
insert into forma_pagamento (descricao) values ('A vista');

insert into estado (nome) values ('MA');
insert into estado (nome) values ('CE');
insert into estado (nome) values ('PI');
insert into estado (nome) values ('AM');


insert into cidade (nome, estado_id) mvalues ('São Luis', 1);
insert into cidade (nome, estado_id) mvalues ('Itapipoca', 2);
insert into cidade (nome, estado_id) mvalues ('Teresina', 3);

insert into restaurante (
	nome, 
	taxa_frete, 
	cozinha_id,
	endereco_cep,
	endereco_logradouro,
	endereco_numero,
	endereco_complemento,
	endereco_bairro,
	endereco_cidade_id
) values (
	'Sal & Brasa', 
	29.90, 
	1,
	'65000000',
	'Rua Turu II Quadra 3',
	'105-B',
	'Ao lado da Padaria Santa Luzia',
	'Tucuma',
	2        	
);

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Barriga Verde', 26.90, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Panela de Minas', 32.90, 2);

insert into algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1,1);
insert into algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1,2);
insert into algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1,3);
insert into algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2,1);
insert into algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2,2);
insert into algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (3,3);