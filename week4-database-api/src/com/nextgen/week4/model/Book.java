package com.nextgen.week4.model;

/**
 * Book entity model.
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private int totalCopies;
    private int availableCopies;

    public Book() {}

    public Book(int id, String title, String author, String isbn, String genre, int totalCopies, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public Book(String title, String author, String isbn, String genre, int totalCopies) {
        this(0, title, author, isbn, genre, totalCopies, totalCopies);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }

    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    public String toJson() {
        return String.format("{\"id\":%d,\"title\":\"%s\",\"author\":\"%s\",\"isbn\":\"%s\",\"genre\":\"%s\",\"totalCopies\":%d,\"availableCopies\":%d}",
                id, escape(title), escape(author), escape(isbn), escape(genre), totalCopies, availableCopies);
    }

    private String escape(String s) {
        return s == null ? "" : s.replace("\"", "\\\"");
    }

    @Override
    public String toString() {
        return String.format("[#%d] \"%s\" by %s | ISBN: %s | Genre: %s | Avail: %d/%d",
                id, title, author, isbn, genre, availableCopies, totalCopies);
    }
}
