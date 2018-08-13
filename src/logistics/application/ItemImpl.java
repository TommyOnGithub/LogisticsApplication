/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

/**
 *
 * @author tbarry
 */
public class ItemImpl implements Item {
    
    private String itemID;
    private int cost;
    
    public ItemImpl(String ID, int Cost) {
        this.itemID = ID;
        this.cost = Cost;
    }
    
    public String getID() {
        return itemID;
    }
    
    public int getCost() {
        return cost;
    }
}
