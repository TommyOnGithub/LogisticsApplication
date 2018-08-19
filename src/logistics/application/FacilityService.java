/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;


/**
 * The FacilityService keeps track of Facilities in the program's network. There
 * are also methods to query information on specific facilities.
 * 
 * @author tbarry
 */
public class FacilityService {
    
    private static FacilityService unique;
    private final HashMap<String, Facility> hm = new HashMap();
    private ArrayList<String> fNames;
    
    private FacilityService(String fname) throws
            ParserConfigurationException, IOException, SAXException{
        getFacilities(fname);
    }
    
    public static FacilityService getInstance(String fileName) throws
            ParserConfigurationException, IOException, SAXException {
        if (unique == null) {
            unique = new FacilityService(fileName);
        }
        return unique;
    }
    
    private void getFacilities(String fname) throws
            ParserConfigurationException, IOException, SAXException {
        FacilityLoader fLoader = new FacilityLoader();
        ArrayList facilitiesList = fLoader.load(fname);
        
        fNames = new ArrayList<String>(facilitiesList.size());
        for (int i = 0; i < facilitiesList.size(); i++) {
            Facility f = (Facility)facilitiesList.get(i);
            fNames.add(f.getName());
            hm.put(f.getName(), f);
        }
    }
    
    public int shipDay(Facility f, String item, int qty) {
        return 1;
    }
    
    public double getTravelTime(String from, String to) throws
            ParserConfigurationException, IOException, SAXException {
        int f = fNames.indexOf(from);
        int t = fNames.indexOf(to);
        ShippingNetwork SN;
        SN = ShippingNetwork.getInstance();
        RouteService RS = new RouteService(SN, f);
        Double travelTime = RS.distTo(t)/50/8;
        travelTime = Math.round(travelTime * 10d) / 10d;
        return travelTime;
    }
    
    public boolean isFacility(String fname) {
        return hm.containsKey(fname);
    }
    
    public ArrayList<String> getFNames() {
        return fNames;
    }
    
    public int getRate(String fname) {
        assert isFacility(fname);
        return hm.get(fname).getRate();
    }
    
    public int getCost(String fname) {
        assert isFacility(fname);
        return hm.get(fname).getCost();
    }
    
    public HashMap<String, Integer> getNeighbors(String fname) {
        assert isFacility(fname);
        HashMap<String, Integer> neighbors = hm.get(fname).getNeighbors();        
        return neighbors;
    }
    
    public HashMap<String, Integer> getInventory(String fname) {
        assert isFacility(fname);
        HashMap<String, Integer> inventory = hm.get(fname).getInventory();
        return inventory;
    }
    
    public ArrayList<Integer> getSchedule(String fname) {
        assert isFacility(fname);
        ArrayList<Integer> sched = hm.get(fname).getSchedule();
        return sched;
    }

    public int getDistance(String from, String to) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
