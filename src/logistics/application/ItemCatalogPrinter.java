/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

/**
 *
 * @author Tommy
 */
public class ItemCatalogPrinter {
    ItemService iService = ItemService.getInstance();
    
    public void getCatalog() {
        for (String i : iService.getItemIDs()) {
            int cost = iService.getCost(i);
            System.out.format("%-8s: $%-6s\n", i, cost);
        }
    }
}
