-- ============================================
-- AIRLINE RESERVATION SYSTEM - MYSQL SCHEMA
-- ============================================

CREATE DATABASE IF NOT EXISTS airline_db;
USE airline_db;

-- ============================================
-- USERS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name   VARCHAR(100)        NOT NULL,
    email       VARCHAR(150)        NOT NULL UNIQUE,
    password    VARCHAR(255)        NOT NULL,
    phone       VARCHAR(20),
    role        ENUM('USER','ADMIN') DEFAULT 'USER',
    created_at  DATETIME            DEFAULT CURRENT_TIMESTAMP,
    is_active   BOOLEAN             DEFAULT TRUE
);

-- ============================================
-- FLIGHTS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS flights (
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    flight_number    VARCHAR(20)         NOT NULL UNIQUE,
    airline_name     VARCHAR(100)        NOT NULL,
    source_city      VARCHAR(100)        NOT NULL,
    source_code      VARCHAR(5)          NOT NULL,
    destination_city VARCHAR(100)        NOT NULL,
    destination_code VARCHAR(5)          NOT NULL,
    departure_time   DATETIME            NOT NULL,
    arrival_time     DATETIME            NOT NULL,
    duration_minutes INT                 NOT NULL,
    base_price       DECIMAL(10, 2)      NOT NULL,
    status           ENUM('SCHEDULED','DELAYED','CANCELLED','COMPLETED') DEFAULT 'SCHEDULED',
    total_economy_seats    INT DEFAULT 60,
    total_business_seats   INT DEFAULT 20,
    total_first_seats      INT DEFAULT 10,
    created_at       DATETIME            DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- SEATS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS seats (
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    flight_id    BIGINT              NOT NULL,
    seat_number  VARCHAR(10)         NOT NULL,
    class_type   ENUM('Economy','Business','First') NOT NULL,
    is_booked    BOOLEAN             DEFAULT FALSE,
    price        DECIMAL(10, 2)      NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE,
    UNIQUE KEY unique_seat (flight_id, seat_number)
);

-- ============================================
-- BOOKINGS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS bookings (
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_ref      VARCHAR(20)         NOT NULL UNIQUE,
    user_id          BIGINT              NOT NULL,
    flight_id        BIGINT              NOT NULL,
    seat_id          BIGINT              NOT NULL,
    passenger_name   VARCHAR(100)        NOT NULL,
    passenger_email  VARCHAR(150)        NOT NULL,
    passenger_phone  VARCHAR(20),
    class_type       ENUM('Economy','Business','First') NOT NULL,
    amount_paid      DECIMAL(10, 2)      NOT NULL,
    status           ENUM('CONFIRMED','CANCELLED','PENDING') DEFAULT 'CONFIRMED',
    booked_at        DATETIME            DEFAULT CURRENT_TIMESTAMP,
    cancelled_at     DATETIME,
    FOREIGN KEY (user_id)   REFERENCES users(id),
    FOREIGN KEY (flight_id) REFERENCES flights(id),
    FOREIGN KEY (seat_id)   REFERENCES seats(id)
);

-- ============================================
-- SEED ADMIN USER (password: admin123)
-- ============================================
INSERT IGNORE INTO users (full_name, email, password, role)
VALUES ('System Admin', 'admin@airreserve.com',
        '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ADMIN');

-- ============================================
-- SAMPLE FLIGHTS
-- ============================================
INSERT IGNORE INTO flights
    (flight_number, airline_name, source_city, source_code, destination_city, destination_code,
     departure_time, arrival_time, duration_minutes, base_price, status)
VALUES
    ('AR101', 'AirReserve', 'New Delhi', 'DEL', 'Mumbai', 'BOM',
     DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 2 DAY) + INTERVAL 125 MINUTE, 125, 4500.00, 'SCHEDULED'),
    ('AR202', 'AirReserve', 'Mumbai', 'BOM', 'Bangalore', 'BLR',
     DATE_ADD(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 3 DAY) + INTERVAL 100 MINUTE, 100, 3800.00, 'SCHEDULED'),
    ('AR303', 'AirReserve', 'New Delhi', 'DEL', 'Kolkata', 'CCU',
     DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL 145 MINUTE, 145, 5200.00, 'SCHEDULED'),
    ('AR404', 'AirReserve', 'Bangalore', 'BLR', 'Chennai', 'MAA',
     DATE_ADD(NOW(), INTERVAL 4 DAY), DATE_ADD(NOW(), INTERVAL 4 DAY) + INTERVAL 60 MINUTE, 60, 2900.00, 'SCHEDULED'),
    ('AR505', 'AirReserve', 'Hyderabad', 'HYD', 'New Delhi', 'DEL',
     DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 2 DAY) + INTERVAL 130 MINUTE, 130, 4700.00, 'SCHEDULED');
