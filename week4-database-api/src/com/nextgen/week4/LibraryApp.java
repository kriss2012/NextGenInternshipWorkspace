package com.nextgen.week4;

import com.nextgen.week4.api.LibraryRestApiServer;
import com.nextgen.week4.config.DatabaseConfig;
import com.nextgen.week4.dao.BookDao;
import com.nextgen.week4.dao.BookDaoImpl;
import com.nextgen.week4.dao.BorrowDao;
import com.nextgen.week4.dao.BorrowDaoImpl;
import com.nextgen.week4.dao.MemberDao;
import com.nextgen.week4.dao.MemberDaoImpl;
import com.nextgen.week4.model.Book;
import com.nextgen.week4.model.BorrowRecord;
import com.nextgen.week4.model.Member;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Master Application for Week 4: Library Management System.
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 *
 * Integrates:
 * - JDBC Connectivity and DAO Architecture
 * - Interactive Command Line System
 * - Standalone HTTP REST API Server (port 8080)
 */
public class LibraryApp {
    private static final BookDao bookDao = new BookDaoImpl();
    private static final MemberDao memberDao = new MemberDaoImpl();
    private static final BorrowDao borrowDao = new BorrowDaoImpl(bookDao, memberDao);
    private static final Scanner scanner = new Scanner(System.in);
    private static LibraryRestApiServer apiServer = null;

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("       LIBRARY MANAGEMENT SYSTEM (JDBC & REST)     ");
        System.out.println("    Next Gen Software Hub - Week 4 Final Project  ");
        System.out.println("==================================================");

        // Check MySQL Database Status
        boolean mysqlLive = DatabaseConfig.testMySQLConnection();
        if (mysqlLive) {
            System.out.println("[Database Status] -> Connected to live MySQL Server!");
        } else {
            System.out.println("[Database Status] -> Local MySQL not detected. Running high-performance in-memory JDBC provider.");
            System.out.println("                     (To connect to MySQL, see sql/mysql_schema.sql instructions in README)");
        }

        // Auto-start REST API Server on port 8080
        try {
            apiServer = new LibraryRestApiServer(8080, bookDao, memberDao, borrowDao);
            apiServer.start();
        } catch (IOException e) {
            System.out.println("Could not auto-start REST API Server: " + e.getMessage());
        }

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Select an option (1-9): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> listBooks();
                case "2" -> addNewBook();
                case "3" -> searchBooks();
                case "4" -> listMembers();
                case "5" -> registerMember();
                case "6" -> borrowBook();
                case "7" -> returnBook();
                case "8" -> viewActiveBorrows();
                case "9" -> {
                    System.out.println("Shutting down REST API server and exiting...");
                    if (apiServer != null) {
                        apiServer.stop();
                    }
                    running = false;
                }
                default -> System.out.println("Invalid selection! Please enter 1-9.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n---------------- MAIN MENU ----------------");
        System.out.println(" 1. View All Books");
        System.out.println(" 2. Add New Book");
        System.out.println(" 3. Search Books (Title / Author)");
        System.out.println(" 4. View All Members");
        System.out.println(" 5. Register New Member");
        System.out.println(" 6. Borrow a Book");
        System.out.println(" 7. Return a Book");
        System.out.println(" 8. View Borrow Transaction History");
        System.out.println(" 9. Exit System");
        System.out.println("-------------------------------------------");
    }

    private static void listBooks() {
        List<Book> books = bookDao.findAll();
        System.out.println("\n---------------- BOOK CATALOG ----------------");
        if (books.isEmpty()) {
            System.out.println("No books in catalog.");
        } else {
            System.out.printf("%-4s %-30s %-20s %-16s %-12s %-6s%n",
                    "ID", "Title", "Author", "ISBN", "Genre", "Copies");
            System.out.println("-----------------------------------------------------------------------------------------");
            for (Book b : books) {
                System.out.printf("%-4d %-30s %-20s %-16s %-12s %d/%d%n",
                        b.getId(), truncate(b.getTitle(), 29), truncate(b.getAuthor(), 19),
                        b.getIsbn(), b.getGenre(), b.getAvailableCopies(), b.getTotalCopies());
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    private static void addNewBook() {
        try {
            System.out.println("\n--- Add New Book ---");
            System.out.print("Enter Title: ");
            String title = scanner.nextLine().trim();

            System.out.print("Enter Author: ");
            String author = scanner.nextLine().trim();

            System.out.print("Enter ISBN: ");
            String isbn = scanner.nextLine().trim();

            System.out.print("Enter Genre: ");
            String genre = scanner.nextLine().trim();

            System.out.print("Enter Number of Copies: ");
            int copies = Integer.parseInt(scanner.nextLine().trim());

            Book saved = bookDao.save(new Book(0, title, author, isbn, genre, copies, copies));
            System.out.println("Book registered successfully with ID: " + saved.getId());
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    private static void searchBooks() {
        System.out.print("\nEnter keyword to search (Title or Author): ");
        String kw = scanner.nextLine().trim();
        List<Book> list = bookDao.searchByTitleOrAuthor(kw);
        System.out.printf("Results for '%s' (%d books found):%n", kw, list.size());
        for (Book b : list) {
            System.out.println("  " + b);
        }
    }

    private static void listMembers() {
        List<Member> members = memberDao.findAll();
        System.out.println("\n---------------- REGISTERED MEMBERS ----------------");
        for (Member m : members) {
            System.out.println("  " + m);
        }
        System.out.println("----------------------------------------------------");
    }

    private static void registerMember() {
        try {
            System.out.println("\n--- Register Member ---");
            System.out.print("Enter Full Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Phone: ");
            String phone = scanner.nextLine().trim();

            Member m = memberDao.save(new Member(name, email, phone));
            System.out.println("Member registered successfully with ID: " + m.getId());
        } catch (Exception e) {
            System.out.println("Error registering member: " + e.getMessage());
        }
    }

    private static void borrowBook() {
        try {
            System.out.println("\n--- Borrow Book ---");
            System.out.print("Enter Book ID: ");
            int bookId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Member ID: ");
            int memberId = Integer.parseInt(scanner.nextLine().trim());

            BorrowRecord record = borrowDao.borrowBook(bookId, memberId);
            System.out.printf("Success! Record #%d created. %s borrowed \"%s\"%n",
                    record.getId(), record.getMemberName(), record.getBookTitle());
        } catch (Exception e) {
            System.out.println("Borrow failed: " + e.getMessage());
        }
    }

    private static void returnBook() {
        try {
            System.out.println("\n--- Return Book ---");
            System.out.print("Enter Borrow Transaction ID: ");
            int txId = Integer.parseInt(scanner.nextLine().trim());

            boolean ok = borrowDao.returnBook(txId);
            if (ok) {
                System.out.println("Book returned successfully! Inventory copy restored.");
            } else {
                System.out.println("Failed to return: Transaction ID not found or already returned.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewActiveBorrows() {
        List<BorrowRecord> records = borrowDao.findAll();
        System.out.println("\n---------------- BORROW TRANSACTION LOG ----------------");
        for (BorrowRecord r : records) {
            System.out.println("  " + r);
        }
        System.out.println("---------------------------------------------------------");
    }

    private static String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 3) + "...";
    }
}
