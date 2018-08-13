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
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Tommy
 */
public class ShippingNetwork {
    
    private final int V;
    private int E;
    private FacilityService fService; 
    private HashSet<Edge>[] adj;
    private ArrayList<String> names;
    private static ShippingNetwork unique;
   
    private ShippingNetwork(ArrayList<String> facilities) throws ParserConfigurationException, IOException, SAXException {
        this.V = facilities.size();
        if (V < 0) throw new IllegalArgumentException("Number of verticies must be nonnegative");
        this.E = 0;
        this.fService = FacilityService.getInstance();
        adj = (HashSet<Edge>[]) new HashSet[V];
        names = new ArrayList(V);
        for (int v = 0; v < V; v++) {
            adj[v] = new HashSet<Edge>();
            names.add(v, facilities.get(v));
        }
        HashMap<String, Integer> neighbors = new HashMap<>();
        for (int i = 0; i < facilities.size(); i++) {
            neighbors = fService.getNeighbors(facilities.get(i));
            for (Map.Entry<String, Integer> entry : neighbors.entrySet()) {
                Edge e = new Edge(i, names.indexOf(entry.getKey()), entry.getValue());
                System.out.format("Edge as String: %s\n", e.toString());
                addEdge(e);
            }
        }
        
    }
    
    public static ShippingNetwork getInstance(ArrayList<String> facilities) throws ParserConfigurationException, IOException, SAXException {
        if (unique == null) {
            return new ShippingNetwork(facilities);
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
