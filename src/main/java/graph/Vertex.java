package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vertex {

    private final String id;
    private final double x;
    private final double y;
    private final List<Vertex> neighbours = new ArrayList<>();

    public Vertex(String id, double x, double y) {
        this.id = id;
        this.x  = x;
        this.y  = y;
    }

    /* GETTERS */
    public String getId()        { return id; }
    public double getX()         { return x;  }
    public double getY()         { return y;  }
    public List<Vertex> getNeighbours(){ return neighbours; }

    /* GRAPH MUTATORS */
    public void addNeighbour(Vertex v){
        if(!neighbours.contains(v)) neighbours.add(v);
        if(!v.neighbours.contains(this)) v.neighbours.add(this); // grafo no dirigido
    }

    @Override public String toString(){ return id+" ("+x+","+y+")"; }


    @Override public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof Vertex)) return false;
        return Objects.equals(id,((Vertex)o).id);
    }
    @Override public int hashCode(){ return Objects.hash(id); }
}