CREATE TABLE GH_STORE (
  id_store SERIAL PRIMARY KEY,
  no_name VARCHAR(255) NOT NULL,
  no_url VARCHAR(512) NOT NULL,
  dt_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  dt_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

