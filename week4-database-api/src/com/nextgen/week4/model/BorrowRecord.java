package com.nextgen.week4.model;

/**
 * BorrowRecord entity tracking lending transactions.
 */
public class BorrowRecord {
    private int id;
    private int bookId;
    private int memberId;
    private String bookTitle;
    private String memberName;
    private String borrowDate;
    private String returnDate;
    private String status; // BORROWED, RETURNED

    public BorrowRecord() {}

    public BorrowRecord(int id, int bookId, int memberId, String bookTitle, String memberName,
                        String borrowDate, String returnDate, String status) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.bookTitle = bookTitle;
        this.memberName = memberName;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public String getBorrowDate() { return borrowDate; }
    public void setBorrowDate(String borrowDate) { this.borrowDate = borrowDate; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toJson() {
        return String.format("{\"id\":%d,\"bookId\":%d,\"memberId\":%d,\"bookTitle\":\"%s\",\"memberName\":\"%s\",\"borrowDate\":\"%s\",\"returnDate\":%s,\"status\":\"%s\"}",
                id, bookId, memberId, escape(bookTitle), escape(memberName), borrowDate,
                (returnDate == null ? "null" : "\"" + returnDate + "\""), status);
    }

    private String escape(String s) {
        return s == null ? "" : s.replace("\"", "\\\"");
    }

    @Override
    public String toString() {
        return String.format("[#%d] Book: \"%s\" (ID %d) -> Member: %s (ID %d) | Borrowed: %s | Returned: %s | Status: %s",
                id, bookTitle, bookId, memberName, memberId, borrowDate, (returnDate == null ? "Active" : returnDate), status);
    }
}
