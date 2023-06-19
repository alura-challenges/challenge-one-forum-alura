create table cursos(
id bigint not null auto_increment,
nome varchar(255),
categoria varchar(255),

primary key(id)
);

create table usuarios(
id bigint not null auto_increment,
nome varchar(100) not null,
email varchar(100) not null,
senha varchar(255) not null,

primary key(id)
);

create table topicos(

id bigint not null auto_increment,
titulo varchar(255) not null,
mensagem text not null,
data_criacao datetime not null,
status varchar(255),
autor_id bigint not null,
curso_id bigint not null,

primary key(id),
constraint fk_autor foreign key(autor_id) references usuarios(id),
constraint fk_curso foreign key(curso_id) references cursos(id)
);