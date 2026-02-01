-- Script de inicialización de la base de datos con datos de ejemplo
-- Este archivo se ejecuta automáticamente cuando MySQL se crea por primera vez

USE ionix_db;

-- Crear la tabla si no existe (por si acaso)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar datos de ejemplo
INSERT INTO users (name, username, email, phone, created_at) VALUES
('Juan Pérez', 'juanperez', 'juan.perez@example.com', '+56912345678', NOW()),
('María González', 'mariagonzalez', 'maria.gonzalez@example.com', '+56987654321', NOW()),
('Carlos Rodríguez', 'carlosrodriguez', 'carlos.rodriguez@example.com', '+56911223344', NOW()),
('Ana Martínez', 'anamartinez', 'ana.martinez@example.com', '+56955667788', NOW()),
('Luis Fernández', 'luisfernandez', 'luis.fernandez@example.com', '+56999887766', NOW()),
('Laura Sánchez', 'laurasanchez', 'laura.sanchez@example.com', '+56944332211', NOW()),
('Pedro López', 'pedrolopez', 'pedro.lopez@example.com', '+56933221100', NOW()),
('Sofía Ramírez', 'sofiaramirez', 'sofia.ramirez@example.com', '+56922110099', NOW()),
('Diego Torres', 'diegotorres', 'diego.torres@example.com', '+56911009988', NOW()),
('Isabella Morales', 'isabellamorales', 'isabella.morales@example.com', '+56900998877', NOW())
ON DUPLICATE KEY UPDATE name=name;

-- Verificar los datos insertados
SELECT COUNT(*) as total_usuarios FROM users;
