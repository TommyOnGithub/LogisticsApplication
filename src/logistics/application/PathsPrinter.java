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
 * @author Tommy
 */
public class PathsPrinter {
    FacilityService FS;
    ShippingNetwork SN;
    String A;
    String B;
    String C;
    String D;
    String E;
    String F;
    String G;
    String H;
    String I;
    String J;

    public PathsPrinter() throws ParserConfigurationException, IOException, SAXException {
        this.FS = FacilityService.getInstance();
        this.SN = ShippingNetwork.getInstance(FS.getFNames());
        String bullet = Character.toString((char) 149);
        A = "a)  Santa Fe, NM to Chicago, IL:\n"
                + bullet + pathString("Santa Fe, NM", "Chicago, IL")+"\n"
                + bullet + calcString("Santa Fe, NM", "Chicago, IL")+"\n";
        B = "b)  Atlanta, GA to St. Louis, MO:\n"
                + bullet + pathString("Atlanta, GA", "St. Louis, MO")+"\n"
                + bullet + calcString("Atlanta, GA", "St. Louis, MO")+"\n";
        C = "c)  Seattle, WA to Nashville, TN:\n"
                + bullet + pathString("Seattle, WA", "Nashville, TN")+"\n"
                + bullet + calcString("Seattle, WA", "Nashville, TN")+"\n";
        D = "d)  New York City, NY to Phoenix, AZ:\n"
                + bullet + pathString("New York City, NY", "Phoenix, AZ")+"\n"
                + bullet + calcString("New York City, NY", "Phoenix, AZ")+"\n";
        E = "e)  Fargo, ND to Austin TX:\n"
                + bullet + pathString("Fargo, ND", "Austin, TX")+"\n"
                + bullet + calcString("Fargo, ND", "Austin, TX")+"\n";
        F = "f)  Denver, CO to Miami, FL:\n"
                + bullet + pathString("Denver, CO", "Miami, FL")+"\n"
                + bullet + calcString("Denver, CO", "Miami, FL")+"\n";
        G = "g)  Austin, TX to Norfolk, VA:\n"
                + bullet + pathString("Austin, TX", "Norfolk, VA")+"\n"
                + bullet + calcString("Austin, TX", "Norfolk, VA")+"\n";
        H = "h)  Miami, FL to Seattle, WA:\n"
                + bullet + pathString("Miami, FL", "Seattle, WA")+"\n"
                + bullet + calcString("Miami, FL", "Seattle, WA")+"\n";
        I = "i)  Los Angeles, CA to Chicago, IL:\n"
                + bullet + pathString("Los Angeles, CA", "Chicago, IL")+"\n"
                + bullet + calcString("Los Angeles, CA", "Chicago, IL")+"\n";
        J = "j)  Detroit, MI to Nashville, TN:\n"
                + bullet + pathString("Detroit, MI", "Nashville, TN")+"\n"
                + bullet + calcString("Detroit, MI", "Nashville, TN")+"\n";
    }
    
    public void getShortestPathTests() {
        System.out.println("Shortest Path Tests:");
        System.out.println("");
        System.out.print(A);
        System.out.print(B);
        System.out.print(C);
        System.out.print(D);
        System.out.print(E);
        System.out.print(F);
        System.out.print(G);
        System.out.print(H);
        System.out.print(I);
        System.out.print(J);
    }
    
    private String pathString(String from, String to) throws ParserConfigurationException, IOException, SAXException {
        int f = FS.getFNames().indexOf(from);
        int t = FS.getFNames().indexOf(to);
        RouteService RS = new RouteService(SN, f);
        String retString = "  ";
        String arrow = Character.toString((char) 0x2191);
        boolean leadingArrow = false;
        for (Edge e : RS.pathTo(f)) {
            if (leadingArrow == false){
                leadingArrow = true;
                retString = retString.concat(e.toString());
                break;
            } else retString = retString.concat(arrow + e.toString());
            System.out.println(retString);
        }
        System.out.println(to);
        retString = retString.concat(String.format(" = %f mi\n", RS.distTo(t)));
        return retString;
    }
    
    private String calcString(String from, String to) throws ParserConfigurationException, IOException, SAXException {
        int f = FS.getFNames().indexOf(from);
        int t = FS.getFNames().indexOf(to);
        RouteService RS = new RouteService(SN, f);
        String retString = String.format("  %f mi / (8 hours per day * 50 mph) = %f days", RS.distTo(t), FS.getTravelTime(from, to));
        return retString;
    }
}
