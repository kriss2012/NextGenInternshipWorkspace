package com.nextgen.week4.dao;

import com.nextgen.week4.model.Book;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) Interface for Book entities.
 */
public interface BookDao {
    Book save(Book book);
    boolean update(Book book);
    boolean delete(int id);
    Optional<Book> findById(int id);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findAll();
    List<Book> searchByTitleOrAuthor(String keyword);
    boolean decrementAvailableCopies(int bookId);
    boolean incrementAvailableCopies(int bookId);
}
