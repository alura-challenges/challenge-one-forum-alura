alter table usuarios add column ativo tinyint;

update usuarios set ativo = 1;