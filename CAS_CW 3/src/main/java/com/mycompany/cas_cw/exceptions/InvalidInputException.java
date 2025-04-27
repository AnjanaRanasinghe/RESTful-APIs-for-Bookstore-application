/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cas_cw.exceptions;

// Extends RuntimeException to make it an unchecked exception, which is standard for JAX-RS custom exceptions
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);     // Takes a message parameter to store the validation error message
    }
}
