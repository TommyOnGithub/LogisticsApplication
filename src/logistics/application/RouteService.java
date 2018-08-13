/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Tommy
 */
public class RouteService {
    
    private FacilityService fService;
    private double[] distTo;
    private Edge[] edgeTo;
    private IndexMinPQ<Double> pq;
    
    public RouteService(ShippingNetwork G, int s) throws ParserConfigurationException, ParserConfigurationException, IOException, SAXException {
        fService = FacilityService.getInstance();
        
        for (Edge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight value");
        }
        
        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        
                
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        System.out.format("Length of pq is: %d\n", pq.size());
        while (!pq.isEmpty()) {
//            System.out.format("Length of pq (inside loop) is: %d\n", pq.size());
//            for (int i : pq)
//                System.out.format("%s\n", fService.getFNames().get(i));
            int v = pq.delMin();
            for (Edge e : G.adj(v))
                relax(e);
        }
    }
        
    private void relax(Edge e) {
        int v = e.either();
        int w = e.other(v);
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else pq.insert(w, distTo[w]);
        }
        if (distTo[v] > distTo[w] + e.weight()) {
            distTo[v] = distTo[w] + e.weight();
            edgeTo[v] = e;
            if (pq.contains(v)) pq.decreaseKey(v, distTo[v]);
            else pq.insert(v, distTo[v]);
        }
    }
    
    public double distTo(int v) {
        return distTo[v];
    }
    
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }
    
    public Iterable<Edge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<Edge>();
        System.out.format("edgeTo[v] = %s\n", edgeTo[v]);
        for (Edge e = edgeTo[v]; e != null;) {
            System.out.format("edgeTo[v] = %s", edgeTo[v]);
            path.push(e);
            e = edgeTo[e.other(e.either())];
            System.out.format("Path represented as a String: %s\n", path.toString());
        }
        return path;
    }
}
