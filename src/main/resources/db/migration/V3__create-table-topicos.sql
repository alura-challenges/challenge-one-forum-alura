CREATE TABLE topicos(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    mensagem VARCHAR(255) NOT NULL,
    data_criacao DATE,
    status VARCHAR(20),
    usuarios_id BIGINT NOT NULL,
    cursos_id BIGINT NOT NULL,

    FOREIGN KEY (usuarios_id) REFERENCES usuarios(id),
    FOREIGN KEY (cursos_id) REFERENCES cursos(id)
);