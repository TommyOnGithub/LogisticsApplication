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
 * @author tbarry
 */
public class FacilityRecord {
    
    private FacilityService fService;
    private ShippingNetwork sn;
    private Facility f;
    private double travelDays;
    private int procEndDay;
    private double arrivalDay;

    public FacilityRecord(Facility supplier, String requestor, String item, int qty) throws ParserConfigurationException, IOException, SAXException {
        
        this.fService = FacilityService.getInstance("FacilityDataSet.xml");
        this.f = f;
        this.travelDays = fService.getTravelTime(supplier.getName(), requestor);
        this.procEndDay = fService.shipDay(supplier, item, qty);
        this.arrivalDay = travelDays + procEndDay;
    }
    
    public int getProcEndDay() {
        return procEndDay;
    }
    
    public double getArrivalDay() {
        return arrivalDay;
    }
}
