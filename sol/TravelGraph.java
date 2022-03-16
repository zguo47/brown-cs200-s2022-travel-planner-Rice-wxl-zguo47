package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TravelGraph implements IGraph<City, Transport> {
    HashMap<City, Set<Transport>> graph;

    public TravelGraph(){
        this.graph = new HashMap<>();
    }

    @Override
    public void addVertex(City vertex) {
        // TODO: implement this method!
        Set<Transport> outgoing = new HashSet<>();
        this.graph.put(vertex, outgoing);
    }

    @Override
    public void addEdge(City origin, Transport edge) {
        // TODO: implement this method!
        this.graph.get(origin).add(edge);
    }

    @Override
    public Set<City> getVertices() {
        // TODO: implement this method!
        return this.graph.keySet();

    }

    @Override
    public City getEdgeSource(Transport edge) {
        // TODO: implement this method!
        return edge.getSource();
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        // TODO: implement this method!
        return edge.getTarget();
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        // TODO: implement this method!
        return this.graph.get(fromVertex);
    }

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name
}