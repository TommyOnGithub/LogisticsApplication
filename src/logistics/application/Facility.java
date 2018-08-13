/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author tbarry
 */
public interface Facility {
    public String getName();
    public int getRate();
    public int getCost();
    public HashMap getNeighbors();
    public ArrayList getSchedule();
    public HashMap getInventory();
}
