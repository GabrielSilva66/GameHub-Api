CREATE TABLE GH_DEVELOPER (
  id_developer SERIAL PRIMARY KEY,
  no_name VARCHAR(255) NOT NULL,
  nu_year_of_foundation INTEGER,
  no_host_country VARCHAR(255),
  dt_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  dt_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);