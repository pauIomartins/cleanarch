CREATE TABLE address (
    id INTEGER NOT NULL AUTO_INCREMENT,
    label VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE product (
    id INTEGER NOT NULL AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    ean VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
);