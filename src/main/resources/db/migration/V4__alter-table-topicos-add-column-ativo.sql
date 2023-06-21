alter table topicos add column ativo tinyint;
update topicos set ativo = 1;