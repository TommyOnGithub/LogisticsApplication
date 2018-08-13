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
public class ItemFactory {
    
    public Item build(String ID, int cost) {
        return new ItemImpl(ID, cost);
    }
}
