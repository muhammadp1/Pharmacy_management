-- Drop old composite key if it exists
ALTER TABLE product_platforms
DROP CONSTRAINT IF EXISTS product_platforms_pkey;

-- Add new id column


-- Ensure foreign keys exist
ALTER TABLE product_platforms
ADD CONSTRAINT fk_product
FOREIGN KEY (product_id)
REFERENCES products(id)
ON DELETE CASCADE;

ALTER TABLE product_platforms
ADD CONSTRAINT fk_platform
FOREIGN KEY (platform_id)
REFERENCES platforms(id)
ON DELETE CASCADE;