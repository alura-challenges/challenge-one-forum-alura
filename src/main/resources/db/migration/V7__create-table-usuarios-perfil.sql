create table usuarios_perfil(
    id bigint not null auto_increment,
    usuario_id bigint not null,
    perfil_id bigint not null,

    primary key(id),
    constraint fk_usuarios_perfil_usuario foreign key(usuario_id) references usuarios(id),
    constraint fk_usuarios_perfil_perfil foreign key(perfil_id) references perfil(id)

);