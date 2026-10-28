package com.nextgen.week4.dao;

import com.nextgen.week4.config.DatabaseConfig;
import com.nextgen.week4.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Robust DAO implementation for Books.
 * Uses JDBC PreparedStatements when MySQL is connected, and seamless in-memory store for standalone/testing.
 */
public class BookDaoImpl implements BookDao {
    private final Map<Integer, Book> memoryStore = new ConcurrentHashMap<>();
    private final AtomicInteger idSequence = new AtomicInteger(100);

    public BookDaoImpl() {
        seedInitialBooks();
    }

    private void seedInitialBooks() {
        saveInMemory(new Book(1, "Effective Java", "Joshua Bloch", "978-0134685991", "Programming", 5, 4));
        saveInMemory(new Book(2, "Clean Code", "Robert C. Martin", "978-0132350884", "Software Engineering", 4, 3));
        saveInMemory(new Book(3, "Java: The Complete Reference", "Herbert Schildt", "978-1260440232", "Reference", 6, 6));
        saveInMemory(new Book(4, "Design Patterns", "Erich Gamma et al.", "978-0201633610", "Architecture", 3, 3));
    }

    private void saveInMemory(Book b) {
        memoryStore.put(b.getId(), b);
    }

    @Override
    public Book save(Book book) {
        if (DatabaseConfig.testMySQLConnection()) {
            String sql = "INSERT INTO books (title, author, isbn, genre, total_copies, available_copies) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseConfig.getMySQLConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getIsbn());
                stmt.setString(4, book.getGenre());
                stmt.setInt(5, book.getTotalCopies());
                stmt.setInt(6, book.getAvailableCopies());
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        book.setId(rs.getInt(1));
                    }
                }
                memoryStore.put(book.getId(), book);
                return book;
            } catch (Exception e) {
                System.err.println("[JDBC MySQL Notice]: " + e.getMessage() + " (Operating in standalone mode)");
            }
        }

        if (book.getId() <= 0) {
            book.setId(idSequence.incrementAndGet());
        }
        memoryStore.put(book.getId(), book);
        return book;
    }

    @Override
    public boolean update(Book book) {
        if (DatabaseConfig.testMySQLConnection()) {
            String sql = "UPDATE books SET title=?, author=?, isbn=?, genre=?, total_copies=?, available_copies=? WHERE id=?";
            try (Connection conn = DatabaseConfig.getMySQLConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getIsbn());
                stmt.setString(4, book.getGenre());
                stmt.setInt(5, book.getTotalCopies());
                stmt.setInt(6, book.getAvailableCopies());
                stmt.setInt(7, book.getId());
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    memoryStore.put(book.getId(), book);
                    return true;
                }
            } catch (Exception ignored) {}
        }

        if (memoryStore.containsKey(book.getId())) {
            memoryStore.put(book.getId(), book);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (DatabaseConfig.testMySQLConnection()) {
            String sql = "DELETE FROM books WHERE id=?";
            try (Connection conn = DatabaseConfig.getMySQLConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    memoryStore.remove(id);
                    return true;
                }
            } catch (Exception ignored) {}
        }
        return memoryStore.remove(id) != null;
    }

    @Override
    public Optional<Book> findById(int id) {
        return Optional.ofNullable(memoryStore.get(id));
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return memoryStore.values().stream()
                .filter(b -> b.getIsbn().equalsIgnoreCase(isbn.trim()))
                .findFirst();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(memoryStore.values());
    }

    @Override
    public List<Book> searchByTitleOrAuthor(String keyword) {
        String lower = keyword.toLowerCase();
        return memoryStore.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(lower) || b.getAuthor().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized boolean decrementAvailableCopies(int bookId) {
        Book book = memoryStore.get(bookId);
        if (book != null && book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            update(book);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean incrementAvailableCopies(int bookId) {
        Book book = memoryStore.get(bookId);
        if (book != null && book.getAvailableCopies() < book.getTotalCopies()) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            update(book);
            return true;
        }
        return false;
    }
}
