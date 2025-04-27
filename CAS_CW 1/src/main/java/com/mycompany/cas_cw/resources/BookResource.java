/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cas_cw.resources;

import com.mycompany.cas_cw.exceptions.BookNotFoundException;
import com.mycompany.cas_cw.exceptions.InvalidInputException;
import com.mycompany.cas_cw.model.Book;
import com.mycompany.cas_cw.storage.DataStore;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/books")
public class BookResource {

    // Create a book with details  
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
        // Response the message CREATED
        return Response.status(Response.Status.CREATED).entity(createdBook).build();
    }

    // get books details from DataStore
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        return DataStore.getBooks();
    }

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
   
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        if (updatedBook == null) {
            throw new InvalidInputException("Updated book cannot be null");
        }
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
        try {
            DataStore.updateBook(updatedBook);
            return Response.ok(updatedBook).build();
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book book = DataStore.findBookById(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        DataStore.getBooks().remove(book);
        return Response.noContent().build();
    }
}

