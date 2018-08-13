/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

import java.util.HashMap;

/**
 *
 * @author tbarry
 */
public class Order {
    
    public final String orderID;
    public final int orderTime;
    public final String destination;
    public final HashMap<String, Integer> orderItems;
    
    public Order(String orderID, String destination, int orderTime, HashMap orderItems) {
        
        this.orderID = orderID;
        this.orderTime = orderTime;
        this.destination = destination;
        this.orderItems = orderItems;
    }
}
