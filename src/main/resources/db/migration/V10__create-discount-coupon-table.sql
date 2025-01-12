CREATE TABLE GH_DISCOUNT_COUPON (
    id_discount_coupon SERIAL PRIMARY KEY,
    id_store INTEGER NOT NULL,
    no_name VARCHAR(255) NOT NULL,
    tp_coupon VARCHAR(255) NOT NULL,
    nu_value DECIMAL(10, 2) NOT NULL,
    dt_deadline DATE,
    nu_maximum_use INTEGER,
    nu_minimum_price DECIMAL(10, 2),
    dt_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dt_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_store FOREIGN KEY (id_store) REFERENCES GH_STORE(id_store)
);
