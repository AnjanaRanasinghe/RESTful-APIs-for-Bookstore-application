/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cas_cw.model;

import java.util.ArrayList;
import java.util.List;


public class Cart {
    private int customerId;
    private List<CartItem> items;

    public Cart() {
    }

    
    
    public Cart(int customerId) {
        this.customerId = customerId;
        this.items = new ArrayList<>();
    }

    // Getters and setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    
}
