package com.nextgen.week4.dao;

import com.nextgen.week4.model.BorrowRecord;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Robust DAO implementation for Borrow & Lending transactions.
 */
public class BorrowDaoImpl implements BorrowDao {
    private final Map<Integer, BorrowRecord> memoryStore = new ConcurrentHashMap<>();
    private final AtomicInteger idSequence = new AtomicInteger(10);
    private final BookDao bookDao;
    private final MemberDao memberDao;

    public BorrowDaoImpl(BookDao bookDao, MemberDao memberDao) {
        this.bookDao = bookDao;
        this.memberDao = memberDao;
        seedInitialRecords();
    }

    private void seedInitialRecords() {
        memoryStore.put(1, new BorrowRecord(1, 1, 1, "Effective Java", "Aditya Rao", "2026-03-01", null, "BORROWED"));
        memoryStore.put(2, new BorrowRecord(2, 2, 2, "Clean Code", "Kavita Sen", "2026-03-05", null, "BORROWED"));
    }

    @Override
    public BorrowRecord borrowBook(int bookId, int memberId) {
        var bookOpt = bookDao.findById(bookId);
        var memberOpt = memberDao.findById(memberId);

        if (bookOpt.isEmpty()) {
            throw new IllegalArgumentException("Book not found with ID: " + bookId);
        }
        if (memberOpt.isEmpty()) {
            throw new IllegalArgumentException("Member not found with ID: " + memberId);
        }

        var book = bookOpt.get();
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No available copies left for: " + book.getTitle());
        }

        bookDao.decrementAvailableCopies(bookId);

        int recordId = idSequence.incrementAndGet();
        BorrowRecord record = new BorrowRecord(
                recordId,
                bookId,
                memberId,
                book.getTitle(),
                memberOpt.get().getName(),
                LocalDate.now().toString(),
                null,
                "BORROWED"
        );
        memoryStore.put(recordId, record);
        return record;
    }

    @Override
    public boolean returnBook(int borrowRecordId) {
        BorrowRecord record = memoryStore.get(borrowRecordId);
        if (record == null) {
            return false;
        }
        if ("RETURNED".equalsIgnoreCase(record.getStatus())) {
            return false; // Already returned
        }

        record.setReturnDate(LocalDate.now().toString());
        record.setStatus("RETURNED");
        bookDao.incrementAvailableCopies(record.getBookId());
        return true;
    }

    @Override
    public Optional<BorrowRecord> findById(int id) {
        return Optional.ofNullable(memoryStore.get(id));
    }

    @Override
    public List<BorrowRecord> findActiveByMemberId(int memberId) {
        return memoryStore.values().stream()
                .filter(r -> r.getMemberId() == memberId && "BORROWED".equalsIgnoreCase(r.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowRecord> findAll() {
        return new ArrayList<>(memoryStore.values());
    }
}
