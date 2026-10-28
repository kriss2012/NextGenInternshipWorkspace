-- ==========================================================
-- SQLite Schema for Standalone Zero-Configuration Execution
-- ==========================================================

CREATE TABLE IF NOT EXISTS books (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    author TEXT NOT NULL,
    isbn TEXT UNIQUE NOT NULL,
    genre TEXT,
    total_copies INTEGER NOT NULL DEFAULT 1,
    available_copies INTEGER NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS members (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    phone TEXT,
    membership_date TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS borrow_records (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    book_id INTEGER NOT NULL,
    member_id INTEGER NOT NULL,
    borrow_date TEXT NOT NULL,
    return_date TEXT,
    status TEXT DEFAULT 'BORROWED',
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (member_id) REFERENCES members(id)
);

INSERT OR IGNORE INTO books (id, title, author, isbn, genre, total_copies, available_copies) VALUES
(1, 'Effective Java', 'Joshua Bloch', '978-0134685991', 'Programming', 5, 4),
(2, 'Clean Code', 'Robert C. Martin', '978-0132350884', 'Software Engineering', 4, 3),
(3, 'Java: The Complete Reference', 'Herbert Schildt', '978-1260440232', 'Reference', 6, 6),
(4, 'Design Patterns', 'Erich Gamma et al.', '978-0201633610', 'Architecture', 3, 3);

INSERT OR IGNORE INTO members (id, name, email, phone, membership_date) VALUES
(1, 'Aditya Rao', 'aditya.rao@nextgenhub.in', '9876543210', '2026-01-15'),
(2, 'Kavita Sen', 'kavita.sen@nextgenhub.in', '9876543211', '2026-02-01'),
(3, 'Manish Kumar', 'manish.k@nextgenhub.in', '9876543212', '2026-02-10');

INSERT OR IGNORE INTO borrow_records (id, book_id, member_id, borrow_date, return_date, status) VALUES
(1, 1, 1, '2026-03-01', NULL, 'BORROWED'),
(2, 2, 2, '2026-03-05', NULL, 'BORROWED');
