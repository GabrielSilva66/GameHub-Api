-- Tabela de Relacionamento Platform-Game
CREATE TABLE GH_PLATFORM_GAME (
  id_platform_game SERIAL PRIMARY KEY,
  id_game INTEGER REFERENCES GH_GAME(id_game) ON DELETE CASCADE,
  id_platform INTEGER REFERENCES GH_PLATFORM(id_platform) ON DELETE CASCADE,
  dt_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  dt_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);