/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.cas_cw.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

// Exception mapper to handle BookNotFoundException globally
@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {
    
    // Converts BookNotFoundException into a HTTP 404 (Not Found) response
    @Override
    public Response toResponse(BookNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)    // Set HTTP status code 404
                      .entity(new ErrorResponse(exception.getMessage()))    // Include error message in response body
                      .type(MediaType.APPLICATION_JSON)    // Set response content type to JSON
                      .build();
    }

     // Inner class to represent the structure of the error response in JSON
    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() { 
            return error; 
        }
        
        public void setError(String error) { 
            this.error = error; 
        }
    }
}
