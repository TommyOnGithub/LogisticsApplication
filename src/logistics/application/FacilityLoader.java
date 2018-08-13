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


/**
 *
 * @author tbarry
 */
public class FacilityLoader {
    
    public ArrayList load(String fname) throws SAXException, IOException, ParserConfigurationException {
        ArrayList<Facility> facilityList = new ArrayList<>();
        FacilityFactory facilityFactory = new FacilityFactory();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(fname);
            
            // normalize text representation
            doc.getDocumentElement().normalize();
            
            NodeList listOfFacilities = doc.getElementsByTagName("record");
            
            for (int s = 0; s < listOfFacilities.getLength(); s++) {
                Node facilityNode = listOfFacilities.item(s);
                
                if (facilityNode.getNodeType() == Node.ELEMENT_NODE) {
                    
                    Element facilityElement = (Element)facilityNode;
                    
                    //-------
                    NodeList facilityNameList = facilityElement.getElementsByTagName("Name");
                    Element facilityNameElement = (Element)facilityNameList.item(0);
                    
                    NodeList textFacilityNameList = facilityNameElement.getChildNodes();
                    String name = ((Node)textFacilityNameList.item(0)).getNodeValue().trim();
                    
                    //-------
                    NodeList costList = facilityElement.getElementsByTagName("Cost");
                    Element costElement = (Element)costList.item(0);
                    
                    NodeList textCost = costElement.getChildNodes();
                    int cost = Integer.parseInt(((Node)textCost.item(0)).getNodeValue().trim());
                    
                    //-------
                    NodeList rateList = facilityElement.getElementsByTagName("Rate");
                    Element facilityRateElement = (Element)rateList.item(0);
                    
                    NodeList textRate = facilityRateElement.getChildNodes();
                    String rate = ((Node)textRate.item(0)).getNodeValue().trim();
                    
                    //-------
                    NodeList neighborsList = facilityElement.getElementsByTagName("Neighbor");
                    ArrayList<String> neighbors = new ArrayList<>();
                    for (int t = 0; t < neighborsList.getLength(); t++) {
                        
                        Element neighborsListElement = (Element)neighborsList.item(t);
                        
                        NodeList textNeighborList = neighborsListElement.getChildNodes();
                        String neighbor = ((Node)textNeighborList.item(0)).getNodeValue().trim();
                        neighbors.add(neighbor);
                    }
                    
                    //-------
                    NodeList itemsList = facilityElement.getElementsByTagName("Item");
                    ArrayList<String> items = new ArrayList<>();
                    for (int j = 0; j < itemsList.getLength(); j++) {
                        
                        Element itemsListElement = (Element)itemsList.item(j);
                        
                        NodeList textItemsList = itemsListElement.getChildNodes();
                        String item = ((Node)textItemsList.item(0)).getNodeValue().trim();
                        items.add(item);
                    }
                    
                    //-------
                    Facility facility = facilityFactory.build(name, Integer.parseInt(rate), cost, neighbors, items);
                    facilityList.add(facility);
                    
                }
            }
        } catch (IOException e) {
            System.exit(1);
        } catch (SAXException i) {
            System.exit(2);
        }
        return facilityList;
    }
}
