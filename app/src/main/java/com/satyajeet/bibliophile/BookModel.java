package com.satyajeet.bibliophile;

public class BookModel {

    private String bookName;
    private String authorName;
    private String bookImage;
    private String description;

    public BookModel(String bookName, String authorName, String bookImage, String description) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookImage = bookImage;
        this.description = description;
    }

    public BookModel(String bookName, String authorName, String bookImage) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookImage = bookImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }
}
