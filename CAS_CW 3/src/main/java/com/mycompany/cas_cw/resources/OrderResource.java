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
import java.util.List;


// OrderResource for order operations
@Path("/customers/{customerId}/orders")
public class OrderResource {
    
        // Get all orders for a customer
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public List<Order> getOrdersByCustomer(@PathParam("customerId") int customerId) {
            Customer customer = DataStore.findCustomerById(customerId);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
            }
            return DataStore.getOrdersByCustomerId(customerId);
        }
        
        // Get a specific order by ID
        @GET
        @Path("/{orderId}")
        @Produces(MediaType.APPLICATION_JSON)
        public Order getOrderById(@PathParam("customerId") int customerId, 
                                 @PathParam("orderId") int orderId) {
            Customer customer = DataStore.findCustomerById(customerId);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
            }
            
            Order order = DataStore.findOrderById(orderId);
            if (order == null) {
                throw new InvalidInputException("Order with ID " + orderId + " not found");
            }
            if (order.getCustomerId() != customerId) {
                throw new InvalidInputException("Order with ID " + orderId + " does not belong to customer ID " + customerId);
            }
            return order;
        }
        
        // Create a new order from the customer's cart
        @POST
        @Produces(MediaType.APPLICATION_JSON)
        public Response createOrder(@PathParam("customerId") int customerId) {
            Customer customer = DataStore.findCustomerById(customerId);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
            }
            
            Cart cart = DataStore.getCartByCustomerId(customerId);
            if (cart == null || cart.getItems().isEmpty()) {
                throw new CartNotFoundException("Cart for customer ID " + customerId + " is empty or does not exist");
            }
            
            // Check stock for all items before creating the order
            for (CartItem item : cart.getItems()) {
                Book book = DataStore.findBookById(item.getBookId());
                if (book == null) {
                    throw new BookNotFoundException("Book with ID " + item.getBookId() + " not found");
                }
                if (book.getStock_quantity() < item.getQuantity()) {
                    throw new OutOfStockException("Not enough stock for book ID " + item.getBookId() + ". Available: " + book.getStock_quantity() + ", Requested: " + item.getQuantity());
                }
            }
            
            // Create the order
            Order order = new Order(0, customerId); // Order ID will be set by DataStore
            for (CartItem cartItem : cart.getItems()) {
                OrderItem orderItem = new OrderItem(cartItem.getBookId(), cartItem.getQuantity());
                order.getItems().add(orderItem);
                // Reduce stock
                DataStore.reduceStock(cartItem.getBookId(), cartItem.getQuantity());
            }
            
            Order createdOrder = DataStore.addOrder(order);
            // Clear the cart after successful order placement
            DataStore.deleteCart(customerId);
            return Response.status(Response.Status.CREATED).entity(createdOrder).build();
        }
    
}
