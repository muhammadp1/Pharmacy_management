DROP TABLE IF EXISTS product_platforms;
DROP TABLE IF EXISTS product_images;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS platforms;
DROP TABLE IF EXISTS pharmacies;
DROP TABLE IF EXISTS users;


-- USERS
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- PHARMACIES
CREATE TABLE pharmacies (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- PLATFORMS
CREATE TABLE platforms (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- CATEGORIES
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- PRODUCTS
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    sku VARCHAR(150) UNIQUE NOT NULL,
    pharmacy_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_product_pharmacy
        FOREIGN KEY (pharmacy_id)
        REFERENCES pharmacies(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
        ON DELETE CASCADE
);

-- PRODUCT IMAGES
CREATE TABLE product_images (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    image_url TEXT NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_image_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE CASCADE
);

-- PRODUCT PLATFORM MAPPING
CREATE TABLE product_platforms (
    product_id BIGINT NOT NULL,
    platform_id BIGINT NOT NULL,

    PRIMARY KEY (product_id, platform_id),

    CONSTRAINT fk_pp_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_pp_platform
        FOREIGN KEY (platform_id)
        REFERENCES platforms(id)
        ON DELETE CASCADE
);

-- Fast search indexes

CREATE INDEX idx_products_name
ON products(name);

CREATE INDEX idx_products_sku
ON products(sku);

CREATE INDEX idx_products_pharmacy
ON products(pharmacy_id);

CREATE INDEX idx_products_category
ON products(category_id);

INSERT INTO platforms (name) VALUES
('Amazon'),
('Ebay'),
('Temu'),
('TikTok'),
('Woucher');