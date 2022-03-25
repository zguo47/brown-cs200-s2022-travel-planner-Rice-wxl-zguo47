package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is the TravelGraph class that creates a standard graph of cities and
 * transports as vertices and edges,
 * respectively.
 */
public class TravelGraph implements IGraph<City, Transport> {
    HashMap<City, Set<Transport>> graph;

    /**
     * A constructor for the TravelGraph.
     */
    public TravelGraph(){
        this.graph = new HashMap<>();
    }

    /**
     * This method adds a city to the graph as a new vertex. Its outgoing edges
     * is currently set to be an empty set.
     * @param vertex the vertex
     */
    @Override
    public void addVertex(City vertex) {
        Set<Transport> outgoing = new HashSet<>();
        this.graph.put(vertex, outgoing);
    }

    /**
     * This method adds a new transport for the city to the graph. It updates
     * the corresponding set of transports for
     * the origin city.
     * @param origin the origin of the edge.
     * @param edge the edge to be put.
     */
    @Override
    public void addEdge(City origin, Transport edge) {
            this.graph.get(origin).add(edge);
            origin.addOut(edge);
            this.graph.replace(origin, this.graph.get(origin));
    }

    /**
     * This method gets all the cities in the graph.
     */
    @Override
    public Set<City> getVertices() {
        return this.graph.keySet();

    }

    /**
     * This method gets a transport's origin city.
     * @param edge the edge
     * @return the corresponding origin vertex.
     */
    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    /**
     * This method gets a transport's destination city.
     * @param edge the edge
     * @return the corresponding destination vertex.
     */
    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    /**
     * This method gets all transports originated from the input city.
     * @param fromVertex the vertex
     * @return a set of edges that originated from the input vertex.
     */
    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return this.graph.get(fromVertex);
    }

    /**
     * This method gets a city in the graph by its name.
     * @param name the vertex's name
     * @return the vertex
     */
    public City getCity(String name){
        for (City a : this.getVertices()) {
            if (a.toString().equals(name)){
                return a;
            }
        }
        throw new IllegalArgumentException("City does not exist!");
    }

    /**
     * This method helps for the testing of cheapest route. It calculates the
     * total price of the route.
     * @param ls a list of transports in the route.
     * @return the total price.
     */
    public Double getTotalPrice(List<Transport> ls){
        Double d = 0.0;
        for (Transport t : ls){
            Double a = t.getPrice();
            d = d + a;
        }
        return d;
    }

    /**
     * This method helps for the testing of fastest route. It calculates the
     * total time of the route.
     * @param ls a list of transports in the route.
     * @return the total time.
     */
    public Double getTotalTime(List<Transport> ls){
        Double d = 0.0;
        for (Transport t : ls){
            Double a = t.getMinutes();
            d = d + a;
        }
        return d;
    }

}