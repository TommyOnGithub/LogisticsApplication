/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

import java.io.IOException;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Tommy
 */
public class ShippingNetwork {
    
    private final int               V;
    private int                     E;
    private FacilityService         fService; 
    private HashSet<Edge>[]         adj;
    private ArrayList<String>       names;
    private static ShippingNetwork  unique;
   
    private ShippingNetwork() throws ParserConfigurationException, IOException, SAXException {
        this.fService = FacilityService.getInstance("FacilityDataSet.xml");
        this.names = fService.getFNames();
//        System.out.println(this.names);
        this.V = this.names.size();
        this.E = 0;
        this.adj = (HashSet<Edge>[]) new HashSet[V];
        for (int v = 0; v < V; v++) {
            this.adj[v] = new HashSet<Edge>();
        }
        Set<Map.Entry<String, Integer>> neighbors;
        for (String name : this.names) {
            neighbors = this.fService.getNeighbors(name).entrySet();
//            System.out.print(neighbors + "\n");
            for (Map.Entry<String, Integer> entry : neighbors) {
                Edge e = new Edge(this.names.indexOf(name), this.names.indexOf(entry.getKey()), entry.getValue());
//                System.out.format("Edge as String: %s\n", e.toString());
                addEdge(e);
            }
        }
        
    }
    
    public static ShippingNetwork getInstance() throws ParserConfigurationException, IOException, SAXException {
        if (unique == null) {
            return new ShippingNetwork();
        }
        return unique;
    }
    
    public int V() {
        return V;
    }
    
    public int E() {
        return E;
    }
    
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
    }
    
    private void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }
    
    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }
    
    public Iterable<Edge> edges() {
        HashSet<Edge> list = new HashSet<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // only add one copy of each self loop (self loops will be consecutive)
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }
    
}
