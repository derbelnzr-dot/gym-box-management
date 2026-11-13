-- ==============================================
-- GymBox - Script SQL optionnel (si vous préférez
-- créer la base manuellement depuis phpMyAdmin)
-- ==============================================

CREATE DATABASE IF NOT EXISTS gymbox CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gymbox;

-- Le reste des tables est créé automatiquement par Hibernate
-- (spring.jpa.hibernate.ddl-auto=update) au premier lancement.

-- --------------------------------------------------------------
-- REFRESH DU STORE (catalogue produits)
-- --------------------------------------------------------------
-- Les produits de démonstration sont insérés automatiquement par
-- DataSeeder UNIQUEMENT si la table products est vide.
-- Si vous voulez recharger le nouveau catalogue après une ancienne
-- exécution, exécutez la requête suivante puis relancez le backend :
--
--   TRUNCATE TABLE products;
--
-- --------------------------------------------------------------

DESCRIBE users;