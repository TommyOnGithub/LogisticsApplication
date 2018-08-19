/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

/**
 *
 * @author tbarry
 */
public class Edge implements Comparable<Edge> {
    
    private final int v;
    private final int w;
    private final int weight;
    
    public Edge(int v, int w, int weight) {
        if (v < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (w < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    
    public int weight() {
        return weight;
    }
    
    public int either() {
        return v;
    }
    
    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }
    
    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }
    
    public String toString() {
        return String.format("%d-%d %d", v, w, weight);
    }
}
