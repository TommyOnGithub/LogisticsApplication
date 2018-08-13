/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author tbarry
 */
public class FacilityFactory {
    
    ItemService iService = ItemService.getInstance();

    public FacilityFactory() throws ParserConfigurationException,
            IOException, SAXException {
    }
    
    /**
     * This method is used to create complete Facility objects including
     * inventories, adjacent facilities, and blank job schedules.
     * 
     * @param name the name of the facility
     * @param rate the number 
     * @param cost
     * @param neighbors
     * @param rawInventory
     * @return 
     */
    public Facility build(String name, int rate, int cost,
            ArrayList<String> neighbors, ArrayList<String> rawInventory) {
        
        Pattern fPattern;
        fPattern = Pattern.compile("(^.+,\\s.{2})\\s\\((\\d{3,4})\\)");
        HashMap<String, Integer> fNeighbors = new HashMap();
        for (int i = 0; i < neighbors.size(); i++) {
            String fname = new String();
            int fdist = 0;
            String f = neighbors.get(i);
            Matcher m = fPattern.matcher(f);
            if (m.matches()) {
                fname = m.group(1);
                fdist = Integer.parseInt(m.group(2));
                fNeighbors.put(fname, fdist);
            }
        }
        
        Pattern iPattern;
        iPattern = Pattern.compile("(^.+)\\s\\((\\d{1,3})\\)");
        HashMap<String, Integer> fInventory = new HashMap();
        for (int j = 0; j < rawInventory.size(); j++) {
            String itemAndQty = rawInventory.get(j);
            Matcher m = iPattern.matcher(itemAndQty);
            if (m.matches()) {
                String iName = m.group(1);
                assert iService.isItem(iName);
                Integer iQty = Integer.parseInt(m.group(2));
                fInventory.put(iName, iQty);
            }
        }
                
        return new FacilityImpl(name, rate, cost, fNeighbors, fInventory);
    }
}