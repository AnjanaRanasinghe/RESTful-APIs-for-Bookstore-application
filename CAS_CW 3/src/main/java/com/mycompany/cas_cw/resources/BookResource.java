/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cas_cw.resources;

import com.mycompany.cas_cw.exceptions.BookNotFoundException;
import com.mycompany.cas_cw.exceptions.InvalidInputException;
import com.mycompany.cas_cw.model.Book;
import com.mycompany.cas_cw.storage.DataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

// Base path for all book-related endpoints
@Path("/books")
public class BookResource {

    // Create a new book with details  
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        // Basic validation for inputs
        if (book == null) {
            throw new InvalidInputException("Book cannot be null");
        }
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new InvalidInputException("Title is required");
        }
        if (book.getPublicationYear() > 2025) {
            throw new InvalidInputException("Publication year cannot be in the future");
        }
        if (book.getPublicationYear() < 0) {
            throw new InvalidInputException("Publication year cannot be negative");
        }
        if (book.getAuthorId() <= 0) {
            throw new InvalidInputException("Author ID must be positive");
        }
        // Create book obj and add to the DataStore ArrayList 
        Book createdBook = DataStore.addBook(book);
        // Return response with 201 Created status
        return Response.status(Response.Status.CREATED).entity(createdBook).build();
    }

    // Get the list of all books from DataStore
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        return DataStore.getBooks();
    }

    // Get a specific book by its ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBook(@PathParam("id") int id) {
        Book book = DataStore.findBookById(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        return book;
    }
   
    // Update an existing book by its ID
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        if (updatedBook == null) {
            throw new InvalidInputException("Updated book cannot be null");
        }
        
        // Find the existing book
        Book existingBook = DataStore.findBookById(id);
        if (existingBook == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        
        // Ensure the updated book's ID matches the path parameter
        updatedBook.setBook_id(id);
        
        // Validate updated fields
        if (updatedBook.getTitle() == null || updatedBook.getTitle().isEmpty()) {
            throw new InvalidInputException("Title is required");
        }
        if (updatedBook.getPublicationYear() > 2025) {
            throw new InvalidInputException("Publication year cannot be in the future");
        }
        if (updatedBook.getPublicationYear() < 0) {
            throw new InvalidInputException("Publication year cannot be negative");
        }
        if (updatedBook.getAuthorId() <= 0) {
            throw new InvalidInputException("Author ID must be positive");
        }
        
        // Try updating the book, handle invalid input exceptions
        try {
            DataStore.updateBook(updatedBook);
            return Response.ok(updatedBook).build();
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    // Delete a book by its ID
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        // Find the book to be deleted
        Book book = DataStore.findBookById(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        // Remove the book from DataStore
        DataStore.getBooks().remove(book);
        // Return 204 No Content response
        return Response.noContent().build();
    }
}

