package com.nextgen.week4.dao;

import com.nextgen.week4.model.BorrowRecord;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) Interface for Borrow & Lending transactions.
 */
public interface BorrowDao {
    BorrowRecord borrowBook(int bookId, int memberId);
    boolean returnBook(int borrowRecordId);
    Optional<BorrowRecord> findById(int id);
    List<BorrowRecord> findActiveByMemberId(int memberId);
    List<BorrowRecord> findAll();
}
