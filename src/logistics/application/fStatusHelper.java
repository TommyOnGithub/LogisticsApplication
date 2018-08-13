/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Tommy
 */
public class fStatusHelper {
    
    FacilityService fService;
    ShippingNetwork Network;
    
    public fStatusHelper() throws ParserConfigurationException, IOException, SAXException {
        this.fService = FacilityService.getInstance();
        this.Network = ShippingNetwork.getInstance(fService.getFNames());
    }
    
    public void getStatus(String fname) throws SAXException, ParserConfigurationException, IOException {
        String border = "----------------------------------------------------------------------------------\n";
        String facility = " "+fname;
        String line = " -------";
        String dLinks = " Direct Links:"; 
        String actInv = " Active Inventory:";
        String idQty = "   Item ID     Quantity";
        // Create inventory strings in print statements
        // Determine depleted inventory on print statement
        String sched = " Schedule:";
        String days = " Day:           1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20";
        
        System.out.println(border);
        System.out.println(facility);
        System.out.println(line);
        System.out.format(" Rate per Day: %d\n", fService.getRate(fname));
        System.out.format(" Cost per Day: $%d\n", fService.getCost(fname));
        System.out.println("");
        System.out.println(dLinks);
        String links;
        links = linksBuilder(fname);
        System.out.println(links);
        System.out.println("");
        System.out.println(actInv);
        invBuilder(fname);
        System.out.println("");
        System.out.println(deplInvBuilder(fname));
        System.out.println("");
        System.out.println(sched);
        System.out.println(days);
        System.out.println(schedBuilder(fname));
        System.out.println(border);
        System.out.flush();
    } 
    
    private String linksBuilder(String fName) throws
            ParserConfigurationException, IOException, SAXException {
        String retString = "";
        for (String i : fService.getNeighbors(fName).keySet()) {
            double neighborDist;
            neighborDist = fService.getTravelTime(fName, i);
            retString = retString.concat(i+" ("+neighborDist+"d); ");
        }
        retString = " "+retString.trim();
        return retString;
    }
    
    private void invBuilder(String fname) {
        System.out.println("    Item ID     Quantity");
        for (String i : fService.getInventory(fname).keySet()) {
            int itemQty = fService.getInventory(fname).get(i);
            System.out.format("    %-10s  %-3s\n", i, itemQty);
        }
    }
    
    private String deplInvBuilder(String fname) {
        boolean outOfStock = false;
        String retString = " Depleted Inventory: ";
        for (String i : fService.getInventory(fname).keySet()) {
            if (fService.getInventory(fname).get(i) == 0) {
                outOfStock = true;
                retString = retString.concat(i+", ");
            }
        }    
        if (outOfStock == false)
            retString = retString.concat("None");
        return " "+retString.trim();
    }
    
    private String schedBuilder(String fname) {
        String retString = " Available:     ";
        for (int i : fService.getSchedule(fname)) {
            String avail = Integer.toString(i);
            if (i < 10) {
                retString = retString.concat(avail+"  ");
            } else {
                retString = retString.concat(avail+" ");
            }
        }
        return " "+retString.trim();
    }
}
