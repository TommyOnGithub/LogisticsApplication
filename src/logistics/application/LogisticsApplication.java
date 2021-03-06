/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author tbarry
 */
public class LogisticsApplication {

    /**
     * Main method of the application.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        FacilityService fService = FacilityService.getInstance("FacilityDataSet1.xml");
        fStatusHelper fStatus = new fStatusHelper();
        ItemCatalogPrinter iPrinter = new ItemCatalogPrinter();
        ItemService iService = ItemService.getInstance("ItemsData.xml");
        ShippingNetwork network = ShippingNetwork.getInstance();
        ArrayList<String> names = fService.getFNames();
        for (String name : names) {
            fStatus.getStatus(name);
        }
        iPrinter.getCatalog();
        PathsPrinter PP = new PathsPrinter();
        PP.getShortestPathTests();
    }
    
}
