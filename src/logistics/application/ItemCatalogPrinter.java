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
 * This class contains only one method, getCatalog, which prints a user-friendly
 * list of items in the program's catalog.
 * 
 * @author Tommy
 */
public class ItemCatalogPrinter {
    ItemService iService;
    
    public ItemCatalogPrinter() throws ParserConfigurationException, IOException, SAXException {
        this.iService = ItemService.getInstance("ItemsData.xml");
    }
    
    public void getCatalog() {
        for (String i : this.iService.getItemIDs()) {
            int cost = this.iService.getCost(i);
//            System.out.format("%-8s: $%-6s\n", i, cost);
        }
    }
}
