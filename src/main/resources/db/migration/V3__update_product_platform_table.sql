-- Drop the existing table
DROP TABLE IF EXISTS product_platforms;

-- Create the table to match the new Entity structure
CREATE TABLE product_platforms (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    platform_id BIGINT NOT NULL,
    CONSTRAINT fk_pp_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_pp_platform FOREIGN KEY (platform_id) REFERENCES platforms(id) ON DELETE CASCADE,
    CONSTRAINT uq_product_platform UNIQUE (product_id, platform_id) -- Ensures unique pairs
);
