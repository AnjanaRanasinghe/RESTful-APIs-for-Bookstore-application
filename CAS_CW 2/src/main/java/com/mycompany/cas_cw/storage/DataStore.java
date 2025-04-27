/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cas_cw.storage;

import com.mycompany.cas_cw.model.Author;
import com.mycompany.cas_cw.model.Book;
import com.mycompany.cas_cw.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class DataStore {
    
    // data storage for books using ArrayList
    private static final List<Book> books = new ArrayList<>();
    private static int bookIdCounter = 1;   // Counter to generate unique IDs for each book
    
    public static List<Book> getBooks(){
        return books;
    }
     
    // Add a new book to the DataStore
    public static Book addBook(Book book){
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        // Ensure book_id is unique and set by DataStore
        book.setBook_id(bookIdCounter++);
        books.add(book);
        return book;
    }
    
    // Find a book by its ID
    public static Book findBookById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Book ID must be positive");
        }
        // Search the book list for a matching ID
        return books.stream()
                    .filter(b -> b.getBook_id() == id)
                    .findFirst()
                    .orElse(null);  // Return null if not found
    }
    
    // Update an existing book in the DataStore
    public static void updateBook(Book updatedBook) {
        if (updatedBook == null) {
            throw new IllegalArgumentException("Updated book cannot be null");
        }
        // Find the existing book
        Book existingBook = findBookById(updatedBook.getBook_id());
        if (existingBook == null) {
            throw new IllegalArgumentException("Book with ID " + updatedBook.getBook_id() + " does not exist");
        }
        // Replace the old book entry with the updated one
        int index = books.indexOf(existingBook);
        books.set(index, updatedBook);
    }
    
    // Get all books by a specific author ID
    public static List<Book> getBooksByAuthorId(int authorId) {
        if (authorId <= 0) {
            throw new IllegalArgumentException("Author ID must be positive");
        }
        return books.stream()
                    .filter(book -> book.getAuthorId() == authorId)
                    .collect(Collectors.toList());
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
    
    
    
    
    // data storage for customer using ArrayList
    private static final List<Customer> customers = new ArrayList<>();
    private static int customerIdCounter = 1;   // Counter to generate unique IDs for each Customer
    
    public static List<Customer> getCustomers(){
        return customers;
    }
     
    // Add a new customer to the DataStore
    public static Customer addCustomer(Customer customer){
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        // Ensure customerId is unique and set by DataStore
        customer.setCustomerId(customerIdCounter++);
        customers.add(customer);
        return customer;
    }
    
    // Find a customer by its ID
    public static Customer findCustomerById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive");
        }
        // Search the customers list for a matching ID
        return customers.stream()
                    .filter(b -> b.getCustomerId() == id)
                    .findFirst()
                    .orElse(null);  // Return null if not found
    }
    
    // Update an existing customer in the DataStore
    public static void updateCustomer(Customer updatedCustomer) {
        if (updatedCustomer == null) {
            throw new IllegalArgumentException("Updated Customer cannot be null");
        }
        // Find the existing customer
        Customer existingCustomer = findCustomerById(updatedCustomer.getCustomerId());
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Customer with ID " + updatedCustomer.getCustomerId()+ " does not exist");
        }
        // Replace the old Customer entry with the updated one
        int index = customers.indexOf(existingCustomer);
        customers.set(index, updatedCustomer);
    }
    
}
