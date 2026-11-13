# GymBox — Backend Spring Boot

API REST de la salle de sport GymBox, construite avec **Spring Boot 3.3** + **Spring Security (JWT)** + **JPA**.

## ✅ Prérequis

- **Java 17** ou plus récent
- **Maven 3.9+**
- **MySQL** (via XAMPP ou standalone) — le port 3306 par défaut

## 🚀 Lancer le backend

1. **Démarrer MySQL** depuis XAMPP Control Panel → `Start` sur MySQL (et Apache si vous voulez phpMyAdmin).

2. **Configurer la connexion** dans `src/main/resources/application.properties` :
   - `spring.datasource.username` → `root`
   - `spring.datasource.password` → votre mot de passe (vide par défaut dans XAMPP)

3. **Lancer l'application** :

```bash
cd backend
mvn spring-boot:run
```

La base `gymbox` est créée automatiquement (option `createDatabaseIfNotExist=true`), les tables aussi (`ddl-auto=update`).

4. Les données de démonstration sont insérées automatiquement la première fois :

| Compte  | Email              | Mot de passe | Rôle   |
|---------|--------------------|--------------|--------|
| Admin   | `admin@gymbox.tn`  | `admin123`   | ADMIN  |
| Member  | `ahmed@gym.com`    | `123456`     | MEMBER |

> **Store** : le catalogue (21 produits) n'est inséré que si la table `products` est vide.
> Au démarrage, le seeder **corrige automatiquement** les images manquantes ou cassées
> (URLs externes / champs vides) vers les SVG locaux servis par le frontend :
> `/assets/images/products/…` pour les produits, `/assets/images/coaches/…` pour les coachs.
>
> Pour **recharger intégralement** le nouveau catalogue sur une base existante :
> ```sql
> TRUNCATE TABLE products;
> ```
> puis redémarrer l'application.

## 🔌 API principale

| Méthode | URL                          | Accès    | Description                |
|---------|------------------------------|----------|----------------------------|
| POST    | `/api/auth/register`         | Public   | Inscription                |
| POST    | `/api/auth/login`            | Public   | Connexion → retourne JWT   |
| GET     | `/api/auth/me`               | Connecté | Profil de l'utilisateur    |
| GET     | `/api/coaches`               | Public   | Liste des coachs           |
| GET     | `/api/products`              | Public   | Catalogue du store         |
| GET     | `/api/products/{id}`         | Public   | Détail d'un produit        |
| POST    | `/api/orders`                | Connecté | Créer une commande         |
| GET     | `/api/orders`                | Connecté | Mes commandes              |
| GET     | `/api/admin/members`         | ADMIN    | Liste des membres          |
| POST    | `/api/admin/members`         | ADMIN    | Ajouter un membre          |
| DELETE  | `/api/admin/members/{id}`    | ADMIN    | Supprimer un membre        |
| GET     | `/api/admin/stats`           | ADMIN    | Statistiques du dashboard  |

### Authentification
Envoyer le token JWT dans le header pour les routes protégées :

```
Authorization: Bearer <token>
```

### CORS
Le frontend Angular tourne sur `http://localhost:4200` et est autorisé automatiquement.