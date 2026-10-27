-- Base de datos independiente para la evidencia GA7-220501096-AA3-EV01.
CREATE DATABASE IF NOT EXISTS medisoft_aa3
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE medisoft_aa3;

CREATE TABLE IF NOT EXISTS citas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    documento_paciente VARCHAR(30) NOT NULL,
    nombre_paciente VARCHAR(120) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    medico VARCHAR(120) NOT NULL,
    fecha_hora DATETIME NOT NULL,
    estado ENUM('PROGRAMADA', 'ATENDIDA', 'CANCELADA') NOT NULL DEFAULT 'PROGRAMADA',
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_citas_documento (documento_paciente),
    INDEX idx_citas_fecha_hora (fecha_hora)
);

-- Ejecutar aparte desde una cuenta administradora, definiendo una clave propia:
-- CREATE USER 'medisoft_aa3_app'@'localhost' IDENTIFIED BY 'CAMBIAR_POR_CLAVE_LOCAL';
-- GRANT SELECT, INSERT, UPDATE, DELETE ON medisoft_aa3.* TO 'medisoft_aa3_app'@'localhost';
-- FLUSH PRIVILEGES;
