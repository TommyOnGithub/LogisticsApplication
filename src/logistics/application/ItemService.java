/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * The ItemService keeps track of Items in the program's catalog. It also
 * contains methods to query data on items in the catalog.
 * 
 * @author tbarry
 */
public class ItemService {
    
    private static ItemService unique;
    private final HashMap<String,Item> hm = new HashMap<>();
    private ArrayList<String> itemIDs;
    
    private ItemService(String fileName) throws
            ParserConfigurationException, IOException, SAXException {
        getItems(fileName);
    }
    
    /**
     * Returns a pointer to the singleton ItemService to the caller.
     * 
     * @return 
     */
    public static ItemService getInstance(String fileName) throws
            ParserConfigurationException, IOException, SAXException {
        if (unique == null) {
            unique = new ItemService(fileName);
        }
        return unique;
    }
    
    /**
     * This method retrieves a list of Items on startup to initialize the
     * program's ItemService.
     * 
     * @param fname
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException 
     */
    private void getItems(String fname) throws
            ParserConfigurationException, IOException, SAXException {
        ItemLoader iLoader = new ItemLoader();
        ArrayList itemsList = iLoader.load(fname);
        
        itemIDs = new ArrayList<String>(itemsList.size());
        for (int i = 0; i < itemsList.size(); i++) {
            Item item = (Item)itemsList.get(i);
            itemIDs.add(item.getID());
            hm.put(item.getID(), item);
        }
    }
    
    public ArrayList<String> getItemIDs() {
        return itemIDs;
    }
    
    public boolean isItem(String ID) {
        return hm.containsKey(ID);
    }
    
    public int getCost(String itemID) {
        assert isItem(itemID);
        return hm.get(itemID).getCost();
    }
    
    public void addItem(Item i) throws NoSuchItemException {
        if (isItem(i.getID())) {
            hm.put(i.getID(), i);
        } else {
            throw new NoSuchItemException("This item does not exist");
        }
    }
    
}
