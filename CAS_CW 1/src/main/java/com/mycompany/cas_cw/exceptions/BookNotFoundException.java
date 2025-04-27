/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cas_cw.exceptions;

/**
 *
 * @author USER
 */
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message){
        super(message);  // Takes a message parameter to store the validation error message
    }
    
}
