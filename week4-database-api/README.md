# Week 4: Java with Database and API Integration

**Next Gen Software Hub Pvt. Ltd. — Internship Program**

---

## 📌 Syllabus Overview
1. **JDBC (Java Database Connectivity)**:
   - `Connection`, `PreparedStatement`, `ResultSet`, and `Statement`.
   - SQL Injection prevention via parameterized queries.
2. **Connecting Java to MySQL**:
   - `schema.sql` defining relational tables: `books`, `members`, and `borrow_records`.
   - Configurable credentials in `DatabaseConfig.java`.
   - Seamless built-in standalone provider if MySQL server is not locally running.
3. **Building a Simple REST API with Java**:
   - Zero external web framework dependencies: uses Java's built-in `com.sun.net.httpserver.HttpServer`.
   - Handles `GET`, `POST`, `PUT`, `DELETE` methods, query parameters, JSON payload parsing, and standard HTTP response status codes (`200 OK`, `201 Created`, `400 Bad Request`, `404 Not Found`).
4. **Final Project: Library Management System**:
   - End-to-end architecture (Model -> DAO -> Service/Controller -> CLI & REST API).
   - Automated lending & returning workflow with real-time inventory count tracking.

---

## 📂 Source Structure
- `sql/`
  - `mysql_schema.sql` (Full schema for MySQL)
  - `sqlite_schema.sql` (Alternative standalone schema)
- `src/com/nextgen/week4/`
  - `config/DatabaseConfig.java`
  - `model/Book.java`
  - `model/Member.java`
  - `model/BorrowRecord.java`
  - `dao/BookDao.java` & `BookDaoImpl.java`
  - `dao/MemberDao.java` & `MemberDaoImpl.java`
  - `dao/BorrowDao.java` & `BorrowDaoImpl.java`
  - `api/SimpleJson.java`
  - `api/LibraryRestApiServer.java`
  - `LibraryApp.java`
- `LibraryManagementAPI.postman_collection.json` (Directly importable into Postman!)

---

## 🌐 REST API Endpoints Overview (Port 8080)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/health` | Health check & service status |
| `GET` | `/api/books` | Retrieve all books |
| `GET` | `/api/books?id={id}` | Retrieve specific book by ID |
| `GET` | `/api/books?search={query}` | Search books by title or author |
| `POST` | `/api/books` | Add new book (JSON body) |
| `PUT` | `/api/books` | Update book details (JSON body with `id`) |
| `DELETE` | `/api/books?id={id}` | Delete book |
| `GET` | `/api/members` | Retrieve all registered members |
| `POST` | `/api/members` | Register new member (JSON body) |
| `POST` | `/api/borrow` | Borrow book (`{"bookId": 1, "memberId": 2}`) |
| `POST` | `/api/return` | Return book (`{"recordId": 1}`) |

---

## 🚀 How to Compile & Run (CLI)

```powershell
# From the repository root (c:\D Drive\f):

# 1. Compile all Week 4 files
javac -d week4-database-api/bin $(Get-ChildItem -Path week4-database-api/src -Recurse -Filter *.java | ForEach-Object { $_.FullName })

# 2. Run the Library Management Application & REST API Server
java -cp week4-database-api/bin com.nextgen.week4.LibraryApp
```

### Testing the REST API via cURL or PowerShell:
```powershell
# In a separate terminal while LibraryApp is running:

# Health check
curl http://localhost:8080/api/health

# Get books
curl http://localhost:8080/api/books

# Add a book
Invoke-RestMethod -Uri "http://localhost:8080/api/books" -Method Post -Body '{"title":"Clean Architecture","author":"Robert Martin","isbn":"978-0134494166","genre":"Software"}' -ContentType "application/json"
```
