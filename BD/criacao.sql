CREATE TABLE usuarios (
    usuario_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    usuario_email VARCHAR(255) NOT NULL UNIQUE,
    usuario_imagem_url VARCHAR(255),
    localizacao VARCHAR(255),
    horta_tipo INT -- 0 para interior, 1 para exterior, 2 para ambos
);

CREATE TABLE plantas (
    planta_id SERIAL PRIMARY KEY,
    nome_comum VARCHAR(255) NOT NULL,
    nome_cientifico VARCHAR(255) UNIQUE,
    categoria VARCHAR(255),
    planta_imagem_url VARCHAR(255),
    descricao TEXT,
    tipo_solo VARCHAR(255),
    irrigacao VARCHAR(255),
    local_plantio VARCHAR(255),
    clima VARCHAR(255),
    luz_solar VARCHAR(255)
);

CREATE TABLE guias (
    guia_id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    guia_imagem_url VARCHAR(255),
    guia_titulo VARCHAR(255) NOT NULL,
    guia_conteudo TEXT NOT NULL,
    guia_tipo INT, -- 0 para interior, 1 para exterior, 2 para ambos
    FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id)
);

CREATE TABLE guiasxplantas (
    planta_id INT NOT NULL,
    guia_id INT NOT NULL,
    PRIMARY KEY (planta_id, guia_id),
    FOREIGN KEY (planta_id) REFERENCES plantas(planta_id),
    FOREIGN KEY (guia_id) REFERENCES guias(guia_id)
);

CREATE TABLE posts (
    post_id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    post_texto TEXT NOT NULL,
    post_data DATE NOT NULL DEFAULT CURRENT_DATE,
    post_imagem_url VARCHAR(255),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id)
);

CREATE TABLE comentarios (
    comentario_id SERIAL PRIMARY KEY,
    post_id INT NOT NULL,
    usuario_id INT NOT NULL,
    comentario_data DATE NOT NULL DEFAULT CURRENT_DATE,
    comentario_texto TEXT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(post_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id)
);