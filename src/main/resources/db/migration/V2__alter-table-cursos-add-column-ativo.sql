alter table cursos add column ativo tinyint;
update cursos set ativo = 1;