-- Criação do banco de dados
CREATE DATABASE website_system;

-- Selecionar o banco de dados
USE website_system;

-- Tabela para usuários
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password_hash CHAR(32) NOT NULL, -- MD5 hash para senha
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela para registros de IA utilizados no sistema
CREATE TABLE ai_services (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(255) NOT NULL,
    service_url VARCHAR(255) NOT NULL
);

-- Tabela para armazenar os logs de IA
CREATE TABLE ai_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    service_id INT,
    request_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    result TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (service_id) REFERENCES ai_services(service_id)
);

-- Inserir um serviço de IA (exemplo)
INSERT INTO ai_services (service_name, service_url) 
VALUES ('Azure Image Analysis', 'https://portal.vision.cognitive.azure.com/gallery/imageanalysis');

-- Exemplo de uso de Prepared Statements para evitar SQL Injection
PREPARE stmt FROM 'SELECT * FROM users WHERE username = ?';
SET @username = 'user_input';
EXECUTE stmt USING @username;
DEALLOCATE PREPARE stmt;

