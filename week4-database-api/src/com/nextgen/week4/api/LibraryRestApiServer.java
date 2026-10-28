package com.nextgen.week4.api;

import com.nextgen.week4.dao.BookDao;
import com.nextgen.week4.dao.BorrowDao;
import com.nextgen.week4.dao.MemberDao;
import com.nextgen.week4.model.Book;
import com.nextgen.week4.model.BorrowRecord;
import com.nextgen.week4.model.Member;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Built-in Lightweight REST API Server for the Library Management System.
 * Uses Java Standard Library's com.sun.net.httpserver.HttpServer.
 * Zero external web framework dependencies needed!
 */
public class LibraryRestApiServer {
    private final int port;
    private final BookDao bookDao;
    private final MemberDao memberDao;
    private final BorrowDao borrowDao;
    private HttpServer server;

    public LibraryRestApiServer(int port, BookDao bookDao, MemberDao memberDao, BorrowDao borrowDao) {
        this.port = port;
        this.bookDao = bookDao;
        this.memberDao = memberDao;
        this.borrowDao = borrowDao;
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/api/health", new HealthHandler());
        server.createContext("/api/books", new BooksHandler());
        server.createContext("/api/members", new MembersHandler());
        server.createContext("/api/borrow", new BorrowHandler());
        server.createContext("/api/return", new ReturnHandler());

        server.setExecutor(null); // default executor
        server.start();
        System.out.println("==================================================");
        System.out.printf(" Library REST API Server running on port %d%n", port);
        System.out.printf(" Base URL: http://localhost:%d/api/%n", port);
        System.out.printf(" Health Check : http://localhost:%d/api/health%n", port);
        System.out.printf(" Books API    : http://localhost:%d/api/books%n", port);
        System.out.printf(" Members API  : http://localhost:%d/api/members%n", port);
        System.out.println("==================================================");
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("Library REST API Server stopped.");
        }
    }

    // Helper: read body string
    private static String readBody(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    // Helper: send json response
    private static void sendResponse(HttpExchange exchange, int statusCode, String responseJson) throws IOException {
        byte[] bytes = responseJson.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    // Helper: parse query parameters
    private static Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) return params;
        for (String param : query.split("&")) {
            String[] pair = param.split("=", 2);
            if (pair.length == 2) {
                params.put(pair[0].trim(), pair[1].trim());
            } else if (pair.length == 1) {
                params.put(pair[0].trim(), "");
            }
        }
        return params;
    }

    // --- HANDLERS ---

    private class HealthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendResponse(exchange, 204, "");
                return;
            }
            String json = "{\"status\":\"UP\",\"service\":\"Library Management API\",\"timestamp\":\"" + java.time.LocalDateTime.now() + "\"}";
            sendResponse(exchange, 200, json);
        }
    }

    private class BooksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod().toUpperCase();
            Map<String, String> query = parseQueryParams(exchange.getRequestURI().getQuery());

            if ("OPTIONS".equals(method)) {
                sendResponse(exchange, 204, "");
                return;
            }

            switch (method) {
                case "GET" -> {
                    if (query.containsKey("id")) {
                        try {
                            int id = Integer.parseInt(query.get("id"));
                            Optional<Book> b = bookDao.findById(id);
                            if (b.isPresent()) {
                                sendResponse(exchange, 200, SimpleJson.successResponse("Book found", b.get().toJson()));
                            } else {
                                sendResponse(exchange, 404, SimpleJson.errorResponse("Book not found with ID " + id, 404));
                            }
                        } catch (NumberFormatException e) {
                            sendResponse(exchange, 400, SimpleJson.errorResponse("Invalid book ID parameter", 400));
                        }
                    } else if (query.containsKey("search")) {
                        String term = query.get("search");
                        List<Book> results = bookDao.searchByTitleOrAuthor(term);
                        String arrayJson = "[" + results.stream().map(Book::toJson).collect(Collectors.joining(",")) + "]";
                        sendResponse(exchange, 200, SimpleJson.successResponse("Search results", arrayJson));
                    } else {
                        List<Book> list = bookDao.findAll();
                        String arrayJson = "[" + list.stream().map(Book::toJson).collect(Collectors.joining(",")) + "]";
                        sendResponse(exchange, 200, SimpleJson.successResponse("All books retrieved", arrayJson));
                    }
                }
                case "POST" -> {
                    String body = readBody(exchange);
                    Map<String, String> data = SimpleJson.parseObject(body);
                    String title = data.get("title");
                    String author = data.get("author");
                    String isbn = data.get("isbn");
                    String genre = data.getOrDefault("genre", "General");
                    int copies = Integer.parseInt(data.getOrDefault("copies", data.getOrDefault("totalCopies", "1")));

                    if (title == null || author == null || isbn == null) {
                        sendResponse(exchange, 400, SimpleJson.errorResponse("title, author, and isbn are required fields", 400));
                        return;
                    }

                    Book created = bookDao.save(new Book(0, title, author, isbn, genre, copies, copies));
                    sendResponse(exchange, 201, SimpleJson.successResponse("Book added successfully", created.toJson()));
                }
                case "PUT" -> {
                    String body = readBody(exchange);
                    Map<String, String> data = SimpleJson.parseObject(body);
                    if (!data.containsKey("id")) {
                        sendResponse(exchange, 400, SimpleJson.errorResponse("id is required to update a book", 400));
                        return;
                    }
                    int id = Integer.parseInt(data.get("id"));
                    Optional<Book> opt = bookDao.findById(id);
                    if (opt.isEmpty()) {
                        sendResponse(exchange, 404, SimpleJson.errorResponse("Book not found with ID " + id, 404));
                        return;
                    }
                    Book book = opt.get();
                    if (data.containsKey("title")) book.setTitle(data.get("title"));
                    if (data.containsKey("author")) book.setAuthor(data.get("author"));
                    if (data.containsKey("isbn")) book.setIsbn(data.get("isbn"));
                    if (data.containsKey("genre")) book.setGenre(data.get("genre"));
                    if (data.containsKey("totalCopies")) book.setTotalCopies(Integer.parseInt(data.get("totalCopies")));
                    if (data.containsKey("availableCopies")) book.setAvailableCopies(Integer.parseInt(data.get("availableCopies")));

                    bookDao.update(book);
                    sendResponse(exchange, 200, SimpleJson.successResponse("Book updated successfully", book.toJson()));
                }
                case "DELETE" -> {
                    if (!query.containsKey("id")) {
                        sendResponse(exchange, 400, SimpleJson.errorResponse("id parameter required for deletion", 400));
                        return;
                    }
                    int id = Integer.parseInt(query.get("id"));
                    boolean deleted = bookDao.delete(id);
                    if (deleted) {
                        sendResponse(exchange, 200, SimpleJson.successResponse("Book ID " + id + " deleted", "null"));
                    } else {
                        sendResponse(exchange, 404, SimpleJson.errorResponse("Book not found", 404));
                    }
                }
                default -> sendResponse(exchange, 405, SimpleJson.errorResponse("Method Not Allowed", 405));
            }
        }
    }

    private class MembersHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendResponse(exchange, 204, "");
                return;
            }
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                List<Member> list = memberDao.findAll();
                String arrayJson = "[" + list.stream().map(Member::toJson).collect(Collectors.joining(",")) + "]";
                sendResponse(exchange, 200, SimpleJson.successResponse("All members retrieved", arrayJson));
            } else if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = readBody(exchange);
                Map<String, String> data = SimpleJson.parseObject(body);
                String name = data.get("name");
                String email = data.get("email");
                String phone = data.getOrDefault("phone", "");
                if (name == null || email == null) {
                    sendResponse(exchange, 400, SimpleJson.errorResponse("name and email required", 400));
                    return;
                }
                Member member = memberDao.save(new Member(name, email, phone));
                sendResponse(exchange, 201, SimpleJson.successResponse("Member registered", member.toJson()));
            } else {
                sendResponse(exchange, 405, SimpleJson.errorResponse("Method Not Allowed", 405));
            }
        }
    }

    private class BorrowHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendResponse(exchange, 204, "");
                return;
            }
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = readBody(exchange);
                Map<String, String> data = SimpleJson.parseObject(body);
                try {
                    int bookId = Integer.parseInt(data.get("bookId"));
                    int memberId = Integer.parseInt(data.get("memberId"));
                    BorrowRecord record = borrowDao.borrowBook(bookId, memberId);
                    sendResponse(exchange, 201, SimpleJson.successResponse("Book borrowed successfully", record.toJson()));
                } catch (Exception e) {
                    sendResponse(exchange, 400, SimpleJson.errorResponse(e.getMessage(), 400));
                }
            } else if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                List<BorrowRecord> records = borrowDao.findAll();
                String arrayJson = "[" + records.stream().map(BorrowRecord::toJson).collect(Collectors.joining(",")) + "]";
                sendResponse(exchange, 200, SimpleJson.successResponse("Borrow records", arrayJson));
            } else {
                sendResponse(exchange, 405, SimpleJson.errorResponse("Method Not Allowed", 405));
            }
        }
    }

    private class ReturnHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendResponse(exchange, 204, "");
                return;
            }
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = readBody(exchange);
                Map<String, String> data = SimpleJson.parseObject(body);
                try {
                    int recordId = Integer.parseInt(data.get("recordId"));
                    boolean success = borrowDao.returnBook(recordId);
                    if (success) {
                        sendResponse(exchange, 200, SimpleJson.successResponse("Book returned successfully", "null"));
                    } else {
                        sendResponse(exchange, 400, SimpleJson.errorResponse("Return failed: Record not found or already returned", 400));
                    }
                } catch (Exception e) {
                    sendResponse(exchange, 400, SimpleJson.errorResponse(e.getMessage(), 400));
                }
            } else {
                sendResponse(exchange, 405, SimpleJson.errorResponse("Method Not Allowed", 405));
            }
        }
    }
}
