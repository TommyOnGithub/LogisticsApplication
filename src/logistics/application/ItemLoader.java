/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * This class has one method, load, which parses Item data from a given XML
 * document into an ArrayList of Item objects.
 * 
 * @author tbarry
 */
public class ItemLoader {
    
    /**
     * This method parses the given XML document for Item data and returns
     * that data in the form of an ArrayList<Item>. It takes one String
     * argument, the file name, and parses the document, which should be saved
     * in the same directory. The return value is an ArrayList of Item
     * objects.
     * 
     * @param fname
     * @return ArrayList<Item>
     * @throws IOException 
     */
    public ArrayList load (String fname) throws IOException {
        ArrayList<Item> itemList = new ArrayList<>();
        ItemFactory itemFactory = new ItemFactory();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(fname);
            
            // normalize text representation
            doc.getDocumentElement().normalize();
            
            
            NodeList listOfItems = doc.getElementsByTagName("record");
            
            for (int s = 0; s < listOfItems.getLength(); s++) {
                
                Node itemNode = listOfItems.item(s);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    
                    Element itemElement = (Element)itemNode;
                    
                    //-------
                    NodeList itemIDList = itemElement.getElementsByTagName("ItemID");
                    Element itemIDElement = (Element)itemIDList.item(0);
                    
                    NodeList textItemIDList = itemIDElement.getChildNodes();
                    String itemID = ((Node)textItemIDList.item(0)).getNodeValue().trim();
                    
                    //-------
                    NodeList priceList = itemElement.getElementsByTagName("Price");
                    Element priceElement = (Element)priceList.item(0);
                    
                    NodeList textPriceList = priceElement.getChildNodes();
                    String stringItemCost = ((Node)textPriceList.item(0)).getNodeValue().trim();
                    int itemCost = Integer.parseInt(stringItemCost);
                    // System.out.println("Item price: " + ((Node)textPriceList.item(0)).getNodeValue().trim());
                    
                    //-------
                    Item item = itemFactory.build(itemID, itemCost);
                    itemList.add(item);
                }
            }
        } catch (IOException e) {
            System.exit(1);
        } catch (SAXException i) {
            System.exit(2);
        } catch (ParserConfigurationException x) {
            System.exit(3);
        }
        return itemList;
    }
}
