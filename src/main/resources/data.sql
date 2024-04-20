CREATE TABLE USER_ENTITY (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    creation_date DATE,
    modification_date DATE,
    last_login_date DATE,
    is_active BOOLEAN
);

CREATE TABLE PHONE_ENTITY (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255),
    number VARCHAR(255),
    city_code VARCHAR(255),
    country_code VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES USER_ENTITY(id)
);
