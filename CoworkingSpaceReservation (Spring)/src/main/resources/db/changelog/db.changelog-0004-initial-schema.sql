--liquibase formatted sql

--changeset DimAl:1
CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(20)  NOT NULL CHECK (role IN ('ADMIN', 'CUSTOMER'))
    );

--changeset DimAl:2
ALTER TABLE admins DROP COLUMN IF EXISTS login;

--changeset DimAl:3
ALTER TABLE admins
    ADD CONSTRAINT fk_admin_user FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE;

--changeset DimAl:4
ALTER TABLE customers DROP COLUMN IF EXISTS login;

--changeset DimAl:5
ALTER TABLE customers
    ADD CONSTRAINT fk_customer_user FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE;

--changeset DimAl:6
DROP SEQUENCE IF EXISTS hibernate_sequence;

