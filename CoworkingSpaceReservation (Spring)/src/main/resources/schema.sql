CREATE TABLE IF NOT EXISTS admins
(
    id    BIGINT PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS customers
(
    id    BIGINT PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS space_types
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name         VARCHAR(50)  NOT NULL UNIQUE,
    display_name VARCHAR(100) NOT NULL
);


CREATE TABLE IF NOT EXISTS workspaces
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type_id   BIGINT         NOT NULL,
    price     NUMERIC(15, 2) NOT NULL,
    CONSTRAINT unique_type_price UNIQUE (type_id, price),
    FOREIGN KEY (type_id) REFERENCES space_types (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reservations
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    customer_id  BIGINT    NOT NULL,
    workspace_id BIGINT    NOT NULL,
    start_time   TIMESTAMP NOT NULL,
    end_time     TIMESTAMP NOT NULL,
    CONSTRAINT unique_workspace_time UNIQUE (workspace_id, start_time, end_time),
    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE,
    FOREIGN KEY (workspace_id) REFERENCES workspaces (id) ON DELETE CASCADE
);

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence
    INCREMENT BY 5;
