/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cas_cw.resources;


import com.mycompany.cas_cw.exceptions.CustomerNotFoundException;
import com.mycompany.cas_cw.exceptions.InvalidInputException;
import com.mycompany.cas_cw.model.Customer;
import com.mycompany.cas_cw.storage.DataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


// Base path for all customer-related endpoints
@Path("/customers")
public class CustomerResource {

    // Create a new customer with details  
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer) {
        // Basic validation for inputs
        if (customer == null) {
            throw new InvalidInputException("Customer cannot be null");
        }
        if (customer.getName()== null || customer.getName().isEmpty()) {
            throw new InvalidInputException("Name is required");
        }
        if (customer.getEmail()== null || customer.getEmail().isEmpty()) {
            throw new InvalidInputException("Email is required");
        }
        if (customer.getPassword()== null || customer.getPassword().isEmpty()) {
            throw new InvalidInputException("Password is required");
        }
 
        // Create customer obj and add to the DataStore ArrayList 
        Customer createdCustomer = DataStore.addCustomer(customer);
        // Return response with 201 Created status
        return Response.status(Response.Status.CREATED).entity(createdCustomer).build();
    }

    // Get the list of all customers from DataStore
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomers() {
        return DataStore.getCustomers();
    }

    // Get a specific customer by its ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomer(@PathParam("id") int id) {
        Customer customer = DataStore.findCustomerById(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found");
        }
        return customer;
    }
   
    // Update an existing customer by its ID
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        if (updatedCustomer == null) {
            throw new InvalidInputException("Updated customer cannot be null");
        }
        
        // Find the existing customer
        Customer existingCustomer = DataStore.findCustomerById(id);
        if (existingCustomer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found");
        }
        
        // Ensure the updated customer's ID matches the path parameter
        updatedCustomer.setCustomerId(id);
        
        // Validate updated fields
        if (updatedCustomer.getName()== null || updatedCustomer.getName().isEmpty()) {
            throw new InvalidInputException("Name is required");
        }
        if (updatedCustomer.getEmail()== null || updatedCustomer.getEmail().isEmpty()) {
            throw new InvalidInputException("Email is required");
        }
        if (updatedCustomer.getPassword()== null || updatedCustomer.getPassword().isEmpty()) {
            throw new InvalidInputException("Password is required");
        }
        
        // Try updating the customer, handle invalid input exceptions
        try {
            DataStore.updateCustomer(updatedCustomer);
            return Response.ok(updatedCustomer).build();
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    // Delete a customer by its ID
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        // Find the customer to be deleted
        Customer customer = DataStore.findCustomerById(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found");
        }
        // Remove the customer from DataStore
        DataStore.getCustomers().remove(customer);
        // Return 204 No Content response
        return Response.noContent().build();
    }
}