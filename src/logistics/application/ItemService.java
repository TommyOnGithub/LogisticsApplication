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
 *
 * @author tbarry
 */
public class ItemService {
    
    private static ItemService unique;
    private ItemService() {}
    private final HashMap<String,Item> hm = new HashMap<>();
    private ArrayList<String> itemIDs;
    
    
    public static ItemService getInstance() {
        if (unique == null) {
            unique = new ItemService();
        }
        return unique;
    }
    
    public void getItems(String fname) throws
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
