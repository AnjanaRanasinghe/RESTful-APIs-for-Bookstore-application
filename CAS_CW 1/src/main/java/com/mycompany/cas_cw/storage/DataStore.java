/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cas_cw.storage;

import com.mycompany.cas_cw.model.Author;
import com.mycompany.cas_cw.model.Book;
import java.util.ArrayList;
import java.util.List;


public class DataStore {
    
    // data storage for books using ArrayList
    private static final List<Book> books = new ArrayList<>();
    private static int bookIdCounter = 1;
    
    public static List<Book> getBooks(){
        return books;
    }
     
    public static Book addBook(Book book){
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        // Ensure book_id is unique and set by DataStore
        book.setBook_id(bookIdCounter++);
        books.add(book);
        return book;
    }
    
    public static Book findBookById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Book ID must be positive");
        }
        return books.stream()
                    .filter(b -> b.getBook_id() == id)
                    .findFirst()
                    .orElse(null);
    }
    
    public static void updateBook(Book updatedBook) {
        if (updatedBook == null) {
            throw new IllegalArgumentException("Updated book cannot be null");
        }
        Book existingBook = findBookById(updatedBook.getBook_id());
        if (existingBook == null) {
            throw new IllegalArgumentException("Book with ID " + updatedBook.getBook_id() + " does not exist");
        }
        int index = books.indexOf(existingBook);
        books.set(index, updatedBook);
    }
    
    
    
    // data storage for authors using ArrayList
    private static final List<Author> authors = new ArrayList<>();
    private static int authorIdCounter = 1;
    
    public static List<Author> getAuthors(){
        return authors;
    }
     
    public static Author addAuthor(Author author){
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        // Ensure author_id is unique and set by DataStore
        author.setAuthorId(authorIdCounter++);
        authors.add(author);
        return author;
    }
    
    public static Author findAuthorById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Author ID must be positive");
        }
        return authors.stream()
                    .filter(b -> b.getAuthorId() == id)
                    .findFirst()
                    .orElse(null);
    }
    
    public static void updateAuthor(Author updatedAuthor) {
        if (updatedAuthor == null) {
            throw new IllegalArgumentException("Updated author cannot be null");
        }
        Author existingAuthor = findAuthorById(updatedAuthor.getAuthorId());
        if (existingAuthor == null) {
            throw new IllegalArgumentException("Book with ID " + updatedAuthor.getAuthorId() + " does not exist");
        }
        int index = authors.indexOf(existingAuthor);
        authors.set(index, updatedAuthor);
    }
    
}
