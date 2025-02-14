CREATE TABLE GH_USER (
  id_user SERIAL PRIMARY KEY,
  no_email VARCHAR(255) NOT NULL UNIQUE,
  no_password_hash VARCHAR(255) NOT NULL,
  user_type user_type NOT NULL DEFAULT 'COMMON',
  is_email_verified BOOLEAN DEFAULT FALSE,
  id_profile INTEGER REFERENCES GH_PROFILE(id_profile) ON DELETE CASCADE,
  dt_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  dt_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);