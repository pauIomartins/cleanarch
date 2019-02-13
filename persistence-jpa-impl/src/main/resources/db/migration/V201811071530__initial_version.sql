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

CREATE TABLE movement (
    id INTEGER NOT NULL AUTO_INCREMENT,
    address_id_from INTEGER,
    address_id_to INTEGER,
    product_id INTEGER NOT NULL,
    quantity NUMERIC(10,4) NOT NULL,
    type VARCHAR(60) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id_from) references address(id),
    FOREIGN KEY (address_id_to) references address(id),
    FOREIGN KEY (product_id) references product(id)
);

CREATE TABLE stock (
    id INTEGER NOT NULL AUTO_INCREMENT,
    address_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity NUMERIC(10,4) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) references address(id),
    FOREIGN KEY (product_id) references product(id)
);