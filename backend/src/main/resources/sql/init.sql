-- ============================================================
-- GymBox Database Initialization Script
-- ============================================================

CREATE DATABASE IF NOT EXISTS gymbox_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE gymbox_db;

-- Users table
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  phone VARCHAR(20),
  photo VARCHAR(255),
  role VARCHAR(20) NOT NULL DEFAULT 'CLIENT',
  is_active BOOLEAN DEFAULT TRUE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Members table
CREATE TABLE IF NOT EXISTS members (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL UNIQUE,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  date_of_birth DATE,
  gender VARCHAR(10),
  address VARCHAR(255),
  city VARCHAR(50),
  emergency_contact VARCHAR(20),
  notes TEXT,
  is_active BOOLEAN DEFAULT TRUE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Coaches table
CREATE TABLE IF NOT EXISTS coaches (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL UNIQUE,
  specialty VARCHAR(100),
  bio TEXT,
  years_experience INT,
  certifications VARCHAR(255),
  is_available BOOLEAN DEFAULT TRUE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Subscriptions table
CREATE TABLE IF NOT EXISTS subscriptions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  member_id BIGINT NOT NULL,
  plan_name VARCHAR(100),
  price DECIMAL(10,2),
  start_date DATE,
  end_date DATE,
  duration_months INT,
  status VARCHAR(20) DEFAULT 'ACTIVE',
  description TEXT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE
);

-- Sessions table
CREATE TABLE IF NOT EXISTS sessions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  description TEXT,
  coach_id BIGINT,
  session_date DATE,
  start_time TIME,
  end_time TIME,
  max_participants INT,
  room VARCHAR(100),
  level VARCHAR(50),
  is_active BOOLEAN DEFAULT TRUE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (coach_id) REFERENCES coaches(id) ON DELETE SET NULL
);

-- Plannings table
CREATE TABLE IF NOT EXISTS plannings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  day_of_week VARCHAR(20),
  session_id BIGINT NOT NULL,
  is_active BOOLEAN DEFAULT TRUE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE
);

-- Payments table
CREATE TABLE IF NOT EXISTS payments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  member_id BIGINT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  payment_date DATE,
  payment_method VARCHAR(50),
  transaction_id VARCHAR(100),
  status VARCHAR(20) DEFAULT 'PENDING',
  notes TEXT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE
);

-- Invoices table
CREATE TABLE IF NOT EXISTS invoices (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  invoice_number VARCHAR(50) NOT NULL UNIQUE,
  member_id BIGINT NOT NULL,
  subscription_id BIGINT,
  amount DECIMAL(10,2) NOT NULL,
  tax DECIMAL(10,2),
  total DECIMAL(10,2),
  issue_date DATE,
  due_date DATE,
  status VARCHAR(20) DEFAULT 'PENDING',
  notes TEXT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE,
  FOREIGN KEY (subscription_id) REFERENCES subscriptions(id) ON DELETE SET NULL
);

-- ============================================================
-- Seed Data: Admin User (password: admin123)
-- ============================================================
INSERT INTO users (username, email, password, role, is_active)
VALUES ('admin', 'admin@gymbox.tn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', TRUE);

-- Seed Data: Sample Coach Users
INSERT INTO users (username, email, password, role, is_active) VALUES
('ahmed', 'ahmed@gym.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'COACH', TRUE),
('sara', 'sara@gym.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'COACH', TRUE),
('karim', 'karim@gym.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'COACH', TRUE),
('anis', 'anis@gym.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'COACH', TRUE);

-- Seed Data: Coaches
INSERT INTO coaches (user_id, specialty, bio, years_experience, certifications, is_available) VALUES
(2, 'Musculation', 'Coach certifié en musculation avec 8 ans d''expérience', 8, 'Certifié Fitness Pro', TRUE),
(3, 'Yoga & Stretching', 'Experte en yoga et bien-être, certifiée internationalement', 6, 'RYT 500 Yoga Alliance', TRUE),
(4, 'Cross Training', 'Spécialiste en cross training et préparation physique intensive', 10, 'CrossFit Level 3', TRUE),
(5, 'Boxe & Fitness', 'Ancien boxeur professionnel, coach fitness depuis 12 ans', 12, 'BPJEPS Boxe', TRUE);
