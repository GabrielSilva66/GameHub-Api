
CREATE TABLE GH_PROFILE (
  id_profile SERIAL PRIMARY KEY,
  dt_birth_date DATE NOT NULL CHECK (dt_birth_date <= CURRENT_DATE),
  no_name VARCHAR(50) NOT NULL,
  gender gender,
  dt_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  dt_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);