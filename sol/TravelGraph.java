package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TravelGraph implements IGraph<City, Transport> {
    HashMap<City, Set<Transport>> graph;

    public TravelGraph(){
        this.graph = new HashMap<>();
    }

    @Override
    public void addVertex(City vertex) {
        Set<Transport> outgoing = new HashSet<>();
        this.graph.put(vertex, outgoing);
    }

    @Override
    public void addEdge(City origin, Transport edge) {
        this.graph.get(origin).add(edge);
        origin.addOut(edge);
        this.graph.replace(origin, this.graph.get(origin));
    }

    @Override
    public Set<City> getVertices() {
        return this.graph.keySet();

    }

    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return this.graph.get(fromVertex);
    }

    public City getCity(String name){
        for (City a : this.getVertices()) {
            if (a.toString().equals(name)){
                return a;
            }
        }
        throw new RuntimeException();
    }

    public Double getTotalPrice(List<Transport> ls){
        Double d = 0.0;
        for (Transport t : ls){
            Double a = t.getPrice();
            d = d + a;
        }
        return d;
    }

    public Double getTotalTime(List<Transport> ls){
        Double d = 0.0;
        for (Transport t : ls){
            Double a = t.getMinutes();
            d = d + a;
        }
        return d;
    }

}