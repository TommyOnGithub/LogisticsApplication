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
public class FacilityImpl implements Facility {
    
    private final String name;
    private final int cost;
    private final int rate;
    private final HashMap neighborsHM;
    private final HashMap inventoryHM;
    private ArrayList scheduleAL;

    
    public FacilityImpl(String name, int rate, int cost, HashMap neighbors, HashMap inventory) {
        this.name = name;
        this.cost = cost;
        this.rate = rate;
        this.neighborsHM = neighbors;
        this.inventoryHM = inventory;
        this.scheduleAL = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            scheduleAL.add(i, 10);
        }
    }
    
    public String getName() {
        return name;
    }
    /**
     * gives the rate at which this facility processes orders
     * 
     * @return rate the number of items this facility can process each day
     */
    public int getRate() {
        return rate;
    }
    
    /**
     * Returns the cost of the Facility's operations per day.
     * 
     * @return cost the cost of the Facility's operations per day.
     */
    public int getCost() {
        return cost;
    }
    
    /**
     * Returns a HashMap of this facility's neighbors with the names as the keys
     * and the distance from this facility as the value.
     * 
     *
     * @return 
     */
    public HashMap getNeighbors() {
        return neighborsHM;
    }
    
    public HashMap getInventory() {
        return inventoryHM;
    }
    
    public ArrayList getSchedule() {
        scheduleAL.ensureCapacity(20);
        return scheduleAL;
    }
}
