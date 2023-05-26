CREATE TABLE respostas(
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensagem VARCHAR(255) NOT NULL,
    topico VARCHAR(50) NOT NULL,
    data_criacao VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    solucao tinyint,

    PRIMARY KEY(id)
);