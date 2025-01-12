CREATE TABLE GH_ASSESSMENT (
    id_assessment SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_game INTEGER NOT NULL,
    nu_rating INTEGER NOT NULL,
    ds_assessment VARCHAR(1024),
    dt_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dt_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES GH_USER(id_user),
    CONSTRAINT fk_game FOREIGN KEY (id_game) REFERENCES GH_GAME(id_game),
    CONSTRAINT uq_user_game UNIQUE (id_user, id_game)
);
