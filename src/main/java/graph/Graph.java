package graph;

import java.util.*;


public class Graph {

    private final Map<String,Vertex> vertices = new HashMap<>();


    public Vertex addVertex(String id,double x,double y){
        return vertices.computeIfAbsent(id,k->new Vertex(k,x,y));
    }
    public void connect(String id1, String id2){
        Vertex v1 = vertices.get(id1);
        Vertex v2 = vertices.get(id2);
        if(v1 != null && v2 != null){
            v1.addNeighbour(v2);
            v2.addNeighbour(v1);
        }
    }


    public Vertex get(String id){ return vertices.get(id); }
    public Collection<Vertex> all(){ return vertices.values(); }
    public Vertex randomVertex(){
        if(vertices.isEmpty()) return null;
        int i = new Random().nextInt(vertices.size());
        return vertices.values().stream().skip(i).findFirst().orElse(null);
    }


    public List<Vertex> shortestPath(String fromId,String toId){
        Vertex src = vertices.get(fromId);
        Vertex dst = vertices.get(toId);
        List<Vertex> empty = Collections.emptyList();
        if(src==null || dst==null) return empty;
        if(src.equals(dst))        return List.of(src);

        Map<Vertex,Vertex> pred = new HashMap<>();
        Queue<Vertex> q         = new ArrayDeque<>();
        Set<Vertex> visited     = new HashSet<>();

        q.add(src); visited.add(src);

        while(!q.isEmpty()){
            Vertex cur = q.poll();
            if(cur.equals(dst)) break;
            for(Vertex n: cur.getNeighbours()){
                if(visited.add(n)){
                    pred.put(n,cur);
                    q.add(n);
                }
            }
        }

        if(!pred.containsKey(dst)) return empty; // sin camino

        List<Vertex> path = new ArrayList<>();
        for(Vertex v=dst; v!=null; v=pred.get(v)) path.add(0,v);
        return path;
    }
}