/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cas_cw.resources;

import com.mycompany.cas_cw.exceptions.AuthorNotFoundException;
import com.mycompany.cas_cw.exceptions.InvalidInputException;
import com.mycompany.cas_cw.model.Author;
import com.mycompany.cas_cw.model.Book;
import com.mycompany.cas_cw.storage.DataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/authors")
public class AuthorResource {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author author) {
        // Basic validation
        if (author == null) {
            throw new InvalidInputException("Author cannot be null");
        }
        if (author.getF_name() == null || author.getF_name().isEmpty()) {
            throw new InvalidInputException("Author's first name is required");
        }
        if (author.getL_name() == null || author.getL_name().isEmpty()) {
            throw new InvalidInputException("Author's last name is required");
        }
        Author createAuthor = DataStore.addAuthor(author);
        return Response.status(Response.Status.CREATED).entity(createAuthor).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAuthors() {
        return DataStore.getAuthors();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Author getAuthor(@PathParam("id") int id) {
        Author author = DataStore.findAuthorById(id);
        if (author == null) {
            throw new AuthorNotFoundException("Auther with ID " + id + " not found");
        }
        return author;
    }
   
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        if (updatedAuthor == null) {
            throw new InvalidInputException("Updated author cannot be null");
        }
        Author existingAuthor = DataStore.findAuthorById(id);
        if (existingAuthor == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found");
        }
        // Ensure the updated author's ID matches the path parameter
        updatedAuthor.setAuthorId(id);
        // Validate updated fields
        if (updatedAuthor.getF_name() == null || updatedAuthor.getF_name().isEmpty()) {
            throw new InvalidInputException("Author's first name is required");
        }
        if (updatedAuthor.getL_name() == null || updatedAuthor.getL_name().isEmpty()) {
            throw new InvalidInputException("Author's last name is required");
        }
        if (updatedAuthor.getAuthorId() <= 0) {
            throw new InvalidInputException("Author ID must be positive");
        }
        try {
            DataStore.updateAuthor(updatedAuthor);
            return Response.ok(updatedAuthor).build();
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        Author author = DataStore.findAuthorById(id);
        if (author == null) {
            throw new AuthorNotFoundException("Auther with ID " + id + " not found");
        }
        DataStore.getAuthors().remove(author);
        return Response.noContent().build();
    }
    
    
    // Get details about books by Auhtor id
    @GET
    @Path("/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooksByAuthor(@PathParam("id") int id) {
        // Check if the author exists
        Author author = DataStore.findAuthorById(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found");
        }
        // Retrieve books by authorId using DataStore
        return DataStore.getBooksByAuthorId(id);
    }
     
    
    
}