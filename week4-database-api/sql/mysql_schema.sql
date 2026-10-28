-- ==========================================================
-- Week 4 Final Project: Library Management System
-- Next Gen Software Hub Pvt. Ltd. - Internship Task
-- MySQL Database Schema and Seed Data
-- ==========================================================

CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

-- 1. Books Table
DROP TABLE IF EXISTS borrow_records;
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS books;

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    genre VARCHAR(50),
    total_copies INT NOT NULL DEFAULT 1,
    available_copies INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Members Table
CREATE TABLE members (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    membership_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Borrow Records Table
CREATE TABLE borrow_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20) DEFAULT 'BORROWED', -- BORROWED, RETURNED
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE
);

-- Seed Initial Data
INSERT INTO books (title, author, isbn, genre, total_copies, available_copies) VALUES
('Effective Java', 'Joshua Bloch', '978-0134685991', 'Programming', 5, 4),
('Clean Code', 'Robert C. Martin', '978-0132350884', 'Software Engineering', 4, 3),
('Java: The Complete Reference', 'Herbert Schildt', '978-1260440232', 'Reference', 6, 6),
('Design Patterns', 'Erich Gamma et al.', '978-0201633610', 'Architecture', 3, 3);

INSERT INTO members (name, email, phone, membership_date) VALUES
('Aditya Rao', 'aditya.rao@nextgenhub.in', '9876543210', '2026-01-15'),
('Kavita Sen', 'kavita.sen@nextgenhub.in', '9876543211', '2026-02-01'),
('Manish Kumar', 'manish.k@nextgenhub.in', '9876543212', '2026-02-10');

INSERT INTO borrow_records (book_id, member_id, borrow_date, return_date, status) VALUES
(1, 1, '2026-03-01', NULL, 'BORROWED'),
(2, 2, '2026-03-05', NULL, 'BORROWED');
