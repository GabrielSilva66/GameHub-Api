CREATE TABLE GH_USER_OBTAINED_GAME (
    id_user_obtained_game SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_game INTEGER NOT NULL,
    id_store INTEGER,
    dt_obtained DATE,
    st_status game_status DEFAULT 'NOT_PLAYED',
    dt_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dt_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES GH_USER(id_user),
    CONSTRAINT fk_game FOREIGN KEY (id_game) REFERENCES GH_GAME(id_game),
    CONSTRAINT fk_store FOREIGN KEY (id_store) REFERENCES GH_STORE(id_store)
);
