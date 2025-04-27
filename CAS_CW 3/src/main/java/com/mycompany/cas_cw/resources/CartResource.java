/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cas_cw.resources;

import com.mycompany.cas_cw.exceptions.*;
import com.mycompany.cas_cw.model.*;
import com.mycompany.cas_cw.storage.DataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



// CartResource for cart operations
@Path("/customers/{customerId}/cart")
public class CartResource {
        // Get the customer's cart
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Cart getCart(@PathParam("customerId") int customerId) {
            Customer customer = DataStore.findCustomerById(customerId);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
            }
            Cart cart = DataStore.getCartByCustomerId(customerId);
            if (cart == null) {
                throw new CartNotFoundException("Cart for customer ID " + customerId + " not found");
            }
            return cart;
        }
        
        // Add an item to the cart
        @POST
        @Path("/items")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response addItemToCart(@PathParam("customerId") int customerId, CartItem item) {
            if (item == null) {
                throw new InvalidInputException("Cart item cannot be null");
            }
            if (item.getQuantity() <= 0) {
                throw new InvalidInputException("Quantity must be positive");
            }
            
            Customer customer = DataStore.findCustomerById(customerId);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
            }
            
            Book book = DataStore.findBookById(item.getBookId());
            if (book == null) {
                throw new BookNotFoundException("Book with ID " + item.getBookId() + " not found");
            }
            
            Cart cart = DataStore.getCartByCustomerId(customerId);
            if (cart == null) {
                cart = new Cart(customerId);
                DataStore.addCart(cart);
            }
            
            // Check if the book is already in the cart
            CartItem existingItem = cart.getItems().stream()
                                        .filter(i -> i.getBookId() == item.getBookId())
                                        .findFirst()
                                        .orElse(null);
            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            } else {
                cart.getItems().add(item);
            }
            
            DataStore.updateCart(cart);
            return Response.status(Response.Status.CREATED).entity(cart).build();
        }
        
        // Update an item in the cart
        @PUT
        @Path("/items/{bookId}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response updateItemInCart(@PathParam("customerId") int customerId, 
                                        @PathParam("bookId") int bookId, 
                                        CartItem updatedItem) {
            if (updatedItem == null) {
                throw new InvalidInputException("Cart item cannot be null");
            }
            if (updatedItem.getQuantity() <= 0) {
                throw new InvalidInputException("Quantity must be positive");
            }
            if (updatedItem.getBookId() != bookId) {
                throw new InvalidInputException("Book ID in request body must match path parameter");
            }
            
            Customer customer = DataStore.findCustomerById(customerId);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
            }
            
            Book book = DataStore.findBookById(bookId);
            if (book == null) {
                throw new BookNotFoundException("Book with ID " + bookId + " not found");
            }
            
            Cart cart = DataStore.getCartByCustomerId(customerId);
            if (cart == null) {
                throw new CartNotFoundException("Cart for customer ID " + customerId + " not found");
            }
            
            CartItem existingItem = cart.getItems().stream()
                                        .filter(i -> i.getBookId() == bookId)
                                        .findFirst()
                                        .orElse(null);
            if (existingItem == null) {
                throw new BookNotFoundException("Book with ID " + bookId + " not found in cart");
            }
            
            existingItem.setQuantity(updatedItem.getQuantity());
            DataStore.updateCart(cart);
            return Response.ok(cart).build();
        }
        
        // Remove an item from the cart
        @DELETE
        @Path("/items/{bookId}")
        public Response removeItemFromCart(@PathParam("customerId") int customerId, 
                                          @PathParam("bookId") int bookId) {
            Customer customer = DataStore.findCustomerById(customerId);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
            }
            
            Book book = DataStore.findBookById(bookId);
            if (book == null) {
                throw new BookNotFoundException("Book with ID " + bookId + " not found");
            }
            
            Cart cart = DataStore.getCartByCustomerId(customerId);
            if (cart == null) {
                throw new CartNotFoundException("Cart for customer ID " + customerId + " not found");
            }
            
            CartItem itemToRemove = cart.getItems().stream()
                                        .filter(i -> i.getBookId() == bookId)
                                        .findFirst()
                                        .orElse(null);
            if (itemToRemove == null) {
                throw new BookNotFoundException("Book with ID " + bookId + " not found in cart");
            }
            
            cart.getItems().remove(itemToRemove);
            if (cart.getItems().isEmpty()) {
                DataStore.deleteCart(customerId);
            } else {
                DataStore.updateCart(cart);
            }
            return Response.noContent().build();
        }
}
